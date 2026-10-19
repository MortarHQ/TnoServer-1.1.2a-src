package team.lodestar.lodestone.systems.rendering.cube;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public record CubeVertexData(Vector3f[] bottomVertices, Vector3f[] topVertices, List<Vector3f[]> offsetMap) {
   public static CubeVertexData makeCubePositions(float scale) {
      return makeCubePositions(scale, scale);
   }

   public static CubeVertexData makeCubePositions(float hScale, float vScale) {
      float xOffset = hScale / 2.0F;
      float yOffset = vScale / 2.0F;
      return makeCubePositions(-xOffset, xOffset, -yOffset, yOffset);
   }

   public static CubeVertexData makeCubePositions(float hStart, float hEnd, float vStart, float vEnd) {
      return makeCubePositions(hStart, hEnd, vStart, vEnd, hStart, hEnd);
   }

   public static CubeVertexData makeCubePositions(float xStart, float xEnd, float yStart, float yEnd, float zStart, float zEnd) {
      Vector3f[] bottomVertices = new Vector3f[]{
         new Vector3f(xStart, yStart, zStart), new Vector3f(xStart, yStart, zEnd), new Vector3f(xEnd, yStart, zEnd), new Vector3f(xEnd, yStart, zStart)
      };
      Vector3f[] topVertices = new Vector3f[]{
         new Vector3f(xStart, yEnd, zStart), new Vector3f(xStart, yEnd, zEnd), new Vector3f(xEnd, yEnd, zEnd), new Vector3f(xEnd, yEnd, zStart)
      };
      List<Vector3f[]> offsetMap = new ArrayList<>();

      for (int i = 0; i < 4; i++) {
         int index = (i * 3 + 3) % 4;
         offsetMap.add(new Vector3f[]{bottomVertices[index % 4], bottomVertices[(index + 1) % 4], topVertices[(index + 1) % 4], topVertices[index % 4]});
      }

      return new CubeVertexData(bottomVertices, topVertices, offsetMap);
   }

   public Vector3f[] getVerticesByDirection(Direction direction) {
      if (direction.equals(Direction.UP)) {
         return this.topVertices;
      } else {
         return direction.equals(Direction.DOWN)
            ? new Vector3f[]{this.bottomVertices[3], this.bottomVertices[2], this.bottomVertices[1], this.bottomVertices[0]}
            : this.getVerticesByIndex(direction.get2DDataValue());
      }
   }

   public Vector3f[] getVerticesByIndex(int index) {
      return this.offsetMap.get(index);
   }

   public CubeVertexData applyWobble(float sineOffset, float strength) {
      return this.applyWobble(sineOffset, sineOffset, strength);
   }

   public CubeVertexData applyWobble(float bottomSineOffset, float topSineOffset, float strength) {
      return this.applyWobble(this.bottomVertices, bottomSineOffset, strength).applyWobble(this.topVertices, topSineOffset, strength);
   }

   public CubeVertexData applyWobble(Vector3f[] offsets, float sineOffset, float strength) {
      applyVertexWobble(offsets, sineOffset, strength);
      return this;
   }

   public static void applyVertexWobble(Vector3f[] offsets, float sineOffset, float strength) {
      float offset = sineOffset;

      for (Vector3f vector3f : offsets) {
         double time = (float)Minecraft.getInstance().level.getGameTime() / 40.0F % 6.28F;
         float angle = (float)(time + offset * 6.28F);
         float sin = Mth.sin(angle) * strength;
         float cos = Mth.cos(angle) * strength;
         vector3f.add(sin, cos, 0.0F);
         offset += 0.25F;
      }
   }

   public CubeVertexData scale(float scale) {
      return this.scale(scale, scale);
   }

   public CubeVertexData scale(float width, float height) {
      return this.scale(width, height, width);
   }

   public CubeVertexData scale(float x, float y, float z) {
      for (int i = 0; i < this.bottomVertices.length; i++) {
         this.bottomVertices[i].mul(x, y, z);
         this.topVertices[i].mul(x, y, z);
      }

      return this;
   }

   public CubeVertexData offset(float x, float y, float z) {
      for (int i = 0; i < this.bottomVertices.length; i++) {
         this.bottomVertices[i].add(x, y, z);
         this.topVertices[i].add(x, y, z);
      }

      return this;
   }
}
