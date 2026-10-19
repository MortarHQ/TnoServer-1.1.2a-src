package com.tensura_tno.event;

import com.tensura_tno.ability.skill.MagicOreSenseSkill;
import com.tensura_tno.registry.TensuraTNOSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillMasteryEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno")
public final class MagicOreSenseAcquisitionHandler {
   private static final Set<UUID> PENDING_PLAYERS = ConcurrentHashMap.newKeySet();

   private MagicOreSenseAcquisitionHandler() {
   }

   public static void registerArchitecturyEvents() {
      SkillEvents.SKILL_MASTERY.register((SkillMasteryEvent)(instance, owner, newMastery) -> {
         if (owner instanceof ServerPlayer player && isSensePrerequisite(instance)) {
            Double proposedMastery = (Double)newMastery.get();
            if (proposedMastery != null && proposedMastery >= instance.getMaxMastery()) {
               PENDING_PLAYERS.add(player.getUUID());
            }

            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }

   private static boolean isSensePrerequisite(ManasSkillInstance instance) {
      ManasSkill skill = instance.getSkill();
      return skill == ExtraSkills.MAGIC_SENSE.get() || skill == ExtraSkills.SENSE_HEAT_SOURCE.get() || skill == ExtraSkills.SENSE_SOUNDWAVE.get();
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && PENDING_PLAYERS.remove(player.getUUID())) {
         MagicOreSenseSkill skill = (MagicOreSenseSkill)TensuraTNOSkills.MAGIC_ORE_SENSE.get();
         if (MagicOreSenseSkill.hasAcquisitionPrerequisites(player) && !SkillAPI.getSkillsFrom(player).getSkill(skill).isPresent()) {
            SkillHelper.learnSkill(player, skill.createLearningInstance(player));
         }
      }
   }
}
