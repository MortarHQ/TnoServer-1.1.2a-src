package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorPhase;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SolarCreeperIntroPhase extends BehaviorPhase<SolarCreeper> {
   public static final int ID = 1;
   public static final int DURATION = 120;
   public static final SmoothSegmentedValue SHINE_SCALE = SmoothSegmentedValue.of(Easing.IN_CIRC, 0.0F, 0.5F, 0.6F)
      .add(Easing.OUT_BACK, 0.5F, 1.0F, 0.1F)
      .add(v -> v, 1.0F, 1.0F, 0.2F)
      .add(Easing.IN_OUT_CIRC, 1.0F, 0.0F, 0.1F);
   public static final SmoothSegmentedValue SUN_SCALE = SmoothSegmentedValue.of(Easing.OUT_BOUNCE, 0.0F, 0.6F, 0.1F)
      .add(Easing.IN_OUT_CIRC, 0.6F, 1.0F, 0.6F)
      .add(Easing.OUT_QUART, 1.0F, 0.0F, 0.2F)
      .add(v -> v, 0.0F, 0.0F, 0.1F);
   public static final SmoothSegmentedValue BODY_SCALE = SmoothSegmentedValue.of(v -> v, 0.0F, 0.0F, 0.7F)
      .add(Easing.OUT_ELASTIC, 0.0F, 1.0F, 0.2F)
      .add(v -> v, 1.0F, 1.0F, 0.1F);

   public SolarCreeperIntroPhase() {
      super(1, 1, 120, 0);
   }

   public boolean canStart(SolarCreeper entity, boolean cooldownOver) {
      return false;
   }

   public void tick(SolarCreeper entity) {
      RandomSource random = entity.getRandom();
      int ticks = entity.getBehaviorTicks();
      if (entity.level() instanceof ServerLevel serverLevel && ticks <= 84.0) {
         int interval = Mth.lerpInt(ticks / 84.0F, 10, 3);
         if (ticks % interval == 0) {
            serverLevel.sendParticles(
               RingParticleOptions.getFlare(interval / 20.0F), entity.getX(), entity.getY() + entity.getBbHeight() / 2.0F, entity.getZ(), 1, 0.0, 0.0, 0.0, 0.0
            );

            for (int i = 0; i < 3; i++) {
               double dx = (random.nextDouble() * 3.0 + 2.0) * (random.nextBoolean() ? 1 : -1);
               double dy = (random.nextDouble() * 3.0 + 2.0) * (random.nextBoolean() ? 1 : -1);
               double dz = (random.nextDouble() * 3.0 + 2.0) * (random.nextBoolean() ? 1 : -1);
               ESPlatform.INSTANCE
                  .sendToTrackingClients(
                     serverLevel,
                     entity,
                     new ParticlePacket(
                        GatheringTrailParticleOptions.FLARE,
                        entity.getX() - dx,
                        entity.getY() + entity.getBbHeight() / 2.0F - dy,
                        entity.getZ() - dz,
                        dx,
                        dy,
                        dz
                     )
                  );
            }
         }
      }
   }

   public boolean canContinue(SolarCreeper entity) {
      return true;
   }

   public void onStop(SolarCreeper entity) {
      entity.finishIntro();
   }
}
