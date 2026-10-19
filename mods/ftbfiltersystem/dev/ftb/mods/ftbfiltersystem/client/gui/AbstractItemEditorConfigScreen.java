package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.CustomStringWidget;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.ItemWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractItemEditorConfigScreen<T extends SmartFilter> extends AbstractFilterConfigScreen<T> {
   private static final int SEARCH_ROWS = 4;
   private static final int SEARCH_COLS = 9;
   protected MultiLineEditBox editBox;
   private CustomStringWidget statusLine;
   private final List<AbstractItemEditorConfigScreen<T>.SearchItemWidget> itemWidgets = new ArrayList<>();
   protected Component customHoverName = null;

   public AbstractItemEditorConfigScreen(T filter, AbstractFilterScreen parentScreen, int guiWidth, int guiHeight) {
      super(filter, parentScreen, guiWidth, guiHeight);
   }

   @Override
   protected void init() {
      super.init();
      this.editBox = (MultiLineEditBox)this.addRenderableWidget(
         new MultiLineEditBox(this.font, this.leftPos + 8, this.topPos + 20, 304, 75, Component.empty(), Component.empty()) {
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
               this.visible = false;
               boolean res = super.mouseClicked(mouseX, mouseY, button);
               this.visible = true;
               return res;
            }
         }
      );
      this.setFocused(this.editBox);
      this.editBox.setValueListener(s -> this.scheduleUpdate(5));
      this.statusLine = (CustomStringWidget)this.addRenderableWidget(
         new CustomStringWidget(this.leftPos + 8, this.topPos + 98, this.guiWidth - 16, 9, Component.empty(), this.font)
      );
      this.statusLine.alignRight();
      this.itemWidgets.clear();

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 9; col++) {
            AbstractItemEditorConfigScreen<T>.SearchItemWidget w = new AbstractItemEditorConfigScreen.SearchItemWidget(row, col);
            this.itemWidgets.add(w);
            this.addRenderableWidget(w);
         }
      }

      Inventory inv = Minecraft.getInstance().player.getInventory();

      for (int i = 0; i < 36; i++) {
         int idx = i < 9 ? i + 27 : i - 9;
         this.itemWidgets.get(idx).setStack((ItemStack)inv.items.get(i));
      }
   }

   @Override
   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      guiGraphics.renderOutline(this.leftPos + 7, this.topPos + 109, 164, 74, -6250336);
   }

   protected abstract Predicate<ItemStack> inventoryChecker();

   protected void onItemWidgetClicked() {
   }

   protected void setStatus(boolean ok, Component message, String detail) {
      this.statusLine.setMessage(message.copy().withStyle(ok ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED));
      this.statusLine.setTooltip(detail != null && !detail.isEmpty() ? Tooltip.create(Component.literal(detail)) : null);
   }

   protected abstract String serialize(ItemStack var1);

   private class SearchItemWidget extends ItemWidget {
      public SearchItemWidget(int row, int col) {
         super(AbstractItemEditorConfigScreen.this.leftPos + 8 + 18 * col, AbstractItemEditorConfigScreen.this.topPos + 110 + 18 * row, 18, 18, ItemStack.EMPTY);
      }

      @Override
      protected void handleClick(boolean doubleClick) {
         if (AbstractItemEditorConfigScreen.this.inventoryChecker().test(this.getStack())
            && AbstractItemEditorConfigScreen.this.minecraft.player.hasPermissions(2)) {
            AbstractItemEditorConfigScreen.this.editBox.setValue(AbstractItemEditorConfigScreen.this.serialize(this.getStack()));
            AbstractItemEditorConfigScreen.this.customHoverName = this.getStack().getHoverName();
            AbstractItemEditorConfigScreen.this.setFocused(AbstractItemEditorConfigScreen.this.editBox);
            AbstractItemEditorConfigScreen.this.onItemWidgetClicked();
         }
      }

      @Override
      protected void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
         super.renderWidget(guiGraphics, pMouseX, pMouseY, pPartialTick);
         if (!AbstractItemEditorConfigScreen.this.inventoryChecker().test(this.getStack())) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
            guiGraphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getWidth(), -1593835521);
            guiGraphics.pose().popPose();
         }
      }
   }
}
