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
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IntroductionAttackSouls2 extends SubSteadyParticleEmitter {
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-13116187), 0.0F), new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(4648703), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static IntroductionAttackSouls2.Provider provider(SpriteSet spriteSet) {
      return new IntroductionAttackSouls2.Provider(spriteSet);
   }

   protected IntroductionAttackSouls2(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 8, 30, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 10, 12);
   }

   @Override
   protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return new Vector3f(1.0F, 3.0F, 1.0F);
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
      SubParticle subParticle = super.makeParticle();
      double distance = 2.0;
      double theta = this.random.nextDouble() * Math.PI;
      double phi = this.random.nextDouble() * 2.0 * Math.PI;
      Vec3 pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta));
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearDragCoefficient = 3.0;
      subParticle.setVelocity(pos, 15.0F);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new IntroductionAttackSouls2(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
