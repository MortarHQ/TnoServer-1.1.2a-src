package com.trbeyond.tensura_better_subs.ability.skill;

import com.trbeyond.tensura_better_subs.network.s2c.OpenSkillGrantMenuPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IFoodChain {
   int getProvideMode();

   default void openProvideMenu(LivingEntity owner, ManasSkill skillGrantingSkill) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, 8.0, false, false);
      if (target == null) {
         if (owner instanceof Player player) {
            player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
         }
      } else if (SubordinateHelper.isSubordinate(owner, target) && !(target instanceof Player)) {
         if (owner instanceof ServerPlayer player) {
            NetworkManager.sendToPlayer(player, new OpenSkillGrantMenuPayload(target.getId(), skillGrantingSkill.getRegistryName()));
         }
      } else if (owner instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.targeting.not_allowed").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
      }
   }
}
