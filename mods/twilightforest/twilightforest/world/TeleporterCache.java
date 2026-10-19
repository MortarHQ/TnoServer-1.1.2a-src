package twilightforest.world;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.Nullable;

public class TeleporterCache extends SavedData {
   private final Map<ResourceLocation, Map<ColumnPos, TFTeleporter.PortalPosition>> destinationCoordinateCache = new HashMap<>();

   private TeleporterCache() {
      this.setDirty();
   }

   public static TeleporterCache get(ServerLevel level) {
      ServerLevel server = level.getServer().overworld();
      DimensionDataStorage storage = server.getDataStorage();
      return (TeleporterCache)storage.computeIfAbsent(factory(), "twilightforest_teleporter_cache");
   }

   public static Factory<TeleporterCache> factory() {
      return new Factory(TeleporterCache::new, TeleporterCache::load, null);
   }

   public void addBlockToCache(ResourceLocation dimension, ColumnPos columnPos, TFTeleporter.PortalPosition position) {
      this.destinationCoordinateCache.putIfAbsent(dimension, Maps.newHashMapWithExpectedSize(4096));
      this.destinationCoordinateCache.get(dimension).put(columnPos, position);
      this.setDirty();
   }

   @Nullable
   public TFTeleporter.PortalPosition getPortalPosition(ResourceLocation dimension, ColumnPos pos) {
      return this.destinationCoordinateCache.containsKey(dimension) ? this.destinationCoordinateCache.get(dimension).get(pos) : null;
   }

   public void removeInvalidPos(ResourceLocation dimension, ColumnPos pos) {
      this.destinationCoordinateCache.get(dimension).remove(pos);
      this.setDirty();
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag dcc = new ListTag();
      this.destinationCoordinateCache.forEach((rl, map) -> {
         CompoundTag ct = new CompoundTag();
         ListTag links = new ListTag();
         map.forEach((columnPos, portalPos) -> {
            CompoundTag link = new CompoundTag();
            CompoundTag column = new CompoundTag();
            column.putInt("x", columnPos.x());
            column.putInt("z", columnPos.z());
            link.put("column", column);
            CompoundTag portal = new CompoundTag();
            portal.putLong("time", portalPos.lastUpdateTime);
            portal.putLong("pos", portalPos.pos.asLong());
            link.put("portal", portal);
            links.add(link);
         });
         ct.put("links", links);
         ct.putString("name", rl.toString());
         dcc.add(ct);
      });
      tag.put("dest", dcc);
      return tag;
   }

   public static TeleporterCache load(CompoundTag tag, Provider provider) {
      TeleporterCache cache = new TeleporterCache();
      tag.getList("dest", 10)
         .stream()
         .map(CompoundTag.class::cast)
         .forEach(
            dest -> {
               ResourceLocation name = ResourceLocation.parse(dest.getString("name"));
               cache.destinationCoordinateCache.putIfAbsent(name, Maps.newHashMapWithExpectedSize(4096));
               dest.getList("links", 10)
                  .stream()
                  .map(CompoundTag.class::cast)
                  .forEach(
                     link -> {
                        CompoundTag column = link.getCompound("column");
                        CompoundTag portal = link.getCompound("portal");
                        cache.destinationCoordinateCache
                           .get(name)
                           .put(
                              new ColumnPos(column.getInt("x"), column.getInt("z")),
                              new TFTeleporter.PortalPosition(BlockPos.of(portal.getLong("pos")), portal.getLong("time"))
                           );
                     }
                  );
            }
         );
      return cache;
   }
}
