package dev.ftb.mods.ftblibrary.icon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import dev.ftb.mods.ftblibrary.math.PixelBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class AtlasSpriteIcon extends Icon implements IResourceIcon {
   private final ResourceLocation id;
   private final Color4I color;

   AtlasSpriteIcon(ResourceLocation id) {
      this(id, Color4I.WHITE);
   }

   AtlasSpriteIcon(ResourceLocation id, Color4I color) {
      this.id = id;
      this.color = color;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
      TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(this.id);
      if (sprite != null) {
         Matrix4f m = graphics.pose().last().pose();
         int r = this.color.redi();
         int g = this.color.greeni();
         int b = this.color.bluei();
         int a = this.color.alphai();
         float minU = sprite.getU0();
         float minV = sprite.getV0();
         float maxU = sprite.getU1();
         float maxV = sprite.getV1();
         RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShaderTexture(0, sprite.atlasLocation());
         BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
         buffer.addVertex(m, x, y, 0.0F).setUv(minU, minV).setColor(r, g, b, a);
         buffer.addVertex(m, x, y + h, 0.0F).setUv(minU, maxV).setColor(r, g, b, a);
         buffer.addVertex(m, x + w, y + h, 0.0F).setUv(maxU, maxV).setColor(r, g, b, a);
         buffer.addVertex(m, x + w, y, 0.0F).setUv(maxU, minV).setColor(r, g, b, a);
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      }
   }

   @Override
   public String toString() {
      return this.id.toString();
   }

   @Override
   public boolean hasPixelBuffer() {
      return true;
   }

   @Nullable
   @Override
   public PixelBuffer createPixelBuffer() {
      try {
         ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(this.id.getNamespace(), "textures/" + this.id.getPath() + ".png");
         return PixelBuffer.from(((Resource)Minecraft.getInstance().getResourceManager().getResource(loc).orElseThrow()).open());
      } catch (Exception ex) {
         return null;
      }
   }

   @Override
   public int getPixelBufferFrameCount() {
      TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(this.id);
      return sprite.contents().getFrameCount();
   }

   @Override
   public double aspectRatio() {
      TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(this.id);
      return (double)sprite.contents().width() / sprite.contents().height();
   }

   public AtlasSpriteIcon copy() {
      return new AtlasSpriteIcon(this.id);
   }

   public AtlasSpriteIcon withColor(Color4I color) {
      return new AtlasSpriteIcon(this.id, color);
   }

   public AtlasSpriteIcon withTint(Color4I c) {
      return this.withColor(this.color.withTint(c));
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.getId();
   }
}
