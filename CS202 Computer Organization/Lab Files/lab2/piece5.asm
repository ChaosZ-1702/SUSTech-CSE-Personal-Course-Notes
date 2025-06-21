# Piece 2-5
.data
	a: .word 0x12345678
	b: .word 0x9abcdef0
.text
main:
	lw t0, a # load word
	lw t1, b
	la t2, a
	
	sb t0, 8(t2) # store byte
	sb t1, 9(t2)
	
	sw t0, 10(t2) # store word
	sw t1, 14(t2)
	
	sh t0, 18(t2) # store halfword
	sh t1, 20(t2)
	
	li a7,10 # to exit
	ecall
