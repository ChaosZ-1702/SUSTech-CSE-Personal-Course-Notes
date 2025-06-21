# Piece 6-1
.data
    m1: .byte 8       # multiplicand
    m2: .byte 9       # multiplier
.text
    lb t0, m1
    lb t1, m2
    add t2, zero, zero
loop:
    li s1, 1
    and s2, s1, t1    # to determine the lowest bit of t1
    beq s2, zero, jumpAdd
    add t2, t0, t2
jumpAdd:
    slli t0, t0, 1
    srli t1, t1, 1
    addi a0, a0,1
    li a1, 4          # 4 is the length of 9 in binary
    blt a0, a1, loop

    mv a0, t2
    li a7, 1
    ecall

    li a7, 35
    ecall
