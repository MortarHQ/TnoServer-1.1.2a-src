package xaero.map.common.config.option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xaero.lib.common.config.option.BooleanConfigOption;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.option.ConfigOptionManager;
import xaero.lib.common.config.option.RangeConfigOption;
import xaero.lib.common.config.option.SteppedConfigOption;
import xaero.lib.common.config.option.BooleanConfigOption.Builder;
import xaero.lib.common.config.option.ConfigOption.FinalBuilder;
import xaero.lib.common.config.option.value.type.BuiltInConfigValueTypes;
import xaero.lib.common.config.util.ConfigUtils;
import xaero.map.common.config.WorldMapConfigConstants;

public class WorldMapProfiledConfigOptions {
   private static final List<ConfigOption<?>> ALL = new ArrayList<>();
   public static final BooleanConfigOption LIGHTING = ((Builder)((Builder)((Builder)Builder.begin().setId("lighting")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_lighting")))
      .build(ALL);
   public static final RangeConfigOption BLOCK_COLORS = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("block_colors"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(WorldMapConfigConstants.BLOCK_COLORS_NAMES.length - 1))
            .setDisplayGetter((o, v) -> WorldMapConfigConstants.BLOCK_COLORS_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_block_colours")))
      .build(ALL);
   public static final BooleanConfigOption LOAD_NEW_CHUNKS = ((Builder)((Builder)((Builder)Builder.begin().setId("load_new_chunks")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_load_chunks")))
      .build(ALL);
   public static final BooleanConfigOption UPDATE_CHUNKS = ((Builder)((Builder)((Builder)Builder.begin().setId("update_chunks")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_update_chunks")))
      .build(ALL);
   public static final BooleanConfigOption TERRAIN_DEPTH = ((Builder)((Builder)((Builder)Builder.begin().setId("terrain_depth")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_terrain_depth")))
      .build(ALL);
   public static final RangeConfigOption TERRAIN_SLOPES = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("terrain_slopes"))
                     .setDefaultValue(2))
                  .setMinIndex(0))
               .setMaxIndex(WorldMapConfigConstants.TERRAIN_SLOPES_NAMES.length - 1))
            .setDisplayGetter((o, v) -> WorldMapConfigConstants.TERRAIN_SLOPES_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_terrain_slopes")))
      .build(ALL);
   public static final BooleanConfigOption FOOTSTEPS = ((Builder)((Builder)((Builder)Builder.begin().setId("footsteps")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_footsteps")))
      .build(ALL);
   public static final BooleanConfigOption COORDINATES = ((Builder)((Builder)((Builder)Builder.begin().setId("display_coordinates")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_coordinates")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINTS = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoints")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_worldmap_waypoints")))
      .build(ALL);
   public static final BooleanConfigOption RENDER_WAYPOINTS = ((Builder)((Builder)((Builder)Builder.begin().setId("render_waypoints")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_render_worldmap_waypoints")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_BACKGROUNDS = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoint_backgrounds"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_backgrounds")))
      .build(ALL);
   public static final SteppedConfigOption WAYPOINT_SCALE = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
               .setId("waypoint_scale"))
            .setDefaultValue(1.0))
         .setMinValue(0.5)
         .setMaxValue(5.0)
         .setStep(0.5)
         .setRangeValidator(true)
         .setDisplayName(Component.translatable("gui.xaero_wm_waypoint_scale")))
      .build(ALL);
   public static final BooleanConfigOption BIOME_BLENDING = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("biome_blending"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_biome_blending")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_biome_blending")))
      .build(ALL);
   public static final BooleanConfigOption BIOME_COLORS_IN_VANILLA = ((Builder)((Builder)((Builder)Builder.begin().setId("biome_colors_in_vanilla"))
            .setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_biome_colors")))
      .build(ALL);
   public static final SteppedConfigOption MIN_ZOOM_LOCAL_WAYPOINTS = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
               .setId("minimum_zoom_for_local_waypoints"))
            .setDefaultValue(0.0))
         .setMinValue(0.0)
         .setMaxValue(3.0)
         .setStep(0.01)
         .setRangeValidator(true)
         .setDisplayName(Component.translatable("gui.xaero_wm_min_zoom_local_waypoints")))
      .build(ALL);
   public static final BooleanConfigOption ADJUST_HEIGHT_FOR_SHORT_BLOCKS = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("adjust_height_for_short_blocks"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_adjust_height_for_carpetlike_blocks")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_adjust_height_for_carpetlike_blocks")))
      .build(ALL);
   public static final BooleanConfigOption FLOWERS = ((Builder)((Builder)((Builder)Builder.begin().setId("display_flowers")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_flowers")))
      .build(ALL);
   public static final BooleanConfigOption STAINED_GLASS = ((Builder)((Builder)((Builder)Builder.begin().setId("display_stained_glass")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_display_stained_glass")))
      .build(ALL);
   public static final BooleanConfigOption CAVE_MODE_ALLOWED = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("cave_mode_allowed"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_cave_mode_allowed")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_cave_mode_allowed")))
      .build(ALL);
   public static final ConfigOption<Set<ResourceLocation>> CAVE_MODE_ALLOWED_DIMENSIONS = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                        .setId("cave_mode_allowed_dimensions"))
                     .setDefaultValue(Collections.unmodifiableSet(new LinkedHashSet())))
                  .setValueType(
                     xaero.lib.common.config.option.value.type.CollectionConfigValueType.Builder.begin()
                        .setElementValueType(BuiltInConfigValueTypes.RESOURCE_LOCATION)
                        .setIoCodecSeparator(',')
                        .build()
                  ))
               .setDisplayName(Component.translatable("gui.xaero_wm_cave_mode_allowed_dimensions")))
            .setTooltip(Component.translatable("gui.xaero_wm_box_cave_mode_allowed_dimensions")))
         .setOverridable(false))
      .build(ALL);
   public static final RangeConfigOption CAVE_MODE_DEPTH = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("cave_mode_depth"))
                  .setDefaultValue(30))
               .setMinIndex(1))
            .setMaxIndex(64))
         .setDisplayName(Component.translatable("gui.xaero_wm_cave_mode_depth")))
      .build(ALL);
   public static final BooleanConfigOption LEGIBLE_CAVE_MAPS = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("legible_cave_maps"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_wm_legible_cave_maps")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_legible_cave_maps")))
      .build(ALL);
   public static final RangeConfigOption AUTO_CAVE_MODE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("auto_cave_mode"))
                        .setDefaultValue(-1))
                     .setMinIndex(-1))
                  .setMaxIndex(3))
               .setDisplayGetter((o, v) -> {
                  if (v == 0) {
                     return Component.translatable("gui.xaero_off");
                  }

                  if (v < 0) {
                     return Component.translatable("gui.xaero_auto_cave_mode_minimap");
                  }

                  int roofSideSize = v * 2 - 1;
                  return Component.translatable("gui.xaero_wm_ceiling", new Object[]{roofSideSize + "x" + roofSideSize});
               }))
            .setDisplayName(Component.translatable("gui.xaero_auto_cave_mode")))
         .setTooltip(Component.translatable("gui.xaero_box_auto_cave_mode")))
      .build(ALL);
   public static final SteppedConfigOption CAVE_MODE_TOGGLE_TIMER = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                     .setId("cave_mode_toggle_timer"))
                  .setDefaultValue(1.0))
               .setMinValue(0.0)
               .setMaxValue(10.0)
               .setStep(0.1)
               .setDisplayGetter((o, v) -> ConfigUtils.getDisplayForSimpleNumber(o, v, WorldMapConfigConstants.SEC)))
            .setDisplayName(Component.translatable("gui.xaero_wm_cave_mode_toggle_timer")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_cave_mode_toggle_timer")))
      .build(ALL);
   public static final RangeConfigOption DEFAULT_CAVE_MODE_TYPE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("default_cave_mode_type"))
                        .setDefaultValue(1))
                     .setMinIndex(0))
                  .setMaxIndex(WorldMapConfigConstants.DEFAULT_CAVE_MODE_TYPE_NAMES.length - 1))
               .setDisplayGetter((o, v) -> WorldMapConfigConstants.DEFAULT_CAVE_MODE_TYPE_NAMES[v]))
            .setDisplayName(Component.translatable("gui.xaero_wm_default_cave_mode_type")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_default_cave_mode_type")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_CAVE_MODE_START = ((Builder)((Builder)((Builder)Builder.begin().setId("display_cave_mode_start"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_display_cave_mode_start")))
      .build(ALL);
   public static final RangeConfigOption WRITING_DISTANCE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("map_writing_distance"))
                        .setDefaultValue(-1))
                     .setMinIndex(-1))
                  .setMaxIndex(32))
               .setDisplayGetter((o, v) -> v < 0 ? Component.translatable("gui.xaero_map_writing_distance_unlimited") : Component.literal(v + "")))
            .setDisplayName(Component.translatable("gui.xaero_map_writing_distance")))
         .setTooltip(Component.translatable("gui.xaero_box_map_writing_distance")))
      .build(ALL);
   public static final BooleanConfigOption ARROW = ((Builder)((Builder)((Builder)Builder.begin().setId("display_player_as_arrow")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_render_arrow")))
      .build(ALL);
   public static final BooleanConfigOption OPENING_ANIMATION = ((Builder)((Builder)((Builder)Builder.begin().setId("opening_animation")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_open_map_animation")))
      .build(ALL);
   public static final RangeConfigOption ARROW_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("arrow_color"))
                        .setDefaultValue(-2))
                     .setMinIndex(-2))
                  .setMaxIndex(WorldMapConfigConstants.ARROW_COLORS.length - 1))
               .setDisplayGetter((o, v) -> WorldMapConfigConstants.ARROW_COLOR_NAMES[v + 2]))
            .setDisplayName(Component.translatable("gui.xaero_wm_arrow_colour")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_arrow_color")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_ZOOM = ((Builder)((Builder)((Builder)Builder.begin().setId("display_zoom")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_display_zoom")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_HOVERED_BIOME = ((Builder)((Builder)((Builder)Builder.begin().setId("display_hovered_biome"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_hovered_biome")))
      .build(ALL);
   public static final BooleanConfigOption ZOOM_BUTTONS = ((Builder)((Builder)((Builder)Builder.begin().setId("zoom_buttons")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_zoom_buttons")))
      .build(ALL);
   public static final BooleanConfigOption DETECT_AMBIGUOUS_Y = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("detect_ambiguous_y"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_detect_ambiguous_y")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_detect_ambiguous_y")))
      .build(ALL);
   public static final ConfigOption<String> DEFAULT_MAP_TELEPORT_FORMAT = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("default_map_teleport_command_format"))
               .setDefaultValue("/tp @s {x} {y} {z}"))
            .setValueType(BuiltInConfigValueTypes.getString(500)))
         .setDisplayName(Component.translatable("gui.xaero_wm_default_teleport_command")))
      .build(ALL);
   public static final ConfigOption<String> DEFAULT_MAP_TELEPORT_DIMENSION_FORMAT = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("default_map_teleport_command_dimension_format"))
               .setDefaultValue("/execute as @s in {d} run tp {x} {y} {z}"))
            .setValueType(BuiltInConfigValueTypes.getString(500)))
         .setDisplayName(Component.translatable("gui.xaero_wm_default_teleport_command_dimension")))
      .build(ALL);
   public static final ConfigOption<String> DEFAULT_PLAYER_TELEPORT_FORMAT = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("default_player_teleport_command_format"))
               .setDefaultValue("/tp @s {name}"))
            .setValueType(BuiltInConfigValueTypes.getString(500)))
         .setDisplayName(Component.translatable("gui.xaero_wm_default_player_teleport_command")))
      .build(ALL);
   public static final BooleanConfigOption MAP_TELEPORT_ALLOWED = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("map_teleport_allowed"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_teleport_allowed")))
         .setTooltip(Component.translatable("gui.xaero_wm_teleport_allowed_tooltip")))
      .build(ALL);
   public static final BooleanConfigOption PARTIAL_Y_TELEPORT = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("partial_y_teleport"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_partial_y_teleportation")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_partial_y_teleportation")))
      .build(ALL);
   public static final BooleanConfigOption OPAC_CLAIMS = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("display_opac_claims"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_pac_claims")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_pac_claims")))
      .build(ALL);
   public static final RangeConfigOption OPAC_CLAIMS_BORDER_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("opac_claims_border_opacity"))
                     .setDefaultValue(80))
                  .setMinIndex(1))
               .setMaxIndex(100))
            .setDisplayName(Component.translatable("gui.xaero_wm_pac_claims_border_opacity")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_pac_claims_border_opacity")))
      .build(ALL);
   public static final RangeConfigOption OPAC_CLAIMS_FILL_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("opac_claims_fill_opacity"))
                     .setDefaultValue(46))
                  .setMinIndex(1))
               .setMaxIndex(100))
            .setDisplayName(Component.translatable("gui.xaero_wm_pac_claims_fill_opacity")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_pac_claims_fill_opacity")))
      .build(ALL);
   public static final ConfigOption<String> MAP_ITEM = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                        .setId("map_item"))
                     .setDefaultValue("-"))
                  .setValueType(BuiltInConfigValueTypes.getString(BuiltInConfigValueTypes.RESOURCE_LOCATION.getIoCodec().getMaxStringLength())))
               .setDisplayGetter(ConfigUtils::getDisplayForString))
            .setDisplayName(Component.translatable("gui.xaero_map_item")))
         .setTooltip(Component.translatable("gui.xaero_box_map_item")))
      .build(ALL);
   public static final BooleanConfigOption MINIMAP_RADAR = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("display_minimap_radar"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_wm_display_minimap_radar")))
         .setTooltip(Component.translatable("gui.xaero_wm_box_display_minimap_radar")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_TRACKED_PLAYERS = ((Builder)((Builder)((Builder)Builder.begin().setId("display_tracked_players"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_display_tracked_players")))
      .build(ALL);

   public static void registerAll(ConfigOptionManager manager) {
      for (ConfigOption<?> option : ALL) {
         manager.register(option);
      }
   }
}
