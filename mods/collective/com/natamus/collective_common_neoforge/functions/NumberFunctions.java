package com.natamus.collective_common_neoforge.functions;

import java.util.concurrent.ThreadLocalRandom;

public class NumberFunctions {
   public static int getEnchantingTableLevel(int tablerow, int bookshelfcount) {
      if (bookshelfcount > 15) {
         bookshelfcount = 15;
      }

      double base = ThreadLocalRandom.current().nextInt(1, 9) + Math.floor(bookshelfcount / 2.0F) + ThreadLocalRandom.current().nextInt(0, bookshelfcount + 1);
      if (tablerow == 0) {
         return (int)Math.max(base / 3.0, 1.0);
      } else if (tablerow == 1) {
         return (int)(base * 2.0 / 3.0 + 1.0);
      } else {
         return tablerow == 2 ? (int)Math.max(base, bookshelfcount * 2) : -1;
      }
   }

   public static int moveToZero(int num) {
      return num > 0 ? -1 : 1;
   }

   public static boolean isNumeric(String string) {
      return string == null ? false : string.matches("-?\\d+(\\.\\d+)?");
   }
}
