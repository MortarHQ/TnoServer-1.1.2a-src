package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tensura_tno.compat.RandomSkillBookMessageGuard;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = {"io.github.Memoires.mysticism.handler.AbilityHandler", "io.github.Memoires.mysticism.handler.AttributeHandler"}, remap = false)
public abstract class MysticismRandomBookMessageMixin {
   @WrapOperation(
      method = "*",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V"),
      remap = false,
      require = 1
   )
   private static void tensuraTno$deferRandomCandidateSoulEnergyFailure(Player player, Component message, boolean actionBar, Operation<Void> original) {
      String key = message.getContents() instanceof TranslatableContents contents ? contents.getKey() : null;
      if (!RandomSkillBookMessageGuard.deferSoulEnergyFailure(player, key, () -> original.call(new Object[]{player, message, actionBar}))) {
         original.call(new Object[]{player, message, actionBar});
      }
   }
}
