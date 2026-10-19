package com.finderfeed.fdlib.shunting_yard;

import com.finderfeed.fdlib.shunting_yard.functions.AdditionFunction;
import com.finderfeed.fdlib.shunting_yard.functions.DivisionFunction;
import com.finderfeed.fdlib.shunting_yard.functions.MultiplicationFunction;
import com.finderfeed.fdlib.shunting_yard.functions.SubtractionFunction;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYFunction;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYNamedVariable;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYNode;
import com.finderfeed.fdlib.shunting_yard.sy_base.SYStaticValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShuntingYard {
   public static Map<String, SYFunction> OPERATORS = new HashMap<>(
      Map.of("-", new SubtractionFunction(), "+", new AdditionFunction(), "*", new MultiplicationFunction(), "/", new DivisionFunction())
   );
   public static Map<String, Integer> OPERATOR_PRIORITY = new HashMap<>(Map.of("-", 2, "+", 2, "*", 3, "/", 3));

   public static RPNExpression parse(String expression) {
      expression = expression.replace(" ", "");
      StringBuilder builder = new StringBuilder(expression);
      Stack<ShuntingYard.ReadResult> readResults = new Stack<>();
      List<SYNode> result = new ArrayList<>();
      AtomicBoolean prevOperator = new AtomicBoolean(true);
      AtomicBoolean minus = new AtomicBoolean(false);

      ShuntingYard.ReadResult readResult;
      while ((readResult = next(builder)) != null) {
         handleReadResult(readResult, readResults, result, prevOperator, minus);
      }

      while (!readResults.isEmpty()) {
         SYFunction operator = OPERATORS.get(readResults.pop().str);
         result.add(operator);
      }

      return new RPNExpression(result);
   }

   private static void handleReadResult(
      ShuntingYard.ReadResult readResult,
      Stack<ShuntingYard.ReadResult> readResults,
      List<SYNode> result,
      AtomicBoolean wasPreviousAnOperator,
      AtomicBoolean unaryMinus
   ) {
      switch (readResult.type) {
         case OPERATOR:
            if (readResult.str.equals("-") && wasPreviousAnOperator.get()) {
               unaryMinus.set(true);
               result.add(new SYStaticValue(0.0F));
            } else {
               handleOperatorToken(readResult, readResults, result);
            }

            wasPreviousAnOperator.set(true);
            break;
         case CLOSING_BRACKET:
            handleClosingBracket(readResults, result);
            wasPreviousAnOperator.set(false);
            break;
         case OPENING_BRACKET:
            if (unaryMinus.get()) {
               readResult.setWithUnaryMinus(true);
               unaryMinus.set(false);
            }

            readResults.push(readResult);
            wasPreviousAnOperator.set(true);
            break;
         case NUMBER:
            float fl = Float.parseFloat(readResult.str);
            result.add(new SYStaticValue(fl));
            if (unaryMinus.get()) {
               result.add(new SubtractionFunction());
               unaryMinus.set(false);
            }

            wasPreviousAnOperator.set(false);
            break;
         case FUNCTION:
            if (unaryMinus.get()) {
               readResult.setWithUnaryMinus(true);
               unaryMinus.set(false);
            }

            readResults.push(readResult);
            wasPreviousAnOperator.set(true);
            break;
         case VARIABLE:
            result.add(new SYNamedVariable(readResult.str));
            if (unaryMinus.get()) {
               result.add(new SubtractionFunction());
               unaryMinus.set(false);
            }

            wasPreviousAnOperator.set(false);
            break;
         case COMMA:
            handleComma(readResults, result);
            wasPreviousAnOperator.set(true);
      }
   }

   private static void handleComma(Stack<ShuntingYard.ReadResult> readResults, List<SYNode> resultList) {
      while (!readResults.isEmpty()) {
         ShuntingYard.ReadResult result = readResults.peek();
         if (result.type != ShuntingYard.TokenType.FUNCTION) {
            readResults.pop();
            resultList.add(OPERATORS.get(result.str));
            continue;
         }
         break;
      }
   }

   private static void handleClosingBracket(Stack<ShuntingYard.ReadResult> readResults, List<SYNode> resultList) {
      while (!readResults.isEmpty()) {
         ShuntingYard.ReadResult result = readResults.pop();
         if (result.type == ShuntingYard.TokenType.FUNCTION) {
            SYFunction function = FunctionRegistry.getFunction(result.str);
            if (function == null) {
               throw new RuntimeException("Unknown function: " + result.str);
            }

            resultList.add(function);
            if (result.isWithUnaryMinus()) {
               resultList.add(new SubtractionFunction());
            }
            break;
         } else {
            if (result.type == ShuntingYard.TokenType.OPENING_BRACKET) {
               if (result.isWithUnaryMinus()) {
                  resultList.add(new SubtractionFunction());
               }
               break;
            }

            resultList.add(OPERATORS.get(result.str));
         }
      }
   }

   private static void handleOperatorToken(ShuntingYard.ReadResult operator, Stack<ShuntingYard.ReadResult> readResults, List<SYNode> resultList) {
      if (!readResults.isEmpty()) {
         int priority = OPERATOR_PRIORITY.get(operator.str);

         while (!readResults.isEmpty()) {
            ShuntingYard.ReadResult result = readResults.peek();
            if (result.type != ShuntingYard.TokenType.OPERATOR) {
               readResults.push(operator);
               return;
            }

            int priority2 = OPERATOR_PRIORITY.get(result.str);
            if (priority > priority2) {
               readResults.push(operator);
               return;
            }

            readResults.pop();
            resultList.add(OPERATORS.get(result.str));
         }

         readResults.push(operator);
      } else {
         readResults.push(operator);
      }
   }

   public static ShuntingYard.ReadResult next(StringBuilder string) {
      if (string.isEmpty()) {
         return null;
      } else {
         char c = string.charAt(0);
         if (OPERATORS.containsKey(c + "")) {
            string.delete(0, 1);
            return new ShuntingYard.ReadResult(c + "", ShuntingYard.TokenType.OPERATOR);
         } else if (Character.isLetter(c)) {
            ShuntingYard.ReadResult v = readFunctionOrVariable(string);
            string.delete(0, v.type == ShuntingYard.TokenType.VARIABLE ? v.str.length() : v.str.length() + 1);
            return v;
         } else if (c == '(') {
            string.delete(0, 1);
            return new ShuntingYard.ReadResult("(", ShuntingYard.TokenType.OPENING_BRACKET);
         } else if (c == ')') {
            string.delete(0, 1);
            return new ShuntingYard.ReadResult(")", ShuntingYard.TokenType.CLOSING_BRACKET);
         } else if (c == ',') {
            string.delete(0, 1);
            return new ShuntingYard.ReadResult(",", ShuntingYard.TokenType.COMMA);
         } else {
            String val = readNumber(string);
            string.delete(0, val.length());
            return new ShuntingYard.ReadResult(val, ShuntingYard.TokenType.NUMBER);
         }
      }
   }

   private static ShuntingYard.ReadResult readFunctionOrVariable(StringBuilder builder) {
      StringBuilder val = new StringBuilder();

      char c;
      int i;
      for (i = 0; i < builder.length() && (Character.isLetter(c = builder.charAt(i)) || c == '.' || c == '_' || Character.isDigit(c)); i++) {
         val.append(c);
      }

      return i < builder.length() && builder.charAt(i) == '('
         ? new ShuntingYard.ReadResult(val.toString(), ShuntingYard.TokenType.FUNCTION)
         : new ShuntingYard.ReadResult(val.toString(), ShuntingYard.TokenType.VARIABLE);
   }

   private static String readNumber(StringBuilder builder) {
      StringBuilder val = new StringBuilder();
      int i = 0;

      char c;
      while (i < builder.length() && (Character.isDigit(c = builder.charAt(i++)) || c == '.')) {
         val.append(c);
      }

      return val.toString();
   }

   public static class ReadResult {
      private String str;
      private ShuntingYard.TokenType type;
      private boolean isWithUnaryMinus;

      public ReadResult(String str, ShuntingYard.TokenType type) {
         this.str = str;
         this.type = type;
      }

      public void setWithUnaryMinus(boolean withUnaryMinus) {
         this.isWithUnaryMinus = withUnaryMinus;
      }

      public boolean isWithUnaryMinus() {
         return this.isWithUnaryMinus;
      }
   }

   public enum TokenType {
      OPERATOR,
      CLOSING_BRACKET,
      OPENING_BRACKET,
      NUMBER,
      FUNCTION,
      VARIABLE,
      COMMA;
   }
}
