package com.blackgear.vanillabackport.client.level.particles;

import com.blackgear.vanillabackport.client.level.particles.particleoptions.TrailParticleOption;
import com.blackgear.vanillabackport.core.util.ColorUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrailParticle extends TextureSheetParticle {
   private final Vec3 target;

   protected TrailParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Vec3 target, int color) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      color = ColorUtils.scaleRGB(
         color, 0.875F + this.random.nextFloat() * 0.25F, 0.875F + this.random.nextFloat() * 0.25F, 0.875F + this.random.nextFloat() * 0.25F
      );
      this.rCol = ARGB32.red(color) / 255.0F;
      this.gCol = ARGB32.green(color) / 255.0F;
      this.bCol = ARGB32.blue(color) / 255.0F;
      this.quadSize = 0.26F;
      this.target = target;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         int ageInTicks = this.lifetime - this.age;
         double deltaTime = 1.0 / ageInTicks;
         this.x = Mth.lerp(deltaTime, this.x, this.target.x());
         this.y = Mth.lerp(deltaTime, this.y, this.target.y());
         this.z = Mth.lerp(deltaTime, this.z, this.target.z());
      }
   }

   protected int getLightColor(float partialTick) {
      return 15728880;
   }

   @OnlyIn(Dist.CLIENT)
   public record Provider(SpriteSet sprites) implements ParticleProvider<TrailParticleOption> {
      public Particle createParticle(TrailParticleOption option, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         TrailParticle particle = new TrailParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, option.target(), option.color());
         particle.pickSprite(this.sprites);
         particle.setLifetime(option.duration());
         return particle;
      }
   }
}
