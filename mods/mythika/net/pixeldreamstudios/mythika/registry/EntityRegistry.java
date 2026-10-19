package net.pixeldreamstudios.mythika.registry;

import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.CyclopsEntity;
import net.pixeldreamstudios.mythika.entity.MinotaurEntity;
import net.pixeldreamstudios.mythika.entity.SatyrEntity;

public final class EntityRegistry {
   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create("mythika", Registries.ENTITY_TYPE);
   public static final RegistrySupplier<EntityType<CyclopsEntity>> CYCLOPS = ENTITIES.register(
      "cyclops",
      () -> net.minecraft.world.entity.EntityType.Builder.of(CyclopsEntity::new, MobCategory.MONSTER)
         .sized(1.8F, 4.0F)
         .eyeHeight(3.84F)
         .clientTrackingRange(10)
         .build(Mythika.id("cyclops").toString())
   );
   public static final RegistrySupplier<EntityType<MinotaurEntity>> MINOTAUR = ENTITIES.register(
      "minotaur",
      () -> net.minecraft.world.entity.EntityType.Builder.of(MinotaurEntity::new, MobCategory.MONSTER)
         .sized(1.8F, 3.7F)
         .eyeHeight(3.0F)
         .clientTrackingRange(9)
         .build(Mythika.id("minotaur").toString())
   );
   public static final RegistrySupplier<EntityType<SatyrEntity>> SATYR = ENTITIES.register(
      "satyr",
      () -> net.minecraft.world.entity.EntityType.Builder.of(SatyrEntity::new, MobCategory.CREATURE)
         .sized(0.8F, 1.95F)
         .eyeHeight(1.41F)
         .clientTrackingRange(8)
         .build(Mythika.id("satyr").toString())
   );

   private EntityRegistry() {
   }

   public static void init() {
      ENTITIES.register();
   }

   public static void forEachAttribute(BiConsumer<EntityType<? extends LivingEntity>, Builder> consumer) {
      consumer.accept(CYCLOPS.get(), CyclopsEntity.createAttributes());
      consumer.accept(MINOTAUR.get(), MinotaurEntity.createAttributes());
      consumer.accept(SATYR.get(), SatyrEntity.createAttributes());
   }

   public static void forEachSpawnPlacement(EntityRegistry.SpawnPlacementConsumer consumer) {
      consumer.accept(CYCLOPS.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
      consumer.accept(MINOTAUR.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
      consumer.accept(SATYR.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, SatyrEntity::checkSatyrSpawnRules);
   }

   public interface SpawnPlacementConsumer {
      <T extends Mob> void accept(EntityType<T> var1, SpawnPlacementType var2, Types var3, SpawnPredicate<T> var4);
   }
}
