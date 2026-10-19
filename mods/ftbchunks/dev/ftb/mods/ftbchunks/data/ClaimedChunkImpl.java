package dev.ftb.mods.ftbchunks.data;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksExpected;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.event.ClaimedChunkEvent;
import dev.ftb.mods.ftbchunks.net.SendChunkPacket;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ClaimedChunkImpl implements ClaimedChunk {
   private ChunkTeamDataImpl teamData;
   private final ChunkDimPos pos;
   private long time;
   private long forceLoaded;
   private long forceLoadExpiryTime;

   public ClaimedChunkImpl(ChunkTeamDataImpl teamData, ChunkDimPos pos) {
      this.teamData = teamData;
      this.pos = pos;
      this.time = System.currentTimeMillis();
      this.forceLoaded = 0L;
      this.forceLoadExpiryTime = 0L;
   }

   public ChunkTeamDataImpl getTeamData() {
      return this.teamData;
   }

   public void setTeamData(@NotNull ChunkTeamDataImpl teamData) {
      teamData.clearClaimCaches();
      this.teamData.clearClaimCaches();
      this.teamData = teamData;
   }

   @Override
   public ChunkDimPos getPos() {
      return this.pos;
   }

   @Override
   public long getTimeClaimed() {
      return this.time;
   }

   @Override
   public String getResultId() {
      return "ok";
   }

   @Override
   public boolean isSuccess() {
      return true;
   }

   @Override
   public MutableComponent getMessage() {
      return Component.literal("OK");
   }

   public void setClaimedTime(long t) {
      this.time = t;
      this.teamData.getManager().clearForceLoadedCache();
      this.teamData.clearClaimCaches();
      this.sendUpdateToAll();
   }

   @Override
   public long getForceLoadedTime() {
      return this.forceLoaded;
   }

   @Override
   public boolean isForceLoaded() {
      return this.forceLoaded > 0L;
   }

   @Override
   public boolean isActuallyForceLoaded() {
      return this.isForceLoaded() && this.teamData.canDoOfflineForceLoading();
   }

   public void setForceLoadedTime(long time) {
      if (this.forceLoaded != time) {
         this.forceLoaded = time;
         this.teamData.getManager().clearForceLoadedCache();
         this.teamData.clearClaimCaches();
         this.sendUpdateToAll();
         ServerLevel level = this.teamData.getManager().getMinecraftServer().getLevel(this.pos.dimension());
         if (level != null) {
            if (this.forceLoaded > 0L) {
               level.getChunk(this.pos.x(), this.pos.z());
            }

            ServerChunkCache cache = level.getChunkSource();
            ChunkPos chunkPos = this.pos.chunkPos();
            if (cache != null) {
               FTBChunksExpected.addChunkToForceLoaded(level, "ftbchunks", this.teamData.getTeamId(), chunkPos.x, chunkPos.z, this.forceLoaded > 0L);
               cache.save(false);
            } else {
               FTBChunks.LOGGER.warn("Failed to force-load chunk " + this.pos.x() + ", " + this.pos.z() + " @ " + this.pos.dimension().location() + "!");
            }
         }
      }
   }

   public boolean canEntitySpawn(Entity entity) {
      return true;
   }

   public boolean allowExplosions() {
      return this.teamData.canExplosionsDamageTerrain();
   }

   public boolean allowMobGriefing() {
      return this.teamData.allowMobGriefing();
   }

   public void sendUpdateToAll() {
      new SendChunkPacket(this.pos.dimension(), this.teamData.getTeamId(), ChunkSyncInfo.create(System.currentTimeMillis(), this.pos.x(), this.pos.z(), this))
         .sendToAll(this.teamData.getManager().getMinecraftServer(), this.teamData);
   }

   @Override
   public void unload(CommandSourceStack source) {
      if (this.isForceLoaded()) {
         this.setForceLoadedTime(0L);
         ((ClaimedChunkEvent.After)ClaimedChunkEvent.AFTER_UNLOAD.invoker()).after(source, this);
         this.teamData.clearClaimCaches();
         this.teamData.markDirty();
         this.forceLoadExpiryTime = 0L;
      }
   }

   @Override
   public void unclaim(CommandSourceStack source, boolean sync) {
      this.unload(source);
      this.teamData.getManager().unregisterClaim(this.pos);
      ((ClaimedChunkEvent.After)ClaimedChunkEvent.AFTER_UNCLAIM.invoker()).after(source, this);
      this.teamData.clearClaimCaches();
      this.teamData.markDirty();
      if (sync) {
         SendChunkPacket packet = new SendChunkPacket(
            this.pos.dimension(), Util.NIL_UUID, ChunkSyncInfo.create(System.currentTimeMillis(), this.pos.x(), this.pos.z(), null)
         );
         NetworkManager.sendToPlayers(source.getServer().getPlayerList().getPlayers(), packet);
      }
   }

   @Override
   public long getForceLoadExpiryTime() {
      return this.forceLoadExpiryTime;
   }

   @Override
   public void setForceLoadExpiryTime(long forceLoadExpiryTime) {
      this.forceLoadExpiryTime = forceLoadExpiryTime;
      this.teamData.markDirty();
   }

   @Override
   public boolean hasForceLoadExpired(long now) {
      return this.forceLoadExpiryTime > 0L && this.forceLoadExpiryTime < now;
   }

   @Override
   public String toString() {
      return "[ " + this.pos.toString() + " - " + this.teamData + " ]";
   }

   public CompoundTag serializeNBT() {
      SNBTCompoundTag o = new SNBTCompoundTag();
      o.singleLine();
      o.putInt("x", this.getPos().x());
      o.putInt("z", this.getPos().z());
      o.putLong("time", this.getTimeClaimed());
      if (this.isForceLoaded()) {
         o.putLong("force_loaded", this.getForceLoadedTime());
      }

      if (this.getForceLoadExpiryTime() > 0L) {
         o.putLong("expiry_time", this.getForceLoadExpiryTime());
      }

      return o;
   }

   public static ClaimedChunkImpl deserializeNBT(ChunkTeamDataImpl data, ResourceKey<Level> dimKey, CompoundTag tag) {
      ClaimedChunkImpl chunk = new ClaimedChunkImpl(data, new ChunkDimPos(dimKey, tag.getInt("x"), tag.getInt("z")));
      chunk.time = tag.getLong("time");
      chunk.forceLoaded = tag.getLong("force_loaded");
      chunk.forceLoadExpiryTime = tag.getLong("expiry_time");
      return chunk;
   }
}
