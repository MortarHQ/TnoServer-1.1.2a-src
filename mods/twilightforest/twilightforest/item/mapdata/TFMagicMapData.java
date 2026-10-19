package twilightforest.item.mapdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.item.MagicMapItem;
import twilightforest.network.MagicMapPacket;
import twilightforest.util.Codecs;

public class TFMagicMapData extends MapItemSavedData {
   private static final Map<String, TFMagicMapData> CLIENT_DATA = new HashMap<>();
   public final List<String> conqueredStructures = new ArrayList<>();

   public TFMagicMapData(int x, int z, byte scale, boolean trackpos, boolean unlimited, boolean locked, ResourceKey<Level> dim) {
      super(x, z, scale, trackpos, unlimited, locked, dim);
   }

   public static TFMagicMapData load(CompoundTag nbt, Provider provider) {
      MapItemSavedData data = MapItemSavedData.load(nbt, provider);
      boolean trackingPosition = !nbt.contains("trackingPosition", 1) || nbt.getBoolean("trackingPosition");
      boolean unlimitedTracking = nbt.getBoolean("unlimitedTracking");
      boolean locked = nbt.getBoolean("locked");
      TFMagicMapData tfdata = new TFMagicMapData(data.centerX, data.centerZ, data.scale, trackingPosition, unlimitedTracking, locked, data.dimension);
      tfdata.colors = data.colors;
      tfdata.bannerMarkers.putAll(data.bannerMarkers);
      tfdata.frameMarkers.putAll(data.frameMarkers);

      for (TFMagicMapData.DecorationHolder decoration : TFMagicMapData.DecorationHolder.CODEC
         .listOf()
         .parse(provider.createSerializationContext(NbtOps.INSTANCE), nbt.get("decorations"))
         .resultOrPartial(error -> TwilightForestMod.LOGGER.warn("Failed to parse map decoration: '{}'", error))
         .orElse(List.of())) {
         MapDecoration mapdecoration1 = decoration.decoration();
         MapDecoration mapdecoration = tfdata.decorations.put(decoration.id(), mapdecoration1);
         if (!mapdecoration1.equals(mapdecoration)) {
            if (mapdecoration != null && ((MapDecorationType)mapdecoration.type().value()).trackCount()) {
               tfdata.trackedDecorationCount--;
            }

            if (((MapDecorationType)decoration.decoration().type().value()).trackCount()) {
               tfdata.trackedDecorationCount++;
            }

            tfdata.setDecorationsDirty();
         }
      }

      if (nbt.contains("conquered_structures", 9)) {
         tfdata.conqueredStructures.clear();
         ListTag tag = nbt.getList("conquered_structures", 8);
         tag.forEach(tag1 -> tfdata.conqueredStructures.add(tag1.getAsString()));
      }

      return tfdata;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      tag = super.save(tag, provider);
      List<TFMagicMapData.DecorationHolder> holders = new ArrayList<>();
      this.decorations.forEach((s, decoration) -> {
         if (((MapDecorationType)decoration.type().value()).showOnItemFrame()) {
            holders.add(new TFMagicMapData.DecorationHolder(s, decoration));
         }
      });
      tag.put("decorations", (Tag)TFMagicMapData.DecorationHolder.CODEC.listOf().encodeStart(NbtOps.INSTANCE, holders).getOrThrow());
      if (!this.conqueredStructures.isEmpty()) {
         ListTag conqueredTag = new ListTag();

         for (String structure : this.conqueredStructures) {
            conqueredTag.add(StringTag.valueOf(structure));
         }

         tag.put("conquered_structures", conqueredTag);
      }

      return tag;
   }

   @Nullable
   public static TFMagicMapData getMagicMapData(Level level, String name) {
      return level instanceof ServerLevel serverLevel
         ? (TFMagicMapData)serverLevel.getServer().overworld().getDataStorage().get(factory(), name)
         : CLIENT_DATA.get(name);
   }

   @Nullable
   public static TFMagicMapData getClientMagicMapData(String name) {
      return CLIENT_DATA.get(name);
   }

   public static void registerMagicMapData(Level level, TFMagicMapData data, String id) {
      if (level instanceof ServerLevel serverLevel) {
         serverLevel.getServer().overworld().getDataStorage().set(id, data);
      } else {
         CLIENT_DATA.put(id, data);
      }
   }

   public static Factory<MapItemSavedData> factory() {
      return new Factory(() -> {
         throw new IllegalStateException("Should never create an empty map saved data");
      }, TFMagicMapData::load, DataFixTypes.SAVED_DATA_MAP_DATA);
   }

   @Nullable
   public Packet<?> getUpdatePacket(MapId mapId, Player player) {
      Packet<?> packet = super.getUpdatePacket(mapId, player);
      return (Packet<?>)(packet instanceof ClientboundMapItemDataPacket mapItemDataPacket
         ? new MagicMapPacket(mapItemDataPacket, this.conqueredStructures).toVanillaClientbound()
         : packet);
   }

   public void addTFDecoration(
      Holder<MapDecorationType> decorationType, @Nullable LevelAccessor level, String id, double x, double z, double yRot, boolean conquered
   ) {
      this.addDecoration(decorationType, level, id, x, z, yRot, null);
      MapDecoration deco = (MapDecoration)this.decorations.get(id);
      if (deco != null) {
         String conqueredID = MagicMapItem.makeName(decorationType, deco.x(), deco.y());
         if (conquered && !this.conqueredStructures.contains(conqueredID)) {
            this.conqueredStructures.add(conqueredID);
         } else if (!conquered) {
            this.conqueredStructures.remove(conqueredID);
         }
      }
   }

   public record DecorationHolder(String id, MapDecoration decoration) {
      public static final Codec<TFMagicMapData.DecorationHolder> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.STRING.fieldOf("id").forGetter(TFMagicMapData.DecorationHolder::id),
               Codecs.DECORATION_CODEC.fieldOf("decoration").forGetter(TFMagicMapData.DecorationHolder::decoration)
            )
            .apply(instance, TFMagicMapData.DecorationHolder::new)
      );
   }
}
