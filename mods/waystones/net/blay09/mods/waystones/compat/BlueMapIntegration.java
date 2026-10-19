package net.blay09.mods.waystones.compat;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.bluecolored.bluemap.api.markers.POIMarker.Builder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.event.WaystoneInitializedEvent;
import net.blay09.mods.waystones.api.event.WaystoneRemovedEvent;
import net.blay09.mods.waystones.api.event.WaystoneUpdatedEvent;
import net.blay09.mods.waystones.api.event.WaystonesLoadedEvent;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class BlueMapIntegration {
   private final Map<ResourceKey<Level>, BlueMapIntegration.LevelMarkers> levelMarkersByDimension = new HashMap<>();
   private BlueMapAPI api;

   public BlueMapIntegration() {
      BlueMapAPI.onEnable(api -> {
         this.api = api;
         if (this.isEnabled()) {
            for (BlueMapIntegration.LevelMarkers levelMarkers : this.levelMarkersByDimension.values()) {
               levelMarkers.update(api);
            }
         }
      });
      BlueMapAPI.onDisable(api -> this.api = null);
      Balm.getEvents().onEvent(WaystonesLoadedEvent.class, this::onWaystonesLoaded);
      Balm.getEvents().onEvent(WaystoneInitializedEvent.class, this::onWaystoneInitialized);
      Balm.getEvents().onEvent(WaystoneUpdatedEvent.class, this::onWaystoneUpdated);
      Balm.getEvents().onEvent(WaystoneRemovedEvent.class, this::onWaystoneRemoved);
   }

   public static String getMarkerId(Waystone waystone) {
      return waystone.getWaystoneUid().toString();
   }

   public static POIMarker createWaystoneMarker(Waystone waystone) {
      return ((Builder)((Builder)((Builder)POIMarker.builder().label(waystone.getName().getString()))
               .position(waystone.getPos().getX(), waystone.getPos().getY(), waystone.getPos().getZ()))
            .maxDistance(1000.0))
         .build();
   }

   private static boolean isSupportedWaystone(Waystone waystone) {
      return isSupportedWaystoneType(waystone.getWaystoneType()) && !waystone.isTransient();
   }

   private static boolean isSupportedWaystoneType(ResourceLocation waystoneType) {
      WaystonesConfigData.BlueMap config = WaystonesConfig.getActive().blueMap;
      if (!waystoneType.equals(WaystoneTypes.WAYSTONE)) {
         return WaystoneTypes.isSharestone(waystoneType) ? config.includeSharestones : false;
      } else {
         return config.includeWaystones || config.includeUndiscoveredWaystones;
      }
   }

   public boolean isEnabled() {
      return WaystonesConfig.getActive().blueMap.enabled;
   }

   private void onWaystoneInitialized(WaystoneInitializedEvent event) {
      if (this.isEnabled()) {
         ResourceKey<Level> dimensionId = event.getWaystone().getDimension();
         BlueMapIntegration.LevelMarkers levelMarkers = this.levelMarkersByDimension.computeIfAbsent(dimensionId, BlueMapIntegration.LevelMarkers::new);
         levelMarkers.addWaystoneMarker(event.getWaystone());
         if (this.api != null) {
            levelMarkers.update(this.api);
         }
      }
   }

   private void onWaystoneUpdated(WaystoneUpdatedEvent event) {
      if (this.isEnabled()) {
         ResourceKey<Level> dimensionId = event.getWaystone().getDimension();
         BlueMapIntegration.LevelMarkers levelMarkers = this.levelMarkersByDimension.computeIfAbsent(dimensionId, BlueMapIntegration.LevelMarkers::new);
         levelMarkers.addWaystoneMarker(event.getWaystone());
         if (this.api != null) {
            levelMarkers.update(this.api);
         }
      }
   }

   private void onWaystoneRemoved(WaystoneRemovedEvent event) {
      if (this.isEnabled()) {
         ResourceKey<Level> dimensionId = event.getWaystone().getDimension();
         BlueMapIntegration.LevelMarkers levelMarkers = this.levelMarkersByDimension.computeIfAbsent(dimensionId, BlueMapIntegration.LevelMarkers::new);
         levelMarkers.removeWaystoneMarker(event.getWaystone());
         if (this.api != null) {
            levelMarkers.update(this.api);
         }
      }
   }

   private void onWaystonesLoaded(WaystonesLoadedEvent event) {
      if (this.isEnabled()) {
         Map<ResourceKey<Level>, List<Waystone>> waystonesByDimension = event.getWaystoneManager()
            .getWaystones()
            .filter(BlueMapIntegration::isSupportedWaystone)
            .collect(Collectors.groupingBy(Waystone::getDimension));

         for (Entry<ResourceKey<Level>, List<Waystone>> entry : waystonesByDimension.entrySet()) {
            BlueMapIntegration.LevelMarkers levelMarkers = this.levelMarkersByDimension.computeIfAbsent(entry.getKey(), BlueMapIntegration.LevelMarkers::new);
            levelMarkers.createFromWaystones(entry.getValue());
            if (this.api != null) {
               levelMarkers.update(this.api);
            }
         }
      }
   }

   private static class LevelMarkers {
      private final MarkerSet waystoneMarkers = MarkerSet.builder().label("Waystones").build();
      private final MarkerSet undiscoveredWaystoneMarkers = MarkerSet.builder().label("Waystones (undiscovered)").build();
      private final MarkerSet sharestoneMarkers = MarkerSet.builder().label("Sharestones").build();
      private final ResourceKey<Level> level;

      private LevelMarkers(ResourceKey<Level> level) {
         this.level = level;
      }

      public void update(BlueMapAPI api) {
         WaystonesConfigData.BlueMap config = WaystonesConfig.getActive().blueMap;
         api.getWorld(this.level).ifPresent(world -> {
            for (BlueMapMap map : world.getMaps()) {
               if (config.includeWaystones) {
                  map.getMarkerSets().put("waystones:waystones", this.waystoneMarkers);
               } else {
                  map.getMarkerSets().remove("waystones:waystones");
               }

               if (config.includeUndiscoveredWaystones) {
                  map.getMarkerSets().put("waystones:undiscovered_waystones", this.undiscoveredWaystoneMarkers);
               } else {
                  map.getMarkerSets().remove("waystones:undiscovered_waystones");
               }

               if (config.includeSharestones) {
                  map.getMarkerSets().put("waystones:sharestones", this.sharestoneMarkers);
               } else {
                  map.getMarkerSets().remove("waystones:sharestones");
               }
            }
         });
      }

      public void createFromWaystones(List<Waystone> waystones) {
         this.waystoneMarkers.getMarkers().clear();
         this.undiscoveredWaystoneMarkers.getMarkers().clear();
         this.sharestoneMarkers.getMarkers().clear();

         for (Waystone waystone : waystones) {
            this.addWaystoneMarker(waystone);
         }
      }

      public void addWaystoneMarker(Waystone waystone) {
         POIMarker marker = BlueMapIntegration.createWaystoneMarker(waystone);
         String markerId = BlueMapIntegration.getMarkerId(waystone);
         WaystonesConfigData.BlueMap config = WaystonesConfig.getActive().blueMap;
         if (WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
            if (config.includeSharestones) {
               this.sharestoneMarkers.put(markerId, marker);
            }
         } else if (waystone.hasName()) {
            if (config.includeWaystones) {
               this.waystoneMarkers.put(markerId, marker);
            }
         } else if (config.includeUndiscoveredWaystones) {
            this.undiscoveredWaystoneMarkers.put(markerId, marker);
         }
      }

      public void removeWaystoneMarker(Waystone waystone) {
         String markerId = BlueMapIntegration.getMarkerId(waystone);
         if (WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
            this.sharestoneMarkers.remove(markerId);
         } else {
            this.waystoneMarkers.remove(markerId);
            this.undiscoveredWaystoneMarkers.remove(markerId);
         }
      }
   }
}
