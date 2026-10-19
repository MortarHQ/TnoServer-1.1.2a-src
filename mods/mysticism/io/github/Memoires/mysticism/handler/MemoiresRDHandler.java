package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.entity.boss.memoires.MemoiresEntity;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingDamageEvent;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingHurtEvent;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;

public class MemoiresRDHandler {
   public static void init() {
      EntityEvents.LIVING_HURT.register((LivingHurtEvent)(livingEntity, damageSource, changeable) -> {
         if (damageSource.is(MysticismDamageTypes.REPEATED)) {
            return EventResult.pass();
         } else {
            MemoiresEntity memoires = getMemoiresAttacker(damageSource);
            if (memoires != null && memoires.isRecursionDepthActive()) {
               UUID targetId = livingEntity.getUUID();
               float original = (Float)changeable.get();
               float multiplier = memoires.getRecursionDepthMultiplier(targetId);
               float adjusted = original * multiplier;
               changeable.set(adjusted);
               memoires.setRecursionDepthMultiplier(targetId, multiplier * 0.9F);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         }
      });
      EntityEvents.LIVING_DAMAGE.register((LivingDamageEvent)(livingEntity, damageSource, changeable) -> {
         if (damageSource.is(MysticismDamageTypes.REPEATED)) {
            return EventResult.pass();
         } else {
            MemoiresEntity memoires = getMemoiresAttacker(damageSource);
            if (memoires != null && memoires.isRecursionDepthActive()) {
               memoires.addRecursionDamage(livingEntity.getUUID(), (Float)changeable.get());
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         }
      });
   }

   private static MemoiresEntity getMemoiresAttacker(DamageSource damageSource) {
      if (damageSource.getEntity() instanceof MemoiresEntity memoires) {
         return memoires;
      } else {
         Entity direct = damageSource.getDirectEntity();
         if (direct instanceof MemoiresEntity memoires) {
            return memoires;
         } else {
            return direct instanceof Projectile projectile && projectile.getOwner() instanceof MemoiresEntity memoires ? memoires : null;
         }
      }
   }
}
