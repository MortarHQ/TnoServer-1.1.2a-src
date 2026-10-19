package net.blay09.mods.waystones.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public interface IPlayerWaystoneData {
   void activateWaystone(Player var1, Waystone var2);

   boolean isWaystoneActivated(Player var1, Waystone var2);

   void deactivateWaystone(Player var1, Waystone var2);

   long getCooldownUntil(Player var1, ResourceLocation var2);

   void setCooldownUntil(Player var1, ResourceLocation var2, long var3);

   List<UUID> getSortingIndex(Player var1);

   List<UUID> ensureSortingIndex(Player var1, Collection<Waystone> var2);

   void setSortingIndex(Player var1, List<UUID> var2);

   Collection<Waystone> getWaystones(Player var1);

   void sortWaystoneAsFirst(Player var1, UUID var2);

   void sortWaystoneAsLast(Player var1, UUID var2);

   void sortWaystoneSwap(Player var1, UUID var2, UUID var3);

   Map<ResourceLocation, Long> getCooldowns(Player var1);

   void resetCooldowns(Player var1);

   Optional<Waystone> findWaystoneByName(Player var1, String var2);
}
