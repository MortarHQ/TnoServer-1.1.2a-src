package net.unusual.block_factorys_bosses.client.particle.knight;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.ColorCurve;
import net.unusual.block_factorys_bosses.client.particle.util.LinearCurve;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticle;
import net.unusual.block_factorys_bosses.client.particle.util.SubSteadyParticleEmitter;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PhaseTransition1 extends SubSteadyParticleEmitter {
   private static final LinearCurve X_FACTOR = new LinearCurve(
      new float[]{0.0F, 1.0F, 1.1F, 1.07F, 1.0F}, (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime, (subParticle, partial) -> 1.0F
   );
   private static final ColorCurve COLOR_CURVE = new ColorCurve(
      new ColorCurve.Point[]{
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 0.0F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(-16713473), 0.31F),
         new ColorCurve.Point(ColorCurve.FloatRGBA.fromARGB(16777215), 1.0F)
      },
      (subParticle, partial) -> (subParticle.age + partial) / subParticle.lifeTime
   );

   public static PhaseTransition1.Provider provider(SpriteSet spriteSet) {
      return new PhaseTransition1.Provider(spriteSet);
   }

   protected PhaseTransition1(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z, spriteSet, 60, 5, 100);
      this.setSize(0.6F, 0.6F);
      this.gravity = 0.0F;
      this.hasPhysics = false;
   }

   @Override
   protected int getSubParticleLifeTime() {
      return Mth.randomBetweenInclusive(this.random, 40, 60);
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
      SubParticle subParticle = new SubParticle(this, this.set, this.getSubParticleLifeTime(), this.random) {
         @Override
         public Vec3 getLinearAcceleration() {
            return new Vec3(Math.cos(this.age * 360.0F) * 5.0, 3.0, Math.sin(this.age * 360.0F) * 5.0);
         }

         @Override
         protected List<Pair<Float, BiConsumer<SubParticle, Float>>> createLifetimeEvents() {
            List<Pair<Float, BiConsumer<SubParticle, Float>>> pairs = new ArrayList<>();

            for (float f = 0.0F; f <= 2.7F; f += 0.01F) {
               pairs.add(new Pair(f, this::event));
            }

            return pairs;
         }

         private void event(SubParticle sub, Float partialTick) {
            Vec3 pos = this.parent.getActualSubPos(sub, partialTick, (this.age + partialTick) / 20.0F);
            this.parent
               .getLevel()
               .addParticle((ParticleOptions)BossesRiseParticleTypes.KNIGHT_INTRODUCTION_ATTACK_EVENT.get(), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         }
      };
      float angle = this.random.nextFloat() * 360.0F * (float) (Math.PI / 180.0);
      Vec3 pos = new Vec3(4.0, 0.0, 0.0).yRot(angle);
      subParticle.pos = pos;
      subParticle.pos0 = pos;
      subParticle.setVelocity(pos, -subParticle.random2);
      return subParticle;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new PhaseTransition1(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
