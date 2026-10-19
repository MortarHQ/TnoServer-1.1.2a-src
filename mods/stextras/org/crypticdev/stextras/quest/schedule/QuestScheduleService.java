package org.crypticdev.stextras.quest.schedule;

import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Server;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService;

public final class QuestScheduleService {
   private static volatile boolean MASTER = true;
   private static final int CHECK_INTERVAL_TICKS = 600;
   private static int tickCounter = 0;

   public static boolean isMaster() {
      return MASTER;
   }

   public static void setMaster(boolean master) {
      MASTER = master;
   }

   public static void initServerHooks() {
      TickEvent.SERVER_POST.register((Server)server -> {
         if (++tickCounter % 600 == 0) {
            ensureScheduleUpToDate(server);
         }
      });
   }

   public static long currentDailyCodeUtc() {
      return LocalDate.now(ZoneOffset.UTC).toEpochDay();
   }

   public static long currentWeeklyCodeUtc() {
      return currentDailyCodeUtc() / 7L;
   }

   public static long secondsUntilNextDailyUtc() {
      long today = currentDailyCodeUtc();
      long nextSecond = LocalDate.ofEpochDay(today + 1L).atStartOfDay(ZoneOffset.UTC).toEpochSecond();
      return nextSecond - Instant.now().getEpochSecond();
   }

   public static long secondsUntilNextWeeklyUtc() {
      long today = currentDailyCodeUtc();
      long nextWeekStartDay = (today / 7L + 1L) * 7L;
      long nextSecond = LocalDate.ofEpochDay(nextWeekStartDay).atStartOfDay(ZoneOffset.UTC).toEpochSecond();
      return nextSecond - Instant.now().getEpochSecond();
   }

   public static void ensureScheduleUpToDate(MinecraftServer server) {
      QuestScheduleSavedData data = QuestScheduleSavedData.get(server);
      QuestSchedule schedule = data.schedule();
      boolean changed = false;
      long day = currentDailyCodeUtc();
      if (schedule.dailyCode != day && isMaster()) {
         schedule.dailyCode = day;
         schedule.dailyNonce = 0;
         schedule.dailyCount = rollBetween(server, day, 1, 3);
         changed = true;
      }

      long week = currentWeeklyCodeUtc();
      if (schedule.weeklyCode != week && isMaster()) {
         schedule.weeklyCode = week;
         schedule.weeklyNonce = 0;
         schedule.weeklyCount = rollBetween(server, week * 13L, 3, 5);
         changed = true;
      }

      if (changed) {
         data.setDirty();
         ((QuestScheduleEvents.Sync)QuestScheduleEvents.SYNC.invoker()).onSync(server, getScheduleSnapshot(server), true);

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            PlayerQuestAssignmentService.ensurePlayerMatchesSchedule(server, player);
         }
      }
   }

   public static void applyRemoteSchedule(MinecraftServer server, QuestSchedule incoming) {
      QuestScheduleSavedData data = QuestScheduleSavedData.get(server);
      QuestSchedule schedule = data.schedule();
      boolean changed = false;
      if (schedule.dailyCode != incoming.dailyCode || schedule.dailyCount != incoming.dailyCount || schedule.dailyNonce != incoming.dailyNonce) {
         schedule.dailyCode = incoming.dailyCode;
         schedule.dailyCount = incoming.dailyCount;
         schedule.dailyNonce = incoming.dailyNonce;
         changed = true;
      }

      if (schedule.weeklyCode != incoming.weeklyCode || schedule.weeklyCount != incoming.weeklyCount || schedule.weeklyNonce != incoming.weeklyNonce) {
         schedule.weeklyCode = incoming.weeklyCode;
         schedule.weeklyCount = incoming.weeklyCount;
         schedule.weeklyNonce = incoming.weeklyNonce;
         changed = true;
      }

      if (changed) {
         data.setDirty();
         ((QuestScheduleEvents.Sync)QuestScheduleEvents.SYNC.invoker()).onSync(server, getScheduleSnapshot(server), false);

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            PlayerQuestAssignmentService.ensurePlayerMatchesSchedule(server, player);
         }
      }
   }

   public static void forceRoll(MinecraftServer server, boolean daily, boolean weekly) {
      if (isMaster()) {
         QuestScheduleSavedData data = QuestScheduleSavedData.get(server);
         QuestSchedule schedule = data.schedule();
         boolean changed = false;
         if (daily) {
            schedule.dailyNonce++;
            schedule.dailyCount = 1 + RandomSource.create(seedFor(server, System.nanoTime())).nextInt(3);
            changed = true;
         }

         if (weekly) {
            schedule.weeklyNonce++;
            schedule.weeklyCount = 3 + RandomSource.create(seedFor(server, System.nanoTime() ^ 1597463007L)).nextInt(3);
            changed = true;
         }

         if (changed) {
            data.setDirty();
            ((QuestScheduleEvents.Sync)QuestScheduleEvents.SYNC.invoker()).onSync(server, getScheduleSnapshot(server), true);

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
               PlayerQuestAssignmentService.ensurePlayerMatchesSchedule(server, player);
            }
         }
      }
   }

   public static QuestSchedule getScheduleSnapshot(MinecraftServer server) {
      QuestSchedule schedule = QuestScheduleSavedData.get(server).schedule();
      QuestSchedule copy = new QuestSchedule();
      copy.dailyCode = schedule.dailyCode;
      copy.dailyCount = Math.max(0, schedule.dailyCount);
      copy.dailyNonce = schedule.dailyNonce;
      copy.weeklyCode = schedule.weeklyCode;
      copy.weeklyCount = Math.max(0, schedule.weeklyCount);
      copy.weeklyNonce = schedule.weeklyNonce;
      return copy;
   }

   private static long seedFor(MinecraftServer server, long salt) {
      long worldSeed = server.overworld().getSeed();
      return worldSeed ^ salt * -7046029254386353131L;
   }

   private static int rollBetween(MinecraftServer server, long salt, int min, int max) {
      RandomSource random = RandomSource.create(seedFor(server, salt ^ 12648430L));
      return min + random.nextInt(max - min + 1);
   }

   private QuestScheduleService() {
   }
}
