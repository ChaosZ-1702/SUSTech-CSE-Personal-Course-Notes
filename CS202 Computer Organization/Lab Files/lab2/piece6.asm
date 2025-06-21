# Piece 2-6
.include "macro_print_str.asm"
.data
.text
main:
	print_string("\n -1 is less than 1 using slt (1 for yes and 0 for no): ")
	li t0,-1
	li t1,1
	slt a0,t0,t1
	li a7,1
	ecall

	print_string("\n -1 is less than 1 using sltu (1 for yes and 0 for no): ")
	sltu a0,t0,t1
	li a7,1
	ecall

	end
