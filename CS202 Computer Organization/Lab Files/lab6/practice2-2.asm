# Piece 6-3
.include "macro_print_str.asm"
.data
    dividend: .word -7
    divisor:  .word 2
    x: .word 0x80000000
    looptimes: .byte 5
.text
    la t5, dividend
    lw t1, (t5)  # t1 : dividend
    la t5, divisor
    lw t2, (t5)  # t2 : divisor
    beqz t2, zero_error
    
    bgez t1, neg_divisor   # if dividend > 0, skip process m1
    not t1, t1
    addi t1, t1, 1    # take neg of m1
    li a0, 1
    
neg_divisor:
	bgez t2, start
	not t2, t2
	addi t2, t2, 1    # take neg of m2
	li a1, 1
	
start:
	xor s0, a0, a1
    
    slli t2, t2, 4
    la t5, dividend
    lw t3, (t5)  # t3 store the remainder
    add t4, zero, zero     # t4 quotient
    
    la t5, x
    lw a1, (t5)            # a1 used to get the highest bit of remainder
    add t0, zero, zero     # t0: loop cnt
    la t5, looptimes
    lb s1, (t5)  # s1: loop times
loopb:
    # t1: dividend,   t2: divisor,    t3: remainder,      t4: quotient
    # a1: 0x80000000, s1: 5

    sub t3, t3, t2     # dividend - divisor
    and s0, t3, a1     # get the highest bit of remainder to check if rem<0
    slli t4, t4, 1     # shift left quot with 1bit
    beq s0, zero, SdrUq # if rem>=0, shift Div right
    add t3, t3, t2     # if rem<0, rem=rem+div
    srli t2, t2, 1
    addi t4, t4, 0
    j loope

SdrUq:
    srli t2, t2, 1
    addi t4, t4, 1

loope:
    addi t0, t0, 1
    bne t0, s1, loopb
    beqz s0, end
    not t4, t4
    addi t4, t4, 1
    not t3, t3
    addi t3, t3, 1

end:
    li a7, 1
    mv a0, t4  # print quotient
    ecall
    print_string("\n")
    li a7, 1
    mv a0, t3  # print remainder
    ecall

    li a7, 10
    ecall

zero_error:
    print_string("The divisor is 0!")
    li a7, 10
    ecall
