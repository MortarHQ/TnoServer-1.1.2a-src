package com.finderfeed.fdlib.systems.simple_screen;

import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Items;
import org.joml.Vector2f;

public class TTestScreers extends SimpleFDScreen {
   private static int test = 0;
   private static Vector2f[] testw = new Vector2f[]{
      new Vector2f(20.0F, 20.0F), new Vector2f(220.0F, 20.0F), new Vector2f(220.0F, 220.0F), new Vector2f(20.0F, 220.0F)
   };

   @Override
   protected void init() {
      super.init();
   }

   public void render(GuiGraphics graphics, int mx, int my, float pticks) {
      super.render(graphics, mx, my, pticks);
      FDRenderUtil.renderScaledItemStack(graphics, mx, my, 2.0F, Items.ACACIA_DOOR.getDefaultInstance());
   }

   @Override
   public float getScreenWidth() {
      return 0.0F;
   }

   @Override
   public float getScreenHeight() {
      return 0.0F;
   }
}
