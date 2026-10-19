package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mortal_cultivation.ability.skill.unique.GreatSageSkill;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ResetGreatSageCooldownCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("resetgreatsage")
                  .requires(source -> source.hasPermission(2)))
               .then(Commands.argument("player", EntityArgument.player()).executes(ResetGreatSageCooldownCommand::resetPlayerCooldown)))
            .executes(ResetGreatSageCooldownCommand::resetSelfCooldown)
      );
   }

   private static int resetPlayerCooldown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
      GreatSageSkill.resetPlayerCooldowns(targetPlayer);
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.reset_great_sage.success_other", new Object[]{targetPlayer.getName().getString()})
            .withStyle(ChatFormatting.GREEN),
         true
      );
      targetPlayer.sendSystemMessage(Component.translatable("mortal_cultivation.command.reset_great_sage.notification").withStyle(ChatFormatting.GOLD));
      return 1;
   }

   private static int resetSelfCooldown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer player = source.getPlayerOrException();
      GreatSageSkill.resetPlayerCooldowns(player);
      source.sendSuccess(() -> Component.translatable("mortal_cultivation.command.reset_great_sage.success_self").withStyle(ChatFormatting.GREEN), false);
      return 1;
   }
}
