## "print_caller_e.asm" ##
#.include "print_callee_e.asm"
.data
    str_caller: .asciz "It's in print_caller.\n"
    data1: .word 0x64636261 #abcd
.text
.globl main
main: 
    jal print_callee
    
    la a1, data1
    lw a0, (a1)
    la a1, default_str
    sw a0, (a1)
    li a7, 4 
    la a0, str_caller
    ecall
    la a0, default_str
    ecall

    jal print_callee

    li a7, 10
    ecall
