package net.unusual.block_factorys_bosses.client.particle.knight;

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
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition4 extends SubSteadyParticleEmitter {
   private static final LinearCurve R_FACTOR = new LinearCurve(
      new float[]{0.54F, 1.0F, 0.54F}, (subParticle, partial) -> subParticle.random2, (subParticle, partial) -> 1.0F
   );
   private static final LinearCurve SCALE_FACTOR = new LinearCurve(
      new float[]{1.0F, 1.0F, 1.0F, 1.08F, 0.59F, 0.0F, 0.0F},
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime,
      (subParticle, partial) -> 1.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(65471), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-486473780), 0.24F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(50687), 0.91F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(50687), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static PhaseTransition4.Provider provider(SpriteSet spriteSet) {
      return new PhaseTransition4.Provider(spriteSet);
   }

   protected PhaseTransition4(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 300, 20, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
   }

   @Override
   public boolean shouldAttach() {
      return true;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 40;
   }

   @Override
   protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return new Vector3f(
         R_FACTOR.get(subParticle, partialTick) * SCALE_FACTOR.get(subParticle, partialTick) * 0.7F,
         R_FACTOR.get(subParticle, partialTick) * SCALE_FACTOR.get(subParticle, partialTick) * 0.6F,
         1.0F
      );
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
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
         protected void tickSprite() {
            this.sprite = this.set.get(this.random2 <= 0.5F ? this.age : this.age + this.lifeTime, this.lifeTime * 2);
         }
      };
      double distance = 1.0;
      double theta = this.random.nextDouble() * Math.PI;
      double phi = this.random.nextDouble() * 2.0 * Math.PI;
      Vec3 pos = new Vec3(distance * Math.sin(theta) * Math.cos(phi), distance * Math.sin(theta) * Math.sin(phi), distance * Math.cos(theta));
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearDragCoefficient = 3.0;
      subParticle.setVelocity(pos, -0.1F);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PhaseTransition4(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
