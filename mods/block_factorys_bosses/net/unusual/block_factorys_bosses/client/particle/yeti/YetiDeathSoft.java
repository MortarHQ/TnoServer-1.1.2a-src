package net.unusual.block_factorys_bosses.client.particle.yeti;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiDeathSoft extends SubSteadyParticleEmitter {
   private static final LinearCurve GROW_FACTOR = new LinearCurve(
      new float[]{0.67F, 0.86F, 1.0F, 1.0F, 1.0F, 1.0F, 0.84F, 0.55F, 0.0F},
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.73F, 0.94F, 1.0F, 1.0F), 0.0F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.77F, 0.94F, 1.0F, 0.42F), 0.07F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.63F, 0.85F, 0.93F, 0.0F), 0.23F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.63F, 0.85F, 0.93F, 0.0F), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime + subParticle.random2
   );

   public static YetiDeathSoft.YetiDeathSoftProvider provider(SpriteSet spriteSet) {
      return new YetiDeathSoft.YetiDeathSoftProvider(spriteSet);
   }

   protected YetiDeathSoft(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 160, 30, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 40, 120);
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return (float)(4.0 * (0.7 + subParticle.random1 * 0.6) * GROW_FACTOR.get(subParticle, partialTick));
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      quaternion.rotateZ(subParticle.random2 * 360.0F + ageInSeconds * 30.0F * subParticle.random2);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      float angle = this.random.nextFloat() * 360.0F * (float) (Math.PI / 180.0);
      Vec3 pos = new Vec3(4.0, 0.0, 0.0).yRot(angle);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearAcceleration = new Vec3(Mth.randomBetween(this.random, -2.0F, 2.0F), -5.0, Mth.randomBetween(this.random, -2.0F, 2.0F));
      subParticle.linearDragCoefficient = 1.5;
      subParticle.setVelocity(new Vec3(0.1F, 1.0, 0.0).yRot(angle), Mth.randomBetween(this.random, 2.0F, 32.0F));
      return subParticle;
   }

   public static class YetiDeathSoftProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public YetiDeathSoftProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new YetiDeathSoft(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
