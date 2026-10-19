package fzzyhmstrs.emi_loot.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class IconEmiWidget extends Widget {
   static final ResourceLocation FRAME_ID = ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/icon_frame.png");
   private final ResourceLocation tex;
   private int x;
   private int y;
   private final Bounds bounds;
   private final List<ClientTooltipComponent> tooltipText;

   public IconEmiWidget(int x, int y, int keyIndex, Component text) {
      this.tex = TextKey.getSpriteId(keyIndex);
      this.x = x;
      this.y = y;
      this.bounds = new Bounds(x, y, 12, 12);
      this.tooltipText = Collections.singletonList(ClientTooltipComponent.create(text.getVisualOrderText()));
   }

   public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
      return this.tooltipText;
   }

   public Bounds getBounds() {
      return this.bounds;
   }

   public void renderInternal(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      matrices.blit(FRAME_ID, this.x, this.y, 12, 12, 0.0F, 0.0F, 12, 12, 64, 16);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      matrices.blit(this.tex, this.x + 2, this.y + 2, 8, 8, 0.0F, 0.0F, 8, 8, 8, 8);
   }
}
