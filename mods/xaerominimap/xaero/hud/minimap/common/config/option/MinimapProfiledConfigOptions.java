package xaero.hud.minimap.common.config.option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xaero.hud.minimap.common.config.MinimapConfigConstants;
import xaero.hud.minimap.common.config.info.config.InfoDisplayManagerConfigData;
import xaero.hud.minimap.common.config.option.value.type.MinimapConfigValueTypes;
import xaero.hud.minimap.common.config.util.MinimapConfigUtils;
import xaero.hud.minimap.radar.category.EntityRadarCategoryConstants;
import xaero.hud.minimap.radar.category.serialization.data.EntityRadarCategoryData;
import xaero.lib.common.config.option.BooleanConfigOption;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.option.ConfigOptionManager;
import xaero.lib.common.config.option.IndexedConfigOption;
import xaero.lib.common.config.option.RangeConfigOption;
import xaero.lib.common.config.option.SteppedConfigOption;
import xaero.lib.common.config.option.BooleanConfigOption.Builder;
import xaero.lib.common.config.option.ConfigOption.FinalBuilder;
import xaero.lib.common.config.option.value.type.BuiltInConfigValueTypes;
import xaero.lib.common.config.util.ConfigConstants;
import xaero.lib.common.config.util.ConfigUtils;

