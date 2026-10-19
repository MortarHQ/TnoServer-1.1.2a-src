package bigchadguys.sellingbin.render;

import bigchadguys.sellingbin.SellingBinMod;
import bigchadguys.sellingbin.init.ModBlocks;
import bigchadguys.sellingbin.trade.Trade;
import bigchadguys.sellingbin.world.data.SellingBinData;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class SellingBinTooltipComponent implements ClientTooltipComponent {
   private static final ResourceLocation TEXTURE = SellingBinMod.id("textures/gui/selling_bin.png");
   public static final ThreadLocal<ItemStack> STACK = new ThreadLocal<>();
   private final ItemStack stack;
   private Trade trade;

   public SellingBinTooltipComponent(ItemStack stack) {
      this.stack = stack;

      for (Trade trade : SellingBinData.CLIENT_TRADES) {
         if (trade.getInput().getFilter().test(stack)) {
            this.trade = trade;
            break;
         }
      }
   }

   public boolean isActive() {
      return this.trade != null;
   }

   public int getHeight() {
      return 9 + 17 + 8;
   }

   public int getWidth(Font textRenderer) {
      ItemStack bin = new ItemStack((ItemLike)ModBlocks.SELLING_BIN.get());
      MutableComponent prefix = Component.empty().append(bin.getHoverName()).withStyle(ChatFormatting.GRAY);
      int textWidth = textRenderer.width(prefix);
      int graphicWidth = 47;
      return Math.max(graphicWidth, textWidth) + 6;
   }

   public void renderText(Font textRenderer, int x, int y, Matrix4f matrix, BufferSource vertexConsumers) {
   }

   public void renderImage(Font textRenderer, int x, int y, GuiGraphics context) {
      x += 3;
      y += 3;
      ItemStack bin = new ItemStack((ItemLike)ModBlocks.SELLING_BIN.get());
      MutableComponent prefix = Component.empty().append(bin.getHoverName()).setStyle(Style.EMPTY.withColor(-2894893));
      int textWidth = textRenderer.width(prefix);
      int textHeight = 9;
      int graphicWidth = 47;
      int offset = (textWidth - graphicWidth) / 2;
      int textOffset = offset < 0 ? -offset : 0;
      int graphicOffset = offset >= 0 ? offset : 0;
      this.draw(context, x - 3.5F, y - 3.5F, Math.max(graphicWidth, textWidth) + 6, textHeight + 17 + 6, -16777216);
      this.drawOutline(x - 3.5F, y - 3.5F, Math.max(graphicWidth, textWidth) + 6, textHeight + 17 + 6, -2894893, context);
      context.drawString(textRenderer, prefix, x + textOffset, y, 16777215, false);
      ItemStack input = this.stack.copy();
      input.setCount(this.trade.getInput().getCount());
      context.renderFakeItem(input, x + graphicOffset, y + textHeight);
      context.renderItemDecorations(textRenderer, input, x + graphicOffset, y + textHeight);
      ItemStack output = this.trade.getOutput().generate().orElse(ItemStack.EMPTY);
      context.renderFakeItem(output, x + graphicOffset + 31, y + textHeight);
      context.renderItemDecorations(textRenderer, output, x + graphicOffset + 31, y + textHeight);
      context.blit(TEXTURE, x + graphicOffset + 19, y + 3 + textHeight, 0, 15.0F, 171.0F, 10, 9, 512, 256);
   }

   private void drawOutline(float x, float y, float width, float height, int color, GuiGraphics context) {
      this.draw(context, x, y, width, 0.5F, color);
      this.draw(context, x, y + height, width + 0.5F, 0.5F, color);
      this.draw(context, x, y, 0.5F, height, color);
      this.draw(context, x + width, y, 0.5F, height, color);
   }

   private void draw(GuiGraphics context, float x, float y, float width, float height, int color) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Matrix4f position = context.pose().last().pose();
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      buffer.addVertex(position, x, y, 0.0F).setColor(color);
      buffer.addVertex(position, x, y + height, 0.0F).setColor(color);
      buffer.addVertex(position, x + width, y + height, 0.0F).setColor(color);
      buffer.addVertex(position, x + width, y, 0.0F).setColor(color);
      BufferUploader.drawWithShader(buffer.buildOrThrow());
   }

   @OnlyIn(Dist.CLIENT)
   enum Sprite {
      SLOT(0, 0, 17, 20),
      BLOCKED_SLOT(0, 40, 17, 20),
      BORDER_VERTICAL(0, 18, 1, 20),
      BORDER_HORIZONTAL_TOP(1, 20, 17, 1),
      BORDER_HORIZONTAL_BOTTOM(1, 60, 17, 1),
      BORDER_CORNER_TOP(0, 20, 1, 1),
      BORDER_CORNER_BOTTOM(0, 60, 1, 1);

      public final int u;
      public final int v;
      public final int width;
      public final int height;

      Sprite(int u, int v, int width, int height) {
         this.u = u;
         this.v = v;
         this.width = width;
         this.height = height;
      }
   }
}
