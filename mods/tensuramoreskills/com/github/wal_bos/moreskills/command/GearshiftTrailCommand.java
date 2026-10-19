package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.net.msg.FullCowlLightningPayload;
import com.github.wal_bos.moreskills.net.msg.GearshiftTrailPayload;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.architectury.networking.NetworkManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class GearshiftTrailCommand {
   private static final int DEFAULT_GEARSHIFT_DURATION = 200;
   private static final int DEFAULT_GEARSHIFT_GEAR = 5;
   private static final int DEFAULT_FULL_COWL_DURATION = 32;
   private static final int DEFAULT_FULL_COWL_PERCENT = 5;
   private static final int MIN_DURATION = 1;
   private static final int MAX_DURATION = 2400;
   private static final double SEND_RANGE = 128.0;

   private GearshiftTrailCommand() {
   }

   @SubscribeEvent
   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("gearshifttrail")
                     .requires(source -> source.hasPermission(2)))
                  .executes(context -> activateGearshiftTrail((CommandSourceStack)context.getSource(), 200, 5)))
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("duration", IntegerArgumentType.integer(1, 2400))
                        .executes(
                           context -> activateGearshiftTrail((CommandSourceStack)context.getSource(), IntegerArgumentType.getInteger(context, "duration"), 5)
                        ))
                     .then(
                        Commands.argument("gear", IntegerArgumentType.integer(0, 5))
                           .executes(
                              context -> activateGearshiftTrail(
                                 (CommandSourceStack)context.getSource(),
                                 IntegerArgumentType.getInteger(context, "duration"),
                                 IntegerArgumentType.getInteger(context, "gear")
                              )
                           )
                     )
               )
         );
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fullcowl").requires(source -> source.hasPermission(2)))
                  .executes(context -> activateFullCowl((CommandSourceStack)context.getSource(), 32, 5)))
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("duration", IntegerArgumentType.integer(1, 2400))
                        .executes(context -> activateFullCowl((CommandSourceStack)context.getSource(), IntegerArgumentType.getInteger(context, "duration"), 5)))
                     .then(
                        Commands.argument("percent", IntegerArgumentType.integer(1, 100))
                           .executes(
                              context -> activateFullCowl(
                                 (CommandSourceStack)context.getSource(),
                                 IntegerArgumentType.getInteger(context, "duration"),
                                 IntegerArgumentType.getInteger(context, "percent")
                              )
                           )
                     )
               )
         );
   }

   private static int activateGearshiftTrail(CommandSourceStack source, int duration, int gear) {
      ServerPlayer player = source.getPlayer();
      if (player == null) {
         source.sendFailure(Component.literal("This command must be used by a player."));
         return 0;
      }

      GearshiftTrailPayload payload = new GearshiftTrailPayload(player.getId(), duration, gear);
      AABB area = player.getBoundingBox().inflate(128.0);

      for (ServerPlayer other : player.serverLevel().getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(other, payload);
      }

      source.sendSuccess(() -> Component.literal("Activated Gearshift trail."), true);
      return 1;
   }

   private static int activateFullCowl(CommandSourceStack source, int duration, int percent) {
      ServerPlayer player = source.getPlayer();
      if (player == null) {
         source.sendFailure(Component.literal("This command must be used by a player."));
         return 0;
      }

      int clampedDuration = Mth.clamp(duration, 1, 2400);
      int clampedPercent = Mth.clamp(percent, 1, 100);
      FullCowlLightningPayload payload = new FullCowlLightningPayload(player.getId(), clampedDuration, clampedPercent, false, false);
      AABB area = player.getBoundingBox().inflate(128.0);

      for (ServerPlayer other : player.serverLevel().getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(other, payload);
      }

      source.sendSuccess(() -> Component.literal("full cowl activate"), true);
      return 1;
   }
}
