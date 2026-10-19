package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SendDeltaMovementS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WaveAction implements IActionWithCooldown {
   private final Mob entity;
   private final List<Vec3> circlePoints;
   private final Level level;
   private final EventScheduler eventScheduler;
   private final double riftRadius = 4.0;
   public static final int waveDelay = 20;
   public static final int attackStartDelay = 20;

   public WaveAction(Mob entity) {
      this.entity = entity;
      this.circlePoints = MathUtils.buildBlockCircle(4.0);
      this.level = entity.level();
      this.eventScheduler = CapabilityUtils.getLevelEventScheduler(this.level);
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      this.placeRifts(target);
      return 80;
   }

   private void placeRifts(LivingEntity target) {
      RiftBurst riftBurst = new RiftBurst(
         this.entity,
         (ServerLevel)this.level,
         (ParticleOptions)BMDParticles.OBSIDILITH_WAVE_INDICATOR.get(),
         (ParticleOptions)BMDParticles.OBSIDILITH_WAVE.get(),
         20,
         this.eventScheduler,
         this::damageEntity
      );
      SoundUtils.playSound(
         (ServerLevel)this.level, this.entity.position(), (SoundEvent)BMDSounds.OBSIDILITH_PREPARE_ATTACK.get(), SoundSource.HOSTILE, 3.0F, 0.8F, 64.0, null
      );
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> {
                  Vec3 direction = MathUtils.unNormedDirection(this.entity.position(), target.position()).normalize().scale(4.0);
                  int numRifts = 5;
                  Vec3 startRiftPos = this.entity.position().add(direction);
                  Vec3 endRiftPos = startRiftPos.add(direction.scale(numRifts * 1.5));
                  MathUtils.lineCallback(
                     startRiftPos,
                     endRiftPos,
                     numRifts,
                     (linePos, i) -> this.eventScheduler
                        .addEvent(
                           new TimedEvent(
                              () -> {
                                 SoundUtils.playSound(
                                    (ServerLevel)this.level, linePos, (SoundEvent)BMDSounds.WAVE_INDICATOR.get(), SoundSource.HOSTILE, 0.7F, 32.0, null
                                 );
                                 this.eventScheduler
                                    .addEvent(
                                       new TimedEvent(
                                          () -> SoundUtils.playSound(
                                             (ServerLevel)this.level,
                                             linePos,
                                             (SoundEvent)BMDSounds.OBSIDILITH_WAVE.get(),
                                             SoundSource.HOSTILE,
                                             1.2F,
                                             32.0,
                                             null
                                          ),
                                          20,
                                          1,
                                          () -> !this.entity.isAlive()
                                       )
                                    );

                                 for (Vec3 point : this.circlePoints) {
                                    riftBurst.tryPlaceRift(linePos.add(point));
                                 }
                              },
                              i * 8,
                              1,
                              () -> !this.entity.isAlive()
                           )
                        )
                  );
               },
               20,
               1,
               () -> !this.entity.isAlive()
            )
         );
   }

   private void damageEntity(LivingEntity livingEntity) {
      float damage = (float)this.entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
      if (livingEntity instanceof ServerPlayer serverPlayer) {
         Dispatcher.sendToClient(
            new SendDeltaMovementS2CPacket(new Vec3(livingEntity.getDeltaMovement().x, 0.8, livingEntity.getDeltaMovement().z)), serverPlayer
         );
      }

      livingEntity.setRemainingFireTicks(5);
      livingEntity.hurt(BMDUtils.shieldPiercing(livingEntity.level(), this.entity), damage);
   }
}
