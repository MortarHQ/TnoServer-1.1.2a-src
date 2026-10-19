package com.progwml6.ironchest.common.creativetabs;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IronChestsCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "ironchest");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> IRON_CHEST_TAP = CREATIVE_MODE_TABS.register(
      "ironchest",
      () -> CreativeModeTab.builder()
         .withTabsBefore(new ResourceKey[]{CreativeModeTabs.SPAWN_EGGS})
         .title(Component.translatable("itemGroup.ironchest"))
         .icon(() -> new ItemStack((ItemLike)IronChestsBlocks.IRON_CHEST.get()))
         .displayItems((parameters, output) -> {
            for (Item item : IronChestsItems.ITEMS.getEntries().stream().map(DeferredHolder::value).toList()) {
               output.accept(item);
            }
         })
         .build()
   );
}
