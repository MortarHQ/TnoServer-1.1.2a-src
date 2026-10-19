package net.conczin.immersive_optimization;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.conczin.immersive_optimization.config.Config;
import net.conczin.immersive_optimization.mixin.EntityTickListAccessor;
import net.conczin.immersive_optimization.mixin.ServerLevelAccessor;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class TickScheduler {
   public static TickScheduler INSTANCE = new TickScheduler();
   public static final int THREAD_SLEEP = 500;
   public static final int MAX_STRESS_TICKS = 600;
   public static final int CLEAR_BLOCK_ENTITIES_INTERVAL = 207;
   public MinecraftServer server;
   public TickScheduler.FrustumProxy frustum;
   public final Map<ResourceLocation, TickScheduler.LevelData> levelData = new ConcurrentHashMap<>();

   public static void setServer(MinecraftServer server) {
      INSTANCE.server = server;
      INSTANCE.reset();
      Thread worker = new Thread(new TickScheduler.Worker(INSTANCE, server), "Immersive Optimization Worker");
      worker.setPriority(1);
      worker.setDaemon(true);
      worker.start();
   }

   @Nullable
   public TickScheduler.LevelData getLevelData(Level level) {
      return this.levelData.get(level.dimension().location());
   }

   public void reset() {
      this.levelData.clear();
      this.frustum = null;
   }

   public void startLevelTick(ServerLevel level) {
      TickScheduler.LevelData data = this.getLevelData(level);
      if (data != null) {
         int stressedThreshold = Config.getInstance().stressedThreshold;
         boolean stressed = stressedThreshold > 0 && this.server.getAverageTickTimeNanos() > stressedThreshold * 1000000L;
         if (stressed) {
            data.stressedTicks = Math.min(600, data.stressedTicks + 2);
            data.lifeTimeStressedTicks++;
         } else {
            data.stressedTicks = Math.max(0, data.stressedTicks - 1);
         }

         data.tick++;
         data.time = System.nanoTime();
      }
   }

   void tickLevel(ServerLevel level) {
      TickScheduler.LevelData data = this.levelData.computeIfAbsent(level.dimension().location(), TickScheduler.LevelData::new);
      long tick = level.getGameTime();
      TickScheduler.Stats previousStats = data.previousStats;
      data.previousStats = data.stats;
      data.stats = previousStats;
      data.stats.reset();
      if (tick % 207L == 0L) {
         data.blockEntityPriorities.clear();
      }

      if (!Config.getInstance().enableEntities) {
         data.priorities.clear();
      } else {
         Int2IntOpenHashMap newPriorities = new Int2IntOpenHashMap();
         Int2ObjectMap<Entity> entities = ((EntityTickListAccessor)((ServerLevelAccessor)level).getEntityTickList()).getActive();
         entities.values().forEach(entity -> {
            if (entity != null) {
               int priority = this.getPriority(data, level, entity);
               if (priority > 0) {
                  newPriorities.put(entity.getId(), priority);
               }

               data.stats.tickRate = data.stats.tickRate + 1.0 / Math.max(1, priority);
               data.stats.entities++;
            }
         });
         data.priorities = newPriorities;
      }
   }

   public boolean shouldTick(Entity entity) {
      if (!entity.noCulling && INSTANCE != null) {
         TickScheduler.LevelData data = this.getLevelData(entity.level());
         if (data == null) {
            return true;
         }

         int priority = data.priorities.getOrDefault(entity.getId(), 0);
         return priority <= 1 ? true : (data.tick + entity.getId()) % priority == 0L;
      } else {
         return true;
      }
   }

   public int getPriority(TickScheduler.LevelData data, ServerLevel level, Entity entity) {
      Config config = Config.getInstance();
      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
      if (!config.entities.getOrDefault(id.toString(), true)) {
         return 0;
      }

      if (!config.entities.getOrDefault(id.getNamespace(), true)) {
         return 0;
      }

      if (!config.cullProjectiles && entity instanceof Projectile) {
         return 0;
      }

      double minDistance = 999999.0;

      for (Player player : level.players()) {
         minDistance = Math.min(minDistance, player.distanceToSqr(entity));
      }

      AABB box = entity.getBoundingBox();
      int blocksPerLevel = config.blocksPerLevel;
      boolean integratedAndSinglePlayer = !level.getServer().isDedicatedServer() && level.players().size() == 1;
      if (config.enableDistanceCulling && integratedAndSinglePlayer && !entity.shouldRenderAtSqrDistance(minDistance)) {
         blocksPerLevel = config.blocksPerLevelDistanceCulled;
         data.stats.distanceCulledEntities++;
      }

      if (config.enableTrackingCulling && blocksPerLevel > config.blocksPerLevelTrackingCulled) {
         int trackingRange = entity.getType().clientTrackingRange();
         int scaledTrackingDistance = level.getServer().getScaledTrackingDistance(trackingRange * 16);
         if (minDistance > scaledTrackingDistance * scaledTrackingDistance) {
            blocksPerLevel = config.blocksPerLevelTrackingCulled;
            data.stats.trackingCulledEntities++;
         }
      }

      if (config.enableViewportCulling
         && blocksPerLevel > config.blocksPerLevelViewportCulled
         && integratedAndSinglePlayer
         && this.frustum != null
         && !this.frustum.isVisible(box)) {
         blocksPerLevel = config.blocksPerLevelViewportCulled;
         data.stats.viewportCulledEntities++;
      }

      double antiStress = 1.0 - data.stressedTicks / 600.0 * config.minDecreaseFactor;
      int finalBlocksPerLevel = (int)(blocksPerLevel * antiStress);
      int distanceLevel = (int)((Math.sqrt(minDistance) - config.minDistance) / Math.max(2, finalBlocksPerLevel));
      return Math.min(config.maxLevel, Math.max(1, distanceLevel + 1));
   }

   public boolean shouldTick(Level level, long pos) {
      if (!Config.getInstance().enableBlockEntities) {
         return true;
      }

      TickScheduler.LevelData data = this.getLevelData(level);
      if (data == null) {
         return true;
      }

      int priority = data.blockEntityPriorities.computeIfAbsent(pos, p -> this.getBlockEntityPriority(level, p));
      return priority < 1 || (level.getGameTime() + pos) % priority == 0L;
   }

   private int getBlockEntityPriority(Level level, long p) {
      int x = ChunkPos.getX(p);
      int z = ChunkPos.getZ(p);
      if (x >= -300000 && x <= 300000 && z >= -300000 && z <= 300000) {
         double minDistance = Double.MAX_VALUE;

         for (Player player : level.players()) {
            double dx = SectionPos.blockToSectionCoord(player.getX()) - x;
            double dz = SectionPos.blockToSectionCoord(player.getZ()) - z;
            double distance = dx * dx + dz * dz;
            if (distance < minDistance) {
               minDistance = distance;
            }
         }

         return Math.min(
            (int)((Math.sqrt(minDistance * 16.0) - Config.getInstance().minDistance) / Config.getInstance().blocksPerLevelBlockEntities) + 1,
            Config.getInstance().maxLevel
         );
      } else {
         return -1;
      }
   }

   public interface FrustumProxy {
      boolean isVisible(AABB var1);
   }

   public static class LevelData {
      public boolean active;
      public long tick = 0L;
      public long time = 0L;
      public TickScheduler.Stats stats = new TickScheduler.Stats();
      public TickScheduler.Stats previousStats = new TickScheduler.Stats();
      public int stressedTicks = 0;
      public int lifeTimeStressedTicks = 0;
      public Int2IntOpenHashMap priorities = new Int2IntOpenHashMap();
      public Map<Long, Integer> blockEntityPriorities = new ConcurrentHashMap<>();

      public LevelData(ResourceLocation location) {
         this.active = Config.getInstance().dimensions.getOrDefault(location.toString(), true);
      }

      public int getEntities() {
         return this.previousStats.entities;
      }

      public String toLog() {
         return "Rate %2.1f%%, %d entities, %d stress, %d stressed | culled %2.1f%% distance, %2.1f%% tracking, %2.1f%% viewport"
            .formatted(
               this.previousStats.tickRate / this.previousStats.entities * 100.0,
               this.previousStats.entities,
               this.stressedTicks,
               this.lifeTimeStressedTicks,
               (float)this.previousStats.distanceCulledEntities / this.previousStats.entities * 100.0F,
               (float)this.previousStats.trackingCulledEntities / this.previousStats.entities * 100.0F,
               (float)this.previousStats.viewportCulledEntities / this.previousStats.entities * 100.0F
            );
      }
   }

   public static class Stats {
      public double tickRate = 0.0;
      public int entities = 0;
      public int distanceCulledEntities = 0;
      public int trackingCulledEntities = 0;
      public int viewportCulledEntities = 0;

      public void reset() {
         this.tickRate = 0.0;
         this.entities = 0;
         this.distanceCulledEntities = 0;
         this.trackingCulledEntities = 0;
         this.viewportCulledEntities = 0;
      }
   }

   public static class Worker implements Runnable {
      TickScheduler scheduler;
      MinecraftServer server;

      public Worker(TickScheduler scheduler, MinecraftServer server) {
         this.scheduler = scheduler;
         this.server = server;
      }

      @Override
      public void run() {
         while (this.server.isRunning()) {
            try {
               for (ServerLevel level : this.server.getAllLevels()) {
                  this.scheduler.tickLevel(level);
               }

               Thread.sleep(500L);
            } catch (ConcurrentModificationException | ArrayIndexOutOfBoundsException var3) {
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         Constants.LOG.info("Shutting down Immersive Optimization worker");
      }
   }
}
