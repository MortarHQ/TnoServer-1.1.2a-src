package net.unusual.block_factorys_bosses.client.particle.knight;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition7 extends SubSteadyParticleEmitter {
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(1543569381), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(5521977), 0.9F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(5521977), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static PhaseTransition7.Provider provider(SpriteSet spriteSet) {
      return new PhaseTransition7.Provider(spriteSet);
   }

   protected PhaseTransition7(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 100, 10, 1000);
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
      return Mth.randomBetweenInclusive(this.random, 20, 28);
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 0.4F;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      Vector3f halfDimensions = new Vector3f(0.5F, 0.5F, 0.5F);
      Vec3 pos = new Vec3(
            Mth.randomBetween(this.random, -halfDimensions.x, halfDimensions.x),
            Mth.randomBetween(this.random, -halfDimensions.y, halfDimensions.y),
            Mth.randomBetween(this.random, -halfDimensions.z, halfDimensions.z)
         )
         .add(0.0, 0.5, 0.0);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearAcceleration = new Vec3(0.0, 2.0 * subParticle.random2, 0.0);
      subParticle.setVelocity(
         new Vec3(Mth.randomBetween(this.random, -0.5F, 0.5F), 1.0, Mth.randomBetween(this.random, -0.5F, 0.5F)), -2.0F * subParticle.random2
      );
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PhaseTransition7(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
