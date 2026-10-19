package com.chronodawn.entities.mobs;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ChronoUrsidEntity extends PolarBear {
   public ChronoUrsidEntity(EntityType<? extends ChronoUrsidEntity> entityType, Level level) {
      super(entityType, level);
   }

   public static Builder createAttributes() {
      return PolarBear.createAttributes();
   }

   public static boolean checkChronoUrsidSpawnRules(
      EntityType<ChronoUrsidEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return Animal.checkAnimalSpawnRules(entityType, level, spawnType, pos, random);
   }
}
