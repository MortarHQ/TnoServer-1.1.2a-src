package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.data.DragonType;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public final class IafDragonTypes {
   public static final DragonType FIRE = register(
      "fire", w -> (DragonBaseEntity)((EntityType)IafEntities.FIRE_DRAGON.get()).create(w), IafItems.DRAGON_SKULL_FIRE, IafItems.SUMMONING_CRYSTAL_FIRE, false
   );
   public static final DragonType ICE = register(
      "ice", w -> (DragonBaseEntity)((EntityType)IafEntities.ICE_DRAGON.get()).create(w), IafItems.DRAGON_SKULL_ICE, IafItems.SUMMONING_CRYSTAL_ICE, true
   );
   public static final DragonType LIGHTNING = register(
      "lightning",
      w -> (DragonBaseEntity)((EntityType)IafEntities.LIGHTNING_DRAGON.get()).create(w),
      IafItems.DRAGON_SKULL_LIGHTNING,
      IafItems.SUMMONING_CRYSTAL_LIGHTNING,
      false
   );

   private static DragonType register(
      String name, Function<Level, DragonBaseEntity> hatchEntityCreator, Supplier<Item> skullItem, Supplier<Item> crystalItem, boolean piscivore
   ) {
      return (DragonType)Registry.register(
         IafRegistries.DRAGON_TYPE,
         ResourceLocation.fromNamespaceAndPath("iceandfire", name),
         new DragonType(name, hatchEntityCreator, skullItem, crystalItem, piscivore)
      );
   }

   public static void init() {
   }
}
