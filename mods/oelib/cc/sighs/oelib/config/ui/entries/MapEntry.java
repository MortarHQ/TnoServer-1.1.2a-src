package cc.sighs.oelib.config.ui.entries;

import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import cc.sighs.oelib.config.util.ConfigGuiUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MapEntry extends AbstractConfigEntry<JsonObject> {
   private final String keyPath;
   private final JsonObject working;
   private final int controlWidth;
   private final int rowHeight;
   private final Component label;
   private final Component tooltip;
   private final JsonObject mapObj;
   private final List<EditBox> keyBoxes = new ArrayList<>();
   private final List<EditBox> valBoxes = new ArrayList<>();
   private final List<Checkbox> valToggles = new ArrayList<>();
   private final List<Button> delButtons = new ArrayList<>();
   private boolean created = false;
   private Screen screen;
   private Button addBtn;
   private boolean expanded = false;
   private int expX;
   private int expY;
   private int expW;
   private int expH;
   private Button resetButton;
   private JsonObject defaultMap;
   private int headerY;

   public MapEntry(String keyPath, Component label, JsonObject working, int controlWidth, int rowHeight, Component tooltip) {
      this.keyPath = keyPath;
      this.label = label;
      this.tooltip = tooltip;
      this.working = working;
      this.controlWidth = controlWidth;
      this.rowHeight = rowHeight;
      JsonElement value = ConfigGuiUtil.getPath(working, keyPath);
      this.mapObj = value != null && value.isJsonObject() ? value.getAsJsonObject() : new JsonObject();
   }

   @Override
   public int getItemHeight() {
      return this.expanded ? this.rowHeight * Math.max(1, this.mapObj.size() + 1) : this.rowHeight;
   }

   public void attach(Screen screen, int x, int y, JsonObject defaults) {
      if (!this.created) {
         this.screen = screen;
         this.headerY = y;
         int resetX = screen.width - 80;
         int deleteX = resetX - 24;
         int addX = resetX - 24;
         JsonElement def = ConfigGuiUtil.getPath(defaults, this.keyPath);
         this.defaultMap = def != null && def.isJsonObject() ? def.getAsJsonObject() : new JsonObject();
         this.addBtn = Button.builder(Component.literal("+"), b -> {
            this.mapObj.add("", new JsonPrimitive(""));
            ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
            this.rebuildRows(x, this.headerY, resetX, deleteX);
            this.updateResetButtonState();
            if (screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }).bounds(addX, y, 22, 20).build();
         screen.addRenderableWidget(this.addBtn);
         this.resetButton = Button.builder(Component.translatable("config.oelib.reset"), b -> {
            this.mapObj.entrySet().clear();

            for (Entry<String, JsonElement> e : this.defaultMap.entrySet()) {
               this.mapObj.add(e.getKey(), e.getValue());
            }

            ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
            this.rebuildRows(x, this.headerY, resetX, deleteX);
            this.updateResetButtonState();
            if (screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }).bounds(resetX, y, 72, 20).build();
         screen.addRenderableWidget(this.resetButton);
         this.rebuildRows(x, y, resetX, deleteX);
         this.updateResetButtonState();
         this.created = true;
      }
   }

   private void rebuildRows(int x, int y, int resetX, int deleteX) {
      if (this.screen != null) {
         this.clearWidgets();
         this.keyBoxes.clear();
         this.valBoxes.clear();
         this.valToggles.clear();
         this.delButtons.clear();
         List<Entry<String, JsonElement>> entries = new ArrayList<>(this.mapObj.entrySet());
         int rowY = y + this.rowHeight;
         int margin = 40;
         int contentLeft = x + margin;
         int contentRight = resetX - margin;

         for (Entry<String, JsonElement> e : entries) {
            JsonElement v = e.getValue();
            int width = Math.max(60, contentRight - contentLeft);
            int half = width / 2 - 6;
            EditBox keyBox = ConfigGuiUtil.createEditBox(e.getKey(), contentLeft, rowY, half);
            this.screen.addRenderableWidget(keyBox);
            EditBox valBox = ConfigGuiUtil.createEditBox(ConfigGuiUtil.jsonToString(v), contentLeft + half + 12, rowY, half);
            this.screen.addRenderableWidget(valBox);
            Checkbox valToggle = null;
            if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isBoolean()) {
               valToggle = Checkbox.builder(Component.empty(), Minecraft.getInstance().font).selected(v.getAsBoolean()).onValueChange((box, selected) -> {
                  this.mapObj.add(keyBox.getValue(), new JsonPrimitive(selected));
                  ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
                  this.updateResetButtonState();
                  if (this.screen instanceof ConfigScreen cs) {
                     cs.markDirty();
                  }
               }).build();
               this.screen.addRenderableWidget(valToggle);
            }

            Button del = Button.builder(Component.literal("x"), b -> {
               this.mapObj.remove(keyBox.getValue());
               ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
               this.rebuildRows(x, this.headerY, resetX, deleteX);
               this.updateResetButtonState();
               if (this.screen instanceof ConfigScreen cs) {
                  cs.markDirty();
               }
            }).bounds(deleteX, rowY, 20, 20).build();
            this.screen.addRenderableWidget(del);
            keyBox.setResponder(newKey -> {
               if (!newKey.equals(e.getKey())) {
                  JsonElement currentValue = this.mapObj.remove(e.getKey());
                  if (currentValue == null) {
                     currentValue = v;
                  }

                  this.mapObj.add(newKey, currentValue);
                  ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
                  this.updateResetButtonState();
                  if (this.screen instanceof ConfigScreen cs) {
                     cs.markDirty();
                  }
               }
            });
            valBox.setResponder(newVal -> {
               JsonElement prev = this.mapObj.get(keyBox.getValue());
               String prevStr = ConfigGuiUtil.jsonToString(prev);
               if (!prevStr.equals(newVal)) {
                  this.mapObj.add(keyBox.getValue(), ConfigGuiUtil.parsePrimitive(newVal));
                  ConfigGuiUtil.setPath(this.working, this.keyPath, this.mapObj);
                  this.updateResetButtonState();
                  if (this.screen instanceof ConfigScreen cs) {
                     cs.markDirty();
                  }
               }
            });
            this.keyBoxes.add(keyBox);
            this.valBoxes.add(valBox);
            this.valToggles.add(valToggle);
            this.delButtons.add(del);
            rowY += this.rowHeight;
         }
      }
   }

   private void clearWidgets() {
      for (EditBox kb : this.keyBoxes) {
         kb.setVisible(false);
         this.screen.children().remove(kb);
      }

      for (EditBox vb : this.valBoxes) {
         if (vb != null) {
            vb.setVisible(false);
            this.screen.children().remove(vb);
         }
      }

      for (Checkbox cb : this.valToggles) {
         if (cb != null) {
            cb.visible = false;
            this.screen.children().remove(cb);
         }
      }

      for (Button db : this.delButtons) {
         db.visible = false;
         this.screen.children().remove(db);
      }
   }

   private void updateResetButtonState() {
      if (this.resetButton != null) {
         this.resetButton.active = this.defaultMap != null && !this.mapObj.equals(this.defaultMap);
      }
   }

   public boolean toggleIfHit(double mx, double my) {
      if (mx >= this.expX && mx <= this.expX + this.expW && my >= this.expY && my <= this.expY + this.expH) {
         this.expanded = !this.expanded;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
      this.headerY = y;
      graphics.drawString(Minecraft.getInstance().font, this.label, x + 4, y + 6, -1);
      if (isHovered && this.tooltip != null && this.screen instanceof ConfigScreen cs && mouseY >= y && mouseY <= y + this.rowHeight) {
         cs.setHoverTooltip(this.tooltip, mouseX, mouseY);
      }

      int resetX = Minecraft.getInstance().screen.width - 80;
      int deleteX = resetX - 24;
      int addX = resetX - 24;
      this.expX = addX - 12;
      this.expY = y + 2;
      this.expW = 12;
      this.expH = 16;
      String arrow = this.expanded ? "▾" : "▸";
      int var10003 = this.expX + 2;
      int var10004 = this.expY + 4;
      graphics.drawString(Minecraft.getInstance().font, arrow, var10003, var10004, -1);
      if (this.addBtn != null) {
         this.addBtn.visible = this.expanded;
         this.addBtn.setX(addX);
         this.addBtn.setY(y);
      }

      if (this.resetButton != null) {
         this.resetButton.visible = this.expanded;
         this.resetButton.setX(resetX);
         this.resetButton.setY(y);
      }

      int rowY = y + this.rowHeight;
      int margin = 40;
      int contentLeft = x + margin;
      int contentRight = resetX - margin;

      for (int i = 0; i < this.keyBoxes.size(); i++) {
         EditBox kb = this.keyBoxes.get(i);
         EditBox vb = this.valBoxes.get(i);
         Checkbox cb = this.valToggles.get(i);
         Button del = this.delButtons.get(i);
         int bottomBarTop = Minecraft.getInstance().screen.height - 32;
         boolean visible = this.expanded && rowY <= bottomBarTop - 20;
         kb.visible = visible;
         if (vb != null) {
            vb.visible = visible && cb == null;
         }

         if (cb != null) {
            cb.visible = visible;
         }

         del.visible = visible;
         if (visible) {
            int width = Math.max(60, contentRight - contentLeft);
            int half = width / 2 - 6;
            kb.setX(contentLeft);
            kb.setY(rowY);
            kb.setWidth(half);
            if (vb != null && cb == null) {
               vb.setX(contentLeft + half + 12);
               vb.setY(rowY);
               vb.setWidth(half);
            }

            if (cb != null) {
               cb.setX(contentLeft + half + 12);
               cb.setY(rowY);
               cb.setWidth(20);
            }

            del.setX(deleteX);
            del.setY(rowY);
            rowY += this.rowHeight;
         }
      }
   }

   @Override
   public Component getFieldName() {
      return this.label;
   }

   public JsonObject getValue() {
      return this.mapObj;
   }

   @Override
   public Optional<JsonObject> getDefaultValue() {
      return Optional.of(this.mapObj);
   }
}
