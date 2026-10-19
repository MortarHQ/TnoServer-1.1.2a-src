package dev.ftb.mods.ftbchunks.client.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.api.client.waypoint.WaypointManager;
import dev.ftb.mods.ftbchunks.client.gui.WaypointEditorScreen;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class WaypointManagerImpl implements Iterable<WaypointImpl>, WaypointManager {
   private static final String WAYPOINTS_FILE = "waypoints.json";
   private final Set<WaypointImpl> waypoints = new HashSet<>();
   private final Set<WaypointImpl> deathpoints = new HashSet<>();
   private final MapDimension mapDimension;

   public WaypointManagerImpl(MapDimension mapDimension) {
      this.mapDimension = mapDimension;
   }

   public static WaypointManagerImpl fromJson(MapDimension mapDimension) {
      Path file = mapDimension.directory.resolve("waypoints.json");
      WaypointManagerImpl manager = new WaypointManagerImpl(mapDimension);
      if (Files.exists(file)) {
         try (Reader reader = Files.newBufferedReader(file)) {
            JsonObject json = (JsonObject)FTBChunks.GSON.fromJson(reader, JsonObject.class);
            if (json.has("waypoints")) {
               for (JsonElement e : json.get("waypoints").getAsJsonArray()) {
                  JsonObject o = e.getAsJsonObject();
                  WaypointType type = o.has("type") ? WaypointType.forId(o.get("type").getAsString()) : WaypointType.DEFAULT;
                  WaypointImpl wp = new WaypointImpl(type, mapDimension, new BlockPos(o.get("x").getAsInt(), o.get("y").getAsInt(), o.get("z").getAsInt()))
                     .setHidden(o.get("hidden").getAsBoolean())
                     .setName(o.get("name").getAsString())
                     .setColor(16777215);
                  if (o.has("color")) {
                     try {
                        wp.setColor(Integer.decode(o.get("color").getAsString()));
                     } catch (NumberFormatException var12) {
                     }
                  }

                  manager.add(wp);
                  wp.refreshIcon();
               }
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }

      return manager;
   }

   public static void writeJson(MapDimension mapDimension, List<WaypointImpl> waypoints) throws IOException {
      JsonObject json = new JsonObject();
      JsonArray waypointArray = new JsonArray();

      for (WaypointImpl w : waypoints) {
         if (!w.isTransient()) {
            JsonObject o = new JsonObject();
            o.addProperty("hidden", w.isHidden());
            o.addProperty("name", w.getName());
            o.addProperty("x", w.getPos().getX());
            o.addProperty("y", w.getPos().getY());
            o.addProperty("z", w.getPos().getZ());
            o.addProperty("color", String.format("#%06X", 16777215 & w.getColor()));
            o.addProperty("type", w.getType().getId());
            waypointArray.add(o);
         }
      }

      json.add("waypoints", waypointArray);

      try (Writer writer = Files.newBufferedWriter(mapDimension.directory.resolve("waypoints.json"))) {
         FTBChunks.GSON.toJson(json, writer);
      }
   }

   public void add(WaypointImpl waypoint) {
      if (this.waypoints.add(waypoint)) {
         if (waypoint.isDeathpoint()) {
            this.deathpoints.add(waypoint);
         }

         waypoint.refreshIcon();
         this.mapDimension.markDirty();
         FTBChunksAPI.clientApi().requestMinimapIconRefresh();
         WaypointEditorScreen.refreshIfOpen();
      }
   }

   public void remove(WaypointImpl waypoint) {
      if (this.waypoints.remove(waypoint)) {
         if (waypoint.isDeathpoint()) {
            this.deathpoints.remove(waypoint);
         }

         this.mapDimension.markDirty();
         FTBChunksAPI.clientApi().requestMinimapIconRefresh();
         WaypointEditorScreen.refreshIfOpen();
      }
   }

   public boolean removeIf(Predicate<WaypointImpl> predicate) {
      if (this.waypoints.removeIf(predicate)) {
         this.deathpoints.clear();
         this.deathpoints.addAll(this.waypoints.stream().filter(w -> w.getType() == WaypointType.DEATH).toList());
         this.mapDimension.markDirty();
         FTBChunksAPI.clientApi().requestMinimapIconRefresh();
         WaypointEditorScreen.refreshIfOpen();
         return true;
      } else {
         return false;
      }
   }

   public boolean isEmpty() {
      return this.waypoints.isEmpty();
   }

   @NotNull
   @Override
   public Iterator<WaypointImpl> iterator() {
      return this.waypoints.iterator();
   }

   public boolean hasDeathpoint() {
      return !this.deathpoints.isEmpty();
   }

   public Stream<WaypointImpl> stream() {
      return this.waypoints.stream();
   }

   @Override
   public Waypoint addWaypointAt(BlockPos pos, String name) {
      WaypointImpl waypoint = new WaypointImpl(WaypointType.DEFAULT, this.mapDimension, pos).setName(name);
      this.add(waypoint);
      return waypoint;
   }

   @Override
   public Waypoint addTransientWaypointAt(BlockPos pos, String name) {
      WaypointImpl waypoint = new WaypointImpl(WaypointType.DEFAULT, this.mapDimension, pos).setName(name).setTransient(true);
      this.add(waypoint);
      return waypoint;
   }

   @Override
   public boolean removeWaypointAt(BlockPos pos) {
      WaypointImpl impl = new WaypointImpl(WaypointType.DEFAULT, this.mapDimension, pos);
      if (this.waypoints.contains(impl)) {
         this.remove(impl);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean removeWaypoint(Waypoint waypoint) {
      return this.removeWaypointAt(waypoint.getPos());
   }

   @Override
   public Collection<Waypoint> getAllWaypoints() {
      return Collections.unmodifiableCollection(this.waypoints);
   }

   @Override
   public Optional<WaypointImpl> getNearestDeathpoint(Player player) {
      return this.deathpoints.isEmpty() ? Optional.empty() : this.deathpoints.stream().min(Comparator.comparingDouble(o -> o.getDistanceSq(player)));
   }
}
