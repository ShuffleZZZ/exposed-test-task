grammar Regex;
@header{ package com.github.shufflezzz.matcher.grammar; }

regex    : concat  ( '|' concat )*;
concat   : closure ( closure )*;

closure  : token ( OPERATOR )*;

token    : '(' regex ')'
         | '[' range ']'
         | char
         ;

OPERATOR : STAR
         | QUESTION
         | PLUS
         ;

range    : ( char )+;

char     : CHAR;

CHAR     : ~[\r\n\t$^];

STAR     : '*';
QUESTION : '?';
PLUS     : '+';
