import json
import os
from openai import OpenAI
from typing import List, Dict, Any
from dotenv import load_dotenv

load_dotenv()

client = OpenAI(
    api_key=os.getenv("OPENAI_API_KEY"),
    base_url=os.getenv("BASE_URL")
)

# ============= Tool Functions Implementation =============
def read_file(file_path: str) -> str:
    """读取文件内容"""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        return content
    except FileNotFoundError:
        return f"Error: File '{file_path}' not found"
    except Exception as e:
        return f"Error reading file: {str(e)}"
    
#Todo:

def write_file(file_path: str, content: str) -> str:
    """写入内容到文件"""
    try:
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
        return f"Successfully wrote to file '{file_path}'"
    except FileNotFoundError:
        return f"Error: File '{file_path}' not found"
    except Exception as e:
        return f"Error writing file: {str(e)}"

def command_exec(command: str, confirmation: bool) -> str:
    """执行命令行指令"""
    if confirmation:
        if input(f"Are you sure you want to execute the command: \"{command}\"? (y/n): ").lower() != 'y':
            return "Command execution is cancelled by user."
    try:
        return os.popen(f"{command} 2>&1").read()
    except Exception as e:
        return f"Error executing command: {str(e)}"

    

# ============= Tool Definition (JSON Schema) =============

tools = [
    {
        "type": "function",
        "function": {
            "name": "read_file",
            "description": "读取指定文件的完整内容。适用于查看文本文件、配置文件、代码文件等。",
            "parameters": {
                "type": "object",
                "properties": {
                    "file_path": {
                        "type": "string",
                        "description": "文件的路径（绝对路径或相对路径）"
                    }
                },
                "required": ["file_path"],
                "additionalProperties": False
            }
        }
    },


    #Todo:
    {
        "type": "function",
        "function": {
            "name": "write_file",
            "description": "将指定内容写入到文件中。适用于保存文本文件、配置文件、代码文件等。",
            "parameters": {
                "type": "object",
                "properties": {
                    "file_path": {
                        "type": "string",
                        "description": "文件的路径（绝对路径或相对路径）"
                    },
                    "content": {
                        "type": "string",
                        "description": "要写入文件的内容"
                    }
                },
                "required": ["file_path", "content"],
                "additionalProperties": False
            }
        }
    },
    {
        "type": "function",
        "function": {
            "name": "command_exec",
            "description": "执行指定的命令行指令并返回输出结果。",
            "parameters": {
                "type": "object",
                "properties": {
                    "command": {
                        "type": "string",
                        "description": f"要执行的命令行指令。当前操作系统类型为：{os.name}。当命令中包含任何类型的路径时，必须使用引号括起路径。"
                    },
                    "confirmation": {
                        "type": "boolean",
                        "description": "是否需要用户确认后再执行命令。涉及系统变更的命令必须得到用户的确认。"
                    }
                },
                "required": ["command", "confirmation"],
                "additionalProperties": False
            }
        }
    },
]



# ============= Tool Map =============

available_functions = {
    "read_file": read_file,
    
    #Todo:
    "write_file": write_file,
    "command_exec": command_exec,
}



# ============= Function Calling Agent =============
class Agent:
    def __init__(self, model: str = "gemini-2.5-flash", verbose: bool = True):
        self.model = model
        self.verbose = verbose
        self.tools = tools
        self.available_functions = available_functions

    def run(self, user_query: str, max_iterations: int = 15) -> Dict[str, Any]:
        messages = [{"role": "user", "content": user_query}]
        total_tokens = 0
        tool_calls_count = 0

        if self.verbose:
            print(f"\n{'='*60}")
            print(f"🤖 Function Calling Agent")
            print(f"{'='*60}")
            print(f"📝 User Query: {user_query}\n")

        for iteration in range(max_iterations):
            if self.verbose:
                print(f"--- Iteration {iteration + 1} ---")

            response = client.chat.completions.create(
                model=self.model,
                messages=messages,
                tools=self.tools,
                tool_choice="auto"
            )

            total_tokens += response.usage.total_tokens
            assistant_message = response.choices[0].message

            if not assistant_message.tool_calls:
                final_response = assistant_message.content
                if self.verbose:
                    print(f"\n✅ Final Response:\n{final_response}")
                    print(f"\n📊 Statistics:")
                    print(f"   - Total tokens used: {total_tokens}")
                    print(f"   - Tool calls made: {tool_calls_count}")
                    print(f"   - Iterations: {iteration + 1}")

                return {
                    "success": True,
                    "response": final_response,
                    "tokens": total_tokens,
                    "tool_calls": tool_calls_count,
                    "iterations": iteration + 1
                }

            messages.append(assistant_message)

            for tool_call in assistant_message.tool_calls:
                tool_calls_count += 1
                function_name = tool_call.function.name
                function_args = json.loads(tool_call.function.arguments)

                if self.verbose:
                    print(f"\n🔧 Tool Call #{tool_calls_count}:")
                    print(f"   Function: {function_name}")
                    print(f"   Arguments: {json.dumps(function_args, ensure_ascii=False)}")

                function_response = self.available_functions[function_name](**function_args)

                if self.verbose:
                    display_response = function_response[:200] + "..." if len(function_response) > 200 else function_response
                    print(f"   Result: {display_response}")

                messages.append({
                    "role": "tool",
                    "tool_call_id": tool_call.id,
                    "content": function_response
                })

        if self.verbose:
            print(f"\n⚠️  Reached maximum iterations ({max_iterations})")

        return {
            "success": False,
            "response": "Maximum iterations reached without completion",
            "tokens": total_tokens,
            "tool_calls": tool_calls_count,
            "iterations": max_iterations
        }

# ============= Test =============
def main():
    agent = Agent(verbose=True)

    # 测试点 1: 简单文件读取与写入
    print("\n" + "="*60)
    print("Test case 1: 简单文件读取与写入")
    print("="*60)
    agent.run("阅读./Sample code/Test1.py的内容,将其修改为打印10个数字")

    # 测试点 2: 列出目录内容
    print("\n" + "="*60)
    print("Test case 2: 列出目录内容")
    print("="*60)
    agent.run("列出Sample code目录下的所有文件")

    # 测试点 3: 执行命令行指令
    print("\n" + "="*60)
    print("Test case 3: 执行命令行指令")
    print("="*60)
    agent.run("新建一个叫做Test的文件夹，并在新建的Test文件夹下新建一个Code.py文件")

    # 测试点 4: 修复bug
    print("\n" + "="*60)
    print("Test case 4: 修复bug")
    print("="*60)
    agent.run("./Sample code/Test3.py存在明显的语法错误，请找出问题并修复")

    # 测试点 5: 运行python文件并查看运行结果
    print("\n" + "="*60)
    print("Test case 5: 运行python文件并查看运行结果")
    print("="*60)
    agent.run("./Sample code/Test4.py是一个用于打印 1~100 中所有的素数并统计个数的python程序，请运行并检查其结果是否正确")

if __name__ == "__main__":
    main()
