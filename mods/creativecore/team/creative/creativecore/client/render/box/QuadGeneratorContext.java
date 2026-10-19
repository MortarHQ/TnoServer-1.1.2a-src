package team.creative.creativecore.client.render.box;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import team.creative.creativecore.client.render.VertexFormatUtils;
import team.creative.creativecore.common.util.math.base.Facing;

public class QuadGeneratorContext {
   public RenderBox box;
   public Facing facing;
   public int color;
   public VertexFormat format;
   public int uvOffset;
   public BlockPos offset;
   public boolean shouldOverrideColor;
   public BakedQuad quad;
   public boolean scaleAndOffset;
   public float offsetX;
   public float offsetY;
   public float offsetZ;
   public float scaleX;
   public float scaleY;
   public float scaleZ;
   public float minX;
   public float minY;
   public float minZ;
   public float maxX;
   public float maxY;
   public float maxZ;
   public float sizeX;
   public float sizeY;
   public float sizeZ;
   public boolean uvInverted;
   public float sizeU;
   public float sizeV;

   public void set(VertexFormat format, RenderBox box, Facing facing, int color) {
      this.color = color;
      this.format = format;
      this.facing = facing;
      this.uvOffset = VertexFormatUtils.blockUvOffset() / 4;
      this.box = box;
      this.scaleAndOffset = box.scaleAndOffsetQuads(facing);
      if (this.scaleAndOffset) {
         if (box.onlyScaleOnceNoOffset(facing)) {
            this.offsetX = this.offsetY = this.offsetZ = 0.0F;
            this.scaleX = this.scaleY = this.scaleZ = box.getOverallScale(facing);
         } else {
            this.offsetX = box.getOffsetX();
            this.offsetY = box.getOffsetY();
            this.offsetZ = box.getOffsetZ();
            this.scaleX = box.getScaleX();
            this.scaleY = box.getScaleY();
            this.scaleZ = box.getScaleZ();
         }
      } else {
         this.offsetX = this.offsetY = this.offsetZ = 0.0F;
         this.scaleX = this.scaleY = this.scaleZ = 0.0F;
      }
   }

   public void setQuad(BakedQuad quad, boolean overrideTint, int defaultColor) {
      this.quad = quad;
      this.shouldOverrideColor = overrideTint && (defaultColor == -1 || quad.isTinted()) && this.color != -1;
   }

   public void setBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
      this.minX = minX;
      this.minY = minY;
      this.minZ = minZ;
      this.maxX = maxX;
      this.maxY = maxY;
      this.maxZ = maxZ;
      this.sizeX = this.maxX - this.minX;
      this.sizeY = this.maxY - this.minY;
      this.sizeZ = this.maxZ - this.minZ;
   }

   public boolean hasBounds() {
      return switch (this.facing.axis) {
         case X -> this.minY != 0.0F || this.maxY != 1.0F || this.minZ != 0.0F || this.maxZ != 1.0F;
         case Y -> this.minX != 0.0F || this.maxX != 1.0F || this.minZ != 0.0F || this.maxZ != 1.0F;
         case Z -> this.minX != 0.0F || this.maxX != 1.0F || this.minY != 0.0F || this.maxY != 1.0F;
      };
   }

   public void clear() {
      this.box = null;
      this.facing = null;
      this.format = null;
      this.quad = null;
   }
}
