package org.crypticdev.stextras.client.screen.skilllock;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.utils.TextUtil;
import org.jetbrains.annotations.Nullable;

public final class STSkillLockTooltips {
   public static Component lockDisabledReason(@Nullable ManasSkillInstance selectedAbility) {
      if (selectedAbility == null) {
         return Component.translatable("stextras.skill_lock.cannot_lock.no_selection");
      } else {
         ResourceLocation id = STSkillLockRules.skillId(selectedAbility);
         if (id == null || !STSkillLockRules.isSkillValid(id)) {
            return Component.translatable("stextras.skill_lock.invalid_skill");
         } else if (STSkillLockRules.isSkillLocked(id)) {
            return Component.translatable("stextras.skill_lock.already_locked");
         } else if (!STSkillLockRules.canLockSkill()) {
            return Component.translatable("stextras.skill_lock.no_slots");
         } else {
            return selectedAbility.isTemporarySkill()
               ? Component.translatable("stextras.skill_lock.cannot_lock.temporary")
               : Component.translatable("stextras.skill_lock.cannot_lock.generic");
         }
      }
   }

   public static List<Component> unlockTooltip(@Nullable ManasSkillInstance selectedAbility, IExistence existenceData) {
      List<Component> tooltip = new ArrayList<>();
      int requiredSoulGrade = STExtarsStorage.Player.getRequiredSoulGradeToUnlockSkill();
      double requiredEP = STExtarsStorage.Player.getRequiredEpToUnlockSkill();
      double requiredMagicule = STExtarsStorage.Player.getRequiredMagiculeToUnlockSkill();
      tooltip.add(Component.literal("Requirements").withStyle(ChatFormatting.GOLD));
      boolean enabled = STSkillLockRules.canUnlockSelectedSkill(selectedAbility, existenceData);
      ResourceLocation id = STSkillLockRules.skillId(selectedAbility);
      if (!enabled) {
         tooltip.add(Component.empty());
         if (!STSkillLockRules.isSkillAdminLocked(id)) {
            tooltip.add(
               Component.literal("Soul Grade Cost: " + requiredSoulGrade)
                  .withStyle(STExtarsStorage.Player.getSoulGrade() >= requiredSoulGrade ? ChatFormatting.GRAY : ChatFormatting.RED)
            );
            tooltip.add(
               Component.literal("MP Cost: " + TextUtil.formatNumber((long)requiredMagicule))
                  .withStyle(existenceData.getMagicule() >= requiredMagicule ? ChatFormatting.GRAY : ChatFormatting.RED)
            );
            tooltip.add(
               Component.literal("EP Needed: " + TextUtil.formatNumber((long)requiredEP))
                  .withStyle(existenceData.getEP() >= requiredEP ? ChatFormatting.GRAY : ChatFormatting.RED)
            );
         }

         tooltip.add(unlockFailureReason(selectedAbility, existenceData).copy().withStyle(ChatFormatting.RED));
         return tooltip;
      } else {
         if (!STSkillLockRules.isSkillAdminLocked(id)) {
            tooltip.add(Component.literal("Soul Grade Cost: " + requiredSoulGrade).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("MP Cost: " + TextUtil.formatNumber((long)requiredMagicule)).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("EP Needed: " + TextUtil.formatNumber((long)requiredEP)).withStyle(ChatFormatting.GRAY));
         }

         return tooltip;
      }
   }

   public static Component unlockFailureReason(@Nullable ManasSkillInstance selectedAbility, IExistence existenceData) {
      if (selectedAbility == null) {
         return Component.translatable("stextras.skill_lock.cannot_unlock.no_selection");
      } else {
         ResourceLocation id = STSkillLockRules.skillId(selectedAbility);
         double requiredEP = STExtarsStorage.Player.getRequiredEpToUnlockSkill();
         int requiredSoulGrade = STExtarsStorage.Player.getRequiredSoulGradeToUnlockSkill();
         double requiredMagicule = STExtarsStorage.Player.getRequiredMagiculeToUnlockSkill();
         if (id == null) {
            return Component.translatable("stextras.skill_lock.invalid_skill");
         } else if (STSkillLockRules.isSkillAdminLocked(id)) {
            return Component.translatable("stextras.skill_lock.admin.deny_unlock");
         } else if (!STSkillLockRules.isSkillValid(id)) {
            return Component.translatable("stextras.skill_lock.invalid_skill");
         } else if (!STSkillLockRules.isSkillLocked(id)) {
            return Component.translatable("stextras.skill_lock.not_locked");
         } else if (STExtarsStorage.Player.getSoulGrade() < requiredSoulGrade) {
            return Component.translatable("stextras.skill_lock.not_enough_soul_grade", new Object[]{requiredSoulGrade});
         } else if (existenceData.getEP() < requiredEP) {
            return Component.translatable("stextras.skill_lock.not_enough_ep", new Object[]{TextUtil.formatNumber((long)requiredEP)});
         } else {
            return existenceData.getMagicule() < requiredMagicule
               ? Component.translatable("stextras.skill_lock.not_enough_magicule", new Object[]{TextUtil.formatNumber((long)requiredMagicule)})
               : Component.translatable("stextras.skill_lock.cannot_unlock.generic");
         }
      }
   }

   private STSkillLockTooltips() {
   }
}
