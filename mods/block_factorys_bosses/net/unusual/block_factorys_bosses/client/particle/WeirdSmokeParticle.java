package net.unusual.block_factorys_bosses.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeirdSmokeParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;
   private final Vec3 target;

   public static WeirdSmokeParticle.WeirdSmokeParticleProvider provider(SpriteSet spriteSet) {
      return new WeirdSmokeParticle.WeirdSmokeParticleProvider(spriteSet);
   }

   protected WeirdSmokeParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.quadSize = 3.0F;
      this.lifetime = 40;
      this.target = new Vec3(vx, vy, vz);
      this.setColor(0.0F, 0.961F, 0.98F);
      this.setAlpha(0.0F);
      this.pickSprite(spriteSet);
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public ParticleRenderType getRenderType() {
      return BRParticleRenderType.NORMAL_TRANSLUCENT;
   }

   public float getQuadSize(float scale) {
      return (40.0F - this.age) / 40.0F * (this.quadSize - 0.25F) + 0.25F;
   }

   public void tick() {
      super.tick();
      Vec3 pos = this.target.subtract(this.getPos()).scale(0.02F).add(this.getPos());
      this.setPos(pos.x, pos.y, pos.z);
      this.setAlpha(this.age < 20 ? this.age / 20.0F : (40.0F - this.age) / 20.0F);
   }

   public static class WeirdSmokeParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public WeirdSmokeParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new WeirdSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
