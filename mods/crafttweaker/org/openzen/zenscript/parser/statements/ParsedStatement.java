package org.openzen.zenscript.parser.statements;

import java.util.ArrayList;
import java.util.List;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.codemodel.WhitespaceInfo;
import org.openzen.zenscript.codemodel.WhitespacePostComment;
import org.openzen.zenscript.codemodel.scope.StatementScope;
import org.openzen.zenscript.codemodel.statement.Statement;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSToken;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.ParsedAnnotation;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.type.IParsedType;

public abstract class ParsedStatement {
   public final CodePosition position;
   public final ParsedAnnotation[] annotations;
   public final WhitespaceInfo whitespace;

   public ParsedStatement(CodePosition position, ParsedAnnotation[] annotations, WhitespaceInfo whitespace) {
      this.position = position;
      this.annotations = annotations;
      this.whitespace = whitespace;
   }

   public static ParsedFunctionBody parseLambdaBody(ZSTokenParser tokens, boolean inExpression) throws ParseException {
      CodePosition position = tokens.getPosition();
      if (tokens.optional(ZSTokenType.T_AOPEN) == null) {
         ParsedFunctionBody result = new ParsedLambdaFunctionBody(ParsedExpression.parse(tokens));
         if (!inExpression) {
            tokens.required(ZSTokenType.T_SEMICOLON, "; expected");
         }

         return result;
      } else {
         List<ParsedStatement> statements = new ArrayList<>();

         while (tokens.optional(ZSTokenType.T_ACLOSE) == null) {
            statements.add(parse(tokens));
         }

         return new ParsedStatementsFunctionBody(new ParsedStatementBlock(position, ParsedAnnotation.NONE, null, null, statements));
      }
   }

   public static ParsedFunctionBody parseFunctionBody(ZSTokenParser tokens) throws ParseException {
      if (tokens.optional(ZSTokenType.T_LAMBDA) != null) {
         return parseLambdaBody(tokens, false);
      } else {
         return tokens.optional(ZSTokenType.T_SEMICOLON) != null
            ? new ParsedEmptyFunctionBody(tokens.getPosition())
            : new ParsedStatementsFunctionBody(parseBlock(tokens, ParsedAnnotation.NONE, true));
      }
   }

   public static ParsedStatementBlock parseBlock(ZSTokenParser parser, ParsedAnnotation[] annotations, boolean isFirst) throws ParseException {
      String ws = parser.getLastWhitespace();
      CodePosition position = parser.getPosition();
      parser.required(ZSTokenType.T_AOPEN, "{ expected");
      parser.skipWhitespaceNewline();
      ArrayList<ParsedStatement> statements = new ArrayList<>();

      for (boolean firstContent = true; parser.optional(ZSTokenType.T_ACLOSE) == null; firstContent = false) {
         statements.add(parse(parser, annotations, firstContent));
      }

      WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
      WhitespacePostComment postComment = WhitespacePostComment.fromWhitespace(parser.getLastWhitespace());
      return new ParsedStatementBlock(position, annotations, whitespace, postComment, statements);
   }

   public static ParsedStatement parse(ZSTokenParser parser) throws ParseException {
      ParsedAnnotation[] annotations = ParsedAnnotation.parseAnnotations(parser);
      return parse(parser, annotations);
   }

   public static ParsedStatement parse(ZSTokenParser parser, ParsedAnnotation[] annotations) throws ParseException {
      return parse(parser, annotations, false);
   }

