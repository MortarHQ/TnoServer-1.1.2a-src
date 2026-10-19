package dev.ftb.mods.ftbchunks.client.minimap.components;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PlayerPosInfoComponent implements MinimapInfoComponent {
   public static final ResourceLocation ID = FTBChunksAPI.rl("player_pos");

   @Override
   public ResourceLocation id() {
      return ID;
   }

   @Override
   public void render(MinimapContext context, GuiGraphics graphics, Font font) {
      MutableComponent text = Component.literal(
         Mth.floor(context.playerPos().x()) + " " + Mth.floor(context.playerPos().y()) + " " + Mth.floor(context.playerPos().z())
      );
      this.drawCenteredText(font, graphics, text, 0);
   }
}
