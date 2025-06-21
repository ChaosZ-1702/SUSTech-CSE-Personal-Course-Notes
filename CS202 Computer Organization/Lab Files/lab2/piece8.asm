# Piece 2-8
.include "macro_print_str.asm"
.data
	tdata: .byte 0x80
.text
main:
	lb a0, tdata
	li a7, 1
	ecall
	
	print_string("\n")
	lb a0, tdata
	li a7, 36
	ecall
	
	end