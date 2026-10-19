package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationConfig;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public final class SkillAvailabilityHandler {
   private static boolean initialized;
   private static final Map<UUID, Set<ResourceLocation>> messageSent = new HashMap<>();

   private SkillAvailabilityHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         SkillEvents.UNLOCK_SKILL.register((UnlockSkillEvent)(instance, entity, unlockMessage) -> {
            if (TenreincarnationConfig.isSkillEnabled(instance.getSkillId())) {
               return EventResult.pass();
            }

            sendDisabledMessage(instance, entity);
            return EventResult.interruptFalse();
         });
      }
   }

   private static void sendDisabledMessage(ManasSkillInstance instance, LivingEntity entity) {
      UUID entityId = entity.getUUID();
      ResourceLocation skillId = instance.getSkillId();
      Set<ResourceLocation> sent = messageSent.computeIfAbsent(entityId, k -> new HashSet<>());
      if (sent.add(skillId)) {
         entity.sendSystemMessage(
            Component.translatable("tennogamenolife.skill.disabled", new Object[]{instance.getChatDisplayName(true)}).withStyle(ChatFormatting.RED)
         );
      }
   }
}
