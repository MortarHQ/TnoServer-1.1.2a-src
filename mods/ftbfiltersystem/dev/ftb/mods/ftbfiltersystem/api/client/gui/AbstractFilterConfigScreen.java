package dev.ftb.mods.ftbfiltersystem.api.client.gui;

import dev.ftb.mods.ftbfiltersystem.api.client.Textures;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractSmartFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.LinearLayout.Orientation;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFilterConfigScreen<T extends SmartFilter> extends Screen {
   protected final T filter;
   protected final AbstractFilterScreen parentScreen;
   protected final int guiWidth;
   protected final int guiHeight;
   protected int leftPos;
   protected int topPos;
   protected T modifiedFilter;
   private int updateCounter = 0;
   private boolean deleteOnCancel = false;
   private boolean changesApplied = false;
   private Rect2i bounds = new Rect2i(0, 0, 0, 0);

   public AbstractFilterConfigScreen(T filter, AbstractFilterScreen parentScreen, int guiWidth, int guiHeight) {
      super(filter.getDisplayName());
      this.filter = filter;
      this.modifiedFilter = filter;
      this.parentScreen = parentScreen;
      this.guiWidth = guiWidth;
      this.guiHeight = guiHeight + 40;
   }

   protected final void scheduleUpdate(int ticks) {
      this.updateCounter = ticks;
   }

   protected void doScheduledUpdate() {
   }

   @Nullable
   protected abstract T makeNewFilter();

   public final Rect2i getGuiBounds() {
      return this.bounds;
   }

   protected void init() {
      this.setupGuiDimensions();
      LinearLayout bottomPanel = new LinearLayout(this.leftPos, this.topPos + this.guiHeight - 25, Orientation.HORIZONTAL);
      ((FrameLayout)bottomPanel.addChild(new FrameLayout(this.guiWidth / 2, 20)))
         .addChild(Button.builder(Component.translatable("gui.done"), b -> this.applyChanges()).width(70).build());
      ((FrameLayout)bottomPanel.addChild(new FrameLayout(this.guiWidth / 2, 20)))
         .addChild(Button.builder(Component.translatable("gui.cancel"), b -> this.onClose()).width(70).build());
      bottomPanel.arrangeElements();
      bottomPanel.visitWidgets(x$0 -> {
         AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(x$0);
      });
      ImageWidget img = (ImageWidget)this.addRenderableWidget(ImageWidget.sprite(16, 16, Textures.INFO_ICON));
      img.setPosition(this.leftPos + this.guiWidth - 19, this.topPos + 3);
      img.setTooltip(Tooltip.create(AbstractSmartFilter.getTooltip(this.filter.getId())));
   }

   public void tick() {
      if (this.updateCounter > 0 && --this.updateCounter == 0) {
         this.doScheduledUpdate();
      }
   }

   public void onClose() {
      this.minecraft.setScreen(this.parentScreen);
      this.parentScreen.findAndSelect(this.filter);
      if (!this.changesApplied && this.deleteOnCancel) {
         this.parentScreen.deleteSelectedFilter(true);
      }
   }

   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      guiGraphics.hLine(this.leftPos + 3, this.leftPos + this.guiWidth - 4, this.topPos + this.guiHeight - 29, -2143272896);
      guiGraphics.hLine(this.leftPos + 3, this.leftPos + this.guiWidth - 4, this.topPos + this.guiHeight - 28, -2130706433);
      guiGraphics.drawString(this.font, this.title, this.leftPos + 8, this.topPos + 6, 4210752, false);
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      guiGraphics.blitSprite(Textures.BACKGROUND, this.leftPos, this.topPos, this.guiWidth, this.guiHeight);
   }

   protected final EditBox makeSearchEditBox(int x, int y, Supplier<String> prevStrSupplier, Consumer<String> prevStrConsumer) {
      EditBox editBox = new EditBox(this.font, x, y, 88, 9 + 4, Component.empty());
      editBox.setMaxLength(15);
      editBox.setBordered(true);
      editBox.setVisible(true);
      editBox.setTextColor(16777215);
      editBox.setValue(prevStrSupplier.get());
      editBox.setResponder(s -> {
         if (!s.equals(prevStrSupplier.get())) {
            this.scheduleUpdate(5);
            prevStrConsumer.accept(s);
         }
      });
      this.addRenderableWidget(editBox);
      this.setFocused(editBox);
      return editBox;
   }

   private void setupGuiDimensions() {
      this.leftPos = (this.width - this.guiWidth) / 2;
      this.topPos = (this.height - this.guiHeight) / 2;
      this.bounds = new Rect2i(this.leftPos, this.topPos, this.width, this.height);
   }

   protected final void applyChanges() {
      T newFilter = this.makeNewFilter();
      if (newFilter != null) {
         this.parentScreen.replaceFilter(this.filter, newFilter);
         this.changesApplied = true;
      }

      this.onClose();
   }

   public void setDeleteOnCancel(boolean deleteOnCancel) {
      this.deleteOnCancel = deleteOnCancel;
   }
}
