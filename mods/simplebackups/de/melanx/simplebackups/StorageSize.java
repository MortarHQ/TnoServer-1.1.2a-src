package de.melanx.simplebackups;

import java.util.Locale;

public enum StorageSize {
   B(0),
   KB(1),
   MB(2),
   GB(3),
   TB(4);

   private final long sizeInBytes;
   private final String postfix;

   StorageSize(int factor) {
      this.sizeInBytes = (long)Math.pow(1024.0, factor);
      this.postfix = this.name().toUpperCase(Locale.ROOT);
   }

   public static StorageSize getSizeFor(double bytes) {
      for (StorageSize value : values()) {
         if (bytes < value.sizeInBytes) {
            return value.getLower();
         }

         if (value == TB) {
            return value;
         }
      }

      return B;
   }

   public static long getBytes(String s) {
      String[] splits = s.split(" ");
      int amount = Integer.parseInt(splits[0]);
      StorageSize size = valueOf(splits[1].toUpperCase(Locale.ROOT));
      return amount * size.sizeInBytes;
   }

   public static String getFormattedSize(double bytes) {
      StorageSize size = getSizeFor(bytes);
      double small = bytes / size.sizeInBytes;
      return String.format("%.1f %s", small, size.postfix);
   }

   public StorageSize getLower() {
      return this.ordinal() == 0 ? this : values()[this.ordinal() - 1];
   }
}
