package com.tkisor.memorysweep;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.tkisor.memorysweep.config.ModConfig;
import com.tkisor.memorysweep.data.Flags;
import com.tkisor.memorysweep.utils.SweepUtil;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientPlayerEvent.ClientPlayerJoin;
import dev.architectury.event.events.client.ClientPlayerEvent.ClientPlayerQuit;
import dev.architectury.event.events.client.ClientTickEvent.Client;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MemorySweep {
   public static final String MOD_ID = "memorysweep";
   public static final Logger logger = LoggerFactory.getLogger("memorysweep");
   public static LocalPlayer player = null;

   public static void init() {
      if (!Flags.initConfig) {
         ModConfig.init(Platform.getGameFolder());
         Flags.initConfig = true;
      }

      if (ModConfig.isLoaderConfigMod() && Platform.getEnvironment() == Env.CLIENT) {
         Platform.getMod("memorysweep").registerConfigurationScreen(parent -> ModConfig.get().getConfigScreen(parent));
      }

      CommandRegistrationEvent.EVENT
         .register(
            (CommandRegistrationEvent)(dispatcher, registry, selection) -> dispatcher.register(
               (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("memorysweep_s").requires(source -> {
                     int requiredPermission = Platform.getEnvironment() == Env.CLIENT ? 0 : 2;
                     return source.hasPermission(requiredPermission);
                  }))
                  .executes(
                     context -> {
                        Util.ioPool()
                           .submit(
                              () -> {
                                 try {
                                    Optional.ofNullable(((CommandSourceStack)context.getSource()).getPlayer())
                                       .ifPresent(
                                          player -> player.displayClientMessage(
                                             Component.translatable("memorysweep.gc.start", new Object[]{SweepUtil.getFormattedMemoryInGB()}), true
                                          )
                                       );
                                    System.gc();
                                    Thread.sleep(1000L);
                                    System.gc();
                                    ((CommandSourceStack)context.getSource())
                                       .getPlayer()
                                       .displayClientMessage(
                                          Component.translatable("memorysweep.gc.end", new Object[]{SweepUtil.getFormattedMemoryInGB()}), true
                                       );
                                    logger.info("MemorySweep: Garbage Collection executed.");
                                 } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                 }
                              }
                           );
                        return 0;
                     }
                  )
            )
         );
      if (Platform.getEnvironment() == Env.CLIENT) {
         ClientCommandRegistrationEvent.EVENT
            .register(
               (ClientCommandRegistrationEvent)(dispatcher, context1) -> dispatcher.register(
                  (LiteralArgumentBuilder)LiteralArgumentBuilder.literal("memorysweep_c").executes(context -> {
                     Flags.sweepClient = true;
                     return 0;
                  })
               )
            );
         ClientPlayerEvent.CLIENT_PLAYER_JOIN.register((ClientPlayerJoin)player1 -> player = player1);
         ClientPlayerEvent.CLIENT_PLAYER_QUIT.register((ClientPlayerQuit)player1 -> player = null);
         ClientTickEvent.CLIENT_PRE
            .register(
               (Client)clientLevel -> {
                  if (ModConfig.get().memorySweep) {
                     if (SweepUtil.isSweep()) {
                        if (player != null && !ModConfig.get().silent) {
                           player.displayClientMessage(Component.translatable("memorysweep.gc.start", new Object[]{SweepUtil.getFormattedMemoryInGB()}), true);
                        }

                        SweepUtil.sweepAsync()
                           .thenAccept(
                              data -> {
                                 if (player != null && !ModConfig.get().silent) {
                                    player.displayClientMessage(
                                       Component.translatable(
                                          "memorysweep.gc.end", new Object[]{(long)(((Long)data.getRight()).longValue() / 1024.0 / 1024.0 / 1024.0)}
                                       ),
                                       true
                                    );
                                 }
                              }
                           );
                     }

                     if (SweepUtil.isAvgSweep()) {
                        SweepUtil.calculateAndSweepMemory().thenAccept(data -> {
                           if (player != null && !ModConfig.get().silent) {
                              player.displayClientMessage(Component.translatable("memorysweep.gc.end", new Object[]{SweepUtil.getFormattedMemoryInGB()}), true);
                           }
                        });
                     }
                  }
               }
            );
      }
   }
}
