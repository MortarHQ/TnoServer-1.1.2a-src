package com.github.wal_bos.moreutils.command;

import com.github.wal_bos.moreutils.data.MoreUtilsData;
import com.github.wal_bos.moreutils.data.SkillCapType;
import com.github.wal_bos.moreutils.handler.MoreUtilsSkillCap;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class MoreUtilsCapCommands {
   private MoreUtilsCapCommands() {
   }

   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tmu")
                        .requires(source -> source.hasPermission(2)))
                     .then(
                        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("cap")
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("global")
                                                .then(typeSetTree("unique", SkillCapType.UNIQUE)))
                                             .then(typeSetTree("ultimate", SkillCapType.ULTIMATE)))
                                          .then(Commands.literal("reset").executes(context -> resetGlobal((CommandSourceStack)context.getSource())))
                                    ))
                                 .then(Commands.literal("list").executes(context -> list((CommandSourceStack)context.getSource()))))
                              .then(
                                 Commands.literal("check")
                                    .then(
                                       Commands.argument("player", GameProfileArgument.gameProfile())
                                          .executes(
                                             context -> check((CommandSourceStack)context.getSource(), GameProfileArgument.getGameProfiles(context, "player"))
                                          )
                                    )
                              ))
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("player", GameProfileArgument.gameProfile())
                                       .then(playerTypeTree("unique", SkillCapType.UNIQUE)))
                                    .then(playerTypeTree("ultimate", SkillCapType.ULTIMATE)))
                                 .then(
                                    Commands.literal("reset")
                                       .executes(
                                          context -> resetPlayers(
                                             (CommandSourceStack)context.getSource(), GameProfileArgument.getGameProfiles(context, "player")
                                          )
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("resetcounter").then(resetIntervalTree("unique", SkillCapType.UNIQUE)))
                           .then(resetIntervalTree("ultimate", SkillCapType.ULTIMATE)))
                        .then(Commands.literal("list").executes(context -> listIntervals((CommandSourceStack)context.getSource())))
                  ))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("resetcap")
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("global")
                                       .then(
                                          Commands.argument("amount", IntegerArgumentType.integer(0))
                                             .executes(
                                                context -> setGlobalResetCap(
                                                   (CommandSourceStack)context.getSource(), IntegerArgumentType.getInteger(context, "amount")
                                                )
                                             )
                                       ))
                                    .then(Commands.literal("reset").executes(context -> clearGlobalResetCap((CommandSourceStack)context.getSource())))
                              ))
                           .then(Commands.literal("list").executes(context -> listResetCaps((CommandSourceStack)context.getSource()))))
                        .then(
                           Commands.literal("check")
                              .then(
                                 Commands.argument("player", GameProfileArgument.gameProfile())
                                    .executes(
                                       context -> checkResetCaps(
                                          (CommandSourceStack)context.getSource(), GameProfileArgument.getGameProfiles(context, "player")
                                       )
                                    )
                              )
                        ))
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("player", GameProfileArgument.gameProfile())
                              .then(
                                 Commands.argument("amount", IntegerArgumentType.integer(0))
                                    .executes(
                                       context -> setPlayerResetCaps(
                                          (CommandSourceStack)context.getSource(),
                                          GameProfileArgument.getGameProfiles(context, "player"),
                                          IntegerArgumentType.getInteger(context, "amount")
                                       )
                                    )
                              ))
                           .then(
                              Commands.literal("reset")
                                 .executes(
                                    context -> clearPlayerResetCaps(
                                       (CommandSourceStack)context.getSource(), GameProfileArgument.getGameProfiles(context, "player")
                                    )
                                 )
                           )
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> typeSetTree(String literal, SkillCapType type) {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal(literal)
            .then(
               Commands.argument("amount", IntegerArgumentType.integer(0))
                  .executes(context -> setGlobal((CommandSourceStack)context.getSource(), type, IntegerArgumentType.getInteger(context, "amount")))
            ))
         .then(Commands.literal("reset").executes(context -> clearGlobal((CommandSourceStack)context.getSource(), type)));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> playerTypeTree(String literal, SkillCapType type) {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal(literal)
         .then(
            Commands.argument("amount", IntegerArgumentType.integer(0))
               .executes(
                  context -> setPlayers(
                     (CommandSourceStack)context.getSource(),
                     GameProfileArgument.getGameProfiles(context, "player"),
                     type,
                     IntegerArgumentType.getInteger(context, "amount")
                  )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> resetIntervalTree(String literal, SkillCapType type) {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal(literal)
         .then(
            Commands.literal("interval")
               .then(
                  Commands.argument("resets", IntegerArgumentType.integer(1))
                     .executes(context -> setInterval((CommandSourceStack)context.getSource(), type, IntegerArgumentType.getInteger(context, "resets")))
               )
         );
   }

   private static int setGlobal(CommandSourceStack source, SkillCapType type, int cap) {
      MoreUtilsData.setGlobalCap(source.getServer(), type, cap);
      source.sendSuccess(() -> Component.literal("Global " + type.displayName() + " skill cap set to " + cap).withStyle(ChatFormatting.GREEN), true);
      return cap;
   }

   private static int clearGlobal(CommandSourceStack source, SkillCapType type) {
      MoreUtilsData.setGlobalCap(source.getServer(), type, -1);
      source.sendSuccess(() -> Component.literal("Global " + type.displayName() + " skill cap disabled").withStyle(ChatFormatting.YELLOW), true);
      return 1;
   }

   private static int resetGlobal(CommandSourceStack source) {
      MoreUtilsData.setGlobalCap(source.getServer(), SkillCapType.UNIQUE, -1);
      MoreUtilsData.setGlobalCap(source.getServer(), SkillCapType.ULTIMATE, -1);
      source.sendSuccess(() -> Component.literal("All global skill caps disabled").withStyle(ChatFormatting.YELLOW), true);
      return 1;
   }

   private static int setPlayers(CommandSourceStack source, Collection<GameProfile> profiles, SkillCapType type, int cap) {
      for (GameProfile profile : profiles) {
         MoreUtilsData.setPlayerCap(source.getServer(), profile.getId(), profile.getName(), type, cap);
      }

      source.sendSuccess(
         () -> Component.literal("Set " + type.displayName() + " base cap to " + cap + " for " + profiles.size() + " player(s)")
            .withStyle(ChatFormatting.GREEN),
         true
      );
      return profiles.size();
   }

   private static int resetPlayers(CommandSourceStack source, Collection<GameProfile> profiles) {
      for (GameProfile profile : profiles) {
         MoreUtilsData.resetPlayerCaps(source.getServer(), profile.getId());
      }

      source.sendSuccess(() -> Component.literal("Reset personal base skill caps for " + profiles.size() + " player(s)").withStyle(ChatFormatting.YELLOW), true);
      return profiles.size();
   }

   private static int setInterval(CommandSourceStack source, SkillCapType type, int interval) {
      MoreUtilsData.setResetInterval(source.getServer(), type, interval);
      source.sendSuccess(
         () -> Component.literal(type.displayName() + " earns +1 personal cap slot every " + interval + " reset(s)").withStyle(ChatFormatting.GREEN), true
      );
      return interval;
   }

   private static int listIntervals(CommandSourceStack source) {
      source.sendSuccess(() -> Component.literal("Reset counter cap intervals").withStyle(ChatFormatting.AQUA), false);
      source.sendSuccess(() -> Component.literal("Unique: +1 personal slot every " + MoreUtilsData.getResetInterval(SkillCapType.UNIQUE) + " reset(s)"), false);
      source.sendSuccess(
         () -> Component.literal("Ultimate: +1 personal slot every " + MoreUtilsData.getResetInterval(SkillCapType.ULTIMATE) + " reset(s)"), false
      );
      return 1;
   }

   private static int setGlobalResetCap(CommandSourceStack source, int cap) {
      MoreUtilsData.setGlobalResetCap(source.getServer(), cap);
      source.sendSuccess(() -> Component.literal("Global maximum earned reset slots set to " + cap).withStyle(ChatFormatting.GREEN), true);
      return cap;
   }

   private static int clearGlobalResetCap(CommandSourceStack source) {
      MoreUtilsData.setGlobalResetCap(source.getServer(), -1);
      source.sendSuccess(() -> Component.literal("Global maximum earned reset slots disabled").withStyle(ChatFormatting.YELLOW), true);
      return 1;
   }

   private static int setPlayerResetCaps(CommandSourceStack source, Collection<GameProfile> profiles, int cap) {
      for (GameProfile profile : profiles) {
         MoreUtilsData.setPlayerResetCap(source.getServer(), profile.getId(), profile.getName(), cap);
      }

      source.sendSuccess(
         () -> Component.literal("Set personal maximum earned reset slots to " + cap + " for " + profiles.size() + " player(s)")
            .withStyle(ChatFormatting.GREEN),
         true
      );
      return profiles.size();
   }

   private static int clearPlayerResetCaps(CommandSourceStack source, Collection<GameProfile> profiles) {
      for (GameProfile profile : profiles) {
         MoreUtilsData.clearPlayerResetCap(source.getServer(), profile.getId());
      }

      source.sendSuccess(
         () -> Component.literal("Reset personal reset-cap overrides for " + profiles.size() + " player(s)").withStyle(ChatFormatting.YELLOW), true
      );
      return profiles.size();
   }

   private static int listResetCaps(CommandSourceStack source) {
      source.sendSuccess(() -> Component.literal("Reset-earned slot caps").withStyle(ChatFormatting.AQUA), false);
      source.sendSuccess(() -> Component.literal("Global reset cap: " + formatCap(MoreUtilsData.getGlobalResetCap())), false);
      MoreUtilsData.playerCaps()
         .entrySet()
         .stream()
         .filter(entry -> entry.getValue().resetCap() >= 0)
         .sorted(Comparator.comparing(entry -> entry.getValue().name(), String.CASE_INSENSITIVE_ORDER))
         .forEach(
            entry -> source.sendSuccess(
               () -> Component.literal(
                  ((MoreUtilsData.PlayerCaps)entry.getValue()).name() + " [" + entry.getKey() + "]: " + ((MoreUtilsData.PlayerCaps)entry.getValue()).resetCap()
               ),
               false
            )
         );
      return 1;
   }

   private static int checkResetCaps(CommandSourceStack source, Collection<GameProfile> profiles) {
      for (GameProfile profile : profiles) {
         MoreUtilsData.PlayerCaps data = MoreUtilsData.playerCaps().get(profile.getId());
         int uniqueBonus = data == null ? 0 : data.uniqueResetBonus();
         int ultimateBonus = data == null ? 0 : data.ultimateResetBonus();
         int personal = data == null ? -1 : data.resetCap();
         int effective = MoreUtilsData.getEffectiveResetCap(profile.getId());
         source.sendSuccess(
            () -> Component.literal(
               profile.getName()
                  + ": Reset cap="
                  + formatCap(effective)
                  + ", Personal override="
                  + formatInherited(personal)
                  + ", Earned Unique=+"
                  + uniqueBonus
                  + ", Earned Ultimate=+"
                  + ultimateBonus
            ),
            false
         );
      }

      return profiles.size();
   }

   private static int list(CommandSourceStack source) {
      source.sendSuccess(() -> Component.literal("TensuraMoreUtils skill caps").withStyle(ChatFormatting.AQUA), false);
      source.sendSuccess(() -> Component.literal("Global Unique base: " + formatCap(MoreUtilsData.getGlobalCap(SkillCapType.UNIQUE))), false);
      source.sendSuccess(() -> Component.literal("Global Ultimate base: " + formatCap(MoreUtilsData.getGlobalCap(SkillCapType.ULTIMATE))), false);
      source.sendSuccess(() -> Component.literal("Global reset-earned maximum: " + formatCap(MoreUtilsData.getGlobalResetCap())), false);
      source.sendSuccess(() -> Component.literal("Unique reset interval: " + MoreUtilsData.getResetInterval(SkillCapType.UNIQUE)), false);
      source.sendSuccess(() -> Component.literal("Ultimate reset interval: " + MoreUtilsData.getResetInterval(SkillCapType.ULTIMATE)), false);
      Map<UUID, MoreUtilsData.PlayerCaps> caps = MoreUtilsData.playerCaps();
      if (caps.isEmpty()) {
         source.sendSuccess(() -> Component.literal("Player data: none").withStyle(ChatFormatting.GRAY), false);
         return 1;
      } else {
         source.sendSuccess(() -> Component.literal("Player data:").withStyle(ChatFormatting.GOLD), false);
         caps.entrySet()
            .stream()
            .sorted(Comparator.comparing(entry -> entry.getValue().name(), String.CASE_INSENSITIVE_ORDER))
            .forEach(
               entry -> source.sendSuccess(
                  () -> Component.literal(
                     ((MoreUtilsData.PlayerCaps)entry.getValue()).name()
                        + " ["
                        + entry.getKey()
                        + "] UniqueBase="
                        + formatInherited(((MoreUtilsData.PlayerCaps)entry.getValue()).uniqueCap())
                        + ", UltimateBase="
                        + formatInherited(((MoreUtilsData.PlayerCaps)entry.getValue()).ultimateCap())
                        + ", ResetCap="
                        + formatInherited(((MoreUtilsData.PlayerCaps)entry.getValue()).resetCap())
                        + ", EarnedUnique=+"
                        + ((MoreUtilsData.PlayerCaps)entry.getValue()).uniqueResetBonus()
                        + ", EarnedUltimate=+"
                        + ((MoreUtilsData.PlayerCaps)entry.getValue()).ultimateResetBonus()
                        + ", LastReset="
                        + ((MoreUtilsData.PlayerCaps)entry.getValue()).lastResetCounter()
                  ),
                  false
               )
            );
         return caps.size();
      }
   }

   private static int check(CommandSourceStack source, Collection<GameProfile> profiles) {
      for (GameProfile profile : profiles) {
         ServerPlayer player = source.getServer().getPlayerList().getPlayer(profile.getId());
         if (player == null) {
            MoreUtilsData.PlayerCaps data = MoreUtilsData.playerCaps().get(profile.getId());
            int uniqueBase = data == null ? -1 : data.uniqueCap();
            int ultimateBase = data == null ? -1 : data.ultimateCap();
            int uniqueBonus = data == null ? 0 : data.uniqueResetBonus();
            int ultimateBonus = data == null ? 0 : data.ultimateResetBonus();
            source.sendSuccess(
               () -> Component.literal(
                  profile.getName()
                     + " is offline. Personal Unique base="
                     + formatInherited(uniqueBase)
                     + ", Ultimate base="
                     + formatInherited(ultimateBase)
                     + ", Earned Unique=+"
                     + uniqueBonus
                     + ", Earned Ultimate=+"
                     + ultimateBonus
               ),
               false
            );
         } else {
            MoreUtilsSkillCap.processResetCounter(player);
            int uniqueCount = MoreUtilsSkillCap.count(player, SkillCapType.UNIQUE);
            int ultimateCount = MoreUtilsSkillCap.count(player, SkillCapType.ULTIMATE);
            int uniqueCap = MoreUtilsSkillCap.effectiveCap(player, SkillCapType.UNIQUE);
            int ultimateCap = MoreUtilsSkillCap.effectiveCap(player, SkillCapType.ULTIMATE);
            int uniqueBonus = MoreUtilsData.getResetBonus(player.getUUID(), SkillCapType.UNIQUE);
            int ultimateBonus = MoreUtilsData.getResetBonus(player.getUUID(), SkillCapType.ULTIMATE);
            source.sendSuccess(
               () -> Component.literal(
                  player.getGameProfile().getName()
                     + ": Unique "
                     + uniqueCount
                     + "/"
                     + formatCap(uniqueCap)
                     + " (reset +"
                     + uniqueBonus
                     + "), Ultimate "
                     + ultimateCount
                     + "/"
                     + formatCap(ultimateCap)
                     + " (reset +"
                     + ultimateBonus
                     + ")"
               ),
               false
            );
         }
      }

      return profiles.size();
   }

   private static String formatCap(int cap) {
      return cap < 0 ? "Unlimited" : String.valueOf(cap);
   }

   private static String formatInherited(int cap) {
      return cap < 0 ? "Global" : String.valueOf(cap);
   }
}
