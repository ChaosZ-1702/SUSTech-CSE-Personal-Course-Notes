.data
	array: .space 800
.text
.globl main
main:
	li a7, 7
	ecall
	fmv.d ft0, fa0  # ft0 = k
	
	fcvt.w.d t0, ft0
	la t1, array
	jal input_loop  # ft1 = sum
	
	fdiv.d ft1, ft1, ft0  # ft1 = mean
	
	fcvt.w.d t0, ft0
	la t1, array
	jal variance  # ft3 = ?
	
	fdiv.d ft3, ft3, ft0  # ft3 = variance
	
	fmv.d fa0, ft1
	li a7, 3
	ecall
	li a7, 11                 # System call 11: print character
	li a0, 10                 # ASCII code 10 corresponds to newline '\n'
	ecall
	
	fmv.d fa0, ft3
	li a7, 3
	ecall
	li a7, 11
	li a0, 10
	ecall
	
	li a7, 10
	ecall
	
exit:
	jr ra
	
input_loop:
	li a7, 7
	ecall
	fsd fa0, (t1)
	fadd.d ft1, ft1, fa0
	
	addi t0, t0, -1
	addi t1, t1, 8
	beq t0, zero, exit
	jal x0, input_loop
	
variance:
	fld ft2, (t1)  # ft2 = xi
	fsub.d ft2, ft2, ft1  # ft2 = xi - mean
	fmul.d ft2, ft2, ft2  # ft2 = (xi - mean)^2
	fadd.d ft3, ft3, ft2  # ft3 += ft2
	
	addi t0, t0, -1
	addi t1, t1, 8
	beq t0, zero, exit
	jal x0, variance
	