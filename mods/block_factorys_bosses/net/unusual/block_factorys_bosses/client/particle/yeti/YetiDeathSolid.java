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
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiDeathSolid extends SubInstantParticleEmitter {
   private static final LinearCurve GROW_FACTOR = new LinearCurve(
      new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.84F, 0.55F, 0.0F},
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.94F, 0.98F, 0.99F, 0.0F), 0.0F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.83F, 0.98F, 1.0F, 1.0F), 0.01F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.83F, 1.0F, 0.99F, 0.0F), 0.78F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.9F, 0.97F, 1.0F, 0.0F), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime + subParticle.random2
   );

   public static YetiDeathSolid.YetiDeathSolidProvider provider(SpriteSet spriteSet) {
      return new YetiDeathSolid.YetiDeathSolidProvider(spriteSet);
   }

   protected YetiDeathSolid(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 40);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 60, 120);
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return (float)(1.4 * (0.7 + subParticle.random1 * 0.6) * GROW_FACTOR.get(subParticle, partialTick));
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
      SubParticle subParticle = super.makeParticle();
      float angle = this.random.nextFloat() * 360.0F * (float) (Math.PI / 180.0);
      Vec3 pos = new Vec3(4.0, 0.0, 0.0).yRot(angle);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.linearAcceleration = new Vec3(Mth.randomBetween(this.random, -2.0F, 2.0F), -5.0, Mth.randomBetween(this.random, -2.0F, 2.0F));
      subParticle.linearDragCoefficient = 1.4;
      subParticle.setVelocity(new Vec3(0.1F, 1.0, 0.0).yRot(angle), Mth.randomBetween(this.random, 3.0F, 48.0F));
      return subParticle;
   }

   public static class YetiDeathSolidProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public YetiDeathSolidProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new YetiDeathSolid(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
