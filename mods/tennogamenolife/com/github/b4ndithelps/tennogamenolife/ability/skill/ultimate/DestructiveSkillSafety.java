package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level.ExplosionInteraction;

public final class DestructiveSkillSafety {
   private DestructiveSkillSafety() {
   }

   public static boolean isDisabled() {
      return TenreincarnationConfig.isDestructiveSkillsDisabled();
   }

   public static boolean shouldPreventTerrainDamage() {
      return TenreincarnationConfig.shouldPreventTerrainDamage();
   }

   public static boolean blockIfDisabled(LivingEntity entity, String skillNameKey) {
      if (!isDisabled()) {
         return false;
      }

      if (entity instanceof Player player) {
         player.sendSystemMessage(
            Component.translatable("tennogamenolife.skill.destructive_disabled", new Object[]{Component.translatable(skillNameKey)})
               .withStyle(ChatFormatting.RED)
         );
      }

      return true;
   }

   public static ExplosionInteraction interaction(ExplosionInteraction defaultMode) {
      return shouldPreventTerrainDamage() ? ExplosionInteraction.NONE : defaultMode;
   }
}
