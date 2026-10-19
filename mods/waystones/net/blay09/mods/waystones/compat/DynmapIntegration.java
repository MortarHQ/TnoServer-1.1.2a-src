package net.blay09.mods.waystones.compat;

import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.event.WaystoneInitializedEvent;
import net.blay09.mods.waystones.api.event.WaystoneRemovedEvent;
import net.blay09.mods.waystones.api.event.WaystoneUpdatedEvent;
import net.blay09.mods.waystones.api.event.WaystonesLoadedEvent;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.resources.ResourceLocation;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerSet;

public class DynmapIntegration extends DynmapCommonAPIListener {
   private final List<Runnable> scheduledJobsWhenReady = new ArrayList<>();
   private DynmapCommonAPI api;
   private MarkerSet waystoneMarkers;
   private MarkerSet sharestoneMarkers;

   public DynmapIntegration() {
      DynmapCommonAPIListener.register(this);
      Balm.getEvents().onEvent(WaystonesLoadedEvent.class, this::onWaystonesLoaded);
      Balm.getEvents().onEvent(WaystoneInitializedEvent.class, this::onWaystoneInitialized);
      Balm.getEvents().onEvent(WaystoneUpdatedEvent.class, this::onWaystoneUpdated);
      Balm.getEvents().onEvent(WaystoneRemovedEvent.class, this::onWaystoneRemoved);
   }

   public static String getMarkerId(Waystone waystone) {
      return waystone.getWaystoneUid().toString();
   }

   public static Marker createWaystoneMarker(MarkerSet markerSet, Waystone waystone) {
      return markerSet.createMarker(
         getMarkerId(waystone),
         waystone.getName().getString(),
         false,
         getDynmapWorldName(waystone.getDimension().location()),
         waystone.getPos().getX(),
         waystone.getPos().getY(),
         waystone.getPos().getZ(),
         markerSet.getDefaultMarkerIcon(),
         false
      );
   }

   private static String getDynmapWorldName(ResourceLocation id) {
      return switch (id.toString()) {
         case "minecraft:overworld" -> Balm.getHooks().getServer().getWorldData().getLevelName();
         case "minecraft:the_nether" -> "DIM-1";
         case "minecraft:the_end" -> "DIM1";
         default -> id.getNamespace() + "_" + id.getPath();
      };
   }

   private static boolean isSupportedWaystone(Waystone waystone) {
      return isSupportedWaystoneType(waystone.getWaystoneType()) && !waystone.isTransient();
   }

   private static boolean isSupportedWaystoneType(ResourceLocation waystoneType) {
      return waystoneType.equals(WaystoneTypes.WAYSTONE) || WaystoneTypes.isSharestone(waystoneType);
   }

   public boolean isEnabled() {
      return WaystonesConfig.getActive().compatibility.dynmap;
   }

   private void prepareMarkerSets() {
      if (this.waystoneMarkers == null) {
         this.waystoneMarkers = this.api.getMarkerAPI().createMarkerSet("waystones:waystones", "Waystones", null, false);
      }

      if (this.sharestoneMarkers == null) {
         this.sharestoneMarkers = this.api.getMarkerAPI().createMarkerSet("waystones:sharestones", "Sharestones", null, false);
      }
   }

   public void createFromWaystones(List<Waystone> waystones) {
      if (this.waystoneMarkers != null) {
         this.waystoneMarkers.deleteMarkerSet();
      }

      if (this.sharestoneMarkers != null) {
         this.sharestoneMarkers.deleteMarkerSet();
      }

      this.prepareMarkerSets();

      for (Waystone waystone : waystones) {
         this.addWaystoneMarker(waystone);
      }
   }

   public void addWaystoneMarker(Waystone waystone) {
      this.prepareMarkerSets();
      MarkerSet markerSet = WaystoneTypes.isSharestone(waystone.getWaystoneType()) ? this.sharestoneMarkers : this.waystoneMarkers;
      createWaystoneMarker(markerSet, waystone);
   }

   public void removeWaystoneMarker(Waystone waystone) {
      if (this.waystoneMarkers == null) {
         String markerId = getMarkerId(waystone);
         Marker marker = this.waystoneMarkers.findMarker(markerId);
         if (marker != null) {
            marker.deleteMarker();
         }
      }
   }

   public void apiEnabled(DynmapCommonAPI api) {
      this.api = api;

      for (Runnable scheduledJob : this.scheduledJobsWhenReady) {
         scheduledJob.run();
      }

      this.scheduledJobsWhenReady.clear();
   }

   public void apiDisabled(DynmapCommonAPI api) {
      this.api = null;
   }

   private void onWaystoneInitialized(WaystoneInitializedEvent event) {
      if (this.isEnabled()) {
         this.runWhenDynmapIsReady(() -> this.addWaystoneMarker(event.getWaystone()));
      }
   }

   private void onWaystoneUpdated(WaystoneUpdatedEvent event) {
      if (this.isEnabled()) {
         this.runWhenDynmapIsReady(() -> this.addWaystoneMarker(event.getWaystone()));
      }
   }

   private void onWaystoneRemoved(WaystoneRemovedEvent event) {
      if (this.isEnabled()) {
         this.runWhenDynmapIsReady(() -> this.removeWaystoneMarker(event.getWaystone()));
      }
   }

   private void onWaystonesLoaded(WaystonesLoadedEvent event) {
      if (this.isEnabled()) {
         List<Waystone> waystones = event.getWaystoneManager().getWaystones().filter(DynmapIntegration::isSupportedWaystone).toList();
         this.runWhenDynmapIsReady(() -> this.createFromWaystones(waystones));
      }
   }

   private void runWhenDynmapIsReady(Runnable runnable) {
      if (this.api != null) {
         runnable.run();
      } else {
         this.scheduledJobsWhenReady.add(runnable);
      }
   }
}
