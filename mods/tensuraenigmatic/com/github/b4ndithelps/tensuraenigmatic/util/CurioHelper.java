package com.github.b4ndithelps.tensuraenigmatic.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import top.theillusivec4.curios.api.CuriosApi;

public class CurioHelper {
   public static boolean hasCurio(LivingEntity entity, ItemLike item) {
      return CuriosApi.getCuriosInventory(entity).map(handler -> handler.findFirstCurio(item.asItem()).isPresent()).orElse(false);
   }

   private CurioHelper() {
   }
}
