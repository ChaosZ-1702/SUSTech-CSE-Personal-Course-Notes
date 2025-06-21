# Piece 2-4
.data
	str:	   .ascii "Welcome "
	startpoint:.space 9
	a:	   .word 0x12345678
	b:	   .word 0x9abcdef0
.text
main:
	# ......
	
	li a7,10 # to exit
	ecall