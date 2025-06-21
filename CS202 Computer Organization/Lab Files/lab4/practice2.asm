.include "macro_print_str.asm"               
.data
    min_value: .word 0
.text
    print_string("Please input the number:")
    li a7, 5  # read an integer
    ecall
    mv t0, a0  # t0 is the number of integers

    slli a0, t0, 2  # new a heap with 4*t0
    li a7, 9  # a0 is both used as argument and return value
    ecall
    mv t1, a0  # t1 is the start of the heap
    mv t2, a0  # t2 is the pointer
    print_string("Please input the array.\n")
    add t3, zero, zero  # set t3 as i

loop_read:                   
    li a7, 5  # read the array
    ecall
    sw a0, (t2)  
    addi t2, t2, 4
    addi t3, t3, 1
    beq t3, t0, loop_read

    lw a0, (t1)  # initialize the min_value
    la t4, min_value
    sw a0, (t4)
    add t3, zero, zero
    add t2, t1, zero  # t1 is the start of the heap

loop_find_min:
    lw a0, min_value
    lw a1, (t2)
    jal find_min
    la t4, min_value
    sw a0, (t4)
    addi t2, t2, 4  # t2 is the pointer
    addi t3, t3, 1
    bne t3, t0, loop_find_min  # t0 is the number of integers

    print_string("the min value is: ")
    li a7, 1
    la t4, min_value
    lw a0, (t4)
    ecall

    end

find_min:
    blt a0, a1, not_update
    mv a0, a1

not_update:
    jr ra
