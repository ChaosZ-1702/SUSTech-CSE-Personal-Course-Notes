# Piece 2-3
.data
	a: .word 0x12345678
	b: .word 0x9abcdef0
.text
main:
	lw t0, a # load word
	lw t1, b
	la t2, a
	
	sw t0, 8(t2) # store word
	sw t1, 12(t2)
	
	li a7,10 # to exit
	ecall