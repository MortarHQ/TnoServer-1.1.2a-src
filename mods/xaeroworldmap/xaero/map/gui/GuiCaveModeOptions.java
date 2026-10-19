package xaero.map.gui;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import xaero.lib.client.gui.widget.Tooltip;
import xaero.lib.client.gui.widget.XaeroSliderWidget;
import xaero.lib.common.config.Config;
import xaero.map.MapProcessor;
import xaero.map.WorldMap;
import xaero.map.config.primary.option.WorldMapPrimaryClientConfigOptions;
import xaero.map.config.util.WorldMapClientConfigUtils;
import xaero.map.world.MapDimension;

public class GuiCaveModeOptions {
   private MapDimension dimension;
   private boolean enabled;
   private AbstractWidget caveModeStartSlider;
   private EditBox caveModeStartField;
   private boolean shouldUpdateSlider;

   public void onInit(GuiMap screen, MapProcessor mapProcessor) {
      this.caveModeStartSlider = null;
      this.caveModeStartField = null;
      this.dimension = mapProcessor.getMapWorld().getFutureDimension();
      this.enabled = this.enabled && this.dimension != null && WorldMapClientConfigUtils.getEffectiveCaveModeAllowed();
      if (this.enabled && this.dimension != null) {
         this.updateSlider(screen);
         this.updateField(screen);
         Tooltip caveModeTypeButtonTooltip = new Tooltip("gui.xaero_wm_box_cave_mode_type");
         screen.addButton(
            new TooltipButton(
               20, screen.height - 62, 150, 20, this.getCaveModeTypeButtonMessage(), b -> this.onCaveModeTypeButton(b, screen), () -> caveModeTypeButtonTooltip
            )
         );
      }
   }

   private void onCaveModeTypeButton(Button b, GuiMap screen) {
      this.dimension.toggleCaveModeType(true);
      synchronized (screen.getMapProcessor().uiSync) {
         this.dimension.saveConfigUnsynced();
      }

      b.setMessage(this.getCaveModeTypeButtonMessage());
   }

   private EditBox createField(GuiMap screen) {
      EditBox field = new EditBox(Minecraft.getInstance().font, 172, screen.height - 40, 50, 20, Component.translatable("gui.xaero_wm_cave_mode_start"));
      field.setMaxLength(7);
      int initialCaveModeStart = this.getCaveStart();
      field.setValue(initialCaveModeStart == Integer.MAX_VALUE ? "" : initialCaveModeStart + "");
      field.setResponder(text -> {
         try {
            this.setCaveStart(!text.isEmpty() && !text.equalsIgnoreCase("auto") ? Integer.parseInt(text) : Integer.MAX_VALUE);
            this.shouldUpdateSlider = true;
         } catch (NumberFormatException var3x) {
         }
      });
      return field;
   }

   private int getCaveStart() {
      Config primaryConfig = WorldMap.INSTANCE.getConfigs().getPrimaryClientConfigManager().getConfig();
      return (Integer)primaryConfig.get(WorldMapPrimaryClientConfigOptions.CAVE_MODE_START);
   }

   private void setCaveStart(int y) {
      Config primaryConfig = WorldMap.INSTANCE.getConfigs().getPrimaryClientConfigManager().getConfig();
      primaryConfig.set(WorldMapPrimaryClientConfigOptions.CAVE_MODE_START, y);
      WorldMap.INSTANCE.getConfigs().getPrimaryClientConfigManagerIO().save();
   }

   private AbstractWidget createSlider(GuiMap screen) {
      Component displayName = Component.translatable("gui.xaero_wm_cave_mode_start");
      Supplier<Component> labelGetter = () -> displayName;
      int initialCaveStart = this.getCaveStart();
      int minY = -64;
      int maxY = 319;
      int minOption = minY - 1;
      int range = maxY - minOption;
      double initialSliderValue = Mth.clamp((double)(initialCaveStart - minOption) / range, 0.0, 1.0);
      return new XaeroSliderWidget(20, screen.height - 40, 150, 20, labelGetter.get(), initialSliderValue, newSliderValue -> {
         int selectedY = (int)Math.round(newSliderValue * range) + minOption;
         if (selectedY == minOption) {
            selectedY = Integer.MAX_VALUE;
         }

         this.setCaveStart(selectedY);
         screen.onCaveModeStartSet();
      }, labelGetter);
   }

   private void updateField(GuiMap screen) {
      if (this.caveModeStartField == null) {
         screen.addButton(this.caveModeStartField = this.createField(screen));
      } else {
         screen.replaceRenderableWidget(this.caveModeStartField, this.caveModeStartField = this.createField(screen));
      }
   }

   private void updateSlider(GuiMap screen) {
      if (this.caveModeStartSlider == null) {
         screen.addButton(this.caveModeStartSlider = this.createSlider(screen));
      } else {
         screen.replaceRenderableWidget(this.caveModeStartSlider, this.caveModeStartSlider = this.createSlider(screen));
      }
   }

   public void toggle(GuiMap screen) {
      this.enabled = WorldMapClientConfigUtils.getEffectiveCaveModeAllowed() && !this.enabled;
      screen.init(Minecraft.getInstance(), screen.width, screen.height);
   }

   public void onCaveModeStartSet(GuiMap screen) {
      if (this.enabled) {
         this.updateField(screen);
      }
   }

   public void tick(GuiMap screen) {
      if (this.shouldUpdateSlider) {
         this.updateSlider(screen);
         this.shouldUpdateSlider = false;
      }

      if (this.enabled) {
         this.caveModeStartField
            .setSuggestion(this.caveModeStartField.getValue().isEmpty() ? I18n.get("gui.xaero_wm_cave_mode_start_auto", new Object[0]) : "");
      }
   }

   public void unfocusAll() {
      if (this.caveModeStartField != null) {
         this.caveModeStartField.setFocused(false);
      }

      if (this.caveModeStartSlider != null) {
         this.caveModeStartSlider.setFocused(false);
      }
   }

   private Component getCaveModeTypeButtonMessage() {
      return Component.literal(
         I18n.get("gui.xaero_wm_cave_mode_type", new Object[0])
            + ": "
            + I18n.get(
               this.dimension == null
                  ? "N/A"
                  : (
                     this.dimension.getCaveModeType() == 0
                        ? "gui.xaero_off"
                        : (this.dimension.getCaveModeType() == 1 ? "gui.xaero_wm_cave_mode_type_layered" : "gui.xaero_wm_cave_mode_type_full")
                  ),
               new Object[0]
            )
      );
   }

   public boolean isEnabled() {
      return this.enabled;
   }
}
