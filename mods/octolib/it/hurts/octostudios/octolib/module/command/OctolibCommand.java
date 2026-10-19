package it.hurts.octostudios.octolib.module.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.networking.NetworkManager;
import it.hurts.octostudios.octolib.module.config.ConfigManager;
import it.hurts.octostudios.octolib.module.config.network.TestScreenPacket;
import it.hurts.octostudios.octolib.module.config.network.UnholyAbominationPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.network.chat.Component;

public class OctolibCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, CommandSelection commandSelection) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("octolib")
                     .requires(s -> s.hasPermission(2)))
                  .then(Commands.literal("openScreen").then(Commands.argument("classpath", StringArgumentType.string()).executes(context -> {
                     String path = StringArgumentType.getString(context, "classpath");
                     NetworkManager.sendToPlayer(((CommandSourceStack)context.getSource()).getPlayer(), new UnholyAbominationPacket(path));
                     return 1;
                  }))))
               .then(
                  Commands.literal("animatorSystemTestScreen")
                     .executes(
                        component -> {
                           if (((CommandSourceStack)component.getSource()).getPlayer() == null) {
                              ((CommandSourceStack)component.getSource())
                                 .sendFailure(Component.literal("This command should be ran by a player.").withStyle(ChatFormatting.RED));
                           }

                           NetworkManager.sendToPlayer(((CommandSourceStack)component.getSource()).getPlayer(), new TestScreenPacket());
                           return 1;
                        }
                     )
               ))
            .then(
               Commands.literal("config")
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("reload")
                           .then(
                              Commands.literal("all")
                                 .executes(
                                    conComponent -> {
                                       int counter = 0;
                                       boolean isAdmin = ((CommandSourceStack)conComponent.getSource()).hasPermission(4);

                                       for (String path : ConfigManager.getAllPaths()) {
                                          try {
                                             if (ConfigManager.isServerConfig(path)) {
                                                if (isAdmin) {
                                                   ConfigManager.reload(path);
                                                   ConfigManager.syncConfig(path, ((CommandSourceStack)conComponent.getSource()).getServer());
                                                } else {
                                                   ((CommandSourceStack)conComponent.getSource())
                                                      .sendFailure(Component.literal("You have not permission to reload config."));
                                                }
                                             } else {
                                                ConfigManager.reload(path);
                                             }

                                             counter++;
                                          } catch (RuntimeException e) {
                                             e.printStackTrace();
                                             ((CommandSourceStack)conComponent.getSource())
                                                .sendFailure(
                                                   Component.literal("Error occurs while reload config by path ").append(Component.literal("\"" + path + "\""))
                                                );
                                             return 0;
                                          }
                                       }

                                       ((CommandSourceStack)conComponent.getSource())
                                          .sendSystemMessage(Component.literal(counter + " configs reload successfully"));
                                       return 1;
                                    }
                                 )
                           ))
                        .then(
                           Commands.argument("path", StringArgumentType.string())
                              .suggests((c, b) -> SharedSuggestionProvider.suggest(ConfigManager.getAllPaths(), b))
                              .executes(
                                 c -> {
                                    String path = StringArgumentType.getString(c, "path");
                                    boolean isAdmin = ((CommandSourceStack)c.getSource()).hasPermission(4);
                                    if (!ConfigManager.getAllPaths().contains(path)) {
                                       ((CommandSourceStack)c.getSource()).sendFailure(Component.literal("Config by path \"" + path + "\" does not exist"));
                                       return 0;
                                    }

                                    try {
                                       if (ConfigManager.isServerConfig(path)) {
                                          if (isAdmin) {
                                             ConfigManager.reload(path);
                                             ConfigManager.syncConfig(path, ((CommandSourceStack)c.getSource()).getServer());
                                          } else {
                                             ((CommandSourceStack)c.getSource()).sendFailure(Component.literal("You have not permission to reload config."));
                                          }
                                       } else {
                                          ConfigManager.reload(path);
                                       }

                                       ((CommandSourceStack)c.getSource())
                                          .sendSystemMessage(Component.literal("Config with path \"" + path + "\" has been reloaded successfully"));
                                       return 1;
                                    } catch (RuntimeException e) {
                                       e.printStackTrace();
                                       ((CommandSourceStack)c.getSource())
                                          .sendFailure(
                                             Component.literal("Error occurs while reloading config by path ").append(Component.literal("\"" + path + "\""))
                                          );
                                       return 0;
                                    }
                                 }
                              )
                        )
                  )
            )
      );
   }
}
