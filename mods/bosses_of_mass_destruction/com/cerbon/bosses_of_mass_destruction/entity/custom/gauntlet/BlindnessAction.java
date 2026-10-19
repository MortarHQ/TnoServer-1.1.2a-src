package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.packet.custom.BlindnessS2CPacket;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class BlindnessAction implements IActionWithCooldown {
   private final GauntletEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> cancelAction;
   private final ServerLevel serverLevel;

   public BlindnessAction(GauntletEntity entity, EventScheduler eventScheduler, Supplier<Boolean> cancelAction, ServerLevel serverLevel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.cancelAction = cancelAction;
      this.serverLevel = serverLevel;
   }

   @Override
   public int perform() {
      SoundUtils.playSound(this.serverLevel, this.entity.position(), (SoundEvent)BMDSounds.GAUNTLET_CAST.get(), SoundSource.HOSTILE, 3.0F, 1.0F, 64.0, null);
      this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setClosedFistHitbox, 10, 1, this.cancelAction));
      this.eventScheduler.addEvent(new TimedEvent(this.entity.hitboxHelper::setOpenHandHitbox, 43));
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> {
                  List<Player> players = this.entity
                     .level()
                     .getNearbyPlayers(
                        TargetingConditions.forCombat().range(64.0),
                        this.entity,
                        new AABB(this.entity.position(), this.entity.position()).inflate(64.0, 32.0, 64.0)
                     );
                  if (!players.isEmpty()) {
                     Dispatcher.sendToClientsLoadingPos(
                        new BlindnessS2CPacket(this.entity.getId(), players.stream().flatMapToInt(player -> IntStream.of(player.getId())).toArray()),
                        this.serverLevel,
                        this.entity.position()
                     );
                     this.eventScheduler
                        .addEvent(
                           new TimedEvent(
                              () -> players.forEach(player -> player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140))), 50, 1, this.cancelAction
                           )
                        );
                  }
               },
               30,
               1,
               this.cancelAction
            )
         );
      return 80;
   }
}
