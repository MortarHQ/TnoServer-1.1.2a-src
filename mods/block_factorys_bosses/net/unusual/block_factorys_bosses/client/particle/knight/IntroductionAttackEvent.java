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
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IntroductionAttackEvent extends SubInstantParticleEmitter {
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{1.0F, 1.0F, 1.0F, 0.0F},
      (subParticle, partial) -> (subParticle.age + partial) / 20.0F,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16711694), 0.31F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static IntroductionAttackEvent.Provider provider(SpriteSet spriteSet) {
      return new IntroductionAttackEvent.Provider(spriteSet);
   }

   protected IntroductionAttackEvent(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, spriteSet, new Vec3(x, y, z), 1);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 4;
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 0.3F * X_FACTOR.get(subParticle, partialTick);
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      quaternion.rotateZ(subParticle.random2 * 360.0F + ageInSeconds * 15.0F * subParticle.random2);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      subParticle.linearAcceleration = new Vec3(0.0, subParticle.random2, 0.0);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new IntroductionAttackEvent(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
