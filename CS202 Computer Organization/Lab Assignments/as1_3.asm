.data
	array: .space 200
	br: .ascii "\n"
.text
.globl main
main:
	li a7, 5
	ecall
	
	# input the array
	mv t0, a0
	addi sp, sp, -4
	sw a0, 0(sp)
	la t1, array
	jal input_loop
	
	# bubble sort
	lw t0, 0(sp)
	addi t0, t0, -1  # i
	la t1, array
	jal bubble_sort_outer
	
	# print the sorted loop
	lw t0, 0(sp)
	la t1, array
	jal print_loop
	
	li a7, 10
	ecall
	
input_loop:
	li a7, 5
	ecall
	sw a0, (t1)
	
	addi t0, t0, -1
	addi t1, t1, 4
	beq t0, zero, exit
	jal x0, input_loop
	
print_loop:
	lw a0, (t1)
	li a7, 1
	ecall
	
	addi t0, t0, -1
	addi t1, t1, 4
	beq t0, zero, exit
	
	la a0, br
	li a7, 4
	ecall
	jal x0, print_loop
	
exit:
	jr ra
	
bubble_sort_outer:
	beq t0, zero, exit
	li a0, 0  # j
	li a1, 0  # isSwaped
	
bubble_sort_inner:
	slli a2, a0, 2  # j * 4
	add a2, t1, a2  # &array[j]
	lw a3, 0(a2)  # array[j]
	lw a4, 4(a2)  # array[j + 1]
	
	# if array[j] > array[j + 1] then swap
	ble a3, a4, continue
	sw a3, 4(a2)
	sw a4, 0(a2)
	addi a1, a1, 1
	
continue:
	addi a0, a0, 1
	blt a0, t0, bubble_sort_inner
	beq a1, zero, exit  # if no swap, exit immadiately
	addi t0, t0, -1
	jal x0, bubble_sort_outer
	
