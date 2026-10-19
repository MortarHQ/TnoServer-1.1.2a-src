package cc.sighs.oelib.renderer;

import cc.sighs.oelib.renderer.spi.IFluidRenderers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ServiceLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.Fluid;
import org.joml.Matrix4f;

public class FluidRenderers {
   public static final int TEXTURE_SIZE = 16;
   public static final int MIN_FLUID_HEIGHT = 1;
   private static final IFluidRenderers IMPL = ServiceLoader.load(IFluidRenderers.class)
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("No IFluidRenderers implementation found"));

   private FluidRenderers() {
   }

   public static void render(GuiGraphics graphics, FluidRef ref, long amount, long capacity, int x, int y, int width, int height) {
      FluidRenderAttributes attrs = IMPL.resolve(ref);
      if (attrs != null) {
         int scaledHeight = computeScaledHeight(amount, capacity, height, 1);
         renderTiledSprite(graphics, attrs.sprite(), attrs.colorARGB(), x, y, width, scaledHeight, height);
      }
   }

   public static FluidRef of(Fluid fluid) {
      return new FluidRenderers.SimpleFluidRef(fluid);
   }

   public static int computeScaledHeight(long amount, long capacity, int fullHeight, int minHeight) {
      if (capacity > 0L && fullHeight > 0) {
         long scaled = amount * fullHeight / capacity;
         if (amount > 0L && scaled < minHeight) {
            scaled = minHeight;
         }

         if (scaled > fullHeight) {
            scaled = fullHeight;
         }

         return (int)scaled;
      } else {
         return 0;
      }
   }

   public static void renderTiledSprite(
      GuiGraphics graphics, TextureAtlasSprite sprite, int colorARGB, int x, int y, int width, int scaledHeight, int fullHeight
   ) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.enableBlend();
      RenderSystem.setShaderTexture(0, sprite.atlasLocation());
      RenderSystem.setShaderColor(
         (colorARGB >> 16 & 0xFF) / 255.0F, (colorARGB >> 8 & 0xFF) / 255.0F, (colorARGB & 0xFF) / 255.0F, (colorARGB >> 24 & 0xFF) / 255.0F
      );
      Matrix4f matrix = graphics.pose().last().pose();
      int yStart = y + fullHeight;
      int xTileCount = width / 16;
      int xRemainder = width - xTileCount * 16;
      int yTileCount = scaledHeight / 16;
      int yRemainder = scaledHeight - yTileCount * 16;

      for (int xTile = 0; xTile <= xTileCount; xTile++) {
         for (int yTile = 0; yTile <= yTileCount; yTile++) {
            int tileWidth = xTile == xTileCount ? xRemainder : 16;
            int tileHeight = yTile == yTileCount ? yRemainder : 16;
            if (tileWidth > 0 && tileHeight > 0) {
               int drawX = x + xTile * 16;
               int drawY = yStart - (yTile + 1) * 16;
               float uMin = sprite.getU0();
               float uMax = sprite.getU1();
               float vMin = sprite.getV0();
               float vMax = sprite.getV1();
               uMax -= (16 - tileWidth) / 16.0F * (uMax - uMin);
               vMax -= (16 - tileHeight) / 16.0F * (vMax - vMin);
               Tesselator tess = Tesselator.getInstance();
               BufferBuilder buf = tess.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
               buf.addVertex(matrix, drawX, drawY + tileHeight, 100.0F).setUv(uMin, vMax);
               buf.addVertex(matrix, drawX + tileWidth, drawY + tileHeight, 100.0F).setUv(uMax, vMax);
               buf.addVertex(matrix, drawX + tileWidth, drawY, 100.0F).setUv(uMax, vMin);
               buf.addVertex(matrix, drawX, drawY, 100.0F).setUv(uMin, vMin);
               BufferUploader.drawWithShader(buf.buildOrThrow());
            }
         }
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
   }

   private record SimpleFluidRef(Fluid fluid) implements FluidRef {
      @Override
      public Fluid getFluid() {
         return this.fluid;
      }
   }
}
