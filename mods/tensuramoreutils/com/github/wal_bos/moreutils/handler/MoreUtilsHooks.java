package com.github.wal_bos.moreutils.handler;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillBanType;
import com.github.wal_bos.moreutils.util.MoreUtilsPurge;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.LifecycleEvent;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.EngraveEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.AbilityEquipEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillLearningEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class MoreUtilsHooks {
   private MoreUtilsHooks() {
   }

   public static void init() {
      LifecycleEvent.SERVER_STARTING.register(MoreUtilsData::load);
      LifecycleEvent.SERVER_STOPPING.register(MoreUtilsData::save);
      NeoForge.EVENT_BUS.addListener(MoreUtilsHooks::onPlayerLogin);
      NeoForge.EVENT_BUS.addListener(MoreUtilsHooks::onServerTick);
      SkillEvents.UNLOCK_SKILL.register((UnlockSkillEvent)(instance, entity, unlockMessage) -> {
         if (MoreUtilsPurge.isRefundingMaxMagicules()) {
            SkillBanType type = MoreUtilsData.getSkillBan(instance.getSkillId());
            return type != null && type.blocksLearning() ? EventResult.interruptFalse() : EventResult.pass();
         }

         if (entity instanceof ServerPlayer player) {
            MoreUtilsSkillCap.processResetCounter(player);
            if (MoreUtilsSkillCap.blocksUnlock(player, instance)) {
               return EventResult.interruptFalse();
            }
         }

         SkillBanType type = MoreUtilsData.getSkillBan(instance.getSkillId());
         if (type != null && type.blocksLearning()) {
            if (type.blocksOwning()) {
               MoreUtilsPurge.purgeSkillFromEntity(entity, instance.getSkill());
            }

            sendBlockedSkillMessage(entity, instance.getSkillId());
            return EventResult.interruptFalse();
         } else {
            enforceEntityOnce(entity);
            return EventResult.pass();
         }
      });
      TensuraSkillEvents.SKILL_LEARNING.register((SkillLearningEvent)(instance, entity, mode, requirement, newPoint) -> {
         if (MoreUtilsPurge.isRefundingMaxMagicules()) {
            SkillBanType type = MoreUtilsData.getSkillBan(instance.getSkillId());
            if (type != null && type.blocksLearning()) {
               newPoint.set(0.0);
               return EventResult.interruptFalse();
            } else {
               return EventResult.pass();
            }
         } else {
            if (entity instanceof ServerPlayer player) {
               MoreUtilsSkillCap.processResetCounter(player);
               if (MoreUtilsSkillCap.blocksUnlock(player, instance)) {
                  refundBlockedLearningCost(player, instance, mode);
                  newPoint.set(0.0);
                  return EventResult.interruptFalse();
               }
            }

            SkillBanType type = MoreUtilsData.getSkillBan(instance.getSkillId());
            if (type != null && type.blocksLearning()) {
               newPoint.set(0.0);
               if (type.blocksOwning()) {
                  MoreUtilsPurge.purgeSkillFromEntity(entity, instance.getSkill());
               }

               sendBlockedSkillMessage(entity, instance.getSkillId());
               return EventResult.interruptFalse();
            } else {
               enforceEntityOnce(entity);
               return EventResult.pass();
            }
         }
      });
      TensuraSkillEvents.SKILL_PLUNDER.register((SkillPlunderEvent)(target, owner, steal, skill) -> {
         if (MoreUtilsPurge.isRefundingMaxMagicules()) {
            return EventResult.pass();
         }

         if (owner instanceof LivingEntity livingOwner) {
            enforceEntityOnce(livingOwner);
         }

         ManasSkill selected = (ManasSkill)skill.get();
         if (selected != null && isBlacklisted(selected)) {
            ManasSkill replacement = findPlunderReplacement(target, owner, selected);
            if (replacement != null) {
               skill.set(replacement);
               return EventResult.pass();
            } else {
               return EventResult.interruptFalse();
            }
         } else {
            return EventResult.pass();
         }
      });
      TensuraSkillEvents.ABILITY_EQUIP.register((AbilityEquipEvent)(player, instance, mode, slot, preset) -> {
         if (MoreUtilsPurge.isRefundingMaxMagicules()) {
            return EventResult.pass();
         }

         ManasSkillInstance value = (ManasSkillInstance)instance.get();
         if (value != null) {
            SkillBanType type = MoreUtilsData.getSkillBan(value.getSkillId());
            if (type != null && type.blocksUse()) {
               MoreUtilsPurge.purgeSkillFromEntity(player, value.getSkill());
               sendBlockedSkillMessage(player, value.getSkillId());
               return EventResult.interruptFalse();
            }
         }

         enforceEntityOnce(player);
         return EventResult.pass();
      });
      TensuraEntityEvents.ENGRAVE_EVENT.register((EngraveEvent)(entity, stack, engraving, level) -> {
         Holder<Enchantment> holder = (Holder<Enchantment>)engraving.get();
         if (holder != null && holder.unwrapKey().isPresent()) {
            ResourceLocation id = ((ResourceKey)holder.unwrapKey().get()).location();
            if (MoreUtilsData.isEngravingBanned(id)) {
               return EventResult.interruptFalse();
            }
         }

         if (entity instanceof ServerPlayer player && MoreUtilsData.hasEngravingBans()) {
            MoreUtilsPurge.purgeAllBannedEngravingsFromPlayer(player);
         }

         return EventResult.pass();
      });
   }

   private static void refundBlockedLearningCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      if (!entity.hasInfiniteMaterials()) {
         if (instance.getSkill() instanceof TensuraSkill skill) {
            double var11 = skill.getLearningCostMultiplier(mode);
            double auraRefund = skill.getAuraCost(entity, instance, mode) * var11;
            double magiculeRefund = skill.getMagiculeCost(entity, instance, mode) * var11;
            if (!Double.isFinite(auraRefund) || auraRefund <= 0.0) {
               auraRefund = 0.0;
            }

            if (!Double.isFinite(magiculeRefund) || magiculeRefund <= 0.0) {
               magiculeRefund = 0.0;
            }

            if (!(auraRefund <= 0.0) || !(magiculeRefund <= 0.0)) {
               IExistence existence = TensuraStorages.getExistenceFrom(entity);
               if (auraRefund > 0.0) {
                  existence.setAura(existence.getAura() + auraRefund);
               }

               if (magiculeRefund > 0.0) {
                  existence.setMagicule(existence.getMagicule() + magiculeRefund);
               }

               existence.markDirty();
            }
         }
      }
   }

   private static void onServerTick(Post event) {
      if (event.getServer().getTickCount() % 20 == 0) {
         for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            MoreUtilsSkillCap.processResetCounter(player);
         }
      }
   }

   private static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         MoreUtilsData.updatePlayerName(player.server, player.getUUID(), player.getGameProfile().getName());
         MoreUtilsSkillCap.processResetCounter(player);
         enforcePlayer(player);
      }
   }

   private static void enforceEntityOnce(LivingEntity entity) {
      if (!MoreUtilsPurge.isRefundingMaxMagicules()) {
         if (MoreUtilsData.hasOwningSkillBans()) {
            MoreUtilsPurge.purgeAllBannedSkillsFromEntity(entity);
         }

         if (entity instanceof ServerPlayer player && MoreUtilsData.hasEngravingBans()) {
            MoreUtilsPurge.purgeAllBannedEngravingsFromPlayer(player);
         }
      }
   }

   private static void sendBlockedSkillMessage(LivingEntity entity, ResourceLocation skillId) {
   }

   private static boolean isBlacklisted(ManasSkill skill) {
      ResourceLocation id = skill.getRegistryName();
      SkillBanType type = id == null ? null : MoreUtilsData.getSkillBan(id);
      return type != null && type.isBlacklist();
   }

   private static ManasSkill findPlunderReplacement(Entity target, Entity owner, ManasSkill blockedSkill) {
      if (!(target instanceof LivingEntity targetLiving)) {
         return null;
      } else {
         SkillStorage targetSkills = SkillAPI.getSkillsFrom(targetLiving);
         Skills ownerSkills = owner instanceof LivingEntity ownerLiving ? SkillAPI.getSkillsFrom(ownerLiving) : null;

         for (ManasSkillInstance candidate : targetSkills.getLearnedSkills()) {
            ManasSkill candidateSkill = candidate.getSkill();
            if (candidateSkill != blockedSkill
               && candidateSkill.getRegistryName() != null
               && !isBlacklisted(candidateSkill)
               && !candidate.isTemporarySkill()
               && !(candidate.getMastery() < 0.0)
               && (ownerSkills == null || !ownerSkills.getSkill(candidateSkill).isPresent())) {
               return candidateSkill;
            }
         }

         return null;
      }
   }

   public static void enforcePlayer(ServerPlayer player) {
      enforceEntityOnce(player);
   }
}
