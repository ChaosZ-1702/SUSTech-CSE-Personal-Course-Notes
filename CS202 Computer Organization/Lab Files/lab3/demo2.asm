.include "macro_print_str.asm"
.data
 	min: .word 2147483647  # 0 -> 2147483647
 	max: .word -2147483648  # 0 -> -2147483648
.text
 	#lw t0, min
 	#lw t1, max
 	li t3, 4
 	li t4, 1  # 0 -> 1
 	print_string("Please input 5 integer:\n")
 	li a7, 5
 	ecall
 	mv t0, a0  # Use the first number to initiallize the max and min
 	mv t1, a0
loop:
 	li a7, 5
 	ecall
 	bge a0, t1, set_max
 	j set_min
set_max:
 	mv t1, a0
 	j set_min
set_min:
 	bge a0, t0, judge_times
 	mv t0, a0
 	j judge_times
judge_times:
 	addi t4, t4, 1
 	bge t3, t4, loop
 	
 	print_string("\nMin: ")
 	mv a0, t0
 	li a7, 1
 	ecall
 	print_string("\nMax: ")
 	mv a0, t1
 	li a7, 1
 	ecall
 	end