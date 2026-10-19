package net.unusual.block_factorys_bosses.client.particle.yeti;

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

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiDeathLight extends SubSteadyParticleEmitter {
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.5F, 0.93F, 1.0F, 1.0F), 0.0F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.72F, 0.95F, 1.0F, 1.0F), 0.09F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.46F, 0.46F, 0.46F, 0.09F), 0.28F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 1.0F), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime + subParticle.random2
   );

   public static YetiDeathLight.YetiDeathLightProvider provider(SpriteSet spriteSet) {
      return new YetiDeathLight.YetiDeathLightProvider(spriteSet);
   }

   protected YetiDeathLight(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 160, 8, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 8, 20);
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return (float)(4.0 * (0.1 + subParticle.random1 * 2.4 + subParticle.random1));
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      Vec3 pos = new Vec3(0.0, 1.0, 0.0);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      return subParticle;
   }

   public static class YetiDeathLightProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public YetiDeathLightProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new YetiDeathLight(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
