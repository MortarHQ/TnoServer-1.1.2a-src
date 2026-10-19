package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.CommandImpl;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public final class HelpCommand {
   private static final int COMMANDS_PER_PAGE = 4;

   private HelpCommand() {
   }

   public static void registerCommandIfRequired(CtCommands instance) {
      Map<String, CommandImpl> commands = instance.commands();
      if (!commands.containsKey("help")) {
         instance.registerCommand(
            "help",
            Component.translatable("crafttweaker.command.description.help"),
            builder -> {
               builder.executes(context -> executeHelp(commands, context, 1));
               builder.then(
                  Commands.argument("page", IntegerArgumentType.integer(1))
                     .executes(context -> executeHelp(commands, context, (Integer)context.getArgument("page", int.class)))
               );
            }
         );
      }
   }

   private static int executeHelp(Map<String, CommandImpl> commands, CommandContext<CommandSourceStack> context, int currentPage) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      List<String> allowedToUseCommands = commands.keySet().stream().filter(s -> commands.get(s).requirement().test(source)).toList();
      int maxPages = (int)Math.ceil(allowedToUseCommands.size() / 4.0);
      int clampedPage = Mth.clamp(currentPage, 1, maxPages);
      int minIndex = (clampedPage - 1) * 4;
      int maxIndex = Math.min(commands.size(), minIndex + 4);

      for (int i = minIndex; i < maxIndex; i++) {
         CommandImpl command = commands.get(allowedToUseCommands.get(i));
         String commandStr = "/ct " + command.name();
         source.sendSuccess(() -> CommandUtilities.run(Component.literal(commandStr), commandStr), true);
         source.sendSuccess(() -> Component.literal("- ").append(command.description().withStyle(ChatFormatting.DARK_AQUA)), true);
      }

      source.sendSuccess(() -> Component.translatable("crafttweaker.command.help.page.info", new Object[]{clampedPage, maxPages}), true);
      return 1;
   }
}
