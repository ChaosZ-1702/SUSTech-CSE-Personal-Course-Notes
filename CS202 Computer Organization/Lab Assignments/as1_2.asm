.data
.text
.globl main
main:
	li a7, 5
	ecall
	mv a1, a0  # n in a1, k in a0
	li a7, 5
	ecall
	
	jal combination
	
	li a7, 1
	ecall
	
	li a7, 10
	ecall
	
combination:
	addi sp, sp, -16
	sw ra, 12(sp)
	sw s0, 8(sp)
	sw a1, 4(sp)
	sw a0, 0(sp)
	
	# if (k == 0 || k == n) return 1;
	beq a0, zero, done
	beq a0, a1, done
	
	# combination(n - 1, k - 1)
	addi a1, a1, -1
	addi a0, a0, -1
	jal combination
	mv s0, a0
	
	# combination(n - 1, k)
	lw a1, 4(sp)
	lw a0, 0(sp)
	addi a1, a1, -1
	jal combination
	
	# return (n - 1, k - 1) + (n - 1, k)
	add a0, a0, s0
	lw ra, 12(sp)
	lw s0, 8(sp)
	addi sp, sp, 16
	jr ra
	
done:
	addi a0, zero, 1
	addi sp, sp, 16
	jr ra