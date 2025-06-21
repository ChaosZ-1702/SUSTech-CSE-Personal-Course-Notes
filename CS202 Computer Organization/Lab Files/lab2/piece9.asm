# Piece 2-9
.include "macro_print_str.asm"
.data
	tdata: .byte 0x80
.text
main:
	lbu a0, tdata
	li a7, 1
	ecall
	
	print_string("\n")
	lbu a0, tdata
	li a7, 36
	ecall
	
	end
