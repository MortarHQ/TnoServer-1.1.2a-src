package xaero.hud.minimap.radar.category.setting;

import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xaero.hud.category.setting.ObjectCategorySetting;
import xaero.hud.category.ui.setting.EditorSettingType;
import xaero.hud.category.util.CategoryConstants;
import xaero.hud.minimap.radar.category.EntityRadarCategoryConstants;
import xaero.hud.minimap.radar.color.RadarColor;
import xaero.lib.common.gui.widget.TooltipInfo;

public class EntityRadarCategorySettings {
   public static final String[] ENTITY_ICONS_OPTIONS = new String[]{"gui.xaero_icons_off", "gui.xaero_icons_list", "gui.xaero_icons_always", "-"};
   public static final String[] ENTITY_NAMES_OPTIONS = new String[]{"gui.xaero_names_off", "gui.xaero_names_list", "gui.xaero_names_always", "-"};
   public static final String[] RADAR_OVER_MAP_OPTIONS = new String[]{
      "gui.xaero_radar_over_map_never", "gui.xaero_radar_over_map_list", "gui.xaero_radar_over_map_always", "-"
   };
   public static final Map<String, ObjectCategorySetting<?>> SETTINGS = EntityRadarCategoryConstants.MAP_FACTORY.get();
   public static final List<ObjectCategorySetting<?>> SETTINGS_LIST = EntityRadarCategoryConstants.LIST_FACTORY.get();
   public static final ObjectCategorySetting<Boolean> DISPLAYED = ObjectCategorySetting.Builder.<Boolean>begin()
      .setId("displayed")
      .setDefaultValue(true)
      .setDisplayName(Component.translatable("gui.xaero_radar_displayed"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(1)
      .setIndexReader(x -> x == 1)
      .setIndexWriter(x -> x ? 1 : 0)
      .setUiValueNameProvider(CategoryConstants::getBooleanComponent)
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> COLOR = ObjectCategorySetting.Builder.<Double>begin()
      .setId("color")
      .setDefaultValue(13.0)
      .setDisplayName(Component.translatable("gui.xaero_radar_dots_color"))
      .setSettingUIType(EditorSettingType.EXPANDING)
      .setUiFirstOption(-1)
      .setUiLastOption(15)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> {
         RadarColor color = RadarColor.fromIndex(x.intValue());
         if (color == null) {
            MutableComponent teamColorComponent = Component.literal("# ");
            teamColorComponent.getSiblings().add(Component.translatable("gui.xaero_radar_dots_color_team_colour"));
            return teamColorComponent;
         } else {
            MutableComponent colorComponent = Component.literal("§" + color.getFormat() + "#§r ");
            colorComponent.getSiblings().add(color.getName());
            return colorComponent;
         }
      })
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> ICONS = ObjectCategorySetting.Builder.<Double>begin()
      .setId("icons")
      .setDefaultValue(1.0)
      .setDisplayName(Component.translatable("gui.xaero_radar_icons_displayed"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(2)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> Component.translatable(ENTITY_ICONS_OPTIONS[x.intValue()]))
      .setTooltip(new TooltipInfo("gui.xaero_box_entity_radar_icons"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> NAMES = ObjectCategorySetting.Builder.<Double>begin()
      .setId("names")
      .setDefaultValue(0.0)
      .setDisplayName(Component.translatable("gui.xaero_radar_names_displayed"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(2)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> Component.translatable(ENTITY_NAMES_OPTIONS[x.intValue()]))
      .setTooltip(new TooltipInfo("gui.xaero_box_entity_radar_names"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> DOT_SIZE = ObjectCategorySetting.Builder.<Double>begin()
      .setId("dotSize")
      .setDefaultValue(2.0)
      .setDisplayName(Component.translatable("gui.xaero_dots_size"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(1)
      .setUiLastOption(4)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> Component.literal(x.intValue() + ""))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> ICON_SCALE = ObjectCategorySetting.Builder.<Double>begin()
      .setId("iconScale")
      .setDefaultValue(1.0)
      .setDisplayName(Component.translatable("gui.xaero_entity_heads_scale"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(5)
      .setUiLastOption(40)
      .setIndexReader(x -> x * 0.05)
      .setIndexWriter(x -> (int)(x / 0.05))
      .setUiValueNameProvider(x -> Component.literal(String.format("%.2f", x)))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> HEIGHT_LIMIT = ObjectCategorySetting.Builder.<Double>begin()
      .setId("heightLimit")
      .setDefaultValue(20.0)
      .setDisplayName(Component.translatable("gui.xaero_height_limit"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(2)
      .setUiLastOption(410)
      .setIndexReader(x -> x * 5.0)
      .setIndexWriter(x -> (int)(x / 5.0))
      .setUiValueNameProvider(x -> Component.literal(x.intValue() + ""))
      .setTooltip(new TooltipInfo("gui.xaero_box_height_limit"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Boolean> HEIGHT_FADE = ObjectCategorySetting.Builder.<Boolean>begin()
      .setId("heightBasedFade")
      .setDefaultValue(true)
      .setDisplayName(Component.translatable("gui.xaero_entity_depth"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(1)
      .setIndexReader(x -> x == 1)
      .setIndexWriter(x -> x ? 1 : 0)
      .setUiValueNameProvider(CategoryConstants::getBooleanComponent)
      .setTooltip(new TooltipInfo("gui.xaero_box_entity_depth"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> DISPLAY_Y = ObjectCategorySetting.Builder.<Double>begin()
      .setId("displayHeight")
      .setDefaultValue(0.0)
      .setDisplayName(Component.translatable("gui.xaero_entity_display_height"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(3)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(
         x -> {
            if (x.intValue() == 0) {
               return Component.translatable("gui.xaero_off");
            } else if (x.intValue() == 1) {
               return Component.translatable("gui.xaero_entity_display_height_actual");
            } else {
               return x.intValue() == 2
                  ? Component.translatable("gui.xaero_entity_display_height_relative")
                  : Component.translatable("gui.xaero_entity_display_height_direction");
            }
         }
      )
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> START_FADING_AT = ObjectCategorySetting.Builder.<Double>begin()
      .setId("startFadingAt")
      .setDefaultValue(0.0)
      .setDisplayName(Component.translatable("gui.xaero_start_fading_at"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(0)
      .setUiLastOption(256)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> (int)x.doubleValue())
      .setUiValueNameProvider(x -> x.intValue() == 0 ? Component.translatable("gui.xaero_start_fading_at_auto") : Component.literal(x.intValue() + ""))
      .setTooltip(new TooltipInfo("gui.xaero_box_start_fading_at"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> ENTITY_NUMBER = ObjectCategorySetting.Builder.<Double>begin()
      .setId("entityNumber")
      .setDefaultValue(1000.0)
      .setDisplayName(Component.translatable("gui.xaero_entity_amount"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(0)
      .setUiLastOption(10)
      .setIndexReader(x -> x * 100.0)
      .setIndexWriter(x -> (int)(x / 100.0))
      .setUiValueNameProvider(x -> x.intValue() == 0 ? Component.translatable("gui.xaero_unlimited") : Component.literal(x.intValue() + ""))
      .setTooltip(new TooltipInfo("gui.xaero_box_entity_amount"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Boolean> ALWAYS_NAMETAGS = ObjectCategorySetting.Builder.<Boolean>begin()
      .setId("alwaysDisplayNametags")
      .setDefaultValue(false)
      .setDisplayName(Component.translatable("gui.xaero_always_entity_nametags"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(1)
      .setIndexReader(x -> x == 1)
      .setIndexWriter(x -> x ? 1 : 0)
      .setUiValueNameProvider(CategoryConstants::getBooleanComponent)
      .setTooltip(new TooltipInfo("gui.xaero_box_always_entity_nametags2"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Boolean> ICON_NAME_FALLBACK = ObjectCategorySetting.Builder.<Boolean>begin()
      .setId("displayNameWhenIconFails")
      .setDefaultValue(true)
      .setDisplayName(Component.translatable("gui.xaero_entity_icon_name_fallback"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(1)
      .setIndexReader(x -> x == 1)
      .setIndexWriter(x -> x ? 1 : 0)
      .setUiValueNameProvider(CategoryConstants::getBooleanComponent)
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> RENDER_OVER_MINIMAP = ObjectCategorySetting.Builder.<Double>begin()
      .setId("renderOverMinimapFrame")
      .setDefaultValue(1.0)
      .setDisplayName(Component.translatable("gui.xaero_radar_render_over_minimap"))
      .setSettingUIType(EditorSettingType.ITERATION_BUTTON)
      .setUiFirstOption(0)
      .setUiLastOption(2)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> Component.translatable(RADAR_OVER_MAP_OPTIONS[x.intValue()]))
      .setTooltip(new TooltipInfo("gui.xaero_box_radar_render_over_minimap"))
      .build(SETTINGS, SETTINGS_LIST);
   public static final ObjectCategorySetting<Double> RENDER_ORDER = ObjectCategorySetting.Builder.<Double>begin()
      .setId("renderOrder")
      .setDefaultValue(0.0)
      .setDisplayName(Component.translatable("gui.xaero_radar_render_order"))
      .setSettingUIType(EditorSettingType.SLIDER)
      .setUiFirstOption(0)
      .setUiLastOption(1000)
      .setIndexReader(x -> (double)x)
      .setIndexWriter(x -> x.intValue())
      .setUiValueNameProvider(x -> Component.literal(x.intValue() + ""))
      .setTooltip(new TooltipInfo("gui.xaero_box_radar_render_order"))
      .build(SETTINGS, SETTINGS_LIST);
}
