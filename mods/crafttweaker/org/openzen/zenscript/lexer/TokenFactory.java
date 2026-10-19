package org.openzen.zenscript.lexer;

public interface TokenFactory<T, TT> {
   T create(TT var1, String var2);
}
