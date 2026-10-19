package net.darkhax.pricklemc.common.api.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtils {
   public static boolean greaterThan(Number x, Number y) {
      return compareNumber(x, y) == 1;
   }

   public static boolean equals(Number x, Number y) {
      return compareNumber(x, y) == 0;
   }

   public static boolean lessThan(Number x, Number y) {
      return compareNumber(x, y) == -1;
   }

   public static int compareNumber(Number x, Number y) {
      return !isSpecialNumber(x) && !isSpecialNumber(y) ? asBigDecimal(x).compareTo(asBigDecimal(y)) : Double.compare(x.doubleValue(), y.doubleValue());
   }

   public static boolean isSpecialNumber(Number number) {
      boolean specialDouble = number instanceof Double doubleVal && (Double.isNaN(doubleVal) || Double.isInfinite(doubleVal));
      boolean specialFloat = number instanceof Float floatVal && (Float.isNaN(floatVal) || Float.isInfinite(floatVal));
      return specialDouble || specialFloat;
   }

   public static BigDecimal asBigDecimal(Number number) {
      if (number instanceof BigDecimal decimalVal) {
         return decimalVal;
      } else if (number instanceof BigInteger bigInt) {
         return new BigDecimal(bigInt);
      } else {
         if (number instanceof Byte || number instanceof Short || number instanceof Integer || number instanceof Long) {
            return new BigDecimal(number.longValue());
         }

         if (!(number instanceof Float) && !(number instanceof Double)) {
            try {
               return new BigDecimal(number.toString());
            } catch (NumberFormatException e) {
               throw new IllegalArgumentException("The number '" + number + "' of class " + number.getClass().getName() + " is not supported.");
            }
         } else {
            return BigDecimal.valueOf(number.doubleValue());
         }
      }
   }
}
