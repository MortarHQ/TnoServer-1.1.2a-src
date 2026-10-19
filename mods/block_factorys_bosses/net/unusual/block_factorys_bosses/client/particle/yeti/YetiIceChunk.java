package net.unusual.block_factorys_bosses.client.particle.yeti;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiIceChunk extends SubSteadyParticleEmitter {
   public static YetiIceChunk.YetiIceChunkProvider provider(SpriteSet spriteSet) {
      return new YetiIceChunk.YetiIceChunkProvider(spriteSet);
   }

   protected YetiIceChunk(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 20, 100, 12);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 30;
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 0.45F;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, Math.max(0.5F + 0.5F * -ageInSeconds, 0.0F));
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      quaternion.rotateZ(subParticle.random2 * 360.0F);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random) {
         @Override
         public void tick() {
            this.pos0 = new Vec3(this.pos.x, this.pos.y, this.pos.z);
            Vec3 acceleration = this.linearAcceleration.subtract(this.velocity.scale(this.linearDragCoefficient));
            this.velocity = this.velocity.add(acceleration.scale(0.05F));
            this.pos = this.pos.add(this.velocity.scale(0.05F));
         }
      };
      Vec3 pos = new Vec3(
         this.random.nextDouble() - this.random.nextDouble(),
         this.random.nextDouble() - this.random.nextDouble(),
         this.random.nextDouble() - this.random.nextDouble()
      );
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.sprite = subParticle.set.get(this.random);
      subParticle.linearAcceleration = new Vec3(0.0, -7.0, 0.0);
      subParticle.setVelocity(pos.normalize(), 3.0F);
      return subParticle;
   }

   public static class YetiIceChunkProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public YetiIceChunkProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new YetiIceChunk(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
