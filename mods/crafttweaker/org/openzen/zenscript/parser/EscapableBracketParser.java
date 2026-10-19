package org.openzen.zenscript.parser;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.expression.CallArguments;
import org.openzen.zenscript.codemodel.expression.CallStaticExpression;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.member.ref.FunctionalMemberRef;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.codemodel.type.BasicTypeID;
import org.openzen.zenscript.codemodel.type.GlobalTypeRegistry;
import org.openzen.zenscript.codemodel.type.TypeID;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionBinary;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;

public class EscapableBracketParser implements BracketExpressionParser {
   private final FunctionalMemberRef method;
   private final TypeID targetType;

   public EscapableBracketParser(GlobalTypeRegistry registry, FunctionalMemberRef method) {
      if (!method.isStatic()) {
         throw new IllegalArgumentException("Method must be static");
      }

      if (method.getHeader().getNumberOfTypeParameters() > 0) {
         throw new IllegalArgumentException("Method cannot have type parameters");
      }

      this.method = method;
      this.targetType = registry.getForDefinition(method.getTarget().definition);
   }

   @Override
   public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
      StringBuilder string = new StringBuilder();
      List<ParsedExpression> expressionList = new ArrayList<>();

      while (tokens.optional(ZSTokenType.T_GREATER) == null) {
         ZSTokenType peekType = tokens.peek().getType();
         if (peekType == ZSTokenType.EOF) {
            throw new ParseException(position, "Reached EOF, BEP is missing a closing >");
         }

         if (tokens.getLastWhitespace().contains("\n")) {
            throw new ParseException(position, "BEPs cannot contain new lines!");
         }

         ZSToken next = tokens.next();
         if (next.type != ZSTokenType.T_DOLLAR) {
            string.append(next.content);
            string.append(tokens.getLastWhitespace());
         } else {
            String ws = tokens.getLastWhitespace();
            if (!ws.isEmpty()) {
               string.append(next.content).append(ws);
            } else {
               next = tokens.next();
               if (next.type == ZSTokenType.T_AOPEN) {
                  if (string.length() != 0) {
                     expressionList.add(new ParsedExpressionString(position, string.toString(), false));
                     string = new StringBuilder();
                  }

                  expressionList.add(ParsedExpression.parse(tokens));
                  tokens.required(ZSTokenType.T_ACLOSE, "} expected.");
                  string.append(tokens.getLastWhitespace());
               } else {
                  string.append("$").append(ws);
                  string.append(next.content).append(tokens.getLastWhitespace());
               }
            }
         }
      }

      if (string.length() != 0) {
         expressionList.add(new ParsedExpressionString(position, string.toString(), false));
      }

      return new EscapableBracketParser.StaticMethodCallExpression(position, expressionList);
   }

   private class StaticMethodCallExpression extends ParsedExpression {
      private final ParsedExpression call;

      public StaticMethodCallExpression(CodePosition position, List<ParsedExpression> expressions) {
         super(position);
         ParsedExpression p = null;

         for (ParsedExpression expression : expressions) {
            p = p == null ? expression : new ParsedExpressionBinary(expression.position, p, expression, OperatorType.ADD);
         }

         this.call = p;
      }

      @Override
      public IPartialExpression compile(ExpressionScope scope) throws CompileException {
         Expression methodCall = this.call.compile(scope.withHint(BasicTypeID.STRING)).eval();
         CallArguments arguments = new CallArguments(methodCall);
         return new CallStaticExpression(
            this.position,
            EscapableBracketParser.this.targetType,
            EscapableBracketParser.this.method,
            EscapableBracketParser.this.method.getHeader(),
            arguments
         );
      }

      @Override
      public boolean hasStrongType() {
         return true;
      }
   }
}
