package dev.ftb.mods.ftbfiltersystem.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

public record NumericComparison(NumericComparison.ComparisonOp op, int value, boolean percentage) implements IntPredicate {
   public static NumericComparison fromString(String str, boolean allowPercentages) throws FilterException {
      boolean pct = false;
      if (str.endsWith("%")) {
         if (!allowPercentages) {
            throw new FilterException("Percentage used but not permitted in: '" + str + "'");
         }

         pct = true;
         str = str.substring(0, str.length() - 1);
      }

      int numStart = -1;

      for (int i = 0; i < str.length(); i++) {
         if (Character.isDigit(str.charAt(i))) {
            numStart = i;
            break;
         }
      }

      if (numStart < 0) {
         throw new FilterException("Missing number in: '" + str + "'");
      }

      String numStr = str.substring(numStart);
      String opStr = str.substring(0, numStart);

      try {
         int val = Integer.parseInt(numStr);
         NumericComparison.ComparisonOp op = NumericComparison.ComparisonOp.byString(opStr)
            .orElseThrow(() -> new FilterException("Invalid comparison op: '" + opStr + "'"));
         return new NumericComparison(op, val, pct);
      } catch (NumberFormatException e) {
         throw new FilterException("Invalid number: '" + numStr + "'");
      }
   }

   @Override
   public String toString() {
      return this.op.toString() + this.value + (this.percentage ? "%" : "");
   }

   @Override
   public boolean test(int toCheck) {
      return this.op.test(toCheck, this.value);
   }

   public enum ComparisonOp {
      EQ("=", (v1, v2) -> v1 == v2),
      NE("!=", (v1, v2) -> v1 != v2),
      GT(">", (v1, v2) -> v1 > v2),
      LT("<", (v1, v2) -> v1 < v2),
      LE("<=", (v1, v2) -> v1 <= v2),
      GE(">=", (v1, v2) -> v1 >= v2);

      private final String str;
      private final NumericComparison.ValuePredicate predicate;
      private static final Map<String, NumericComparison.ComparisonOp> map = (Map<String, NumericComparison.ComparisonOp>)Util.make(
         new HashMap(), map -> Arrays.stream(values()).forEach(op -> map.put(op.str, op))
      );

      ComparisonOp(String str, NumericComparison.ValuePredicate predicate) {
         this.str = str;
         this.predicate = predicate;
      }

      public static Optional<NumericComparison.ComparisonOp> byString(String str) {
         return Optional.ofNullable(map.get(str));
      }

      public boolean test(int val1, int val2) {
         return this.predicate.compare(val1, val2);
      }

      @Override
      public String toString() {
         return this.str;
      }

      public Component getDisplayName() {
         return Component.literal(this.str);
      }
   }

   interface ValuePredicate {
      boolean compare(int var1, int var2);
   }
}
