package twilightforest.item.mapdata;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFStructures;
import twilightforest.network.MazeMapPacket;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class TFMazeMapData extends MapItemSavedData {
   private static final Map<String, TFMazeMapData> CLIENT_DATA = new HashMap<>();
   public int yCenter;
   public boolean ore;

   public TFMazeMapData(int x, int z, byte scale, boolean trackpos, boolean unlimited, boolean locked, ResourceKey<Level> dim) {
      super(x, z, scale, trackpos, unlimited, locked, dim);
   }

   public static TFMazeMapData load(CompoundTag nbt, Provider provider) {
      MapItemSavedData data = MapItemSavedData.load(nbt, provider);
      boolean trackingPosition = !nbt.contains("trackingPosition", 1) || nbt.getBoolean("trackingPosition");
      boolean unlimitedTracking = nbt.getBoolean("unlimitedTracking");
      boolean locked = nbt.getBoolean("locked");
      TFMazeMapData tfdata = new TFMazeMapData(data.centerX, data.centerZ, data.scale, trackingPosition, unlimitedTracking, locked, data.dimension);
      tfdata.colors = data.colors;
      tfdata.bannerMarkers.putAll(data.bannerMarkers);
      tfdata.decorations.putAll(data.decorations);
      tfdata.frameMarkers.putAll(data.frameMarkers);
      tfdata.trackedDecorationCount = data.trackedDecorationCount;
      tfdata.yCenter = nbt.getInt("yCenter");
      tfdata.ore = nbt.getBoolean("mapOres");
      return tfdata;
   }

   public CompoundTag save(CompoundTag nbt, Provider provider) {
      CompoundTag ret = super.save(nbt, provider);
      ret.putInt("yCenter", this.yCenter);
      ret.putBoolean("mapOres", this.ore);
      return ret;
   }

   public void calculateMapCenter(Level world, int x, int y, int z) {
      this.yCenter = y;
      if (world instanceof ServerLevel level && LegacyLandmarkPlacements.pickLandmarkForChunk(x >> 4, z >> 4, level) == TFStructures.LABYRINTH) {
         BlockPos mc = LegacyLandmarkPlacements.getNearestCenterXZ(x >> 4, z >> 4);
         this.centerX = mc.getX();
         this.centerZ = mc.getZ();
      }
   }

   @Nullable
   public static TFMazeMapData getMazeMapData(Level level, String name) {
      return level.isClientSide() ? CLIENT_DATA.get(name) : (TFMazeMapData)((ServerLevel)level).getServer().overworld().getDataStorage().get(factory(), name);
   }

   @Nullable
   public static TFMazeMapData getClientMagicMapData(String name) {
      return CLIENT_DATA.get(name);
   }

   public static Factory<MapItemSavedData> factory() {
      return new Factory(() -> {
         throw new IllegalStateException("Should never create an empty map saved data");
      }, TFMazeMapData::load, DataFixTypes.SAVED_DATA_MAP_DATA);
   }

   public static void registerMazeMapData(Level level, TFMazeMapData data, String id) {
      if (level.isClientSide()) {
         CLIENT_DATA.put(id, data);
      } else {
         ((ServerLevel)level).getServer().overworld().getDataStorage().set(id, data);
      }
   }

   @Nullable
   public Packet<?> getUpdatePacket(MapId mapId, Player player) {
      Packet<?> packet = super.getUpdatePacket(mapId, player);
      return (Packet<?>)(packet instanceof ClientboundMapItemDataPacket mapItemDataPacket
         ? new MazeMapPacket(mapItemDataPacket, this.ore, this.yCenter).toVanillaClientbound()
         : packet);
   }
}
