package cc.sighs.oelib.config.ui.entries;

import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import cc.sighs.oelib.config.util.ConfigGuiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ListEntry extends AbstractConfigEntry<JsonArray> {
   private final String keyPath;
   private final JsonObject working;
   private final int controlWidth;
   private final int rowHeight;
   private final Component label;
   private final Component tooltip;
   private final JsonArray array;
   private final List<EditBox> valueBoxes = new ArrayList<>();
   private final List<Checkbox> toggleBoxes = new ArrayList<>();
   private final List<Button> delButtons = new ArrayList<>();
   private final List<Button> upButtons = new ArrayList<>();
   private final List<Button> downButtons = new ArrayList<>();
   private boolean created = false;
   private Screen screen;
   private Button addBtn;
   private Button resetButton;
   private JsonArray defaultArray;
   private boolean expanded = false;
   private int expX;
   private int expY;
   private int expW;
   private int expH;

   public ListEntry(String keyPath, Component label, JsonObject working, int controlWidth, int rowHeight, Component tooltip) {
      this.keyPath = keyPath;
      this.label = label;
      this.tooltip = tooltip;
      this.working = working;
      this.controlWidth = controlWidth;
      this.rowHeight = rowHeight;
      JsonElement value = ConfigGuiUtil.getPath(working, keyPath);
      this.array = value != null && value.isJsonArray() ? value.getAsJsonArray() : new JsonArray();
   }

   @Override
   public int getItemHeight() {
      return this.expanded ? this.rowHeight * Math.max(1, this.array.size() + 1) : this.rowHeight;
   }

   public void attach(Screen screen, int x, int y, JsonObject defaults) {
      if (!this.created) {
         this.screen = screen;
         int resetX = screen.width - 80;
         JsonElement def = ConfigGuiUtil.getPath(defaults, this.keyPath);
         this.defaultArray = def != null && def.isJsonArray() ? def.getAsJsonArray() : new JsonArray();
         this.addBtn = Button.builder(Component.literal("+"), b -> {
            this.array.add(new JsonPrimitive(""));
            ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
            this.rebuildRows(x, y, resetX);
            this.updateResetButtonState();
            if (screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }).bounds(resetX - 24, y, 22, 20).build();
         screen.addRenderableWidget(this.addBtn);
         this.resetButton = Button.builder(Component.translatable("config.oelib.reset"), b -> {
            while (!this.array.isEmpty()) {
               this.array.remove(this.array.size() - 1);
            }

            for (JsonElement el : this.defaultArray) {
               this.array.add(el);
            }

            ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
            this.rebuildRows(x, y, resetX);
            this.updateResetButtonState();
            if (screen instanceof ConfigScreen cs) {
               cs.markDirty();
            }
         }).bounds(resetX, y, 72, 20).build();
         screen.addRenderableWidget(this.resetButton);
         this.rebuildRows(x, y, resetX);
         this.updateResetButtonState();
         this.created = true;
      }
   }

   private void rebuildRows(int x, int y, int resetX) {
      if (this.screen != null) {
         this.clearWidgets();
         this.valueBoxes.clear();
         this.toggleBoxes.clear();
         this.delButtons.clear();
         this.upButtons.clear();
         this.downButtons.clear();
         int rowY = y + this.rowHeight;
         int vx = x + 40;
         int vw = Math.max(60, resetX - vx - 86);

         for (int i = 0; i < this.array.size(); i++) {
            JsonElement el = this.array.get(i);
            EditBox vb = ConfigGuiUtil.createEditBox(ConfigGuiUtil.jsonToString(el), vx, rowY, vw);
            this.screen.addRenderableWidget(vb);
            Checkbox cb = null;
            if (el != null && el.isJsonPrimitive() && el.getAsJsonPrimitive().isBoolean()) {
               cb = Checkbox.builder(Component.empty(), Minecraft.getInstance().font).selected(el.getAsBoolean()).onValueChange((box, selected) -> {
                  int idx = this.valueBoxes.indexOf(vb);
                  if (idx >= 0) {
                     this.array.set(idx, new JsonPrimitive(selected));
                     ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
                     this.updateResetButtonState();
                     if (this.screen instanceof ConfigScreen cs) {
                        cs.markDirty();
                     }
                  }
               }).build();
               this.screen.addRenderableWidget(cb);
            }

            int deleteX = resetX - 21;
            int downX = deleteX - 22;
            int upX = downX - 22;
            Button up = Button.builder(Component.literal("▲"), b -> this.moveUp(vb)).bounds(upX, rowY, 20, 20).build();
            Button down = Button.builder(Component.literal("▼"), b -> this.moveDown(vb)).bounds(downX, rowY, 20, 20).build();
            Button del = Button.builder(Component.literal("x"), b -> this.removeItem(vb)).bounds(deleteX, rowY, 20, 20).build();
            this.screen.addRenderableWidget(up);
            this.screen.addRenderableWidget(down);
            this.screen.addRenderableWidget(del);
            vb.setResponder(str -> {
               int idx = this.valueBoxes.indexOf(vb);
               if (idx >= 0) {
                  String prev = ConfigGuiUtil.jsonToString(this.array.get(idx));
                  if (!prev.equals(str)) {
                     this.array.set(idx, ConfigGuiUtil.parsePrimitive(str));
                     ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
                     this.updateResetButtonState();
                     if (this.screen instanceof ConfigScreen cs) {
                        cs.markDirty();
                     }
                  }
               }
            });
            this.valueBoxes.add(vb);
            this.toggleBoxes.add(cb);
            this.delButtons.add(del);
            this.upButtons.add(up);
            this.downButtons.add(down);
            rowY += this.rowHeight;
         }
      }
   }

   private void moveUp(EditBox vb) {
      int idx = this.valueBoxes.indexOf(vb);
      if (idx > 0 && idx < this.array.size()) {
         JsonElement cur = this.array.get(idx);
         JsonElement prev = this.array.get(idx - 1);
         this.array.set(idx - 1, cur);
         this.array.set(idx, prev);
         ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
         this.rebuildRows(vb.getX() - 40, vb.getY() - this.rowHeight, vb.getX() + vb.getWidth() + 54);
         this.updateResetButtonState();
         if (this.screen instanceof ConfigScreen cs) {
            cs.markDirty();
         }
      }
   }

   private void moveDown(EditBox vb) {
      int idx = this.valueBoxes.indexOf(vb);
      if (idx >= 0 && idx < this.array.size() - 1) {
         JsonElement cur = this.array.get(idx);
         JsonElement next = this.array.get(idx + 1);
         this.array.set(idx + 1, cur);
         this.array.set(idx, next);
         ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
         this.rebuildRows(vb.getX() - 40, vb.getY() - this.rowHeight, vb.getX() + vb.getWidth() + 54);
         this.updateResetButtonState();
         if (this.screen instanceof ConfigScreen cs) {
            cs.markDirty();
         }
      }
   }

   private void removeItem(EditBox vb) {
      int idx = this.valueBoxes.indexOf(vb);
      if (idx >= 0 && idx < this.array.size()) {
         this.array.remove(idx);
         ConfigGuiUtil.setPath(this.working, this.keyPath, this.array);
         this.rebuildRows(vb.getX() - 40, vb.getY() - this.rowHeight, vb.getX() + vb.getWidth() + 54);
         this.updateResetButtonState();
         if (this.screen instanceof ConfigScreen cs) {
            cs.markDirty();
         }
      }
   }

   private void clearWidgets() {
      for (List<? extends AbstractWidget> list : List.of(this.valueBoxes, this.delButtons, this.upButtons, this.downButtons)) {
         for (AbstractWidget w : list) {
            if (w instanceof EditBox eb) {
               eb.setVisible(false);
               this.screen.children().remove(eb);
            } else {
               w.visible = false;
               this.screen.children().remove(w);
            }
         }
      }
   }

   private void updateResetButtonState() {
      if (this.resetButton != null) {
         this.resetButton.active = this.defaultArray != null && !this.array.equals(this.defaultArray);
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
      graphics.drawString(Minecraft.getInstance().font, this.label, x + 4, y + 6, -1);
      if (isHovered && this.tooltip != null && this.screen instanceof ConfigScreen cs && mouseY >= y && mouseY <= y + this.rowHeight) {
         cs.setHoverTooltip(this.tooltip, mouseX, mouseY);
      }

      int resetX = Minecraft.getInstance().screen.width - 80;
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
         this.addBtn.setX(addX);
         this.addBtn.setY(y);
      }

      if (this.resetButton != null) {
         this.resetButton.setX(resetX);
         this.resetButton.setY(y);
      }

      int rowY = y + this.rowHeight;
      int vx = x + 40;

      for (int i = 0; i < this.valueBoxes.size(); i++) {
         EditBox vb = this.valueBoxes.get(i);
         Checkbox cb = this.toggleBoxes.get(i);
         Button del = this.delButtons.get(i);
         Button up = this.upButtons.get(i);
         Button down = this.downButtons.get(i);
         int bottomBarTop = Minecraft.getInstance().screen.height - 32;
         boolean visible = this.expanded && rowY <= bottomBarTop - 20;
         vb.visible = visible && cb == null;
         if (cb != null) {
            cb.visible = visible;
         }

         del.visible = visible;
         up.visible = visible;
         down.visible = visible;
         if (visible) {
            int vw = Math.max(60, resetX - vx - 86);
            if (cb == null) {
               vb.setX(vx);
               vb.setY(rowY);
               vb.setWidth(vw);
            } else {
               cb.setX(vx);
               cb.setY(rowY);
               cb.setWidth(20);
            }

            int deleteX = resetX - 21;
            int downX = deleteX - 22;
            int upX = downX - 22;
            del.setX(deleteX);
            del.setY(rowY);
            down.setX(downX);
            down.setY(rowY);
            up.setX(upX);
            up.setY(rowY);
            rowY += this.rowHeight;
         }
      }
   }

   @Override
   public Component getFieldName() {
      return this.label;
   }

   public JsonArray getValue() {
      return this.array;
   }

   @Override
   public Optional<JsonArray> getDefaultValue() {
      return Optional.of(this.array);
   }
}
