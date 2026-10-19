package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

public enum RequireNbt {
   REQUIRED,
   NO_NBT,
   NOT_REQUIRED;

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final boolean match(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      switch (RequireNbt$WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
         case 1:
            if (var1.getTag() != null) {
               return true;
            }

            return false;
         case 2:
            if (var1.getTag() == null) {
               return true;
            }

            return false;
         case 3:
            return true;
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
