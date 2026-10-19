package com.iafenvoy.iceandfire.world.feature;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.data.DragonColor;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.uranus.util.RandomHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DragonSkeletonSpawnFeature extends Feature<NoneFeatureConfiguration> {
   protected final EntityType<? extends DragonBaseEntity> dragonType;

   public DragonSkeletonSpawnFeature(EntityType<? extends DragonBaseEntity> dt, Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
      this.dragonType = dt;
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel world = context.level();
      RandomSource random = context.random();
      BlockPos pos = world.getHeightmapPos(Types.OCEAN_FLOOR_WG, context.origin().offset(8, 0, 8));
      if ((Boolean)IafCommonConfig.INSTANCE.dragon.generateSkeletons.getValue()
         && random.nextDouble() < (Double)IafCommonConfig.INSTANCE.dragon.generateSkeletonChance.getValue()) {
         DragonBaseEntity dragon = (DragonBaseEntity)this.dragonType.create(world.getLevel());
         assert dragon != null;
         dragon.setPos(pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F);
         int age = 10 + random.nextInt(100);
         dragon.growDragon(age);
         dragon.modelDeadProgress = 20.0F;
         dragon.setModelDead(true);
         dragon.setDeathStage(age / 10);
         dragon.setYRot(random.nextInt(360));
         dragon.setVariant(((DragonColor)RandomHelper.randomOne(dragon.dragonType.colors())).getName());
         world.addFreshEntity(dragon);
      }

      return true;
   }
}
