## "print_caller.asm" ##
.include "print_callee.asm"
.data
    str_caller: .asciz "It's in print_caller.\n"
.text
.globl main
main: 
    jal print_callee

    li a7, 4 
    la a0, str_caller
    ecall
    la a0, default_str  ### Which one?
    ecall
    
    li a7,10
    ecall