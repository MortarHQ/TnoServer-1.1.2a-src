package com.iafenvoy.iceandfire.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class SerpentBubbleParticle extends TextureSheetParticle {
   protected SerpentBubbleParticle(
      ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider
   ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.setPos(x, y, z);
      this.quadSize = 0.3F;
      this.pickSprite(spriteProvider);
   }

   public static ParticleProvider<SimpleParticleType> factory(SpriteSet spriteProvider) {
      return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new SerpentBubbleParticle(world, x, y, z, 1.0, 1.0, 1.0, spriteProvider);
   }

   public int getLightColor(float partialTick) {
      return 240;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }
}
