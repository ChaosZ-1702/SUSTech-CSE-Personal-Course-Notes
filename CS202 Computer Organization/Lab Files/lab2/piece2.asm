# Piece 2-2
.data
	a: .word 0x12345678
	b: .word 0x9abcdef0
.text
main:
	lw t0, a # load word
	lw t1, b # requires multiplication of 4
	
	lb t0, a # load byte
	lb t1, b # accessing every byte is available
	
	lbu t0, a # load byte
	lbu t1, b
	
	lh t0, a # load halfword
	lh t1, b # requires multiplication of 2
