package com.github.wal_bos.moreskills.handler.phainon;

import com.github.wal_bos.moreskills.ability.skill.shades.PhainonSkill;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.DimensionTravelEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.EngraveEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.SpatialMovementEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillLearningEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class PhainonProgressHooks {
   private PhainonProgressHooks() {
   }

   public static void init() {
      TensuraEntityEvents.DIMENSION_TRAVEL_EVENT.register((DimensionTravelEvent)(entity, portal, dimension) -> {
         if (entity instanceof LivingEntity living) {
            PhainonHandler.getPhainon(living).ifPresent(instance -> PhainonHandler.grantOrMessage(living, instance, PhainonProgress.CoreFlame.PASSAGE, 1));
         }

         return EventResult.pass();
      });
      TensuraEntityEvents.INSTANT_TRANSMISSION_EVENT.register((SpatialMovementEvent)(entity, user, skill, vec3) -> {
         if (entity instanceof LivingEntity living) {
            PhainonHandler.getPhainon(living).ifPresent(instance -> PhainonHandler.grantOrMessage(living, instance, PhainonProgress.CoreFlame.PASSAGE, 1));
         }

         return EventResult.pass();
      });
      TensuraEntityEvents.ENGRAVE_EVENT.register((EngraveEvent)(entity, stack, enchantment, level) -> {
         PhainonHandler.getPhainon(entity).ifPresent(instance -> PhainonHandler.grantOrMessage(entity, instance, PhainonProgress.CoreFlame.REASON, 1));
         return EventResult.pass();
      });
      TensuraSkillEvents.SKILL_PLUNDER.register((SkillPlunderEvent)(attacker, target, temporary, skill) -> {
         ManasSkill value = (ManasSkill)skill.get();
         if (value == ShadeSkills.PHAINON.get()) {
            if (attacker instanceof LivingEntity living) {
               living.kill();
               living.sendSystemMessage(Component.literal("Phainon rejects false deliverance.").withStyle(ChatFormatting.DARK_RED));
            }

            skill.set(null);
            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      });
      TensuraSkillEvents.SKILL_LEARNING.register((SkillLearningEvent)(instance, entity, level, cost, changeableCost) -> {
         if (instance.getSkill() != ShadeSkills.PHAINON.get()) {
            return EventResult.pass();
         } else if (entity instanceof Player player && !((PhainonSkill)ShadeSkills.PHAINON.get()).checkAcquiringRequirement(player, 0.0)) {
            entity.sendSystemMessage(Component.literal("You do not meet Phainon's requirements.").withStyle(ChatFormatting.RED));
            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      });
   }
}
