package org.crypticdev.stextras.mixin.server.commands;

import com.mojang.brigadier.context.CommandContext;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Value;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRuleCommand.class)
public class GameRuleCommandMixin {
   @Inject(method = "setRule(Lcom/mojang/brigadier/context/CommandContext;Lnet/minecraft/world/level/GameRules$Key;)I", at = @At("HEAD"), cancellable = true)
   private static <T extends Value<T>> void stextras$forceResetPerSkillLockSetToZero(
      CommandContext<CommandSourceStack> context, Key<T> key, CallbackInfoReturnable<Integer> cir
   ) {
      if (key == TensuraGameRules.RESET_PER_SKILL_LOCK) {
         CommandSourceStack source = (CommandSourceStack)context.getSource();
         forceResetPerSkillLockZero(source.getServer());
         source.sendSuccess(() -> Component.translatable("commands.gamerule.set", new Object[]{key.getId(), "0"}), true);
         cir.setReturnValue(0);
      }
   }

   @Inject(method = "queryRule(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/world/level/GameRules$Key;)I", at = @At("HEAD"), cancellable = true)
   private static <T extends Value<T>> void stextras$forceResetPerSkillLockQueryToZero(
      CommandSourceStack source, Key<T> key, CallbackInfoReturnable<Integer> cir
   ) {
      if (key == TensuraGameRules.RESET_PER_SKILL_LOCK) {
         forceResetPerSkillLockZero(source.getServer());
         source.sendSuccess(() -> Component.translatable("commands.gamerule.query", new Object[]{key.getId(), "0"}), false);
         cir.setReturnValue(0);
      }
   }

   private static void forceResetPerSkillLockZero(MinecraftServer server) {
      IntegerValue rule = (IntegerValue)server.getGameRules().getRule(TensuraGameRules.RESET_PER_SKILL_LOCK);
      if (rule.get() != 0) {
         rule.set(0, server);
      }
   }
}
