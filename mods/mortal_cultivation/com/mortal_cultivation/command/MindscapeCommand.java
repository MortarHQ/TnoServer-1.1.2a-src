package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mortal_cultivation.event.MindscapeSleepHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MindscapeCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("mortalcultivation").requires(source -> source.hasPermission(2)))
            .then(
               Commands.literal("admin")
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("mindscape").executes(MindscapeCommand::forceSelf))
                        .then(Commands.argument("player", EntityArgument.player()).executes(MindscapeCommand::forceOther))
                  )
            )
      );
   }

   private static int forceSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      MindscapeSleepHandler.setForceFlag(player);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(() -> Component.translatable("mortal_cultivation.command.mindscape.force_self").withStyle(ChatFormatting.GREEN), false);
      return 1;
   }

   private static int forceOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(context, "player");
      MindscapeSleepHandler.setForceFlag(target);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.mindscape.force_other", new Object[]{target.getName().getString()})
               .withStyle(ChatFormatting.GREEN),
            true
         );
      return 1;
   }
}
