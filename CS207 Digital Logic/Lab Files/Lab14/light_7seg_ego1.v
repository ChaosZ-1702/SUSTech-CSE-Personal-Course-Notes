`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/11/26 21:45:42
// Design Name: 
// Module Name: light_7seg_ego1
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


module light_7seg_ego1(
input [3:0] sw, output reg[7:0] seg_out, output [7:0] seg_en
    );
    assign seg_en = 8'hff;
    always@*
        case(sw)
            4'h0: seg_out = 8'b1111_1100;
            4'h1: seg_out = 8'b0110_0000;
            4'h2: seg_out = 8'b1101_1010;
            4'h3: seg_out = 8'b1111_0010;
            4'h4: seg_out = 8'b0110_0110;
            4'h5: seg_out = 8'b1011_0110;
            4'h6: seg_out = 8'b1011_1110;
            4'h7: seg_out = 8'b1110_0000;
            default: seg_out = 8'b0000_0000;
        endcase
endmodule
