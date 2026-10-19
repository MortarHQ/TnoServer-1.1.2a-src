package net.unusual.block_factorys_bosses.client.particle.knight;

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
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IntroductionAttackSouls extends SubInstantParticleEmitter {
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{3.15F, 1.0F, 1.0F}, (subParticle, partial) -> (subParticle.age + partial) / 20.0F, (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve S_FACTOR = new LinearCurve(
      new float[]{4.04F, 3.36F, 1.0F}, (subParticle, partial) -> (subParticle.age + partial) / 20.0F, (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16714241), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-13327980), 0.25F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(65505), 0.88F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(65505), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static IntroductionAttackSouls.Provider provider(SpriteSet spriteSet) {
      return new IntroductionAttackSouls.Provider(spriteSet);
   }

   protected IntroductionAttackSouls(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 20);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 10, 30);
   }

   @Override
   protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
      float x = X_FACTOR.get(subParticle, partialTick) * 0.6F;
      float y = X_FACTOR.get(subParticle, partialTick) * S_FACTOR.get(subParticle, partialTick) * 0.3F;
      return new Vector3f(x, y, x);
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return X_FACTOR.get(subParticle, partialTick) * 0.6F;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      SubParticleEmitter.lookAtMovement(subParticle, quaternion, 90.0F);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random) {
         @Override
         protected void tickSprite() {
            this.sprite = this.set.get(this.random2 <= 0.5F ? this.age : this.age + this.lifeTime, this.lifeTime * 2);
         }
      };
      double distance = 4.0;
      double theta = this.random.nextDouble() * Math.PI;
      double phi = this.random.nextDouble() * 2.0 * Math.PI;
      Vec3 pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta))
         .add(0.0, 0.05, 0.0);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.setVelocity(
         new Vec3(Mth.randomBetween(this.random, -0.5F, 0.5F), Mth.randomBetween(this.random, -0.5F, 0.5F), Mth.randomBetween(this.random, -0.5F, 0.5F)),
         30.0F + subParticle.random2
      );
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new IntroductionAttackSouls(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
