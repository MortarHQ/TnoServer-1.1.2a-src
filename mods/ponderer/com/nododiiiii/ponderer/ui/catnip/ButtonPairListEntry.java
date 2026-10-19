package com.nododiiiii.ponderer.ui.catnip;

import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.LabeledEntry;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ButtonPairListEntry extends LabeledEntry implements SearchableListEntry {
   private static final int GAP = 8;
   private final ButtonPairListEntry.ButtonModel left;
   @Nullable
   private final ButtonPairListEntry.ButtonModel right;

   public ButtonPairListEntry(
      String leftLabel,
      @Nullable String leftTooltip,
      Runnable leftAction,
      @Nullable String rightLabel,
      @Nullable String rightTooltip,
      @Nullable Runnable rightAction
   ) {
      this(
         createButtonModel(leftLabel, leftTooltip, leftAction, () -> 16777215, () -> true),
         rightLabel != null && rightAction != null ? createButtonModel(rightLabel, rightTooltip, rightAction, () -> 16777215, () -> true) : null
      );
   }

   public ButtonPairListEntry(ButtonPairListEntry.ButtonModel left, @Nullable ButtonPairListEntry.ButtonModel right) {
      super("");
      this.left = left;
      this.right = right;
      this.listeners.add(left.widget());
      if (right != null) {
         this.listeners.add(right.widget());
      }

      refreshTooltip(left);
      if (right != null) {
         refreshTooltip(right);
      }
   }

   @Override
   public boolean matchesQuery(String query) {
      StringBuilder search = new StringBuilder(searchText(this.left));
      if (this.right != null) {
         search.append(' ').append(searchText(this.right));
      }

      return search.toString().toLowerCase(Locale.ROOT).contains(query);
   }

   @Override
   public void highlightEntry() {
      this.annotations.put("highlight", ":)");
   }

   public void tick() {
      super.tick();
      tickButton(this.left);
      if (this.right != null) {
         tickButton(this.right);
      }
   }

   public void render(GuiGraphics graphics, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks) {
      int buttonHeight = Math.max(16, height - 16);
      int buttonY = y + Math.max(4, (height - buttonHeight) / 2);
      int buttonWidth = this.right == null ? width - 8 : (width - 8 - 8) / 2;
      renderButton(graphics, this.left, x + 4, buttonY, buttonWidth, buttonHeight, mouseX, mouseY, partialTicks);
      if (this.right != null) {
         renderButton(graphics, this.right, x + 4 + buttonWidth + 8, buttonY, buttonWidth, buttonHeight, mouseX, mouseY, partialTicks);
      }
   }

   private static ButtonPairListEntry.ButtonModel createButtonModel(
      String label, @Nullable String tooltip, Runnable action, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      return new ButtonPairListEntry.ButtonModel(
         (BoxWidget)new BoxWidget(0, 0, 120, 18).withCallback(action), () -> label, tooltip == null ? null : () -> tooltip, colorGetter, activeGetter
      );
   }

   private static void tickButton(ButtonPairListEntry.ButtonModel model) {
      model.widget().active = model.activeGetter().getAsBoolean();
      model.widget().tick();
   }

   private static void renderButton(
      GuiGraphics graphics, ButtonPairListEntry.ButtonModel model, int x, int y, int width, int height, int mouseX, int mouseY, float partialTicks
   ) {
      refreshTooltip(model);
      BoxWidget widget = model.widget();
      widget.setX(x);
      widget.setY(y);
      widget.setWidth(width);
      widget.setHeight(height);
      widget.active = model.activeGetter().getAsBoolean();
      widget.updateGradientFromState();
      widget.render(graphics, mouseX, mouseY, partialTicks);
      int color = widget.active ? model.colorGetter().getAsInt() : 7829367;
      graphics.drawCenteredString(
         Minecraft.getInstance().font, model.labelGetter().get(), widget.getX() + widget.getWidth() / 2, widget.getY() + (widget.getHeight() - 8) / 2, color
      );
   }

   private static void refreshTooltip(ButtonPairListEntry.ButtonModel model) {
      model.widget().getToolTip().clear();
      String tooltip = model.tooltipGetter() != null ? model.tooltipGetter().get() : null;
      if (tooltip != null && !tooltip.isBlank()) {
         model.widget().getToolTip().add(Component.literal(tooltip));
      }
   }

   private static String searchText(ButtonPairListEntry.ButtonModel model) {
      StringBuilder builder = new StringBuilder(model.labelGetter().get());
      if (model.tooltipGetter() != null) {
         String tooltip = model.tooltipGetter().get();
         if (tooltip != null && !tooltip.isBlank()) {
            builder.append(' ').append(tooltip);
         }
      }

      return builder.toString();
   }

   private record ButtonModel(
      BoxWidget widget, Supplier<String> labelGetter, @Nullable Supplier<String> tooltipGetter, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
   }
}
