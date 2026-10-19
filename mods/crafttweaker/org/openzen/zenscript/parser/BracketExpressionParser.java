package org.openzen.zenscript.parser;

import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

public interface BracketExpressionParser {
   ParsedExpression parse(CodePosition var1, ZSTokenParser var2) throws ParseException;
}
