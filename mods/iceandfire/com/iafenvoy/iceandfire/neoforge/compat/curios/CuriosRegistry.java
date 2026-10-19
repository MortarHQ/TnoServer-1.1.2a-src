package com.iafenvoy.iceandfire.neoforge.compat.curios;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosRegistry {
   public static void registerItems() {
      registerSingle((Item)IafItems.HYDRA_HEART.get());
   }

   private static void registerSingle(Item item) {
      CuriosApi.registerCurio(item, new SimpleTickItemWrapper(item));
   }
}
