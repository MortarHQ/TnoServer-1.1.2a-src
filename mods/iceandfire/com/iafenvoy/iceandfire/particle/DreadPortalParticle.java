package com.iafenvoy.iceandfire.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class DreadPortalParticle extends TextureSheetParticle {
   protected DreadPortalParticle(
      ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider
   ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.setPos(x, y, z);
      this.pickSprite(spriteProvider);
   }

   public static ParticleProvider<SimpleParticleType> factory(SpriteSet spriteProvider) {
      return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new DreadPortalParticle(
         world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider
      );
   }

   public void render(VertexConsumer consumer, Camera camera, float tickDelta) {
      this.quadSize = 0.125F * (this.lifetime - this.age);
      this.quadSize *= 0.09F;
      super.render(consumer, camera, tickDelta);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public int getLightColor(float tint) {
      return 240;
   }
}
