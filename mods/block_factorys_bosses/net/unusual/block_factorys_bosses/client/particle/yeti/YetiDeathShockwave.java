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
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiDeathShockwave extends SubSteadyParticleEmitter {
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 0.5F), 0.0F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(1.0F, 1.0F, 1.0F, 0.5F), 0.16F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.46F, 0.46F, 0.46F, 0.1F), 0.33F),
         new ColorCurve.Point(new ColorCurve.FloatRGBA(0.46F, 0.46F, 0.46F, 0.1F), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static YetiDeathShockwave.YetiDeathShockwaveProvider provider(SpriteSet spriteSet) {
      return new YetiDeathShockwave.YetiDeathShockwaveProvider(spriteSet);
   }

   protected YetiDeathShockwave(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
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
      return 8;
   }

   @Override
   protected float getSubSize(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return 2.0F;
   }

   @Override
   protected ColorCurve.FloatRGBA getSubColor(SubParticle subParticle, float partialTick, float ageInSeconds) {
      return COLOR_CURVE.get(subParticle, partialTick);
   }

   @Override
   protected Quaternionf getQuaternion(SubParticle subParticle, Camera camera, float partialTick, float ageInSeconds) {
      Quaternionf quaternion = new Quaternionf();
      quaternion.setAngleAxis((float) (Math.PI * 3.0 / 2.0), 1.0F, 0.0F, 0.0F);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = super.makeParticle();
      float angle = this.random.nextFloat() * 360.0F * (float) (Math.PI / 180.0);
      Vec3 pos = new Vec3(2.0, Mth.randomBetween(this.random, 0.01F, 0.02F), 0.0).yRot(angle);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      return subParticle;
   }

   public static class YetiDeathShockwaveProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public YetiDeathShockwaveProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new YetiDeathShockwave(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
