package org.crypticdev.stextras.client.screen.skilllock;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.jetbrains.annotations.Nullable;

public final class STSkillLockRules {
   @Nullable
   public static ResourceLocation skillId(@Nullable ManasSkillInstance selectedAbility) {
      if (selectedAbility == null) {
         return null;
      }

      ManasSkill skill = selectedAbility.getSkill();
      return SkillAPI.getSkillRegistry().getId(skill);
   }

   public static boolean canLockSelectedSkill(@Nullable ManasSkillInstance selectedAbility) {
      if (selectedAbility != null && !selectedAbility.isTemporarySkill()) {
         ResourceLocation id = skillId(selectedAbility);
         return id != null && STExtarsStorage.Player.canLockSkill(id);
      } else {
         return false;
      }
   }

   public static boolean canUnlockSelectedSkill(@Nullable ManasSkillInstance selectedAbility, IExistence existenceData) {
      ResourceLocation id = skillId(selectedAbility);
      return id != null && STExtarsStorage.Player.canUnlockSkill(id, existenceData.getEP(), existenceData.getMagicule());
   }

   public static boolean canLockSkill() {
      return STExtarsStorage.Player.hasAvailableSkillLockSlot();
   }

   public static int getMaxLocksForSoulGrade(int soulGrade) {
      return STExtarsStorage.Player.getMaxLocksForSoulGradeLevel(soulGrade);
   }

   public static boolean isSkillValid(@Nullable ResourceLocation skill) {
      return STExtarsStorage.Player.isSkillValid(skill);
   }

   public static boolean isSkillLocked(@Nullable ResourceLocation skill) {
      return skill != null && STExtarsStorage.Player.isSkillLocked(skill);
   }

   public static boolean isSkillAdminLocked(@Nullable ResourceLocation skill) {
      return skill != null && STExtarsStorage.Player.isSkillAdminLocked(skill);
   }

   public static double requiredMagicule() {
      return STExtarsStorage.Player.getRequiredMagiculeToUnlockSkill();
   }

   private STSkillLockRules() {
   }
}
