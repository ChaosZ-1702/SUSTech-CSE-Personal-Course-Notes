# Piece 6-1
.data
	# 16bit: -32768 ~ 32767
    m1: .half -108       # 16-bit multiplicand
    m2: .half 108       # 16-bit multiplier
.text
    lh t0, m1
    lh t1, m2
    add t2, zero, zero
    
    bgez t0, neg_m2   # if m1 > 0, skip process m1
    not t0, t0
    addi t0, t0, 1    # take neg of m1
    li a0, 1
    
neg_m2:
	bgez t1, start    # if m2 > 0, skip process m2
	not t1, t1
	addi t1, t1, 1    # take neg of m2
	li a1, 1
	
start:
	xor s0, a0, a1
	li a0, 0
	li a1, 16

loop:
    li s1, 1
    and s2, s1, t1    # to determine the lowest bit of t1
    beq s2, zero, jumpAdd
    add t2, t0, t2

jumpAdd:
    slli t0, t0, 1
    srli t1, t1, 1
    addi a0, a0, 1
    blt a0, a1, loop
    beqz s0, end
    not t2, t2
    addi t2, t2, 1
	
end:
    mv a0, t2
    li a7, 1
    ecall
    
    li a7, 11                 # System call 11: print character
	li a0, 10                 # ASCII code 10 corresponds to newline '\n'
	ecall

    li a7, 35
    ecall
    
    li a7, 10
    ecall