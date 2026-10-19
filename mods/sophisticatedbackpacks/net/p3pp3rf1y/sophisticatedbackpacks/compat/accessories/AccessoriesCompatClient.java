package net.p3pp3rf1y.sophisticatedbackpacks.compat.accessories;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.world.item.Item;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class AccessoriesCompatClient {
   public static void registerRenderers() {
      ModItems.ITEMS.getEntries().forEach(holder -> {
         if (holder.get() instanceof BackpackItem) {
            AccessoriesRendererRegistry.registerRenderer((Item)holder.get(), BackpackAccessoryRenderer::new);
         }
      });
   }
}
