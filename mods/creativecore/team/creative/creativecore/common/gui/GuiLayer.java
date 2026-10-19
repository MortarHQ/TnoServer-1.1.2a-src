package team.creative.creativecore.common.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.control.inventory.IGuiInventory;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.manager.GuiManager;
import team.creative.creativecore.common.gui.manager.GuiManagerItem;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.sync.GuiSyncHolder;
import team.creative.creativecore.common.util.math.geo.Rect;

public abstract class GuiLayer extends GuiParent {
   public static final int MINIMUM_OUTER_SPACING = 10;
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public GuiStyle style;
   private final GuiSyncHolder.GuiSyncHolderLayer sync = new GuiSyncHolder.GuiSyncHolderLayer(this);
   private HashMap<GuiManager.GuiManagerType, GuiManager> managers;

   protected static void collectInventories(Iterable<GuiControl> parent, List<IGuiInventory> inventories) {
      for (GuiControl control : parent) {
         if (control instanceof IGuiInventory i) {
            inventories.add(i);
         } else if (control instanceof GuiParent p) {
            collectInventories(p, inventories);
         }
      }
   }

   public GuiLayer(String name) {
      super(name, GuiFlow.STACK_X);
      if (CreativeCore.loader().getOverallSide().isClient()) {
         this.style = GuiStyle.getStyle(name);
      }
   }

   public GuiLayer(String name, int width, int height) {
      super(name, GuiFlow.STACK_X);
      this.setDim(width, height);
      if (CreativeCore.loader().getOverallSide().isClient()) {
         this.style = GuiStyle.getStyle(name);
      }
   }

   public Iterable<IGuiInventory> inventoriesToInsert() {
      List<IGuiInventory> inventories = new ArrayList<>();
      collectInventories(this, inventories);
      return inventories;
   }

   public Iterable<IGuiInventory> inventoriesToExract() {
      List<IGuiInventory> inventories = new ArrayList<>();
      collectInventories(this, inventories);
      Collections.reverse(inventories);
      return inventories;
   }

   public boolean has(GuiManager.GuiManagerType type) {
      return this.managers == null ? false : this.managers.containsKey(type);
   }

   public <T extends GuiManager> T getOrCreate(GuiManager.GuiManagerType<T> type) {
      if (this.managers == null) {
         this.managers = new HashMap<>();
      }

      T manager = (T)this.managers.get(type);
      if (manager == null) {
         this.managers.put(type, manager = (T)type.factory().apply(this));
      }

      return manager;
   }

   public GuiManagerItem itemManager() {
      return this.getOrCreate(GuiManager.ITEM);
   }

   public Iterable<GuiManager> managers() {
      return this.managers == null ? Collections.EMPTY_LIST : this.managers.values();
   }

   public GuiSyncHolder.GuiSyncHolderLayer getSyncHolder() {
      return this.sync;
   }

   public int getWidth() {
      return this.rect.getWidth();
   }

   public int getHeight() {
      return this.rect.getHeight();
   }

   @Override
   public void init() {
      this.create();
      super.init();
      this.reflow();
   }

   public void reinit() {
      super.init();
      this.reflow();
   }

   @Override
   public void reflow() {
      if (!CreativeCore.loader().getOverallSide().isServer()) {
         Rect screen = Rect.getScreenRect();
         int screenWidth = (int)screen.getWidth() - this.getContentOffset() * 2 - 10;
         int fixedWidth = -1;
         int width = 0;
         if (this.preferred != null) {
            width = fixedWidth = this.preferred.preferredWidth(this, screenWidth);
         }

         if (fixedWidth == -1) {
            if (this.isExpandableX()) {
               width = screenWidth;
            } else {
               width = Math.min(screenWidth, this.preferredWidth(screenWidth));
            }
         }

         if (this.preferred != null) {
            int minWidth = this.preferred.minWidth(this, screenWidth);
            if (minWidth != -1) {
               width = Math.max(width, minWidth);
            }

            int maxWidth = this.preferred.maxWidth(this, screenWidth);
            if (maxWidth != -1) {
               width = Math.min(width, maxWidth);
            }
         }

         this.rect.setRight(width + this.getContentOffset() * 2);
         this.flowX(width, this.preferredWidth(fixedWidth != -1 ? fixedWidth : screenWidth));
         int screenHeight = (int)screen.getHeight() - this.getContentOffset() * 2 - 10;
         int fixedHeight = -1;
         int height = 0;
         if (this.preferred != null) {
            height = fixedHeight = this.preferred.preferredHeight(this, width, screenHeight);
         }

         if (fixedHeight == -1) {
            if (this.isExpandableY()) {
               height = screenHeight;
            } else {
               height = Math.min(screenHeight, this.preferredHeight(width, screenHeight));
            }
         }

         if (this.preferred != null) {
            int minHeight = this.preferred.minHeight(this, width, screenHeight);
            if (minHeight != -1) {
               height = Math.max(height, minHeight);
            }

            int maxHeight = this.preferred.maxHeight(this, width, screenHeight);
            if (maxHeight != -1) {
               height = Math.min(height, maxHeight);
            }
         }

         this.rect.setBottom(height + this.getContentOffset() * 2);
         this.flowY(width, height, this.preferredHeight(width, fixedHeight != -1 ? fixedHeight : screenHeight));
      }
   }

   public abstract void create();

   public void becameTopLayer() {
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.GUI;
   }

   @Override
   public GuiLayer getLayer() {
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public GuiStyle getStyle() {
      return this.style;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      super.renderContent(graphics, controlRect, realRect, scale, mouseX, mouseY);

      for (GuiManager manager : this.managers()) {
         manager.renderOverlay(graphics, this, mouseX - (int)controlRect.minX, mouseY - (int)controlRect.minY);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   public boolean closeLayerUsingEscape() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public Options getSettings() {
      return Minecraft.getInstance().options;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public boolean hasGrayBackground() {
      return true;
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      if (!this.rect.inside(x, y) && !this.isMouseOverHovered(x, y)) {
         this.looseFocus();

         for (GuiManager manager : this.managers()) {
            manager.mouseClickedOutside(x, y);
         }

         return false;
      } else {
         return super.mouseClicked(x, y, button);
      }
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      super.mouseReleased(x, y, button);

      for (GuiManager manager : this.managers()) {
         manager.mouseReleased(x, y, button);
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         if (this.closeLayerUsingEscape()) {
            this.closeTopLayer();
         }

         return true;
      } else if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.getSettings().keyInventory.matches(keyCode, scanCode)) {
         this.closeTopLayer();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean hasLayer() {
      return true;
   }

   @Override
   public void tick() {
      for (GuiManager manager : this.managers()) {
         manager.tick();
      }

      super.tick();
   }

   @Override
   public void closed() {
      for (GuiManager manager : this.managers()) {
         manager.closed();
      }

      super.closed();
   }

   @Override
   public Rect toLayerRect(GuiControl control, Rect rect) {
      rect.move(control.rect.getX() + this.getOffsetX(), control.rect.getY() + this.getOffsetY());
      rect.scale(this.scaleFactor());
      return rect;
   }
}
