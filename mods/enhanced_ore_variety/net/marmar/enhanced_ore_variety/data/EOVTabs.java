package net.marmar.enhanced_ore_variety.data;

import net.marmar.enhanced_ore_variety.block.EOVBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EOVTabs {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "enhanced_ore_variety");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ORE_VARIANTS = TABS.register(
      "ore_variants",
      () -> CreativeModeTab.builder()
         .icon(() -> new ItemStack(EOVBlocks.GRANITE_IRON_ORE))
         .title(Component.translatable("tabs.enhanced_ore_variety.ore_variants"))
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)EOVBlocks.GRANITE_COAL_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_COAL_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_COAL_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_COAL_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_COAL_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_COPPER_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_COPPER_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_COPPER_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_COPPER_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_COPPER_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_IRON_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_IRON_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_IRON_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_IRON_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_IRON_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_GOLD_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_GOLD_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_GOLD_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_GOLD_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_GOLD_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_REDSTONE_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_REDSTONE_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_REDSTONE_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_REDSTONE_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_REDSTONE_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_EMERALD_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_EMERALD_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_EMERALD_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_EMERALD_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_EMERALD_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_LAPIS_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_LAPIS_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_LAPIS_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_LAPIS_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_LAPIS_ORE.get());
            output.accept((ItemLike)EOVBlocks.GRANITE_DIAMOND_ORE.get());
            output.accept((ItemLike)EOVBlocks.DIORITE_DIAMOND_ORE.get());
            output.accept((ItemLike)EOVBlocks.ANDESITE_DIAMOND_ORE.get());
            output.accept((ItemLike)EOVBlocks.TUFF_DIAMOND_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_DIAMOND_ORE.get());
            output.accept((ItemLike)EOVBlocks.BLACKSTONE_QUARTZ_ORE.get());
         })
         .build()
   );

   public static void register(IEventBus eventBus) {
      TABS.register(eventBus);
   }
}
