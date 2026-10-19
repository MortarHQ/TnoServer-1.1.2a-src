package com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.util.DistributionUtils;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SandstormClientData {
   private static final double SANDSTORM_NOISE_FREQUENCY_Y_RELATIVE = 0.3333333333333333;
   private static final double SANDSTORM_NOISE_FREQUENCY_XZ = 0.0078125;
   private static final double SANDSTORM_NOISE_FREQUENCY_Y = 0.0026041666666666665;
   private static final double SANDSTORM_NOISE_FREQUENCY_TIME = 0.0013020833333333333;
   private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y_RELATIVE = 0.33333334F;
   private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_XZ = 0.9F;
   private static final float SANDSTORM_PARTICLE_SPEED_SCALAR_Y = 0.3F;
   private static final long SEED_2_OFFSET = 7160011195410019394L;
   private boolean isSandstormActive = false;
   private int sandstormTime = 0;
   private int totalSandstormDuration = 0;
   private long sandstormSeed;

   public Vector3f getSandstormParticleSpeedVector(double worldX, double worldY, double worldZ, Vector3f prevParticleSpeedVector) {
      Vector3f output = new Vector3f(prevParticleSpeedVector);
      Vector4f noise4GradientA = new Vector4f();
      Vector4f noise4GradientB = new Vector4f();
      OpenSimplex2S.noise4Grad_ImproveXYZ_ImproveXZ(
         this.sandstormSeed,
         worldX * 0.0078125,
         worldY * 0.0026041666666666665,
         worldZ * 0.0078125,
         this.sandstormTime * 0.0013020833333333333,
         noise4GradientA
      );
      OpenSimplex2S.noise4Grad_ImproveXYZ_ImproveXZ(
         this.sandstormSeed + 7160011195410019394L,
         worldX * 0.0078125,
         worldY * 0.0026041666666666665,
         worldZ * 0.0078125,
         this.sandstormTime * 0.0013020833333333333,
         noise4GradientB
      );
      output.set(
         noise4GradientA.y() * noise4GradientB.z() - noise4GradientA.z() * noise4GradientB.y(),
         noise4GradientA.z() * noise4GradientB.x() - noise4GradientA.x() * noise4GradientB.z(),
         noise4GradientA.x() * noise4GradientB.y() - noise4GradientA.y() * noise4GradientB.x()
      );
      output.mul(0.9F, 0.3F, 0.9F);
      output.normalize();
      output.mul(0.9F, 0.3F, 0.9F);
      if (this.sandstormTime > this.totalSandstormDuration - 20) {
         output.mul(
            (this.totalSandstormDuration - this.sandstormTime) / 20.0F,
            (this.totalSandstormDuration - this.sandstormTime) / 20.0F,
            (this.totalSandstormDuration - this.sandstormTime) / 20.0F
         );
      }

      return output;
   }

   public void tick() {
      if (YungsCaveBiomesCommon.DEBUG_LOG) {
         YungsCaveBiomesCommon.LOGGER.info("Sandstorm CLIENT {} / {} time", this.sandstormTime, this.totalSandstormDuration);
      }

      if (this.isSandstormActive) {
         if (this.sandstormTime > 0) {
            this.sandstormTime--;
         } else {
            this.isSandstormActive = false;
         }
      }
   }

   public void addSandstormParticles(ClientLevel clientLevel, int x, int y, int z, int maxDistance, RandomSource random, MutableBlockPos pos) {
      if (this.isSandstormActive) {
         double chance = Mth.clamp(Mth.lerp((this.totalSandstormDuration - this.sandstormTime) / 20.0, 0.0, 0.03), 0.0, 0.03);
         if (!(random.nextDouble() > chance)) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
               boolean isPlayerInLostCaves = clientLevel.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES);
               if (isPlayerInLostCaves) {
                  this.addSandstormParticle(clientLevel, x, y, z, false, maxDistance, random, pos);
               }
            }
         }
      }
   }

   public void addExtraSandstormParticles(ClientLevel clientLevel, int x, int y, int z) {
      if (YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles) {
         if (this.isSandstormActive) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
               boolean isPlayerInLostCaves = clientLevel.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES);
               if (isPlayerInLostCaves) {
                  MutableBlockPos mutable = new MutableBlockPos();

                  for (int i = 0; i < 667; i++) {
                     double chance = Mth.clamp(Mth.lerp((this.totalSandstormDuration - this.sandstormTime) / 20.0, 0.0, 0.03), 0.0, 0.03);
                     if (!(clientLevel.getRandom().nextDouble() > chance)) {
                        this.addSandstormParticle(clientLevel, x, y, z, true, 64, clientLevel.getRandom(), mutable);
                     }
                  }
               }
            }
         }
      }
   }

   private void addSandstormParticle(
      ClientLevel clientLevel, int x, int y, int z, boolean overrideLimiter, int maxDistance, RandomSource random, MutableBlockPos pos
   ) {
      Vector3d particlePos = DistributionUtils.ellipsoidCenterBiasedSpread(maxDistance, maxDistance, random, Vector3d::new);
      particlePos.x += x;
      particlePos.y += y;
      particlePos.z += z;
      pos.set(Mth.floor(particlePos.x), Mth.floor(particlePos.y), Mth.floor(particlePos.z));
      BlockState blockState = clientLevel.getBlockState(pos);
      if (blockState.isAir()) {
         clientLevel.addParticle(
            (ParticleOptions)ParticleTypeModule.SANDSTORM.get(), overrideLimiter, particlePos.x, particlePos.y, particlePos.z, 0.0, 0.0, 0.0
         );
      }
   }

   public boolean isSandstormActive() {
      return this.isSandstormActive;
   }

   public void setSandstormActive(boolean sandstormActive) {
      this.isSandstormActive = sandstormActive;
   }

   public void setSandstormTime(int time) {
      this.sandstormTime = time;
   }

   public void setSandstormSeed(long sandstormSeed) {
      this.sandstormSeed = sandstormSeed;
   }

   public void setTotalSandstormDuration(int totalSandstormDuration) {
      this.totalSandstormDuration = totalSandstormDuration;
   }
}
