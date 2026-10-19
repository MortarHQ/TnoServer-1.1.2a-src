package com.tensura_tno.event;

import com.tensura_tno.compat.MysticismRestrictedSkillPolicy;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;

@EventBusSubscriber(modid = "tensura_tno")
public final class MysticismRestrictedSkillHandler {
   private MysticismRestrictedSkillHandler() {
   }

   public static void register() {
      SkillEvents.UNLOCK_SKILL
         .register(
            (UnlockSkillEvent)(instance, owner, message) -> shouldBlock(owner, instance.getSkillId()) ? EventResult.interruptFalse() : EventResult.pass()
         );
   }

   public static boolean shouldBlock(LivingEntity owner, ResourceLocation skillId) {
      if (owner instanceof ServerPlayer player && MysticismRestrictedSkillPolicy.RESTRICTED.equals(skillId)) {
         ResourceLocation race = MysticismRestrictedSkillPolicy.effectiveRace(player.getUUID(), () -> actualRace(player));
         return MysticismRestrictedSkillPolicy.shouldBlock(skillId, race);
      } else {
         return false;
      }
   }

   private static ResourceLocation actualRace(ServerPlayer player) {
      return RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace().getRegistryName()).orElse(null);
   }

   public static void removeInvalidSkill(ServerPlayer player) {
      if (MysticismRestrictedSkillPolicy.shouldBlock(MysticismRestrictedSkillPolicy.RESTRICTED, actualRace(player))) {
         SkillStorage skills = SkillAPI.getSkillsFrom(player);
         skills.forgetSkill(MysticismRestrictedSkillPolicy.RESTRICTED, null);
         removeModifier(player, TensuraAttributes.MAGICULE_GAIN);
         removeModifier(player, TensuraAttributes.AURA_GAIN);
         removeModifier(player, TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
         removeModifier(player, TensuraAttributes.RESISTANCE_DEGRADATION);
         removeModifier(player, Attributes.MOVEMENT_SPEED);
         removeModifier(player, Attributes.SNEAKING_SPEED);
         removeModifier(player, Attributes.SAFE_FALL_DISTANCE);
         removeModifier(player, Attributes.ARMOR);
      }
   }

   private static void removeModifier(ServerPlayer player, Holder<Attribute> attribute) {
      AttributeInstance instance = player.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(MysticismRestrictedSkillPolicy.RESTRICTED);
      }
   }

   @SubscribeEvent
   public static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         removeInvalidSkill(player);
      }
   }

   @SubscribeEvent
   public static void onRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         removeInvalidSkill(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Pre event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.tickCount % 20 == 0 || SkillAPI.getSkillsFrom(player).getSkill(MysticismRestrictedSkillPolicy.RESTRICTED).isPresent()) {
            removeInvalidSkill(player);
         }
      }
   }
}
