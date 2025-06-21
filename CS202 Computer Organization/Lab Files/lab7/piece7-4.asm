.include "macro_print_str.asm"
.data
    float1: .float 12.625
    float2: .float 0.5
.text
    la t0, float1
    flw ft0, (t0)
    la t0, float2
    flw ft1, (t0)

    print_string("Orignal float: ")
    print_float(ft0)

    print_string("\nAfter floor:")
    # floor operation
    fsub.s ft2, ft0, ft1  # ft2 = ft0 - 0.5
    # convert the result to a 32-bit integer
    fcvt.w.s a0, ft2 # a0 = (int32_t)ft2
    li a7, 1
    ecall

    print_string("\nAfter ceil:")
    # ceil operation
    fadd.s ft2, ft0, ft1  # ft2 = ft0 + 0.5  
    # convert the result to a 32-bit integer
    fcvt.w.s a0, ft2  # a0 = (int32_t)ft2
    li a7, 1
    ecall

    print_string("\nAfter round:")
    # round operation
    fcvt.w.s a0, ft0  # a0 = (int32_t)ft0
    li a7, 1
    ecall

    end