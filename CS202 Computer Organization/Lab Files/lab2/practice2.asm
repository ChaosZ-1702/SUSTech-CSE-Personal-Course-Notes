.data
	name:	.space 16	 # malloc 16 bytes, not initialize
	mick:	.ascii "Mick\n"	 # malloc 4+1 = 5 bytes
	alice:	.asciz "Alice\n" ##### What's the value of alice?
	tony:	.asciz "Tony\n"	 ##### What's the value of tony?
	chen:	.asciz "Chen\n"
.text
.main:
	la t0, name
	la t1, mick
	sw t1, (t0) # get the value of t0; use it as the address of a piece of memory
	la t1, alice
	sw t1, 4(t0) # baseline: the content of t0; offset: 4
	la t1, tony
	sw t1, 8(t0)
	la t1, chen
	sw t1, 12(t0)
	
	li a7, 4
	lw a0, 0(t0)
	ecall # What's the output while this system call is done?
	
	li a7, 10
	ecall
