package com.iafenvoy.iceandfire.world.feature;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.world.DangerousGeneration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class SeaSerpentSpawnFeature extends Feature<NoneFeatureConfiguration> implements DangerousGeneration {
   public SeaSerpentSpawnFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel world = context.level();
      RandomSource random = context.random();
      BlockPos pos = world.getHeightmapPos(Types.WORLD_SURFACE_WG, context.origin().offset(8, 0, 8));
      BlockPos oceanPos = world.getHeightmapPos(Types.OCEAN_FLOOR_WG, pos.offset(8, 0, 8));
      if (this.isFarEnoughFromSpawn(world, pos) && random.nextDouble() < (Double)IafCommonConfig.INSTANCE.seaSerpent.spawnChance.getValue()) {
         BlockPos spawnPos = oceanPos.offset(random.nextInt(10) - 5, random.nextInt(30), random.nextInt(10) - 5);
         if (world.getFluidState(spawnPos).getType() == Fluids.WATER) {
            SeaSerpentEntity serpent = (SeaSerpentEntity)((EntityType)IafEntities.SEA_SERPENT.get()).create(world.getLevel());
            assert serpent != null;
            serpent.onWorldSpawn(random);
            serpent.moveTo(spawnPos.getX() + 0.5F, spawnPos.getY() + 0.5F, spawnPos.getZ() + 0.5F, 0.0F, 0.0F);
            world.addFreshEntity(serpent);
         }
      }

      return true;
   }
}
