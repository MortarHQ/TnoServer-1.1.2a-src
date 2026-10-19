package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@ParametersAreNonnullByDefault
public class BrittleSandstoneBlock extends Block {
   private final int dustColor;

   public BrittleSandstoneBlock(int dustColor, Properties properties) {
      super(properties);
      this.dustColor = dustColor;
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
      if (random.nextInt(36) == 0 && FallingBlock.isFree(level.getBlockState(blockPos.below()))) {
         double d = blockPos.getX() + random.nextDouble();
         double e = blockPos.getY() - 0.05;
         double f = blockPos.getZ() + random.nextDouble();
         level.addParticle(new BlockParticleOption((ParticleType)ParticleTypeModule.ANCIENT_DUST.get(), blockState), d, e, f, 0.0, 0.0, 0.0);
      }
   }

   public int getDustColor() {
      return this.dustColor;
   }
}
