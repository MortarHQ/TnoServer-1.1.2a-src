package fzzyhmstrs.emi_loot.emi;

import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class LootSimplifiedRenderer implements EmiRenderable {
   private final int u;
   private final int v;
   private final ResourceLocation SPRITE_SHEET = ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/emi_recipe_textures.png");

   public LootSimplifiedRenderer(int u, int v) {
      this.u = u;
      this.v = v;
   }

   public void render(GuiGraphics draw, int x, int y, float delta) {
      draw.blit(this.SPRITE_SHEET, x, y, this.u, this.v, 16, 16, 32, 32);
   }
}
