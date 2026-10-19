package dev.ftb.mods.ftbchunks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftbchunks.api.Protection;
import dev.ftb.mods.ftbchunks.neoforge.FTBChunksExpectedImpl;
import dev.ftb.mods.ftbteams.api.event.TeamCollectPropertiesEvent;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;

public class FTBChunksExpected {
   @ExpectPlatform
   @Transformed
   public static void addChunkToForceLoaded(ServerLevel level, String modId, UUID owner, int chunkX, int chunkY, boolean add) {
      FTBChunksExpectedImpl.addChunkToForceLoaded(level, modId, owner, chunkX, chunkY, add);
   }

   @ExpectPlatform
   @Transformed
   public static void getPlatformSpecificProperties(TeamCollectPropertiesEvent event) {
      FTBChunksExpectedImpl.getPlatformSpecificProperties(event);
   }

   @ExpectPlatform
   @Transformed
   public static Protection getBlockPlaceProtection() {
      return FTBChunksExpectedImpl.getBlockPlaceProtection();
   }

   @ExpectPlatform
   @Transformed
   public static Protection getBlockInteractProtection() {
      return FTBChunksExpectedImpl.getBlockInteractProtection();
   }

   @ExpectPlatform
   @Transformed
   public static Protection getBlockBreakProtection() {
      return FTBChunksExpectedImpl.getBlockBreakProtection();
   }
}
