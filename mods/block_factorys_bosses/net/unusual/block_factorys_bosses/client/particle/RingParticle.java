package net.unusual.block_factorys_bosses.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle.FacingCameraMode;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class RingParticle extends FlameParticle {
   private final SpriteSet spriteSet;

   public static RingParticle.RingParticleProvider provider(SpriteSet spriteSet) {
      return new RingParticle.RingParticleProvider(spriteSet);
   }

   protected RingParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteSet = spriteSet;
      this.quadSize = 1.5F;
      this.lifetime = 8;
      this.setColor(0.0F, 0.961F, 0.98F);
      this.setSpriteFromAge(spriteSet);
   }

   public FacingCameraMode getFacingCameraMode() {
      return FacingCameraMode.LOOKAT_Y;
   }

   public int getLightColor(float partialTick) {
      return 15728880;
   }

   public float getQuadSize(float scale) {
      return this.quadSize;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.spriteSet);
   }

   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Quaternionf quaternionf = new Quaternionf();
      quaternionf.rotateX((float)Math.toRadians(-90.0));
      this.renderRotatedQuad(buffer, renderInfo, quaternionf, partialTicks);
   }

   public static class RingParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public RingParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
