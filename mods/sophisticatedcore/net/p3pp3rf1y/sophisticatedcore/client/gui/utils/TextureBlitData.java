package net.p3pp3rf1y.sophisticatedcore.client.gui.utils;

import net.minecraft.resources.ResourceLocation;

public class TextureBlitData {
   private final ResourceLocation textureName;
   private final int xOffset;
   private final int yOffset;
   private final int textureWidth;
   private final int textureHeight;
   private final int u;
   private final int v;
   private final int width;
   private final int height;

   public TextureBlitData(ResourceLocation textureName, Dimension textureDimension, UV uv, Dimension dimension) {
      this(textureName, new Position(0, 0), textureDimension, uv, dimension);
   }

   public TextureBlitData(ResourceLocation textureName, UV uv, Dimension dimension) {
      this(textureName, new Dimension(256, 256), uv, dimension);
   }

   public TextureBlitData(ResourceLocation textureName, Position offset, Dimension textureDimension, UV uv, Dimension dimension) {
      this.textureName = textureName;
      this.xOffset = offset.x();
      this.yOffset = offset.y();
      this.textureWidth = textureDimension.width();
      this.textureHeight = textureDimension.height();
      this.u = uv.u();
      this.v = uv.v();
      this.width = dimension.width();
      this.height = dimension.height();
   }

   public ResourceLocation getTextureName() {
      return this.textureName;
   }

   public int getWidth() {
      return this.width;
   }

   public int getTextureWidth() {
      return this.textureWidth;
   }

   public int getTextureHeight() {
      return this.textureHeight;
   }

   public int getU() {
      return this.u;
   }

   public int getV() {
      return this.v;
   }

   public int getHeight() {
      return this.height;
   }

   public int getXOffset() {
      return this.xOffset;
   }

   public int getYOffset() {
      return this.yOffset;
   }
}
