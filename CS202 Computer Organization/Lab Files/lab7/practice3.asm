.data
.text
	li a7, 7
	ecall
	fmv.d ft0, fa0  # ft0 = precision
	
	li t0, 1
	fcvt.d.w ft1, t0  # ft1 = 1
	li t0, 0  # t0 = n
	# ft2 = pre, ft3 = cur
	jal calculate_e  # result store in ft2
	
	fmv.d fa0, ft2
	li a7, 3
	ecall
	
	li a7, 10
	ecall
	
exit:
	jr ra
	
calculate_e:
	addi sp, sp, -4
	sw ra, 0(sp)
	li t1, 1  # index
	li t2, 1  # result
	jal factorial
	lw ra, 0(sp)
	addi sp, sp, 4
	
	fcvt.d.w ft3, t2  # ft3 = factorial result
	fdiv.d ft3, ft1, ft3  # ft3 = addition in this iteration
	fadd.d ft2, ft2, ft3  # update ft2
	flt.d t1, ft3, ft0  # if diff < prec, t1 = 1; else t1 = 0
	addi t0, t0, 1
	beqz t1, calculate_e  # if t1 = 0, still need to calculate
	jr ra
	
factorial:
	beqz t0, exit
	
	mul t2, t1, t2  # t2 = index * result
	addi t1, t1, 1
	ble t1, t0, factorial
	jr ra