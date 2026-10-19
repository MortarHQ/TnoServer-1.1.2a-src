package mcjty.incontrol.data;

import java.util.ArrayList;
import java.util.List;
import mcjty.incontrol.InControl;

public class Statistics {
   private static final List<Statistics.SpawnStat> SPAWN_STATS = new ArrayList<>();
   private static final List<Statistics.SpawnerStat> SPAWNER_STATS = new ArrayList<>();

   public static void addSpawnerStat(int rule) {
      while (rule >= SPAWNER_STATS.size()) {
         SPAWNER_STATS.add(new Statistics.SpawnerStat());
      }

      Statistics.SpawnerStat stat = SPAWNER_STATS.get(rule);
      stat.counter++;
   }

   public static void addSpawnStat(int rule, boolean deny) {
      while (rule >= SPAWN_STATS.size()) {
         SPAWN_STATS.add(new Statistics.SpawnStat());
      }

      Statistics.SpawnStat stat = SPAWN_STATS.get(rule);
      stat.counter++;
      stat.deny = deny;
   }

   public static void clear() {
      SPAWN_STATS.clear();
      SPAWNER_STATS.clear();
   }

   public static void dump() {
      InControl.setup.getLogger().info("### Spawner ###");

      for (int i = 0; i < SPAWNER_STATS.size(); i++) {
         Statistics.SpawnerStat stat = SPAWNER_STATS.get(i);
         InControl.setup.getLogger().info("Rule " + i + " spawned " + stat.counter + " mobs");
      }

      InControl.setup.getLogger().info("### Spawn ###");

      for (int i = 0; i < SPAWN_STATS.size(); i++) {
         Statistics.SpawnStat stat = SPAWN_STATS.get(i);
         InControl.setup.getLogger().info("Rule " + i + " fired " + stat.counter + " times (" + (stat.deny ? "deny)" : "allow)"));
      }
   }

   private static class SpawnStat {
      private int counter;
      private boolean deny;
   }

   private static class SpawnerStat {
      private int counter;
   }
}
