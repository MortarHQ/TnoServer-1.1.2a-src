package xaero.map.config.primary.option;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import xaero.lib.client.config.option.ClientConfigOptionManager;
import xaero.lib.common.config.option.BooleanConfigOption;
import xaero.lib.common.config.option.ConfigOption;
import xaero.lib.common.config.option.RangeConfigOption;
import xaero.lib.common.config.option.BooleanConfigOption.Builder;
import xaero.lib.common.config.option.ConfigOption.FinalBuilder;
import xaero.lib.common.config.option.value.type.BuiltInConfigValueTypes;
import xaero.lib.common.config.util.ConfigUtils;

public class WorldMapPrimaryClientConfigOptions {
   private static final List<ConfigOption<?>> ALL = new ArrayList<>();
   public static final ConfigOption<Integer> IGNORED_UPDATE = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("ignored_update"))
               .setDefaultValue(0))
            .setValueType(BuiltInConfigValueTypes.INTEGER))
         .setDisplayGetter(ConfigUtils::getDisplayForSimpleNumber))
      .build(ALL);
   public static final BooleanConfigOption UPDATE_NOTIFICATIONS = ((Builder)((Builder)((Builder)Builder.begin().setId("update_notifications"))
            .setDefaultValue(true))
         .setDisplayName(Component.translatable("gui.xaero_wm_update_notification")))
      .build(ALL);
   public static final BooleanConfigOption RELOAD_VIEWED = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("reload_viewed"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_reload_viewed_regions")))
         .setTooltip(Component.translatable("gui.xaero_box_reload_viewed_regions")))
      .build(ALL);
   public static final ConfigOption<Integer> RELOAD_VIEWED_VERSION = ((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
               .setId("reload_viewed_version"))
            .setValueType(BuiltInConfigValueTypes.INTEGER))
         .setDefaultValue(0))
      .build(ALL);
   public static final BooleanConfigOption DEBUG = ((Builder)((Builder)((Builder)Builder.begin().setId("debug")).setDefaultValue(false))
         .setDisplayName(Component.translatable("gui.xaero_debug")))
      .build(ALL);
   public static final ConfigOption<Integer> CAVE_MODE_START = ((FinalBuilder)((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin()
                  .setId("cave_mode_start_y"))
               .setValueType(BuiltInConfigValueTypes.INTEGER))
            .setDefaultValue(Integer.MAX_VALUE))
         .setDisplayName(Component.translatable("gui.xaero_wm_cave_mode_start")))
      .build(ALL);
   public static final BooleanConfigOption EXPORT_MULTIPLE_IMAGES = ((Builder)((Builder)((Builder)((Builder)Builder.begin()
                  .setId("png_export_in_multiple_images"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_export_option_multiple_images")))
         .setTooltip(Component.translatable("gui.xaero_box_export_option_multiple_images")))
      .build(ALL);
   public static final BooleanConfigOption NIGHT_EXPORT = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("png_export_night"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_export_option_nighttime")))
         .setTooltip(Component.translatable("gui.xaero_box_export_option_nighttime")))
      .build(ALL);
   public static final RangeConfigOption EXPORT_SCALE_DOWN_SQUARE = ((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)((xaero.lib.common.config.option.RangeConfigOption.Builder)xaero.lib.common.config.option.RangeConfigOption.Builder.begin()
                           .setId("png_export_scale_down_square"))
                        .setDefaultValue(20))
                     .setMinIndex(0))
                  .setMaxIndex(90))
               .setDisplayGetter(
                  (o, v) -> v <= 0
                     ? Component.translatable("gui.xaero_export_option_scale_down_square_unscaled")
                     : Component.translatable("gui.xaero_export_option_scale_down_square_value", new Object[]{v})
               ))
            .setDisplayName(Component.translatable("gui.xaero_export_option_scale_down_square")))
         .setTooltip(Component.translatable("gui.xaero_box_export_option_scale_down_square")))
      .build(ALL);
   public static final BooleanConfigOption EXPORT_HIGHLIGHTS = ((Builder)((Builder)((Builder)((Builder)Builder.begin().setId("png_export_highlights"))
               .setDefaultValue(false))
            .setDisplayName(Component.translatable("gui.xaero_export_option_highlights")))
         .setTooltip(Component.translatable("gui.xaero_box_export_option_highlights")))
      .build(ALL);
   public static final BooleanConfigOption DIFFERENTIATE_BY_SERVER_ADDRESS = ((Builder)((Builder)Builder.begin().setId("differentiate_by_server_address"))
         .setDefaultValue(true))
      .build(ALL);
   public static final BooleanConfigOption DISPLAY_DISABLED_WAYPOINTS = ((Builder)((Builder)Builder.begin().setId("display_disabled_waypoints"))
         .setDefaultValue(false))
      .build(ALL);
   public static final BooleanConfigOption CLOSE_WAYPOINTS_AFTER_HOP = ((Builder)((Builder)Builder.begin().setId("close_waypoints_after_hopping"))
         .setDefaultValue(true))
      .build(ALL);
   public static final BooleanConfigOption ONLY_CURRENT_MAP_WAYPOINTS = ((Builder)((Builder)Builder.begin().setId("only_display_current_map_waypoints"))
         .setDefaultValue(false))
      .build(ALL);
   public static final ConfigOption<Integer> GLOBAL_VERSION = ((FinalBuilder)((FinalBuilder)((FinalBuilder)FinalBuilder.begin().setId("global_version"))
            .setValueType(BuiltInConfigValueTypes.INTEGER))
         .setDefaultValue(1))
      .build(ALL);

   public static void registerAll(ClientConfigOptionManager manager) {
      for (ConfigOption<?> option : ALL) {
         manager.register(option);
      }
   }
}
