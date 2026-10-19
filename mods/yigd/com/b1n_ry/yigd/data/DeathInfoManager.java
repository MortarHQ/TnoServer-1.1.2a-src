package com.b1n_ry.yigd.data;

import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.components.RespawnComponent;
import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.util.GraveCompassHelper;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.NotNull;

public class DeathInfoManager extends SavedData {
   public static DeathInfoManager INSTANCE = new DeathInfoManager();
   private final Map<GameProfile, RespawnComponent> respawnEffects = new HashMap<>();
   private final Map<GameProfile, List<GraveComponent>> graveBackups = new HashMap<>();
   private final Map<UUID, GraveComponent> graveMap = new HashMap<>();
   private ListMode graveListMode = ListMode.BLACKLIST;
   private final Set<GameProfile> affectedPlayers = new HashSet<>();

   public void clear() {
      this.respawnEffects.clear();
      this.graveBackups.clear();
      this.graveMap.clear();
      this.affectedPlayers.clear();
   }

   public Set<GameProfile> getAffectedPlayers() {
      return this.affectedPlayers;
   }

   public static Factory<DeathInfoManager> getPersistentStateType(MinecraftServer server) {
      return new Factory(DeathInfoManager::new, (nbt, lookupRegistry) -> load(nbt, lookupRegistry, server), null);
   }

   public InteractionResult delete(UUID graveId) {
      GraveComponent component = this.graveMap.get(graveId);
      if (component == null) {
         return InteractionResult.FAIL;
      } else {
         GameProfile profile = component.getOwner().gameProfile();
         this.graveMap.remove(graveId);
         if (!this.graveBackups.containsKey(profile)) {
            return InteractionResult.PASS;
         } else {
            this.graveBackups.get(profile).remove(component);
            if (component.getStatus() != GraveStatus.UNCLAIMED) {
               return InteractionResult.SUCCESS;
            } else {
               return component.removeGraveBlock() ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
         }
      }
   }

   public void addRespawnComponent(ResolvableProfile profile, RespawnComponent component) {
      if (!this.respawnEffects.containsKey(profile.gameProfile())) {
         this.respawnEffects.put(profile.gameProfile(), component);
      }
   }

   public Optional<RespawnComponent> getRespawnComponent(ResolvableProfile profile) {
      return Optional.ofNullable(this.respawnEffects.get(profile.gameProfile()));
   }

   public Map<GameProfile, List<GraveComponent>> getPlayerGraves() {
      return this.graveBackups;
   }

   public void removeRespawnComponent(ResolvableProfile profile) {
      this.respawnEffects.remove(profile.gameProfile());
   }

   public void addBackup(ResolvableProfile p, GraveComponent component) {
      YigdConfig config = YigdConfig.getConfig();
      GameProfile profile = p.gameProfile();
      if (!this.graveBackups.containsKey(profile)) {
         this.graveBackups.put(profile, new ArrayList<>());
      }

      List<GraveComponent> playerGraves = this.graveBackups.get(profile);
      playerGraves.add(component);
      this.graveMap.put(component.getGraveId(), component);
      if (playerGraves.size() > config.graveConfig.maxBackupsPerPerson) {
         GraveComponent toBeRemoved = playerGraves.getFirst();
         this.delete(toBeRemoved.getGraveId());
         if (toBeRemoved.getStatus() == GraveStatus.UNCLAIMED && config.graveConfig.dropFromOldestWhenDeleted) {
            toBeRemoved.dropAllGraveItems();
         }
      }

      if (config.extraFeatures.graveCompass.pointToClosest != ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED
         && component.getStatus() == GraveStatus.UNCLAIMED) {
         GraveCompassHelper.addGravePosition(component.getWorldRegistryKey(), component.getPos(), profile.getId());
      }
   }

   @NotNull
   public List<GraveComponent> getBackupData(ResolvableProfile profile) {
      return this.graveBackups.computeIfAbsent(profile.gameProfile(), k -> new ArrayList<>());
   }

   public Optional<GraveComponent> getGrave(UUID graveId) {
      return Optional.ofNullable(this.graveMap.get(graveId));
   }

   public ListMode getGraveListMode() {
      return this.graveListMode;
   }

   public void setGraveListMode(ListMode listMode) {
      this.graveListMode = listMode;
   }

   public void addToList(ResolvableProfile profile) {
      this.affectedPlayers.add(profile.gameProfile());
   }

   public boolean removeFromList(ResolvableProfile profile) {
      return this.affectedPlayers.remove(profile.gameProfile());
   }

   public boolean isInList(ResolvableProfile profile) {
      return this.affectedPlayers.contains(profile.gameProfile());
   }

   @NotNull
   public CompoundTag save(@NotNull CompoundTag nbt, @NotNull Provider registryLookup) {
      ListTag respawnNbt = new ListTag();
      ListTag graveNbt = new ListTag();
      CompoundTag graveListNbt = new CompoundTag();

      for (Entry<GameProfile, RespawnComponent> entry : this.respawnEffects.entrySet()) {
         CompoundTag respawnCompound = new CompoundTag();
         ResolvableProfile.CODEC
            .encodeStart(NbtOps.INSTANCE, new ResolvableProfile(entry.getKey()))
            .result()
            .ifPresent(nbtElement -> respawnCompound.put("user", nbtElement));
         respawnCompound.put("component", entry.getValue().toNbt(registryLookup));
         respawnNbt.add(respawnCompound);
      }

      for (Entry<GameProfile, List<GraveComponent>> entry : this.graveBackups.entrySet()) {
         CompoundTag graveCompound = new CompoundTag();
         ResolvableProfile.CODEC
            .encodeStart(NbtOps.INSTANCE, new ResolvableProfile(entry.getKey()))
            .result()
            .ifPresent(nbtElement -> graveCompound.put("user", nbtElement));
         ListTag graveNbtList = new ListTag();

         for (GraveComponent graveComponent : entry.getValue()) {
            graveNbtList.add(graveComponent.toNbt(registryLookup));
         }

         graveCompound.put("graves", graveNbtList);
         graveNbt.add(graveCompound);
      }

      graveListNbt.putString("listMode", this.graveListMode.name());
      ListTag affectedPlayersNbt = new ListTag();

      for (GameProfile profile : this.affectedPlayers) {
         Tag profileNbt = (Tag)ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, new ResolvableProfile(profile)).result().orElseThrow();
         affectedPlayersNbt.add(profileNbt);
      }

      graveListNbt.put("affectedPlayers", affectedPlayersNbt);
      nbt.put("respawns", respawnNbt);
      nbt.put("graves", graveNbt);
      nbt.put("whitelist", graveListNbt);
      return nbt;
   }

