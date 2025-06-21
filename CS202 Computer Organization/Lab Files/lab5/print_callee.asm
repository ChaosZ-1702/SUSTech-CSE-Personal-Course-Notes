## "print_callee.asm" ##
.data
    .extern default_str 20
    default_str: .asciz "It's the default string in data seg\n" 
    str_callee:  .asciz "It's in print_callee.\n"
.text
print_callee: 
    li a7, 4 
    la a0, str_callee
    ecall
    la a0, default_str  ### Which one?
    ecall

    jr ra