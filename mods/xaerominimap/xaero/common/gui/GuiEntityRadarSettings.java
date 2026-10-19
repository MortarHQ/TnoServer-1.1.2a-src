package xaero.common.gui;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.hud.category.ObjectCategory;
import xaero.hud.category.ui.RootCategorySettingEntry;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.radar.category.setting.EntityRadarCategorySettings;
import xaero.lib.client.gui.ISettingEntry;
import xaero.lib.client.gui.config.context.IEditConfigScreenContext;

public class GuiEntityRadarSettings extends GuiMinimapSettings {
   public GuiEntityRadarSettings(IXaeroMinimap modMain, Screen backScreen, Screen escScreen, IEditConfigScreenContext context) {
      super(Component.translatable("gui.xaero_entity_radar_settings"), backScreen, escScreen, context);
      BooleanSupplier allowNullSupplier = () -> HudMod.INSTANCE.getEntityRadarCategoryManager().getEditedCategoryConfig().isAllowNullValues();
      Supplier<ObjectCategory<?, ?>> categorySupplier = () -> HudMod.INSTANCE.getEntityRadarCategoryManager().getEditedCategory();
      BiConsumer<Object, Object> defaultValueChangeListener = (o, n) -> HudMod.INSTANCE.getEntityRadarCategoryManager().setEditedCategoryNeedsSaving(true);
      RootCategorySettingEntry<?> entityNumberEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.ENTITY_NUMBER,
         Component.translatable("gui.xaero_entity_amount"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> entityFadeEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.HEIGHT_FADE,
         Component.translatable("gui.xaero_entity_depth"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> alwaysNametagsEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.ALWAYS_NAMETAGS,
         Component.translatable("gui.xaero_always_entity_nametags"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> displayYEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.DISPLAY_Y,
         Component.translatable("gui.xaero_entity_display_height_full"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> iconNameFallbackEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.ICON_NAME_FALLBACK,
         Component.translatable("gui.xaero_entity_icon_name_fallback"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> iconsEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.ICONS,
         Component.translatable("gui.xaero_radar_setting_icons"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> namesEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.NAMES,
         Component.translatable("gui.xaero_radar_setting_names"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> renderOverMinimapEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.RENDER_OVER_MINIMAP,
         Component.translatable("gui.xaero_radar_render_radar_over_frame"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> dotSizeEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.DOT_SIZE,
         Component.translatable("gui.xaero_dots_size"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> iconScaleEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.ICON_SCALE,
         Component.translatable("gui.xaero_entity_heads_scale"),
         (currentScale, newScale) -> {
            defaultValueChangeListener.accept(currentScale, newScale);
            if (context.isClientSide() && (newScale < 1.0 || newScale < 1.0 != currentScale < 1.0)) {
               modMain.getInterfaces().getMinimapInterface().getMinimapFBORenderer().resetEntityIcons();
            }
         }
      );
      RootCategorySettingEntry<?> heightLimitEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.HEIGHT_LIMIT,
         Component.translatable("gui.xaero_height_limit"),
         defaultValueChangeListener::accept
      );
      RootCategorySettingEntry<?> startFadingEntry = new RootCategorySettingEntry<>(
         categorySupplier,
         allowNullSupplier,
         EntityRadarCategorySettings.START_FADING_AT,
         Component.translatable("gui.xaero_start_fading_at"),
         defaultValueChangeListener::accept
      );
      this.entries = new ISettingEntry[]{
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_CATEGORIES),
         this.optionEntry(MinimapProfiledConfigOptions.DISPLAY_RADAR),
         dotSizeEntry,
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_DOTS_STYLE),
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_SMOOTH_DOTS),
         iconsEntry,
         namesEntry,
         iconScaleEntry,
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_NAME_SCALE),
         alwaysNametagsEntry,
         iconNameFallbackEntry,
         entityNumberEntry,
         heightLimitEntry,
         entityFadeEntry,
         startFadingEntry,
         displayYEntry,
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_MAIN_ENTITY),
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_MAIN_DOT_SIZE),
         this.optionEntry(MinimapProfiledConfigOptions.ARROW_SCALE),
         this.optionEntry(MinimapProfiledConfigOptions.ARROW_COLOR),
         this.optionEntry(MinimapProfiledConfigOptions.ARROW_OPACITY),
         renderOverMinimapEntry,
         this.optionEntry(MinimapProfiledConfigOptions.RADAR_HIDE_INVISIBLE),
         this.optionEntry(MinimapProfiledConfigOptions.TRACKED_PLAYERS_ON_MINIMAP),
         this.optionEntry(MinimapProfiledConfigOptions.TRACKED_PLAYERS_IN_WORLD),
         this.optionEntry(MinimapProfiledConfigOptions.TRACKED_PLAYER_MINIMAP_ICON_SCALE),
         this.optionEntry(MinimapProfiledConfigOptions.TRACKED_PLAYER_WORLD_ICON_SCALE),
         this.optionEntry(MinimapProfiledConfigOptions.TRACKED_PLAYER_WORLD_NAME_SCALE)
      };
   }
}
