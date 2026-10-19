package cn.leolezury.eternalstarlight.common.client.particle.effect;

import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

public class AethersentExplosionParticle extends NoRenderParticle {
   protected AethersentExplosionParticle(ClientLevel level, double x, double y, double z) {
      super(level, x, y, z);
      this.lifetime = 20;
   }

   public void tick() {
      if (this.age == 0) {
         for (int angle = 0; angle <= 360; angle += 30) {
            Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(this.x, this.y, this.z), 0.2F, 0.0F, angle);
            this.level.addParticle(ESSmokeParticleOptions.AETHERSENT, true, this.x, this.y, this.z, pos.x - this.x, pos.y - this.y, pos.z - this.z);
         }

         Vec3 basePos = new Vec3(this.x, this.y + 2.0, this.z);

         for (int i = 0; i < 300; i++) {
            Vec3 pos = ESMathUtil.rotationToPosition(
               basePos, this.random.nextFloat() * 3.0F, this.random.nextFloat() * 360.0F, this.random.nextFloat() * 360.0F
            );
            this.level
               .addParticle((ParticleOptions)ESParticles.AETHERSENT_SMOKE.get(), true, this.x, this.y, this.z, pos.x - this.x, pos.y - this.y, pos.z - this.z);
         }

         for (int i = 0; i < 15; i++) {
            Vec3 pos = ESMathUtil.rotationToPosition(new Vec3(this.x, this.y, this.z), 0.5F, this.random.nextInt(30, 60), this.random.nextFloat() * 360.0F);
            this.level
               .addParticle((ParticleOptions)ESParticles.SMOKE_TRAIL.get(), true, this.x, this.y, this.z, pos.x - this.x, pos.y - this.y, pos.z - this.z);
         }
      }

      super.tick();
      float radius = (float)this.age / this.lifetime * 20.0F;

      for (int angle = 0; angle <= 360; angle += 10) {
         Vec3 basePos = ESMathUtil.rotationToPosition(new Vec3(this.x, this.y, this.z), radius, 0.0F, angle);
         Vec3 pos = basePos.offsetRandom(this.random, 2.0F);
         this.level.addParticle(ESExplosionParticleOptions.AETHERSENT, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         pos = basePos.offsetRandom(this.random, 2.0F);
         this.level.addParticle(ParticleTypes.SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         pos = basePos.offsetRandom(this.random, 2.0F);
         this.level.addParticle(ParticleTypes.LARGE_SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         if (this.random.nextInt(25) == 0) {
            this.level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.05, 0.0);
         }

         pos = basePos.offsetRandom(this.random, 2.0F);
         this.level.addParticle(ParticleTypes.WHITE_SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      public Provider(SpriteSet spriteSet) {
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
         return new AethersentExplosionParticle(clientLevel, d, e, f);
      }
   }
}
