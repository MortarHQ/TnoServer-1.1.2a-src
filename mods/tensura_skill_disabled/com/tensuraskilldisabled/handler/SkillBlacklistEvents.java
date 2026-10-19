package com.tensuraskilldisabled.handler;

import com.tensuraskilldisabled.TensuraSkillDisabled;
import com.tensuraskilldisabled.config.SkillBlacklistConfig;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.List;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

@EventBusSubscriber(modid = "tensura_skill_disabled")
public final class SkillBlacklistEvents {
   private static boolean registered;

   private SkillBlacklistEvents() {
   }

   public static synchronized void register() {
      if (!registered) {
         registered = true;
         SkillEvents.UNLOCK_SKILL.register(SkillBlacklistEvents::onUnlockSkill);
      }
   }

   private static EventResult onUnlockSkill(ManasSkillInstance instance, LivingEntity owner, Changeable<MutableComponent> message) {
      ResourceLocation skillId = instance.getSkillId();
      return SkillBlacklistConfig.getInstance().isDisabled(skillId) ? EventResult.interruptFalse() : EventResult.pass();
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         player.server.execute(() -> removeDisabledSkills(player));
      }
   }

   @SubscribeEvent
   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         player.server.execute(() -> removeDisabledSkills(player));
      }
   }

   @SubscribeEvent
   public static void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         player.server.execute(() -> removeDisabledSkills(player));
      }
   }

   public static int removeDisabledSkills(ServerPlayer player) {
      SkillBlacklistConfig config = SkillBlacklistConfig.getInstance();
      if (config.getDisabledSkills().isEmpty()) {
         return 0;
      }

      for (String disabledSkill : config.getDisabledSkills()) {
         ResourceLocation id = ResourceLocation.tryParse(disabledSkill);
         if (id != null) {
            SkillRemovalHelper.clearExternalState(player, id);
         }
      }

      Skills skills = SkillAPI.getSkillsFrom(player);
      List<ManasSkillInstance> learnedSkills = List.copyOf(skills.getLearnedSkills());
      int removed = 0;
      int forced = 0;

      for (ManasSkillInstance instance : learnedSkills) {
         if (config.isDisabled(instance.getSkillId())) {
            SkillRemovalHelper.RemovalResult result = SkillRemovalHelper.removeBlacklistedSkill(player, instance.getSkillId());
            if (result.removed()) {
               removed++;
               if (result.forced()) {
                  forced++;
               }
            } else if (result.failed()) {
               TensuraSkillDisabled.LOGGER.warn("Failed to remove blacklisted skill {} from {}", instance.getSkillId(), player.getGameProfile().getName());
            }
         }
      }

      if (removed > 0) {
         TensuraSkillDisabled.LOGGER.debug("Removed {} disabled skills from {} ({} forced)", new Object[]{removed, player.getGameProfile().getName(), forced});
      }

      return removed;
   }
}
