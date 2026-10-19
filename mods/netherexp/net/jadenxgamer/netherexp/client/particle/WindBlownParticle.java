package net.jadenxgamer.netherexp.client.particle;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WindBlownParticle extends TextureSheetParticle {
   protected WindBlownParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
      super(level, x, y, z);
      this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
      this.xd = (Double)JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED.get() + this.random.nextDouble() * 0.2;
      this.yd = Mth.randomBetween(level.random, 0.1F, 0.5F) * 0.1;
      this.zd = (Double)JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED.get() + this.random.nextDouble() * 0.2;
      this.lifetime = 20 + this.random.nextInt(10);
      this.quadSize = this.quadSize * (float)(0.75 * (Double)JNEConfigs.WINDY_ASH_SCALE_MULTIPLIER.get());
      this.roll = (float)(this.random.nextFloat() * (Math.PI * 2));
      this.hasPhysics = true;
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      if (this.age % 20 == 0) {
         this.xd = this.xd + (this.random.nextDouble() - 0.5) * 0.1;
         this.zd = this.zd + (this.random.nextDouble() - 0.5) * 0.1;
         this.xd = Mth.clamp(this.xd, 0.1, 0.5);
         this.zd = Mth.clamp(this.zd, 0.1, 0.5);
      }
   }

   @NotNull
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @Nullable
      public Particle createParticle(
         SimpleParticleType type, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ
      ) {
         return new WindBlownParticle(level, x, y, z, this.spriteSet);
      }
   }
}
