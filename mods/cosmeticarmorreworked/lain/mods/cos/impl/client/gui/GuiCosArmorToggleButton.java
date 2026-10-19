package lain.mods.cos.impl.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class GuiCosArmorToggleButton extends Button implements IShiftingWidget {
   public int state;

   public GuiCosArmorToggleButton(int x, int y, int width, int height, Component message, int initialState, OnPress onPress) {
      super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
      this.state = initialState;
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      graphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      graphics.blit(GuiCosArmorInventory.TEXTURE, this.getX(), this.getY(), 0 + 5 * this.state, 176, 5, 5);
      graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void shiftLeft(int diffLeft) {
      this.setX(this.getX() + diffLeft);
   }
}
