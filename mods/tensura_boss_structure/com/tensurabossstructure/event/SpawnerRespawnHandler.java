package com.tensurabossstructure.event;

import com.tensurabossstructure.config.BossConfig;
import com.tensurabossstructure.world.SpawnerRespawnData;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@EventBusSubscriber
public class SpawnerRespawnHandler {
   private static final String BOSS_SPAWNER_TAG = "TensuraBossSpawnerTracked";
   private static final int TICK_INTERVAL = 20;
   private static int tickCounter = 0;

   public static void markBoss(Entity boss) {
      boss.getPersistentData().putBoolean("TensuraBossSpawnerTracked", true);
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if ((Boolean)BossConfig.SPAWNER_RECOVERY_ENABLED.get()) {
         if (!event.getEntity().level().isClientSide()) {
            if (event.getEntity().getPersistentData().getBoolean("TensuraBossSpawnerTracked")) {
               UUID deadUuid = event.getEntity().getUUID();
               ServerLevel serverLevel = (ServerLevel)event.getEntity().level();
               SpawnerRespawnData data = SpawnerRespawnData.get(serverLevel);

               for (SpawnerRespawnData.Entry entry : data.getEntries()) {
                  if (entry.status == SpawnerRespawnData.Status.WAITING_FOR_DEATH && entry.bossUuid.equals(deadUuid)) {
                     entry.status = SpawnerRespawnData.Status.COOLING_DOWN;
                     entry.ticksLeft = entry.cooldownTicks;
                     data.setDirty();
                     break;
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onServerTick(Post event) {
      if ((Boolean)BossConfig.SPAWNER_RECOVERY_ENABLED.get()) {
         if (++tickCounter >= 20) {
            tickCounter = 0;
            MinecraftServer server = event.getServer();
            SpawnerRespawnData data = SpawnerRespawnData.get(server.overworld());
            if (!data.getEntries().isEmpty()) {
               boolean dirty = false;
               Iterator<SpawnerRespawnData.Entry> it = data.getEntries().iterator();

               while (it.hasNext()) {
                  SpawnerRespawnData.Entry entry = it.next();
                  if (entry.status != SpawnerRespawnData.Status.WAITING_FOR_DEATH) {
                     entry.ticksLeft -= 20;
                     if (entry.ticksLeft <= 0) {
                        ServerLevel targetLevel = null;

                        for (ServerLevel level : server.getAllLevels()) {
                           if (level.dimension().location().equals(entry.dimension)) {
                              targetLevel = level;
                              break;
                           }
                        }

                        if (targetLevel != null && targetLevel.isLoaded(entry.pos)) {
                           Block block = (Block)BuiltInRegistries.BLOCK.get(entry.blockId);
                           targetLevel.setBlock(entry.pos, block.defaultBlockState(), 3);
                           it.remove();
                           dirty = true;
                        }
                     }
                  }
               }

               if (dirty) {
                  data.setDirty();
               }
            }
         }
      }
   }
}
