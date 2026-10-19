package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.effect.ability.AuraHealingEffect;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DeathHandler {
   public static void init() {
      EntityEvents.DEATH_EVENT_LOW
         .register(
            (LivingDeath)(entity, source) -> {
               if (entity.isAlive()) {
                  return EventResult.pass();
               }

               Entity attacker = source.getEntity();
               if (attacker instanceof LivingEntity livingAttacker && !livingAttacker.level().isClientSide) {
                  SkillAPI.getSkillsFrom(livingAttacker)
                     .getSkill((ManasSkill)UniqueMysticismSkills.CULTIVATOR.get())
                     .ifPresent(
                        instance -> {
                           CompoundTag tag = instance.getOrCreateTag();
                           tag.putInt("MobKills", tag.getInt("MobKills") + 1);
                           if (tag.getInt("MobKills") == 1000 && attacker instanceof Player player) {
                              player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                              player.displayClientMessage(
                                 Component.translatable("mysticism.skill.mode.cultivator.breakthrough.available").withStyle(ChatFormatting.AQUA), false
                              );
                           }
                        }
                     );
               }

               return EventResult.pass();
            }
         );
      EntityEvents.DEATH_EVENT_FIRST.register((LivingDeath)(entity, source) -> {
         if (Float.isNaN(entity.getHealth()) || entity.getHealth() < 0.0F) {
            entity.setHealth(0.0F);
            if (!(entity instanceof Player)) {
               entity.discard();
            }

            return EventResult.interruptFalse();
         } else if (AuraHealingEffect.canStopDeath(source, entity)) {
            entity.setHealth(0.01F);
            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      });
   }
}
