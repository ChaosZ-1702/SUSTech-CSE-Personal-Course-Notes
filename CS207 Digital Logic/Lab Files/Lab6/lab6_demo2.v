`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2023/10/20 20:04:25
// Design Name: 
// Module Name: lab6_p3
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module lab6_demo2(
input [1:0] a, b,
output tub_sel,
output [7:0] tub_control 
    );
    wire [2:0] sum;
    wire [3:0] in_b4;
    assign in_b4 = {1'b0,sum};
    lab3_practic_add2bit adder_u1(.a(a), .b(b), .sum(sum));
    lab6_demo1 BCD2d_u2(.in_b4(in_b4), .tub_sel(tub_sel),. tub_control(tub_control));
endmodule
