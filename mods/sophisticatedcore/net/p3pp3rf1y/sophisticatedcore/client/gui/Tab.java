package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.IntConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.ButtonBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.CompositeWidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.controls.WidgetBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public abstract class Tab extends CompositeWidgetBase<WidgetBase> {
   private static final int TEXTURE_WIDTH = 256;
   private static final int TEXTURE_HEIGHT = 256;
   public static final int DEFAULT_HEIGHT = 24;
   protected static final int DEFAULT_WIDTH = 21;
   private int width = 21;
   private int height = 24;
   private final List<Component> tooltip;
   private BooleanSupplier shouldShowTooltip = () -> true;
   protected BooleanSupplier shouldRender = () -> true;

   protected Tab(Position position, List<Component> tooltip, Function<IntConsumer, ButtonBase> getTabButton) {
      super(position, new Dimension(0, 0));
      this.tooltip = tooltip;
      this.addChild(getTabButton.apply(this::onTabIconClicked));
   }

   protected Tab(Position position, Component tooltip, Function<IntConsumer, ButtonBase> getTabButton) {
      this(position, List.of(tooltip), getTabButton);
   }

   public void setHandlers(BooleanSupplier shouldShowTooltip, BooleanSupplier shouldRender) {
      this.shouldShowTooltip = shouldShowTooltip;
      this.shouldRender = shouldRender;
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (this.shouldRender.getAsBoolean()) {
         super.render(guiGraphics, mouseX, mouseY, partialTicks);
      }
   }

   @Override
   public void renderTooltip(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
      super.renderTooltip(screen, guiGraphics, mouseX, mouseY);
      if (this.shouldRender.getAsBoolean() && this.isClosedTooltipVisible(mouseX, mouseY)) {
         guiGraphics.renderTooltip(screen.font, this.tooltip, Optional.empty(), mouseX, mouseY);
      }
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public Optional<Rect2i> getTabRectangle() {
      return GuiHelper.getPositiveRectangle(this.x, this.y, this.width, this.height);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
      int halfHeight = this.height / 2;
      int oddHeightAddition = this.height % 2;
      int secondHalfHeight = halfHeight + oddHeightAddition;
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, this.x, this.y, 256.0F - this.width, 0.0F, this.width, halfHeight, 256, 256);
      guiGraphics.blit(
         GuiHelper.GUI_CONTROLS, this.x, this.y + halfHeight, 256.0F - this.width, 256.0F - secondHalfHeight, this.width, secondHalfHeight, 256, 256
      );
      guiGraphics.blit(GuiHelper.GUI_CONTROLS, this.x - 3, this.y, 128, 256 - this.height, 3, this.height);
   }

   protected boolean isClosedTooltipVisible(int mouseX, int mouseY) {
      return this.shouldShowTooltip.getAsBoolean() && this.isMouseOver(mouseX, mouseY);
   }

   public int getTopY() {
      return this.y;
   }

   public int getBottomY() {
      return this.y + this.getHeight();
   }

   protected abstract void onTabIconClicked(int var1);

   @Override
   public NarrationPriority narrationPriority() {
      return NarrationPriority.NONE;
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
   }

   public void tick() {
   }
}
