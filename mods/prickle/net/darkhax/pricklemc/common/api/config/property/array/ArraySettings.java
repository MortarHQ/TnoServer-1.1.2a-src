package net.darkhax.pricklemc.common.api.config.property.array;

import net.darkhax.pricklemc.common.api.annotations.Array;

public class ArraySettings {
   public static final ArraySettings DEFAULT = new ArraySettings();
   private final int inlineCount;
   private final boolean inlineComplex;
   private final boolean allowEmpty;

   public ArraySettings(Array meta) {
      this.inlineCount = meta.inlineCount();
      this.inlineComplex = meta.inlineComplex();
      this.allowEmpty = meta.allowEmpty();
   }

   private ArraySettings() {
      this.inlineCount = 5;
      this.inlineComplex = false;
      this.allowEmpty = true;
   }

   public int inlineCount() {
      return this.inlineCount;
   }

   public boolean inlineComplex() {
      return this.inlineComplex;
   }

   public boolean allowEmpty() {
      return this.allowEmpty;
   }
}
