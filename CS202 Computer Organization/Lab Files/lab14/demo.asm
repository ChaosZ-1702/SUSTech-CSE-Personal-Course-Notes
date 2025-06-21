.data
    blk0: .word 1:32
    blk1: .word 0:32
.text
    add t0,x0,x0
    add s0,x0,x0
    addi t1,x0,32
    la t5, blk0
    la t6, blk1
loop:
    lw t2,0(t5)
    add t2,t2,t0
    sll t2,t2,s0
    sw t2,0(t6)
    addi t0,t0,4
    addi t5,t5,4
    addi t6,t6,4
    addi s0,s0,1
    bne s0,t1,loop
    li a7,10
    ecall