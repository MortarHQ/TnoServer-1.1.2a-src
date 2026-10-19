package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.STExtrasQuestMessageLocalization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.handler.HandlePrestigeListeners", remap = false)
public class STExtrasQuestCompletedLocalizationMixin {
   @Redirect(
      method = "finishCompletedQuest(Lnet/minecraft/server/level/ServerPlayer;Lorg/crypticdev/stextras/storage/quest/STExtrasQuestStorage;Lnet/minecraft/resources/ResourceLocation;Lorg/crypticdev/stextras/quest/definition/QuestDefinition;Lnet/minecraft/world/entity/LivingEntity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;sendSystemMessage(Lnet/minecraft/network/chat/Component;)V", remap = true),
      remap = false,
      require = 0
   )
   private static void tensura_tno$sendLocalizedCompletion(
      ServerPlayer recipient,
      Component original,
      ServerPlayer player,
      @Coerce Object store,
      ResourceLocation questId,
      @Coerce Object quest,
      LivingEntity subordinateRewardTarget
   ) {
      try {
         String name = (String)quest.getClass().getField("name").get(quest);
         recipient.sendSystemMessage(STExtrasQuestMessageLocalization.completed(questId, name));
      } catch (ReflectiveOperationException ignored) {
         recipient.sendSystemMessage(original);
      }
   }
}
