package net.darkhax.bookshelf.common.api.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.darkhax.bookshelf.common.api.service.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.joml.Matrix4f;

public interface IRenderHelper {
   IRenderHelper GET = Services.load(IRenderHelper.class);

   default TextureAtlasSprite blockSprite(ResourceLocation texturePath) {
      return (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texturePath);
   }

   void renderFluidBox(PoseStack var1, FluidState var2, Level var3, BlockPos var4, MultiBufferSource var5, int var6, int var7);

   default int[] unpackARGB(int color) {
      return new int[]{color >> 24 & 0xFF, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF};
   }

   default void renderBox(VertexConsumer builder, PoseStack stack, TextureAtlasSprite sprite, int light, int overlay, int[] color) {
      this.renderBox(builder, stack.last().pose(), sprite, light, overlay, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, color);
   }

   default void renderBox(
      VertexConsumer builder,
      PoseStack stack,
      TextureAtlasSprite sprite,
      int light,
      int overlay,
      float x1,
      float x2,
      float y1,
      float y2,
      float z1,
      float z2,
      int[] color
   ) {
      this.renderBox(builder, stack.last().pose(), sprite, light, overlay, x1, x2, y1, y2, z1, z2, color);
   }

   default void renderBox(
      VertexConsumer builder,
      Matrix4f pos,
      TextureAtlasSprite sprite,
      int light,
      int overlay,
      float x1,
      float x2,
      float y1,
      float y2,
      float z1,
      float z2,
      int[] color
   ) {
      this.renderFace(builder, pos, sprite, Direction.DOWN, light, overlay, x1, x2, y1, y2, z1, z2, color);
      this.renderFace(builder, pos, sprite, Direction.UP, light, overlay, x1, x2, y1, y2, z1, z2, color);
      this.renderFace(builder, pos, sprite, Direction.NORTH, light, overlay, x1, x2, y1, y2, z1, z2, color);
      this.renderFace(builder, pos, sprite, Direction.SOUTH, light, overlay, x1, x2, y1, y2, z1, z2, color);
      this.renderFace(builder, pos, sprite, Direction.WEST, light, overlay, x1, x2, y1, y2, z1, z2, color);
      this.renderFace(builder, pos, sprite, Direction.EAST, light, overlay, x1, x2, y1, y2, z1, z2, color);
   }

   default void renderFace(
      VertexConsumer builder,
      Matrix4f pos,
      TextureAtlasSprite sprite,
      Direction side,
      int light,
      int overlay,
      float x1,
      float x2,
      float y1,
      float y2,
      float z1,
      float z2,
      int[] color
   ) {
      switch (side) {
         case DOWN: {
            float u1 = sprite.getU(x1);
            float u2 = sprite.getU(x2);
            float v1 = sprite.getV(z1);
            float v2 = sprite.getV(z2);
            builder.addVertex(pos, x1, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pos, x1, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pos, x2, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, -1.0F, 0.0F);
            builder.addVertex(pos, x2, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, -1.0F, 0.0F);
            break;
         }
         case UP: {
            float u1 = sprite.getU(x1);
            float u2 = sprite.getU(x2);
            float v1 = sprite.getV(z1);
            float v2 = sprite.getV(z2);
            builder.addVertex(pos, x1, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pos, x2, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pos, x2, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(pos, x1, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 1.0F, 0.0F);
            break;
         }
         case NORTH: {
            float u1 = sprite.getU(x1);
            float u2 = sprite.getU(x2);
            float v1 = sprite.getV(y1);
            float v2 = sprite.getV(y2);
            builder.addVertex(pos, x1, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pos, x1, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pos, x2, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, -1.0F);
            builder.addVertex(pos, x2, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, -1.0F);
            break;
         }
         case SOUTH: {
            float u1 = sprite.getU(x1);
            float u2 = sprite.getU(x2);
            float v1 = sprite.getV(y1);
            float v2 = sprite.getV(y2);
            builder.addVertex(pos, x2, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pos, x2, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pos, x1, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, 1.0F);
            builder.addVertex(pos, x1, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(0.0F, 0.0F, 1.0F);
            break;
         }
         case WEST: {
            float u1 = sprite.getU(y1);
            float u2 = sprite.getU(y2);
            float v1 = sprite.getV(z1);
            float v2 = sprite.getV(z2);
            builder.addVertex(pos, x1, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x1, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x1, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(-1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x1, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(-1.0F, 0.0F, 0.0F);
            break;
         }
         case EAST: {
            float u1 = sprite.getU(y1);
            float u2 = sprite.getU(y2);
            float v1 = sprite.getV(z1);
            float v2 = sprite.getV(z2);
            builder.addVertex(pos, x2, y1, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x2, y2, z1)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v1)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x2, y2, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u2, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(1.0F, 0.0F, 0.0F);
            builder.addVertex(pos, x2, y1, z2)
               .setColor(color[1], color[2], color[3], color[0])
               .setUv(u1, v2)
               .setOverlay(overlay)
               .setLight(light)
               .setNormal(1.0F, 0.0F, 0.0F);
         }
      }
   }
}
