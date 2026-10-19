package dev.shadowako.trlagbegon;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class TrLagBegonCommands {
   private TrLagBegonCommands() {
   }

   public static void register(RegisterCommandsEvent event) {
      CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("trlagbegon").requires(source -> source.hasPermission(2)))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("stresstest").executes(ctx -> startStress((CommandSourceStack)ctx.getSource(), 30, 60)))
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("seconds", IntegerArgumentType.integer(1, 600))
                              .executes(ctx -> startStress((CommandSourceStack)ctx.getSource(), IntegerArgumentType.getInteger(ctx, "seconds"), 60)))
                           .then(
                              Commands.argument("entities_per_tick", IntegerArgumentType.integer(1, 500))
                                 .executes(
                                    ctx -> startStress(
                                       (CommandSourceStack)ctx.getSource(),
                                       IntegerArgumentType.getInteger(ctx, "seconds"),
                                       IntegerArgumentType.getInteger(ctx, "entities_per_tick")
                                    )
                                 )
                           )
                     )
               ))
            .then(Commands.literal("stop").executes(ctx -> stopStress((CommandSourceStack)ctx.getSource())))
      );
   }

   private static int startStress(CommandSourceStack source, int seconds, int entitiesPerTick) {
      if (source.getPlayer() == null) {
         source.sendFailure(Component.literal("Run this command as a player."));
         return 0;
      } else {
         StressTestRunner.start(source.getPlayer(), seconds, entitiesPerTick);
         source.sendSuccess(() -> Component.literal("Started stress test for " + seconds + "s at " + entitiesPerTick + " entities/tick."), true);
         return 1;
      }
   }

   private static int stopStress(CommandSourceStack source) {
      if (source.getPlayer() == null) {
         source.sendFailure(Component.literal("Run this command as a player."));
         return 0;
      } else {
         boolean stopped = StressTestRunner.stop(source.getPlayer().getUUID());
         if (stopped) {
            source.sendSuccess(() -> Component.literal("Stopped your stress test."), true);
            return 1;
         } else {
            source.sendFailure(Component.literal("No active stress test for you."));
            return 0;
         }
      }
   }
}
