package net.unusual.block_factorys_bosses.client.particle.knight;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition2 extends SubSteadyParticleEmitter {
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{0.0F, 1.0F, 1.24F, 0.0F}, (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime, (subParticle, partial) -> 1.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16711694), 0.31F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static PhaseTransition2.Provider provider(SpriteSet spriteSet) {
      return new PhaseTransition2.Provider(spriteSet);
   }

   protected PhaseTransition2(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 20, 30, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 60;
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
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      float angle = this.random.nextFloat() * 360.0F * (float) (Math.PI / 180.0);
      Vec3 pos = new Vec3(10.0F * subParticle.random2, 0.0, 0.0).yRot(angle);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearAcceleration = new Vec3(0.0, subParticle.random2, 0.0);
      subParticle.setVelocity(pos, -subParticle.random2 * 3.0F);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PhaseTransition2(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
