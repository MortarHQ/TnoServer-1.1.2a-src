package net.pixeldreamstudios.mythika.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.pixeldreamstudios.mythika.platform.Services;

public final class TabRegistry {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create("mythika", Registries.CREATIVE_MODE_TAB);
   public static final RegistrySupplier<CreativeModeTab> TAB = TABS.register(
      "mythika_tab",
      () -> Services.REGISTRY
         .createCreativeTab(
            Component.translatable("category.mythika"),
            () -> new ItemStack((ItemLike)ItemRegistry.MINOTAUR_SPAWN_EGG.get()),
            (params, output) -> ItemRegistry.tabContents().forEach(item -> output.accept((ItemLike)item.get()))
         )
   );

   private TabRegistry() {
   }

   public static void init() {
      TABS.register();
   }
}
