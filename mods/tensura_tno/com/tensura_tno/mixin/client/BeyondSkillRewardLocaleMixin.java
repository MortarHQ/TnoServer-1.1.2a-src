package com.tensura_tno.mixin.client;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "com.trbeyond.quest.reward.SkillReward", remap = false)
public class BeyondSkillRewardLocaleMixin {
   @Redirect(
      method = "award",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;sendSystemMessage(Lnet/minecraft/network/chat/Component;)V"),
      remap = false,
      require = 0
   )
   private void tensura_tno$localizeSkillMismatch(Player player, Component original) {
      List<Component> siblings = original.getSiblings();
      if (siblings.size() >= 3) {
         player.sendSystemMessage(
            Component.translatable("tensura_tno.beyond.skill.soul_mismatch", new Object[]{siblings.get(0), siblings.get(2)}).withStyle(ChatFormatting.RED)
         );
      } else {
         player.sendSystemMessage(original);
      }
   }
}
