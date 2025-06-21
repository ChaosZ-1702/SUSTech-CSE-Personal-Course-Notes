## "print_callee_e.asm" ##
.data
    .extern       default_str 20 
    str_callee:   .asciz "It's in print_callee.\n"
    default_str:  .asciz "ABCD\n"
.text
.globl print_callee
print_callee: 
    addi sp, sp, -4
    sw a0, (sp)
    
    li a7, 4 
    la a0, str_callee
    ecall
    la a0, default_str
    ecall

    lw a0, (sp)
    addi sp, sp, 4
    jr ra
