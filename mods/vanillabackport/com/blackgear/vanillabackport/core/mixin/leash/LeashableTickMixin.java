package com.blackgear.vanillabackport.core.mixin.leash;

import com.blackgear.vanillabackport.common.api.leash.LeashDataAccess;
import com.blackgear.vanillabackport.common.api.leash.LeashHolderCallback;
import com.blackgear.vanillabackport.common.api.leash.LeashPhysics;
import com.blackgear.vanillabackport.common.api.leash.LeashableCallback;
import com.blackgear.vanillabackport.core.ModChecker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.Leashable.LeashData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashableTickMixin {
   @Shadow
   private static <E extends Entity & Leashable> void restoreLeashFromSave(E entity, LeashData leashData) {
   }

   @Inject(method = "tickLeash(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   private static <E extends Entity & Leashable> void vb$tickLeash(E entity, CallbackInfo ci) {
      if (!ModChecker.SABLE_LOADED) {
         ci.cancel();
         LeashData data = entity.getLeashData();
         if (data != null && data.delayedLeashInfo != null) {
            restoreLeashFromSave(entity, data);
         }

         if (data != null && data.leashHolder != null) {
            if (entity.isAlive() && data.leashHolder.isAlive()) {
               Entity holder = entity.getLeashHolder();
               if (holder != null && holder.level() == entity.level()) {
                  if (holder instanceof LeashHolderCallback cb) {
                     cb.vb$notifyLeashHolder(entity);
                  }

                  double dist = LeashPhysics.distanceBetween(entity, holder);
                  if (dist > LeashPhysics.snapDistance(entity)) {
                     entity.level().playSound(null, holder.getX(), holder.getY(), holder.getZ(), SoundEvents.LEASH_KNOT_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                     entity.leashTooFarBehaviour();
                  } else if (dist > LeashPhysics.elasticDistance(entity) - holder.getBbWidth() - entity.getBbWidth()) {
                     if (LeashPhysics.checkElasticInteractions(entity, holder, data)) {
                        if (entity instanceof LeashableCallback cb) {
                           cb.vb$onElasticLeashPull();
                        } else {
                           entity.checkSlowFallDistance();
                        }
                     }
                  } else {
                     entity.closeRangeLeashBehaviour(holder);
                  }

                  LeashDataAccess acc = (LeashDataAccess)data;
                  if (entity instanceof LeashableCallback cb && cb.vb$shouldResetAngularMomentum()) {
                     acc.vb$setAngularMomentum(0.0);
                  } else {
                     entity.setYRot((float)(entity.getYRot() - acc.vb$getAngularMomentum()));
                     acc.vb$setAngularMomentum(acc.vb$getAngularMomentum() * LeashPhysics.angularFriction(entity));
                  }
               }
            } else {
               entity.dropLeash(true, entity.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS));
            }
         }
      }
   }
}
