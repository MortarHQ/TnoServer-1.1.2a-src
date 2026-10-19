package org.openzen.zenscript.lexer;

public interface TokenType {
   String getRegexp();

   boolean isWhitespace();
}
