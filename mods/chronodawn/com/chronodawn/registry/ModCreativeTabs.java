package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ModCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create("chronodawn", Registries.CREATIVE_MODE_TAB);
   public static final RegistrySupplier<CreativeModeTab> CHRONO_DAWN_TAB = TABS.register(
      "chronodawn",
      () -> CreativeTabRegistry.create(
         builder -> builder.title(Component.translatable("itemGroup.chronodawn.chronodawn"))
            .icon(() -> new ItemStack((ItemLike)ModItems.CLOCKSTONE.get()))
            .displayItems(ModItems::populateCreativeTab)
      )
   );

   public static void register() {
      TABS.register();
      ChronoDawn.LOGGER.debug("Registered ModCreativeTabs");
   }
}
