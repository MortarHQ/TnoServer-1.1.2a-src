package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.CuriosEPCompat;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.handler.DeathHandler", remap = false)
public class CuriosGearEPMixin {
   @Inject(method = "init", at = @At("TAIL"), remap = false, require = 0)
   private static void tensuraTno$registerCuriosEPGain(CallbackInfo ci) {
      EntityEvents.DEATH_EVENT_NORMAL.register((LivingDeath)(entity, source) -> {
         if (entity.isAlive()) {
            return EventResult.pass();
         }

         if (entity.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)) {
            return EventResult.pass();
         }

         Entity pAttacker = source.getEntity();
         if (pAttacker == entity) {
            return EventResult.pass();
         }

         try {
            Class<?> cloneClass = Class.forName("io.github.manasmods.tensura.entity.human.CloneEntity");
            if (cloneClass.isInstance(pAttacker) && cloneClass.getMethod("getOwner").invoke(pAttacker) instanceof Entity ownerEntity) {
               pAttacker = ownerEntity;
            }
         } catch (Exception | NoClassDefFoundError var9) {
         }

         LivingEntity attacker;
         if (pAttacker instanceof LivingEntity living) {
            attacker = living;
         } else {
            attacker = entity.getLastAttacker();
         }

         if (attacker == null) {
            return EventResult.pass();
         }

         double EP = EnergyHelper.getEPGain(entity, attacker, false);
         if (EP <= 0.0) {
            return EventResult.pass();
         }

         if (TensuraStorages.getExistenceFrom(entity).isSkippingEPDrop()) {
            return EventResult.pass();
         }

         float multiplier = entity.level().getGameRules().getInt(TensuraGameRules.EP_GAIN_MULTIPLIER);
         double totalEP = EP * multiplier;
         CuriosEPCompat.applyEPToCuriosSlots(attacker, totalEP);
         return EventResult.pass();
      });
   }
}
