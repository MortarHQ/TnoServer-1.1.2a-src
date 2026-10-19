package nadiendev.constructionwand.client.property;

import nadiendev.constructionwand.api.IWandCore;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.items.core.ItemCoreAngel;
import nadiendev.constructionwand.items.core.ItemCoreDestruction;
import nadiendev.constructionwand.items.wand.ItemWand;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredItem;

public class SelectWandCore {
   public static final ResourceLocation WAND_CORE = ResourceLocation.fromNamespaceAndPath("constructionwand", "wand_core");

   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         for (DeferredItem<Item> wand : ModItems.WANDS) {
            ItemProperties.register((Item)wand.get(), WAND_CORE, (stack, level, entity, seed) -> {
               if (!(stack.getItem() instanceof ItemWand)) {
                  return 0.0F;
               } else {
                  IWandCore core = (new WandOptions(stack)).cores.get();
                  if (core instanceof ItemCoreAngel) {
                     return 1.0F;
                  } else {
                     return core instanceof ItemCoreDestruction ? 2.0F : 0.0F;
                  }
               }
            });
         }
      });
   }
}
