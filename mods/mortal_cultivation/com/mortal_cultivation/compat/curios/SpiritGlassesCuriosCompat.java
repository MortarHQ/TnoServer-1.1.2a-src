package com.mortal_cultivation.compat.curios;

import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public final class SpiritGlassesCuriosCompat {
   private SpiritGlassesCuriosCompat() {
   }

   public static boolean isSpiritGlassesEquipped(LivingEntity wearer) {
      return CuriosApi.getCuriosInventory(wearer).map(handler -> handler.isEquipped((Item)CultivationItems.SPIRIT_GLASSES.get())).orElse(false);
   }
}
