package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.item.HighQualityHoneyItem;
import com.tno_tensura_extras.item.MagicCameraItem;
import com.tno_tensura_extras.item.TeleportMagicTubeItem;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ExtrasItems {
   private static final FoodProperties HIGH_QUALITY_HONEY_FOOD = new Builder()
      .nutrition(20)
      .saturationModifier(0.0F)
      .alwaysEdible()
      .usingConvertsTo(Items.BUCKET)
      .build();
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "tno_tensura_extras");
   public static final Supplier<Item> TELEPORT_MAGIC_TUBE = ITEMS.register("teleport_magic_tube", () -> new TeleportMagicTubeItem(new Properties().stacksTo(1)));
   public static final Supplier<Item> HIGH_QUALITY_HONEY = ITEMS.register(
      "high_quality_honey", () -> new HighQualityHoneyItem(new Properties().stacksTo(1).food(HIGH_QUALITY_HONEY_FOOD))
   );
   public static final Supplier<Item> MAGIC_CAMERA = ITEMS.register("magic_camera", () -> new MagicCameraItem(new Properties().stacksTo(1)));
   public static final Supplier<Item> DIABLO_SPAWN_EGG = ITEMS.register(
      "diablo_spawn_egg", () -> new DeferredSpawnEggItem(ExtrasEntityTypes.DIABLO, 2170151, 11938354, new Properties())
   );
}
