package dev.ftb.mods.ftbchunks.client;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbchunks.EntityTypeBoolMapValue;
import dev.ftb.mods.ftbchunks.client.gui.GuiClaimMode;
import dev.ftb.mods.ftbchunks.client.map.BiomeBlendMode;
import dev.ftb.mods.ftbchunks.client.map.MapMode;
import dev.ftb.mods.ftbchunks.client.minimap.MinimapComponentConfig;
import dev.ftb.mods.ftbchunks.client.minimap.components.BiomeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.DebugComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.FPSComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.GameTimeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.PlayerPosInfoComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.RealTimeComponent;
import dev.ftb.mods.ftbchunks.client.minimap.components.ZoneInfoComponent;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.DoubleValue;
import dev.ftb.mods.ftblibrary.snbt.config.EnumValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringListValue;
import dev.ftb.mods.ftblibrary.snbt.config.StringMapValue;
import java.util.Collections;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public interface FTBChunksClientConfig {
   String KEY = "ftbchunks-client";
   SNBTConfig CONFIG = (SNBTConfig)SNBTConfig.create("ftbchunks-client")
      .comment(
         new String[]{
            "Client-specific configuration for FTB Chunks",
            "Modpack defaults should be defined in <instance>/config/ftbchunks-client.snbt",
            "  (may be overwritten on modpack update)",
            "Players may locally override this by copying into <instance>/local/ftbchunks-client.snbt",
            "  (will NOT be overwritten on modpack update)"
         }
      );
   SNBTConfig APPEARANCE = CONFIG.addGroup("appearance", 0);
   DoubleValue NOISE = (DoubleValue)APPEARANCE.addDouble("noise", 0.05, 0.0, 0.5)
      .fader()
      .comment(new String[]{"Noise added to map to make it look less plastic"});
   DoubleValue SHADOWS = (DoubleValue)APPEARANCE.addDouble("shadows", 0.1, 0.0, 0.3).fader().comment(new String[]{"Shadow intensity"});
   BooleanValue CHUNK_GRID = (BooleanValue)APPEARANCE.addBoolean("chunk_grid", false).comment(new String[]{"Chunk grid overlay in large map"});
   BooleanValue REDUCED_COLOR_PALETTE = (BooleanValue)APPEARANCE.addBoolean("reduced_color_palette", false)
      .comment(new String[]{"Reduces color palette to 256 colors"});
   DoubleValue SATURATION = (DoubleValue)APPEARANCE.addDouble("saturation", 1.0, 0.0, 1.0).fader().comment(new String[]{"Color intensity"});
   BooleanValue CLAIMED_CHUNKS_ON_MAP = (BooleanValue)APPEARANCE.addBoolean("claimed_chunks_on_map", true)
      .comment(new String[]{"Show claimed chunks on the map"});
   BooleanValue OWN_CLAIMED_CHUNKS_ON_MAP = (BooleanValue)APPEARANCE.addBoolean("own_claimed_chunks_on_map", true)
      .comment(new String[]{"Show your own claimed chunks on the map"});
   BooleanValue ONLY_SURFACE_ENTITIES = (BooleanValue)APPEARANCE.addBoolean("only_surface_entities", true)
      .comment(new String[]{"Only show entities that are on the surface"});
   EnumValue<MapMode> MAP_MODE = (EnumValue<MapMode>)APPEARANCE.addEnum("map_mode", MapMode.NAME_MAP).comment(new String[]{"Different ways to render map"});
   IntValue WATER_HEIGHT_FACTOR = (IntValue)APPEARANCE.addInt("water_height_factor", 8, 0, 128)
      .comment(new String[]{"How many blocks should height checks skip in water. 0 means flat water, ignoring terrain"});
   EnumValue<BiomeBlendMode> BIOME_BLEND = (EnumValue<BiomeBlendMode>)APPEARANCE.addEnum("biome_blend", BiomeBlendMode.NAME_MAP)
      .comment(new String[]{"Biome blend"});
   IntValue WATER_VISIBILITY = (IntValue)APPEARANCE.addInt("water_visibility", 220, 0, 255)
      .excluded()
      .comment(new String[]{"Advanced option. Water visibility"});
   IntValue GRASS_DARKNESS = (IntValue)APPEARANCE.addInt("grass_darkness", 50, 0, 255).excluded().comment(new String[]{"Advanced option. Grass darkness"});
   IntValue FOLIAGE_DARKNESS = (IntValue)APPEARANCE.addInt("foliage_darkness", 50, 0, 255)
      .excluded()
      .comment(new String[]{"Advanced option. Foliage darkness"});
   EnumValue<GuiClaimMode> CLAIM_MODE = (EnumValue<GuiClaimMode>)APPEARANCE.addEnum("claim_mode", GuiClaimMode.NAME_MAP)
      .comment(new String[]{"Claim mode for the chunk claiming screen"});
   SNBTConfig WAYPOINTS = CONFIG.addGroup("waypoints", 1);
   BooleanValue IN_WORLD_WAYPOINTS = (BooleanValue)WAYPOINTS.addBoolean("in_world_waypoints", true).comment(new String[]{"Show waypoints in world"});
   BooleanValue DEATH_WAYPOINTS = (BooleanValue)WAYPOINTS.addBoolean("death_waypoints", true).comment(new String[]{"Enables creation of death waypoints"});
   IntValue DEATH_WAYPOINT_AUTOREMOVE_DISTANCE = (IntValue)WAYPOINTS.addInt("death_waypoint_autoremove_distance", 0, 0, Integer.MAX_VALUE)
      .comment(new String[]{"Automatically remove death waypoints if closer than this many blocks away (distance of 0 disables removal)"});
   DoubleValue WAYPOINT_BEACON_FADE_DISTANCE = (DoubleValue)WAYPOINTS.addDouble("waypoint_fade_distance", 12.0, 1.0, 200.0)
      .comment(new String[]{"Minimum distance before waypoint beacons start to fade"});
   DoubleValue WAYPOINT_DOT_FADE_DISTANCE = (DoubleValue)WAYPOINTS.addDouble("waypoint_dot_fade_distance", 1.0, 1.0, 200.0)
      .comment(new String[]{"Minimum distance before waypoint dots start to fade"});
   DoubleValue WAYPOINT_MAX_DISTANCE = (DoubleValue)WAYPOINTS.addDouble("waypoint_max_distance", 5000.0, 1.0, 2.147483647E9)
      .comment(new String[]{"Maximum distance at which waypoints are drawn"});
   DoubleValue WAYPOINT_FOCUS_DISTANCE = (DoubleValue)WAYPOINTS.addDouble("waypoint_focus_distance", 1.0, 1.0, 10.0)
      .comment(new String[]{"How close player crosshair needs to be to in-world waypoints to show waypoint labels"});
   DoubleValue WAYPOINT_FOCUS_SCALE = (DoubleValue)WAYPOINTS.addDouble("waypoint_focus_scale", 2.0, 1.0, 10.0)
      .comment(new String[]{"How much do in-world waypoints enlarge when the player crosshair is close"});
   SNBTConfig MINIMAP = CONFIG.addGroup("minimap", 2);
   BooleanValue MINIMAP_ENABLED = (BooleanValue)MINIMAP.addBoolean("enabled", !hasOtherMinimapMod()).comment(new String[]{"Enable minimap"});
   EnumValue<MinimapPosition> MINIMAP_POSITION = (EnumValue<MinimapPosition>)MINIMAP.addEnum("position", MinimapPosition.NAME_MAP)
      .comment(new String[]{"Enables minimap to show up in corner"});
   DoubleValue MINIMAP_SCALE = (DoubleValue)MINIMAP.addDouble("scale", 1.0, 0.25, 4.0).comment(new String[]{"Scale of minimap"});
   DoubleValue MINIMAP_FONT_SCALE = (DoubleValue)MINIMAP.addDouble("font_scale", 0.5, 0.1, 5.0)
      .comment(new String[]{"Minimap font scaling (values not a multiple of 0.5 may look bad)"});
   DoubleValue MINIMAP_ZOOM = (DoubleValue)MINIMAP.addDouble("zoom", 1.0, 1.0, 4.0).comment(new String[]{"Zoom distance of the minimap"});
   BooleanValue MINIMAP_LOCKED_NORTH = (BooleanValue)MINIMAP.addBoolean("locked_north", true).comment(new String[]{"Minimap will not rotate"});
   BooleanValue SHOW_PLAYER_WHEN_UNLOCKED = (BooleanValue)MINIMAP.addBoolean("show_player_when_unlocked", true)
      .comment(new String[]{"Always show player on minimap, even when rotation not locked"});
   BooleanValue MINIMAP_WAYPOINTS = (BooleanValue)MINIMAP.addBoolean("waypoints", true).comment(new String[]{"Show waypoints on minimap"});
   BooleanValue MINIMAP_PLAYER_HEADS = (BooleanValue)MINIMAP.addBoolean("player_heads", true).comment(new String[]{"Show player heads on minimap"});
   BooleanValue MINIMAP_ENTITIES = (BooleanValue)MINIMAP.addBoolean("entities", true).comment(new String[]{"Show entities on minimap"});
   BooleanValue MINIMAP_ENTITY_HEADS = (BooleanValue)MINIMAP.addBoolean("entity_heads", true).comment(new String[]{"Show entity heads on minimap"});
   BooleanValue MINIMAP_LARGE_ENTITIES = (BooleanValue)MINIMAP.addBoolean("large_entities", false).comment(new String[]{"Entities in minimap will be larger"});
   EnumValue<MinimapBlurMode> MINIMAP_BLUR_MODE = (EnumValue<MinimapBlurMode>)MINIMAP.addEnum("blur_mode", MinimapBlurMode.NAME_MAP)
      .comment(new String[]{"Blurs minimap"});
   BooleanValue MINIMAP_COMPASS = (BooleanValue)MINIMAP.addBoolean("compass", true).comment(new String[]{"Adds NWSE compass inside minimap"});
   BooleanValue MINIMAP_RETICLE = (BooleanValue)MINIMAP.addBoolean("reticle", true).comment(new String[]{"Draw reticle lines on minimap"});
   IntValue MINIMAP_VISIBILITY = (IntValue)MINIMAP.addInt("visibility", 255, 0, 255).comment(new String[]{"Minimap visibility"});
   IntValue MINIMAP_OFFSET_X = (IntValue)MINIMAP.addInt("position_offset_x", 0)
      .comment(
         new String[]{
            "Changes the maps X offset from it's origin point. When on the Left, the map will be pushed out from the left, then from the right when on the right."
         }
      );
   IntValue MINIMAP_OFFSET_Y = (IntValue)MINIMAP.addInt("position_offset_y", 0)
      .comment(
         new String[]{
            "Changes the maps X offset from it's origin point. When on the Left, the map will be pushed out from the left, then from the right when on the right."
         }
      );
   EnumValue<MinimapPosition.MinimapOffsetConditional> MINIMAP_POSITION_OFFSET_CONDITION = (EnumValue<MinimapPosition.MinimapOffsetConditional>)MINIMAP.addEnum(
         "position_offset_condition", MinimapPosition.MinimapOffsetConditional.NAME_MAP
      )
      .comment(
         new String[]{
            "Applied a conditional check to the offset. When set to anything other that None, the offset will apply only to the selected minimap position.",
            "When set to none and the maps offset is greater than 0, the offset will apply to all directions"
         }
      );
   BooleanValue SQUARE_MINIMAP = (BooleanValue)MINIMAP.addBoolean("square", false).comment(new String[]{"Draw a square minimap instead of a circular one"});
   BooleanValue MINIMAP_PROPORTIONAL = (BooleanValue)MINIMAP.addBoolean("proportional", true)
      .comment(new String[]{"Size minimap proportional to screen width (and scale)"});
   StringListValue MINIMAP_INFO_ORDER = (StringListValue)MINIMAP.addStringList(
         "info_order",
         Stream.of(
               PlayerPosInfoComponent.ID,
               BiomeComponent.ID,
               ZoneInfoComponent.ID,
               FPSComponent.ID,
               GameTimeComponent.ID,
               RealTimeComponent.ID,
               DebugComponent.ID
            )
            .map(ResourceLocation::toString)
            .toList()
      )
      .excluded()
      .comment(new String[]{"Info displayed under minimap"});
   StringListValue MINIMAP_INFO_HIDDEN = (StringListValue)MINIMAP.addStringList(
         "info_hidden", Stream.of(FPSComponent.ID, GameTimeComponent.ID, RealTimeComponent.ID, DebugComponent.ID).map(ResourceLocation::toString).toList()
      )
      .excluded()
      .comment(new String[]{"Info hidden under minimap"});
   StringMapValue MINIMAP_SETTINGS = (StringMapValue)((MinimapComponentConfig)MINIMAP.add(
         new MinimapComponentConfig(MINIMAP, "info_settings", Collections.emptyMap())
      ))
      .comment(new String[]{"Settings for minimap info components"});
   EntityTypeBoolMapValue ENTITY_ICON = (EntityTypeBoolMapValue)((EntityTypeBoolMapValue)MINIMAP.add(
         new EntityTypeBoolMapValue(MINIMAP, "entity_icon", Collections.emptyMap())
      ))
      .comment(new String[]{"Entity icons on minimap"});
   EnumValue<PointerIconMode> POINTER_ICON_MODE = (EnumValue<PointerIconMode>)MINIMAP.addEnum("pointer_icon_mode", PointerIconMode.NAME_MAP)
      .comment(new String[]{"Mode for the pointer icon to render on full screen minimap"});
   EnumValue<PointerIconMode> POINTER_ICON_MODE_MINIMAP = (EnumValue<PointerIconMode>)MINIMAP.addEnum("pointer_icon_mode_minimap", PointerIconMode.NAME_MAP)
      .comment(new String[]{"Mode for the pointer icon to render on minimap"});
   BooleanValue TEXT_ABOVE_MINIMAP = (BooleanValue)MINIMAP.addBoolean("text_above_minimap", false).comment(new String[]{"Show text above minimap"});
   SNBTConfig ADVANCED = CONFIG.addGroup("advanced", 3);
   BooleanValue DEBUG_INFO = (BooleanValue)ADVANCED.addBoolean("debug_info", false).comment(new String[]{"Enables debug info"});
   IntValue TASK_QUEUE_TICKS = (IntValue)ADVANCED.addInt("task_queue_ticks", 4, 1, 300)
      .excluded()
      .comment(new String[]{"Advanced option. How often queued tasks will run"});
   IntValue RERENDER_QUEUE_TICKS = (IntValue)ADVANCED.addInt("rerender_queue_ticks", 60, 1, 600)
      .excluded()
      .comment(new String[]{"Advanced option. How often map render update will be queued"});
   IntValue TASK_QUEUE_MAX = (IntValue)ADVANCED.addInt("task_queue_max", 100, 1, 10000)
      .excluded()
      .comment(new String[]{"Advanced option. Max tasks that can queue up"});
   IntValue MINIMAP_ICON_UPDATE_TIMER = (IntValue)ADVANCED.addInt("minimap_icon_update_timer", 500, 0, 10000)
      .excluded()
      .comment(new String[]{"Advanced option. Change how often the minimap will refresh icons"});
   SNBTConfig MEMORY = ADVANCED.addGroup("memory", 4);
   IntValue REGION_RELEASE_TIME = (IntValue)MEMORY.addInt("region_release_time", 300, 0, Integer.MAX_VALUE)
      .comment(new String[]{"Periodically release region data for non-recently-used regions to save memory (units of seconds, 0 disables releasing"});
   IntValue AUTORELEASE_ON_MAP_CLOSE = (IntValue)MEMORY.addInt("autorelease_on_map_close", 32, 0, Integer.MAX_VALUE)
      .comment(new String[]{"When the large map is closed, auto-release least recently accessed regions down to this number (0 disables releasing)"});
   BooleanValue MAX_ZOOM_CONSTRAINT = (BooleanValue)MEMORY.addBoolean("max_zoom_constraint", true)
      .comment(new String[]{"Constrain maximum map zoom-out based on number of explored regions and available memory"});

   static boolean hasOtherMinimapMod() {
      return Platform.isModLoaded("journeymap")
         || Platform.isModLoaded("voxelmap")
         || Platform.isModLoaded("antiqueatlas")
         || Platform.isModLoaded("xaerominimap");
   }

   static void saveConfig() {
      ConfigManager.getInstance().save("ftbchunks-client");
   }
}
