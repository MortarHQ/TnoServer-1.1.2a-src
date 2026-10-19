package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerSculptorTrade;
import com.bobmowzie.mowziesmobs.server.inventory.InventoryOneInput;
import com.bobmowzie.mowziesmobs.server.inventory.InventorySculptor;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.message.MessageSculptorTrade;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.network.PacketDistributor;

public final class GuiSculptorTrade extends AbstractContainerScreen<ContainerSculptorTrade> implements InventoryOneInput.ChangeListener {
   private static final ResourceLocation TEXTURE_TRADE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/gui/container/umvuthi_trade.png");
   private final EntitySculptor sculptor;
   private final InventorySculptor inventory;
   private final ItemStack output = new ItemStack((ItemLike)ItemHandler.EARTHREND_GAUNTLET.get());
   private Button beginButton;
   private boolean prevBlocked;

   public GuiSculptorTrade(ContainerSculptorTrade screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.sculptor = screenContainer.getSculptor();
      this.inventory = screenContainer.getInventorySculptor();
      this.inventory.addListener(this);
   }

   protected void init() {
      super.init();
      String text = I18n.get("entity.mowziesmobs.sculptor.trade.button.text", new Object[0]);
      this.beginButton = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable(text), this::actionPerformed).pos(this.leftPos + 115, this.topPos + 52).size(56, 20).build()
      );
      this.updateButton();
   }

   private void actionPerformed(Button button) {
      if (button == this.beginButton) {
         PacketDistributor.sendToServer(new MessageSculptorTrade(this.sculptor.getId()), new CustomPacketPayload[0]);
      }
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      guiGraphics.blit(TEXTURE_TRADE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (this.sculptor != null) {
         this.sculptor.renderingInGUI = true;
         InventoryScreen.renderEntityInInventoryFollowsMouse(
            guiGraphics, this.leftPos + 8, this.topPos + 8, this.leftPos + 59, this.topPos + 69, 14, 0.0F, x, y, this.sculptor
         );
         this.sculptor.renderingInGUI = false;
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
      guiGraphics.drawString(this.font, this.title, (int)(this.imageWidth / 2.0F - this.font.width(this.title) / 2.0F) + 30, 6, 4210752, false);
      guiGraphics.drawString(this.font, I18n.get("container.inventory", new Object[0]), 8, this.imageHeight - 96 + 2, 4210752, false);
      if (this.sculptor != null) {
         if (this.sculptor.isTestObstructed()) {
            String blocked = I18n.get("entity.mowziesmobs.sculptor.trade.blocked", new Object[0]);
            guiGraphics.drawString(this.font, blocked, (int)(this.imageWidth / 2.0F - this.font.width(blocked) / 2.0F) + 30, 42, 4210752, false);
         }

         if (this.prevBlocked != this.sculptor.isTestObstructed()) {
            this.onChange(this.inventory);
         }

         this.prevBlocked = this.sculptor.isTestObstructed();
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      ItemStack inSlot = this.inventory.getItem(0);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
      guiGraphics.renderItem(this.sculptor.getDesires(), this.leftPos + 68, this.topPos + 24);
      guiGraphics.renderItemDecorations(this.font, this.sculptor.getDesires(), this.leftPos + 68, this.topPos + 24);
      guiGraphics.renderItem(this.output, this.leftPos + 134, this.topPos + 24);
      guiGraphics.renderItemDecorations(this.font, this.output, this.leftPos + 134, this.topPos + 24);
      if (this.isHovering(68, 24, 16, 16, mouseX, mouseY)) {
         guiGraphics.renderTooltip(this.font, this.sculptor.getDesires(), mouseX, mouseY);
      } else if (this.isHovering(134, 24, 16, 16, mouseX, mouseY)) {
         guiGraphics.renderTooltip(this.font, this.output, mouseX, mouseY);
      }

      if (this.beginButton.isMouseOver(mouseX, mouseY)) {
         guiGraphics.renderComponentHoverEffect(this.font, this.getHoverText(), mouseX, mouseY);
      }

      guiGraphics.pose().popPose();
   }

   @Override
   public void onChange(Container inv) {
      this.beginButton.active = this.sculptor.doesItemSatisfyDesire(inv.getItem(0)) && !this.sculptor.isTestObstructed();
   }

   private void updateButton() {
      this.beginButton.setMessage(Component.translatable(I18n.get("entity.mowziesmobs.sculptor.trade.button.text", new Object[0])));
   }

   private Style getHoverText() {
      MutableComponent text = Component.translatable(I18n.get("entity.mowziesmobs.sculptor.trade.button.hover", new Object[0]));
      return text.getStyle().withHoverEvent(new HoverEvent(Action.SHOW_TEXT, text));
   }
}
