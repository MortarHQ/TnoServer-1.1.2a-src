package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithUtils;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.packet.custom.SpikeS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.EventSeries;
import com.cerbon.cerbons_api.api.general.event.IEvent;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SpikeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;
   private final List<Vec3> circlePoints = MathUtils.buildBlockCircle(2.0);
   public static final int indicatorDelay = 20;

   public SpikeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.getTarget();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      }

      this.placeSpikes((ServerPlayer)target);
      return 150;
   }

   private void placeSpikes(ServerPlayer target) {
      Spikes riftBurst = new Spikes(
         this.entity, target.serverLevel(), (ParticleOptions)BMDParticles.VOID_BLOSSOM_SPIKE_INDICATOR.get(), 20, this.eventScheduler, this.shouldCancel
      );
      SoundUtils.playSound(target.serverLevel(), this.entity.position(), (SoundEvent)BMDSounds.VOID_BLOSSOM_BURROW.get(), SoundSource.HOSTILE, 1.5F, 32.0, null);
      this.eventScheduler
         .addEvent(
            new EventSeries(
               new IEvent[]{
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Spike.getId()), 20, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Idle.getId()), 100)
               }
            )
         );

      for (int i = 0; i < 3; i++) {
         int timeBetweenRifts = 30;
         int initialDelay = 30;
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> {
                     Vec3 placement = ObsidilithUtils.approximatePlayerNextPosition(BMDCapabilities.getPlayerPositions(target), target.position());
                     SoundUtils.playSound(
                        target.serverLevel(), placement, (SoundEvent)BMDSounds.VOID_SPIKE_INDICATOR.get(), SoundSource.HOSTILE, 1.0F, 32.0, null
                     );
                     List<BlockPos> successfulSpikes = this.circlePoints
                        .stream()
                        .flatMap(point -> riftBurst.tryPlaceRift(placement.add(point)).stream())
                        .toList();
                     this.eventScheduler
                        .addEvent(
                           new TimedEvent(
                              () -> {
                                 SoundUtils.playSound(
                                    target.serverLevel(), placement, (SoundEvent)BMDSounds.VOID_BLOSSOM_SPIKE.get(), SoundSource.HOSTILE, 1.2F, 32.0, null
                                 );
                                 Dispatcher.sendToClientsLoadingPos(
                                    new SpikeS2CPacket(this.entity.getId(), successfulSpikes), (ServerLevel)this.entity.level(), this.entity.position()
                                 );
                              },
                              20,
                              1,
                              this.shouldCancel
                           )
                        );
                  },
                  initialDelay + i * timeBetweenRifts,
                  1,
                  this.shouldCancel
               )
            );
      }
   }
}
