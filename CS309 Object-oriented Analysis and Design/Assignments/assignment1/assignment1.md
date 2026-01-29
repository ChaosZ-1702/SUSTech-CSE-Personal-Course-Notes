# Assignment 1: Nano Claude Code Implementation

In this assignment, you will build a simplified intelligent programming assistant Agent that simulates the core functionality of Claude Code. This assistant needs to understand user instructions, autonomously plan task execution workflows, and complete various programming-related operations. You will need to implement specific tool functions and define corresponding tool descriptions (JSON Schema) to equip the Agent with complete functionality.

## Functional Requirements

The Nano Claude Code intelligent assistant must possess the following core functions:

- **File Reading**: Ability to read and parse the contents of specified files
- **File Writing**: Ability to create new files or modify existing file content
- **Directory File Listing**: Ability to scan and list all files and subdirectories within a specified directory
- **Command Line Instruction Execution**: Ability to execute system commands, but all instructions involving system changes must obtain user confirmation before execution
- **Python Program Execution**: Ability to run Python code and capture and display execution results

## Test Cases

We provide a series of test cases to verify the implementation of your various functions:

1. **Test1.py** - Basic File Operations Test  
   Process file read and write tasks through the Agent to verify the correctness of file reading and writing functions

2. **Directory Structure Scanning Test**  
   Require the Agent to list all files in the Sample Code directory to verify the implementation of directory traversal functionality

3. **System Operations Test**  
   Create new folders and files to verify command line instruction execution functionality and user confirmation mechanism

4. **Test3.py** - Code Debugging Capability Test  
   Contains three Python codes with obvious syntax errors to verify whether the Agent can autonomously identify issues and perform fixes

5. **Test4.py** - Program Execution Test  
   Implement functionality to print all prime numbers in the range 1~100 and count them to verify the Agent's ability to run Python programs and obtain results

You can try some customized cases to verify your implementation

## Task List

The overall framework of Nano Claude Code has been provided. You need to complete the following specific tasks:

1. **Tool Design** - Autonomously design the required toolset based on functional requirements
2. **Function Implementation** - Complete the specific code implementation of each tool function
3. **Interface Definition** - Write corresponding tool definition descriptions (JSON Schema)
4. **Mapping Association** - Establish corresponding relationships between tool functions and tool definitions to complete system integration

Please ensure your implementation has good error handling mechanisms and user interaction experience, especially when performing operations that may affect the system, strictly adhering to the user confirmation principle.