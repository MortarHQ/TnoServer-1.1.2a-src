package org.anti_ad.mc.ipnext.ingame;

import kotlin.jvm.functions.Function0;
import net.minecraft.world.item.ItemStack;

public final class VanillaAccessorsKt$(itemType)$1 implements Function0 {
   public VanillaAccessorsKt$_itemType_$1/* $VF was: VanillaAccessorsKt$(itemType)$1 */(ItemStack var1) {
      this.$this_(itemType) = var1;
   }

   public final Boolean invoke() {
      return this.$this_(itemType).isDamageableItem();
   }
}
