package org.anti_ad.mc.ipnext.item;

import net.minecraft.world.effect.MobEffectCategory;

// $VF: synthetic class
public final class ComponentUtils$WhenMappings {
   static {
      int[] var0 = new int[MobEffectCategory.values().length];

      try {
         var0[MobEffectCategory.HARMFUL.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         var0[MobEffectCategory.NEUTRAL.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         var0[MobEffectCategory.BENEFICIAL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
