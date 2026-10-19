package org.crypticdev.stextras.quest.reward;

import com.mojang.logging.LogUtils;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestRewardItem;
import org.crypticdev.stextras.quest.definition.QuestRewardSet;
import org.crypticdev.stextras.quest.definition.QuestRewardSkill;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;
import org.slf4j.Logger;

public final class QuestRewardService {
   private static final Logger LOGGER = LogUtils.getLogger();

   public static void grant(ServerPlayer player, QuestDefinition QuestDefinition) {
      if (QuestDefinition.rewards != null && !QuestDefinition.rewards.isEmpty()) {
         if (QuestDefinition.rewards.items != null) {
            for (QuestRewardItem itemReward : QuestDefinition.rewards.items) {
               Item item = (Item)BuiltInRegistries.ITEM.get(itemReward.id);
               if (item != null) {
                  player.getInventory().placeItemBackInInventory(new ItemStack(item, Math.max(1, itemReward.count)));
               }
            }
         }

         grantSkills(player, QuestDefinition.rewards, QuestDefinition);
         if (QuestDefinition.rewards.xp != null && QuestDefinition.rewards.xp > 0) {
            player.giveExperiencePoints(QuestDefinition.rewards.xp);
         }

         if (QuestDefinition.rewards.re != null && QuestDefinition.rewards.re > 0) {
            int current = ((ISTExtrasPlayer)player).getReincarnationEssence();
            ((ISTExtrasPlayer)player)
               .setReincarnationEssence(
                  current
                     + STExtarsStorage.Player.GetREBoosted(
                        TensuraStorages.getPlayerDataFrom(player).getResetCounter(), QuestDefinition.rewards.re, QuestDefinition.category
                     )
               );
         }

         if (QuestDefinition.rewards.commands != null) {
            MinecraftServer server = player.server;

            for (String command : QuestDefinition.rewards.commands) {
               server.getCommands().performPrefixedCommand(server.createCommandSourceStack().withSuppressedOutput(), command);
            }
         }
      }
   }

   public static void grantSubordinate(ServerPlayer owner, LivingEntity subordinate, QuestDefinition QuestDefinition) {
      if (QuestDefinition.subordinateRewards != null && !QuestDefinition.subordinateRewards.isEmpty()) {
         grantSkills(subordinate, QuestDefinition.subordinateRewards, QuestDefinition);
         if (QuestDefinition.subordinateRewards.commands != null) {
            MinecraftServer server = owner.server;

            for (String command : QuestDefinition.subordinateRewards.commands) {
               server.getCommands().performPrefixedCommand(subordinate.createCommandSourceStack().withPermission(4).withSuppressedOutput(), command);
            }
         }
      }
   }

   private static void grantSkills(LivingEntity target, QuestRewardSet rewards, QuestDefinition QuestDefinition) {
      if (rewards.skills != null) {
         for (QuestRewardSkill skillReward : rewards.skills) {
            if (skillReward.id != null) {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillReward.id);
               if (skill == null) {
                  LOGGER.warn("[STExtras] Unknown Tensura skill reward '{}' in quest '{}'", skillReward.id, QuestDefinition.id);
               } else {
                  ManasSkillInstance instance = new TensuraSkillInstance(skill);
                  if (skillReward.mastery >= 0.0) {
                     instance.setMastery(skillReward.mastery);
                  }

                  if (skillReward.noMagiculeCost) {
                     instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  }

                  boolean learned = SkillHelper.learnSkill(target, instance, skillReward.removeTime, Component.empty());
                  SkillStorage skillStorage = SkillAPI.getSkillsFrom(target);
                  if (learned || skillStorage != null && !skillStorage.getSkill(skillReward.id).isEmpty()) {
                     if (skillStorage != null) {
                        skillStorage.markDirty();
                     }
                  } else {
                     LOGGER.warn(
                        "[STExtras] Failed to grant Tensura skill reward '{}' to '{}' in quest '{}'",
                        new Object[]{skillReward.id, target.getName().getString(), QuestDefinition.id}
                     );
                  }
               }
            }
         }
      }
   }

   private QuestRewardService() {
   }
}