   public static ParsedStatement parse(ZSTokenParser parser, ParsedAnnotation[] annotations, boolean isFirst) throws ParseException {
      String ws = parser.getLastWhitespace();
      CodePosition position = parser.getPosition();
      ZSToken next = parser.peek();
      switch (next.getType()) {
         case T_AOPEN:
            return parseBlock(parser, annotations, isFirst);
         case K_RETURN: {
            parser.next();
            ParsedExpression expression = null;
            if (!parser.isNext(ZSTokenType.T_SEMICOLON)) {
               expression = ParsedExpression.parse(parser);
            }

            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementReturn(position, annotations, whitespace, expression);
         }
         case K_VAR:
         case K_VAL: {
            ZSToken start = parser.next();
            String name = parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content;
            IParsedType type = null;
            ParsedExpression initializer = null;
            if (parser.optional(ZSTokenType.K_AS) != null || parser.optional(ZSTokenType.T_COLON) != null) {
               type = IParsedType.parse(parser);
            }

            if (parser.optional(ZSTokenType.T_ASSIGN) != null) {
               initializer = ParsedExpression.parse(parser);
            }

            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementVar(position, annotations, whitespace, name, type, initializer, start.getType() == ZSTokenType.K_VAL);
         }
         case K_IF: {
            parser.next();
            ParsedExpression expression = ParsedExpression.parse(parser);
            parser.skipWhitespaceNewline();
            ParsedStatement onIf = parse(parser);
            ParsedStatement onElse = null;
            if (parser.optional(ZSTokenType.K_ELSE) != null) {
               parser.skipWhitespaceNewline();
               onElse = parse(parser);
            }

            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementIf(position, annotations, whitespace, expression, onIf, onElse);
         }
         case K_FOR: {
            parser.next();
            String name = parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content;
            List<String> names = new ArrayList<>();
            names.add(name);

            while (parser.optional(ZSTokenType.T_COMMA) != null) {
               names.add(parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content);
            }

            parser.required(ZSTokenType.K_IN, "in expected");
            ParsedExpression source = ParsedExpression.parse(parser);
            ParsedStatement content = parse(parser);
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementForeach(position, annotations, whitespace, names.toArray(new String[names.size()]), source, content);
         }
         case K_DO: {
            ZSToken t = parser.next();
            String label = null;
            if (parser.optional(ZSTokenType.T_COLON) != null) {
               label = parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content;
            }

            ParsedStatement content = parse(parser);
            parser.required(ZSTokenType.K_WHILE, "while expected");
            ParsedExpression condition = ParsedExpression.parse(parser);
            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementDoWhile(position, annotations, whitespace, label, content, condition);
         }
         case K_WHILE: {
            ZSToken t = parser.next();
            String label = null;
            if (parser.optional(ZSTokenType.T_COLON) != null) {
               label = parser.required(ZSTokenType.T_IDENTIFIER, "identifier expected").content;
            }

            ParsedExpression condition = ParsedExpression.parse(parser);
            ParsedStatement content = parse(parser);
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementWhile(position, annotations, whitespace, label, condition, content);
         }
         case K_LOCK: {
            ZSToken t = parser.next();
            ParsedExpression object = ParsedExpression.parse(parser);
            ParsedStatement content = parse(parser);
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementLock(position, annotations, whitespace, object, content);
         }
         case K_THROW: {
            ZSToken t = parser.next();
            ParsedExpression value = ParsedExpression.parse(parser);
            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementThrow(position, annotations, whitespace, value);
         }
         case K_TRY: {
            parser.pushMark();
            ZSToken t = parser.next();
            if (parser.peek().type != ZSTokenType.T_QUEST && parser.peek().type != ZSTokenType.T_NOT) {
               parser.popMark();
               String name = null;
               ParsedExpression initializer = null;
               if (parser.isNext(ZSTokenType.T_IDENTIFIER)) {
                  name = parser.next().content;
                  parser.required(ZSTokenType.T_ASSIGN, "= expected");
                  initializer = ParsedExpression.parse(parser);
               }

               ParsedStatement contentx = parse(parser);
               List<ParsedCatchClause> catchClauses = new ArrayList<>();

               while (parser.optional(ZSTokenType.K_CATCH) != null) {
                  CodePosition catchPosition = parser.getPosition();
                  String catchName = null;
                  if (parser.isNext(ZSTokenType.T_IDENTIFIER)) {
                     catchName = parser.next().content;
                  }

                  IParsedType catchType = null;
                  if (parser.optional(ZSTokenType.K_AS) != null) {
                     catchType = IParsedType.parse(parser);
                  }

                  ParsedStatement catchContent = parse(parser);
                  catchClauses.add(new ParsedCatchClause(catchPosition, catchName, catchType, catchContent));
               }

               ParsedStatement finallyClause = null;
               if (parser.optional(ZSTokenType.K_FINALLY) != null) {
                  finallyClause = parse(parser);
               }

               WhitespaceInfo whitespacex = parser.collectWhitespaceInfo(ws, isFirst);
               return new ParsedStatementTryCatch(position, annotations, whitespacex, name, initializer, contentx, catchClauses, finallyClause);
            } else {
               parser.reset();
            }
         }
         default: {
            ParsedExpression expression = ParsedExpression.parse(parser);
            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementExpression(position, annotations, whitespace, expression);
         }
         case K_CONTINUE: {
            ZSToken t = parser.next();
            String name = null;
            if (parser.isNext(ZSTokenType.T_IDENTIFIER)) {
               name = parser.next().content;
            }

            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementContinue(position, annotations, whitespace, name);
         }
         case K_BREAK: {
            ZSToken t = parser.next();
            String name = null;
            if (parser.isNext(ZSTokenType.T_IDENTIFIER)) {
               name = parser.next().content;
            }

            parser.required(ZSTokenType.T_SEMICOLON, "; expected");
            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementBreak(position, annotations, whitespace, name);
         }
         case K_SWITCH: {
            ZSToken t = parser.next();
            String name = null;
            if (parser.optional(ZSTokenType.T_COLON) != null) {
               name = parser.next().content;
            }

            ParsedExpression value = ParsedExpression.parse(parser);
            ParsedSwitchCase currentCase = null;
            List<ParsedSwitchCase> cases = new ArrayList<>();
            parser.required(ZSTokenType.T_AOPEN, "{ expected");

            while (parser.optional(ZSTokenType.T_ACLOSE) == null) {
               if (parser.optional(ZSTokenType.K_CASE) != null) {
                  currentCase = new ParsedSwitchCase(ParsedExpression.parse(parser));
                  cases.add(currentCase);
                  parser.required(ZSTokenType.T_COLON, ": expected");
               } else if (parser.optional(ZSTokenType.K_DEFAULT) != null) {
                  currentCase = new ParsedSwitchCase(null);
                  cases.add(currentCase);
                  parser.required(ZSTokenType.T_COLON, ": expected");
               } else {
                  if (currentCase == null) {
                     throw new ParseException(parser.getPosition(), "Statement in switch outside case");
                  }

                  currentCase.statements.add(parse(parser));
               }
            }

            WhitespaceInfo whitespace = parser.collectWhitespaceInfo(ws, isFirst);
            return new ParsedStatementSwitch(position, annotations, whitespace, name, value, cases);
         }
      }
   }

   public static List<Statement> compile(List<ParsedStatement> statements, StatementScope scope) {
      if (statements == null) {
         return null;
      }

      List<Statement> result = new ArrayList<>();

      for (ParsedStatement statement : statements) {
         result.add(statement.compile(scope));
      }

      return result;
   }

   public abstract Statement compile(StatementScope var1);

   protected Statement result(Statement statement, StatementScope scope) {
      statement.setTag(WhitespaceInfo.class, this.whitespace);
      statement.annotations = ParsedAnnotation.compileForStatement(this.annotations, statement, scope);
      return statement;
   }
}
