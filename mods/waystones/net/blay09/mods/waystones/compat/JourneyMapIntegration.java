package net.blay09.mods.waystones.compat;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.JourneyMapPlugin;
import journeymap.api.v2.client.event.MappingEvent;
import journeymap.api.v2.client.event.MappingEvent.Stage;
import journeymap.api.v2.common.event.ClientEventRegistry;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointFactory;
import journeymap.api.v2.common.waypoint.WaypointGroup;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.event.WaystoneRemoveReceivedEvent;
import net.blay09.mods.waystones.api.event.WaystoneUpdateReceivedEvent;
import net.blay09.mods.waystones.api.event.WaystonesListReceivedEvent;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@JourneyMapPlugin(apiVersion = "2.0.0")
public class JourneyMapIntegration implements IClientPlugin {
   private static final Gson gson = new Gson();
   private IClientAPI api;
   private boolean journeyMapReady;
   private final Map<UUID, String> waystoneToWaypoint = new HashMap<>();
   private final List<Runnable> scheduledJobsWhenReady = new ArrayList<>();
   private static JourneyMapIntegration instance;

   public JourneyMapIntegration() {
      instance = this;
      Balm.getEvents().onEvent(WaystonesListReceivedEvent.class, this::onWaystonesListReceived);
      Balm.getEvents().onEvent(WaystoneUpdateReceivedEvent.class, this::onWaystoneUpdateReceived);
      Balm.getEvents().onEvent(WaystoneRemoveReceivedEvent.class, this::onWaystoneRemoveReceived);
   }

   public void initialize(IClientAPI iClientAPI) {
      this.api = iClientAPI;
      ClientEventRegistry.MAPPING_EVENT.subscribe("waystones", this::onMappingEvent);
   }

   @Nullable
   public static JourneyMapIntegration getInstance() {
      return instance;
   }

   public String getModId() {
      return "waystones";
   }

   public void onMappingEvent(MappingEvent event) {
      if (event.getStage() == Stage.MAPPING_STARTED) {
         for (Waypoint waypoint : this.api.getWaypoints("waystones")) {
            JourneyMapIntegration.WaystonesWaypointData.decode(waypoint.getCustomData())
               .ifPresent(customData -> this.waystoneToWaypoint.put(customData.waystoneId(), waypoint.getGuid()));
         }

         this.journeyMapReady = true;

         for (Runnable scheduledJob : this.scheduledJobsWhenReady) {
            scheduledJob.run();
         }

         this.scheduledJobsWhenReady.clear();
      } else if (event.getStage() == Stage.MAPPING_STOPPED) {
         this.journeyMapReady = false;
         this.waystoneToWaypoint.clear();
      }
   }

   public void onWaystonesListReceived(WaystonesListReceivedEvent event) {
      if (shouldManageWaypoints() && this.isSupportedWaystoneType(event.getWaystoneType())) {
         this.runWhenJourneyMapIsReady(
            () -> this.updateAllWaypoints(event.getWaystoneType(), event.getWaystones().stream().filter(this::isSupportedWaystone).toList())
         );
      }
   }

   private boolean isSupportedWaystone(Waystone waystone) {
      return this.isSupportedWaystoneType(waystone.getWaystoneType()) && !waystone.isTransient();
   }

   private boolean isSupportedWaystoneType(ResourceLocation waystoneType) {
      return waystoneType.equals(WaystoneTypes.WAYSTONE) || WaystoneTypes.isSharestone(waystoneType);
   }

   private static boolean shouldManageWaypoints() {
      WaystonesConfigData config = WaystonesConfig.getActive();
      return config.compatibility.preferJourneyMapIntegrationMod && Balm.isModLoaded("jmi") ? false : config.compatibility.journeyMap;
   }

   public void onWaystoneUpdateReceived(WaystoneUpdateReceivedEvent event) {
      if (shouldManageWaypoints()) {
         if (this.isSupportedWaystone(event.getWaystone())) {
            this.runWhenJourneyMapIsReady(() -> this.updateWaypoint(event.getWaystone()));
         } else {
            this.runWhenJourneyMapIsReady(() -> this.removeWaypoint(event.getWaystone().getWaystoneUid()));
         }
      }
   }

   public void onWaystoneRemoveReceived(WaystoneRemoveReceivedEvent event) {
      if (shouldManageWaypoints() && this.isSupportedWaystoneType(event.getWaystoneType())) {
         this.runWhenJourneyMapIsReady(() -> this.removeWaypoint(event.getWaystoneId()));
      }
   }

   private void runWhenJourneyMapIsReady(Runnable runnable) {
      if (this.journeyMapReady) {
         runnable.run();
      } else {
         this.scheduledJobsWhenReady.add(runnable);
      }
   }

