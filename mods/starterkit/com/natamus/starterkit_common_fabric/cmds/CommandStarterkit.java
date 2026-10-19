package com.natamus.starterkit_common_fabric.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.natamus.collective_common_fabric.functions.MessageFunctions;
import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.data.Constants;
import com.natamus.starterkit_common_fabric.data.Variables;
import com.natamus.starterkit_common_fabric.functions.StarterCheckFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterCommandFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterDataFunctions;
import com.natamus.starterkit_common_fabric.functions.StarterGearFunctions;
import com.natamus.starterkit_common_fabric.util.Util;
import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2168;
import net.minecraft.class_2170;
import net.minecraft.class_2186;
import net.minecraft.class_2561;

public class CommandStarterkit {
   public static void register(CommandDispatcher<class_2168> dispatcher) {
      for (String commandPrefix : Constants.commandPrefixes) {
         dispatcher.register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)class_2170.method_9247(
                                                                  commandPrefix
                                                               )
                                                               .executes(command -> showCommandHelp((class_2168)command.getSource())))
                                                            .then(
                                                               class_2170.method_9247("help")
                                                                  .executes(command -> showCommandHelp((class_2168)command.getSource()))
                                                            ))
                                                         .then(
                                                            class_2170.method_9247("info")
                                                               .then(
                                                                  class_2170.method_9244("kit_name", StringArgumentType.string())
                                                                     .suggests(StarterCommandFunctions.activeKitSuggestions)
                                                                     .executes(command -> {
                                                                        class_2168 source = (class_2168)command.getSource();
                                                                        String kitName = Util.findCorrectKitNameFromInput(
                                                                           StringArgumentType.getString(command, "kit_name")
                                                                        );
                                                                        return StarterGearFunctions.showKitInformation(
                                                                           source.method_9225(), source, null, kitName
                                                                        );
                                                                     })
                                                               )
                                                         ))
                                                      .then(
                                                         class_2170.method_9247("info")
                                                            .then(
                                                               class_2170.method_9244("kit_name", StringArgumentType.string())
                                                                  .suggests(StarterCommandFunctions.activeKitSuggestions)
                                                                  .then(class_2170.method_9244("target", class_2186.method_9305()).executes(command -> {
                                                                     class_1657 targetPlayer = class_2186.method_9315(command, "target");
                                                                     String kitName = Util.findCorrectKitNameFromInput(
                                                                        StringArgumentType.getString(command, "kit_name")
                                                                     );
                                                                     return StarterGearFunctions.showKitInformation(
                                                                        targetPlayer.method_37908(), null, targetPlayer, kitName
                                                                     );
                                                                  }))
                                                            )
                                                      ))
                                                   .then(
                                                      class_2170.method_9247("choose")
                                                         .then(
                                                            class_2170.method_9244("kit_name", StringArgumentType.string())
                                                               .suggests(StarterCommandFunctions.activeKitSuggestions)
                                                               .executes(
                                                                  command -> {
                                                                     class_2168 source = (class_2168)command.getSource();
                                                                     if (!source.method_43737()) {
                                                                        MessageFunctions.sendMessage(
                                                                           source, "This command can only be ran as a player.", class_124.field_1061
                                                                        );
                                                                        return 0;
                                                                     } else {
                                                                        class_1657 player = source.method_44023();
                                                                        if (!StarterCheckFunctions.shouldPlayerReceiveStarterKit(player)) {
                                                                           MessageFunctions.sendMessage(
                                                                              source, "You are not eligible for a starter kit.", class_124.field_1061
                                                                           );
                                                                           return 0;
                                                                        } else {
                                                                           String kitName = Util.findCorrectKitNameFromInput(
                                                                              StringArgumentType.getString(command, "kit_name")
                                                                           );
                                                                           if (!Variables.starterGearEntries.containsKey(kitName)) {
                                                                              MessageFunctions.sendMessage(
                                                                                 source,
                                                                                 "The starter kit '" + kitName + "' does not exist.",
                                                                                 class_124.field_1061
                                                                              );
                                                                              return 0;
                                                                           } else {
                                                                              String actualKitName = StarterGearFunctions.giveStarterKit(player, null, kitName);
                                                                              if (actualKitName == null) {
                                                                                 MessageFunctions.sendMessage(
                                                                                    source,
                                                                                    "Something went wrong while choosing your starter kit.",
                                                                                    class_124.field_1061
                                                                                 );
                                                                                 return 0;
                                                                              } else {
                                                                                 MessageFunctions.sendMessage(
                                                                                    player,
                                                                                    "You have been given the '"
                                                                                       + Util.formatKitName(actualKitName)
                                                                                       + "' starter kit.",
                                                                                    class_124.field_1077,
                                                                                    true
                                                                                 );
                                                                                 return 1;
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   class_2170.method_9247("add").executes(command -> processCommand((class_2168)command.getSource(), "", true))
                                                ))
                                             .then(
                                                class_2170.method_9247("add")
                                                   .then(
                                                      class_2170.method_9244("kit_name", StringArgumentType.string())
                                                         .executes(
                                                            command -> processCommand(
                                                               (class_2168)command.getSource(), StringArgumentType.getString(command, "kit_name"), true
                                                            )
                                                         )
                                                   )
                                             ))
                                          .then(class_2170.method_9247("set").executes(command -> processCommand((class_2168)command.getSource(), "", false))))
                                       .then(
                                          class_2170.method_9247("set")
                                             .then(
                                                class_2170.method_9244("kit_name", StringArgumentType.string())
                                                   .executes(
                                                      command -> processCommand(
                                                         (class_2168)command.getSource(), StringArgumentType.getString(command, "kit_name"), false
                                                      )
                                                   )
                                             )
                                       ))
                                    .then(
                                       class_2170.method_9247("deactivate")
                                          .then(
                                             class_2170.method_9244("kit_name", StringArgumentType.string())
                                                .suggests(StarterCommandFunctions.activeKitSuggestionsWithAll)
                                                .executes(
                                                   command -> {
                                                      class_2168 source = (class_2168)command.getSource();
                                                      if (!permissionCheck(source)) {
                                                         return 0;
                                                      } else {
                                                         String kitNameInput = StringArgumentType.getString(command, "kit_name");
                                                         if (kitNameInput.equalsIgnoreCase("_all")) {
                                                            StarterGearFunctions.moveAllKitsToInactive();
                                                            MessageFunctions.sendMessage(
                                                               source, "Moved all kits to the inactive folder.", class_124.field_1077, true
                                                            );
                                                            return 1;
                                                         } else {
                                                            String kitName = Util.findCorrectKitNameFromInput(kitNameInput);
                                                            if (!StarterGearFunctions.moveKitToInactive(kitName)) {
                                                               MessageFunctions.sendMessage(
                                                                  source,
                                                                  "Unable to move the '" + kitName + "' kit to the inactive folder.",
                                                                  class_124.field_1061,
                                                                  true
                                                               );
                                                               return 0;
                                                            } else {
                                                               MessageFunctions.sendMessage(
                                                                  source,
                                                                  "The kit '" + kitName + "' has been moved to the inactive folder.",
                                                                  class_124.field_1077,
                                                                  true
                                                               );
                                                               return 1;
                                                            }
                                                         }
                                                      }
                                                   }
                                                )
                                          )
                                    ))
                                 .then(
                                    class_2170.method_9247("activate")
                                       .then(
                                          class_2170.method_9244("kit_name", StringArgumentType.string())
                                             .suggests(StarterCommandFunctions.inactiveKitSuggestionsWithAll)
                                             .executes(
                                                command -> {
                                                   class_2168 source = (class_2168)command.getSource();
                                                   if (!permissionCheck(source)) {
                                                      return 0;
                                                   } else {
                                                      String kitNameInput = StringArgumentType.getString(command, "kit_name");
                                                      if (kitNameInput.equalsIgnoreCase("_all")) {
                                                         StarterGearFunctions.moveAllKitsToActive();
                                                         MessageFunctions.sendMessage(
                                                            source, "Moved all kits to the active folder.", class_124.field_1077, true
                                                         );
                                                         return 1;
                                                      } else {
                                                         String kitName = Util.findCorrectKitNameFromInput(kitNameInput);
                                                         if (!StarterGearFunctions.moveKitToActive(kitName)) {
                                                            MessageFunctions.sendMessage(
                                                               source,
                                                               "Unable to move the '" + kitName + "' kit to the active folder.",
                                                               class_124.field_1061,
                                                               true
                                                            );
                                                            return 0;
                                                         } else {
                                                            MessageFunctions.sendMessage(
                                                               source,
                                                               "The kit '" + kitName + "' has been moved to the active folder.",
                                                               class_124.field_1077,
                                                               true
                                                            );
                                                            return 1;
                                                         }
                                                      }
                                                   }
                                                }
                                             )
                                       )
                                 ))
                              .then(
                                 class_2170.method_9247("give")
                                    .then(
                                       class_2170.method_9244("target", class_2186.method_9305())
                                          .executes(
                                             command -> {
                                                class_2168 source = (class_2168)command.getSource();
                                                if (!permissionCheck(source)) {
                                                   return 0;
                                                }

                                                class_1937 level = source.method_9225();
                                                if (level.field_9236) {
                                                   return 1;
                                                }

                                                class_1657 targetPlayer = class_2186.method_9315(command, "target");
                                                StarterDataFunctions.resetTrackingForPlayer(targetPlayer);
                                                StarterGearFunctions.initStarterKitHandle(level, targetPlayer, source);
                                                if (ConfigHandler.randomizeMultipleKitsToggle && Variables.starterGearEntries.size() > 1) {
                                                   MessageFunctions.sendMessage(
                                                      source,
                                                      targetPlayer.method_5477().getString() + " has been given the choice for a new starter kit!",
                                                      class_124.field_1077,
                                                      true
                                                   );
                                                }

                                                return 1;
                                             }
                                          )
                                    )
                              ))
                           .then(
                              class_2170.method_9247("give")
                                 .then(
                                    class_2170.method_9244("target", class_2186.method_9305())
                                       .then(
                                          class_2170.method_9244("kit_name", StringArgumentType.string())
                                             .suggests(StarterCommandFunctions.activeKitSuggestions)
                                             .executes(
                                                command -> {
                                                   class_2168 source = (class_2168)command.getSource();
                                                   if (!permissionCheck(source)) {
                                                      return 0;
                                                   }

                                                   class_1937 level = source.method_9225();
                                                   if (level.field_9236) {
                                                      return 1;
                                                   }

                                                   class_1657 targetPlayer = class_2186.method_9315(command, "target");
                                                   String kitName = Util.findCorrectKitNameFromInput(StringArgumentType.getString(command, "kit_name"));
                                                   StarterDataFunctions.resetTrackingForPlayer(targetPlayer);
                                                   StarterGearFunctions.initStarterKitHandle(level, targetPlayer, source, kitName);
                                                   if (ConfigHandler.randomizeMultipleKitsToggle && Variables.starterGearEntries.size() > 1) {
                                                      MessageFunctions.sendMessage(
                                                         source,
                                                         targetPlayer.method_5477().getString() + " has been given the choice for a new starter kit!",
                                                         class_124.field_1077,
                                                         true
                                                      );
                                                   }

                                                   return 1;
                                                }
                                             )
                                       )
                                 )
                           ))
                        .then(
                           class_2170.method_9247("list")
                              .executes(
                                 command -> {
                                    class_2168 source = (class_2168)command.getSource();
                                    if (!permissionCheck(source)) {
                                       return 0;
                                    }

                                    MessageFunctions.sendMessage(
                                       source,
                                       class_2561.method_43470("The current ")
                                          .method_27692(class_124.field_1077)
                                          .method_10852(
                                             class_2561.method_43470("active")
                                                .method_27692(class_124.field_1065)
                                                .method_10852(class_2561.method_43470(" starter kits are:").method_27692(class_124.field_1077))
                                          ),
                                       true
                                    );
                                    List<String> activeKitNames = StarterGearFunctions.getActiveKitNames();
                                    int activeKitcount = activeKitNames.size();
                                    if (activeKitcount > 0) {
                                       for (String kitName : activeKitNames) {
                                          MessageFunctions.sendMessage(source, " " + kitName, class_124.field_1080);
                                       }
                                    } else {
                                       MessageFunctions.sendMessage(source, " N/A", class_124.field_1061);
                                    }

                                    if (ConfigHandler.randomizeMultipleKitsToggle) {
                                       MessageFunctions.sendMessage(source, "One kit is given randomly on join.", class_124.field_1077, true);
                                    } else {
                                       MessageFunctions.sendMessage(source, "Players can choose one of the kits on join.", class_124.field_1077, true);
                                       if (activeKitcount < 2) {
                                          if (activeKitcount == 0) {
                                             MessageFunctions.sendMessage(source, " > If 2 more kits are added.", class_124.field_1063);
                                          } else {
                                             MessageFunctions.sendMessage(
                                                source,
                                                " > If 1 more kit is added. For now all players will receive the '"
                                                   + activeKitNames.getFirst()
                                                   + "' kit without a choice screen.",
                                                class_124.field_1063
                                             );
                                          }
                                       }
                                    }

                                    return 1;
                                 }
                              )
                        ))
                     .then(
                        class_2170.method_9247("list")
                           .then(
                              class_2170.method_9247("inactive")
                                 .executes(
                                    command -> {
                                       class_2168 source = (class_2168)command.getSource();
                                       if (!permissionCheck(source)) {
                                          return 0;
                                       }

                                       MessageFunctions.sendMessage(
                                          source,
                                          class_2561.method_43470("The current ")
                                             .method_27692(class_124.field_1077)
                                             .method_10852(
                                                class_2561.method_43470("inactive")
                                                   .method_27692(class_124.field_1061)
                                                   .method_10852(class_2561.method_43470(" starter kits are:").method_27692(class_124.field_1077))
                                             ),
                                          true
                                       );
                                       List<String> inactiveKitNames = StarterGearFunctions.getInactiveKitNames();
                                       if (inactiveKitNames.size() > 0) {
                                          for (String kitName : inactiveKitNames) {
                                             MessageFunctions.sendMessage(source, " " + kitName, class_124.field_1080);
                                          }
                                       } else {
                                          MessageFunctions.sendMessage(source, " N/A", class_124.field_1061);
                                       }

                                       return 1;
                                    }
                                 )
                           )
                     ))
                  .then(class_2170.method_9247("reload").executes(command -> {
                     class_2168 source = (class_2168)command.getSource();
                     if (!permissionCheck(source)) {
                        return 0;
                     }

                     StarterGearFunctions.processKitFiles();
                     MessageFunctions.sendMessage((class_2168)command.getSource(), "All active kits have been reloaded.", class_124.field_1077, true);
                     sendKitCount(source, false);
                     return 1;
                  })))
               .then(
                  class_2170.method_9247("reset")
                     .executes(
                        command -> {
                           class_2168 source = (class_2168)command.getSource();
                           if (!permissionCheck(source)) {
                              return 0;
                           }

                           class_1937 level = source.method_9225();
                           if (level.field_9236) {
                              return 0;
                           }

                           StarterDataFunctions.resetTrackingMap(level.method_8503());
                           MessageFunctions.sendMessage(
                              source, "Starter Kit tracking data has been reset. All players will now again receive a kit on join.", class_124.field_1077, true
                           );
                           return 1;
                        }
                     )
               )
         );
      }
   }

   private static boolean permissionCheck(class_2168 source) {
      if (!source.method_9259(2)) {
         MessageFunctions.sendMessage(source, "You do not have permission to use that command.", class_124.field_1061);
         return false;
      } else {
         return true;
      }
   }

   private static int showCommandHelp(class_2168 source) {
      if (source.method_9259(2)) {
         MessageFunctions.sendMessage(source, class_2561.method_43470("Starter Kit Admin Usage:").method_27692(class_124.field_1065), true);
         MessageFunctions.sendMessage(source, " /sk add (kit_name)", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Adds your current inventory to the active kits.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk set (kit_name)", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Sets your current inventory as the only active kit.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk give <player> (kit_name)", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Give a player a random (or specific) starter kit.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk activate <kit_name>/_all", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Moves kit_name or all kits from inactive to active.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk deactivate <kit_name>/_all", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Moves kit_name or all kits from active to inactive.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk list (inactive)", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Lists all active (or inactive) kits.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk reset", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Resets all tracking. Players receive another kit on join.", class_124.field_1080);
         MessageFunctions.sendMessage(source, " /sk reload", class_124.field_1077);
         MessageFunctions.sendMessage(source, "     Reloads starter kit changes from the config folder.", class_124.field_1080);
      }

      MessageFunctions.sendMessage(source, class_2561.method_43470("Starter Kit User Usage:").method_27692(class_124.field_1065), true);
      MessageFunctions.sendMessage(source, " /sk choose <kit_name>", class_124.field_1077);
      MessageFunctions.sendMessage(
         source,
         "     Allows players to choose a starter kit via commands. This is only needed when Starter Kit is not installed on the client and the server enabled kit choices.",
         class_124.field_1080
      );
      MessageFunctions.sendMessage(source, " /sk info <kit_name>", class_124.field_1077);
      MessageFunctions.sendMessage(source, "     Shows the description and items of kit_name.", class_124.field_1080);
      return 1;
   }

   private static int processCommand(class_2168 source, String kitName, boolean adding) {
      if (!permissionCheck(source)) {
         return 0;
      }

      if (!source.method_43737()) {
         MessageFunctions.sendMessage(source, "This command can only be ran as a player.", class_124.field_1061);
         return 0;
      }

      class_1657 player = source.method_44023();
      kitName = Util.findCorrectKitNameFromInput(kitName);
      String wording = "adding";
      String worded = "added";
      String cmd = "add";
      if (!adding) {
         wording = "setting";
         worded = "set";
         cmd = "set";
      }

      String actualKitName = StarterGearFunctions.createStarterKitFile(player, kitName, adding);
      if (actualKitName.equals("")) {
         MessageFunctions.sendMessage(source, "Something went wrong while " + wording + " a new starter kit.", class_124.field_1061);
         return 0;
      }

      MessageFunctions.sendMessage(source, "The starter kit '" + Util.formatKitName(actualKitName) + "' was " + worded + "!", class_124.field_1077, true);
      if (!kitName.equals(actualKitName)) {
         MessageFunctions.sendMessage(source, " If you'd like to specify the kit name, use:", class_124.field_1080, true);
         MessageFunctions.sendMessage(source, "     /sk " + cmd + " <kit_name>", class_124.field_1080);
         MessageFunctions.sendMessage(source, " ", class_124.field_1080);
      }

      sendKitCount(source, true);
      return 1;
   }

   private static void sendKitCount(class_2168 source, boolean now) {
      int kitCount = Variables.starterGearEntries.size();
      String isare = "are";
      String s = "s";
      if (kitCount == 1) {
         isare = "is";
         s = "";
      }

      String nowword = "now ";
      if (!now) {
         nowword = "";
      }

      MessageFunctions.sendMessage(source, " There " + isare + " " + nowword + kitCount + " starter kit" + s + " available.", class_124.field_1063);
   }
}
