package net.blay09.mods.waystones.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Supplier;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WaystoneInventoryButton extends Button {
   private static final ResourceLocation INVENTORY_BUTTON_SPRITE = ResourceLocation.withDefaultNamespace("waystones/inventory_button");
   private final AbstractContainerScreen<?> parentScreen;
   private final ItemStack iconItem;
   private final ItemStack iconItemHovered;
   private final Supplier<Boolean> visiblePredicate;
   private final Supplier<Integer> xPosition;
   private final Supplier<Integer> yPosition;
   private final WarpRequirement warpRequirement;

   public WaystoneInventoryButton(
      AbstractContainerScreen<?> parentScreen, OnPress pressable, Supplier<Boolean> visiblePredicate, Supplier<Integer> xPosition, Supplier<Integer> yPosition
   ) {
      super(0, 0, 16, 16, Component.empty(), pressable, Button.DEFAULT_NARRATION);
      this.parentScreen = parentScreen;
      this.visiblePredicate = visiblePredicate;
      this.xPosition = xPosition;
      this.yPosition = yPosition;
      this.iconItem = new ItemStack(ModItems.boundScroll);
      this.iconItemHovered = new ItemStack(ModItems.warpScroll);
      LocalPlayer player = Minecraft.getInstance().player;
      this.warpRequirement = WaystonesAPI.resolveRequirements(WaystonesAPI.createUnboundTeleportContext(player).addFlag(TeleportFlags.INVENTORY_BUTTON));
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.visible = this.visiblePredicate.get();
      if (this.visible) {
         this.setX(((AbstractContainerScreenAccessor)this.parentScreen).getLeftPos() + this.xPosition.get());
         this.setY(((AbstractContainerScreenAccessor)this.parentScreen).getTopPos() + this.yPosition.get());
         this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
         LocalPlayer player = Minecraft.getInstance().player;
         if (this.warpRequirement.canAfford(player)) {
            ItemStack icon = this.isHovered ? this.iconItemHovered : this.iconItem;
            guiGraphics.renderItem(icon, this.getX(), this.getY());
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, icon, this.getX(), this.getY());
         } else {
            RenderSystem.enableBlend();
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            guiGraphics.blitSprite(INVENTORY_BUTTON_SPRITE, this.getX(), this.getY(), 16, 16);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
         }
      }
   }
}
