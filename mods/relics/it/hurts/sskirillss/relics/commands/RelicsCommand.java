package it.hurts.sskirillss.relics.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import it.hurts.sskirillss.relics.commands.arguments.RelicAbilityArgument;
import it.hurts.sskirillss.relics.commands.arguments.RelicAbilityStatArgument;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import java.util.Map.Entry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.server.command.EnumArgument;

public class RelicsCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                 "relics"
                              )
                              .requires(sender -> sender.hasPermission(2)))
                           .then(Commands.literal("maximize").executes(context -> {
                              ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                              ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                              if (!(stack.getItem() instanceof IRelicItem relic)) {
                                 ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                                 return 0;
                              } else {
                                 RelicData var11 = relic.getRelicData();
                                 relic.setRelicLevel(stack, var11.getLeveling().getMaxLevel());

                                 for (Entry<String, AbilityData> abilityEntry : var11.getAbilities().getAbilities().entrySet()) {
                                    String abilityId = abilityEntry.getKey();
                                    AbilityData abilityData = abilityEntry.getValue();
                                    relic.setAbilityLevel(stack, abilityId, relic.getAbilityMaxLevel(stack, abilityId));
                                    relic.setLockUnlocks(stack, abilityId, relic.getMaxLockUnlocks());
                                    relic.setAbilityResearched(stack, abilityId, true);

                                    for (Entry<String, StatData> statEntry : abilityData.getStats().entrySet()) {
                                       relic.setStatInitialValue(
                                          stack, abilityId, statEntry.getKey(), (Double)statEntry.getValue().getInitialValue().getValue()
                                       );
                                    }
                                 }

                                 return 1;
                              }
                           })))
                        .then(Commands.literal("minimize").executes(context -> {
                           ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                           ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                           if (!(stack.getItem() instanceof IRelicItem relic)) {
                              ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                              return 0;
                           } else {
                              RelicData var10 = relic.getRelicData();
                              relic.setRelicLevel(stack, var10.getLeveling().getMaxLevel());
                              relic.setRelicExperience(stack, 0);

                              for (Entry<String, AbilityData> abilityEntry : var10.getAbilities().getAbilities().entrySet()) {
                                 String abilityId = abilityEntry.getKey();
                                 relic.setAbilityResearched(stack, abilityId, false);
                                 relic.setAbilityLevel(stack, abilityId, 0);
                                 if (!relic.isEnoughLevel(stack, abilityId)) {
                                    relic.setLockUnlocks(stack, abilityId, 0);
                                 }

                                 for (Entry<String, StatData> statEntry : abilityEntry.getValue().getStats().entrySet()) {
                                    relic.setStatInitialValue(stack, abilityId, statEntry.getKey(), (Double)statEntry.getValue().getInitialValue().getKey());
                                 }
                              }

                              return 1;
                           }
                        })))
                     .then(
                        Commands.literal("level")
                           .then(
                              Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                                 .then(Commands.argument("level", IntegerArgumentType.integer()).executes(context -> {
                                    ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                    ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                    if (stack.getItem() instanceof IRelicItem relic) {
                                       int var5 = IntegerArgumentType.getInteger(context, "level");
                                       switch ((RelicsCommand.CommandAction)context.getArgument("action", RelicsCommand.CommandAction.class)) {
                                          case SET:
                                             relic.setRelicLevel(stack, var5);
                                             break;
                                          case ADD:
                                             relic.addRelicLevel(stack, var5);
                                             break;
                                          case TAKE:
                                             relic.addRelicLevel(stack, -var5);
                                       }

                                       return 1;
                                    } else {
                                       ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                                       return 0;
                                    }
                                 }))
                           )
                     ))
                  .then(
                     Commands.literal("experience")
                        .then(
                           Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                              .then(Commands.argument("experience", IntegerArgumentType.integer()).executes(context -> {
                                 ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                 ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                 if (stack.getItem() instanceof IRelicItem relic) {
                                    int var5 = IntegerArgumentType.getInteger(context, "experience");
                                    switch ((RelicsCommand.CommandAction)context.getArgument("action", RelicsCommand.CommandAction.class)) {
                                       case SET:
                                          relic.setRelicExperience(stack, var5);
                                          break;
                                       case ADD:
                                          relic.addRelicExperience(stack, var5);
                                          break;
                                       case TAKE:
                                          relic.addRelicExperience(stack, -var5);
                                    }

                                    return 1;
                                 } else {
                                    ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                                    return 0;
                                 }
                              }))
                        )
                  ))
               .then(
                  Commands.literal("points")
                     .then(
                        Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                           .then(Commands.argument("points", IntegerArgumentType.integer()).executes(context -> {
                              ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                              ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                              if (stack.getItem() instanceof IRelicItem relic) {
                                 int var5 = IntegerArgumentType.getInteger(context, "points");
                                 switch ((RelicsCommand.CommandAction)context.getArgument("action", RelicsCommand.CommandAction.class)) {
                                    case SET:
                                       relic.setRelicLevelingPoints(stack, var5);
                                       break;
                                    case ADD:
                                       relic.addRelicLevelingPoints(stack, var5);
                                       break;
                                    case TAKE:
                                       relic.addRelicLevelingPoints(stack, -var5);
                                 }

                                 return 1;
                              } else {
                                 ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                                 return 0;
                              }
                           }))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ability")
                           .then(
                              Commands.literal("points")
                                 .then(
                                    Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                                       .then(
                                          Commands.argument("ability", RelicAbilityArgument.ability())
                                             .then(Commands.argument("points", IntegerArgumentType.integer()).executes(context -> {
                                                ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                                if (!(stack.getItem() instanceof IRelicItem relic)) {
                                                   ((CommandSourceStack)context.getSource())
                                                      .sendFailure(Component.translatable("command.relics.base.not_relic"));
                                                   return 0;
                                                } else {
                                                   RelicsCommand.CommandAction var9 = (RelicsCommand.CommandAction)context.getArgument(
                                                      "action", RelicsCommand.CommandAction.class
                                                   );
                                                   String ability = RelicAbilityArgument.getAbility(context, "ability");
                                                   int points = IntegerArgumentType.getInteger(context, "points");
                                                   if (ability.equals("all")) {
                                                      for (String entry : relic.getRelicData().getAbilities().getAbilities().keySet()) {
                                                         switch (var9) {
                                                            case SET:
                                                               relic.setAbilityLevel(stack, entry, points);
                                                               break;
                                                            case ADD:
                                                               relic.addAbilityLevel(stack, entry, points);
                                                               break;
                                                            case TAKE:
                                                               relic.addAbilityLevel(stack, entry, -points);
                                                         }
                                                      }
                                                   } else {
                                                      switch (var9) {
                                                         case SET:
                                                            relic.setAbilityLevel(stack, ability, points);
                                                            break;
                                                         case ADD:
                                                            relic.addAbilityLevel(stack, ability, points);
                                                            break;
                                                         case TAKE:
                                                            relic.addAbilityLevel(stack, ability, -points);
                                                      }
                                                   }

                                                   return 1;
                                                }
                                             }))
                                       )
                                 )
                           ))
                        .then(
                           Commands.literal("value")
                              .then(
                                 Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                                    .then(
                                       Commands.argument("ability", RelicAbilityArgument.ability())
                                          .then(
                                             Commands.argument("stat", RelicAbilityStatArgument.abilityStat())
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg()).executes(context -> {
                                                   ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                                   ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                                   if (!(stack.getItem() instanceof IRelicItem relic)) {
                                                      ((CommandSourceStack)context.getSource())
                                                         .sendFailure(Component.translatable("command.relics.base.not_relic"));
                                                      return 0;
                                                   } else {
                                                      RelicsCommand.CommandAction var13 = (RelicsCommand.CommandAction)context.getArgument(
                                                         "action", RelicsCommand.CommandAction.class
                                                      );
                                                      String ability = RelicAbilityArgument.getAbility(context, "ability");
                                                      String stat = RelicAbilityStatArgument.getAbilityStat(context, "stat");
                                                      double value = DoubleArgumentType.getDouble(context, "value");
                                                      if (ability.equals("all")) {
                                                         for (String abilityEntry : relic.getRelicData().getAbilities().getAbilities().keySet()) {
                                                            if (stat.equals("all")) {
                                                               for (String statEntry : relic.getAbilityData(abilityEntry).getStats().keySet()) {
                                                                  switch (var13) {
                                                                     case SET:
                                                                        relic.setStatInitialValue(stack, abilityEntry, statEntry, value);
                                                                        break;
                                                                     case ADD:
                                                                        relic.addStatInitialValue(stack, abilityEntry, statEntry, value);
                                                                        break;
                                                                     case TAKE:
                                                                        relic.addStatInitialValue(stack, abilityEntry, statEntry, -value);
                                                                  }
                                                               }
                                                            } else {
                                                               switch (var13) {
                                                                  case SET:
                                                                     relic.setStatInitialValue(stack, abilityEntry, stat, value);
                                                                     break;
                                                                  case ADD:
                                                                     relic.addStatInitialValue(stack, abilityEntry, stat, value);
                                                                     break;
                                                                  case TAKE:
                                                                     relic.addStatInitialValue(stack, abilityEntry, stat, -value);
                                                               }
                                                            }
                                                         }
                                                      } else if (stat.equals("all")) {
                                                         for (String statEntry : relic.getAbilityData(ability).getStats().keySet()) {
                                                            switch (var13) {
                                                               case SET:
                                                                  relic.setStatInitialValue(stack, ability, statEntry, value);
                                                                  break;
                                                               case ADD:
                                                                  relic.addStatInitialValue(stack, ability, statEntry, value);
                                                                  break;
                                                               case TAKE:
                                                                  relic.addStatInitialValue(stack, ability, statEntry, -value);
                                                            }
                                                         }
                                                      } else {
                                                         switch (var13) {
                                                            case SET:
                                                               relic.setStatInitialValue(stack, ability, stat, value);
                                                               break;
                                                            case ADD:
                                                               relic.addStatInitialValue(stack, ability, stat, value);
                                                               break;
                                                            case TAKE:
                                                               relic.addStatInitialValue(stack, ability, stat, -value);
                                                         }
                                                      }

                                                      return 1;
                                                   }
                                                }))
                                          )
                                    )
                              )
                        ))
                     .then(
                        Commands.literal("quality")
                           .then(
                              Commands.argument("action", EnumArgument.enumArgument(RelicsCommand.CommandAction.class))
                                 .then(
                                    Commands.argument("ability", RelicAbilityArgument.ability())
                                       .then(
                                          Commands.argument("stat", RelicAbilityStatArgument.abilityStat())
                                             .then(Commands.argument("quality", IntegerArgumentType.integer()).executes(context -> {
                                                ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                                if (!(stack.getItem() instanceof IRelicItem relic)) {
                                                   ((CommandSourceStack)context.getSource())
                                                      .sendFailure(Component.translatable("command.relics.base.not_relic"));
                                                   return 0;
                                                } else {
                                                   RelicsCommand.CommandAction var14 = (RelicsCommand.CommandAction)context.getArgument(
                                                      "action", RelicsCommand.CommandAction.class
                                                   );
                                                   String ability = RelicAbilityArgument.getAbility(context, "ability");
                                                   String stat = RelicAbilityStatArgument.getAbilityStat(context, "stat");
                                                   int quality = IntegerArgumentType.getInteger(context, "quality");
                                                   if (ability.equals("all")) {
                                                      for (String abilityEntry : relic.getRelicData().getAbilities().getAbilities().keySet()) {
                                                         if (stat.equals("all")) {
                                                            for (String statEntry : relic.getAbilityData(abilityEntry).getStats().keySet()) {
                                                               double value = relic.getStatValueByQuality(abilityEntry, statEntry, quality);
                                                               switch (var14) {
                                                                  case SET:
                                                                     relic.setStatInitialValue(stack, abilityEntry, statEntry, value);
                                                                     break;
                                                                  case ADD:
                                                                     relic.addStatInitialValue(stack, abilityEntry, statEntry, value);
                                                                     break;
                                                                  case TAKE:
                                                                     relic.addStatInitialValue(stack, abilityEntry, statEntry, -value);
                                                               }
                                                            }
                                                         } else {
                                                            double value = relic.getStatValueByQuality(abilityEntry, stat, quality);
                                                            switch (var14) {
                                                               case SET:
                                                                  relic.setStatInitialValue(stack, abilityEntry, stat, value);
                                                                  break;
                                                               case ADD:
                                                                  relic.addStatInitialValue(stack, abilityEntry, stat, value);
                                                                  break;
                                                               case TAKE:
                                                                  relic.addStatInitialValue(stack, abilityEntry, stat, -value);
                                                            }
                                                         }
                                                      }
                                                   } else if (stat.equals("all")) {
                                                      for (String statEntry : relic.getAbilityData(ability).getStats().keySet()) {
                                                         double value = relic.getStatValueByQuality(ability, statEntry, quality);
                                                         switch (var14) {
                                                            case SET:
                                                               relic.setStatInitialValue(stack, ability, statEntry, value);
                                                               break;
                                                            case ADD:
                                                               relic.addStatInitialValue(stack, ability, statEntry, value);
                                                               break;
                                                            case TAKE:
                                                               relic.addStatInitialValue(stack, ability, statEntry, -value);
                                                         }
                                                      }
                                                   } else {
                                                      double value = relic.getStatValueByQuality(ability, stat, quality);
                                                      switch (var14) {
                                                         case SET:
                                                            relic.setStatInitialValue(stack, ability, stat, value);
                                                            break;
                                                         case ADD:
                                                            relic.addStatInitialValue(stack, ability, stat, value);
                                                            break;
                                                         case TAKE:
                                                            relic.addStatInitialValue(stack, ability, stat, -value);
                                                      }
                                                   }

                                                   return 1;
                                                }
                                             }))
                                       )
                                 )
                           )
                     ))
                  .then(
                     Commands.literal("randomize")
                        .then(
                           Commands.argument("ability", RelicAbilityArgument.ability())
                              .then(Commands.argument("stat", RelicAbilityStatArgument.abilityStat()).executes(context -> {
                                 ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                                 ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                 if (!(stack.getItem() instanceof IRelicItem relic)) {
                                    ((CommandSourceStack)context.getSource()).sendFailure(Component.translatable("command.relics.base.not_relic"));
                                    return 0;
                                 } else {
                                    String var10 = RelicAbilityArgument.getAbility(context, "ability");
                                    String stat = RelicAbilityStatArgument.getAbilityStat(context, "stat");
                                    if (var10.equals("all")) {
                                       for (String abilityEntry : relic.getRelicData().getAbilities().getAbilities().keySet()) {
                                          if (stat.equals("all")) {
                                             for (String statEntry : relic.getAbilityData(abilityEntry).getStats().keySet()) {
                                                relic.randomizeStat(stack, abilityEntry, statEntry);
                                             }
                                          } else {
                                             relic.randomizeStat(stack, abilityEntry, stat);
                                          }
                                       }
                                    } else if (stat.equals("all")) {
                                       for (String statEntry : relic.getAbilityData(var10).getStats().keySet()) {
                                          relic.randomizeStat(stack, var10, statEntry);
                                       }
                                    } else {
                                       relic.randomizeStat(stack, var10, stat);
                                    }

                                    return 1;
                                 }
                              }))
                        )
                  )
            )
      );
   }

   public enum CommandAction {
      SET,
      ADD,
      TAKE;
   }
}
