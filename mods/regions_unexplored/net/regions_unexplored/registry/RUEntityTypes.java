package net.regions_unexplored.registry;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.regions_unexplored.entity.ashen.Ashen;
import net.regions_unexplored.entity.custom.RuBoat;
import net.regions_unexplored.entity.custom.RuChestBoat;
import net.regions_unexplored.module.platform.EntityHelper;
import net.regions_unexplored.module.platform.Registrar;

public interface RUEntityTypes {
   Supplier<EntityType<RuBoat>> BOAT = register(
      "boat", () -> Builder.of(RuBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("boat")
   );
   Supplier<EntityType<RuChestBoat>> CHEST_BOAT = register(
      "chest_boat", () -> Builder.of(RuChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("chest_boat")
   );
   Supplier<EntityType<Ashen>> ASHEN = register(
      "ashen",
      () -> Builder.of(Ashen::new, MobCategory.MONSTER)
         .sized(0.6F, 1.95F)
         .eyeHeight(1.74F)
         .passengerAttachments(new float[]{2.075F})
         .ridingOffset(-0.7F)
         .clientTrackingRange(8)
         .build("ashen")
   );

   static <T extends Entity> Supplier<EntityType<T>> register(String name, Supplier<EntityType<T>> type) {
      return Registrar.register(BuiltInRegistries.ENTITY_TYPE, name, type);
   }

   static void init() {
      EntityHelper.registerPlacement(ASHEN, SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
      EntityHelper.registerAttributes(ASHEN, Zombie::createAttributes);
   }

   static void initPostRegistryFreeze() {
   }
}
