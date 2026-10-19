package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.handler.AsmodayBlackHoleHandler;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class AsmodayBlackHoleCommand {
   private AsmodayBlackHoleCommand() {
   }

   @SubscribeEvent
   public static void register(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("asmodayblackhole")
                     .requires(source -> source.hasPermission(2)))
                  .executes(context -> spawn((CommandSourceStack)context.getSource(), 55.0F, 240)))
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("radius", FloatArgumentType.floatArg(8.0F, 160.0F))
                        .executes(context -> spawn((CommandSourceStack)context.getSource(), FloatArgumentType.getFloat(context, "radius"), 240)))
                     .then(
                        Commands.argument("duration", IntegerArgumentType.integer(40, 1200))
                           .executes(
                              context -> spawn(
                                 (CommandSourceStack)context.getSource(),
                                 FloatArgumentType.getFloat(context, "radius"),
                                 IntegerArgumentType.getInteger(context, "duration")
                              )
                           )
                     )
               )
         );
   }

   private static int spawn(CommandSourceStack source, float radius, int duration) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      Vec3 pos = player.getEyePosition().add(player.getLookAngle().normalize().scale(radius * 0.75F));
      AsmodayBlackHoleHandler.spawn(player.serverLevel(), player, pos, radius, duration);
      source.sendSuccess(() -> Component.literal("Spawned Asmoday black hole."), true);
      return 1;
   }
}