   private void updateAllWaypoints(ResourceLocation waystoneType, List<Waystone> waystones) {
      HashSet<UUID> stillExistingIds = new HashSet<>();

      for (Waystone waystone : waystones) {
         stillExistingIds.add(waystone.getWaystoneUid());
         this.updateWaypoint(waystone);
      }

      for (Waypoint waypoint : this.api.getWaypoints("waystones")) {
         JourneyMapIntegration.WaystonesWaypointData.decode(waypoint.getCustomData()).ifPresent(customData -> {
            UUID waystoneUid = customData.waystoneId();
            if (waystoneType.equals(customData.waystoneType()) && !stillExistingIds.contains(waystoneUid)) {
               this.api.removeWaypoint("waystones", waypoint);
               this.waystoneToWaypoint.remove(waystoneUid);
            }
         });
      }
   }

   private void removeWaypoint(UUID waystoneId) {
      String waypointId = this.waystoneToWaypoint.get(waystoneId);
      if (waypointId != null) {
         Waypoint waypoint = this.api.getWaypoint("waystones", waypointId);
         if (waypoint != null) {
            this.api.removeWaypoint("waystones", waypoint);
         }

         this.waystoneToWaypoint.remove(waystoneId);
      }
   }

   private void updateWaypoint(Waystone waystone) {
      try {
         String waypointId = this.waystoneToWaypoint.get(waystone.getWaystoneUid());
         Component waystoneName = (Component)(waystone.hasName() ? waystone.getName() : Component.translatable("waystones.map.untitled_waystone"));
         Waypoint oldWaypoint = waypointId != null ? this.api.getWaypoint("waystones", waypointId) : null;
         Waypoint waypoint = oldWaypoint != null
            ? oldWaypoint
            : WaypointFactory.createClientWaypoint("waystones", waystone.getPos().above(2), waystoneName.getString(), waystone.getDimension(), true);
         if (oldWaypoint != null) {
            oldWaypoint.setName(waystoneName.getString());
            waypoint.setPos(waystone.getPos().getX(), waystone.getPos().getY() + 2, waystone.getPos().getZ());
            waypoint.setPrimaryDimension(waystone.getDimension());
         }

         waypoint.setCustomData(new JourneyMapIntegration.WaystonesWaypointData(waystone.getWaystoneUid(), waystone.getWaystoneType()).encode());
         if (oldWaypoint == null) {
            this.api.addWaypoint("waystones", waypoint);
            WaypointGroup group = this.getWaystoneGroup(waystone);
            if (group != null) {
               group.setLocked(false);
               group.addWaypoint(waypoint);
               group.setLocked(true);
            }
         }

         this.waystoneToWaypoint.put(waystone.getWaystoneUid(), waypoint.getGuid());
      } catch (Exception e) {
         Waystones.logger.warn("Failed to update waypoint for waystone {}", waystone.getWaystoneUid(), e);
      }
   }

   private WaypointGroup getWaystoneGroup(Waystone waystone) {
      return WaystoneTypes.isSharestone(waystone.getWaystoneType())
         ? Optional.ofNullable(this.api.getWaypointGroupByName("waystones", "Sharestones")).orElseGet(() -> {
            WaypointGroup group = WaypointFactory.createWaypointGroup("waystones", "Sharestones");
            group.setLocked(true);
            this.api.addWaypointGroup(group);
            return group;
         })
         : Optional.ofNullable(this.api.getWaypointGroupByName("waystones", "Waystones")).orElseGet(() -> {
            WaypointGroup group = WaypointFactory.createWaypointGroup("waystones", "Waystones");
            group.setLocked(true);
            this.api.addWaypointGroup(group);
            return group;
         });
   }

   private record WaystonesWaypointData(UUID waystoneId, ResourceLocation waystoneType) {
      private static final MapCodec<JourneyMapIntegration.WaystonesWaypointData> CODEC = RecordCodecBuilder.mapCodec(
         builder -> builder.group(
               UUIDUtil.CODEC.fieldOf("waystoneId").forGetter(JourneyMapIntegration.WaystonesWaypointData::waystoneId),
               ResourceLocation.CODEC.fieldOf("type").forGetter(JourneyMapIntegration.WaystonesWaypointData::waystoneType)
            )
            .apply(builder, JourneyMapIntegration.WaystonesWaypointData::new)
      );

      public static Optional<JourneyMapIntegration.WaystonesWaypointData> decode(String customData) {
         JsonElement jsonElement = (JsonElement)JourneyMapIntegration.gson.fromJson(customData, JsonElement.class);
         return JsonOps.INSTANCE.getMap(jsonElement).flatMap(it -> CODEC.decode(JsonOps.INSTANCE, it)).resultOrPartial();
      }

      public String encode() {
         JsonElement jsonElement = (JsonElement)CODEC.encode(this, JsonOps.INSTANCE, JsonOps.INSTANCE.mapBuilder())
            .build(JsonOps.INSTANCE.empty())
            .getOrThrow();
         return JourneyMapIntegration.gson.toJson(jsonElement);
      }
   }
}
