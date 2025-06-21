# Piece 2-1
.data
	str: .ascii "Welcome "
	startpoint: .space 9
.text
main:
	li a7,8 # to get a string
	la a0,startpoint
	li a1,10
	ecall
	
	li a7,4 # to print a string
	la a0,str
	ecall
	
	li a7,10 # to exit
	ecall