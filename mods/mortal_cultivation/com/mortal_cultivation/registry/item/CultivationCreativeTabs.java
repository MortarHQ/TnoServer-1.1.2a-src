package com.mortal_cultivation.registry.item;

import com.mortal_cultivation.registry.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CultivationCreativeTabs {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "mortal_cultivation");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CULTIVATION_TAB = CREATIVE_MODE_TABS.register(
      "cultivation_tab",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.mortal_cultivation.cultivation_tab"))
         .icon(() -> new ItemStack((ItemLike)BlockRegistry.ALCHEMY_FURNACE.get()))
         .displayItems((parameters, output) -> {
            output.accept((ItemLike)CultivationItems.SPIRIT_FRUIT.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_LEAVES.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_FRUIT_LEAVES.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_SAPLING.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_RECOVERY_HERB.get());
            output.accept((ItemLike)CultivationItems.MILLENNIUM_GINSENG.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_MONKEY_SPAWN_EGG.get());
            output.accept((ItemLike)CultivationItems.AZURE_MOSS_QILIN_SPAWN_EGG.get());
            output.accept((ItemLike)CultivationItems.IMMORTAL_HERB.get());
            output.accept((ItemLike)CultivationItems.LIGHTNING_WOOD_LINGZHI.get());
            output.accept((ItemLike)CultivationItems.NINE_YIN_LOTUS.get());
            output.accept((ItemLike)CultivationItems.NINE_YIN_LOTUS_LEAF.get());
            output.accept((ItemLike)CultivationItems.TALISMAN_PAPER.get());
            output.accept((ItemLike)CultivationItems.BRUSH.get());
            output.accept((ItemLike)CultivationItems.CINNABAR_BLOOD_INK.get());
            output.accept((ItemLike)CultivationItems.BRUSH_AND_INK.get());
            output.accept((ItemLike)CultivationItems.THUNDER_TALISMAN.get());
            output.accept((ItemLike)CultivationItems.NOURISHING_QI_TALISMAN.get());
            output.accept((ItemLike)CultivationItems.PUPPET_TALISMAN.get());
            output.accept((ItemLike)CultivationItems.MONSTER_GATHERING_TALISMAN.get());
            output.accept((ItemLike)CultivationItems.FASTING_TALISMAN.get());
            output.accept((ItemLike)CultivationItems.FOUNDATION_BUILDING_PILL.get());
            output.accept((ItemLike)CultivationItems.STELLAR_CORE_PILL.get());
            output.accept((ItemLike)CultivationItems.GIANT_SPIRIT_PILL.get());
            output.accept((ItemLike)CultivationItems.STRENGTH_PILL.get());
            output.accept((ItemLike)CultivationItems.DEMON_SPIRIT_PILL.get());
            output.accept((ItemLike)CultivationItems.MYSTIC_VOID_ELIXIR.get());
            output.accept((ItemLike)CultivationItems.VOID_REFINEMENT_PILL.get());
            output.accept((ItemLike)CultivationItems.HEAVEN_CLEANSING_ELIXIR.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_CLEANSING_PILL.get());
            output.accept((ItemLike)CultivationItems.TORN_PAGE.get());
            output.accept((ItemLike)CultivationItems.ANCIENT_BOOK.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_GLASSES.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_COMPASS.get());
            output.accept((ItemLike)CultivationItems.ZHAO_YING_ZHU.get());
            output.accept((ItemLike)CultivationItems.ALCHEMY_FURNACE.get());
            output.accept((ItemLike)CultivationItems.ANCIENT_SCHOLAR_TABLE.get());
            output.accept((ItemLike)CultivationItems.ANCIENT_STELE.get());
            output.accept((ItemLike)CultivationItems.RUNE_TABLE.get());
            output.accept((ItemLike)CultivationItems.THE_SUPREME_ALCHEMY_CANON.get());
            output.accept((ItemLike)CultivationItems.SWORD_FLIGHT_MANUAL.get());
            output.accept((ItemLike)CultivationItems.MYRIAD_SWORDS_RETURN_MANUAL.get());
            output.accept((ItemLike)CultivationItems.SIZE_TRANSFORMATION_MANUAL.get());
            output.accept((ItemLike)CultivationItems.TALISMAN_MANUAL.get());
            output.accept((ItemLike)CultivationItems.MUD_ESSENCE_CHARCOAL.get());
            output.accept((ItemLike)CultivationItems.YELLOW_ESSENCE_CHARCOAL.get());
            output.accept((ItemLike)CultivationItems.BLACK_ESSENCE_CHARCOAL.get());
            output.accept((ItemLike)CultivationItems.VOID_ESSENCE_CHARCOAL.get());
            output.accept((ItemLike)CultivationItems.PRIME_ESSENCE_CHARCOAL.get());
            output.accept((ItemLike)CultivationItems.SPIRIT_CHARCOAL_ORE.get());
            output.accept((ItemLike)CultivationItems.DEEPSLATE_SPIRIT_CHARCOAL_ORE.get());
            output.accept((ItemLike)CultivationItems.CRYSTAL_OF_COMBUSTION.get());
            output.accept((ItemLike)CultivationItems.BREAK_AND_REBUILD_MANUAL.get());
         })
         .build()
   );

   public static void register(IEventBus modEventBus) {
      CREATIVE_MODE_TABS.register(modEventBus);
   }
}
