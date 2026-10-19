package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.BrazierBlock;
import twilightforest.enums.BrazierLight;
import twilightforest.init.TFBlockEntities;

public class BrazierBlockEntity extends BlockEntity {
   private static int tick = 0;

   public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
      super((BlockEntityType)TFBlockEntities.BRAZIER.get(), pos, blockState);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity entity) {
      if (level.isClientSide()) {
         if (((BrazierLight)state.getValue(BrazierBlock.LIGHT)).isLit()
            && ((BrazierLight)state.getValue(BrazierBlock.LIGHT)).getSmokeRate() > 0
            && tick % ((BrazierLight)state.getValue(BrazierBlock.LIGHT)).getSmokeRate() == 0) {
            BlockPos above = pos.above();
            level.addParticle(
               ParticleTypes.SMOKE,
               above.getX() + level.random.nextFloat() * 0.4F + 0.3F,
               above.getY() + 0.9F,
               above.getZ() + level.random.nextFloat() * 0.4F + 0.3F,
               0.0,
               0.05,
               0.0
            );
         }

         tick++;
      }
   }
}
