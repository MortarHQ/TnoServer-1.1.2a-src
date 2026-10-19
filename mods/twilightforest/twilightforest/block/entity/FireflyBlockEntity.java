package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFParticleType;

public class FireflyBlockEntity extends BlockEntity {
   public final float randRot = RandomSource.create().nextInt(4) * 90.0F;
   public int currentYaw;
   public float glowIntensity;
   private int yawDelay;
   private int desiredYaw;
   private boolean glowing;
   private int glowDelay;

   public FireflyBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)TFBlockEntities.FIREFLY.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, FireflyBlockEntity te) {
      if (level.isClientSide()) {
         if (te.anyPlayerInRange() && level.getRandom().nextInt(20) == 0) {
            te.spawnParticles();
         }

         if (te.yawDelay > 0) {
            te.yawDelay--;
         } else {
            if (te.currentYaw == 0 && te.desiredYaw == 0) {
               te.yawDelay = 200 + level.getRandom().nextInt(200);
               te.desiredYaw = level.getRandom().nextInt(15) - level.getRandom().nextInt(15);
            }

            if (te.currentYaw < te.desiredYaw) {
               te.currentYaw++;
            }

            if (te.currentYaw > te.desiredYaw) {
               te.currentYaw--;
            }

            if (te.currentYaw == te.desiredYaw) {
               te.desiredYaw = 0;
            }
         }

         if (te.glowDelay > 0) {
            te.glowDelay--;
         } else {
            if (te.glowing && te.glowIntensity >= 1.0) {
               te.glowing = false;
            }

            if (te.glowing && te.glowIntensity < 1.0) {
               te.glowIntensity += 0.05F;
            }

            if (!te.glowing && te.glowIntensity > 0.0F) {
               te.glowIntensity -= 0.05F;
            }

            if (!te.glowing && te.glowIntensity <= 0.0F) {
               te.glowing = true;
               te.glowIntensity = 0.0F;
               te.glowDelay = level.getRandom().nextInt(50);
            }
         }
      }
   }

   private boolean anyPlayerInRange() {
      return this.getLevel().hasNearbyAlivePlayer(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 0.5, this.getBlockPos().getZ() + 0.5, 16.0);
   }

   private void spawnParticles() {
      double rx = this.getBlockPos().getX() + this.getLevel().getRandom().nextFloat();
      double ry = this.getBlockPos().getY() + this.getLevel().getRandom().nextFloat();
      double rz = this.getBlockPos().getZ() + this.getLevel().getRandom().nextFloat();
      this.getLevel().addParticle((ParticleOptions)TFParticleType.FIREFLY.get(), rx, ry, rz, 0.0, 0.0, 0.0);
   }
}
