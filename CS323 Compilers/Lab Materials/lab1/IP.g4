grammar IP;

expression: Number Dot Number Dot Number Dot Number;

Dot : '.' ;
// 000 - 255 numbers
Number
    : '000'
    | '00'[1-9]
    | '0'[1-9][0-9]
    | '1'[0-9][0-9]
    | '2'[0-4][0-9]
    | '25'[0-5]
    ;
