.data
    text: .space 2147483647
.text
.globl main
main:
    la a0, text
    li a1, 2147483647
    li a7, 8
    ecall

    li a7, 4
    ecall

    li a7, 10
    ecall