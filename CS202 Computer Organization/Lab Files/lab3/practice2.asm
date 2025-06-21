.include "macro_print_str.asm"
.data
.text
main:
 	print_string("Please input a character: \n")
 	li a7, 12
 	ecall
 	mv t0, a0
	li t1, 0
	li t2, 7
loop:
	mv t3, t0
	srl t3, t3, t1
	andi t3, t3, 1
	
	mv t4, t0
	srl t4, t4, t2
	andi t4, t4, 1
	
	bne t3, t4, no
	
	addi t1, t1, 1
	addi t2, t2, -1
	blt t1, t2, loop
	
	print_string("\nyes")
	end
	
no:
	print_string("\nno")
	end