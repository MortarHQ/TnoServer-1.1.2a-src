package lain.mods.cos.impl.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class GuiCosArmorButton extends Button implements IShiftingWidget, ICreativeInvWidget {
   private final Minecraft mc = Minecraft.getInstance();
   private final BiConsumer<GuiCosArmorButton, Boolean> onCreativeTabChanged;

   public GuiCosArmorButton(
      int x, int y, int width, int height, Component message, OnPress onPress, BiConsumer<GuiCosArmorButton, Boolean> onCreativeTabChanged
   ) {
      super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
      this.onCreativeTabChanged = onCreativeTabChanged;
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      boolean state = this.isHoveredOrFocused();
      graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      graphics.blit(GuiCosArmorInventory.TEXTURE, this.getX(), this.getY(), state ? 10 : 0, 166, 10, 10);
      graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      if (state) {
         graphics.drawCenteredString(this.mc.font, this.getMessage(), this.getX() + 5, this.getY() + this.height, 16777215);
      }
   }

   @Override
   public void shiftLeft(int diffLeft) {
      this.setX(this.getX() + diffLeft);
   }

   @Override
   public void onSelectedTabChanged(boolean isInventoryOpen) {
      if (this.onCreativeTabChanged != null) {
         this.onCreativeTabChanged.accept(this, isInventoryOpen);
      }
   }
}