   public static DeathInfoManager load(CompoundTag nbt, Provider lookupRegistry, MinecraftServer server) {
      INSTANCE.clear();
      ListTag respawnNbt = nbt.getList("respawns", 10);
      ListTag graveNbt = nbt.getList("graves", 10);

      for (Tag respawnElement : respawnNbt) {
         CompoundTag respawnCompound = (CompoundTag)respawnElement;
         INSTANCE.addRespawnComponent(
            (ResolvableProfile)ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, respawnCompound.get("user")).result().orElseThrow(),
            RespawnComponent.fromNbt(respawnCompound.getCompound("component"), lookupRegistry)
         );
      }

      for (Tag graveElement : graveNbt) {
         CompoundTag graveCompound = (CompoundTag)graveElement;
         ResolvableProfile user = (ResolvableProfile)ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, graveCompound.get("user")).result().orElseThrow();

         for (Tag grave : graveCompound.getList("graves", 10)) {
            GraveComponent component = GraveComponent.fromNbt((CompoundTag)grave, lookupRegistry, server);
            INSTANCE.addBackup(user, component);
            ServerLevel world = component.getWorld();
            if (world != null
               && world.areEntitiesLoaded(new ChunkPos(component.getPos()).toLong())
               && world.getBlockEntity(component.getPos()) instanceof GraveBlockEntity be
               && be.getGraveId() != null
               && be.getGraveId().equals(component.getGraveId())) {
               be.setComponent(component);
            }
         }
      }

      CompoundTag graveListNbt = nbt.getCompound("whitelist");
      ListMode listMode = ListMode.valueOf(graveListNbt.getString("listMode"));
      INSTANCE.setGraveListMode(listMode);

      for (Tag e : graveListNbt.getList("affectedPlayers", 9)) {
         ResolvableProfile profile = (ResolvableProfile)ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, e).result().orElseThrow();
         INSTANCE.addToList(profile);
      }

      return INSTANCE;
   }
}
