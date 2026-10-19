package com.finderfeed.raids_enhanced.init;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RECreativeTabs {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "raidsenhanced");
   public static final Supplier<CreativeModeTab> MAIN_TAB = TABS.register(
      "main",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.raidsenhanced"))
         .icon(() -> new ItemStack((ItemLike)REItems.ZAPPER_STAFF.get()))
         .displayItems((params, output) -> {
            output.accept((ItemLike)REItems.ZAPPER_STAFF.get());
            output.accept((ItemLike)REItems.PLAYER_BLIMP.get());
            output.accept((ItemLike)REItems.BLIMP_PARTS.get());
            output.accept((ItemLike)REItems.HANDCANNON.get());
            output.accept((ItemLike)REItems.RAID_BLIMP_SPAWN_EGG.get());
            output.accept((ItemLike)REItems.GOLEM_OF_LAST_RESORT_SPAWN_EGG.get());
            output.accept((ItemLike)REItems.RAID_DRILL_SPAWN_EGG.get());
            output.accept((ItemLike)REItems.ZAPPER_SPAWN_EGG.get());
         })
         .build()
   );
}
