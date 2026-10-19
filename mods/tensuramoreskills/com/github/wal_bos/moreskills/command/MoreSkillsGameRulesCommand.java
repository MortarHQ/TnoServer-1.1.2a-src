package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.registry.MoreSkillsGameRules;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class MoreSkillsGameRulesCommand {
   private MoreSkillsGameRulesCommand() {
   }

   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tms").requires(source -> source.hasPermission(2)))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("gamerule")
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("uniqueOwnerData")
                                 .executes(
                                    context -> queryRule((CommandSourceStack)context.getSource(), "tmsUniqueOwnerData", MoreSkillsGameRules.UNIQUE_OWNER_DATA)
                                 ))
                              .then(
                                 Commands.argument("value", BoolArgumentType.bool())
                                    .executes(
                                       context -> setRule(
                                          (CommandSourceStack)context.getSource(),
                                          "tmsUniqueOwnerData",
                                          MoreSkillsGameRules.UNIQUE_OWNER_DATA,
                                          BoolArgumentType.getBool(context, "value")
                                       )
                                    )
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("feldwayRewardOneTime")
                              .executes(
                                 context -> queryRule(
                                    (CommandSourceStack)context.getSource(), "tmsFeldwayRewardOneTime", MoreSkillsGameRules.FELDWAY_REWARD_ONE_TIME
                                 )
                              ))
                           .then(
                              Commands.argument("value", BoolArgumentType.bool())
                                 .executes(
                                    context -> setRule(
                                       (CommandSourceStack)context.getSource(),
                                       "tmsFeldwayRewardOneTime",
                                       MoreSkillsGameRules.FELDWAY_REWARD_ONE_TIME,
                                       BoolArgumentType.getBool(context, "value")
                                    )
                                 )
                           )
                     )
               )
         );
   }

   private static int queryRule(CommandSourceStack source, String name, Key<BooleanValue> rule) {
      boolean value = source.getLevel().getGameRules().getBoolean(rule);
      source.sendSuccess(() -> Component.literal(name + " = " + value), false);
      return value ? 1 : 0;
   }

   private static int setRule(CommandSourceStack source, String name, Key<BooleanValue> rule, boolean value) {
      ((BooleanValue)source.getLevel().getGameRules().getRule(rule)).set(value, source.getServer());
      source.sendSuccess(() -> Component.literal(name + " set to " + value), true);
      return value ? 1 : 0;
   }
}
