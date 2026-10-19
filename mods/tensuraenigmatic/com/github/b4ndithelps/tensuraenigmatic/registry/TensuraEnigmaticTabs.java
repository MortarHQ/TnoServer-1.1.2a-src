package com.github.b4ndithelps.tensuraenigmatic.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TensuraEnigmaticTabs {
   private static final String ENIGMATIC_MOD_ID = "enigmaticlegacyplus";
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "tensuraenigmatic");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register(
      "main",
      () -> CreativeModeTab.builder()
         .title(getMainTabTitle())
         .icon(TensuraEnigmaticItems.RING_OF_ARCANE_SOURCE::toStack)
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)TensuraEnigmaticItems.RING_OF_ARCANE_SOURCE.get());
            output.accept((ItemLike)TensuraEnigmaticItems.RING_OF_THE_ELVES.get());
            output.accept((ItemLike)TensuraEnigmaticItems.MANA_RING.get());
            output.accept((ItemLike)TensuraEnigmaticItems.POWER_OF_FORCE.get());
         })
         .build()
   );

   private static Component getMainTabTitle() {
      return ModList.get().isLoaded("enigmaticlegacyplus")
         ? Component.translatable("itemGroup.tensuraenigmatic.main")
         : Component.translatable("itemGroup.tensuraenigmatic.accessories");
   }

   private TensuraEnigmaticTabs() {
   }
}
