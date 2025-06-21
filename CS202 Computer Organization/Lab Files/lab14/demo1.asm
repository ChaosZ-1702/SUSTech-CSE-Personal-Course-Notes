.data
# 32*2 word (rows: 2, columns: 32)
matrix: .space 256

.macro getindex(%ans, %i, %j)
    slli %ans, %i, 5  # complete here
    add %ans, %ans, %j
    slli %ans, %ans, 2  # complete here
.end_macro

.text
    addi  t0, x0, 0  # i
    addi  s0, x0, 2
    addi  t1, x0, 0  # j
    addi s1, x0, 32
    la t6, matrix
    # 0,0 -> 1,0 -> 0,1 ...
loopi:    
    beq t0, s0, loopiend
    addi t1, x0, 0  
loopj:
    beq t1, s1, loopjend
    getindex(a0, t0, t1)
    # complete here
    add t5, t6, a0  # addr = matrix + [i][j]
    sw a0, 0(t5)
    addi t1, t1, 1
    j loopj
loopjend:
    addi t0, t0, 1
    j loopi
loopiend:
    li a7, 10
    ecall
