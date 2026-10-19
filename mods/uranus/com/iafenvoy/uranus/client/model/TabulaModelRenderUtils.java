package com.iafenvoy.uranus.client.model;

import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class TabulaModelRenderUtils {
   public static class ModelBox {
      public final TabulaModelRenderUtils.TexturedQuad[] quads;
      public final float posX1;
      public final float posY1;
      public final float posZ1;
      public final float posX2;
      public final float posY2;
      public final float posZ2;

      public ModelBox(
         int p_i225950_1_,
         int p_i225950_2_,
         float x,
         float y,
         float z,
         float _x,
         float _y,
         float _z,
         float p_i225950_9_,
         float p_i225950_10_,
         float p_i225950_11_,
         boolean mirror,
         float width,
         float height
      ) {
         this.posX1 = x;
         this.posY1 = y;
         this.posZ1 = z;
         this.posX2 = x + _x;
         this.posY2 = y + _y;
         this.posZ2 = z + _z;
         this.quads = new TabulaModelRenderUtils.TexturedQuad[6];
         float x2 = this.posX2;
         float y2 = this.posY2;
         float z2 = this.posZ2;
         x -= p_i225950_9_;
         y -= p_i225950_10_;
         z -= p_i225950_11_;
         x2 += p_i225950_9_;
         y2 += p_i225950_10_;
         z2 += p_i225950_11_;
         if (mirror) {
            float lvt_18_1_ = x2;
            x2 = x;
            x = lvt_18_1_;
         }

         TabulaModelRenderUtils.PositionTextureVertex vertex = new TabulaModelRenderUtils.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex1 = new TabulaModelRenderUtils.PositionTextureVertex(x2, y, z, 0.0F, 8.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex2 = new TabulaModelRenderUtils.PositionTextureVertex(x2, y2, z, 8.0F, 8.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex3 = new TabulaModelRenderUtils.PositionTextureVertex(x, y2, z, 8.0F, 0.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex4 = new TabulaModelRenderUtils.PositionTextureVertex(x, y, z2, 0.0F, 0.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex5 = new TabulaModelRenderUtils.PositionTextureVertex(x2, y, z2, 0.0F, 8.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex6 = new TabulaModelRenderUtils.PositionTextureVertex(x2, y2, z2, 8.0F, 8.0F);
         TabulaModelRenderUtils.PositionTextureVertex vertex7 = new TabulaModelRenderUtils.PositionTextureVertex(x, y2, z2, 8.0F, 0.0F);
         float u1 = p_i225950_1_;
         float u2 = p_i225950_1_ + _z;
         float u3 = p_i225950_1_ + _z + _x;
         float u4 = p_i225950_1_ + _z + _x + _x;
         float u5 = p_i225950_1_ + _z + _x + _z;
         float u6 = p_i225950_1_ + _z + _x + _z + _x;
         float v1 = p_i225950_2_;
         float v2 = p_i225950_2_ + _z;
         float v3 = p_i225950_2_ + _z + _y;
         this.quads[2] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex5, vertex4, vertex, vertex1}, u2, v1, u3, v2, width, height, mirror, Direction.DOWN
         );
         this.quads[3] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex2, vertex3, vertex7, vertex6}, u3, v2, u4, v1, width, height, mirror, Direction.UP
         );
         this.quads[1] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex, vertex4, vertex7, vertex3}, u1, v2, u2, v3, width, height, mirror, Direction.WEST
         );
         this.quads[4] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex1, vertex, vertex3, vertex2}, u2, v2, u3, v3, width, height, mirror, Direction.NORTH
         );
         this.quads[0] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex5, vertex1, vertex2, vertex6}, u3, v2, u5, v3, width, height, mirror, Direction.EAST
         );
         this.quads[5] = new TabulaModelRenderUtils.TexturedQuad(
            new TabulaModelRenderUtils.PositionTextureVertex[]{vertex4, vertex5, vertex6, vertex7}, u5, v2, u6, v3, width, height, mirror, Direction.SOUTH
         );
      }
   }

   public static class PositionTextureVertex {
      public final Vector3f position;
      public final float textureU;
      public final float textureV;

      public PositionTextureVertex(float x, float y, float z, float u, float v) {
         this(new Vector3f(x, y, z), u, v);
      }

      public PositionTextureVertex(Vector3f position, float u, float v) {
         this.position = position;
         this.textureU = u;
         this.textureV = v;
      }

      public TabulaModelRenderUtils.PositionTextureVertex setTextureUV(float u, float v) {
         return new TabulaModelRenderUtils.PositionTextureVertex(this.position, u, v);
      }
   }

   public static class TexturedQuad {
      public final TabulaModelRenderUtils.PositionTextureVertex[] vertexPositions;
      public final Vector3f normal;

      public TexturedQuad(
         TabulaModelRenderUtils.PositionTextureVertex[] textureVertices,
         float u1,
         float v1,
         float u2,
         float v2,
         float width,
         float height,
         boolean mirror,
         Direction direction
      ) {
         this.vertexPositions = textureVertices;
         textureVertices[0] = textureVertices[0].setTextureUV(u2 / width, v1 / height);
         textureVertices[1] = textureVertices[1].setTextureUV(u1 / width, v1 / height);
         textureVertices[2] = textureVertices[2].setTextureUV(u1 / width, v2 / height);
         textureVertices[3] = textureVertices[3].setTextureUV(u2 / width, v2 / height);
         if (mirror) {
            int length = textureVertices.length;

            for (int i = 0; i < length / 2; i++) {
               TabulaModelRenderUtils.PositionTextureVertex vertex = textureVertices[i];
               textureVertices[i] = textureVertices[length - 1 - i];
               textureVertices[length - 1 - i] = vertex;
            }
         }

         this.normal = direction.step();
         if (mirror) {
            this.normal.mul(-1.0F, 1.0F, 1.0F);
         }
      }
   }
}
