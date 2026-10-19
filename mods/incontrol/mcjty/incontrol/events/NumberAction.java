package mcjty.incontrol.events;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import mcjty.incontrol.ErrorHandler;
import org.jetbrains.annotations.Nullable;

public record NumberAction(String name, List<NumberAction.Action> actions) {
   @Nullable
   static NumberAction parse(JsonObject object) {
      JsonObject number = object.getAsJsonObject("number");
      if (number == null) {
         return null;
      } else if (!number.has("name")) {
         ErrorHandler.error("No name specified for number action!");
         return null;
      } else if (!number.has("value")) {
         ErrorHandler.error("No set/add/mul specified for number action!");
         return null;
      } else {
         String name = number.getAsJsonPrimitive("name").getAsString();
         String value = number.getAsJsonPrimitive("value").getAsString();
         List<NumberAction.Action> actions = parseActions(value);
         return actions == null ? null : new NumberAction(name, actions);
      }
   }

   @Nullable
   public static NumberAction createUnnamed(String value) {
      List<NumberAction.Action> actions = parseActions(value);
      return actions == null ? null : new NumberAction("", actions);
   }

   @Nullable
   private static List<NumberAction.Action> parseActions(String value) {
      List<NumberAction.Action> actions = new ArrayList<>();
      value = value.trim();
      int pos = 0;

      while (pos < value.length()) {
         char c = value.charAt(pos);
         if (c == ' ') {
            pos++;
         } else {
            NumberAction.Operator operator = NumberAction.Operator.getOperator(c);
            if (operator == NumberAction.Operator.NONE) {
               ErrorHandler.error("Invalid number action '" + value + "'!");
               return null;
            }

            int start = ++pos;

            while (pos < value.length() && Character.isDigit(value.charAt(pos))) {
               pos++;
            }

            if (pos <= start) {
               ErrorHandler.error("Invalid number action '" + value + "'!");
               return null;
            }

            int v = Integer.parseInt(value.substring(start, pos));
            actions.add(new NumberAction.Action(operator, v));
         }
      }

      return actions;
   }

   public int perform(int a) {
      for (NumberAction.Action action : this.actions) {
         a = action.perform(a);
      }

      return a;
   }

   record Action(NumberAction.Operator operator, int value) {
      public int perform(int a) {
         return this.operator.perform(a, this.value);
      }
   }

   enum Operator {
      NONE("", (a, b) -> -1),
      SET("", (a, b) -> b),
      ADD("+", (a, b) -> a + b),
      SUB("-", (a, b) -> a - b),
      MUL("*", (a, b) -> a * b),
      DIV("/", (a, b) -> a / b),
      MOD("%", (a, b) -> a % b);

      private final String symbol;
      private final BiFunction<Integer, Integer, Integer> function;

      Operator(String symbol, BiFunction<Integer, Integer, Integer> function) {
         this.symbol = symbol;
         this.function = function;
      }

      public int perform(int a, int b) {
         return this.function.apply(a, b);
      }

      public static NumberAction.Operator getOperator(char op) {
         for (NumberAction.Operator operator : values()) {
            if (operator.symbol.length() >= 1 && operator.symbol.charAt(0) == op) {
               return operator;
            }
         }

         return Character.isDigit(op) ? SET : NONE;
      }
   }
}
