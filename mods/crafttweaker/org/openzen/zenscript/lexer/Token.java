package org.openzen.zenscript.lexer;

public interface Token<TT extends TokenType> {
   TT getType();

   String getContent();
}
