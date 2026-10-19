package net.blay09.mods.waystones.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneManager;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.event.WaystoneInitializedEvent;
import net.blay09.mods.waystones.api.event.WaystoneRemovedEvent;
import net.blay09.mods.waystones.api.event.WaystoneUpdatedEvent;
import net.blay09.mods.waystones.api.event.WaystonesLoadedEvent;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.Nullable;

public class WaystoneManagerImpl extends SavedData implements WaystoneManager {
   private static final String DATA_NAME = "waystones";
   private static final String TAG_WAYSTONES = "Waystones";
   private static final WaystoneManagerImpl clientStorageCopy = new WaystoneManagerImpl();
   private final Map<UUID, Waystone> waystones = new HashMap<>();

   public void addWaystone(Waystone waystone) {
      this.waystones.put(waystone.getWaystoneUid(), waystone);
      this.setDirty();
      Balm.getEvents().fireEvent(new WaystoneInitializedEvent(waystone));
   }

   public void updateWaystone(Waystone waystone) {
      WaystoneImpl mutableWaystone = (WaystoneImpl)this.waystones.getOrDefault(waystone.getWaystoneUid(), waystone);
      mutableWaystone.setName(waystone.getName());
      mutableWaystone.setVisibility(waystone.getVisibility());
      this.waystones.put(waystone.getWaystoneUid(), mutableWaystone);
      this.setDirty();
      Balm.getEvents().fireEvent(new WaystoneUpdatedEvent(waystone));
   }

   public void removeWaystone(Waystone waystone) {
      this.waystones.remove(waystone.getWaystoneUid());
      this.setDirty();
      Balm.getEvents().fireEvent(new WaystoneRemovedEvent(waystone));
   }

   @Override
   public Optional<Waystone> getWaystoneAt(BlockGetter world, BlockPos pos) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      return blockEntity instanceof WaystoneBlockEntityBase ? Optional.of(((WaystoneBlockEntityBase)blockEntity).getWaystone()) : Optional.empty();
   }

   @Override
   public Optional<Waystone> getWaystoneById(UUID waystoneUid) {
      return Optional.ofNullable(this.waystones.get(waystoneUid));
   }

   @Override
   public Optional<Waystone> findWaystoneByName(String name) {
      return this.waystones.values().stream().filter(it -> it.getName().getString().equals(name)).findFirst();
   }

   @Override
   public Stream<Waystone> getWaystones() {
      return this.waystones.values().stream();
   }

   @Override
   public Stream<Waystone> getWaystonesByType(ResourceLocation type) {
      return this.waystones.values().stream().filter(it -> it.getWaystoneType().equals(type));
   }

   @Override
   public List<Waystone> getGlobalWaystones() {
      return this.waystones.values().stream().filter(it -> it.getVisibility() == WaystoneVisibility.GLOBAL).collect(Collectors.toList());
   }

   public static WaystoneManagerImpl read(CompoundTag tagCompound, Provider provider) {
      WaystoneManagerImpl waystoneManager = new WaystoneManagerImpl();

      for (Tag tag : tagCompound.getList("Waystones", 10)) {
         CompoundTag compound = (CompoundTag)tag;
         Waystone waystone = WaystoneImpl.read(compound, provider);
         if (waystone.hasName() || !WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
            waystoneManager.waystones.put(waystone.getWaystoneUid(), waystone);
         }
      }

      Balm.getEvents().fireEvent(new WaystonesLoadedEvent(waystoneManager));
      return waystoneManager;
   }

   public CompoundTag save(CompoundTag tagCompound, Provider provider) {
      ListTag tagList = new ListTag();

      for (Waystone waystone : this.waystones.values()) {
         tagList.add(WaystoneImpl.write(waystone, new CompoundTag(), provider));
      }

      tagCompound.put("Waystones", tagList);
      return tagCompound;
   }

   public static WaystoneManagerImpl get(@Nullable MinecraftServer server) {
      if (server != null) {
         ServerLevel overworld = server.getLevel(Level.OVERWORLD);
         return (WaystoneManagerImpl)Objects.requireNonNull(overworld)
            .getDataStorage()
            .computeIfAbsent(new Factory(WaystoneManagerImpl::new, WaystoneManagerImpl::read, DataFixTypes.SAVED_DATA_MAP_DATA), "waystones");
      } else {
         return clientStorageCopy;
      }
   }
}
