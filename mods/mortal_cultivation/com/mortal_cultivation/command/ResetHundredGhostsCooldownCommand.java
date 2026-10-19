package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mortal_cultivation.ability.skill.extra.HundredGhostsSurgeSkill;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ResetHundredGhostsCooldownCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("resethundredghosts")
                  .requires(source -> source.hasPermission(2)))
               .then(Commands.argument("player", EntityArgument.player()).executes(ResetHundredGhostsCooldownCommand::resetPlayerCooldown)))
            .executes(ResetHundredGhostsCooldownCommand::resetSelfCooldown)
      );
   }

   private static int resetPlayerCooldown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
      HundredGhostsSurgeSkill.resetPlayerCooldowns(targetPlayer);
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.reset_hundred_ghosts.success_other", new Object[]{targetPlayer.getName().getString()}), true
      );
      targetPlayer.sendSystemMessage(Component.translatable("mortal_cultivation.command.reset_hundred_ghosts.notification"));
      return 1;
   }

   private static int resetSelfCooldown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer player = source.getPlayerOrException();
      HundredGhostsSurgeSkill.resetPlayerCooldowns(player);
      source.sendSuccess(() -> Component.translatable("mortal_cultivation.command.reset_hundred_ghosts.success_self"), false);
      return 1;
   }
}
