package xaero.hud.minimap.info;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import xaero.common.gui.GuiInfoDisplayEdit;
import xaero.common.settings.ModSettings;
import xaero.hud.minimap.MinimapLogs;
import xaero.hud.minimap.common.config.info.config.InfoDisplayConfigData;
import xaero.hud.minimap.common.config.info.config.InfoDisplayManagerConfigData;
import xaero.hud.minimap.info.codec.InfoDisplayStateCodec;
import xaero.hud.minimap.info.render.compile.InfoDisplayOnCompile;
import xaero.hud.minimap.info.widget.InfoDisplayWidgetFactory;
import xaero.lib.common.config.option.value.io.serialization.ConfigValueIOCodec;

public final class InfoDisplay<T> {
   private final String id;
   private final Component name;
   private final T defaultState;
   private final ConfigValueIOCodec<T> codec;
   private final InfoDisplayWidgetFactory<T> widgetFactory;
   private final InfoDisplayOnCompile<T> compiler;
   private final Function<ModSettings, T> legacyGetter;
   private InfoDisplay<T>.ConfigCache configCache;
   private InfoDisplayManager manager;

   private InfoDisplay(
      String id,
      Component name,
      T defaultState,
      ConfigValueIOCodec<T> codec,
      InfoDisplayWidgetFactory<T> widgetFactory,
      InfoDisplayOnCompile<T> compiler,
      Function<ModSettings, T> legacyGetter
   ) {
      this.id = id;
      this.name = name;
      this.defaultState = defaultState;
      this.codec = codec;
      this.widgetFactory = widgetFactory;
      this.compiler = compiler;
      this.legacyGetter = legacyGetter;
   }

   public void setManager(InfoDisplayManager manager) {
      if (this.manager != null) {
         throw new IllegalStateException();
      }

      this.manager = manager;
   }

   @Deprecated
   public T getState() {
      return this.getEffectiveState();
   }

   public T getEffectiveState() {
      if (this.manager == null) {
         throw new IllegalStateException("The info display must be added to a manager first!");
      }

      this.updateConfigCache();
      return this.configCache.state;
   }

   public int getTextColor() {
      if (this.manager == null) {
         throw new IllegalStateException("The info display must be added to a manager first!");
      }

      this.updateConfigCache();
      return this.configCache.textColor;
   }

   public int getBackgroundColor() {
      if (this.manager == null) {
         throw new IllegalStateException("The info display must be added to a manager first!");
      }

      this.updateConfigCache();
      return this.configCache.backgroundColor;
   }

   private void updateConfigCache() {
      if (this.configCache == null) {
         this.configCache = new InfoDisplay.ConfigCache();
      }
   }

   public String getId() {
      return this.id;
   }

   public Component getName() {
      return this.name;
   }

   public T getDefaultState() {
      return this.defaultState;
   }

   public ConfigValueIOCodec<T> getCodec() {
      return this.codec;
   }

   public AbstractWidget createWidget(int x, int y, int w, int h, GuiInfoDisplayEdit.MoveableEntry<T> entry, Runnable onChange, boolean includeNull) {
      return this.widgetFactory.create(x, y, w, h, entry, onChange, includeNull);
   }

   public InfoDisplayOnCompile<T> getCompiler() {
      return this.compiler;
   }

   public void clearStateCache() {
      this.configCache = null;
   }

   public T getLegacyValue(ModSettings legacySettings) {
      return this.legacyGetter != null && legacySettings != null ? this.legacyGetter.apply(legacySettings) : null;
   }

   public static final class Builder<T> {
      private String id;
      private Component name;
      private T defaultState;
      private ConfigValueIOCodec<T> codec;
      private InfoDisplayWidgetFactory<T> widgetFactory;
      private InfoDisplayOnCompile<T> compiler;
      private Consumer<InfoDisplay<?>> destination;
      private Function<ModSettings, T> legacyGetter;

      private Builder() {
      }

      public InfoDisplay.Builder<T> setDefault() {
         this.setId(null);
         this.setName(null);
         this.setDefaultState(null);
         this.setCodec((InfoDisplayStateCodec<T>)null);
         this.setWidgetFactory(null);
         this.setCompiler(null);
         this.setDestination(null);
         this.setLegacyGetter(null);
         return this;
      }

      public InfoDisplay.Builder<T> setId(String id) {
         this.id = id;
         return this;
      }

      public InfoDisplay.Builder<T> setName(Component name) {
         this.name = name;
         return this;
      }

      public InfoDisplay.Builder<T> setDefaultState(T defaultState) {
         this.defaultState = defaultState;
         return this;
      }

      @Deprecated
      public InfoDisplay.Builder<T> setCodec(InfoDisplayStateCodec<T> codec) {
         return this.setCodec((ConfigValueIOCodec<T>)codec);
      }

