package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.EncasedSmokerBlock;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFParticleType;

public class TFSmokerBlockEntity extends BlockEntity {
   private long counter = 0L;

   public TFSmokerBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.SMOKER.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TFSmokerBlockEntity te) {
      if (level.isClientSide() && ++te.counter % 4L == 0L) {
         if (state.getBlock() == TFBlocks.ENCASED_SMOKER.get() && (Boolean)state.getValue(EncasedSmokerBlock.ACTIVE)) {
            te.particles(level, pos, te);
         } else if (state.getBlock() == TFBlocks.SMOKER.get()) {
            te.particles(level, pos, te);
         }
      }
   }

   public void particles(Level level, BlockPos pos, TFSmokerBlockEntity te) {
      level.addParticle(
         (ParticleOptions)TFParticleType.HUGE_SMOKE.get(),
         pos.getX() + 0.5,
         pos.getY() + 0.95,
         pos.getZ() + 0.5,
         Math.cos(te.counter / 10.0) * 0.05,
         0.25,
         Math.sin(te.counter / 10.0) * 0.05
      );
   }
}
