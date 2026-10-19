package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.inventory.InventoryOneInput;
import com.bobmowzie.mowziesmobs.server.inventory.InventoryUmvuthi;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.message.MessageUmvuthiTrade;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.network.PacketDistributor;

public final class GuiUmvuthiTrade extends AbstractContainerScreen<ContainerUmvuthiTrade> implements InventoryOneInput.ChangeListener {
   private static final ResourceLocation TEXTURE_TRADE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/gui/container/umvuthi_trade.png");
   private static final ResourceLocation TEXTURE_REPLENISH = ResourceLocation.fromNamespaceAndPath(
      "mowziesmobs", "textures/gui/container/umvuthi_replenish.png"
   );
   private final EntityUmvuthi umvuthi;
   private final Player player;
   private final InventoryUmvuthi inventory;
   private final ItemStack output = new ItemStack((ItemLike)ItemHandler.GRANT_SUNS_BLESSING.get());
   private Button grantButton;
   private boolean hasTraded;

   public GuiUmvuthiTrade(ContainerUmvuthiTrade screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.umvuthi = screenContainer.getUmvuthi();
      this.player = inv.player;
      this.inventory = screenContainer.getInventoryUmvuthi();
      this.hasTraded = this.umvuthi.hasTradedWith(inv.player);
      this.inventory.addListener(this);
   }

   protected void init() {
      super.init();
      String text = I18n.get(
         this.hasTraded ? "entity.mowziesmobs.umvuthi.replenish.button.text" : "entity.mowziesmobs.umvuthi.trade.button.text", new Object[0]
      );
      this.grantButton = (Button)this.addRenderableWidget(
         Button.builder(Component.translatable(text), this::actionPerformed).width(204).pos(this.leftPos + 115, this.topPos + 52).size(56, 20).build()
      );
      this.grantButton.active = this.hasTraded;
      this.updateButton();
   }

   protected void actionPerformed(Button button) {
      if (button == this.grantButton) {
         this.hasTraded = true;
         this.updateButton();
         PacketDistributor.sendToServer(new MessageUmvuthiTrade(this.umvuthi.getId()), new CustomPacketPayload[0]);
         if (!Minecraft.getInstance().isLocalServer()) {
            boolean satisfied = this.umvuthi.hasTradedWith(this.player);
            if (!satisfied && this.umvuthi.fulfillDesire(((ContainerUmvuthiTrade)this.menu).getSlot(0))) {
               this.umvuthi.rememberTrade(this.player);
               ((ContainerUmvuthiTrade)this.menu).broadcastChanges();
            }
         }
      }
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, this.hasTraded ? TEXTURE_REPLENISH : TEXTURE_TRADE);
      guiGraphics.blit(this.hasTraded ? TEXTURE_REPLENISH : TEXTURE_TRADE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      if (this.umvuthi != null) {
         this.umvuthi.renderingInGUI = true;
         InventoryScreen.renderEntityInInventoryFollowsMouse(
            guiGraphics, this.leftPos + 8, this.topPos + 8, this.leftPos + 59, this.topPos + 69, 20, 0.25F, x, y, this.umvuthi
         );
         this.umvuthi.renderingInGUI = false;
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
      String title = I18n.get("entity.mowziesmobs.umvuthi.trade", new Object[0]);
      guiGraphics.drawString(this.font, title, (int)(this.imageWidth / 2.0F - this.font.width(title) / 2.0F) + 30, 6, 4210752, false);
      guiGraphics.drawString(this.font, I18n.get("container.inventory", new Object[0]), 8, this.imageHeight - 96 + 2, 4210752, false);
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      ItemStack inSlot = this.inventory.getItem(0);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
      if (this.hasTraded) {
         guiGraphics.renderItem(this.output, this.leftPos + 106, this.topPos + 24);
         guiGraphics.renderItemDecorations(this.font, this.output, this.leftPos + 106, this.topPos + 24);
         if (this.isHovering(106, 24, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, this.output, mouseX, mouseY);
         }
      } else {
         guiGraphics.renderItem(this.umvuthi.getDesires(), this.leftPos + 68, this.topPos + 24);
         guiGraphics.renderItemDecorations(this.font, this.umvuthi.getDesires(), this.leftPos + 68, this.topPos + 24);
         guiGraphics.renderItem(this.output, this.leftPos + 134, this.topPos + 24);
         guiGraphics.renderItemDecorations(this.font, this.output, this.leftPos + 134, this.topPos + 24);
         if (this.isHovering(68, 24, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, this.umvuthi.getDesires(), mouseX, mouseY);
         } else if (this.isHovering(134, 24, 16, 16, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, this.output, mouseX, mouseY);
         }
      }

      if (this.grantButton.isMouseOver(mouseX, mouseY)) {
         guiGraphics.renderComponentHoverEffect(this.font, this.getHoverText(), mouseX, mouseY);
      }

      guiGraphics.pose().popPose();
   }

   @Override
   public void onChange(Container inv) {
      this.grantButton.active = this.hasTraded || this.umvuthi.doesItemSatisfyDesire(inv.getItem(0));
   }

   private void updateButton() {
      if (this.hasTraded) {
         this.grantButton.setMessage(Component.translatable(I18n.get("entity.mowziesmobs.umvuthi.replenish.button.text", new Object[0])));
         this.grantButton.setWidth(108);
         this.grantButton.setPosition(this.leftPos + 63, this.grantButton.getY());
      } else {
         this.grantButton.setMessage(Component.translatable(I18n.get("entity.mowziesmobs.umvuthi.trade.button.text", new Object[0])));
      }
   }

   private Style getHoverText() {
      MutableComponent text = Component.translatable(
         I18n.get(this.hasTraded ? "entity.mowziesmobs.umvuthi.replenish.button.hover" : "entity.mowziesmobs.umvuthi.trade.button.hover", new Object[0])
      );
      return text.getStyle().withHoverEvent(new HoverEvent(Action.SHOW_TEXT, text));
   }
}
