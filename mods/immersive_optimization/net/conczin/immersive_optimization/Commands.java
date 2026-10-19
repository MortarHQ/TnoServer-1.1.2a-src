package net.conczin.immersive_optimization;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.function.Consumer;
import net.conczin.immersive_optimization.config.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class Commands {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("io")
                  .requires(source -> source.hasPermission(4)))
               .then(LiteralArgumentBuilder.literal("report").executes(context -> {
                  StringBuilder sb = new StringBuilder();
                  sb.append("§l§a[Immersive Optimization Report]§r\n");
                  TickScheduler.INSTANCE.levelData.forEach((key, data) -> {
                     if (data.getEntities() > 0) {
                        sb.append("%s: %s\n".formatted(key.getPath(), data.toLog()));
                     }
                  });
                  send(context, sb.toString());
                  return 0;
               })))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(
                                             "config"
                                          )
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("preset")
                                                      .then(LiteralArgumentBuilder.literal("performance").executes(context -> {
                                                         setConfigPreset(0.5F);
                                                         return 0;
                                                      })))
                                                   .then(LiteralArgumentBuilder.literal("default").executes(context -> {
                                                      setConfigPreset(1.0F);
                                                      return 0;
                                                   })))
                                                .then(LiteralArgumentBuilder.literal("quality").executes(context -> {
                                                   setConfigPreset(2.0F);
                                                   return 0;
                                                }))
                                          ))
                                       .then(toggle("enableEntities", enabled -> Config.getInstance().enableEntities = enabled)))
                                    .then(toggle("enableBlockEntities", enabled -> Config.getInstance().enableBlockEntities = enabled)))
                                 .then(toggle("enableDistanceCulling", enabled -> Config.getInstance().enableDistanceCulling = enabled)))
                              .then(toggle("enableTrackingCulling", enabled -> Config.getInstance().enableTrackingCulling = enabled)))
                           .then(toggle("enableViewportCulling", enabled -> Config.getInstance().enableViewportCulling = enabled)))
                        .then(toggle("enabledStress", enabled -> Config.getInstance().stressedThreshold = enabled ? (new Config()).stressedThreshold : 0)))
                     .then(
                        LiteralArgumentBuilder.literal("blacklist")
                           .then(RequiredArgumentBuilder.argument("id", StringArgumentType.greedyString()).executes(context -> {
                              String id = StringArgumentType.getString(context, "id");
                              boolean state = Config.getInstance().entities.getOrDefault(id, true);
                              Config.getInstance().entities.put(id, !state);
                              Config.getInstance().save();
                              if (state) {
                                 send(context, "Added %s to blacklist!".formatted(id));
                              } else {
                                 send(context, "Removed %s from blacklist!".formatted(id));
                              }

                              return 0;
                           }))
                     ))
                  .then(LiteralArgumentBuilder.literal("reload").executes(context -> {
                     Config.getInstance().reload();
                     send(context, "Config reloaded!");
                     return 0;
                  }))
            )
      );
   }

   private static void setConfigPreset(float quality) {
      Config d = new Config();
      Config c = Config.getInstance();
      c.minDistance = (int)(d.minDistance * quality);
      c.blocksPerLevel = (int)(d.blocksPerLevel * quality);
      c.blocksPerLevelDistanceCulled = (int)(d.blocksPerLevelDistanceCulled * quality);
      c.blocksPerLevelTrackingCulled = (int)(d.blocksPerLevelTrackingCulled * quality);
      c.blocksPerLevelViewportCulled = (int)(d.blocksPerLevelViewportCulled * quality);
      c.maxLevel = (int)(d.maxLevel / quality);
      c.save();
   }

   private static ArgumentBuilder<CommandSourceStack, ?> toggle(String name, Consumer<Boolean> consumer) {
      return LiteralArgumentBuilder.literal(name).then(RequiredArgumentBuilder.argument("enabled", BoolArgumentType.bool()).executes(context -> {
         consumer.accept(BoolArgumentType.getBool(context, "enabled"));
         TickScheduler.INSTANCE.reset();
         Config.getInstance().save();
         send(context, "Config updated!");
         return 0;
      }));
   }

   private static void send(CommandContext<CommandSourceStack> context, String message) {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayer();
      if (player != null) {
         player.sendSystemMessage(Component.literal(message));
      }
   }
}