      public InfoDisplay.Builder<T> setCodec(ConfigValueIOCodec<T> codec) {
         this.codec = codec;
         return this;
      }

      public InfoDisplay.Builder<T> setWidgetFactory(InfoDisplayWidgetFactory<T> widgetFactory) {
         this.widgetFactory = widgetFactory;
         return this;
      }

      public InfoDisplay.Builder<T> setCompiler(InfoDisplayOnCompile<T> compiler) {
         this.compiler = compiler;
         return this;
      }

      public InfoDisplay.Builder<T> setDestination(Consumer<InfoDisplay<?>> destination) {
         this.destination = destination;
         return this;
      }

      public InfoDisplay.Builder<T> setLegacyGetter(Function<ModSettings, T> legacyGetter) {
         this.legacyGetter = legacyGetter;
         return this;
      }

      public InfoDisplay<T> build() {
         if (this.id != null && this.name != null && this.defaultState != null && this.codec != null && this.widgetFactory != null && this.compiler != null) {
            InfoDisplay<T> result = new InfoDisplay<>(this.id, this.name, this.defaultState, this.codec, this.widgetFactory, this.compiler, this.legacyGetter);
            if (this.destination != null) {
               this.destination.accept(result);
            }

            return result;
         } else {
            throw new IllegalStateException();
         }
      }

      public static <T> InfoDisplay.Builder<T> begin() {
         return new InfoDisplay.Builder<T>().setDefault();
      }
   }

   private class ConfigCache {
      private static final Function<InfoDisplayConfigData, Integer> BG_COLOR_GETTER = InfoDisplayConfigData::getBackgroundColor;
      private static final Function<InfoDisplayConfigData, Integer> TEXT_COLOR_GETTER = InfoDisplayConfigData::getTextColor;
      private T state = (T)this.fetchStateFromConfig();
      private int backgroundColor = this.fetchBackgroundColorFromConfig();
      private int textColor = this.fetchTextColorFromConfig();

      public ConfigCache() {
      }

      private T fetchStateFromConfig() {
         InfoDisplayManagerConfigData enforcedManagerConfig = InfoDisplay.this.manager.getEnforcedConfig();
         InfoDisplayConfigData enforcedDisplayConfig = enforcedManagerConfig == null ? null : enforcedManagerConfig.get(InfoDisplay.this.id);
         String enforcedStateString = enforcedDisplayConfig == null ? null : enforcedDisplayConfig.getState();
         if (enforcedStateString != null) {
            try {
               return (T)InfoDisplay.this.codec.decode(enforcedStateString, null, null);
            } catch (Throwable t) {
               MinimapLogs.LOGGER.error("Error trying to decode info display state from enforced config: {}", enforcedStateString, t);
            }
         }

         InfoDisplayManagerConfigData localManagerConfig = InfoDisplay.this.manager.getLocalConfig();
         InfoDisplayConfigData localDisplayConfig = localManagerConfig.get(InfoDisplay.this.id);
         String localStateString = localDisplayConfig == null ? null : localDisplayConfig.getState();
         if (localStateString == null) {
            return InfoDisplay.this.getDefaultState();
         }

         try {
            return (T)InfoDisplay.this.codec.decode(localStateString, null, null);
         } catch (Throwable t) {
            MinimapLogs.LOGGER.error("Error trying to decode info display state from current config profile: {}", localStateString, t);
            return InfoDisplay.this.getDefaultState();
         }
      }

      private int fetchBackgroundColorFromConfig() {
         return this.fetchColorFromConfig(BG_COLOR_GETTER, -1);
      }

      private int fetchTextColorFromConfig() {
         return this.fetchColorFromConfig(TEXT_COLOR_GETTER, 15);
      }

      private int fetchColorFromConfig(Function<InfoDisplayConfigData, Integer> colorGetter, int defaultColor) {
         InfoDisplayManagerConfigData enforcedManagerConfig = InfoDisplay.this.manager.getEnforcedConfig();
         InfoDisplayConfigData enforcedDisplayConfig = enforcedManagerConfig == null ? null : enforcedManagerConfig.get(InfoDisplay.this.id);
         Integer enforcedValue = enforcedDisplayConfig == null ? null : colorGetter.apply(enforcedDisplayConfig);
         if (enforcedValue != null) {
            return enforcedValue;
         }

         InfoDisplayManagerConfigData localManagerConfig = InfoDisplay.this.manager.getLocalConfig();
         InfoDisplayConfigData localDisplayConfig = localManagerConfig.get(InfoDisplay.this.id);
         Integer localValue = localDisplayConfig == null ? null : colorGetter.apply(localDisplayConfig);
         return localValue == null ? defaultColor : localValue;
      }
   }
}
