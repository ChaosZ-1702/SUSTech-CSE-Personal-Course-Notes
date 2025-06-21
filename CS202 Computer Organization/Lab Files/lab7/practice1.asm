.data
	i: .float 409.2675
	j: .word 0xc1a6fae1
	k: .float -409.2675
	l: .dword 0xc0611bf1a9fbe76d
.text
	la t0, i
	flw ft0, (t0)
	fmv.x.s a0, ft0
	li a7, 34
	ecall
	li a7, 11                 # System call 11: print character
	li a0, 10                 # ASCII code 10 corresponds to newline '\n'
	ecall
	
	la t0, j
	flw fa0, (t0)
	li a7, 2
	ecall
	li a7, 11                 # System call 11: print character
	li a0, 10                 # ASCII code 10 corresponds to newline '\n'
	ecall
	
	la t0, k
	lw a0, (t0)
	li a7, 34
	ecall
	li a7, 11                 # System call 11: print character
	li a0, 10                 # ASCII code 10 corresponds to newline '\n'
	ecall
	
	la t0, l
	fld fa0, (t0)
	li a7, 3
	ecall
	
	li a7, 10
	ecall
	