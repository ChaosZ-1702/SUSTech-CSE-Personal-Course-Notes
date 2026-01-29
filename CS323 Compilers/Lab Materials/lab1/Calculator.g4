grammar Calculator;

expression: term ((ADD|SUB) term)* ;

term: INT ;

INT : [0-9]+ ;
ADD : '+' ;
SUB : '-' ;
WS  : [ \t\r\n]+ -> skip ;

