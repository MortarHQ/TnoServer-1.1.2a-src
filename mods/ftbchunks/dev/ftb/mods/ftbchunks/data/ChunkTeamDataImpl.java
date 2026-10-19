package dev.ftb.mods.ftbchunks.data;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.FTBChunksExpected;
import dev.ftb.mods.ftbchunks.FTBChunksWorldConfig;
import dev.ftb.mods.ftbchunks.api.ChunkTeamData;
import dev.ftb.mods.ftbchunks.api.ClaimResult;
import dev.ftb.mods.ftbchunks.api.FTBChunksProperties;
import dev.ftb.mods.ftbchunks.api.event.ClaimedChunkEvent;
import dev.ftb.mods.ftbchunks.net.SendGeneralDataPacket;
import dev.ftb.mods.ftbchunks.net.SendManyChunksPacket;
import dev.ftb.mods.ftbchunks.util.DimensionFilter;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.TeamManager;
import dev.ftb.mods.ftbteams.api.TeamRank;
import dev.ftb.mods.ftbteams.api.property.PrivacyMode;
import dev.ftb.mods.ftbteams.api.property.PrivacyProperty;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ChunkTeamDataImpl implements ChunkTeamData {
   private final ClaimedChunkManagerImpl manager;
   private final Team team;
   private final Path file;
   private boolean shouldSave;
   private int maxClaimChunks;
   private int maxForceLoadChunks;
   private int extraClaimChunks;
   private int extraForceLoadChunks;
   private Boolean canForceLoadChunks;
   private final Map<UUID, TeamMemberData> memberData;
   private int prevChunkX = Integer.MAX_VALUE;
   private int prevChunkZ = Integer.MAX_VALUE;
   private String lastChunkID = "";
   private long lastLoginTime;
   private Set<String> fakePlayerNameCache;
   private Collection<ClaimedChunkImpl> claimedChunkCache;
   private Collection<ClaimedChunkImpl> forcedChunkCache;
   private final Map<UUID, ChunkTeamDataImpl.PreventedAccess> preventedAccess = new HashMap<>();

   public ChunkTeamDataImpl(ClaimedChunkManagerImpl manager, Path file, Team team) {
      this.manager = manager;
      this.file = file;
      this.team = team;
      this.shouldSave = false;
      this.maxClaimChunks = -1;
      this.maxForceLoadChunks = -1;
      this.extraClaimChunks = 0;
      this.extraForceLoadChunks = 0;
      this.lastLoginTime = 0L;
      this.memberData = new HashMap<>();
   }

   @Override
   public String toString() {
      return this.team.getId().toString();
   }

   public ClaimedChunkManagerImpl getManager() {
      return this.manager;
   }

   public Path getFile() {
      return this.file;
   }

   @Override
   public TeamManager getTeamManager() {
      return this.manager.getTeamManager();
   }

   @Override
   public Team getTeam() {
      return this.team;
   }

   public UUID getTeamId() {
      return this.team.getId();
   }

   @Override
   public void setExtraClaimChunks(int extraClaimChunks) {
      this.extraClaimChunks = extraClaimChunks;
   }

   @Override
   public void setExtraForceLoadChunks(int extraForceLoadChunks) {
      this.extraForceLoadChunks = extraForceLoadChunks;
   }

   @Override
   public Collection<ClaimedChunkImpl> getClaimedChunks() {
      if (this.claimedChunkCache == null) {
         this.claimedChunkCache = this.manager.getAllClaimedChunks().stream().filter(chunk -> chunk.getTeamData() == this).collect(Collectors.toList());
      }

      return this.claimedChunkCache;
   }

   @Override
   public Collection<ClaimedChunkImpl> getForceLoadedChunks() {
      if (this.forcedChunkCache == null) {
         this.forcedChunkCache = this.manager
            .getAllClaimedChunks()
            .stream()
            .filter(chunk -> chunk.getTeamData() == this && chunk.isForceLoaded())
            .collect(Collectors.toList());
      }

      return this.forcedChunkCache;
   }

   public Collection<ClaimedChunkImpl> getOriginalClaims(UUID playerID) {
      if (!this.memberData.containsKey(playerID)) {
         return Collections.emptyList();
      }

      List<ClaimedChunkImpl> res = new ArrayList<>();

      for (ChunkDimPos cdp : this.memberData.get(playerID).getOriginalClaims()) {
         ClaimedChunkImpl cc = this.manager.getChunk(cdp);
         if (cc != null && cc.getTeamData() == this) {
            res.add(this.manager.getChunk(cdp));
         }
      }

      return res;
   }

   @Override
   public ClaimResult claim(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly) {
      ClaimedChunkImpl chunk = this.manager.getChunk(pos);
      if (chunk != null) {
         return ClaimResult.StandardProblem.ALREADY_CLAIMED;
      }

      if (!DimensionFilter.isDimensionOK(pos.dimension())) {
         return ClaimResult.StandardProblem.DIMENSION_FORBIDDEN;
      }

      if (!this.team.isServerTeam() && this.getClaimedChunks().size() >= this.getMaxClaimChunks()) {
         return ClaimResult.StandardProblem.NOT_ENOUGH_POWER;
      }

      chunk = new ClaimedChunkImpl(this, pos);
      ClaimResult result = (ClaimResult)((ClaimedChunkEvent.Before)ClaimedChunkEvent.BEFORE_CLAIM.invoker()).before(source, chunk).object();
      if (result == null) {
         result = chunk;
      }

      if (!checkOnly && result.isSuccess()) {
         chunk.setClaimedTime(System.currentTimeMillis());
         this.manager.registerClaim(pos, chunk);
         ((ClaimedChunkEvent.After)ClaimedChunkEvent.AFTER_CLAIM.invoker()).after(source, chunk);
         this.markDirty();
         return chunk;
      } else {
         return result;
      }
   }

   @Override
   public ClaimResult unclaim(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly, boolean adminOverride) {
      ClaimedChunkImpl chunk = this.manager.getChunk(pos);
      if (chunk == null) {
         return ClaimResult.StandardProblem.NOT_CLAIMED;
      }

      if (chunk.getTeamData() == this || adminOverride && source.hasPermission(2)) {
         ClaimResult result = (ClaimResult)((ClaimedChunkEvent.Before)ClaimedChunkEvent.BEFORE_UNCLAIM.invoker()).before(source, chunk).object();
         if (result == null) {
            result = chunk;
         }

         if (!checkOnly && result.isSuccess()) {
            chunk.unclaim(source, true);
            this.markDirty();
            return chunk;
         } else {
            return result;
         }
      } else {
         return ClaimResult.StandardProblem.NOT_OWNER;
      }
   }

   @Override
   public ClaimResult forceLoad(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly, boolean adminOverride) {
      ClaimedChunkImpl chunk = this.manager.getChunk(pos);
      if (chunk == null) {
         return ClaimResult.StandardProblem.NOT_CLAIMED;
      }

      if (chunk.getTeamData() == this || adminOverride && source.hasPermission(2)) {
         if (chunk.isForceLoaded()) {
            return ClaimResult.StandardProblem.ALREADY_LOADED;
         }

         if (!this.team.isServerTeam() && this.getForceLoadedChunks().size() >= this.getMaxForceLoadChunks()) {
            return ClaimResult.StandardProblem.NOT_ENOUGH_POWER;
         }

         ClaimResult result = (ClaimResult)((ClaimedChunkEvent.Before)ClaimedChunkEvent.BEFORE_LOAD.invoker()).before(source, chunk).object();
         if (result == null) {
            result = chunk;
         }

         if (!checkOnly && result.isSuccess()) {
            chunk.setForceLoadedTime(System.currentTimeMillis());
            ((ClaimedChunkEvent.After)ClaimedChunkEvent.AFTER_LOAD.invoker()).after(source, chunk);
            chunk.getTeamData().markDirty();
            chunk.sendUpdateToAll();
            return chunk;
         } else {
            return result;
         }
      } else {
         return ClaimResult.StandardProblem.NOT_OWNER;
      }
   }

   @Override
   public ClaimResult unForceLoad(CommandSourceStack source, ChunkDimPos pos, boolean checkOnly, boolean adminOverride) {
      ClaimedChunkImpl chunk = this.manager.getChunk(pos);
      if (chunk == null) {
         return ClaimResult.StandardProblem.NOT_CLAIMED;
      }

      if (chunk.getTeamData() == this || adminOverride && source.hasPermission(2)) {
         if (!chunk.isForceLoaded()) {
            return ClaimResult.StandardProblem.NOT_LOADED;
         }

         ClaimResult result = (ClaimResult)((ClaimedChunkEvent.Before)ClaimedChunkEvent.BEFORE_UNLOAD.invoker()).before(source, chunk).object();
         if (result == null) {
            result = chunk;
         }

         if (!checkOnly && result.isSuccess()) {
            chunk.unload(source);
            return chunk;
         } else {
            return result;
         }
      } else {
         return ClaimResult.StandardProblem.NOT_OWNER;
      }
   }

   public void markDirty() {
      this.shouldSave = true;
      this.team.markDirty();
   }

   @Override
   public boolean isTeamMember(UUID playerId) {
      return playerId.equals(this.getTeamId()) || this.team.getMembers().contains(playerId);
   }

   @Override
   public boolean isAlly(UUID playerId) {
      if (FTBChunksWorldConfig.ALLY_MODE.get() != AllyMode.FORCED_ALL && !this.team.getRankForPlayer(playerId).isMemberOrBetter()) {
         return FTBChunksWorldConfig.ALLY_MODE.get() == AllyMode.FORCED_NONE ? false : this.team.getRankForPlayer(playerId) == TeamRank.ALLY;
      } else {
         return true;
      }
   }

   @Override
   public boolean canPlayerUse(ServerPlayer player, PrivacyProperty property) {
      PrivacyMode mode = (PrivacyMode)this.team.getProperty(property);
      if (mode == PrivacyMode.PUBLIC) {
         return true;
      } else if (PlayerHooks.isFake(player)) {
         return this.canFakePlayerUse(player, mode);
      } else {
         return mode == PrivacyMode.ALLIES ? this.isAlly(player.getUUID()) : this.team.getRankForPlayer(player.getUUID()).isMemberOrBetter();
      }
   }

   private boolean canFakePlayerUse(Player player, PrivacyMode mode) {
      if ((Boolean)this.team.getProperty(FTBChunksProperties.ALLOW_ALL_FAKE_PLAYERS)) {
         return mode == PrivacyMode.ALLIES;
      } else {
         boolean checkById = (Boolean)this.team.getProperty(FTBChunksProperties.ALLOW_FAKE_PLAYERS_BY_ID) && player.getUUID() != null;
         if (mode == PrivacyMode.ALLIES) {
            return checkById && this.isAlly(player.getUUID()) || this.fakePlayerMatches(player.getGameProfile());
         } else {
            return mode != PrivacyMode.PRIVATE ? false : checkById && this.team.getRankForPlayer(player.getUUID()).isMemberOrBetter();
         }
      }
   }

   private boolean fakePlayerMatches(GameProfile profile) {
      return profile.getName() != null && this.getCachedFakePlayerNames().contains(profile.getName().toLowerCase(Locale.ROOT))
         || profile.getId() != null && this.getCachedFakePlayerNames().contains(profile.getId().toString().toLowerCase(Locale.ROOT));
   }

   private Set<String> getCachedFakePlayerNames() {
      if (this.fakePlayerNameCache == null) {
         this.fakePlayerNameCache = ((List)this.team.getProperty(FTBChunksProperties.ALLOW_NAMED_FAKE_PLAYERS))
            .stream()
            .map(s -> s.toLowerCase(Locale.ROOT))
            .collect(Collectors.toSet());
      }

      return this.fakePlayerNameCache;
   }

   public void clearFakePlayerNameCache() {
      this.fakePlayerNameCache = null;
   }

   public SNBTCompoundTag serializeNBT() {
      SNBTCompoundTag tag = new SNBTCompoundTag();
      tag.putInt("max_claim_chunks", this.getMaxClaimChunks());
      tag.putInt("max_force_load_chunks", this.getMaxForceLoadChunks());
      if (this.extraClaimChunks > 0 && !this.team.isPartyTeam()) {
         tag.putInt("extra_claim_chunks", this.extraClaimChunks);
      }

      if (this.extraForceLoadChunks > 0 && !this.team.isPartyTeam()) {
         tag.putInt("extra_force_load_chunks", this.extraForceLoadChunks);
      }

      tag.putLong("last_login_time", this.lastLoginTime);
      CompoundTag chunksTag = new CompoundTag();

      for (ClaimedChunkImpl chunk : this.getClaimedChunks()) {
         String key = chunk.getPos().dimension().location().toString();
         ListTag chunksListTag = chunksTag.getList(key, 10);
         if (chunksListTag.isEmpty()) {
            chunksTag.put(key, chunksListTag);
         }

         chunksListTag.add(chunk.serializeNBT());
      }

      tag.put("chunks", chunksTag);
      CompoundTag memberTag = new CompoundTag();
      this.memberData.forEach((id, data) -> memberTag.put(id.toString(), data.serializeNBT()));
      if (!memberTag.isEmpty()) {
         tag.put("member_data", memberTag);
      }

      if (!this.preventedAccess.isEmpty()) {
         SNBTCompoundTag p = new SNBTCompoundTag();
         this.preventedAccess
            .forEach(
               (id, element) -> p.put(id.toString(), (Tag)ChunkTeamDataImpl.PreventedAccess.CODEC.encodeStart(NbtOps.INSTANCE, element).result().orElseThrow())
            );
         tag.put("prevented_access", p);
      }

      return tag;
   }

   public void deserializeNBT(CompoundTag tag) {
      this.maxClaimChunks = tag.getInt("max_claim_chunks");
      this.maxForceLoadChunks = tag.getInt("max_force_load_chunks");
      this.extraClaimChunks = tag.getInt("extra_claim_chunks");
      this.extraForceLoadChunks = tag.getInt("extra_force_load_chunks");
      this.lastLoginTime = tag.getLong("last_login_time");
      this.canForceLoadChunks = null;
      this.claimedChunkCache = null;
      this.forcedChunkCache = null;
      CompoundTag chunksTag = tag.getCompound("chunks");

      for (String key : chunksTag.getAllKeys()) {
         ResourceKey<Level> dimKey = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(key));
         ListTag chunksListTag = chunksTag.getList(key, 10);

         for (int i = 0; i < chunksListTag.size(); i++) {
            ClaimedChunkImpl chunk = ClaimedChunkImpl.deserializeNBT(this, dimKey, chunksListTag.getCompound(i));
            this.manager.registerClaim(chunk.getPos(), chunk);
         }
      }

      this.memberData.clear();
      CompoundTag memberTag = tag.getCompound("member_data");

      for (String key : memberTag.getAllKeys()) {
         try {
            UUID id = UUID.fromString(key);
            if (id != Util.NIL_UUID) {
               this.memberData.put(id, TeamMemberData.deserializeNBT(memberTag.getCompound(key)));
            }
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
         }
      }

      this.preventedAccess.clear();
      if (tag.contains("prevented_access")) {
         CompoundTag p = tag.getCompound("prevented_access");

         for (String key : p.getAllKeys()) {
            this.preventedAccess
               .put(
                  UUID.fromString(key),
                  (ChunkTeamDataImpl.PreventedAccess)ChunkTeamDataImpl.PreventedAccess.CODEC.parse(NbtOps.INSTANCE, p.getCompound(key)).result().orElseThrow()
               );
         }

         this.prunePreventedLog();
      }
   }

   @Override
   public int getExtraClaimChunks() {
      if (this.extraClaimChunks > 0 && this.team.isPartyTeam()) {
         FTBChunks.LOGGER
            .info(
               "found non-zero extra_claim_chunks={} in party team {}: transferring to owner {}", this.extraClaimChunks, this.getTeamId(), this.team.getOwner()
            );
         ChunkTeamDataImpl personalTeam = ClaimedChunkManagerImpl.getInstance().getPersonalData(this.team.getOwner());
         personalTeam.extraClaimChunks = this.extraClaimChunks;
         this.extraClaimChunks = 0;
         this.markDirty();
         personalTeam.markDirty();
      }

      return this.extraClaimChunks;
   }

   @Override
   public int getExtraForceLoadChunks() {
      if (this.extraForceLoadChunks > 0 && this.team.isPartyTeam()) {
         FTBChunks.LOGGER
            .info(
               "found non-zero extra_force_load_chunks={} in party team {}: transferring to owner {}",
               this.extraForceLoadChunks,
               this.getTeamId(),
               this.team.getOwner()
            );
         ChunkTeamDataImpl personalTeam = ClaimedChunkManagerImpl.getInstance().getPersonalData(this.team.getOwner());
         personalTeam.extraForceLoadChunks = this.extraForceLoadChunks;
         this.extraForceLoadChunks = 0;
         this.markDirty();
         personalTeam.markDirty();
      }

      return this.extraForceLoadChunks;
   }

   public boolean setForceLoadMember(UUID id, boolean val) {
      if (val == this.getTeamMemberData(id).isOfflineForceLoader()) {
         return false;
      }

      this.getTeamMemberData(id).setOfflineForceLoader(val);
      FTBChunks.LOGGER.debug("team {}: set force load member {} = {}", this.team.getId(), id, val);
      this.markDirty();
      this.canForceLoadChunks = null;
      this.manager.clearForceLoadedCache();
      return true;
   }

   @NotNull
   private TeamMemberData getTeamMemberData(UUID id) {
      if (id.equals(Util.NIL_UUID)) {
         FTBChunks.LOGGER.warn("attempt to get member data for nil UUID");
         new RuntimeException().printStackTrace();
         return TeamMemberData.defaultData();
      } else {
         return this.memberData.computeIfAbsent(id, k -> TeamMemberData.defaultData());
      }
   }

   public void updateChunkTickets(boolean load) {
      this.getClaimedChunks().forEach(chunk -> {
         if (chunk.isForceLoaded()) {
            ServerLevel level = this.manager.getMinecraftServer().getLevel(chunk.getPos().dimension());
            if (level != null) {
               FTBChunksExpected.addChunkToForceLoaded(level, "ftbchunks", this.getTeamId(), chunk.getPos().x(), chunk.getPos().z(), load);
            }
         }
      });
   }

   public void saveNow() {
      if (this.shouldSave && SNBT.write(this.file, this.serializeNBT())) {
         this.shouldSave = false;
      }
   }

   @Override
   public boolean canDoOfflineForceLoading() {
      if (this.canForceLoadChunks == null) {
         this.canForceLoadChunks = switch ((ForceLoadMode)FTBChunksWorldConfig.FORCE_LOAD_MODE.get()) {
            case ALWAYS -> true;
            case NEVER -> false;
            default -> this.hasForceLoadMembers();
         };
      }

      return this.canForceLoadChunks;
   }

   private boolean hasForceLoadMembers() {
      return this.memberData.values().stream().anyMatch(TeamMemberData::isOfflineForceLoader);
   }

   @Override
   public boolean canExplosionsDamageTerrain() {
      return (Boolean)this.team.getProperty(FTBChunksProperties.ALLOW_EXPLOSIONS);
   }

   @Override
   public boolean allowMobGriefing() {
      return (Boolean)this.team.getProperty(FTBChunksProperties.ALLOW_MOB_GRIEFING);
   }

   @Override
   public boolean allowPVP() {
      return (Boolean)this.team.getProperty(FTBChunksProperties.ALLOW_PVP);
   }

   public void setLastLoginTime(long when) {
      this.lastLoginTime = when;
      this.markDirty();
   }

   @Override
   public long getLastLoginTime() {
      if (this.lastLoginTime == 0L) {
         this.setLastLoginTime(System.currentTimeMillis());
      }

      return this.lastLoginTime;
   }

   @Override
   public boolean shouldHideClaims() {
      return this.getTeam().getProperty(FTBChunksProperties.CLAIM_VISIBILITY) != PrivacyMode.PUBLIC;
   }

   public void syncChunksToPlayer(ServerPlayer recipient) {
      this.chunksByDimension().forEach((dimension, chunkPackets) -> {
         if (!chunkPackets.isEmpty()) {
            new SendManyChunksPacket((ResourceKey<Level>)dimension, this.getTeamId(), (List<ChunkSyncInfo>)chunkPackets).sendToPlayer(recipient, this);
         }
      });
   }

   public void syncChunksToAll(MinecraftServer server) {
      this.chunksByDimension().forEach((dimension, chunkPackets) -> {
         if (!chunkPackets.isEmpty()) {
            new SendManyChunksPacket((ResourceKey<Level>)dimension, this.getTeamId(), (List<ChunkSyncInfo>)chunkPackets).sendToAll(server, this);
         }
      });
   }

   private Map<ResourceKey<Level>, List<ChunkSyncInfo>> chunksByDimension() {
      long now = System.currentTimeMillis();
      return this.getClaimedChunks()
         .stream()
         .collect(
            Collectors.groupingBy(
               c -> c.getPos().dimension(), Collectors.mapping(c -> ChunkSyncInfo.create(now, c.getPos().x(), c.getPos().z(), c), Collectors.toList())
            )
         );
   }

   @Override
   public int getMaxClaimChunks() {
      return this.maxClaimChunks;
   }

   @Override
   public int getMaxForceLoadChunks() {
      return this.maxForceLoadChunks;
   }

   public void updateLimits() {
      this.updateMemberLimitData(!this.memberData.isEmpty());
      int prevMaxClaimed = this.maxClaimChunks;
      int prevMaxForced = this.maxForceLoadChunks;
      if (!this.team.isPartyTeam()) {
         TeamMemberData m = this.getTeamMemberData(this.getTeam().getId());
         this.maxClaimChunks = m.getMaxClaims();
         this.maxForceLoadChunks = m.getMaxForceLoads();
      } else {
         switch ((PartyLimitMode)FTBChunksWorldConfig.PARTY_LIMIT_MODE.get()) {
            case OWNER: {
               TeamMemberData m = this.getTeamMemberData(this.getTeam().getOwner());
               this.maxClaimChunks = m.getMaxClaims();
               this.maxForceLoadChunks = m.getMaxForceLoads();
               break;
            }
            case SUM:
               this.maxClaimChunks = this.maxForceLoadChunks = 0;
               this.memberData.values().forEach(mx -> {
                  this.maxClaimChunks = this.maxClaimChunks + mx.getMaxClaims();
                  this.maxForceLoadChunks = this.maxForceLoadChunks + mx.getMaxForceLoads();
               });
               break;
            case LARGEST: {
               this.maxClaimChunks = this.maxForceLoadChunks = 0;

               for (TeamMemberData m : this.memberData.values()) {
                  this.maxClaimChunks = Math.max(m.getMaxClaims(), this.maxClaimChunks);
                  this.maxForceLoadChunks = Math.max(m.getMaxForceLoads(), this.maxForceLoadChunks);
               }
               break;
            }
            case AVERAGE:
               this.maxClaimChunks = this.maxForceLoadChunks = 0;
               this.memberData.values().forEach(mx -> {
                  this.maxClaimChunks = this.maxClaimChunks + mx.getMaxClaims();
                  this.maxForceLoadChunks = this.maxForceLoadChunks + mx.getMaxForceLoads();
               });
               if (!this.memberData.isEmpty()) {
                  this.maxClaimChunks = this.maxClaimChunks / this.memberData.size();
                  this.maxForceLoadChunks = this.maxForceLoadChunks / this.memberData.size();
               }
         }
      }

      if ((Integer)FTBChunksWorldConfig.HARD_TEAM_CLAIM_LIMIT.get() > 0) {
         this.maxClaimChunks = Math.min(this.maxClaimChunks, (Integer)FTBChunksWorldConfig.HARD_TEAM_CLAIM_LIMIT.get());
      }

      if ((Integer)FTBChunksWorldConfig.HARD_TEAM_FORCE_LIMIT.get() > 0) {
         this.maxForceLoadChunks = Math.min(this.maxForceLoadChunks, (Integer)FTBChunksWorldConfig.HARD_TEAM_FORCE_LIMIT.get());
      }

      if (this.maxClaimChunks != prevMaxClaimed || this.maxForceLoadChunks != prevMaxForced) {
         SendGeneralDataPacket.send(this, this.getTeam().getOnlineMembers());
      }

      this.markDirty();
   }

   private void updateMemberLimitData(boolean onlinePlayersOnly) {
      Set<UUID> members = new HashSet<>(this.team.getMembers());

      for (ServerPlayer p : this.team.getOnlineMembers()) {
         Team playerTeam = (Team)this.getTeamManager().getPlayerTeamForPlayerID(p.getUUID()).orElse(null);
         TeamMemberData m = this.getTeamMemberData(p.getUUID());
         if (playerTeam != null) {
            ChunkTeamDataImpl personalData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(playerTeam);
            m.setMaxClaims(FTBChunksWorldConfig.getMaxClaimedChunks(personalData, p));
            m.setMaxForceLoads(FTBChunksWorldConfig.getMaxForceLoadedChunks(personalData, p));
         } else {
            m.setMaxClaims((Integer)FTBChunksWorldConfig.MAX_CLAIMED_CHUNKS.get());
            m.setMaxForceLoads((Integer)FTBChunksWorldConfig.MAX_CLAIMED_CHUNKS.get());
         }

         members.remove(p.getUUID());
      }

      if (!onlinePlayersOnly) {
         for (UUID id : members) {
            TeamMemberData m = this.getTeamMemberData(id);
            Team playerTeam = (Team)this.getTeamManager().getPlayerTeamForPlayerID(id).orElse(null);
            int maxF;
            int maxC;
            if (playerTeam != null) {
               ChunkTeamDataImpl personalData = ClaimedChunkManagerImpl.getInstance().getOrCreateData(playerTeam);
               maxC = (Integer)FTBChunksWorldConfig.MAX_CLAIMED_CHUNKS.get() + personalData.getExtraClaimChunks();
               maxF = (Integer)FTBChunksWorldConfig.MAX_FORCE_LOADED_CHUNKS.get() + personalData.getExtraForceLoadChunks();
            } else {
               maxC = (Integer)FTBChunksWorldConfig.MAX_CLAIMED_CHUNKS.get();
               maxF = (Integer)FTBChunksWorldConfig.MAX_FORCE_LOADED_CHUNKS.get();
            }

            m.setMaxClaims(maxC);
            m.setMaxForceLoads(maxF);
         }
      }

      this.markDirty();
   }

   public void addMemberData(ServerPlayer player, ChunkTeamDataImpl otherTeam) {
      if (otherTeam.team.isPlayerTeam()) {
         this.memberData.put(otherTeam.getTeamId(), TeamMemberData.fromPlayerData(player, otherTeam));
         this.markDirty();
      }
   }

   public void deleteMemberData(UUID playerId) {
      if (this.memberData.remove(playerId) != null) {
         this.markDirty();
      }
   }

   private boolean hasChunkChanged(ClaimedChunkImpl chunk) {
      String s = chunk == null ? "-" : chunk.getTeamData().getTeamId().toString();
      if (!this.lastChunkID.equals(s)) {
         this.lastChunkID = s;
         return true;
      } else {
         return false;
      }
   }

   public void checkForChunkChange(Player player, int chunkX, int chunkZ) {
      if (this.prevChunkX != chunkX || this.prevChunkZ != chunkZ) {
         ClaimedChunkImpl chunk = ClaimedChunkManagerImpl.getInstance().getChunk(new ChunkDimPos(player));
         if (this.hasChunkChanged(chunk)) {
            if (chunk != null) {
               player.displayClientMessage(chunk.getTeamData().getTeam().getColoredName(), true);
            } else {
               player.displayClientMessage(Component.translatable("wilderness").withStyle(ChatFormatting.DARK_GREEN), true);
            }
         }

         this.prevChunkX = chunkX;
         this.prevChunkZ = chunkZ;
      }
   }

   public void clearClaimCaches() {
      this.claimedChunkCache = null;
      this.forcedChunkCache = null;
   }

   @Override
   public void checkMemberForceLoading(UUID playerId) {
      if (this.isTeamMember(playerId)) {
         ServerPlayer player = this.manager.getMinecraftServer().getPlayerList().getPlayer(playerId);
         if (player != null && this.setForceLoadMember(playerId, FTBChunksWorldConfig.canPlayerOfflineForceload(player))) {
            this.updateLimits();
         }
      }
   }

   public void logPreventedAccess(ServerPlayer player, long when) {
      this.preventedAccess.put(player.getUUID(), new ChunkTeamDataImpl.PreventedAccess(player.getGameProfile().getName(), when));
      this.markDirty();
   }

   private void prunePreventedLog() {
      Set<UUID> toRemove = new HashSet<>();
      long now = System.currentTimeMillis();
      long max = ((Integer)FTBChunksWorldConfig.MAX_PREVENTED_LOG_AGE.get()).intValue() * 86400L * 1000L;
      this.preventedAccess.forEach((id, el) -> {
         if (now - el.when() > max) {
            toRemove.add(id);
         }
      });
      if (!toRemove.isEmpty()) {
         toRemove.forEach(this.preventedAccess::remove);
         this.markDirty();
      }
   }

   private record PreventedAccess(String name, long when) {
      public static final Codec<ChunkTeamDataImpl.PreventedAccess> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("name").forGetter(ChunkTeamDataImpl.PreventedAccess::name),
               Codec.LONG.fieldOf("when").forGetter(ChunkTeamDataImpl.PreventedAccess::when)
            )
            .apply(instance, ChunkTeamDataImpl.PreventedAccess::new)
      );
   }
}
