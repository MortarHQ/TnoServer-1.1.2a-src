package com.yungnickyoung.minecraft.yungscavebiomes.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.network.chat.Component;

public class SandstormCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx, CommandSelection selection) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("sandstorm").requires(source -> source.hasPermission(2)))
               .then(Commands.literal("start").executes(context -> execute((CommandSourceStack)context.getSource(), "start"))))
            .then(Commands.literal("stop").executes(context -> execute((CommandSourceStack)context.getSource(), "stop")))
      );
   }

   public static int execute(CommandSourceStack commandSource, String action) {
      if (action.equals("start")) {
         SandstormServerData sandstormServerData = ((ISandstormServerDataProvider)commandSource.getLevel()).getSandstormServerData();
         sandstormServerData.start();
         commandSource.sendSuccess(
            () -> Component.translatable("command.sandstorm.start", new Object[]{commandSource.getLevel().dimension().location().toString()}), false
         );
         return 1;
      } else if (action.equals("stop")) {
         SandstormServerData sandstorm = ((ISandstormServerDataProvider)commandSource.getLevel()).getSandstormServerData();
         sandstorm.stop();
         commandSource.sendSuccess(
            () -> Component.translatable("command.sandstorm.stop", new Object[]{commandSource.getLevel().dimension().location().toString()}), false
         );
         return 1;
      } else {
         commandSource.sendFailure(Component.literal("Unrecognized action."));
         return -1;
      }
   }
}
