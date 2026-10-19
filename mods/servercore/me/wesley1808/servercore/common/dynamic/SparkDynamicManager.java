package me.wesley1808.servercore.common.dynamic;

import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow.MillisPerTick;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.minecraft.server.MinecraftServer;

public class SparkDynamicManager extends DynamicManager {
   private GenericStatistic<DoubleAverageInfo, MillisPerTick> tickStatistics;

   public SparkDynamicManager(MinecraftServer server) {
      super(server);
   }

   @Override
   protected double calculateAverageTickTime() {
      if (this.tickStatistics == null) {
         this.tickStatistics = SparkProvider.get().mspt();
      }

      return this.tickStatistics != null ? ((DoubleAverageInfo)this.tickStatistics.poll(MillisPerTick.SECONDS_10)).median() : super.calculateAverageTickTime();
   }
}
