package dev.ftb.mods.ftblibrary.api.sidebar;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import java.util.function.Supplier;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface ButtonOverlayRender {
   static ButtonOverlayRender ofSimpleString(Supplier<String> customTextHandler) {
      return (graphics, font, buttonSize) -> {
         String text = customTextHandler.get();
         if (!text.isEmpty()) {
            int nw = font.width(text);
            Color4I.LIGHT_RED.draw(graphics, buttonSize - nw, -1, nw + 1, 9);
            graphics.drawString(font, text, buttonSize - nw + 1, 0, -1);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      };
   }

   void render(GuiGraphics var1, Font var2, int var3);
}