public class MinimapProfiledConfigOptions {
   private static final List<ConfigOption<?>> ALL = new ArrayList<>();
   public static final BooleanConfigOption DISPLAY_MINIMAP = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("display_minimap"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_minimap")))
         .setTooltip(Component.translatable("gui.xaero_box_minimap")))
      .build(ALL);
   public static final ConfigOption<String> MINIMAP_ITEM = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                        .setId("minimap_item"))
                     .setDefaultValue("-"))
                  .setValueType(BuiltInConfigValueTypes.getString(BuiltInConfigValueTypes.RESOURCE_LOCATION.getIoCodec().getMaxStringLength())))
               .setDisplayGetter(ConfigUtils::getDisplayForString))
            .setDisplayName(Component.translatable("gui.xaero_minimap_item")))
         .setTooltip(Component.translatable("gui.xaero_box_minimap_item")))
      .build(ALL);
   public static final BooleanConfigOption SAFE_MODE = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_safe_mode"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_safe_mode")))
         .setTooltip(Component.translatable("gui.xaero_safe_mode_box")))
      .build(ALL);
   public static final RangeConfigOption UI_SCALE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_ui_scale"))
                        .setDefaultValue(1))
                     .setMinIndex(1))
                  .setMaxIndex(11))
               .setDisplayName(Component.translatable("gui.xaero_ui_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_ui_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(1, 11)))
      .build(ALL);
   public static final ConfigOption<EntityRadarCategoryData> RADAR_CATEGORIES = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                                 .setId("entity_radar_categories"))
                              .setDefaultValue(EntityRadarCategoryConstants.NULL_DATA))
                           .setDisplayGetter((o, v) -> Component.literal("")))
                        .setShouldSaveDefaultValue(false))
                     .setDisplayName(Component.translatable("gui.xaero_entity_radar_categories")))
                  .setTooltip(Component.translatable("gui.xaero_box_entity_radar_categories")))
               .setValueType(MinimapConfigValueTypes.ENTITY_RADAR_CATEGORIES))
            .setOverridable(false))
         .setSkipConfigReset(true))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_RADAR = ((Builder)((Builder)((Builder)Builder.begin().setId("display_radar")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_radar_setting_displayed")))
      .build(ALL);
   public static final RangeConfigOption RADAR_DOTS_STYLE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("radar_dots_style"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(MinimapConfigConstants.DOTS_STYLES.length - 1))
            .setDisplayName(Component.translatable("gui.xaero_dots_style")))
         .setDisplayGetter((o, v) -> MinimapConfigConstants.DOTS_STYLES[v]))
      .build(ALL);
   public static final BooleanConfigOption RADAR_SMOOTH_DOTS = ((Builder)((Builder)((Builder)Builder.begin().setId("radar_smooth_dots")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_smooth_dots")))
      .build(ALL);
   public static final SteppedConfigOption RADAR_NAME_SCALE = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                  .setId("radar_name_scale"))
               .setDefaultValue(1.0))
            .setMinValue(1.0)
            .setMaxValue(3.0)
            .setStep(0.5)
            .setDisplayName(Component.translatable("gui.xaero_dot_name_scale")))
         .setValidator(v -> v > 0.0))
      .build(ALL);
   public static final RangeConfigOption RADAR_MAIN_ENTITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("radar_main_entity"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(2))
            .setDisplayName(Component.translatable("gui.xaero_main_entity_as")))
         .setDisplayGetter((o, v) -> MinimapConfigConstants.MAIN_ENTITY_TYPES[v]))
      .build(ALL);
   public static final RangeConfigOption RADAR_MAIN_DOT_SIZE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("radar_main_dot_size"))
                  .setDefaultValue(2))
               .setMinIndex(1))
            .setMaxIndex(4))
         .setDisplayName(Component.translatable("gui.xaero_main_entity_dot_size")))
      .build(ALL);
   public static final BooleanConfigOption RADAR_HIDE_INVISIBLE = ((Builder)((Builder)((Builder)Builder.begin().setId("radar_hide_invisible_entities"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_hide_invisible_entities")))
      .build(ALL);
   public static final SteppedConfigOption ARROW_SCALE = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                        .setId("minimap_arrow_scale"))
                     .setDefaultValue(1.5))
                  .setMinValue(1.0)
                  .setMaxValue(2.0)
                  .setStep(0.1)
                  .setDisplayName(Component.translatable("gui.xaero_arrow_scale")))
               .setTooltip(Component.translatable("gui.xaero_box_arrow_scale")))
            .setValidator(v -> v >= 0.0))
         .setDisplayGetter((c, v) -> Component.literal(v + "x")))
      .build(ALL);
   public static final RangeConfigOption ARROW_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_arrow_color"))
                        .setDefaultValue(0))
                     .setMinIndex(-1))
                  .setMaxIndex(MinimapConfigConstants.ARROW_COLORS.length - 1))
               .setDisplayName(Component.translatable("gui.xaero_arrow_colour")))
            .setTooltip(Component.translatable("gui.xaero_box_arrow_color")))
         .setDisplayGetter((o, v) -> (Component)(v != -1 ? MinimapConfigConstants.ARROW_COLOR_NAMES[v] : Component.translatable("gui.xaero_team"))))
      .build(ALL);
   public static final RangeConfigOption ARROW_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_arrow_opacity"))
                  .setDefaultValue(100))
               .setMinIndex(1))
            .setMaxIndex(100))
         .setDisplayName(Component.translatable("gui.xaero_player_arrow_opacity")))
      .build(ALL);
   public static final BooleanConfigOption TRACKED_PLAYERS_ON_MINIMAP = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("tracked_players_on_minimap"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_tracked_players_on_map")))
         .setTooltip(Component.translatable("gui.xaero_box_tracked_players_on_map")))
      .build(ALL);
   public static final BooleanConfigOption TRACKED_PLAYERS_IN_WORLD = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("tracked_players_in_world"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_tracked_players_in_world")))
         .setTooltip(Component.translatable("gui.xaero_box_tracked_players_in_world")))
      .build(ALL);
   public static final RangeConfigOption TRACKED_PLAYER_MINIMAP_ICON_SCALE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("tracked_player_minimap_icon_scale"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_tracked_player_minimap_icon_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_tracked_player_minimap_icon_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17)))
      .build(ALL);
   public static final RangeConfigOption TRACKED_PLAYER_WORLD_ICON_SCALE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("tracked_player_world_icon_scale"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_tracked_player_world_icon_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_tracked_player_world_icon_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17)))
      .build(ALL);
   public static final RangeConfigOption TRACKED_PLAYER_WORLD_NAME_SCALE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("tracked_player_world_name_scale"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_tracked_player_world_name_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_tracked_player_world_name_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17)))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINTS_IN_WORLD = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoints_in_world"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_ingame_waypoints")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINTS_ON_MINIMAP = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoints_on_minimap"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_display_waypoints")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINTS_ALL_SETS = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoints_all_sets"))
            .setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_render_all_wp_sets")))
      .build(ALL);
   public static final BooleanConfigOption DEATHPOINTS = ((Builder)((Builder)((Builder)Builder.begin().setId("deathpoints")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_deathpoints")))
      .build(ALL);
   public static final BooleanConfigOption OLD_DEATHPOINTS = ((Builder)((Builder)((Builder)Builder.begin().setId("old_deathpoints")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_old_deathpoints")))
      .build(ALL);
   public static final BooleanConfigOption DELETE_REACHED_DEATHPOINTS = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("delete_reached_deathpoints"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_delete_reached_deathpoints")))
         .setTooltip(Component.translatable("gui.xaero_box_delete_reached_deathpoints")))
      .build(ALL);
   public static final BooleanConfigOption AUTO_WAYPOINTS_ON_DEATH = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("switch_auto_waypoints_on_death"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_switch_to_auto_on_death")))
         .setTooltip(Component.translatable("gui.xaero_box_switch_to_auto_on_death")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_ICON_SCALE_IN_WORLD = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("waypoint_icon_scale_in_world"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_ingame_waypoint_icon_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_ingame_waypoint_icon_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17)))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_NAME_SCALE_IN_WORLD = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("waypoint_name_scale_in_world"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_waypoints_name_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_waypoints_name_scale2")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17, 0.5)))
      .build(ALL);
   public static final SteppedConfigOption WAYPOINT_CLOSE_SCALE_IN_WORLD = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                     .setId("waypoint_close_scale_in_world"))
                  .setDefaultValue(1.0))
               .setMinValue(0.125)
               .setMaxValue(8.0)
               .setStep(0.025)
               .setValidator(v -> v > 0.0))
            .setDisplayName(Component.translatable("gui.xaero_ingame_waypoint_close_scale")))
         .setTooltip(Component.translatable("gui.xaero_box_ingame_waypoint_close_scale")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_DISTANCE_SCALE_IN_WORLD = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("waypoint_distance_scale_in_world"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_waypoints_distance_scale")))
            .setTooltip(Component.translatable("gui.xaero_box_waypoints_distance_scale2")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17)))
      .build(ALL);
   public static final RangeConfigOption MULTIPLE_WAYPOINTS_INFO = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("multiple_waypoints_info"))
                        .setDefaultValue(1))
                     .setMinIndex(0))
                  .setMaxIndex(MinimapConfigConstants.MULTIPLE_WAYPOINTS_INFO_NAMES.length - 1))
               .setDisplayName(Component.translatable("gui.xaero_multiple_waypoint_info")))
            .setTooltip(Component.translatable("gui.xaero_box_multiple_waypoint_info")))
         .setDisplayGetter((o, v) -> MinimapConfigConstants.MULTIPLE_WAYPOINTS_INFO_NAMES[v]))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_DISTANCE_IN_WORLD = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("waypoint_distance_in_world"))
                        .setDefaultValue(1))
                     .setMinIndex(0))
                  .setMaxIndex(MinimapConfigConstants.WAYPOINT_DISTANCE_NAMES.length - 1))
               .setDisplayName(Component.translatable("gui.xaero_show_distance")))
            .setTooltip(Component.translatable("gui.xaero_box_distance2")))
         .setDisplayGetter((o, v) -> MinimapConfigConstants.WAYPOINT_DISTANCE_NAMES[v]))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_NAME_IN_WORLD = ((Builder)((Builder)((Builder)Builder.begin().setId("waypoint_name_in_world"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_names")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_SHORT_DISTANCE_IN_WORLD = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("waypoint_short_distance_in_world"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_always_show_distance")))
         .setTooltip(Component.translatable("gui.xaero_box_always_distance")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_VERTICAL_POINTING_ANGLE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("waypoint_vertical_pointing_angle"))
                  .setDefaultValue(180))
               .setMinIndex(1))
            .setMaxIndex(180))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_distance_vertical_visibility_angle")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_HORIZONTAL_POINTING_ANGLE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("waypoint_horizontal_pointing_angle"))
                  .setDefaultValue(10))
               .setMinIndex(1))
            .setMaxIndex(180))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_distance_visibility_angle")))
      .build(ALL);
   public static final IndexedConfigOption<Integer> WAYPOINT_MAX_DISTANCE = ((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder.begin()
                                    .setId("waypoint_max_distance"))
                                 .setValueType(BuiltInConfigValueTypes.INTEGER))
                              .setDefaultValue(0))
                           .setMinIndex(0))
                        .setMaxIndex(20))
                     .setIndexValueGetter(i -> i <= 0 ? 0 : (int)Math.pow(2.0, 2 + i)))
                  .setDisplayGetter((o, v) -> (Component)(v <= 0 ? MinimapConfigConstants.UNLIMITED_COMPONENT : Component.literal(v + "m"))))
               .setValidator(v -> v >= 0))
            .setDisplayName(Component.translatable("gui.xaero_waypoints_distance")))
         .setTooltip(Component.translatable("gui.xaero_box_waypoints_distance2")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_MAX_DISTANCE_DIMENSION_SCALE = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("waypoint_max_distance_dimension_scale"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_dimension_scaled_max_waypoint_distance")))
         .setTooltip(Component.translatable("gui.xaero_box_dimension_scaled_max_waypoint_distance")))
      .build(ALL);
   public static final SteppedConfigOption WAYPOINT_MIN_DISTANCE_IN_WORLD = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                  .setId("waypoint_min_distance_in_world"))
               .setDefaultValue(0.0))
            .setMinValue(0.0)
            .setMaxValue(100.0)
            .setStep(5.0)
            .setRangeValidator(true)
            .setDisplayGetter((o, v) -> v <= 0.0 ? ConfigConstants.OFF : Component.literal(v + "m")))
         .setDisplayName(Component.translatable("gui.xaero_waypoints_distance_min")))
      .build(ALL);
   public static final IndexedConfigOption<Integer> WAYPOINT_CONVERT_DISTANCE_TO_KM_AT = ((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder.begin()
                              .setId("waypoint_convert_distance_to_km_at"))
                           .setValueType(BuiltInConfigValueTypes.INTEGER))
                        .setDefaultValue(10000))
                     .setMinIndex(-1))
                  .setMaxIndex(10))
               .setIndexValueGetter(i -> i <= 0 ? i : (int)Math.pow(10.0, i - 1)))
            .setDisplayGetter((o, v) -> (Component)(v == -1 ? MinimapConfigConstants.CONVERT_DISTANCE_NEVER_COMPONENT : Component.literal(v + "m"))))
         .setDisplayName(Component.translatable("gui.xaero_auto_convert_wp_distance_km")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_DISTANCE_PRECISION = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("waypoint_distance_precision"))
                  .setDefaultValue(1))
               .setMinIndex(0))
            .setMaxIndex(10))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_distance_precision")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_OPACITY_IN_WORLD = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("waypoint_opacity_in_world"))
                  .setDefaultValue(80))
               .setMinIndex(10))
            .setMaxIndex(100))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_opacity_ingame")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_OPACITY_ON_MINIMAP = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("waypoint_opacity_on_minimap"))
                  .setDefaultValue(90))
               .setMinIndex(10))
            .setMaxIndex(100))
         .setDisplayName(Component.translatable("gui.xaero_waypoint_opacity_map")))
      .build(ALL);
   public static final RangeConfigOption WAYPOINT_ICON_SCALE_ON_MINIMAP = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("waypoint_icon_scale_on_minimap"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(17))
            .setDisplayName(Component.translatable("gui.xaero_waypoint_onmap_scale")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17, false)))
      .build(ALL);
   public static final BooleanConfigOption TEMPORARY_WAYPOINTS_GLOBAL = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("temporary_waypoints_global"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_temp_waypoints_global")))
         .setTooltip(Component.translatable("gui.xaero_box_temp_waypoints_global")))
      .build(ALL);
   public static final ConfigOption<String> WAYPOINT_DEFAULT_TELEPORT_FORMAT = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                     .setId("default_waypoint_teleport_format"))
                  .setDefaultValue("/tp @s {x} {y} {z}"))
               .setValueType(BuiltInConfigValueTypes.getString(500)))
            .setDisplayName(Component.translatable("gui.xaero_teleport_default_command")))
         .setTooltip(Component.translatable("gui.xaero_box_teleport_default_command")))
      .build(ALL);
   public static final ConfigOption<String> WAYPOINT_DEFAULT_TELEPORT_ROTATION_FORMAT = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                     .setId("default_waypoint_teleport_rotation_format"))
                  .setDefaultValue("/tp @s {x} {y} {z} {yaw} ~"))
               .setValueType(BuiltInConfigValueTypes.getString(550)))
            .setDisplayName(Component.translatable("gui.xaero_teleport_default_command_rotation")))
         .setTooltip(Component.translatable("gui.xaero_box_teleport_default_command_rotation")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_TELEPORT_CROSS_DIMENSION = ((Builder)((Builder)((Builder)Builder.begin()
               .setId("waypoint_teleport_cross_dimension"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_cross_tp")))
      .build(ALL);
   public static final BooleanConfigOption WAYPOINT_PARTIAL_Y_TELEPORT = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("waypoint_partial_y_teleport"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_partial_y_teleportation")))
         .setTooltip(Component.translatable("gui.xaero_box_partial_y_teleportation")))
      .build(ALL);
   public static final BooleanConfigOption NEW_WAYPOINTS_TO_BOTTOM = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("new_waypoints_to_bottom"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_waypoints_bottom")))
         .setTooltip(Component.translatable("gui.xaero_box_waypoints_bottom")))
      .build(ALL);
   public static final RangeConfigOption HIDE_WORLD_NAMES = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("hide_world_names"))
                     .setDefaultValue(1))
                  .setMinIndex(0))
               .setMaxIndex(MinimapConfigConstants.HIDE_WORLD_NAMES_NAMES.length - 1))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.HIDE_WORLD_NAMES_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_hide_world_names")))
      .build(ALL);
   public static final BooleanConfigOption COMPASS_OVER_EVERYTHING = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("minimap_compass_over_everything"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_compass_over_everything")))
         .setTooltip(Component.translatable("gui.xaero_box_compass_over_everything")))
      .build(ALL);
   public static final BooleanConfigOption HIDE_WAYPOINT_COORDINATES = ((Builder)((Builder)((Builder)Builder.begin().setId("hide_waypoint_coordinates"))
            .setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_hide_wp_coords")))
      .build(ALL);
   public static final RangeConfigOption COMPASS_LOCATION = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_compass_location"))
                        .setDefaultValue(1))
                     .setMinIndex(0))
                  .setMaxIndex(MinimapConfigConstants.COMPASS_LOCATION_NAMES.length - 1))
               .setDisplayGetter((o, v) -> MinimapConfigConstants.COMPASS_LOCATION_NAMES[v]))
            .setDisplayName(Component.translatable("gui.xaero_compass")))
         .setTooltip(Component.translatable("gui.xaero_box_compass")))
      .build(ALL);
   public static final RangeConfigOption COMPASS_SHADOW_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_compass_shadow_color"))
                        .setDefaultValue(9))
                     .setMinIndex(0))
                  .setMaxIndex(MinimapConfigConstants.COLORS.length - 1))
               .setDisplayGetter((o, v) -> MinimapConfigConstants.COLOR_NAMES[v].copy().withColor(MinimapConfigConstants.COLORS[v])))
            .setDisplayName(Component.translatable("gui.xaero_compass_color")))
         .setTooltip(Component.translatable("gui.xaero_box_compass_color")))
      .build(ALL);
   public static final RangeConfigOption COMPASS_NORTH_SHADOW_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_compass_north_shadow_color"))
                        .setDefaultValue(-1))
                     .setMinIndex(-1))
                  .setMaxIndex(MinimapConfigConstants.COLORS.length - 1))
               .setDisplayGetter(
                  (o, v) -> v < 0
                     ? Component.translatable("gui.xaero_north_compass_color_default")
                     : MinimapConfigConstants.COLOR_NAMES[v].copy().withColor(MinimapConfigConstants.COLORS[v])
               ))
            .setDisplayName(Component.translatable("gui.xaero_north_compass_color")))
         .setTooltip(Component.translatable("gui.xaero_box_north_compass_color")))
      .build(ALL);
   public static final RangeConfigOption COMPASS_SCALE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_compass_scale"))
                        .setDefaultValue(0))
                     .setMinIndex(0))
                  .setMaxIndex(17))
               .setDisplayName(Component.translatable("gui.xaero_compass_scale2")))
            .setTooltip(Component.translatable("gui.xaero_box_compass_scale2")))
         .setDisplayGetter(MinimapConfigUtils.getUIScaleDisplayGetter(0, 17, false)))
      .build(ALL);
   public static final RangeConfigOption BLOCK_COLORS = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_block_colors"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(MinimapConfigConstants.BLOCK_COLOR_NAMES.length - 1))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.BLOCK_COLOR_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_block_colours")))
      .build(ALL);
   public static final BooleanConfigOption BIOMES_IN_VANILLA_COLORS = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_biomes_in_vanilla_colors"))
            .setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_biomes_vanilla")))
      .build(ALL);
   public static final BooleanConfigOption CAVE_MODE_ALLOWED = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_cave_mode_allowed"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_cave_maps")))
         .setTooltip(Component.translatable("gui.xaero_box_cave_mode_allowed")))
      .build(ALL);
   public static final RangeConfigOption AUTO_CAVE_MODE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_auto_cave_mode"))
                        .setDefaultValue(2))
                     .setMinIndex(0))
                  .setMaxIndex(3))
               .setDisplayGetter((o, v) -> {
                  if (v == 0) {
                     return ConfigConstants.OFF;
                  }

                  int roofSideSize = v * 2 - 1;
                  MutableComponent result = Component.literal(roofSideSize + "x" + roofSideSize + " ");
                  result.getSiblings().add(Component.translatable("gui.xaero_roof"));
                  return result;
               }))
            .setDisplayName(Component.translatable("gui.xaero_mm_auto_cave_mode")))
         .setTooltip(Component.translatable("gui.xaero_mm_box_auto_cave_mode")))
      .build(ALL);
   public static final ConfigOption<Set<ResourceLocation>> CAVE_MODE_ALLOWED_DIMENSIONS = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                        .setId("minimap_cave_mode_allowed_dimensions"))
                     .setDefaultValue(Collections.unmodifiableSet(new LinkedHashSet())))
                  .setValueType(
                     xaero.lib.common.config.option.value.type.CollectionConfigValueType.Builder.begin()
                        .setElementValueType(BuiltInConfigValueTypes.RESOURCE_LOCATION)
                        .setIoCodecSeparator(',')
                        .build()
                  ))
               .setDisplayName(Component.translatable("gui.xaero_cave_mode_allowed_dimensions")))
            .setTooltip(Component.translatable("gui.xaero_box_cave_mode_allowed_dimensions")))
         .setOverridable(false))
      .build(ALL);
   public static final IndexedConfigOption<Integer> MANUAL_CAVE_MODE_START = ((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder.begin()
                                    .setId("minimap_manual_cave_mode_start"))
                                 .setValueType(BuiltInConfigValueTypes.INTEGER))
                              .setDefaultValue(Integer.MAX_VALUE))
                           .setMinIndex(0))
                        .setMaxIndex(48))
                     .setIndexValueGetter(i -> i <= 0 ? Integer.MAX_VALUE : -65 + i * 8))
                  .setDisplayGetter(
                     (o, v) -> v == Integer.MAX_VALUE ? Component.translatable("gui.xaero_manual_cave_mode_start_auto") : Component.literal(v + "")
                  ))
               .setValidator(i -> true))
            .setDisplayName(Component.translatable("gui.xaero_manual_cave_mode_start")))
         .setTooltip(Component.translatable("gui.xaero_box_manual_cave_mode_start")))
      .build(ALL);
   public static final RangeConfigOption CAVE_MODE_DEPTH = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_cave_mode_depth"))
                  .setDefaultValue(30))
               .setMinIndex(0))
            .setMaxIndex(64))
         .setDisplayName(Component.translatable("gui.xaero_cave_maps_depth")))
      .build(ALL);
   public static final BooleanConfigOption LEGIBLE_CAVE_MAPS = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_legible_cave_maps"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_legible_cave_maps")))
         .setTooltip(Component.translatable("gui.xaero_box_legible_cave_maps")))
      .build(ALL);
   public static final SteppedConfigOption CAVE_MODE_TOGGLE_TIMER = ((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)((xaero.lib.common.config.option.SteppedConfigOption.Builder)xaero.lib.common.config.option.SteppedConfigOption.Builder.begin()
                     .setId("minimap_cave_mode_toggle_timer"))
                  .setDefaultValue(1000.0))
               .setMinValue(0.0)
               .setMaxValue(10000.0)
               .setStep(100.0)
               .setRangeValidator(true)
               .setDisplayGetter((o, v) -> ConfigUtils.getDisplayForSimpleNumber(o, v, Component.translatable("gui.xaero_unit_ms"))))
            .setDisplayName(Component.translatable("gui.xaero_cave_mode_toggle_timer")))
         .setTooltip(Component.translatable("gui.xaero_box_cave_mode_toggle_timer")))
      .build(ALL);
   public static final BooleanConfigOption BIOME_BLENDING = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_biome_blending"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_biome_blending")))
         .setTooltip(Component.translatable("gui.xaero_box_biome_blending")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_WORLD_MAP_CHUNKS = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_display_world_map_chunks"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_use_world_map")))
      .build(ALL);
   public static final BooleanConfigOption TERRAIN_DEPTH = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_terrain_depth")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_terrain_depth")))
      .build(ALL);
   public static final RangeConfigOption TERRAIN_SLOPES = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_terrain_slopes"))
                     .setDefaultValue(2))
                  .setMinIndex(0))
               .setMaxIndex(3))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.TERRAIN_SLOPES_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_terrain_slopes")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_REDSTONE = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_display_redstone"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_display_redstone")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_FLOWERS = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_display_flowers"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_show_flowers")))
      .build(ALL);
   public static final BooleanConfigOption BLOCK_TRANSPARENCY = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_block_transparency"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_block_transparency")))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_STAINED_GLASS = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_display_stained_glass"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_display_stained_glass")))
      .build(ALL);
   public static final BooleanConfigOption ADJUST_HEIGHT_FOR_SHORT_BLOCKS = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("minimap_adjust_height_for_short_blocks"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_adjust_height_for_carpetlike_blocks")))
         .setTooltip(Component.translatable("gui.xaero_box_adjust_height_for_carpetlike_blocks")))
      .build(ALL);
   public static final BooleanConfigOption ANTI_ALIASING = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_anti_aliasing")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_antialiasing")))
      .build(ALL);
   public static final IndexedConfigOption<Integer> SIZE = ((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)((xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder)xaero.lib.common.config.option.IndexedConfigOption.FinalBuilder.begin()
                                 .setId("minimap_size"))
                              .setValueType(BuiltInConfigValueTypes.INTEGER))
                           .setDefaultValue(0))
                        .setMinIndex(0))
                     .setMaxIndex(196))
                  .setIndexValueGetter(i -> i == 0 ? 0 : i + 54))
               .setDisplayGetter((o, v) -> (Component)(v == 0 ? MinimapConfigUtils.getAutoMinimapSizeName() : Component.literal(v + ""))))
            .setDisplayName(Component.translatable("gui.xaero_minimap_size")))
         .setTooltip(Component.translatable("gui.xaero_box_minimap_size")))
      .build(ALL);
   public static final RangeConfigOption SHAPE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_shape"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(1))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.MINIMAP_SHAPE_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_minimap_shape")))
      .build(ALL);
   public static final BooleanConfigOption NORTH_LOCKED = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_north_locked")).setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_lock_north")))
      .build(ALL);
   public static final RangeConfigOption ZOOM = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_zoom"))
                     .setDefaultValue(1))
                  .setMinIndex(1))
               .setMaxIndex(5))
            .setDisplayGetter((o, v) -> Component.literal(v + "x")))
         .setDisplayName(Component.translatable("gui.xaero_zoom")))
      .build(ALL);
   public static final RangeConfigOption CAVE_ZOOM = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_cave_mode_extra_zoom"))
                        .setDefaultValue(2))
                     .setMinIndex(1))
                  .setMaxIndex(4))
               .setDisplayGetter((o, v) -> Component.literal(v + "x")))
            .setDisplayName(Component.translatable("gui.xaero_cave_zoom")))
         .setTooltip(Component.translatable("gui.xaero_box_cave_zoom")))
      .build(ALL);
   public static final RangeConfigOption ZOOM_ENLARGED = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_enlarged_zoom"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(5))
            .setDisplayGetter((o, v) -> v == 0 ? Component.translatable("gui.xaero_zoom_on_enlarge_auto") : Component.literal(v + "x")))
         .setDisplayName(Component.translatable("gui.xaero_zoom_on_enlarge")))
      .build(ALL);
   public static final BooleanConfigOption CENTERED_ENLARGED = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_centered_when_enlarged"))
            .setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_centered_enlarged")))
      .build(ALL);
   public static final BooleanConfigOption KEEP_ENLARGED_UNLOCKED = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("keep_enlarged_minimap_unlocked"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_keep_enlarged_minimap_unlocked")))
         .setTooltip(Component.translatable("gui.xaero_box_keep_enlarged_minimap_unlocked")))
      .build(ALL);
   public static final BooleanConfigOption TOGGLED_ENLARGED = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_toggled_enlarged"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_enlarged_minimap_a_toggle")))
         .setTooltip(Component.translatable("gui.xaero_box_enlarged_minimap_a_toggle")))
      .build(ALL);
   public static final BooleanConfigOption LIGHTING = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_lighting")).setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_lighting")))
      .build(ALL);
   public static final RangeConfigOption OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_opacity"))
                  .setDefaultValue(100))
               .setMinIndex(30))
            .setMaxIndex(100))
         .setDisplayName(Component.translatable("gui.xaero_opacity")))
      .build(ALL);
   public static final RangeConfigOption FRAME = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_frame"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(MinimapConfigConstants.FRAME_NAMES.length - 1))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.FRAME_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_minimap_frame")))
      .build(ALL);
   public static final RangeConfigOption FRAME_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_frame_color"))
                        .setDefaultValue(9))
                     .setMinIndex(0))
                  .setMaxIndex(MinimapConfigConstants.COLOR_NAMES.length - 1))
               .setDisplayGetter((o, v) -> MinimapConfigConstants.COLOR_NAMES[v].copy().withColor(MinimapConfigConstants.COLORS[v])))
            .setDisplayName(Component.translatable("gui.xaero_minimap_frame_color")))
         .setTooltip(Component.translatable("gui.xaero_box_minimap_frame_color")))
      .build(ALL);
   public static final BooleanConfigOption HIDE_UNDER_SCREEN = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("hide_minimap_under_screen"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_hide_minimap_under_screen")))
         .setTooltip(Component.translatable("gui.xaero_box_hide_minimap_under_screen")))
      .build(ALL);
   public static final BooleanConfigOption HIDE_UNDER_F3 = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("hide_minimap_under_f3"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_hide_minimap_under_f3")))
         .setTooltip(Component.translatable("gui.xaero_box_hide_minimap_under_f3")))
      .build(ALL);
   public static final RangeConfigOption BOSS_HEALTH_PUSH_BOX = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("boss_health_push_box"))
                        .setDefaultValue(getDefaultBossHealthPushBox()))
                     .setMinIndex(0))
                  .setMaxIndex(2))
               .setDisplayGetter((o, v) -> MinimapConfigConstants.PUSH_BOX_NAMES[v]))
            .setDisplayName(Component.translatable("gui.xaero_pushbox_boss_health")))
         .setTooltip(Component.translatable("gui.xaero_box_pushbox_boss_health")))
      .build(ALL);
   public static final RangeConfigOption POTION_EFFECT_PUSH_BOX = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("potion_effect_push_box"))
                        .setDefaultValue(1))
                     .setMinIndex(0))
                  .setMaxIndex(2))
               .setDisplayGetter((o, v) -> MinimapConfigConstants.PUSH_BOX_NAMES[v]))
            .setDisplayName(Component.translatable("gui.xaero_pushbox_potion_effects")))
         .setTooltip(Component.translatable("gui.xaero_box_pushbox_potion_effects")))
      .build(ALL);
   public static final RangeConfigOption CHUNK_GRID = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("minimap_chunk_grid"))
                        .setDefaultValue(-1))
                     .setMinIndex(-1))
                  .setMaxIndex(MinimapConfigConstants.COLORS.length - 1))
               .setRangeValidator(false)
               .setValidator(v -> v >= -MinimapConfigConstants.COLORS.length && v < MinimapConfigConstants.COLORS.length))
            .setDisplayGetter((o, v) -> v < 0 ? ConfigConstants.OFF : MinimapConfigConstants.COLOR_NAMES[v].copy().withColor(MinimapConfigConstants.COLORS[v])))
         .setDisplayName(Component.translatable("gui.xaero_chunkgrid")))
      .build(ALL);
   public static final RangeConfigOption CHUNK_GRID_LINE_WIDTH = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_chunk_grid_line_width"))
                  .setDefaultValue(1))
               .setMinIndex(1))
            .setMaxIndex(8))
         .setDisplayName(Component.translatable("gui.xaero_chunk_grid_line_width")))
      .build(ALL);
   public static final BooleanConfigOption SLIME_CHUNKS = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_slime_chunks")).setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_slime_chunks")))
      .build(ALL);
   public static final BooleanConfigOption OPEN_SLIME_CHUNKS_SCREEN = ((Builder)((Builder)((Builder)Builder.begin().setId("minimap_open_slime_chunks_screen"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_open_slime")))
      .build(ALL);
   public static final RangeConfigOption LIGHT_OVERLAY_TYPE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                              .setId("minimap_light_overlay_type"))
                           .setDefaultValue(0))
                        .setMinIndex(0))
                     .setMaxIndex(3))
                  .setRangeValidator(false)
                  .setValidator(v -> v >= -3 && v <= 3))
               .setDisplayGetter((o, v) -> {
                  if (v < 0) {
                     v = v * -1;
                  }

                  return MinimapConfigConstants.LIGHT_NAMES[v];
               }))
            .setDisplayName(Component.translatable("gui.xaero_light_overlay_type")))
         .setTooltip(Component.translatable("gui.xaero_box_light_overlay_type")))
      .build(ALL);
   public static final RangeConfigOption LIGHT_OVERLAY_MAX_LIGHT = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_light_overlay_max_light"))
                  .setDefaultValue(7))
               .setMinIndex(0))
            .setMaxIndex(15))
         .setDisplayName(Component.translatable("gui.xaero_light_overlay_max_light")))
      .build(ALL);
   public static final RangeConfigOption LIGHT_OVERLAY_MIN_LIGHT = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("minimap_light_overlay_min_light"))
                  .setDefaultValue(0))
               .setMinIndex(0))
            .setMaxIndex(15))
         .setDisplayName(Component.translatable("gui.xaero_light_overlay_min_light")))
      .build(ALL);
   public static final RangeConfigOption LIGHT_OVERLAY_COLOR = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_light_overlay_color"))
                     .setDefaultValue(13))
                  .setMinIndex(0))
               .setMaxIndex(15))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.COLOR_NAMES[v].copy().withColor(MinimapConfigConstants.COLORS[v])))
         .setDisplayName(Component.translatable("gui.xaero_light_overlay_color")))
      .build(ALL);
   public static final BooleanConfigOption OPAC_CLAIMS = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("minimap_display_opac_claims"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_pac_claims")))
         .setTooltip(Component.translatable("gui.xaero_box_pac_claims")))
      .build(ALL);
   public static final RangeConfigOption OPAC_CLAIMS_FILL_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_opac_claim_fill_opacity"))
                     .setDefaultValue(46))
                  .setMinIndex(1))
               .setMaxIndex(100))
            .setDisplayName(Component.translatable("gui.xaero_pac_claims_fill_opacity")))
         .setTooltip(Component.translatable("gui.xaero_box_pac_claims_fill_opacity")))
      .build(ALL);
   public static final RangeConfigOption OPAC_CLAIMS_BORDER_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_opac_claim_border_opacity"))
                     .setDefaultValue(80))
                  .setMinIndex(1))
               .setMaxIndex(100))
            .setDisplayName(Component.translatable("gui.xaero_pac_claims_border_opacity")))
         .setTooltip(Component.translatable("gui.xaero_box_pac_claims_border_opacity")))
      .build(ALL);
   public static final BooleanConfigOption OPAC_CURRENT_CLAIM = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("minimap_display_opac_current_claim"))
               .setDefaultValue(true))
            .setDisplayName(Component.translatable("gui.xaero_pac_current_claim")))
         .setTooltip(Component.translatable("gui.xaero_box_pac_current_claim")))
      .build(ALL);
   public static final RangeConfigOption INFO_DISPLAY_BG_OPACITY = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_info_display_background_opacity"))
                     .setDefaultValue(40))
                  .setMinIndex(1))
               .setMaxIndex(100))
            .setDisplayName(Component.translatable("gui.xaero_info_display_background_opacity")))
         .setTooltip(Component.translatable("gui.xaero_box_info_display_background_opacity")))
      .build(ALL);
   public static final RangeConfigOption INFO_DISPLAY_ALIGNMENT = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                        .setId("minimap_info_display_alignment"))
                     .setDefaultValue(0))
                  .setMinIndex(0))
               .setMaxIndex(2))
            .setDisplayGetter((o, v) -> MinimapConfigConstants.INFO_DISPLAY_ALIGNMENT_NAMES[v]))
         .setDisplayName(Component.translatable("gui.xaero_minimap_text_align")))
      .build(ALL);
   public static final ConfigOption<InfoDisplayManagerConfigData> INFO_DISPLAY_CONFIG = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                              .setId("info_display_config"))
                           .setDefaultValue(InfoDisplayManagerConfigData.EMPTY))
                        .setDisplayGetter((o, v) -> Component.literal("")))
                     .setShouldSaveDefaultValue(false))
                  .setDisplayName(Component.translatable("gui.xaero_minimap_info_display_manager")))
               .setTooltip(Component.translatable("gui.xaero_box_minimap_info_display_manager")))
            .setValueType(MinimapConfigValueTypes.INFO_DISPLAY_CONFIG))
         .setOverridable(false))
      .build(ALL);
   public static final RangeConfigOption RENDER_LAYER = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                     .setId("hud_render_layer"))
                  .setDefaultValue(1))
               .setMinIndex(0))
            .setMaxIndex(100))
         .setDisplayName(Component.translatable("gui.xaero_render_layer")))
      .build(ALL);

   public static void registerAll(ConfigOptionManager manager) {
      for (ConfigOption<?> option : ALL) {
         manager.register(option);
      }
   }

   private static Integer getDefaultBossHealthPushBox() {
      try {
         Class.forName("xaero.pvp.BetterPVP");
         return 2;
      } catch (ClassNotFoundException e) {
         return 1;
      }
   }
}
