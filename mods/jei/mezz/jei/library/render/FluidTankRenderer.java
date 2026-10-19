package mezz.jei.library.render;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import mezz.jei.common.platform.IPlatformFluidHelperInternal;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.joml.Matrix4f;

public class FluidTankRenderer<T> implements IIngredientRenderer<T> {
   private static final NumberFormat nf = NumberFormat.getIntegerInstance();
   private static final int TEXTURE_SIZE = 16;
   private static final int MIN_FLUID_HEIGHT = 1;
   private final IPlatformFluidHelperInternal<T> fluidHelper;
   private final long capacity;
   private final FluidTankRenderer.TooltipMode tooltipMode;
   private final int width;
   private final int height;

   public FluidTankRenderer(IPlatformFluidHelperInternal<T> fluidHelper) {
      this(fluidHelper, fluidHelper.bucketVolume(), FluidTankRenderer.TooltipMode.ITEM_LIST, 16, 16);
   }

   public FluidTankRenderer(IPlatformFluidHelperInternal<T> fluidHelper, long capacity, boolean showCapacity, int width, int height) {
      this(
         fluidHelper,
         capacity,
         showCapacity ? FluidTankRenderer.TooltipMode.SHOW_AMOUNT_AND_CAPACITY : FluidTankRenderer.TooltipMode.SHOW_AMOUNT,
         width,
         height
      );
   }

   private FluidTankRenderer(IPlatformFluidHelperInternal<T> fluidHelper, long capacity, FluidTankRenderer.TooltipMode tooltipMode, int width, int height) {
      Preconditions.checkArgument(capacity > 0L, "capacity must be > 0");
      Preconditions.checkArgument(width > 0, "width must be > 0");
      Preconditions.checkArgument(height > 0, "height must be > 0");
      this.fluidHelper = fluidHelper;
      this.capacity = capacity;
      this.tooltipMode = tooltipMode;
      this.width = width;
      this.height = height;
   }

   @Override
   public void render(GuiGraphics guiGraphics, T fluidStack) {
      this.render(guiGraphics, fluidStack, 0, 0);
   }

   @Override
   public void render(GuiGraphics guiGraphics, T ingredient, int posX, int posY) {
      RenderSystem.enableBlend();
      this.drawFluid(guiGraphics, this.width, this.height, ingredient, posX, posY);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
   }

   private void drawFluid(GuiGraphics guiGraphics, int width, int height, T fluidStack, int posX, int posY) {
      IIngredientTypeWithSubtypes<Fluid, T> type = this.fluidHelper.getFluidIngredientType();
      Fluid fluid = type.getBase(fluidStack);
      if (!fluid.isSame(Fluids.EMPTY)) {
         this.fluidHelper.getStillFluidSprite(fluidStack).ifPresent(fluidStillSprite -> {
            int fluidColor = this.fluidHelper.getColorTint(fluidStack);
            long amount = this.fluidHelper.getAmount(fluidStack);
            long scaledAmount = amount * height / this.capacity;
            if (amount > 0L && scaledAmount < 1L) {
               scaledAmount = 1L;
            }

            if (scaledAmount > height) {
               scaledAmount = height;
            }

            drawTiledSprite(guiGraphics, width, height, fluidColor, scaledAmount, fluidStillSprite, posX, posY);
         });
      }
   }

   private static void drawTiledSprite(
      GuiGraphics guiGraphics, int tiledWidth, int tiledHeight, int color, long scaledAmount, TextureAtlasSprite sprite, int posX, int posY
   ) {
      RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
      Matrix4f matrix = guiGraphics.pose().last().pose();
      setGLColorFromInt(color);
      int xTileCount = tiledWidth / 16;
      int xRemainder = tiledWidth - xTileCount * 16;
      long yTileCount = scaledAmount / 16L;
      long yRemainder = scaledAmount - yTileCount * 16L;
      int yStart = tiledHeight + posY;

      for (int xTile = 0; xTile <= xTileCount; xTile++) {
         for (int yTile = 0; yTile <= yTileCount; yTile++) {
            int width = xTile == xTileCount ? xRemainder : 16;
            long height = yTile == yTileCount ? yRemainder : 16L;
            int x = posX + xTile * 16;
            int y = yStart - (yTile + 1) * 16;
            if (width > 0 && height > 0L) {
               long maskTop = 16L - height;
               int maskRight = 16 - width;
               drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight, 100.0F);
            }
         }
      }
   }

   private static void setGLColorFromInt(int color) {
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      float alpha = (color >> 24 & 0xFF) / 255.0F;
      RenderSystem.setShaderColor(red, green, blue, alpha);
   }

   private static void drawTextureWithMasking(
      Matrix4f matrix, float xCoord, float yCoord, TextureAtlasSprite textureSprite, long maskTop, long maskRight, float zLevel
   ) {
      float uMin = textureSprite.getU0();
      float uMax = textureSprite.getU1();
      float vMin = textureSprite.getV0();
      float vMax = textureSprite.getV1();
      uMax -= (float)maskRight / 16.0F * (uMax - uMin);
      vMax -= (float)maskTop / 16.0F * (vMax - vMin);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      Tesselator tesselator = Tesselator.getInstance();
      BufferBuilder bufferBuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bufferBuilder.addVertex(matrix, xCoord, yCoord + 16.0F, zLevel).setUv(uMin, vMax);
      bufferBuilder.addVertex(matrix, xCoord + 16.0F - (float)maskRight, yCoord + 16.0F, zLevel).setUv(uMax, vMax);
      bufferBuilder.addVertex(matrix, xCoord + 16.0F - (float)maskRight, yCoord + (float)maskTop, zLevel).setUv(uMax, vMin);
      bufferBuilder.addVertex(matrix, xCoord, yCoord + (float)maskTop, zLevel).setUv(uMin, vMin);
      BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
   }

   @Override
   public List<Component> getTooltip(T fluidStack, TooltipFlag tooltipFlag) {
      List<Component> tooltip = new ArrayList<>();
      IIngredientTypeWithSubtypes<Fluid, T> type = this.fluidHelper.getFluidIngredientType();
      Fluid fluidType = type.getBase(fluidStack);
      if (fluidType.isSame(Fluids.EMPTY)) {
         return tooltip;
      }

      this.fluidHelper.getTooltip(tooltip, fluidStack, tooltipFlag);
      long amount = this.fluidHelper.getAmount(fluidStack);
      long milliBuckets = amount * 1000L / this.fluidHelper.bucketVolume();
      if (this.tooltipMode == FluidTankRenderer.TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
         MutableComponent amountString = Component.translatable(
            "jei.tooltip.liquid.amount.with.capacity", new Object[]{nf.format(milliBuckets), nf.format(this.capacity)}
         );
         tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
      } else if (this.tooltipMode == FluidTankRenderer.TooltipMode.SHOW_AMOUNT) {
         MutableComponent amountString = Component.translatable("jei.tooltip.liquid.amount", new Object[]{nf.format(milliBuckets)});
         tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
      }

      return tooltip;
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   enum TooltipMode {
      SHOW_AMOUNT,
      SHOW_AMOUNT_AND_CAPACITY,
      ITEM_LIST;
   }
}
