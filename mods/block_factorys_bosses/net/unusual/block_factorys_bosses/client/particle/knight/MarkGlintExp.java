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
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MarkGlintExp extends SubInstantParticleEmitter {
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-1), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-1), 0.23F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-54016), 0.59F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-54016), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static MarkGlintExp.Provider provider(SpriteSet spriteSet) {
      return new MarkGlintExp.Provider(spriteSet);
   }

   protected MarkGlintExp(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, spriteSet, new Vec3(x, y, z), 1);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 10;
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      quaternion.rotateZ(subParticle.random2 * 360.0F);
      return quaternion;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new MarkGlintExp(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
