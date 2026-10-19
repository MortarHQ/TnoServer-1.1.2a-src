package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.data.ChunkSyncInfo;
import dev.ftb.mods.ftblibrary.math.XZ;
import java.util.Date;
import java.util.UUID;

public record UpdateChunkFromServerTask(MapDimension dimension, ChunkSyncInfo info, UUID teamId, Date date) implements MapTask {
   @Override
   public void runMapTask() {
      this.dimension
         .getRegion(XZ.regionFromChunk(this.info.x(), this.info.z()))
         .getChunkForAbsoluteChunkPos(XZ.of(this.info.x(), this.info.z()))
         .updateFromServer(this.date, this.info, this.teamId);
   }
}
