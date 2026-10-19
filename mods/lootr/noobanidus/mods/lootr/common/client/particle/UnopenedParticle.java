package noobanidus.mods.lootr.common.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import noobanidus.mods.lootr.common.particle.ParticleColorOption;

public class UnopenedParticle extends TextureSheetParticle {
   public UnopenedParticle(ClientLevel level, ParticleColorOption type, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.lifetime = 30;
      this.alpha = 0.8F;
      this.xd = 0.0;
      this.yd = ySpeed;
      this.zd = 0.0;
      this.hasPhysics = false;
      this.quadSize = 0.12F;
      this.friction = 1.0F;
      int c1 = type.color1();
      this.rCol = (c1 >> 16 & 0xFF) / 255.0F;
      this.gCol = (c1 >> 8 & 0xFF) / 255.0F;
      this.bCol = (c1 & 0xFF) / 255.0F;
   }

   protected int getLightColor(float partialTick) {
      return 15728880 | super.getLightColor(partialTick) & 0xFF0000;
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         float f = (float)this.age / this.lifetime;
         f *= f;
         this.alpha = Math.max(0.0F, 0.8F - f);
      }
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public record Provider(SpriteSet spriteSet) implements ParticleProvider<ParticleColorOption> {
      public Particle createParticle(ParticleColorOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         UnopenedParticle particle = new UnopenedParticle(level, type, x, y, z, xSpeed, ySpeed, zSpeed);
         particle.pickSprite(this.spriteSet);
         return particle;
      }
   }
}
