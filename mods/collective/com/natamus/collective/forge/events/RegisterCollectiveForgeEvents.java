package com.natamus.collective.forge.events;

import com.natamus.collective_common_forge.cmds.CommandCollective;
import com.natamus.collective_common_forge.config.GenerateJSONFiles;
import com.natamus.collective_common_forge.events.CollectiveEvents;
import com.natamus.collective_common_forge.functions.WorldFunctions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.event.level.LevelEvent.Load;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RegisterCollectiveForgeEvents {
   @SubscribeEvent
   public void onServerStarted(ServerAboutToStartEvent e) {
      GenerateJSONFiles.initGeneration(e.getServer());
   }

   @SubscribeEvent
   public void onWorldLoad(Load e) {
      Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
      if (level != null) {
         CollectiveEvents.onWorldLoad(level);
      }
   }

   @SubscribeEvent
   public void onWorldTick(LevelTickEvent e) {
      Level level = e.level;
      if (!level.isClientSide && e.phase.equals(Phase.END)) {
         CollectiveEvents.onWorldTick((ServerLevel)level);
      }
   }

   @SubscribeEvent
   public void onServerTick(ServerTickEvent e) {
      if (e.phase.equals(Phase.END)) {
         CollectiveEvents.onServerTick(e.getServer());
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public void onMobSpawnerSpecialSpawn(FinalizeSpawn e) {
      Level Level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
      if (Level != null) {
         if (!e.isSpawnCancelled() && e.getSpawner() != null) {
            e.getEntity().addTag("collective.fromspawner");
         }
      }
   }

   @SubscribeEvent
   public void onEntityJoinLevel(EntityJoinLevelEvent e) {
      if (!CollectiveEvents.onEntityJoinLevel(e.getLevel(), e.getEntity())) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onBlockBreak(BreakEvent e) {
      Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
      if (level != null) {
         if (!CollectiveEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null)) {
            e.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void registerCommands(RegisterCommandsEvent e) {
      CommandCollective.register(e.getDispatcher());
   }
}
