package dev.foxboss.registry;

import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class FoxItems {
   public static final Items ITEMS = DeferredRegister.createItems("foxboss");
   public static final DeferredItem<DeferredSpawnEggItem> MILIM_SPAWN_EGG = ITEMS.register(
      "milim_spawn_egg", () -> new DeferredSpawnEggItem(FoxEntities.MILIM, 15971285, 2631229, new Properties())
   );

   public static void creativeTabContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTab() == TensuraCreativeTabs.SPAWN_EGG.get()) {
         event.accept((ItemLike)MILIM_SPAWN_EGG.get());
      }
   }

   private FoxItems() {
   }
}
