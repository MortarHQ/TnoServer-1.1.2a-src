package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.item.PricklyPeachItem;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;

@AutoRegister("yungscavebiomes")
public class ItemModule {
   @AutoRegister("ice_cube_spawn_egg")
   public static final AutoRegisterItem ICE_CUBE_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getIceCubeSpawnEggItem());
   @AutoRegister("sand_snapper_spawn_egg")
   public static final AutoRegisterItem SAND_SNAPPER_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getSandSnapperSpawnEggItem());
   @AutoRegister("prickly_peach")
   public static final AutoRegisterItem PRICKLY_PEACH_ITEM = AutoRegisterItem.of(
      () -> new PricklyPeachItem(new Properties().food(new Builder().nutrition(4).saturationModifier(0.3F).build()))
   );
   @AutoRegister("ancient_armor_trim_smithing_template")
   public static final AutoRegisterItem ANCIENT_ARMOR_TRIM_SMITHING_TEMPLATE = AutoRegisterItem.of(
      () -> SmithingTemplateItem.createArmorTrimTemplate(TrimPatternsModule.ANCIENT, new FeatureFlag[0])
   );
   @AutoRegister("hourglass_pottery_sherd")
   public static final AutoRegisterItem HOURGLASS_POTTERY_SHERD = AutoRegisterItem.of(() -> new Item(new Properties()));
   @AutoRegister("clock_pottery_sherd")
   public static final AutoRegisterItem CLOCK_POTTERY_SHERD = AutoRegisterItem.of(() -> new Item(new Properties()));

   @AutoRegister("init")
   private static void addCompostables() {
      AutoRegisterUtils.addCompostableItem(() -> ((Block)BlockModule.PRICKLY_VINES.get()).asItem(), 0.5F);
      AutoRegisterUtils.addCompostableItem(() -> ((Block)BlockModule.PRICKLY_PEACH_CACTUS.get()).asItem(), 0.5F);
      AutoRegisterUtils.addCompostableItem(() -> (Item)PRICKLY_PEACH_ITEM.get(), 0.65F);
   }
}
