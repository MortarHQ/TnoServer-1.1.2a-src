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
import net.unusual.block_factorys_bosses.procedures.FallingDustParticleVisualScaleProcedure;

@OnlyIn(Dist.CLIENT)
public class FallingDustParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private float fall;
   private float angularVelocity;
   private float angularAcceleration;

   public static FallingDustParticle.FallingDustParticleProvider provider(SpriteSet spriteSet) {
      return new FallingDustParticle.FallingDustParticleProvider(spriteSet);
   }

   protected FallingDustParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.setSize(0.2F, 0.2F);
      this.lifetime = Math.max(1, 60 + (this.random.nextInt(4) - 2));
      this.gravity = 1.2F;
      this.fall = 0.8F;
      this.hasPhysics = true;
      this.xd = vx * 0.3;
      this.yd = Math.abs(vy) * 0.4;
      this.zd = vz * 0.3;
      this.angularVelocity = 0.3F;
      this.angularAcceleration = 0.0F;
      this.pickSprite(spriteSet);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      Level world = this.level;
      return super.getQuadSize(scale) * (float)FallingDustParticleVisualScaleProcedure.execute(this.age);
   }

   public void tick() {
      super.tick();
      this.oRoll = this.roll;
      this.roll = this.roll + this.angularVelocity;
      this.angularVelocity = this.angularVelocity + this.angularAcceleration;
      Level world = this.level;
      if (this.onGround) {
         this.fall = 0.75F * this.fall;
         this.yd = this.fall;
      }
   }

   public static class FallingDustParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public FallingDustParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new FallingDustParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
