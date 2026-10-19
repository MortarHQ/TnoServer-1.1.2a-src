package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ActionStripListEntry extends Entry implements SearchableListEntry {
   private static final int OUTER_GAP = 8;
   private static final int INNER_GAP = 8;
   private static final int ICON_BUTTON_SIZE = 18;
   private final List<ActionStripListEntry.ButtonModel> buttons;

   public ActionStripListEntry(List<ActionStripListEntry.ButtonModel> buttons) {
      this.buttons = new ArrayList<>(buttons);

      for (ActionStripListEntry.ButtonModel button : this.buttons) {
         this.listeners.add(button.widget());
      }
   }

   public static ActionStripListEntry.ButtonModel button(String label, @Nullable String tooltip, Runnable action) {
      return button(() -> label, tooltip == null ? null : () -> List.of(Component.literal(tooltip)), action, () -> 16777215, () -> true);
   }

   public static ActionStripListEntry.ButtonModel button(
      Supplier<String> labelGetter, @Nullable Supplier<List<Component>> tooltipGetter, Runnable action, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      return new ActionStripListEntry.ButtonModel(
         (BoxWidget)new BoxWidget(0, 0, 60, 18).withPadding(2.0F, 2.0F).withCallback(action), labelGetter, tooltipGetter, colorGetter, activeGetter, -1
      );
   }

   public static ActionStripListEntry.ButtonModel iconButton(
      PonderGuiTextures texture, Runnable action, @Nullable Supplier<List<Component>> tooltipGetter, BooleanSupplier activeGetter
   ) {
      BoxWidget widget = (BoxWidget)new BoxWidget(0, 0, 18, 18).withPadding(2.0F, 2.0F).withCallback(action);
      DelegatedStencilElement icon = PonderIconStencils.centered(texture);
      PonderIconStencils.attach(widget, icon);
      return new ActionStripListEntry.ButtonModel(widget, null, tooltipGetter, () -> 16777215, activeGetter, 18);
   }

   public static ActionStripListEntry.ButtonModel failIconButton(
      PonderGuiTextures texture, Runnable action, @Nullable Supplier<List<Component>> tooltipGetter, BooleanSupplier activeGetter
   ) {
      BoxWidget widget = (BoxWidget)new BoxWidget(0, 0, 18, 18).withPadding(2.0F, 2.0F).withCallback(action);
      DelegatedStencilElement icon = PonderIconStencils.centered(texture);
      PonderIconStencils.attachFail(widget, icon);
      return new ActionStripListEntry.ButtonModel(widget, null, tooltipGetter, () -> 16777215, activeGetter, 18);
   }

   @Override
   public boolean matchesQuery(String query) {
      return false;
   }

   @Override
   public void highlightEntry() {
   }

   public void tick() {
      super.tick();
      tickButtons(this.buttons);
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      renderButtonCluster(graphics, this.buttons, x + 8, Math.max(40, width - 16), y, height, mouseX, mouseY, partialTicks);
   }

   static void tickButtons(List<ActionStripListEntry.ButtonModel> buttons) {
      for (ActionStripListEntry.ButtonModel button : buttons) {
         button.widget().active = button.activeGetter().getAsBoolean();
         button.widget().tick();
      }
   }

   static void renderButtonCluster(
      GuiGraphics graphics, List<ActionStripListEntry.ButtonModel> buttons, int x, int width, int y, int height, int mouseX, int mouseY, float partialTicks
   ) {
      if (!buttons.isEmpty()) {
         int count = buttons.size();
         int totalGap = 8 * Math.max(0, count - 1);
         int availableWidth = Math.max(40, width - totalGap);
         int fixedWidth = 0;
         int stretchCount = 0;

         for (ActionStripListEntry.ButtonModel button : buttons) {
            if (button.fixedWidth() > 0) {
               fixedWidth += button.fixedWidth();
            } else {
               stretchCount++;
            }
         }

         int stretchWidth = stretchCount <= 0 ? Math.max(28, availableWidth / count) : Math.max(28, (availableWidth - fixedWidth) / stretchCount);
         int stretchExtra = stretchCount <= 0 ? 0 : Math.max(0, availableWidth - fixedWidth - stretchWidth * stretchCount);
         int buttonHeight = Math.max(16, Math.min(18, height - 12));
         int buttonY = y + Math.max(4, (height - buttonHeight) / 2);
         int usedWidth = fixedWidth + stretchWidth * stretchCount + stretchExtra + totalGap;
         int currentX = stretchCount > 0 ? x : x + (width - usedWidth) / 2;
         boolean extraAssigned = false;

         for (int i = 0; i < count; i++) {
            ActionStripListEntry.ButtonModel button = buttons.get(i);
            int currentWidth = button.fixedWidth() > 0 ? button.fixedWidth() : stretchWidth;
            if (button.fixedWidth() <= 0 && !extraAssigned) {
               currentWidth += stretchExtra;
               extraAssigned = true;
            }

            renderButton(graphics, button, currentX, buttonY, currentWidth, buttonHeight, mouseX, mouseY, partialTicks);
            currentX += currentWidth + 8;
         }
      }
   }

   private static void renderButton(
      GuiGraphics graphics, ActionStripListEntry.ButtonModel button, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks
   ) {
      refreshTooltip(button);
      BoxWidget widget = button.widget();
      widget.setX(x);
      widget.setY(y);
      widget.setWidth(width);
      widget.setHeight(height);
      widget.active = button.activeGetter().getAsBoolean();
      widget.updateGradientFromState();
      widget.render(graphics, mouseX, mouseY, partialTicks);
      Font font = Minecraft.getInstance().font;
      int color = widget.active ? button.colorGetter().getAsInt() : 7829367;
      if (button.labelGetter() != null) {
         String label = font.plainSubstrByWidth(button.labelGetter().get(), Math.max(8, width - 8));
         graphics.drawCenteredString(font, label, x + width / 2, y + (height - 8) / 2, color);
      }
   }

   private static void refreshTooltip(ActionStripListEntry.ButtonModel button) {
      button.widget().getToolTip().clear();
      if (button.tooltipGetter() != null) {
         List<Component> tooltip = button.tooltipGetter().get();
         if (tooltip != null && !tooltip.isEmpty()) {
            button.widget().getToolTip().addAll(tooltip);
         }
      }
   }

   public Component getNarration() {
      return CommonComponents.EMPTY;
   }

   public record ButtonModel(
      BoxWidget widget,
      @Nullable Supplier<String> labelGetter,
      @Nullable Supplier<List<Component>> tooltipGetter,
      IntSupplier colorGetter,
      BooleanSupplier activeGetter,
      int fixedWidth
   ) {
   }
}
