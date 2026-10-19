package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCreativeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@AutoRegister("yungscavebiomes")
public class CreativeTabModule {
   @AutoRegister("general")
   public static final AutoRegisterCreativeTab CREATIVE_TAB = AutoRegisterCreativeTab.builder()
      .title(Component.translatable("itemGroup.yungscavebiomes.general"))
      .iconItem(() -> new ItemStack((ItemLike)BlockModule.LAYERED_ANCIENT_SANDSTONE.get()))
      .entries((params, output) -> {
         output.accept((ItemLike)BlockModule.ICICLE.get());
         output.accept((ItemLike)BlockModule.FROST_LILY.get());
         output.accept((ItemLike)BlockModule.RARE_ICE.get());
         output.accept((ItemLike)BlockModule.ICE_SHEET.get());
         output.accept((ItemLike)BlockModule.ANCIENT_SAND.get());
         output.accept((ItemLike)BlockModule.SUSPICIOUS_ANCIENT_SAND.get());
         output.accept((ItemLike)BlockModule.ANCIENT_SANDSTONE.get());
         output.accept(BlockModule.ANCIENT_SANDSTONE.getStairs());
         output.accept(BlockModule.ANCIENT_SANDSTONE.getSlab());
         output.accept(BlockModule.ANCIENT_SANDSTONE.getWall());
         output.accept((ItemLike)BlockModule.BRITTLE_ANCIENT_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.LAYERED_ANCIENT_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.CUT_ANCIENT_SANDSTONE.get());
         output.accept(BlockModule.CUT_ANCIENT_SANDSTONE.getSlab());
         output.accept((ItemLike)BlockModule.CHISELED_ANCIENT_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.SMOOTH_ANCIENT_SANDSTONE.get());
         output.accept(BlockModule.SMOOTH_ANCIENT_SANDSTONE.getStairs());
         output.accept(BlockModule.SMOOTH_ANCIENT_SANDSTONE.getSlab());
         output.accept((ItemLike)BlockModule.BRITTLE_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.LAYERED_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.BRITTLE_RED_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.LAYERED_RED_SANDSTONE.get());
         output.accept((ItemLike)BlockModule.PRICKLY_PEACH_CACTUS.get());
         output.accept((ItemLike)ItemModule.PRICKLY_PEACH_ITEM.get());
         output.accept((ItemLike)BlockModule.PRICKLY_VINES.get());
         output.accept((ItemLike)ItemModule.ANCIENT_ARMOR_TRIM_SMITHING_TEMPLATE.get());
         output.accept((ItemLike)ItemModule.HOURGLASS_POTTERY_SHERD.get());
         output.accept((ItemLike)ItemModule.CLOCK_POTTERY_SHERD.get());
         output.accept((ItemLike)ItemModule.ICE_CUBE_SPAWN_EGG.get());
         output.accept((ItemLike)ItemModule.SAND_SNAPPER_SPAWN_EGG.get());
      })
      .build();
}
