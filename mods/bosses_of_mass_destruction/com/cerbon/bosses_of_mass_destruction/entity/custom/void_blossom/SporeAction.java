package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.EventSeries;
import com.cerbon.cerbons_api.api.general.event.IEvent;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class SporeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;

   public SporeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
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

      this.eventScheduler
         .addEvent(
            new EventSeries(
               new IEvent[]{
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Spore.getId()), 20, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Idle.getId()), 27, 1, () -> false)
               }
            )
         );
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> SoundUtils.playSound(
                  ((ServerPlayer)target).serverLevel(),
                  this.entity.position(),
                  (SoundEvent)BMDSounds.SPORE_PREPARE.get(),
                  SoundSource.HOSTILE,
                  1.5F,
                  32.0,
                  null
               ),
               26
            )
         );
      this.eventScheduler
         .addEvent(
            new TimedEvent(
               () -> new ProjectileThrower(
                     () -> {
                        SporeBallProjectile projectile = new SporeBallProjectile(
                           this.entity, this.entity.level(), new ExemptEntities(List.of((EntityType<?>)BMDEntities.VOID_BLOSSOM.get()))
                        );
                        projectile.setPos(this.entity.getEyePosition());
                        return new ProjectileThrower.ProjectileData(projectile, 0.75F, 0.0F, 0.2);
                     }
                  )
                  .throwProjectile(target.getEyePosition()),
               45,
               1,
               this.shouldCancel
            )
         );
      return 100;
   }
}
