package com.github.wal_bos.moreskills.event;

import com.github.wal_bos.moreskills.item.FeldwayAngelSummonerItem;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingEffectAddedEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ModEffectBlockEvents {
   private ModEffectBlockEvents() {
   }

   public static void init() {
      EntityEvents.LIVING_EFFECT_ADDED.register((LivingEffectAddedEvent)(entity, source, changeableInstance) -> {
         if (entity instanceof Player player) {
            MobEffectInstance instance = (MobEffectInstance)changeableInstance.get();
            if (instance == null) {
               return EventResult.pass();
            } else if (!instance.getEffect().equals(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON))) {
               return EventResult.pass();
            } else if (hasEnabledFeldwayImmunity(player)) {
               player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
               return EventResult.interruptFalse();
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      });
   }

   private static boolean hasEnabledFeldwayImmunity(Player player) {
      for (ItemStack stack : player.getInventory().items) {
         if (stack.getItem() instanceof FeldwayAngelSummonerItem && FeldwayAngelSummonerItem.hasMagiculePoisonImmunityEnabled(stack)) {
            return true;
         }
      }

      for (ItemStack stack : player.getInventory().offhand) {
         if (stack.getItem() instanceof FeldwayAngelSummonerItem && FeldwayAngelSummonerItem.hasMagiculePoisonImmunityEnabled(stack)) {
            return true;
         }
      }

      return false;
   }
}
