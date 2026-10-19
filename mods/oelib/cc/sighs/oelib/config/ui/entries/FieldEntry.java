package cc.sighs.oelib.config.ui.entries;

import cc.sighs.oelib.config.model.ConfigValueMeta;
import cc.sighs.oelib.config.ui.ConfigUiHint;
import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import cc.sighs.oelib.config.util.ConfigGuiUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FieldEntry extends AbstractConfigEntry<Object> {
   private final ConfigValueMeta meta;
   private final JsonObject working;
   private final int labelWidth;
   private final int controlWidth;
   private final int rowHeight;
   private final Component label;
   private final Component tooltip;
   private final ConfigUiHint hint;
   private boolean created = false;
   private Checkbox toggle;
   private EditBox textBox;
   private AbstractSliderButton slider;
   private CycleButton<String> dropdown;
   private Button resetButton;
   private JsonElement defaultValueElement;
   private Screen screen;

   public FieldEntry(ConfigValueMeta meta, JsonObject working, int labelWidth, int controlWidth, int rowHeight) {
      this.meta = meta;
      this.working = working;
      this.labelWidth = labelWidth;
      this.controlWidth = controlWidth;
      this.rowHeight = rowHeight;
      this.label = meta.translationKey().<Component>map(Component::translatable).orElse(Component.literal(meta.key()));
      this.tooltip = meta.tooltip().<Component>map(Component::translatable).orElse(null);
      JsonElement value = ConfigGuiUtil.getPath(working, meta.key());
      this.hint = meta.uiHint().orElse(this.defaultHintFor(value));
   }

   @Override
   public int getItemHeight() {
      return this.rowHeight;
   }

   public void attach(Screen screen, int x, int y, JsonObject defaults) {
      if (!this.created) {
         this.screen = screen;
         int resetX = screen.width - 80;
         int controlX = resetX - 8 - this.controlWidth;
         JsonElement value = ConfigGuiUtil.getPath(this.working, this.meta.key());
         this.defaultValueElement = ConfigGuiUtil.getPath(defaults, this.meta.key());
         this.createUiControl(controlX, y, value);
         this.createResetButton(resetX, y);
         this.updateResetButtonState();
         this.created = true;
      }
   }

   private void createUiControl(int controlX, int y, JsonElement currentValue) {
      switch (this.hint.type()) {
         case TOGGLE:
            this.createToggleControl(controlX, y, currentValue);
            break;
         case SLIDER:
            this.createSliderControl(controlX, y, currentValue);
            break;
         case DROPDOWN:
            this.createDropdownControl(controlX, y, currentValue);
            break;
         default:
            this.createTextBoxControl(controlX, y, currentValue);
      }
   }

   private void createToggleControl(int controlX, int y, JsonElement currentValue) {
      boolean val = currentValue != null && currentValue.isJsonPrimitive() && currentValue.getAsJsonPrimitive().isBoolean() && currentValue.getAsBoolean();
      this.toggle = Checkbox.builder(Component.empty(), Minecraft.getInstance().font).selected(val).onValueChange((box, selected) -> {
         ConfigGuiUtil.setPath(this.working, this.meta.key(), new JsonPrimitive(selected));
         this.updateResetButtonState();
         if (this.screen instanceof ConfigScreen cs) {
            cs.markDirty();
         }
      }).build();
      this.toggle.setPosition(controlX - 8, y);
      this.toggle.setWidth(20);
      this.screen.addRenderableWidget(this.toggle);
   }

   private void createSliderControl(int controlX, int y, JsonElement currentValue) {
      double min = this.hint.min() != null ? this.hint.min() : 0.0;
      double max = this.hint.max() != null ? this.hint.max() : 1.0;
      double step = this.hint.step() != null ? this.hint.step() : 0.01;
      double cur = this.getDoubleValue(currentValue, min);
      this.slider = this.createSlider(controlX, y, this.controlWidth, min, max, step, cur);
      this.screen.addRenderableWidget(this.slider);
   }

   private AbstractSliderButton createSlider(int x, int y, int width, double min, double max, double step, double currentValue) {
      return new AbstractSliderButton(
         x, y, width, 20, Component.literal(String.format(Locale.ROOT, "%.2f", currentValue)), (currentValue - min) / Math.max(1.0E-4, max - min)
      ) {
         protected void updateMessage() {
            double v = min + this.value * (max - min);
            this.setMessage(Component.literal(String.format(Locale.ROOT, "%.2f", v)));
         }

         protected void applyValue() {
            double v = min + this.value * (max - min);
            double snapped = Math.round(v / step) * step;
            ConfigGuiUtil.setPath(FieldEntry.this.working, FieldEntry.this.meta.key(), new JsonPrimitive(snapped));
            FieldEntry.this.updateResetButtonState();
            if (FieldEntry.this.screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }
      };
   }

   private void createDropdownControl(int controlX, int y, JsonElement currentValue) {
      List<String> options = this.hint.options() != null ? this.hint.options() : List.of();
      String cur = ConfigGuiUtil.jsonToString(currentValue);
      if (options.isEmpty()) {
         cur = "";
      } else if (!options.contains(cur)) {
         cur = options.getFirst();
      }

      this.dropdown = CycleButton.builder(this::optionComponent)
         .withValues(options)
         .displayOnlyValue()
         .withInitialValue(cur)
         .create(controlX, y, this.controlWidth, 20, Component.empty(), (d, v) -> {
            ConfigGuiUtil.setPath(this.working, this.meta.key(), new JsonPrimitive(v));
            this.updateResetButtonState();
            if (this.screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         });
      this.screen.addRenderableWidget(this.dropdown);
   }

   private void createTextBoxControl(int controlX, int y, JsonElement currentValue) {
      this.textBox = ConfigGuiUtil.createEditBox(ConfigGuiUtil.jsonToString(currentValue), controlX, y, this.controlWidth);
      this.screen.addRenderableWidget(this.textBox);
      this.textBox.setResponder(str -> {
         JsonElement currentEl = ConfigGuiUtil.getPath(this.working, this.meta.key());
         String prevStr = ConfigGuiUtil.jsonToString(currentEl);
         if (!str.equals(prevStr)) {
            JsonElement newEl = this.parseStringToJson(str);
            ConfigGuiUtil.setPath(this.working, this.meta.key(), newEl);
            this.updateResetButtonState();
            if (this.screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }
      });
   }

   private void createResetButton(int resetX, int y) {
      this.resetButton = Button.builder(Component.translatable("config.oelib.reset"), b -> this.resetToDefault()).bounds(resetX, y, 72, 20).build();
      this.screen.addRenderableWidget(this.resetButton);
   }

   private void resetToDefault() {
      if (this.defaultValueElement != null) {
         ConfigGuiUtil.setPath(this.working, this.meta.key(), this.defaultValueElement);
         String defaultValueStr = ConfigGuiUtil.jsonToString(this.defaultValueElement);
         this.updateControlValue(defaultValueStr, this.defaultValueElement);
         this.updateResetButtonState();
         if (this.screen instanceof ConfigScreen cs) {
            cs.markDirty();
         }
      }
   }

   private void updateControlValue(String defaultValueStr, JsonElement defaultValueElement) {
      switch (this.hint.type()) {
         case TOGGLE:
            if (this.toggle != null && defaultValueElement.isJsonPrimitive() && defaultValueElement.getAsJsonPrimitive().isBoolean()) {
               this.toggle.selected = defaultValueElement.getAsBoolean();
            }
            break;
         case SLIDER:
            if (this.slider != null) {
               this.recreateSliderWithDefaultValue(defaultValueElement);
            }
            break;
         case DROPDOWN:
            if (this.dropdown != null) {
               this.dropdown.setValue(defaultValueStr);
            }
            break;
         default:
            if (this.textBox != null) {
               this.textBox.setValue(defaultValueStr);
            }
      }
   }

   private void recreateSliderWithDefaultValue(JsonElement defaultValueElement) {
      double min = this.hint.min() != null ? this.hint.min() : 0.0;
      double max = this.hint.max() != null ? this.hint.max() : 1.0;
      double step = this.hint.step() != null ? this.hint.step() : 0.01;
      double defaultValue = this.getDoubleValue(defaultValueElement, min);
      int sx = this.slider.getX();
      int sy = this.slider.getY();
      int sw = this.slider.getWidth();
      this.screen.children().remove(this.slider);
      this.slider.visible = false;
      this.slider = this.createSlider(sx, sy, sw, min, max, step, defaultValue);
      this.screen.addRenderableWidget(this.slider);
   }

   @Override
   public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
      Font font = Minecraft.getInstance().font;
      graphics.drawString(font, this.label, x + 4, y + 6, -1);
      int resetX = Minecraft.getInstance().screen.width - 80;
      int labelTextWidth = font.width(this.label.getVisualOrderText());
      int reservedLabelWidth = Math.max(this.labelWidth, labelTextWidth + 12);
      int dynamicControlX = x + reservedLabelWidth;
      int availableWidth = Math.max(60, resetX - 8 - dynamicControlX);
      int dynamicControlWidth = Math.max(100, Math.min(this.controlWidth, availableWidth));
      this.updateControlPositions(dynamicControlX, y, dynamicControlWidth, resetX);
      if (isHovered && this.tooltip != null && this.screen instanceof ConfigScreen cs) {
         cs.setHoverTooltip(this.tooltip, mouseX, mouseY);
      }
   }

   private void updateControlPositions(int controlX, int y, int controlWidth, int resetX) {
      if (this.toggle != null) {
         this.toggle.setPosition(controlX - 8, y);
         this.toggle.setWidth(20);
      }

      if (this.textBox != null) {
         this.textBox.setX(controlX);
         this.textBox.setY(y);
         this.textBox.setWidth(controlWidth);
      }

      if (this.slider != null) {
         this.slider.setX(controlX);
         this.slider.setY(y);
         this.slider.setWidth(controlWidth);
      }

      if (this.dropdown != null) {
         this.dropdown.setX(controlX);
         this.dropdown.setY(y);
         this.dropdown.setWidth(controlWidth);
      }

      if (this.resetButton != null) {
         this.resetButton.setX(resetX);
         this.resetButton.setY(y);
      }
   }

   @Override
   public Component getFieldName() {
      return this.label;
   }

   @Override
   public Object getValue() {
      return null;
   }

   @Override
   public Optional<Object> getDefaultValue() {
      return Optional.empty();
   }

   private void updateResetButtonState() {
      if (this.resetButton != null) {
         if (this.defaultValueElement == null) {
            this.resetButton.active = false;
         } else {
            JsonElement current = ConfigGuiUtil.getPath(this.working, this.meta.key());
            boolean same = current == null && this.defaultValueElement.isJsonNull() || current != null && current.equals(this.defaultValueElement);
            this.resetButton.active = !same;
         }
      }
   }

   private Component optionComponent(String option) {
      String base = this.meta.translationKey().orElse(null);
      return base != null ? Component.translatable(base + "." + option) : Component.literal(option);
   }

   private ConfigUiHint defaultHintFor(JsonElement value) {
      if (value != null && value.isJsonPrimitive()) {
         return value.getAsJsonPrimitive().isBoolean() ? ConfigUiHint.toggle() : ConfigUiHint.text();
      } else {
         return ConfigUiHint.text();
      }
   }

   private double getDoubleValue(JsonElement element, double defaultValue) {
      return element != null && element.isJsonPrimitive() ? element.getAsJsonPrimitive().getAsDouble() : defaultValue;
   }

   private JsonElement parseStringToJson(String str) {
      String s = str.trim();
      if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
         return new JsonPrimitive(Boolean.parseBoolean(s));
      }

      if (s.matches("^-?\\d+$")) {
         try {
            return new JsonPrimitive(Integer.parseInt(s));
         } catch (NumberFormatException var5) {
         }
      }

      if (s.matches("^-?\\d+(?:\\.\\d+)?$")) {
         try {
            return new JsonPrimitive(Double.parseDouble(s));
         } catch (NumberFormatException var4) {
         }
      }

      return new JsonPrimitive(s);
   }
}
