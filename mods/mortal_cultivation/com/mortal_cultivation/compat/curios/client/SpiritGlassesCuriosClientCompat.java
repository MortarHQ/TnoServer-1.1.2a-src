package com.mortal_cultivation.compat.curios.client;

import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public final class SpiritGlassesCuriosClientCompat {
   private SpiritGlassesCuriosClientCompat() {
   }

   public static void registerRenderer() {
      CuriosRendererRegistry.register((Item)CultivationItems.SPIRIT_GLASSES.get(), SpiritGlassesCurioRenderer::new);
   }
}
