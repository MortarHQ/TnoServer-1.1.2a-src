package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubInstantParticleEmitter;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IntroductionAttackSoulOnce extends SubInstantParticleEmitter {
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{1.0F, 1.0F, 1.0F, 1.0F, 0.94F, 0.86F, 0.56F},
      (subParticle, partial) -> (subParticle.age + partial) / 20.0F,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final LinearCurve S_FACTOR = new LinearCurve(
      new float[]{1.48F, 1.17F, 1.06F, 1.0F, 1.0F, 1.0F, 1.0F},
      (subParticle, partial) -> (subParticle.age + partial) / 20.0F,
      (subParticle, partial) -> subParticle.lifeTime / 20.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-9699352), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-615190545), 0.87F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(11204351), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static IntroductionAttackSoulOnce.Provider provider(SpriteSet spriteSet) {
      return new IntroductionAttackSoulOnce.Provider(spriteSet);
   }

   protected IntroductionAttackSoulOnce(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 1);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.xd = vx * 0.4;
      this.yd = vy * 0.4;
      this.zd = vz * 0.4;
   }

   @Override
   public boolean shouldAttach() {
      return true;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return 34;
   }

   @Override
   protected Vector3f getSubSizeVector(SubParticle subParticle, float partialTick, float ageInSeconds) {
      float x = X_FACTOR.get(subParticle, partialTick);
      float y = x * S_FACTOR.get(subParticle, partialTick);
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
      this.getFacingCameraMode().setRotation(quaternion, camera, partialTick);
      return quaternion;
   }

   @Override
   protected SubParticle makeParticle() {
      SubParticle subParticle = new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random) {
         @Override
         protected List<Pair<Float, BiConsumer<SubParticle, Float>>> createLifetimeEvents() {
            List<Pair<Float, BiConsumer<SubParticle, Float>>> pairs = new ArrayList<>();

            for (float f = 0.0F; f <= 2.0F; f += 0.01F) {
               pairs.add(new Pair(f, this::event));
            }

            return pairs;
         }

         private void event(SubParticle sub, Float partialTick) {
            Vec3 pos = this.parent.getCurrentPos(partialTick);
            this.parent.getLevel().addParticle((ParticleOptions)BossesRiseParticleTypes.SOUL_TRAIL.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         }
      };
      subParticle.pos = new Vec3(0.0, 1.0, 0.0);
      subParticle.pos0 = subParticle.pos;
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new IntroductionAttackSoulOnce(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
