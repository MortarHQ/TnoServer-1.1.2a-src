package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.api.util.ParseUtil;
import java.util.Locale;
import java.util.Map;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

final class IgnorePrefixCasingBracketParser implements BracketExpressionParser {
   private final Map<String, BracketExpressionParser> parsers;

   IgnorePrefixCasingBracketParser(Map<String, BracketExpressionParser> parsers) {
      this.parsers = Map.copyOf(parsers);
   }

   @Override
   public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      String name = ParseUtil.readContent(position, tokens, ZSTokenType.T_COLON);
      if (name.isEmpty()) {
         throw new ParseException(position.withLength(tokens.peek().getContent().length()), "identifier expected");
      } else {
         BracketExpressionParser parser = this.find(name);
         if (parser == null) {
            throw new ParseException(position, "Invalid bracket expression: no prefix " + name);
         } else {
            return parser.parse(position, tokens);
         }
      }
   }

   private BracketExpressionParser find(String name) {
      return this.parsers.get(name.toLowerCase(Locale.ENGLISH));
   }
}
