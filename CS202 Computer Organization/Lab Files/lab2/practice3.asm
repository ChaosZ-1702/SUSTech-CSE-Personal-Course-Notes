# Piece 2-11
.data
	a: .word 0x1111
	b: .word 0x5555
.text
main:
	lw t0, b # get data from memory to register
	addi t1, t0, 1
	la t2, a
	sw t1, 0(t2) # get data from register to memory
	
	li a7,10 # to exit
	ecall