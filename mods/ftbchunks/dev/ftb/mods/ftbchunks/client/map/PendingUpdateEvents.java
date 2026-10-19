package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.api.client.event.ChunksUpdatedFromServerEvent;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftbteams.api.Team;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.Nullable;

class PendingUpdateEvents {
   private final Map<PendingUpdateEvents.MapKey, Set<ChunkDimPos>> pendingMap = new HashMap<>();

   void addPending(@Nullable Team team, ChunkDimPos pos, MapChunk.DateInfo dateInfo) {
      this.pendingMap.computeIfAbsent(PendingUpdateEvents.MapKey.of(team, dateInfo), k -> new HashSet<>()).add(pos);
   }

   void fireEvents() {
      this.pendingMap
         .forEach(
            (key, chunks) -> ((Consumer)ChunksUpdatedFromServerEvent.UPDATED.invoker())
               .accept(new ChunksUpdatedFromServerEvent((Set<ChunkDimPos>)chunks, key.team, key.claimed, key.forceLoaded, key.expiry))
         );
      this.pendingMap.clear();
   }

   private record MapKey(@Nullable Team team, Date claimed, Date forceLoaded, Date expiry) {
      static PendingUpdateEvents.MapKey of(Team team, MapChunk.DateInfo dateInfo) {
         return new PendingUpdateEvents.MapKey(team, dateInfo.claimed(), dateInfo.forceLoaded(), dateInfo.expiry());
      }
   }
}
