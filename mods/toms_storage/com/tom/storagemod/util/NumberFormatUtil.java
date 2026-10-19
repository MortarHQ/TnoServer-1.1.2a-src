package com.tom.storagemod.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;

public class NumberFormatUtil {
   private static final int DIVISION_BASE = 1000;
   private static final char[] ENCODED_POSTFIXES = "KMGTPE".toCharArray();
   public static final Format format;

   public static String formatNumber(long number) {
      int width = 4;
      assert number >= 0L;
      String numberString = Long.toString(number);
      int numberSize = numberString.length();
      if (numberSize <= width) {
         return numberString;
      }

      long base = number;
      double last = base * 1000L;
      int exponent = -1;

      String postFix;
      for (postFix = ""; numberSize > width; postFix = String.valueOf(ENCODED_POSTFIXES[exponent])) {
         last = base;
         base /= 1000L;
         exponent++;
         numberSize = Long.toString(base).length() + 1;
      }

      String withPrecision = format.format(last / 1000.0) + postFix;
      String withoutPrecision = Long.toString(base) + postFix;
      String slimResult = withPrecision.length() <= width ? withPrecision : withoutPrecision;
      assert slimResult.length() <= width;
      return slimResult;
   }

   static {
      DecimalFormatSymbols symbols = new DecimalFormatSymbols();
      symbols.setDecimalSeparator('.');
      DecimalFormat format_ = new DecimalFormat(".#;0.#");
      format_.setDecimalFormatSymbols(symbols);
      format_.setRoundingMode(RoundingMode.DOWN);
      format = format_;
   }
}
