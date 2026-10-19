package net.blay09.mods.waystones.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class InMemoryPlayerWaystoneData implements IPlayerWaystoneData {
   private final List<UUID> sortingIndex = new ArrayList<>();
   private final Map<UUID, Waystone> waystones = new HashMap<>();
   private final Map<ResourceLocation, Long> cooldowns = new HashMap<>();

   @Override
   public void activateWaystone(Player player, Waystone waystone) {
      this.waystones.put(waystone.getWaystoneUid(), waystone);
      this.sortingIndex.add(waystone.getWaystoneUid());
   }

   @Override
   public boolean isWaystoneActivated(Player player, Waystone waystone) {
      return this.waystones.containsKey(waystone.getWaystoneUid());
   }

   @Override
   public void deactivateWaystone(Player player, Waystone waystone) {
      this.waystones.remove(waystone.getWaystoneUid());
      this.sortingIndex.remove(waystone.getWaystoneUid());
   }

   @Override
   public Map<ResourceLocation, Long> getCooldowns(Player player) {
      return this.cooldowns;
   }

   @Override
   public void resetCooldowns(Player player) {
      this.cooldowns.clear();
   }

   @Override
   public long getCooldownUntil(Player player, ResourceLocation key) {
      return this.cooldowns.getOrDefault(key, 0L);
   }

   @Override
   public void setCooldownUntil(Player player, ResourceLocation key, long timeStamp) {
      this.cooldowns.put(key, timeStamp);
   }

   @Override
   public Collection<Waystone> getWaystones(Player player) {
      return this.waystones.values();
   }

   @Override
   public void sortWaystoneAsFirst(Player player, UUID waystoneUid) {
      this.sortingIndex.remove(waystoneUid);
      this.sortingIndex.add(0, waystoneUid);
   }

   @Override
   public void sortWaystoneAsLast(Player player, UUID waystoneUid) {
      this.sortingIndex.remove(waystoneUid);
      this.sortingIndex.add(waystoneUid);
   }

   @Override
   public void sortWaystoneSwap(Player player, UUID waystoneUid, UUID otherWaystoneUid) {
      int waystoneIndex = this.sortingIndex.indexOf(waystoneUid);
      int otherWaystoneIndex = this.sortingIndex.indexOf(otherWaystoneUid);
      if (waystoneIndex != -1 && otherWaystoneIndex != -1) {
         Collections.swap(this.sortingIndex, waystoneIndex, otherWaystoneIndex);
      }
   }

   @Override
   public List<UUID> getSortingIndex(Player player) {
      return this.sortingIndex;
   }

   @Override
   public List<UUID> ensureSortingIndex(Player player, Collection<Waystone> waystones) {
      HashSet<UUID> existing = new HashSet<>(this.sortingIndex);

      for (Waystone waystone : waystones) {
         UUID waystoneUid = waystone.getWaystoneUid();
         if (!existing.contains(waystoneUid)) {
            this.sortingIndex.add(waystoneUid);
         }
      }

      return this.sortingIndex;
   }

   @Override
   public void setSortingIndex(Player player, List<UUID> sortingIndex) {
      this.sortingIndex.clear();
      this.sortingIndex.addAll(sortingIndex);
   }

   public void setWaystones(Collection<Waystone> waystones) {
      this.waystones.clear();

      for (Waystone waystone : waystones) {
         this.waystones.put(waystone.getWaystoneUid(), waystone);
      }
   }

   @Override
   public Optional<Waystone> findWaystoneByName(Player player, String name) {
      return this.waystones.values().stream().filter(it -> it.getName().getString().equals(name)).findFirst();
   }
}
