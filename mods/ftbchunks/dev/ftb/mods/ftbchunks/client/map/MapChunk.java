package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.data.ChunkSyncInfo;
import dev.ftb.mods.ftblibrary.math.XZ;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.world.entity.player.Player;

public class MapChunk {
   public static final int VERSION = 4;
   public static final MapChunk.DateInfo NO_DATE_INFO = new MapChunk.DateInfo(null, null, null);
   private final MapRegion region;
   private final XZ pos;
   private long modified;
   private int version;
   private Team team;
   private MapChunk.DateInfo dateInfo;

   public MapChunk(MapRegion region, XZ pos) {
      this.region = region;
      this.pos = pos;
      this.modified = 0L;
      this.version = 0;
      this.team = null;
      this.dateInfo = NO_DATE_INFO;
   }

   public void forceUpdate() {
      this.modified = System.currentTimeMillis();
      this.region.update(true);
   }

   public MapRegionData getRegionData() {
      return this.region.getDataBlocking();
   }

   public XZ getPos() {
      return this.pos;
   }

   public Optional<Date> getClaimedDate() {
      return Optional.ofNullable(this.dateInfo.claimed);
   }

   public Optional<Date> getForceLoadedDate() {
      return Optional.ofNullable(this.dateInfo.forceLoaded);
   }

   public Optional<Date> getForceLoadExpiryDate() {
      return Optional.ofNullable(this.dateInfo.expiry);
   }

   public long getModified() {
      return this.modified;
   }

   public void setModified(long modified) {
      this.modified = modified;
   }

   public int getVersion() {
      return this.version;
   }

   public void setVersion(int version) {
      this.version = version;
   }

   public void writeData(DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(this.version);
      dataOutput.writeByte(this.pos.x());
      dataOutput.writeByte(this.pos.z());
      dataOutput.writeLong(this.modified);
   }

   public Optional<Team> getTeam() {
      if (this.team != null && !this.team.isValid()) {
         this.team = (Team)FTBTeamsAPI.api().getClientManager().getTeamByID(this.team.getId()).orElse(null);
      }

      return Optional.ofNullable(this.team);
   }

   public boolean connects(MapChunk chunk) {
      return Objects.equals(this.getTeam(), chunk.getTeam());
   }

   public XZ getActualPos() {
      return XZ.of((this.region.pos.x() << 5) + this.pos.x(), (this.region.pos.z() << 5) + this.pos.z());
   }

   public MapChunk created() {
      this.region.update(true);
      return this;
   }

   public MapChunk offsetBlocking(int x, int z) {
      XZ pos = this.getActualPos().offset(x, z);
      return this.region.dimension.getRegion(XZ.regionFromChunk(pos.x(), pos.z())).getDataBlocking().getChunk(pos);
   }

   public void updateFromServer(Date now, ChunkSyncInfo packet, UUID teamId) {
      this.team = (Team)FTBTeamsAPI.api().getClientManager().getTeamByID(teamId).orElse(null);
      this.dateInfo = packet.getDateInfo(this.team != null, now.getTime());
      this.region.update(false);
      MapManager.getInstance().ifPresent(mgr -> mgr.addPendingUpdateEvent(this.team, this.getActualPos().dim(this.region.dimension.dimension), this.dateInfo));
   }

   public void updateForceLoadExpiryDate(long now, long offset) {
      this.dateInfo = this.dateInfo.withExpiryDate(offset == 0L ? null : new Date(now + offset));
   }

   public boolean isTeamMember(Player player) {
      return this.team != null && this.team.getRankForPlayer(player.getUUID()).isMemberOrBetter();
   }

   public record DateInfo(Date claimed, Date forceLoaded, Date expiry) {
      public MapChunk.DateInfo withExpiryDate(Date newExpiry) {
         return new MapChunk.DateInfo(this.claimed, this.forceLoaded, newExpiry);
      }
   }
}
