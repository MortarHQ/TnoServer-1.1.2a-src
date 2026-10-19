package de.melanx.simplebackups.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.melanx.simplebackups.BackupThread;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;

public class BackupCommand implements Command<CommandSourceStack> {
   public static ArgumentBuilder<CommandSourceStack, ?> register() {
      return Commands.literal("backup")
         .then(
            ((LiteralArgumentBuilder)Commands.literal("start").executes(new BackupCommand()))
               .then(Commands.argument("quiet", BoolArgumentType.bool()).executes(new BackupCommand()))
         );
   }

   public int run(CommandContext<CommandSourceStack> context) {
      boolean quiet = false;

      try {
         quiet = BoolArgumentType.getBool(context, "quiet");
      } catch (IllegalArgumentException var4) {
      }

      MinecraftServer server = ((CommandSourceStack)context.getSource()).getServer();
      BackupThread.createBackup(server, quiet);
      return 1;
   }
}
