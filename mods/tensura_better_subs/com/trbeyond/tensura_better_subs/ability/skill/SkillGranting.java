package com.trbeyond.tensura_better_subs.ability.skill;

import com.trbeyond.tensura_better_subs.config.ExpandedSkillConfig;
import com.trbeyond.tensura_better_subs.network.s2c.OpenSkillGrantMenuPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SkillGranting {
   public static final ExpandedSkillConfig.SkillGranting CONFIG = ((ExpandedSkillConfig)ConfigRegistry.getConfig(ExpandedSkillConfig.class)).SkillGranting;

   public static void attemptGrantSkill(LivingEntity owner, ManasSkill skillGrantingSkill) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, 8.0, false, false);
      if (target != null && SubordinateHelper.isSubordinate(owner, target)) {
         if (owner instanceof ServerPlayer player) {
            NetworkManager.sendToPlayer(player, new OpenSkillGrantMenuPayload(target.getId(), skillGrantingSkill.getRegistryName()));
         }
      } else if (owner instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
      }
   }

   public static boolean isValidSkillInstance(ManasSkillInstance instance, LivingEntity owner) {
      if (!(instance.getSkill() instanceof TensuraSkill tensuraSkill)) {
         return false;
      } else if (instance.getSkill() instanceof Skill skill
         && (
            skill.getType() == SkillType.RESISTANCE
               || skill.getType() == SkillType.INTRINSIC
               || skill.getType() == SkillType.UNIQUE
               || skill.getType() == SkillType.ULTIMATE
         )) {
         return false;
      } else if (instance.getMastery() < 0.0) {
         return false;
      } else if (!instance.canInteractSkill(owner)) {
         return false;
      } else {
         return instance.isTemporarySkill() ? false : tensuraSkill.getAcquiringMagiculeCost(instance) != 0.0 || CONFIG.allowNonSkillGrants;
      }
   }

   public static double getAcquisitionCost(ManasSkillInstance instance, LivingEntity owner) {
      if (isValidSkillInstance(instance, owner)) {
         TensuraSkill skill = (TensuraSkill)instance.getSkill();
         double cost = skill.getAcquiringMagiculeCost(instance);
         if (cost == 0.0) {
            cost = CONFIG.defaultAbilityGrantCost;
         }

         if (cost > 0.0) {
            return cost * CONFIG.skillGrantCostMultiplier;
         }
      }

      return -1.0;
   }
}
