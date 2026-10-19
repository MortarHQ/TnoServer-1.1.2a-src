package noobanidus.mods.lootr.neoforge.event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent.Load;
import net.neoforged.neoforge.event.level.ChunkEvent.Unload;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import noobanidus.mods.lootr.common.chunk.LoadedChunks;

@EventBusSubscriber(modid = "lootr")
public class HandleChunk {
   @SubscribeEvent
   public static void onChunkLoad(Load event) {
      if (event.getChunk() instanceof LevelChunk levelChunk) {
         LevelAccessor level = event.getLevel();
         LoadedChunks.onChunkLoad(level, levelChunk);
      }
   }

   @SubscribeEvent
   public static void onChunkUnload(Unload event) {
      if (event.getChunk() instanceof LevelChunk levelChunk) {
         LevelAccessor level = event.getLevel();
         LoadedChunks.onChunkUnload(level, levelChunk);
      }
   }

   @SubscribeEvent
   public static void onServerStarted(ServerAboutToStartEvent event) {
      LoadedChunks.clear();
   }

   @SubscribeEvent
   public static void onServerStopped(ServerStoppedEvent event) {
      LoadedChunks.clear();
   }
}
