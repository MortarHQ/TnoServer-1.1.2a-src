package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;

public class AntibuilderBlockEntity extends BlockEntity {
   private static final int REVERT_CHANCE = 10;
   private static final int RADIUS = 4;
   private static final int DIAMETER = 9;
   private static final double PLAYER_RANGE = 16.0;
   private final RandomSource rand = RandomSource.create();
   private int tickCount;
   private boolean slowScan;
   private int ticksSinceChange;
   private BlockState @Nullable [] blockData;

   public AntibuilderBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.ANTIBUILDER.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, AntibuilderBlockEntity te) {
      if (te.anyPlayerInRange(level, pos)) {
         te.tickCount++;
         if (level.isClientSide()) {
            double x = pos.getX() + level.getRandom().nextFloat();
            double y = pos.getY() + level.getRandom().nextFloat();
            double z = pos.getZ() + level.getRandom().nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0, 0.0, 0.0);
            if (te.rand.nextInt(10) == 0) {
               te.makeRandomOutline(level, pos);
               te.makeRandomOutline(level, pos);
               te.makeRandomOutline(level, pos);
            }
         } else {
            if (te.blockData == null && level.isAreaLoaded(pos, 4)) {
               te.captureBlockData(level, pos);
               te.slowScan = true;
            }

            if (te.blockData != null && (!te.slowScan || te.tickCount % 20 == 0)) {
               if (te.scanAndRevertChanges(level, pos)) {
                  te.slowScan = false;
                  te.ticksSinceChange = 0;
               } else {
                  te.ticksSinceChange++;
                  if (te.ticksSinceChange > 20) {
                     te.slowScan = true;
                  }
               }
            }
         }
      } else {
         te.blockData = null;
         te.tickCount = 0;
      }
   }

   private void makeRandomOutline(Level level, BlockPos pos) {
      this.makeOutline(level, pos, this.rand.nextInt(12));
   }

   private void makeOutline(Level level, BlockPos pos, int outline) {
      double sx = pos.getX();
      double sy = pos.getY();
      double sz = pos.getZ();
      double dx = pos.getX();
      double dy = pos.getY();
      double dz = pos.getZ();
      switch (outline) {
         case 0:
         case 8:
            sx -= 4.0;
            dx += 5.0;
            sz -= 4.0;
            dz -= 4.0;
            break;
         case 1:
         case 9:
            sx -= 4.0;
            dx -= 4.0;
            sz -= 4.0;
            dz += 5.0;
            break;
         case 2:
         case 10:
            sx -= 4.0;
            dx += 5.0;
            sz += 5.0;
            dz += 5.0;
            break;
         case 3:
         case 11:
            sx += 5.0;
            dx += 5.0;
            sz -= 4.0;
            dz += 5.0;
            break;
         case 4:
            sx -= 4.0;
            dx -= 4.0;
            sz -= 4.0;
            dz -= 4.0;
            break;
         case 5:
            sx += 5.0;
            dx += 5.0;
            sz -= 4.0;
            dz -= 4.0;
            break;
         case 6:
            sx += 5.0;
            dx += 5.0;
            sz += 5.0;
            dz += 5.0;
            break;
         case 7:
            sx -= 4.0;
            dx -= 4.0;
            sz += 5.0;
            dz += 5.0;
      }

      switch (outline) {
         case 0:
         case 1:
         case 2:
         case 3:
            sy += 5.0;
            dy += 5.0;
            break;
         case 4:
         case 5:
         case 6:
         case 7:
            sy -= 4.0;
            dy += 5.0;
            break;
         case 8:
         case 9:
         case 10:
         case 11:
            sy -= 4.0;
            dy -= 4.0;
      }

      if (this.rand.nextBoolean()) {
         this.drawParticleLine(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dx, dy, dz);
      } else {
         this.drawParticleLine(level, sx, sy, sz, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
      }

      this.drawParticleLine(level, sx, sy, sz, dx, dy, dz);
   }

   private void drawParticleLine(Level level, double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
      int particles = 16;

      for (int i = 0; i < particles; i++) {
         double trailFactor = i / (particles - 1.0);
         double tx = srcX + (destX - srcX) * trailFactor + this.rand.nextFloat() * 0.005;
         double ty = srcY + (destY - srcY) * trailFactor + this.rand.nextFloat() * 0.005;
         double tz = srcZ + (destZ - srcZ) * trailFactor + this.rand.nextFloat() * 0.005;
         level.addParticle(DustParticleOptions.REDSTONE, tx, ty, tz, 0.0, 0.0, 0.0);
      }
   }

   private boolean scanAndRevertChanges(Level level, BlockPos pos) {
      int index = 0;
      boolean reverted = false;

      for (int x = -4; x <= 4; x++) {
         for (int y = -4; y <= 4; y++) {
            for (int z = -4; z <= 4; z++) {
               BlockState stateThere = level.getBlockState(pos.offset(x, y, z));
               if (this.blockData[index].getBlock() != stateThere.getBlock()) {
                  if (this.revertBlock(level, pos.offset(x, y, z), stateThere, this.blockData[index])) {
                     reverted = true;
                  } else {
                     this.blockData[index] = stateThere;
                  }
               }

               index++;
            }
         }
      }

      return reverted;
   }

   private boolean revertBlock(Level level, BlockPos pos, BlockState stateThere, BlockState replaceWith) {
      if (stateThere.isAir() && !replaceWith.blocksMotion()) {
         return false;
      }

      if (!(stateThere.getDestroySpeed(level, pos) < 0.0F) && !this.isUnrevertable(stateThere, replaceWith)) {
         if (this.rand.nextInt(10) == 0) {
            if (!replaceWith.isAir()) {
               replaceWith = ((Block)TFBlocks.ANTIBUILT_BLOCK.get()).defaultBlockState();
            }

            if (stateThere.isAir()) {
               level.levelEvent(2001, pos, Block.getId(replaceWith));
            }

            Block.updateOrDestroy(stateThere, replaceWith, level, pos, 2);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isUnrevertable(BlockState stateThere, BlockState replaceWith) {
      return stateThere.is(BlockTagGenerator.ANTIBUILDER_IGNORES) || replaceWith.is(BlockTagGenerator.ANTIBUILDER_IGNORES);
   }

   private void captureBlockData(Level level, BlockPos pos) {
      this.blockData = new BlockState[729];
      int index = 0;

      for (int x = -4; x <= 4; x++) {
         for (int y = -4; y <= 4; y++) {
            for (int z = -4; z <= 4; z++) {
               this.blockData[index] = level.getBlockState(pos.offset(x, y, z));
               index++;
            }
         }
      }
   }

   private boolean anyPlayerInRange(Level level, BlockPos pos) {
      return level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16.0);
   }
}
