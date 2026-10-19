package com.benji.netherman.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "netherman");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NETHERMAN_TAB = CREATIVE_MODE_TABS.register(
      "netherman_tab",
      () -> CreativeModeTab.builder()
         .icon(() -> new ItemStack((ItemLike)ModBlocks.AZAZEL_TROPHY.get()))
         .title(Component.translatable("creativetab.netherman_tab"))
         .displayItems((parameters, output) -> {
            for (DeferredHolder<Item, ? extends Item> registry : ModItems.getItems()) {
               if (!(registry.get() instanceof BlockItem)) {
                  output.accept((ItemLike)registry.get());
               }
            }

            for (DeferredHolder<Block, ? extends Block> registry : ModBlocks.getBlocks()) {
               if (((Block)registry.get()).asItem() != Items.AIR) {
                  output.accept((ItemLike)registry.get());
               }
            }
         })
         .build()
   );

   public static void init(IEventBus bus) {
      CREATIVE_MODE_TABS.register(bus);
   }
}
