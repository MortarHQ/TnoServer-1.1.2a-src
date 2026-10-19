package dev.ftb.mods.ftbchunks.api.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class MapIconEvent {
   public static final Event<Consumer<MapIconEvent>> MINIMAP = EventFactory.createLoop(new Consumer[0]);
   public static final Event<Consumer<MapIconEvent>> LARGE_MAP = EventFactory.createConsumerLoop(new MapIconEvent[0]);
   private final List<MapIcon> icons;
   private final MapType mapType;
   private final ResourceKey<Level> dimension;

   public MapIconEvent(ResourceKey<Level> dimension, List<MapIcon> icons, MapType mapType) {
      this.dimension = dimension;
      this.icons = icons;
      this.mapType = mapType;
   }

   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }

   public void add(MapIcon mapIcon) {
      this.icons.add(mapIcon);
   }

   public MapType getMapType() {
      return this.mapType;
   }
}
