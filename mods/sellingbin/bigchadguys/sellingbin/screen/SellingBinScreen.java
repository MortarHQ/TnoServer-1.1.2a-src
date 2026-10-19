package bigchadguys.sellingbin.screen;

import bigchadguys.sellingbin.screen.handler.SellingBinScreenHandler;
import bigchadguys.sellingbin.world.data.SellingBinData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SellingBinScreen extends AbstractContainerScreen<SellingBinScreenHandler> {
   private static final ResourceLocation TEXTURE = ResourceLocation.parse("textures/gui/container/generic_54.png");
   private final int rows;

   public SellingBinScreen(SellingBinScreenHandler handler, Inventory playerInventory, Component title) {
      super(handler, playerInventory, title);
      this.rows = handler.getRows();
      this.imageHeight = 114 + this.rows * 18;
      this.inventoryLabelY = this.imageHeight - 94;
   }

   public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
      this.renderBackground(context, mouseX, mouseY, delta);
      super.render(context, mouseX, mouseY, delta);
      this.renderTooltip(context, mouseX, mouseY);
   }

   protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
      super.renderLabels(context, mouseX, mouseY);
      ((SellingBinScreenHandler)this.getMenu()).getBlockEntity().ifPresent(bin -> {
         long timeLeft = switch (bin.getMode()) {
            case BLOCK_BOUND -> bin.getTimeLeft();
            case PLAYER_BOUND -> SellingBinData.CLIENT_TIME_LEFT.get(bin.getMaterial().getId()).intValue();
         };
         if (timeLeft < 0L) {
            timeLeft = 0L;
         }

         long seconds = timeLeft / 20L % 60L;
         long minutes = timeLeft / 1200L % 60L;
         long hours = timeLeft / 72000L;
         String text = "";
         if (hours > 0L) {
            text = text + hours + ":";
         }

         text = text + (minutes <= 9L ? "0" : "") + minutes + ":";
         text = text + (seconds <= 9L ? "0" : "") + seconds;
         context.drawString(this.font, text, this.titleLabelX - 16 + this.imageWidth - this.font.width(text), this.titleLabelY, 4210752, false);
      });
   }

   protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      context.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.rows * 18 + 17);
      context.blit(TEXTURE, i, j + this.rows * 18 + 17, 0, 126, this.imageWidth, 96);
   }
}
