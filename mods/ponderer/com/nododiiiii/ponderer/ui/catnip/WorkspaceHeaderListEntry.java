package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class WorkspaceHeaderListEntry extends Entry implements SearchableListEntry {
   private static final int OUTER_GAP = 8;
   private static final int INNER_GAP = 8;
   private static final int BUTTON_WIDTH = 22;
   private static final int BUTTON_HEIGHT = 16;
   private final Supplier<String> titleGetter;
   private final Supplier<String> subtitleGetter;
   private final List<WorkspaceHeaderListEntry.HeaderButton> buttons;

   public WorkspaceHeaderListEntry(Supplier<String> titleGetter, Supplier<String> subtitleGetter, List<WorkspaceHeaderListEntry.HeaderButton> buttons) {
      this.titleGetter = titleGetter;
      this.subtitleGetter = subtitleGetter;
      this.buttons = new ArrayList<>(buttons);

      for (WorkspaceHeaderListEntry.HeaderButton button : this.buttons) {
         this.listeners.add(button.widget());
      }
   }

   public static WorkspaceHeaderListEntry.HeaderButton button(String label, Runnable action, @Nullable String tooltip, BooleanSupplier activeGetter) {
      return new WorkspaceHeaderListEntry.HeaderButton(
         (BoxWidget)new BoxWidget(0, 0, 22, 16).withPadding(2.0F, 2.0F).withCallback(action),
         () -> label,
         tooltip == null ? null : () -> List.of(Component.literal(tooltip)),
         activeGetter
      );
   }

   public static WorkspaceHeaderListEntry.HeaderButton iconButton(
      DelegatedStencilElement icon, Runnable action, @Nullable Supplier<List<Component>> tooltipGetter, BooleanSupplier activeGetter
   ) {
      BoxWidget widget = (BoxWidget)new BoxWidget(0, 0, 22, 16).withCallback(action);
      PonderIconStencils.attach(widget, icon);
      return new WorkspaceHeaderListEntry.HeaderButton(widget, null, tooltipGetter, activeGetter);
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

      for (WorkspaceHeaderListEntry.HeaderButton button : this.buttons) {
         button.widget().active = button.activeGetter().getAsBoolean();
         button.widget().tick();
      }
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      UIRenderHelper.streak(graphics, 0.0F, x - 10, y + height / 2, height - 6, width / 8 * 7, new Color(-587202560));
      UIRenderHelper.streak(graphics, 180.0F, x + (int)(width * 1.35F) + 10, y + height / 2, height - 6, width / 8 * 7, new Color(-587202560));
      int buttonsWidth = this.buttons.size() * 22 + Math.max(0, this.buttons.size() - 1) * 8;
      int buttonX = x + width - 8 - buttonsWidth;
      int buttonY = y + Math.max(4, (height - 16) / 2);
      int textRight = buttonX - 8;
      Font font = Minecraft.getInstance().font;
      String title = font.plainSubstrByWidth(this.titleGetter.get(), Math.max(20, textRight - x - 10));
      String subtitle = font.plainSubstrByWidth(this.subtitleGetter.get(), Math.max(20, textRight - x - 10));
      graphics.drawString(font, title, x + 10, y + 8, ((Color)UIRenderHelper.COLOR_TEXT_STRONG_ACCENT.getFirst()).getRGB());
      graphics.drawString(font, subtitle, x + 10, y + 22, ((Color)UIRenderHelper.COLOR_TEXT.getSecond()).getRGB());

      for (WorkspaceHeaderListEntry.HeaderButton button : this.buttons) {
         renderButton(graphics, button, buttonX, buttonY, mouseX, mouseY, partialTicks);
         buttonX += 30;
      }
   }

   private static void renderButton(
      GuiGraphics graphics, WorkspaceHeaderListEntry.HeaderButton button, int x, int y, int mouseX, int mouseY, float partialTicks
   ) {
      refreshTooltip(button);
      BoxWidget widget = button.widget();
      widget.setX(x);
      widget.setY(y);
      widget.setWidth(22);
      widget.setHeight(16);
      widget.active = button.activeGetter().getAsBoolean();
      widget.updateGradientFromState();
      widget.render(graphics, mouseX, mouseY, partialTicks);
      if (button.labelGetter() != null) {
         int color = widget.active ? 16777215 : 7829367;
         graphics.drawCenteredString(Minecraft.getInstance().font, button.labelGetter().get(), x + 11, y + 4, color);
      }
   }

   private static void refreshTooltip(WorkspaceHeaderListEntry.HeaderButton button) {
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

   public record HeaderButton(
      BoxWidget widget, @Nullable Supplier<String> labelGetter, @Nullable Supplier<List<Component>> tooltipGetter, BooleanSupplier activeGetter
   ) {
   }
}
