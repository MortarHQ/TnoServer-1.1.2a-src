package com.natamus.collective_common_forge.functions;

import com.mojang.util.UndashedUuid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDFunctions {
   public static UUID getUUIDFromStringLenient(String uuidString) {
      return UndashedUuid.fromStringLenient(uuidString);
   }

   public static List<Integer> oldIdToIntArray(String oldid) {
      String oldidfull = oldid.replace("-", "");
      return getIntegerParts(oldidfull, 8);
   }

   private static String getConvertedUUID(Integer[] ints) {
      StringBuilder sb = new StringBuilder();
      sb.append("I;");

      for (int x = 0; x < ints.length - 1; x++) {
         sb.append(ints[x]);
         sb.append(",");
      }

      sb.append(ints[ints.length - 1]);
      return sb.toString();
   }

   private static List<Integer> getIntegerParts(String string, int partitionSize) {
      List<Integer> parts = new ArrayList<>();
      int len = string.length();
      int i = 0;

      while (i < len) {
         parts.add(partToDecimalValue(string.substring(i, Math.min(len, i + partitionSize))));
         i += partitionSize;
      }

      return parts;
   }

   private static int partToDecimalValue(String hex) {
      return Long.valueOf(hex, 16).intValue();
   }
}
