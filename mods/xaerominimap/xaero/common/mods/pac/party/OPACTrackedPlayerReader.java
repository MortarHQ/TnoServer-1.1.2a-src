package xaero.common.mods.pac.party;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import xaero.hud.minimap.player.tracker.system.ITrackedPlayerReader;
import xaero.pac.common.parties.party.api.IPartyMemberDynamicInfoSyncableAPI;

public class OPACTrackedPlayerReader implements ITrackedPlayerReader<IPartyMemberDynamicInfoSyncableAPI> {
   private final Map<ResourceLocation, ResourceKey<Level>> dimensionKeyCache = new HashMap<>();

   public UUID getId(IPartyMemberDynamicInfoSyncableAPI player) {
      return player.getPlayerId();
   }

   public double getX(IPartyMemberDynamicInfoSyncableAPI player) {
      return player.getX();
   }

   public double getY(IPartyMemberDynamicInfoSyncableAPI player) {
      return player.getY();
   }

   public double getZ(IPartyMemberDynamicInfoSyncableAPI player) {
      return player.getZ();
   }

   public ResourceKey<Level> getDimension(IPartyMemberDynamicInfoSyncableAPI player) {
      if (player.getDimension() == null) {
         return null;
      }

      ResourceKey<Level> result = this.dimensionKeyCache.get(player.getDimension());
      if (result == null) {
         this.dimensionKeyCache.put(player.getDimension(), result = ResourceKey.create(Registries.DIMENSION, player.getDimension()));
      }

      return result;
   }
}
