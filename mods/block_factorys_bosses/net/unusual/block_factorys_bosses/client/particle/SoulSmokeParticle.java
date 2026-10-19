package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoulSmokeParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final float random_roll = 1.2F * (float)Math.random();

   public static SoulSmokeParticle.SoulSmokeParticleProvider provider(SpriteSet spriteSet) {
      return new SoulSmokeParticle.SoulSmokeParticleProvider(spriteSet);
   }

   protected SoulSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(0.9F, 0.9F);
      this.lifetime = 15;
      this.gravity = -0.4F;
      this.hasPhysics = true;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
      this.pickSprite(spriteSet);
      this.setAlpha(0.4F);
      this.rCol = 0.476F;
      this.gCol = 0.95F;
      this.bCol = 0.95F;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      Level world = this.level;
      return super.getQuadSize(scale) * 8.0F * (this.age / 7.0F);
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      this.roll = this.random_roll;
      if (!this.removed) {
         this.setSprite(this.spriteSet.get(this.age / 1 % 16 + 1, 16));
      }
   }

   public static class SoulSmokeParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public SoulSmokeParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new SoulSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
