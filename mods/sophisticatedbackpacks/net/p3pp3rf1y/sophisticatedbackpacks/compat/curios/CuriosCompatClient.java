package net.p3pp3rf1y.sophisticatedbackpacks.compat.curios;

import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CuriosCompatClient {
   public static void registerRenderers() {
      CuriosRendererRegistry.register((Item)ModItems.BACKPACK.get(), BackpackCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.COPPER_BACKPACK.get(), BackpackCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.IRON_BACKPACK.get(), BackpackCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.GOLD_BACKPACK.get(), BackpackCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.DIAMOND_BACKPACK.get(), BackpackCurioRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.NETHERITE_BACKPACK.get(), BackpackCurioRenderer::new);
   }
}
