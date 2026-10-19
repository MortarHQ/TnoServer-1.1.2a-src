package cn.leolezury.eternalstarlight.common.world.gen.feature;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class GlaciteFeature extends ESFeature<NoneFeatureConfiguration> {
   public GlaciteFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      RandomSource random = context.random();
      BlockPos toPos = pos.offset(
         random.nextInt(8, 10) * (random.nextBoolean() ? -1 : 1),
         random.nextInt(8, 10) * (random.nextBoolean() ? -1 : 1),
         random.nextInt(8, 10) * (random.nextBoolean() ? -1 : 1)
      );
      BlockHitResult result = level.clip(new ClipContext(pos.getCenter(), toPos.getCenter(), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()));
      if (result.getType() == Type.MISS) {
         return false;
      }

      toPos = result.getBlockPos();
      if (pos.distSqr(toPos) < 144.0) {
         return false;
      }

      MutableBlockPos placePos = new MutableBlockPos();

      for (int x = Math.min(pos.getX(), toPos.getX()); x <= Math.max(pos.getX(), toPos.getX()); x++) {
         for (int y = Math.min(pos.getY(), toPos.getY()); y <= Math.max(pos.getY(), toPos.getY()); y++) {
            for (int z = Math.min(pos.getZ(), toPos.getZ()); z <= Math.max(pos.getZ(), toPos.getZ()); z++) {
               if (ESMathUtil.distSqrBetweenLineAndDot(pos.getX(), pos.getY(), pos.getZ(), toPos.getX(), toPos.getY(), toPos.getZ(), x, y, z) < 1.5) {
                  placePos.set(x, y, z);
                  float iceChance = Mth.sqrt((float)placePos.distSqr(pos) / (float)pos.distSqr(toPos)) * 0.6F;
                  this.setBlockIfEmpty(
                     level,
                     placePos,
                     random.nextFloat() < iceChance ? Blocks.PACKED_ICE.defaultBlockState() : ((DropExperienceBlock)ESBlocks.GLACITE.get()).defaultBlockState()
                  );

                  for (Direction direction : Direction.values()) {
                     if (random.nextInt(5) == 0) {
                        this.setBlockIfEmpty(
                           level,
                           placePos.relative(direction),
                           random.nextFloat() < iceChance
                              ? Blocks.PACKED_ICE.defaultBlockState()
                              : ((DropExperienceBlock)ESBlocks.GLACITE.get()).defaultBlockState()
                        );
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
