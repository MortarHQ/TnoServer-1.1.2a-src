package com.tensura_guild.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class GuildConfig {
   public static final int DEFAULT_COMMISSION_TIME_LIMIT_TICKS = 72000;
   public static final int DEFAULT_COMMISSION_AUTO_UPDATE_TICKS = 28800;
   public static final int DEFAULT_COMMISSION_WITHDRAW_TICKS = 201600;
   public static final int DEFAULT_COMMISSION_BOARD_MAX_TASKS = 9;
   public static final int DEFAULT_DWARF_TASKS_FOR_GUILD_MAP = 3;
   public static final int DEFAULT_SHOP_REFRESH_DAYS = 7;
   public static final ModConfigSpec SPEC;
   public static final IntValue COMMISSION_TIME_LIMIT_TICKS;
   public static final IntValue COMMISSION_AUTO_UPDATE_TICKS;
   public static final IntValue COMMISSION_WITHDRAW_TICKS;
   public static final IntValue COMMISSION_BOARD_MAX_TASKS;
   public static final IntValue DWARF_TASKS_FOR_GUILD_MAP;
   public static final IntValue SHOP_REFRESH_DAYS;

   private GuildConfig() {
   }

   public static int commissionTimeLimitTicks() {
      return getLoadedInt(COMMISSION_TIME_LIMIT_TICKS, 72000);
   }

   public static int commissionAutoUpdateTicks() {
      return getLoadedInt(COMMISSION_AUTO_UPDATE_TICKS, 28800);
   }

   public static int commissionAutoUpdateTicksForRank(String rank) {
      return commissionAutoUpdateTicks();
   }

   public static int commissionWithdrawTicks() {
      return getLoadedInt(COMMISSION_WITHDRAW_TICKS, 201600);
   }

   public static int commissionBoardMaxTasks() {
      return getLoadedInt(COMMISSION_BOARD_MAX_TASKS, 9);
   }

   public static int dwarfTasksForGuildMap() {
      return getLoadedInt(DWARF_TASKS_FOR_GUILD_MAP, 3);
   }

   public static int shopRefreshDays() {
      return getLoadedInt(SHOP_REFRESH_DAYS, 7);
   }

   private static int getLoadedInt(IntValue value, int fallback) {
      return SPEC.isLoaded() ? (Integer)value.get() : fallback;
   }

   static {
      Builder builder = new Builder();
      COMMISSION_TIME_LIMIT_TICKS = builder.comment(
            "Time limit for guild commission tasks in game ticks. 72000 ticks = 60 real minutes at 20 TPS. Set to 0 to disable the limit."
         )
         .defineInRange("commissionTimeLimitTicks", 72000, 0, Integer.MAX_VALUE);
      COMMISSION_AUTO_UPDATE_TICKS = builder.comment(
            "Interval in game ticks for automatically adding one commission to the task board. 28800 ticks = 24 real minutes at 20 TPS. Set to 0 to disable automatic updates."
         )
         .defineInRange("commissionAutoUpdateTicks", 28800, 0, Integer.MAX_VALUE);
      COMMISSION_WITHDRAW_TICKS = builder.comment(
            "Time before an unaccepted commission is withdrawn from the task board. 201600 ticks = 7 commission days, where 28800 ticks is one day. Set to 0 to disable withdrawal."
         )
         .defineInRange("commissionWithdrawTicks", 201600, 0, Integer.MAX_VALUE);
      COMMISSION_BOARD_MAX_TASKS = builder.comment(
            "Maximum number of commissions shown on the task board. The board will not auto-update when this many commissions are already available."
         )
         .defineInRange("commissionBoardMaxTasks", 9, 1, 9);
      DWARF_TASKS_FOR_GUILD_MAP = builder.comment("Number of completed dwarf commissions required before a dwarf offers the Adventurer Guild map reward.")
         .defineInRange("dwarfTasksForGuildMap", 3, 1, Integer.MAX_VALUE);
      SHOP_REFRESH_DAYS = builder.comment("Number of vanilla game days between reputation shop refreshes. One game day is 24000 ticks.")
         .defineInRange("shopRefreshDays", 7, 1, Integer.MAX_VALUE);
      SPEC = builder.build();
   }
}
