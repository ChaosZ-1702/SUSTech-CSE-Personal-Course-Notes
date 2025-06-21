.data
.text
.globl main
main:
	li a7, 7
	ecall
	fmv.d ft0, fa0  # ft0 = a
	
	li a7, 7
	ecall
	fmv.d fs0, fa0  # fs0 = x0/x_current
	
	li t0, 1
	fcvt.d.w ft1, t0  # ft1 = 1
	li t0, 2
	fcvt.d.w ft2, t0  # ft2 = 2
	li t0, 1000000
	fcvt.d.w ft3, t0  # ft3 = 1e6
	fdiv.d ft1, ft1, ft3  # ft1 = 1e-6
	li t0, 0
	jal iterator  # fs1 = x_next
	
	fmv.d fa0, fs1
	li a7, 3
	ecall
	
	li a7, 10
	ecall

exit:
	jr ra
	
iterator:
	fdiv.d fs1, ft0, fs0  # fs1 = a / x_current
	fadd.d fs1, fs1, fs0  # fs1 = x_current + a / x_current
	fdiv.d fs1, fs1, ft2  # fs1 = (fs1 = x_current + a / x_current) / 2 = x_next
	fsub.d ft3, fs1, fs0  # ft3 = x_next - x_current
	fabs.d ft3, ft3  # ft3 = |x_next - x_current|
	
	flt.d t0, ft3, ft1  # if |x_next - x_current| < 1e-6, t0 = 1
	bnez t0, exit  # if t0 = 1, then exit
	
	fmv.d fs0, fs1
	jal x0, iterator
