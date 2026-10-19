package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionBinary;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionCast;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;
import org.openzen.zenscript.parser.type.ParsedTypeBasic;

final class ValidatedEscapableBracketParser implements BracketExpressionParser {
   private final String name;
   private final Method resolver;
   private final MethodHandle validator;

   ValidatedEscapableBracketParser(String name, Method resolver, MethodHandle validator) {
      this.name = name;
      this.resolver = resolver;
      this.validator = validator;
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

      if (expressionList.isEmpty()) {
         expressionList.add(new ParsedExpressionString(position, "", false));
      }

      if (expressionList.size() == 1) {
         ParsedExpression parsedExpression = expressionList.get(0);
         if (this.validator != null && parsedExpression instanceof ParsedExpressionString stringExpression) {
            String value = stringExpression.value;

            boolean valid;
            try {
               valid = (boolean)this.validator.invokeExact((String)value);
            } catch (Throwable e) {
               String message = String.format("Invalid parameters to BEP %s: '<%s:%s>", this.name, this.name, value);
               throw new ParseException(position, message, e);
            }

            if (!valid) {
               String format = String.format(
                  "Invalid parameters to BEP %s: '<%s:%s>. There may be more information about this in the log.", this.name, this.name, value
               );
               throw new ParseException(position, format);
            }
         }
      }

      return new ValidatedEscapableBracketParser.BracketResolverCallExpression(position, this.resolver, expressionList);
   }

   private static final class BracketResolverCallExpression extends ParsedExpression {
      private static final String PACKAGE_SEPARATOR = Pattern.quote(".");
      private final Method resolver;
      private final ParsedExpression call;

      BracketResolverCallExpression(CodePosition position, Method resolver, List<ParsedExpression> arguments) {
         super(position);
         this.resolver = resolver;
         this.call = arguments.stream().skip(1L).reduce(arguments.get(0), (a, b) -> new ParsedExpressionBinary(b.position, a, b, OperatorType.ADD));
      }

      @Override
      public IPartialExpression compile(ExpressionScope scope) throws CompileException {
         ParsedExpression classCall = this.compileClassCall(this.resolver.getDeclaringClass());
         ParsedExpression methodReference = this.compileMethodReference(classCall, this.resolver);
         return this.generateCall(methodReference, this.call).compile(scope);
      }

      @Override
      public boolean hasStrongType() {
         return true;
      }

      private ParsedExpression compileClassCall(Class<?> owner) {
         IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
         IZenClassRegistry registry = CraftTweakerAPI.getRegistry().getZenClassRegistry();
         String ownerName = registry.getNameFor(loader, owner).orElseThrow();
         String[] packages = ownerName.split(PACKAGE_SEPARATOR);
         return ParseUtil.staticMemberExpression(this.position, ownerName);
      }

      private ParsedExpression compileMethodReference(ParsedExpression classCall, Method target) {
         String targetName = target.getName();
         return new ParsedExpressionMember(this.position, classCall, targetName, null);
      }

      private ParsedExpression generateCall(ParsedExpression method, ParsedExpression argument) {
         ParsedExpression castedArgument = new ParsedExpressionCast(this.position, argument, ParsedTypeBasic.STRING, false);
         ParsedCallArguments parsedArguments = new ParsedCallArguments(null, List.of(castedArgument));
         return new ParsedExpressionCall(this.position, method, parsedArguments);
      }
   }
}
