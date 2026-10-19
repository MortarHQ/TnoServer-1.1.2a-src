package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.PetalBladeProjectile;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.EventSeries;
import com.cerbon.cerbons_api.api.general.event.IEvent;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BladeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;

   public BladeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
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
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Petal.getId()), 10, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.getEntityData().set(VoidBlossomEntity.hitbox, HitboxId.Idle.getId()), 90)
               }
            )
         );
      Runnable thrower = () -> {
         Vec3 eyePos = target.getBoundingBox().getCenter();
         Vec3 dir = this.entity.getEyePosition().subtract(eyePos);
         Vec3 left = dir.cross(VecUtils.yAxis).normalize();
         double rotation = RandomUtils.randSign() * 20.0;
         Vec3 angled = VecUtils.rotateVector(left, dir, rotation);
         Vec3 lineStart = eyePos.add(angled.scale(7.0));
         Vec3 lineEnd = eyePos.add(angled.scale(-7.0));
         ProjectileThrower projectileThrower = new ProjectileThrower(
            () -> {
               PetalBladeProjectile projectile = new PetalBladeProjectile(
                  this.entity, this.entity.level(), livingEntity -> {}, List.of(this.entity.getType()), (float)rotation
               );
               projectile.setPos(this.entity.getEyePosition().add(VecUtils.yAxis));
               projectile.setNoGravity(true);
               return new ProjectileThrower.ProjectileData(projectile, 0.9F, 0.0F, 0.0);
            }
         );
         SoundUtils.playSound(
            ((ServerPlayer)target).serverLevel(),
            this.entity.position(),
            (SoundEvent)BMDSounds.PETAL_BLADE.get(),
            SoundSource.HOSTILE,
            3.0F,
            SoundUtils.randomPitch(target.getRandom()),
            64.0,
            null
         );
         MathUtils.lineCallback(lineStart, lineEnd, 11, (vec3, integer) -> projectileThrower.throwProjectile(vec3));
      };
      this.eventScheduler.addEvent(new TimedEvent(thrower, 28, 1, this.shouldCancel));
      this.eventScheduler.addEvent(new TimedEvent(thrower, 52, 1, this.shouldCancel));
      this.eventScheduler.addEvent(new TimedEvent(thrower, 75, 1, this.shouldCancel));
      return 120;
   }
}
