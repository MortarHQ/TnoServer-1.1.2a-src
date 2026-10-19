package dev.ftb.mods.ftbultimine;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.net.EditConfigChoicePacket;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.network.chat.Component;

public class FTBUltimineCommands {
   public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, CommandSelection selection) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ftbultimine")
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("serverconfig").requires(sourceStack -> sourceStack.isPlayer() && sourceStack.hasPermission(2)))
                        .executes(
                           context -> {
                              NetworkManager.sendToPlayer(
                                 ((CommandSourceStack)context.getSource()).getPlayerOrException(), EditConfigChoicePacket.server("ftbultimine-server")
                              );
                              return 1;
                           }
                        )
                  ))
               .then(((LiteralArgumentBuilder)Commands.literal("clientconfig").requires(CommandSourceStack::isPlayer)).executes(context -> {
                  NetworkManager.sendToPlayer(
                     ((CommandSourceStack)context.getSource()).getPlayerOrException(), EditConfigChoicePacket.client("ftbultimine-client")
                  );
                  return 1;
               })))
            .then(
               ((LiteralArgumentBuilder)Commands.literal("config").requires(CommandSourceStack::isPlayer))
                  .executes(
                     context -> {
                        NetworkManager.sendToPlayer(
                           ((CommandSourceStack)context.getSource()).getPlayerOrException(),
                           EditConfigChoicePacket.choose("ftbultimine-client", "ftbultimine-server", Component.translatable("key.ftbultimine"))
                        );
                        return 1;
                     }
                  )
            )
      );
   }
}
