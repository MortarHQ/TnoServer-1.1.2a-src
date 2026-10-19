package net.blay09.mods.waystones.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PersistentPlayerWaystoneData implements IPlayerWaystoneData {
   private static final String TAG_NAME = "WaystonesData";
   private static final String ACTIVATED_WAYSTONES = "Waystones";
   private static final String SORTING_INDEX = "SortingIndex";
   private static final String COOLDOWNS = "Cooldowns";

   @Override
   public void activateWaystone(Player player, Waystone waystone) {
      ListTag activatedWaystonesData = getActivatedWaystonesData(getWaystonesData(player));
      activatedWaystonesData.add(StringTag.valueOf(waystone.getWaystoneUid().toString()));
   }

   @Override
   public boolean isWaystoneActivated(Player player, Waystone waystone) {
      ListTag activatedWaystones = getActivatedWaystonesData(getWaystonesData(player));
      String waystoneUid = waystone.getWaystoneUid().toString();

      for (Tag activatedWaystone : activatedWaystones) {
         if (waystoneUid.equals(activatedWaystone.getAsString())) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Collection<Waystone> getWaystones(Player player) {
      ListTag activatedWaystonesTag = getActivatedWaystonesData(getWaystonesData(player));
      ArrayList<Waystone> waystones = new ArrayList<>();
      Iterator<Tag> iterator = activatedWaystonesTag.iterator();

      while (iterator.hasNext()) {
         Tag activatedWaystoneTag = iterator.next();
         WaystoneProxy proxy = new WaystoneProxy(player.getServer(), UUID.fromString(activatedWaystoneTag.getAsString()));
         if (proxy.isValid()) {
            waystones.add(proxy);
         } else {
            iterator.remove();
         }
      }

      return waystones;
   }

   @Override
   public List<UUID> getSortingIndex(Player player) {
      ListTag sortingIndex = getSortingIndexData(getWaystonesData(player));
      return sortingIndex.stream().map(entry -> UUID.fromString(entry.getAsString())).toList();
   }

   @Override
   public void setSortingIndex(Player player, List<UUID> sortingIndex) {
      ListTag sortingIndexData = getSortingIndexData(getWaystonesData(player));
      sortingIndexData.clear();

      for (UUID waystoneUid : sortingIndex) {
         sortingIndexData.add(StringTag.valueOf(waystoneUid.toString()));
      }
   }

   @Override
   public List<UUID> ensureSortingIndex(Player player, Collection<Waystone> waystones) {
      ListTag sortingIndexData = getSortingIndexData(getWaystonesData(player));
      ArrayList<UUID> sortingIndex = new ArrayList<>();
      HashSet<UUID> existing = new HashSet<>();

      for (Tag sortingIndexEntry : sortingIndexData) {
         UUID waystoneUid = UUID.fromString(sortingIndexEntry.getAsString());
         if (existing.add(waystoneUid)) {
            sortingIndex.add(waystoneUid);
         }
      }

      for (Waystone waystone : waystones) {
         UUID waystoneUid = waystone.getWaystoneUid();
         if (!existing.contains(waystoneUid)) {
            sortingIndex.add(waystoneUid);
            sortingIndexData.add(StringTag.valueOf(waystoneUid.toString()));
         }
      }

      return sortingIndex;
   }

   @Override
   public void sortWaystoneAsFirst(Player player, UUID waystoneUid) {
      ListTag sortingIndex = getSortingIndexData(getWaystonesData(player));

      for (int i = 0; i < sortingIndex.size(); i++) {
         Tag sortingIndexEntry = sortingIndex.get(i);
         if (waystoneUid.toString().equals(sortingIndexEntry.getAsString())) {
            sortingIndex.remove(i);
            sortingIndex.add(0, sortingIndexEntry);
            break;
         }
      }
   }

   @Override
   public void sortWaystoneAsLast(Player player, UUID waystoneUid) {
      ListTag sortingIndex = getSortingIndexData(getWaystonesData(player));

      for (int i = 0; i < sortingIndex.size(); i++) {
         Tag sortingIndexEntry = sortingIndex.get(i);
         if (waystoneUid.toString().equals(sortingIndexEntry.getAsString())) {
            sortingIndex.remove(i);
            sortingIndex.add(sortingIndexEntry);
            break;
         }
      }
   }

   @Override
   public void sortWaystoneSwap(Player player, UUID waystoneUid, UUID otherWaystoneUid) {
      ListTag sortingIndex = getSortingIndexData(getWaystonesData(player));
      int waystoneIndex = -1;
      int otherWaystoneIndex = -1;

      for (int i = 0; i < sortingIndex.size(); i++) {
         Tag sortingIndexEntry = sortingIndex.get(i);
         if (waystoneUid.toString().equals(sortingIndexEntry.getAsString())) {
            waystoneIndex = i;
         } else if (otherWaystoneUid.toString().equals(sortingIndexEntry.getAsString())) {
            otherWaystoneIndex = i;
         }
      }

      if (waystoneIndex != -1 && otherWaystoneIndex != -1) {
         Collections.swap(sortingIndex, waystoneIndex, otherWaystoneIndex);
      }
   }

   @Override
   public void deactivateWaystone(Player player, Waystone waystone) {
      CompoundTag data = getWaystonesData(player);
      ListTag activatedWaystones = getActivatedWaystonesData(data);
      String waystoneUid = waystone.getWaystoneUid().toString();

      for (int i = activatedWaystones.size() - 1; i >= 0; i--) {
         Tag activatedWaystone = activatedWaystones.get(i);
         if (waystoneUid.equals(activatedWaystone.getAsString())) {
            activatedWaystones.remove(i);
            break;
         }
      }
   }

   @Override
   public Map<ResourceLocation, Long> getCooldowns(Player player) {
      CompoundTag waystonesData = getWaystonesData(player);
      CompoundTag cooldowns = waystonesData.getCompound("Cooldowns");
      HashMap<ResourceLocation, Long> cooldownMap = new HashMap<>();

      for (String key : cooldowns.getAllKeys()) {
         cooldownMap.put(ResourceLocation.parse(key), cooldowns.getLong(key));
      }

      return cooldownMap;
   }

   @Override
   public void resetCooldowns(Player player) {
      CompoundTag waystonesData = getWaystonesData(player);
      waystonesData.put("Cooldowns", new CompoundTag());
   }

   @Override
   public long getCooldownUntil(Player player, ResourceLocation key) {
      CompoundTag waystonesData = getWaystonesData(player);
      CompoundTag cooldowns = waystonesData.getCompound("Cooldowns");
      return cooldowns.getLong(key.toString());
   }

   @Override
   public void setCooldownUntil(Player player, ResourceLocation key, long timeStamp) {
      CompoundTag waystonesData = getWaystonesData(player);
      CompoundTag cooldowns = waystonesData.getCompound("Cooldowns");
      cooldowns.putLong(key.toString(), timeStamp);
      waystonesData.put("Cooldowns", cooldowns);
   }

   private static ListTag getActivatedWaystonesData(CompoundTag data) {
      ListTag list = data.getList("Waystones", 8);
      data.put("Waystones", list);
      return list;
   }

   private static ListTag getSortingIndexData(CompoundTag data) {
      ListTag list = data.contains("SortingIndex") ? data.getList("SortingIndex", 8) : createSortingIndexFromLegacy(data);
      data.put("SortingIndex", list);
      return list;
   }

   private static CompoundTag getWaystonesData(Player player) {
      CompoundTag persistedData = Balm.getHooks().getPersistentData(player);
      CompoundTag compound = persistedData.getCompound("WaystonesData");
      persistedData.put("WaystonesData", compound);
      return compound;
   }

   private static ListTag createSortingIndexFromLegacy(CompoundTag data) {
      ListTag activatedWaystones = getActivatedWaystonesData(data);
      if (activatedWaystones.isEmpty()) {
         return new ListTag();
      }

      ListTag sortingIndex = new ListTag();
      sortingIndex.addAll(activatedWaystones);
      return sortingIndex;
   }

   @Override
   public Optional<Waystone> findWaystoneByName(Player player, String name) {
      return WaystoneManagerImpl.get(player.getServer()).findWaystoneByName(name);
   }
}
