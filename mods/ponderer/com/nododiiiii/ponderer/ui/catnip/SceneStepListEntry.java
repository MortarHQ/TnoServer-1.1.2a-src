package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class SceneStepListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int OUTER_GAP = 8;
   private static final int BUTTON_GAP = 8;
   private static final int BUTTON_WIDTH = 18;
   private static final int BUTTON_HEIGHT = 16;
   private final Supplier<String> labelGetter;
   @Nullable
   private final Runnable mainClick;
   private final List<SceneStepListEntry.ActionButton> buttons;
   private int lastX;
   private int lastY;
   private int lastWidth;
   private int lastHeight;
   private int lastLabelWidth;

   public SceneStepListEntry(Supplier<String> labelGetter, @Nullable Runnable mainClick, List<SceneStepListEntry.ActionButton> buttons) {
      super("");
      this.labelGetter = labelGetter;
      this.mainClick = mainClick;
      this.buttons = new ArrayList<>(buttons);

      for (SceneStepListEntry.ActionButton button : this.buttons) {
         this.listeners.add(button.widget());
      }

      this.refreshLabelTooltip();
   }

   public static SceneStepListEntry.ActionButton button(
      String label,
      @Nullable Supplier<List<Component>> tooltipGetter,
      Runnable primaryClick,
      @Nullable Runnable secondaryClick,
      IntSupplier colorGetter,
      BooleanSupplier activeGetter
   ) {
      return button(() -> label, tooltipGetter, primaryClick, secondaryClick, colorGetter, activeGetter);
   }

   public static SceneStepListEntry.ActionButton button(
      Supplier<String> labelGetter,
      @Nullable Supplier<List<Component>> tooltipGetter,
      Runnable primaryClick,
      @Nullable Runnable secondaryClick,
      IntSupplier colorGetter,
      BooleanSupplier activeGetter
   ) {
      return new SceneStepListEntry.ActionButton(
         (BoxWidget)new BoxWidget(0, 0, 18, 16).withPadding(2.0F, 2.0F).withCallback(primaryClick),
         labelGetter,
         tooltipGetter,
         secondaryClick,
         colorGetter,
         activeGetter
      );
   }

   public static SceneStepListEntry.ActionButton iconButton(
      DelegatedStencilElement icon,
      @Nullable Supplier<List<Component>> tooltipGetter,
      Runnable primaryClick,
      @Nullable Runnable secondaryClick,
      BooleanSupplier activeGetter
   ) {
      BoxWidget widget = (BoxWidget)new BoxWidget(0, 0, 18, 16).withCallback(primaryClick);
      PonderIconStencils.attach(widget, icon);
      return createIconButton(widget, tooltipGetter, secondaryClick, activeGetter);
   }

   public static SceneStepListEntry.ActionButton dangerIconButton(
      DelegatedStencilElement icon,
      @Nullable Supplier<List<Component>> tooltipGetter,
      Runnable primaryClick,
      @Nullable Runnable secondaryClick,
      BooleanSupplier activeGetter
   ) {
      BoxWidget widget = (BoxWidget)new BoxWidget(0, 0, 18, 16).withCallback(primaryClick);
      PonderIconStencils.attachFail(widget, icon);
      return createIconButton(widget, tooltipGetter, secondaryClick, activeGetter);
   }

   private static SceneStepListEntry.ActionButton createIconButton(
      BoxWidget widget, @Nullable Supplier<List<Component>> tooltipGetter, @Nullable Runnable secondaryClick, BooleanSupplier activeGetter
   ) {
      return new SceneStepListEntry.ActionButton(widget, null, tooltipGetter, secondaryClick, () -> 16777215, activeGetter);
   }

   @Override
   public boolean matchesQuery(String query) {
      return this.labelGetter.get().toLowerCase(Locale.ROOT).contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void tick() {
      super.tick();

      for (SceneStepListEntry.ActionButton button : this.buttons) {
         button.widget().active = button.activeGetter().getAsBoolean();
         button.widget().tick();
      }
   }

   protected int getLabelWidth(int totalWidth) {
      return Math.max(100, totalWidth - this.controlAreaWidth() - 12);
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      this.label.withText(this.labelGetter.get());
      this.refreshLabelTooltip();
      this.lastX = x;
      this.lastY = y;
      this.lastWidth = width;
      this.lastHeight = height;
      this.lastLabelWidth = this.getLabelWidth(width);
      super.render(graphics, index, y, x, width, height, mouseX, mouseY, hovered, partialTicks);
      int clusterWidth = this.controlAreaWidth() - 8 - 4;
      int buttonX = x + width - 8 - clusterWidth;
      int buttonY = y + Math.max(4, (height - 16) / 2);

      for (SceneStepListEntry.ActionButton button : this.buttons) {
         renderButton(graphics, button, buttonX, buttonY, mouseX, mouseY, partialTicks);
         buttonX += 26;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 1) {
         SceneStepListEntry.ActionButton secondaryTarget = this.findButtonAt(mouseX, mouseY);
         if (secondaryTarget != null && secondaryTarget.activeGetter().getAsBoolean() && secondaryTarget.secondaryClick() != null) {
            secondaryTarget.secondaryClick().run();
            return true;
         }
      }

      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else if (button == 0 && this.mainClick != null && this.isInsideMainArea(mouseX, mouseY)) {
         this.mainClick.run();
         return true;
      } else {
         return false;
      }
   }

   private int controlAreaWidth() {
      return this.buttons.isEmpty() ? 0 : this.buttons.size() * 18 + Math.max(0, this.buttons.size() - 1) * 8 + 8 + 4;
   }

   private boolean isInsideMainArea(double mouseX, double mouseY) {
      return mouseX >= this.lastX && mouseX < this.lastX + this.lastLabelWidth && mouseY >= this.lastY + 5 && mouseY < this.lastY + this.lastHeight - 5;
   }

   @Nullable
   private SceneStepListEntry.ActionButton findButtonAt(double mouseX, double mouseY) {
      int clusterWidth = this.controlAreaWidth() - 8 - 4;
      int buttonX = this.lastX + this.lastWidth - 8 - clusterWidth;
      int buttonY = this.lastY + Math.max(4, (this.lastHeight - 16) / 2);

      for (SceneStepListEntry.ActionButton actionButton : this.buttons) {
         if (mouseX >= buttonX && mouseX < buttonX + 18 && mouseY >= buttonY && mouseY < buttonY + 16) {
            return actionButton;
         }

         buttonX += 26;
      }

      return null;
   }

   private void refreshLabelTooltip() {
      this.labelTooltip.clear();
      this.labelTooltip.add(Component.literal(this.labelGetter.get()));
   }

   private static void renderButton(GuiGraphics graphics, SceneStepListEntry.ActionButton button, int x, int y, int mouseX, int mouseY, float partialTicks) {
      refreshTooltip(button);
      BoxWidget widget = button.widget();
      widget.setX(x);
      widget.setY(y);
      widget.setWidth(18);
      widget.setHeight(16);
      widget.active = button.activeGetter().getAsBoolean();
      widget.updateGradientFromState();
      widget.render(graphics, mouseX, mouseY, partialTicks);
      if (button.labelGetter() != null) {
         Font font = Minecraft.getInstance().font;
         int color = widget.active ? button.colorGetter().getAsInt() : 7829367;
         String label = font.plainSubstrByWidth(button.labelGetter().get(), 14);
         graphics.drawCenteredString(font, label, x + 9, y + 4, color);
      }
   }

   private static void refreshTooltip(SceneStepListEntry.ActionButton button) {
      button.widget().getToolTip().clear();
      if (button.tooltipGetter() != null) {
         List<Component> tooltip = button.tooltipGetter().get();
         if (tooltip != null && !tooltip.isEmpty()) {
            button.widget().getToolTip().addAll(tooltip);
         }
      }
   }

   public record ActionButton(
      BoxWidget widget,
      @Nullable Supplier<String> labelGetter,
      @Nullable Supplier<List<Component>> tooltipGetter,
      @Nullable Runnable secondaryClick,
      IntSupplier colorGetter,
      BooleanSupplier activeGetter
   ) {
   }
}
