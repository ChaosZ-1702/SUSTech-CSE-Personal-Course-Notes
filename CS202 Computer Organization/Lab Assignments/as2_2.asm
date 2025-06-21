.data
.text
.globl main
main:
	li a7, 7
	ecall
	fmv.d ft0, fa0  # ft0 = a
	
	li a7, 7
	ecall
	fmv.d ft1, fa0  # ft1 = x1
	
	li a7, 7
	ecall
	fmv.d ft2, fa0  # ft2 = x2
	
	li t0, 2
	fcvt.d.w fs0, t0  # fs0 = 2
	li t0, 1
	fcvt.d.w ft4, t0  # ft4 = 1
	li t0, 1000000
	fcvt.d.w ft5, t0  # ft5 = 1e6
	fdiv.d ft4, ft4, ft5  # ft4 = 1e-6
	li t0, 0
	fcvt.d.w ft6, t0  # ft6 = 0
	jal iterator  # fs1 = x0
	
	fmv.d fa0, fs1
	li a7, 3
	ecall
	
	li a7, 10
	ecall
	
exit:
	jr ra
	
iterator:
	fadd.d ft3, ft1, ft2  # fa0 = ft1 + ft2
	fdiv.d ft3, ft3, fs0  # ft3 = x3 = (ft1 + ft2) / 2
	fmv.d fs1, ft3  # fs1 = x3
	fmul.d ft3, ft3, ft3  # ft3 = x3^2
	fsub.d ft3, ft3, ft0  # ft3 = f(x3) = x3^2 - a
	fmv.d fs2, ft3  # fs2 = f(x3)
	fabs.d ft3, ft3  # ft3 = |f(x3)|
	
	flt.d t0, ft3, ft4  # if |f(x3)| < 1e-6, t0 = 1
	bnez t0, exit  # if t0 = 1, exit
	
	fmul.d ft5, ft1, ft1  # ft5 = x1^2
	fsub.d ft5, ft5, ft0  # ft5 = f(x1) = x1^2 - a
	fmul.d ft5, fs2, ft5  # ft5 = f(x3) * f(x1)
	
	flt.d t0, ft5, ft6  # if f(x3) * f(x1) < 0, t0 = 1
	beqz t0, update_x1  # if t0 = 0, i.e. f(x3) * f(x1) >= 0, x1 = x3
	fmv.d ft2, fs1
	jal x0, iterator
	
update_x1:
	fmv.d ft1, fs1
	jal x0, iterator
