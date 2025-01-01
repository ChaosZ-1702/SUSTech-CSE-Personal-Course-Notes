`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/09/29 09:04:44
// Design Name: 
// Module Name: lab3_adder_2bit
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


module lab3_practic_add2bit(
input [2:0] a,b,
output reg [3:0] sum
    );
   
  //reg [2:0]sum;
    always@* 
    sum = a+b;
endmodule
