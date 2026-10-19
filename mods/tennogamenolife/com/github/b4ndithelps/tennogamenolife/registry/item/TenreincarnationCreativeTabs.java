package com.github.b4ndithelps.tennogamenolife.registry.item;

import com.github.b4ndithelps.tennogamenolife.registry.block.TenreincarnationBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TenreincarnationCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "tennogamenolife");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TENREINCARNATION_TAB = CREATIVE_MODE_TABS.register(
      "tenreincarnation_tab",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.tennogamenolife.tenreincarnation_tab"))
         .icon(() -> new ItemStack((ItemLike)Tenreincarnationitems.MECHANICAL_CORE.get()))
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)Tenreincarnationitems.MAG.get());
            output.accept((ItemLike)Tenreincarnationitems.MECHANICAL_CORE.get());
            output.accept((ItemLike)Tenreincarnationitems.STAR_ESSENCE.get());
            output.accept((ItemLike)Tenreincarnationitems.MUSIC_DISC_GAME_OF_LIFE.get());
            output.accept((ItemLike)Tenreincarnationitems.SICKLE.get());
            output.accept((ItemLike)Tenreincarnationitems.EX_MACHINAS_WEAPON.get());
            output.accept((ItemLike)TenreincarnationBlocks.CHESS_BLOCK.get());
         })
         .build()
   );

   public static void register(IEventBus modEventBus) {
      CREATIVE_MODE_TABS.register(modEventBus);
   }
}
