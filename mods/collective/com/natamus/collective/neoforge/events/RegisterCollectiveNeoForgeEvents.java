package com.natamus.collective.neoforge.events;

import com.natamus.collective_common_neoforge.cmds.CommandCollective;
import com.natamus.collective_common_neoforge.config.GenerateJSONFiles;
import com.natamus.collective_common_neoforge.events.CollectiveEvents;
import com.natamus.collective_common_neoforge.functions.WorldFunctions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

@EventBusSubscriber
public class RegisterCollectiveNeoForgeEvents {
   @SubscribeEvent
   public static void onServerStarted(ServerAboutToStartEvent e) {
      GenerateJSONFiles.initGeneration(e.getServer());
   }

   @SubscribeEvent
   public static void onWorldLoad(Load e) {
      Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
      if (level != null) {
         CollectiveEvents.onWorldLoad(level);
      }
   }

   @SubscribeEvent
   public static void onWorldTick(Post e) {
      Level level = e.getLevel();
      if (!level.isClientSide) {
         CollectiveEvents.onWorldTick((ServerLevel)level);
      }
   }

   @SubscribeEvent
   public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post e) {
      CollectiveEvents.onServerTick(e.getServer());
   }

   @SubscribeEvent
   public static void onEntityJoinLevel(EntityJoinLevelEvent e) {
      if (!CollectiveEvents.onEntityJoinLevel(e.getLevel(), e.getEntity())) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onBlockBreak(BreakEvent e) {
      Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
      if (level != null) {
         if (!CollectiveEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null)) {
            e.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void registerCommands(RegisterCommandsEvent e) {
      CommandCollective.register(e.getDispatcher());
   }
}
