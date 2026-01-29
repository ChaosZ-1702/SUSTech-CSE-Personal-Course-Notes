def calculate_sum(a, b):
    result = a + b
    return result  


def greet(name):
    message = "Hello, " + name + "!"
    print(message)
          
def check_number(num):
    if num > 0:
        print("正数")
    elif num < 0:
        print("负数")
    else:
        print("零")

def main():
    result = calculate_sum(1, 2)
    print(result)

    name = 'student'
    greet(name)

    num = 3
    check_number(num)



if __name__ == "__main__":
    main()