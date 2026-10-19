package com.mortal_cultivation.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;

public final class JiuyouWispParticle extends TextureSheetParticle {
   private boolean surfaced;

   private JiuyouWispParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
      super(level, x, y, z);
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.yd = 0.028 + this.random.nextDouble() * 0.014;
      this.xd = (this.random.nextDouble() - 0.5) * 0.004;
      this.zd = (this.random.nextDouble() - 0.5) * 0.004;
      this.quadSize = 0.035F + this.random.nextFloat() * 0.035F;
      this.lifetime = 140 + this.random.nextInt(60);
      this.rCol = 0.2F;
      this.gCol = 0.55F;
      this.bCol = 1.0F;
      this.alpha = 0.0F;
      this.pickSprite(sprites);
   }

   public void tick() {
      super.tick();
      if (!this.surfaced && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) {
         this.surfaced = true;
         this.lifetime = Math.min(this.lifetime, this.age + 18);
      }

      this.alpha = Math.max(0.0F, Math.min(1.0F, Math.min(this.age / 12.0F, (this.lifetime - this.age) / 18.0F))) * 0.85F;
   }

   protected int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new JiuyouWispParticle(level, x, y, z, this.sprites);
      }
   }
}
