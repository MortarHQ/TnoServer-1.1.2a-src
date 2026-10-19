package net.unusual.block_factorys_bosses.client.particle.yeti;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
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
public class IceWhirlpool4 extends SubSteadyParticleEmitter {
   private static final LinearCurve C_FACTOR = new LinearCurve(
      new float[]{1.0F, 0.25F, 1.0F, 1.04F, 1.06F, 1.05F, 1.0F, 0.95F, 0.88F, 0.75F, 0.56F, 0.29F},
      (subParticle, partial) -> subParticle.random2,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve A_FACTOR = new LinearCurve(
      new float[]{0.0F, 0.47F, 0.8F, 0.95F, 1.09F, 1.14F, 1.15F, 1.11F, 1.0F, 0.78F, 0.55F, 0.32F, 0.0F},
      (subParticle, partial) -> subParticle.random2,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{1.0F, 1.0F, 1.0F, 1.0F},
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve V_FACTOR = new LinearCurve(
      new float[]{2.86F, 3.2F, 3.26F, 2.7F, 3.52F},
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve RANDOM_FACTOR = new LinearCurve(
      new float[]{0.44F, 1.47F, 0.64F, 0.59F}, (subParticle, partial) -> subParticle.random2, (subParticle, partial) -> 1.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 0.15F), 0.0F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 0.35F), 0.46F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 0.15F), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static IceWhirlpool4.IceWhirlpool4Provider provider(SpriteSet spriteSet) {
      return new IceWhirlpool4.IceWhirlpool4Provider(spriteSet);
   }

   protected IceWhirlpool4(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 200, 25, 300);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 40;
   }

   @Override
   protected double getSubX(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return Math.cos(180.0F * ageInSeconds / 20.0F / 2.0F * this.randomValue + subParticle.random4 * 720.0F)
         * C_FACTOR.get(subParticle, partialTick)
         * V_FACTOR.get(subParticle, partialTick)
         * -9.0;
   }

   @Override
   protected double getSubY(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return subParticle.random1 * 2.0F * ageInSeconds * 3.0F * C_FACTOR.get(subParticle, partialTick) * -3.0F;
   }

   @Override
   protected double getSubZ(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return Math.sin(180.0F * ageInSeconds / 20.0F / 2.0F * this.randomValue + subParticle.random4 * 720.0F)
         * C_FACTOR.get(subParticle, partialTick)
         * V_FACTOR.get(subParticle, partialTick)
         * 9.0;
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return X_FACTOR.get(subParticle, partialTick) * 4.0F;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      quaternion.rotateZ(subParticle.random1 * 360.0F);
      return quaternion;
   }

   public static class IceWhirlpool4Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public IceWhirlpool4Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new IceWhirlpool4(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
