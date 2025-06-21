.include "macro_print_str.asm"
.data
.text
main:
 	print_string("Please input an integer: \n")
 	li a7, 5
 	ecall
 	
 	li t1, 1  # li -> addi
 	mv t0, a0 # mv -> add
 	and a0, t1, t0  # edit: a0 -> t2
 	
 	print_string("It is an odd number (0: false, 1: true): ")
 	#mv a0, t2  # edit: t0 -> t2
 	li a7, 1
 	ecall
 
 	end