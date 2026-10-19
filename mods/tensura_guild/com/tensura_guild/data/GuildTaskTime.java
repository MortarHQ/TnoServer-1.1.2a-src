package com.tensura_guild.data;

import java.util.Locale;

public final class GuildTaskTime {
   public static final int DEFAULT_TICK_RATE = 20;
   public static final int COMMISSION_DAY_TICKS = 28800;

   private GuildTaskTime() {
   }

   public static long remainingTicks(long currentGameTime, long startedAt, int timeLimitTicks) {
      return startedAt >= 0L && timeLimitTicks > 0 ? Math.max(0L, timeLimitTicks - Math.max(0L, currentGameTime - startedAt)) : 0L;
   }

   public static String formatRemainingTicks(long remainingTicks, float tickRate) {
      float safeTickRate = tickRate > 0.0F ? tickRate : 20.0F;
      long totalSeconds = Math.max(0L, (long)Math.ceil((float)remainingTicks / safeTickRate));
      return formatSeconds(totalSeconds);
   }

   public static String formatDurationText(long remainingTicks, float tickRate) {
      float safeTickRate = tickRate > 0.0F ? tickRate : 20.0F;
      long totalSeconds = Math.max(0L, (long)Math.ceil((float)remainingTicks / safeTickRate));
      long days = totalSeconds / 86400L;
      long hours = totalSeconds % 86400L / 3600L;
      long minutes = totalSeconds % 3600L / 60L;
      long seconds = totalSeconds % 60L;
      if (days > 0L) {
         return hours > 0L ? days + "天" + hours + "小时" : days + "天";
      } else if (hours > 0L) {
         return minutes > 0L ? hours + "小时" + minutes + "分钟" : hours + "小时";
      } else if (minutes > 0L) {
         return seconds > 0L ? minutes + "分钟" + seconds + "秒" : minutes + "分钟";
      } else {
         return seconds + "秒";
      }
   }

   public static String formatCommissionDayDurationText(long remainingTicks) {
      long totalGameMinutes = Math.max(0L, (long)Math.ceil(remainingTicks * 1440.0 / 28800.0));
      long days = totalGameMinutes / 1440L;
      long hours = totalGameMinutes % 1440L / 60L;
      long minutes = totalGameMinutes % 60L;
      if (days > 0L) {
         return hours > 0L ? days + "天" + hours + "小时" : days + "天";
      } else if (hours > 0L) {
         return minutes > 0L ? hours + "小时" + minutes + "分钟" : hours + "小时";
      } else {
         return minutes + "分钟";
      }
   }

   private static String formatSeconds(long totalSeconds) {
      long minutes = totalSeconds / 60L;
      long seconds = totalSeconds % 60L;
      return String.format(Locale.ROOT, "%02d:%02d", minutes, seconds);
   }

   public static int minutesFromTicks(int ticks) {
      return Math.max(0, Math.round(ticks / 1200.0F));
   }
}
