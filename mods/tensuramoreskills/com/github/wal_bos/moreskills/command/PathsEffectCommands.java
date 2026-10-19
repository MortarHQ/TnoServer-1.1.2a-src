package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.net.PathsEffectNetwork;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class PathsEffectCommands {
   private PathsEffectCommands() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pathsflash").requires(source -> source.hasPermission(2)))
               .then(
                  Commands.argument("targets", EntityArgument.players())
                     .then(
                        Commands.argument("mode", StringArgumentType.word())
                           .suggests((context, builder) -> SharedSuggestionProvider.suggest(List.of("white", "black"), builder))
                           .then(
                              Commands.argument("durationTicks", IntegerArgumentType.integer(1, 12000))
                                 .then(
                                    Commands.argument("alpha", FloatArgumentType.floatArg(0.0F, 1.0F))
                                       .executes(
                                          context -> flash(
                                             (CommandSourceStack)context.getSource(),
                                             EntityArgument.getPlayers(context, "targets"),
                                             StringArgumentType.getString(context, "mode"),
                                             IntegerArgumentType.getInteger(context, "durationTicks"),
                                             FloatArgumentType.getFloat(context, "alpha")
                                          )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               Commands.literal("radius")
                  .then(
                     Commands.argument("range", DoubleArgumentType.doubleArg(0.0, 512.0))
                        .then(
                           Commands.argument("mode", StringArgumentType.word())
                              .suggests((context, builder) -> SharedSuggestionProvider.suggest(List.of("white", "black"), builder))
                              .then(
                                 Commands.argument("durationTicks", IntegerArgumentType.integer(1, 12000))
                                    .then(
                                       Commands.argument("alpha", FloatArgumentType.floatArg(0.0F, 1.0F))
                                          .executes(
                                             context -> flash(
                                                (CommandSourceStack)context.getSource(),
                                                getPlayersInRange((CommandSourceStack)context.getSource(), DoubleArgumentType.getDouble(context, "range")),
                                                StringArgumentType.getString(context, "mode"),
                                                IntegerArgumentType.getInteger(context, "durationTicks"),
                                                FloatArgumentType.getFloat(context, "alpha")
                                             )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pathsshake").requires(source -> source.hasPermission(2)))
               .then(
                  Commands.argument("targets", EntityArgument.players())
                     .then(
                        Commands.argument("durationTicks", IntegerArgumentType.integer(1, 12000))
                           .then(
                              ((RequiredArgumentBuilder)Commands.argument("strength", FloatArgumentType.floatArg(0.0F, 20.0F))
                                    .executes(
                                       context -> shake(
                                          (CommandSourceStack)context.getSource(),
                                          EntityArgument.getPlayers(context, "targets"),
                                          IntegerArgumentType.getInteger(context, "durationTicks"),
                                          FloatArgumentType.getFloat(context, "strength"),
                                          0.0F
                                       )
                                    ))
                                 .then(
                                    Commands.argument("impactStrength", FloatArgumentType.floatArg(0.0F, 20.0F))
                                       .executes(
                                          context -> shake(
                                             (CommandSourceStack)context.getSource(),
                                             EntityArgument.getPlayers(context, "targets"),
                                             IntegerArgumentType.getInteger(context, "durationTicks"),
                                             FloatArgumentType.getFloat(context, "strength"),
                                             FloatArgumentType.getFloat(context, "impactStrength")
                                          )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               Commands.literal("radius")
                  .then(
                     Commands.argument("range", DoubleArgumentType.doubleArg(0.0, 512.0))
                        .then(
                           Commands.argument("durationTicks", IntegerArgumentType.integer(1, 12000))
                              .then(
                                 ((RequiredArgumentBuilder)Commands.argument("strength", FloatArgumentType.floatArg(0.0F, 20.0F))
                                       .executes(
                                          context -> shake(
                                             (CommandSourceStack)context.getSource(),
                                             getPlayersInRange((CommandSourceStack)context.getSource(), DoubleArgumentType.getDouble(context, "range")),
                                             IntegerArgumentType.getInteger(context, "durationTicks"),
                                             FloatArgumentType.getFloat(context, "strength"),
                                             0.0F
                                          )
                                       ))
                                    .then(
                                       Commands.argument("impactStrength", FloatArgumentType.floatArg(0.0F, 20.0F))
                                          .executes(
                                             context -> shake(
                                                (CommandSourceStack)context.getSource(),
                                                getPlayersInRange((CommandSourceStack)context.getSource(), DoubleArgumentType.getDouble(context, "range")),
                                                IntegerArgumentType.getInteger(context, "durationTicks"),
                                                FloatArgumentType.getFloat(context, "strength"),
                                                FloatArgumentType.getFloat(context, "impactStrength")
                                             )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int flash(CommandSourceStack source, Collection<ServerPlayer> players, String modeName, int durationTicks, float alpha) {
      PathsEffectNetwork.FlashMode mode = PathsEffectNetwork.FlashMode.byName(modeName);
      int duration = Mth.clamp(durationTicks, 1, 12000);
      float clampedAlpha = Mth.clamp(alpha, 0.0F, 1.0F);

      for (ServerPlayer player : players) {
         PathsEffectNetwork.sendFlash(player, mode, duration, clampedAlpha);
      }

      source.sendSuccess(() -> Component.literal("Sent Paths flash to " + players.size() + " player(s)."), true);
      return players.size();
   }

   private static int shake(CommandSourceStack source, Collection<ServerPlayer> players, int durationTicks, float strength, float impactStrength) {
      int duration = Mth.clamp(durationTicks, 1, 12000);
      float clampedStrength = Mth.clamp(strength, 0.0F, 20.0F);
      float clampedImpact = Mth.clamp(impactStrength, 0.0F, 20.0F);

      for (ServerPlayer player : players) {
         PathsEffectNetwork.sendShake(player, duration, clampedStrength, clampedImpact);
      }

      source.sendSuccess(() -> Component.literal("Sent Paths shake to " + players.size() + " player(s)."), true);
      return players.size();
   }

   private static List<ServerPlayer> getPlayersInRange(CommandSourceStack source, double range) {
      Vec3 origin = source.getPosition();
      double rangeSqr = range * range;
      List<ServerPlayer> players = new ArrayList<>();

      for (ServerPlayer player : source.getLevel().players()) {
         if (player.distanceToSqr(origin) <= rangeSqr) {
            players.add(player);
         }
      }

      return players;
   }
}
