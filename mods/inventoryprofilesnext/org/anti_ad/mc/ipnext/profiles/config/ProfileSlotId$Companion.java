package org.anti_ad.mc.ipnext.profiles.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProfileSlotId$Companion {
   private ProfileSlotId$Companion() {
   }

   @NotNull
   public final ProfileSlotId valueOfOrFAKE(@Nullable String var1) {
      return var1 == null ? ProfileSlotId.NONE : ProfileSlotId.valueOf(var1);
   }

   @NotNull
   public final ProfileSlotId valueOf(int var1) {
      ProfileSlotId var10000 = (ProfileSlotId)ProfilesConfigParserKt.access$getSlotIdToProfileSlotId$p().get(var1);
      if (var10000 == null) {
         var10000 = ProfileSlotId.NONE;
      }

      return var10000;
   }
}
