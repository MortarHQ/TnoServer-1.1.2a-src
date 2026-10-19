package dev.ftb.mods.ftbchunks.api.client.minimap;

import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface MinimapInfoComponent {
   ResourceLocation id();

   void render(MinimapContext var1, GuiGraphics var2, Font var3);

   default Set<TranslatedOption> getConfigComponents() {
      return Set.of();
   }

   default int height(MinimapContext context) {
      return this.computeLineHeight(context.minecraft(), 1) + 1;
   }

   default boolean shouldRender(MinimapContext context) {
      return true;
   }

   default int computeLineHeight(Minecraft minecraft, int lines) {
      float fontScale = ((Double)FTBChunksClientConfig.MINIMAP_FONT_SCALE.get()).floatValue();
      return (int)((9 + 2) * lines * fontScale);
   }

   default void drawCenteredText(Font font, GuiGraphics graphics, Component text, int y) {
      int textWidth = font.width(text.getVisualOrderText());
      graphics.drawString(font, text, -textWidth / 2, y, -1, true);
   }

   default Component displayName() {
      return Component.translatable("minimap.info." + this.id().getNamespace() + "." + this.id().getPath() + ".title");
   }

   default Component description() {
      return Component.translatable("minimap.info." + this.id().getNamespace() + "." + this.id().getPath() + ".description");
   }
}
