package org.crypticdev.stextras.command.system;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService;
import org.crypticdev.stextras.quest.schedule.QuestSchedule;
import org.crypticdev.stextras.quest.schedule.QuestScheduleEvents;
import org.crypticdev.stextras.quest.schedule.QuestScheduleSavedData;
import org.crypticdev.stextras.quest.schedule.QuestScheduleService;
import org.crypticdev.stextras.storage.STExtarsStorage;

public final class ScheduleCommands {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                           "schedule"
                        )
                        .then(Commands.literal("info").executes(ScheduleCommands::handleInfo)))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("time").then(Commands.literal("daily").executes(ctx -> handleTime(ctx, true))))
                           .then(Commands.literal("weekly").executes(ctx -> handleTime(ctx, false)))
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("count").then(Commands.literal("get").executes(ScheduleCommands::handleCounts)))
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set")
                                       .requires(src -> src.hasPermission(3)))
                                    .then(
                                       Commands.literal("daily")
                                          .then(
                                             Commands.argument("count", IntegerArgumentType.integer(0))
                                                .executes(ctx -> setCounts(ctx, IntegerArgumentType.getInteger(ctx, "count"), null))
                                          )
                                    ))
                                 .then(
                                    Commands.literal("weekly")
                                       .then(
                                          Commands.argument("count", IntegerArgumentType.integer(0))
                                             .executes(ctx -> setCounts(ctx, null, IntegerArgumentType.getInteger(ctx, "count")))
                                       )
                                 ))
                              .then(
                                 Commands.literal("both")
                                    .then(
                                       Commands.argument("daily", IntegerArgumentType.integer(0))
                                          .then(
                                             Commands.argument("weekly", IntegerArgumentType.integer(0))
                                                .executes(
                                                   ctx -> setCounts(
                                                      ctx, IntegerArgumentType.getInteger(ctx, "daily"), IntegerArgumentType.getInteger(ctx, "weekly")
                                                   )
                                                )
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("master").then(Commands.literal("get").executes(ScheduleCommands::handleMasterGet)))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("set").requires(src -> src.hasPermission(3)))
                           .then(Commands.argument("value", BoolArgumentType.bool()).executes(ScheduleCommands::handleMasterSet))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("roll").requires(src -> src.hasPermission(3)))
                        .then(Commands.literal("daily").executes(ctx -> forceRoll(ctx, true, false))))
                     .then(Commands.literal("weekly").executes(ctx -> forceRoll(ctx, false, true))))
                  .then(Commands.literal("both").executes(ctx -> forceRoll(ctx, true, true)))
            ))
         .then(
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("apply").requires(src -> src.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("self").executes(ctx -> applySelf(ctx, false)))
                        .then(
                           Commands.argument("reset_progress", BoolArgumentType.bool())
                              .executes(ctx -> applySelf(ctx, BoolArgumentType.getBool(ctx, "reset_progress")))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("all").requires(src -> src.hasPermission(3)))
                        .executes(ctx -> applyAll(ctx, false)))
                     .then(
                        Commands.argument("reset_progress", BoolArgumentType.bool())
                           .executes(ctx -> applyAll(ctx, BoolArgumentType.getBool(ctx, "reset_progress")))
                     )
               )
         );
   }

   private static int handleInfo(CommandContext<CommandSourceStack> ctx) {
      long daily = STExtarsStorage.Quests.secondsUntilNextDaily();
      long weekly = STExtarsStorage.Quests.secondsUntilNextWeekly();
      QuestSchedule schedule = QuestScheduleSavedData.get(((CommandSourceStack)ctx.getSource()).getServer()).schedule();
      CommandSupport.panel(
         ctx,
         "schedule info",
         List.of(
            CommandText.keyValue("daily code", CommandText.value(schedule.dailyCode)),
            CommandText.keyValue("weekly code", CommandText.value(schedule.weeklyCode)),
            CommandText.keyValue("daily count", CommandText.number(schedule.dailyCount)),
            CommandText.keyValue("weekly count", CommandText.number(schedule.weeklyCount)),
            CommandText.keyValue("master", CommandText.bool(QuestScheduleService.isMaster())),
            CommandText.keyValue("next daily", CommandText.value(CommandSupport.formatDuration(daily))),
            CommandText.keyValue("next weekly", CommandText.value(CommandSupport.formatDuration(weekly)))
         )
      );
      return 1;
   }

   private static int handleCounts(CommandContext<CommandSourceStack> ctx) {
      QuestSchedule schedule = QuestScheduleSavedData.get(((CommandSourceStack)ctx.getSource()).getServer()).schedule();
      CommandSupport.panel(
         ctx,
         "schedule counts",
         List.of(
            CommandText.keyValue("daily", CommandText.number(schedule.dailyCount)), CommandText.keyValue("weekly", CommandText.number(schedule.weeklyCount))
         )
      );
      return 1;
   }

   private static int handleTime(CommandContext<CommandSourceStack> ctx, boolean daily) {
      long seconds = daily ? STExtarsStorage.Quests.secondsUntilNextDaily() : STExtarsStorage.Quests.secondsUntilNextWeekly();
      String name = daily ? "daily" : "weekly";
      CommandSupport.info(
         ctx,
         Component.empty()
            .append(CommandText.label(name + " reset in"))
            .append(Component.literal(" "))
            .append(CommandText.value(CommandSupport.formatDuration(seconds))),
         false
      );
      return (int)Math.min(2147483647L, seconds);
   }

   private static int handleMasterGet(CommandContext<CommandSourceStack> ctx) {
      CommandSupport.info(
         ctx,
         Component.empty()
            .append(CommandText.label("schedule master"))
            .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.bool(QuestScheduleService.isMaster())),
         false
      );
      return QuestScheduleService.isMaster() ? 1 : 0;
   }

   private static int handleMasterSet(CommandContext<CommandSourceStack> ctx) {
      boolean value = BoolArgumentType.getBool(ctx, "value");
      QuestScheduleService.setMaster(value);
      if (value) {
         QuestScheduleService.ensureScheduleUpToDate(((CommandSourceStack)ctx.getSource()).getServer());
      }

      CommandSupport.success(
         ctx,
         Component.empty()
            .append(CommandText.label("schedule master set"))
            .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.bool(value)),
         true
      );
      return 1;
   }

   private static int setCounts(CommandContext<CommandSourceStack> ctx, Integer dailyCount, Integer weeklyCount) {
      if (!QuestScheduleService.isMaster()) {
         CommandSupport.failure(ctx, Component.empty().append(CommandText.label("only the schedule master can change counts")));
         return 0;
      }

      MinecraftServer server = ((CommandSourceStack)ctx.getSource()).getServer();
      QuestSchedule schedule = QuestScheduleSavedData.get(server).schedule();
      if (dailyCount != null) {
         schedule.dailyCount = Math.max(0, dailyCount);
      }

      if (weeklyCount != null) {
         schedule.weeklyCount = Math.max(0, weeklyCount);
      }

      QuestScheduleSavedData.get(server).setDirty();
      ((QuestScheduleEvents.Sync)QuestScheduleEvents.SYNC.invoker()).onSync(server, QuestScheduleService.getScheduleSnapshot(server), true);
      server.getPlayerList().getPlayers().forEach(player -> PlayerQuestAssignmentService.applyCurrentToPlayer(server, player, true));
      CommandSupport.success(
         ctx,
         Component.empty()
            .append(CommandText.label("schedule counts updated"))
            .append(Component.literal(" "))
            .append(Component.literal("(").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.label("daily"))
            .append(Component.literal(": ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.number(schedule.dailyCount))
            .append(Component.literal(", ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.label("weekly"))
            .append(Component.literal(": ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.number(schedule.weeklyCount))
            .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY)),
         true
      );
      return 1;
   }

   private static int forceRoll(CommandContext<CommandSourceStack> ctx, boolean daily, boolean weekly) {
      QuestScheduleService.forceRoll(((CommandSourceStack)ctx.getSource()).getServer(), daily, weekly);
      String label = daily && weekly ? "daily and weekly" : (daily ? "daily" : "weekly");
      CommandSupport.success(
         ctx,
         Component.empty()
            .append(CommandText.label("schedule roll forced"))
            .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.value(label)),
         true
      );
      return 1;
   }

   private static int applySelf(CommandContext<CommandSourceStack> ctx, boolean reset) {
      ServerPlayer player = CommandSupport.self(ctx);
      PlayerQuestAssignmentService.applyCurrentToPlayer(((CommandSourceStack)ctx.getSource()).getServer(), player, reset);
      CommandSupport.success(ctx, applyLine(player, reset), true);
      return 1;
   }

   private static int applyAll(CommandContext<CommandSourceStack> ctx, boolean reset) {
      MinecraftServer server = ((CommandSourceStack)ctx.getSource()).getServer();
      server.getPlayerList().getPlayers().forEach(player -> PlayerQuestAssignmentService.applyCurrentToPlayer(server, player, reset));
      CommandSupport.success(
         ctx,
         Component.empty()
            .append(CommandText.label("schedule applied to players"))
            .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.number(server.getPlayerList().getPlayers().size()))
            .append(Component.literal(" "))
            .append(CommandText.tag("reset=" + reset, reset ? ChatFormatting.YELLOW : ChatFormatting.GRAY)),
         true
      );
      return 1;
   }

   private static Component applyLine(ServerPlayer player, boolean reset) {
      return Component.empty()
         .append(CommandText.label("schedule applied"))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" "))
         .append(CommandText.tag("reset=" + reset, reset ? ChatFormatting.YELLOW : ChatFormatting.GRAY));
   }

   private ScheduleCommands() {
   }
}
