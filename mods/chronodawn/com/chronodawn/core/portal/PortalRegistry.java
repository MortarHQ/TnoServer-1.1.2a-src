package com.chronodawn.core.portal;

import com.chronodawn.ChronoDawn;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.data.PortalRegistryData;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class PortalRegistry {
   private static final PortalRegistry INSTANCE = new PortalRegistry();
   private final Map<UUID, PortalStateMachine> portals = new ConcurrentHashMap<>();
   private final Map<ResourceKey<Level>, Set<UUID>> portalsByDimension = new ConcurrentHashMap<>();
   private final Map<BlockPos, UUID> portalsByPosition = new ConcurrentHashMap<>();
   private final Map<ResourceKey<Level>, Set<UUID>> unmodifiableDimensionPortalCache = new ConcurrentHashMap<>();
   private PortalRegistryData savedData;

   private PortalRegistry() {
   }

   public static PortalRegistry getInstance() {
      return INSTANCE;
   }

   public void setSavedData(PortalRegistryData savedData) {
      this.savedData = savedData;
   }

   private void markDirty() {
      if (this.savedData != null) {
         this.savedData.savePortalRegistry();
      }
   }

   public void markDirtyForPortal(UUID portalId) {
      this.markDirty();
   }

   public void registerPortal(PortalStateMachine portal) {
      UUID portalId = portal.getPortalId();
      ResourceKey<Level> dimension = portal.getSourceDimension();
      BlockPos position = portal.getPosition();
      this.portals.put(portalId, portal);
      this.portalsByDimension.computeIfAbsent(dimension, k -> ConcurrentHashMap.newKeySet()).add(portalId);
      this.portalsByPosition.put(position, portalId);
      this.unmodifiableDimensionPortalCache.remove(dimension);
      this.markDirty();
      ChronoDawn.LOGGER.debug("Registered portal {} at {} in dimension {}", new Object[]{portalId, position, dimension.location()});
   }

   public void unregisterPortal(UUID portalId) {
      PortalStateMachine portal = this.portals.remove(portalId);
      if (portal != null) {
         ResourceKey<Level> dimension = portal.getSourceDimension();
         Set<UUID> dimensionPortals = this.portalsByDimension.get(dimension);
         if (dimensionPortals != null) {
            dimensionPortals.remove(portalId);
         }

         this.portalsByPosition.remove(portal.getPosition());
         this.unmodifiableDimensionPortalCache.remove(dimension);
         this.markDirty();
         ChronoDawn.LOGGER.debug("Unregistered portal {} from dimension {}", portalId, dimension.location());
      }
   }

   public PortalStateMachine getPortal(UUID portalId) {
      return this.portals.get(portalId);
   }

   public PortalStateMachine getPortalAt(BlockPos position) {
      UUID portalId = this.portalsByPosition.get(position);
      return portalId != null ? this.portals.get(portalId) : null;
   }

   public Set<UUID> getPortalsInDimension(ResourceKey<Level> dimension) {
      return this.unmodifiableDimensionPortalCache.computeIfAbsent(dimension, dim -> {
         Set<UUID> dimensionPortals = this.portalsByDimension.get(dim);
         if (dimensionPortals != null && !dimensionPortals.isEmpty()) {
            Set<UUID> copy = ConcurrentHashMap.newKeySet();
            copy.addAll(dimensionPortals);
            return Collections.unmodifiableSet(copy);
         } else {
            return Collections.emptySet();
         }
      });
   }

   public Collection<PortalStateMachine> getAllPortals() {
      return this.portals.values();
   }

   public void clear() {
      this.portals.clear();
      this.portalsByDimension.clear();
      this.portalsByPosition.clear();
      this.unmodifiableDimensionPortalCache.clear();
      ChronoDawn.LOGGER.debug("Cleared all portals from registry");
   }

   public void saveToNBT(CompoundTag tag) {
      ListTag portalList = new ListTag();

      for (PortalStateMachine portal : this.portals.values()) {
         CompoundTag portalTag = new CompoundTag();
         portalTag.putUUID("PortalId", portal.getPortalId());
         portalTag.putString("Dimension", portal.getSourceDimension().location().toString());
         portalTag.putLong("Position", portal.getPosition().asLong());
         portalTag.putString("State", portal.getCurrentState().name());
         portalList.add(portalTag);
      }

      tag.put("Portals", portalList);
      ChronoDawn.LOGGER.debug("Saved {} portals to NBT", this.portals.size());
   }

   public void loadFromNBT(CompoundTag tag) {
      this.clear();
      if (tag.contains("Portals", 9)) {
         ListTag portalList = tag.getList("Portals", 10);

         for (int i = 0; i < portalList.size(); i++) {
            CompoundTag portalTag = portalList.getCompound(i);
            UUID portalId = portalTag.getUUID("PortalId");
            ResourceLocation dimensionLoc = CompatResourceLocation.parse(portalTag.getString("Dimension"));
            ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, dimensionLoc);
            BlockPos position = BlockPos.of(portalTag.getLong("Position"));
            PortalState state = PortalState.valueOf(portalTag.getString("State"));
            PortalStateMachine portal = new PortalStateMachine(portalId, dimension, position);
            portal.setState(state);
            this.registerPortal(portal);
         }

         ChronoDawn.LOGGER.debug("Loaded {} portals from NBT", this.portals.size());
      }
   }
}
