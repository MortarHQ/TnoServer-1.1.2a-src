package net.mcreator.rpgstylemorebosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RpgStyleMoreBossesModTabs {
   public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "rpg_style_more_bosses");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RPGS_STYLE_MORE_DUNGEONS = REGISTRY.register(
      "rpgs_style_more_dungeons",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.rpg_style_more_bosses.rpgs_style_more_dungeons"))
         .icon(() -> new ItemStack((ItemLike)RpgStyleMoreBossesModItems.THE_EXOFRAME_KEY.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept(((Block)RpgStyleMoreBossesModBlocks.DUNGEON_BRICKS_CUBES.get()).asItem());
            tabData.accept((ItemLike)RpgStyleMoreBossesModItems.BEACON_SPAWN_EGG.get());
            tabData.accept((ItemLike)RpgStyleMoreBossesModItems.EXOFRAME_PART.get());
            tabData.accept((ItemLike)RpgStyleMoreBossesModItems.THE_EXOFRAME_KEY.get());
         })
         .build()
   );
}
