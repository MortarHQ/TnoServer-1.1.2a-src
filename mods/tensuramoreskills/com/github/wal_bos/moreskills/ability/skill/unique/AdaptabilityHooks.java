package com.github.wal_bos.moreskills.ability.skill.unique;

import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class AdaptabilityHooks {
   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         AdaptabilitySkill.MutableDamage var3 = new AdaptabilitySkill.MutableDamage(event.getAmount());
         AdaptabilitySkill.handleIncomingDamage(player, event.getSource(), var3);
         if (var3.cancel) {
            event.setCanceled(true);
         } else {
            if (var3.amount != event.getAmount()) {
               event.setAmount(var3.amount);
            }
         }
      }
   }

   private static EventResult onSkillLearning(ManasSkillInstance instance, LivingEntity entity, int mode, double oldPoint, Changeable<Double> point) {
      AdaptabilitySkill.handleResistanceLearning(instance, entity, point);
      return EventResult.pass();
   }

   private AdaptabilityHooks() {
   }

   static {
      TensuraSkillEvents.SKILL_LEARNING.register(AdaptabilityHooks::onSkillLearning);
   }
}
