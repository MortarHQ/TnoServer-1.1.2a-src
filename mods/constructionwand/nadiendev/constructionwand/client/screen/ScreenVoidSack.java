package nadiendev.constructionwand.client.screen;

import java.util.List;
import nadiendev.constructionwand.items.containeritems.MenuVoidSack;
import nadiendev.constructionwand.network.PacketToggleVoidSack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenVoidSack extends AbstractContainerScreen<MenuVoidSack> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("constructionwand", "textures/gui/sprites/basket_4x4.png");
   private static final int GUI_WIDTH = 176;
   private static final int GUI_HEIGHT = 192;
   private static final int TOGGLE_BTN_X = 152;
   private static final int TOGGLE_BTN_Y = 5;
   private static final int TOGGLE_BTN_W = 20;
   private static final int TOGGLE_BTN_H = 10;
   private boolean sendToContainer;
   private boolean hasLinked;

   public ScreenVoidSack(MenuVoidSack menu, Inventory playerInv, Component title) {
      super(menu, playerInv, title);
      this.imageWidth = 176;
      this.imageHeight = 192;
      this.sendToContainer = menu.isSendToContainer();
      this.hasLinked = menu.hasLinkedContainer();
   }

   public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(gfx, mouseX, mouseY, partialTick);
      super.render(gfx, mouseX, mouseY, partialTick);
      this.renderTooltip(gfx, mouseX, mouseY);
      if (this.hasLinked && this.isOverButton(mouseX, mouseY)) {
         Component tooltipText = Component.translatable("gui.constructionwand.void_sack.toggle_tooltip");
         List<FormattedCharSequence> lines = this.font.split(tooltipText, 150);
         gfx.renderTooltip(this.font, lines, mouseX, mouseY);
      }
   }

   protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
      gfx.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, 176, 192);
   }

   protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
      gfx.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
      gfx.drawString(this.font, this.playerInventoryTitle, 8, 100, 4210752, false);
      if (this.hasLinked) {
         Component statusLabel = this.sendToContainer
            ? Component.translatable("gui.constructionwand.void_sack.sending")
            : Component.translatable("gui.constructionwand.void_sack.storing");
         gfx.drawString(this.font, statusLabel, 8, 186, 4210752, false);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.hasLinked && this.isOverButton((int)mouseX, (int)mouseY)) {
         PacketToggleVoidSack.send(((MenuVoidSack)this.menu).getHand());
         this.sendToContainer = !this.sendToContainer;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   private boolean isOverButton(int mouseX, int mouseY) {
      int bx = this.leftPos + 152;
      int by = this.topPos + 5;
      return mouseX >= bx && mouseX < bx + 20 && mouseY >= by && mouseY < by + 10;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return super.keyPressed(keyCode, scanCode, modifiers);
   }
}
