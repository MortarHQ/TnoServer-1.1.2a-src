package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RepeaterHandler {
   public static void init() {
      EntityEvent.LIVING_DEATH
         .register(
            (LivingDeath)(entity, damageSource) -> {
               if (entity.getType() == MysticismMonsterEntityTypes.MEMOIRES.get() && damageSource.getEntity() instanceof LivingEntity attacker) {
                  Skills skills = SkillAPI.getSkillsFrom(attacker);
                  Optional<ManasSkillInstance> repeaterInstance = skills.getSkill((ManasSkill)UniqueMysticismSkills.REPEATER.get());
                  repeaterInstance.ifPresent(
                     instance -> {
                        CompoundTag tag = instance.getOrCreateTag();
                        if (!tag.getBoolean("DefeatedMemoires")) {
                           tag.putBoolean("DefeatedMemoires", true);
                           if (attacker instanceof Player player) {
                              player.displayClientMessage(
                                 Component.translatable("mysticism.skill.mode.repeater.flarefrost_storehouse.released").withStyle(ChatFormatting.GOLD), false
                              );
                              player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.5F, 1.0F);
                           }
                        }
                     }
                  );
               }

               return EventResult.pass();
            }
         );
   }
}
