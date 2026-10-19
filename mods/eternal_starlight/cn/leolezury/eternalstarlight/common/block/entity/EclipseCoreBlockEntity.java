package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.EclipseCoreBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EclipseCoreBlockEntity extends AbstractDuskLightBlockEntity {
   private int ticksLeftNorth = 0;
   private int ticksLeftSouth = 0;
   private int ticksLeftWest = 0;
   private int ticksLeftEast = 0;
   private float oldEclipseProgress;
   private float eclipseProgress;
   private int ticks;

   public EclipseCoreBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(ESBlockEntities.ECLIPSE_CORE.get(), blockPos, blockState);
   }

   @Override
   public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
      switch (sourceDir) {
         case NORTH:
            this.ticksLeftNorth = 5;
            break;
         case SOUTH:
            this.ticksLeftSouth = 5;
            break;
         case WEST:
            this.ticksLeftWest = 5;
            break;
         case EAST:
            this.ticksLeftEast = 5;
      }
   }

   public float getEclipseProgress(float partialTick) {
      return Mth.lerp(partialTick, this.oldEclipseProgress, this.eclipseProgress);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, EclipseCoreBlockEntity entity) {
      entity.ticks++;
      if (level.isClientSide) {
         entity.oldEclipseProgress = entity.eclipseProgress;
         if ((Boolean)state.getValue(EclipseCoreBlock.LIT)) {
            entity.eclipseProgress = Mth.clamp(entity.eclipseProgress + 0.08F, 0.0F, 1.0F);
         } else {
            entity.eclipseProgress = Mth.clamp(entity.eclipseProgress - 0.2F, 0.0F, 1.0F);
         }
      } else {
         entity.ticksLeftNorth--;
         if (entity.ticksLeftNorth < 0) {
            entity.ticksLeftNorth = 0;
         }

         entity.ticksLeftSouth--;
         if (entity.ticksLeftSouth < 0) {
            entity.ticksLeftSouth = 0;
         }

         entity.ticksLeftWest--;
         if (entity.ticksLeftWest < 0) {
            entity.ticksLeftWest = 0;
         }

         entity.ticksLeftEast--;
         if (entity.ticksLeftEast < 0) {
            entity.ticksLeftEast = 0;
         }

         entity.ticksLeft = Math.min(Math.min(entity.ticksLeftNorth, entity.ticksLeftSouth), Math.min(entity.ticksLeftWest, entity.ticksLeftEast));
      }

      boolean oldLit = entity.isLit();
      AbstractDuskLightBlockEntity.tick(level, pos, state, entity);
      if (level instanceof ServerLevel serverLevel && !oldLit && entity.isLit()) {
         if (entity.ticks > 5) {
            ScreenShakeVfx.createInstance(serverLevel.dimension(), entity.getBlockPos().getCenter(), 45.0F, 50, 0.24F, 0.24F, 4.5F, 5.0F).send(serverLevel);
         }

         if (!(Boolean)state.getValue(EclipseCoreBlock.LIT)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(EclipseCoreBlock.LIT, true));
         }
      }
   }

   @Override
   protected boolean isFaceActivated(BlockState state, Direction direction) {
      return direction == Direction.UP;
   }
}
