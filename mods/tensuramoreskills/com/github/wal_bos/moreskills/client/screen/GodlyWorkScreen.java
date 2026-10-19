package com.github.wal_bos.moreskills.client.screen;

import com.github.wal_bos.moreskills.menu.GodlyWorkMenu;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class GodlyWorkScreen extends AbstractContainerScreen<GodlyWorkMenu> {
   private static final int VISIBLE_ROWS = 7;
   private static final int PANEL_COLOR = -804253680;
   private static final int BORDER_COLOR = -9408400;
   private static final int SLOT_COLOR = -14671840;
   private final List<AbstractWidget> dynamicWidgets = new ArrayList<>();
   private final List<GodlyWorkScreen.Entry> filteredEntries = new ArrayList<>();
   private EditBox searchField;
   private int listStart;

   public GodlyWorkScreen(GodlyWorkMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, Component.empty());
      if (isListMode(menu)) {
         this.imageWidth = 208;
         this.imageHeight = 140;
      } else {
         this.imageWidth = 176;
         this.imageHeight = 222;
      }

      this.titleLabelY = 10000;
      this.inventoryLabelY = 10000;
   }

   protected void init() {
      super.init();
      if (isListMode((GodlyWorkMenu)this.menu)) {
         this.searchField = new EditBox(this.font, this.leftPos + 10, this.topPos + 8, 188, 14, Component.empty());
         this.searchField.setMaxLength(80);
         this.searchField.setResponder(value -> {
            this.listStart = 0;
            this.rebuildList();
         });
         this.addRenderableWidget(this.searchField);
         this.rebuildList();
      } else {
         this.addDynamic(
            Button.builder(Component.literal("<"), button -> this.clickMenuButton(1000000)).bounds(this.leftPos + 62, this.topPos + 3, 24, 12).build()
         );
         this.addDynamic(
            Button.builder(Component.literal(">"), button -> this.clickMenuButton(1000001)).bounds(this.leftPos + 90, this.topPos + 3, 24, 12).build()
         );
      }
   }

   private void rebuildList() {
      this.clearDynamic();
      this.filteredEntries.clear();
      String filter = this.searchField == null ? "" : this.searchField.getValue().trim().toLowerCase(Locale.ROOT);
      List<ResourceLocation> entries = ((GodlyWorkMenu)this.menu).getEntries();

      for (int i = 0; i < entries.size(); i++) {
         ResourceLocation id = entries.get(i);
         Component name = this.entryName(id);
         String searchable = name.getString().toLowerCase(Locale.ROOT) + " " + id.toString().toLowerCase(Locale.ROOT);
         if (filter.isEmpty() || searchable.contains(filter)) {
            this.filteredEntries.add(new GodlyWorkScreen.Entry(i, name));
         }
      }

      this.listStart = Mth.clamp(this.listStart, 0, Math.max(0, this.filteredEntries.size() - 7));
      int end = Math.min(this.filteredEntries.size(), this.listStart + 7);

      for (int i = this.listStart; i < end; i++) {
         GodlyWorkScreen.Entry entry = this.filteredEntries.get(i);
         int row = i - this.listStart;
         this.addDynamic(
            Button.builder(entry.name(), button -> this.clickMenuButton(entry.originalIndex()))
               .bounds(this.leftPos + 10, this.topPos + 28 + row * 15, 188, 14)
               .build()
         );
      }
   }

   private Component entryName(ResourceLocation id) {
      if (((GodlyWorkMenu)this.menu).getKind() == GodlyWorkMenu.Kind.GENESIS) {
         Item item = (Item)BuiltInRegistries.ITEM.get(id);
         return item.getDescription();
      } else {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         return skill != null && skill.getName() != null ? skill.getName() : Component.literal(id.toString());
      }
   }

   private void clickMenuButton(int id) {
      if (this.minecraft != null && this.minecraft.gameMode != null) {
         this.minecraft.gameMode.handleInventoryButtonClick(((GodlyWorkMenu)this.menu).containerId, id);
      }
   }

   private <T extends AbstractWidget> T addDynamic(T widget) {
      this.dynamicWidgets.add(widget);
      return (T)this.addRenderableWidget(widget);
   }

   private void clearDynamic() {
      for (AbstractWidget widget : this.dynamicWidgets) {
         this.removeWidget(widget);
      }

      this.dynamicWidgets.clear();
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
      if (isListMode((GodlyWorkMenu)this.menu) && this.filteredEntries.size() > 7) {
         this.listStart = Mth.clamp(this.listStart - (int)Math.signum(deltaY), 0, this.filteredEntries.size() - 7);
         this.rebuildList();
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
      }
   }

   protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
      graphics.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, -804253680);
      if (!isListMode((GodlyWorkMenu)this.menu)) {
         this.drawSlotGrid(graphics, 8, 18, 9, 6);
         this.drawSlotGrid(graphics, 8, 140, 9, 3);
         this.drawSlotGrid(graphics, 8, 198, 9, 1);
      }
   }

   private void drawSlotGrid(GuiGraphics graphics, int startX, int startY, int columns, int rows) {
      for (int row = 0; row < rows; row++) {
         for (int column = 0; column < columns; column++) {
            int x = this.leftPos + startX + column * 18;
            int y = this.topPos + startY + row * 18;
            graphics.fill(x - 1, y - 1, x + 17, y + 17, -9408400);
            graphics.fill(x, y, x + 16, y + 16, -14671840);
         }
      }
   }

   protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      super.render(graphics, mouseX, mouseY, partialTick);
      this.renderTooltip(graphics, mouseX, mouseY);
   }

   private static boolean isListMode(GodlyWorkMenu menu) {
      return menu.getKind() == GodlyWorkMenu.Kind.GENESIS || menu.getKind() == GodlyWorkMenu.Kind.DIVINE_CONSTRUCTION;
   }

   private record Entry(int originalIndex, Component name) {
   }
}
