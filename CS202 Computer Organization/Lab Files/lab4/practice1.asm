.data
    str1:  .asciz "\nThe "
    str2:  .asciz "th number in fibonacci sequence is: "
.text
    li a7, 5  # input an integer
    ecall
    
    mv t0, a0
    
    la a0, str1
    li a7, 4
    ecall
    
    mv a0, t0
    li a7, 1
    ecall
    
    la a0, str2
    li a7, 4
    ecall
    
    mv a0, t0
    li t0, 1
    jal fibonacci
    
    # print the result
    li a7, 1  # print an integer
    ecall
    
    li a7, 10
    ecall

fibonacci:
    addi sp, sp, -12
    sw ra, 8(sp)
    sw s0, 4(sp)
    sw a0, 0(sp)  # store original m
    
    ble a0, t0, base  # if so, go to base case
    
    # recursive case
    addi a0, a0, -1
    jal ra, fibonacci  # calculate (m-1) case
    
    mv s0, a0
    
    lw a0, 0(sp)  # restore to original m and use it to calculate (m-2) case
    addi a0, a0, -2
    jal ra, fibonacci
    
    add a0, a0, s0  # F(n) = F(n-1) + F(n-2)
    
    lw ra, 8(sp)
    lw s0, 4(sp)
    addi sp, sp, 12
    jr ra
base:
	addi a0, zero, 1
	addi sp, sp, 12
	jr ra
