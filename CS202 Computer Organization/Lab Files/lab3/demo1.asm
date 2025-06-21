.include "macro_print_str.asm"
.data
	arrayx: .space 40  # 0x10010000, 10 -> 40
	str: .asciz "\nThe arrayx is: "  # 0x1001000a
.text
main:
 	print_string("Please input 10 integers: \n")
 	add t0, zero, zero
 	addi t1, zero, 10
 	la t2, arrayx  # Set the value of t2 to the address of arrayx
loop_r:
	li a7, 5  # Read an int from the input console
	ecall
 	sw a0, (t2)  # Store the int into the address which t2 represents
 	addi t0, t0, 1
 	addi t2, t2, 4
 	bne t0, t1, loop_r
 	
 	la a0, str  # The inputs overwrites the content of str...
 	li a7, 4  # Print a string to the console
 	ecall
 	#print_string("\nThe arrayx is: ")
 	addi t0, zero, 0
 	la t2, arrayx
loop_w:
 	lw a0, (t2)
 	li a7, 1  # Print an integer
 	ecall
 	print_string(" ")
 	addi t2, t2, 4
 	addi t0, t0, 1
 	bne t0, t1, loop_w
 	
 	end