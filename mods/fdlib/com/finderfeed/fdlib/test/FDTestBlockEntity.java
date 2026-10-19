package com.finderfeed.fdlib.test;

import com.finderfeed.fdlib.init.FDBlockEntities;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile.FDBlockEntity;
import com.finderfeed.fdlib.util.client.particles.ball_particle.BallParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FDTestBlockEntity extends FDBlockEntity {
   public FDTestBlockEntity(BlockPos pos, BlockState state) {
      super(FDBlockEntities.TEST.get(), pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide && this.level.getGameTime() % 5L == 0L) {
         this.level
            .addParticle(
               BallParticleOptions.builder().color(1.0F, 0.4F, 0.1F).size(0.25F).brightness(30).scalingOptions(0, 0, 20).build(),
               true,
               this.getBlockPos().getX() + this.level.random.nextFloat(),
               this.getBlockPos().getY() + 2 + this.level.random.nextFloat(),
               this.getBlockPos().getZ() + this.level.random.nextFloat(),
               0.0,
               0.0,
               0.0
            );
      }
   }
}
