package net.blay09.mods.waystones.client.gui.widget;

import com.google.common.collect.Lists;
import java.util.List;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class RemoveWaystoneButton extends Button implements ITooltipProvider {
   private static final ResourceLocation CANCEL_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/cancel");
   private final List<Component> tooltip;
   private final List<Component> activeTooltip;
   private final int visibleRegionStart;
   private final int visibleRegionHeight;
   private static boolean shiftGuard;

   public RemoveWaystoneButton(int x, int y, int visibleRegionStart, int visibleRegionHeight, Waystone waystone, OnPress pressable) {
      super(x, y, 18, 18, Component.empty(), pressable, Button.DEFAULT_NARRATION);
      this.visibleRegionStart = visibleRegionStart;
      this.visibleRegionHeight = visibleRegionHeight;
      this.tooltip = Lists.newArrayList(new Component[]{Component.translatable("gui.waystones.waystone_selection.hold_shift_to_delete")});
      this.activeTooltip = Lists.newArrayList(new Component[]{Component.translatable("gui.waystones.waystone_selection.click_to_delete")});
      if (waystone.getVisibility() == WaystoneVisibility.GLOBAL || WaystoneTypes.isSharestone(waystone.getWaystoneType())) {
         MutableComponent component = Component.translatable("gui.waystones.waystone_selection.deleting_global_for_all");
         component.withStyle(ChatFormatting.DARK_RED);
         this.tooltip.add(component);
         this.activeTooltip.add(component);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         shiftGuard = true;
         return true;
      } else {
         return false;
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      boolean shiftDown = Screen.hasShiftDown();
      if (!shiftDown) {
         shiftGuard = false;
      }

      this.active = !shiftGuard && shiftDown;
      if (mouseY >= this.visibleRegionStart && mouseY < this.visibleRegionStart + this.visibleRegionHeight) {
         if (this.isHovered && this.active) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         } else {
            guiGraphics.setColor(0.5F, 0.5F, 0.5F, 0.5F);
         }

         guiGraphics.blitSprite(CANCEL_SPRITE, this.getX(), this.getY(), 13, 13);
      }
   }

   @Override
   public boolean shouldShowTooltip() {
      return this.isHovered;
   }

   @Override
   public List<Component> getTooltipComponents() {
      return this.active ? this.activeTooltip : this.tooltip;
   }
}
