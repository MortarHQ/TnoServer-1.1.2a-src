package team.creative.creativecore.client.render.box;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.client.render.VertexFormatUtils;
import team.creative.creativecore.client.render.face.RenderBoxFace;
import team.creative.creativecore.client.render.model.CreativeBakedQuad;
import team.creative.creativecore.common.util.math.base.Axis;
import team.creative.creativecore.common.util.math.base.Facing;
import team.creative.creativecore.common.util.math.box.AlignedBox;
import team.creative.creativecore.common.util.math.geo.VectorFan;
import team.creative.creativecore.common.util.math.vec.Vec3d;
import team.creative.creativecore.common.util.math.vec.Vec3f;
import team.creative.creativecore.common.util.mc.ColorUtils;

@OnlyIn(Dist.CLIENT)
public class RenderBox extends AlignedBox {
   private static final VectorFan DOWN = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(0.0F, 0.0F, 1.0F), new Vec3f(0.0F, 0.0F, 0.0F), new Vec3f(1.0F, 0.0F, 0.0F), new Vec3f(1.0F, 0.0F, 1.0F)}
   );
   private static final VectorFan UP = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(0.0F, 1.0F, 0.0F), new Vec3f(0.0F, 1.0F, 1.0F), new Vec3f(1.0F, 1.0F, 1.0F), new Vec3f(1.0F, 1.0F, 0.0F)}
   );
   private static final VectorFan NORTH = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 0.0F, 0.0F), new Vec3f(0.0F, 0.0F, 0.0F), new Vec3f(0.0F, 1.0F, 0.0F)}
   );
   private static final VectorFan SOUTH = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(0.0F, 1.0F, 1.0F), new Vec3f(0.0F, 0.0F, 1.0F), new Vec3f(1.0F, 0.0F, 1.0F), new Vec3f(1.0F, 1.0F, 1.0F)}
   );
   private static final VectorFan WEST = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(0.0F, 1.0F, 0.0F), new Vec3f(0.0F, 0.0F, 0.0F), new Vec3f(0.0F, 0.0F, 1.0F), new Vec3f(0.0F, 1.0F, 1.0F)}
   );
   private static final VectorFan EAST = new RenderBox.VectorFanSimple(
      new Vec3f[]{new Vec3f(1.0F, 1.0F, 1.0F), new Vec3f(1.0F, 0.0F, 1.0F), new Vec3f(1.0F, 0.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F)}
   );
   public BlockState state;
   public int color = -1;
   public boolean keepVU = false;
   public boolean allowOverlap = false;
   public boolean doesNeedQuadUpdate = true;
   public boolean emissive = false;
   private RenderBoxFace renderEast = RenderBoxFace.RENDER;
   private RenderBoxFace renderWest = RenderBoxFace.RENDER;
   private RenderBoxFace renderUp = RenderBoxFace.RENDER;
   private RenderBoxFace renderDown = RenderBoxFace.RENDER;
   private RenderBoxFace renderSouth = RenderBoxFace.RENDER;
   private RenderBoxFace renderNorth = RenderBoxFace.RENDER;
   private Object quadEast = null;
   private Object quadWest = null;
   private Object quadUp = null;
   private Object quadDown = null;
   private Object quadSouth = null;
   private Object quadNorth = null;
   public Object customData;

   public RenderBox(AlignedBox cube) {
      super(cube);
   }

   public RenderBox(AlignedBox cube, RenderBox box) {
      super(cube);
      this.state = box.state;
      this.color = box.color;
      this.renderEast = box.renderEast;
      this.renderWest = box.renderWest;
      this.renderUp = box.renderUp;
      this.renderDown = box.renderDown;
      this.renderSouth = box.renderSouth;
      this.renderNorth = box.renderNorth;
   }

   public RenderBox(AlignedBox cube, BlockState state) {
      super(cube);
      this.state = state;
   }

   public RenderBox(AlignedBox cube, Block block) {
      this(cube, block.defaultBlockState());
   }

   public RenderBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, BlockState state) {
      super(minX, minY, minZ, maxX, maxY, maxZ);
      this.state = state;
   }

   public RenderBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Block block) {
      this(minX, minY, minZ, maxX, maxY, maxZ, block.defaultBlockState());
   }

   public RenderBox setColor(int color) {
      this.color = color;
      return this;
   }

   public RenderBox setKeepUV(boolean keep) {
      this.keepVU = keep;
      return this;
   }

   public void setQuad(Facing facing, List<BakedQuad> quads) {
      Object quad = quads != null && !quads.isEmpty() ? (quads.size() == 1 ? quads.get(0) : quads) : null;
      switch (facing) {
         case DOWN:
            this.quadDown = quad;
            break;
         case EAST:
            this.quadEast = quad;
            break;
         case NORTH:
            this.quadNorth = quad;
            break;
         case SOUTH:
            this.quadSouth = quad;
            break;
         case UP:
            this.quadUp = quad;
            break;
         case WEST:
            this.quadWest = quad;
      }
   }

   public Object getQuad(Facing facing) {
      return switch (facing) {
         case DOWN -> this.quadDown;
         case EAST -> this.quadEast;
         case NORTH -> this.quadNorth;
         case SOUTH -> this.quadSouth;
         case UP -> this.quadUp;
         case WEST -> this.quadWest;
      };
   }

   public int countQuads() {
      int quads = 0;
      if (this.quadUp != null) {
         quads += this.quadUp instanceof List ? ((List)this.quadUp).size() : 1;
      }

      if (this.quadDown != null) {
         quads += this.quadDown instanceof List ? ((List)this.quadDown).size() : 1;
      }

      if (this.quadEast != null) {
         quads += this.quadEast instanceof List ? ((List)this.quadEast).size() : 1;
      }

      if (this.quadWest != null) {
         quads += this.quadWest instanceof List ? ((List)this.quadWest).size() : 1;
      }

      if (this.quadSouth != null) {
         quads += this.quadSouth instanceof List ? ((List)this.quadSouth).size() : 1;
      }

      if (this.quadNorth != null) {
         quads += this.quadNorth instanceof List ? ((List)this.quadNorth).size() : 1;
      }

      return quads;
   }

   public void setFace(Facing facing, RenderBoxFace face) {
      switch (facing) {
         case DOWN:
            this.renderDown = face;
            break;
         case EAST:
            this.renderEast = face;
            break;
         case NORTH:
            this.renderNorth = face;
            break;
         case SOUTH:
            this.renderSouth = face;
            break;
         case UP:
            this.renderUp = face;
            break;
         case WEST:
            this.renderWest = face;
      }
   }

   public RenderBoxFace getFace(Facing facing) {
      return switch (facing) {
         case DOWN -> this.renderDown;
         case EAST -> this.renderEast;
         case NORTH -> this.renderNorth;
         case SOUTH -> this.renderSouth;
         case UP -> this.renderUp;
         case WEST -> this.renderWest;
      };
   }

   public boolean shouldRenderFace(Facing facing) {
      return switch (facing) {
         case DOWN -> this.renderDown.shouldRender();
         case EAST -> this.renderEast.shouldRender();
         case NORTH -> this.renderNorth.shouldRender();
         case SOUTH -> this.renderSouth.shouldRender();
         case UP -> this.renderUp.shouldRender();
         case WEST -> this.renderWest.shouldRender();
      };
   }

   public boolean intersectsWithFace(Facing facing, QuadGeneratorContext holder, BlockPos offset) {
      return switch (facing.axis) {
         case X -> holder.maxY > this.minY - offset.getY()
            && holder.minY < this.maxY - offset.getY()
            && holder.maxZ > this.minZ - offset.getZ()
            && holder.minZ < this.maxZ - offset.getZ();
         case Y -> holder.maxX > this.minX - offset.getX()
            && holder.minX < this.maxX - offset.getX()
            && holder.maxZ > this.minZ - offset.getZ()
            && holder.minZ < this.maxZ - offset.getZ();
         case Z -> holder.maxX > this.minX - offset.getX()
            && holder.minX < this.maxX - offset.getX()
            && holder.maxY > this.minY - offset.getY()
            && holder.minY < this.maxY - offset.getY();
      };
   }

   protected Object getRenderQuads(Facing facing) {
      if (this.getFace(facing).hasCachedFans()) {
         return this.getFace(facing).getCachedFans();
      }

      return switch (facing) {
         case DOWN -> DOWN;
         case EAST -> EAST;
         case NORTH -> NORTH;
         case SOUTH -> SOUTH;
         case UP -> UP;
         case WEST -> WEST;
      };
   }

   protected float getOffsetX() {
      return this.minX;
   }

   protected float getOffsetY() {
      return this.minY;
   }

   protected float getOffsetZ() {
      return this.minZ;
   }

   protected float getOverallScale(Facing facing) {
      return this.getFace(facing).getScale();
   }

   protected float getScaleX() {
      return this.maxX - this.minX;
   }

   protected float getScaleY() {
      return this.maxY - this.minY;
   }

   protected float getScaleZ() {
      return this.maxZ - this.minZ;
   }

   protected boolean scaleAndOffsetQuads(Facing facing) {
      return true;
   }

   protected boolean onlyScaleOnceNoOffset(Facing facing) {
      return this.getFace(facing).hasCachedFans();
   }

   public void deleteQuadCache() {
      this.doesNeedQuadUpdate = true;
      this.quadEast = null;
      this.quadWest = null;
      this.quadUp = null;
      this.quadDown = null;
      this.quadSouth = null;
      this.quadNorth = null;
   }

   protected boolean previewScalingAndOffset() {
      return true;
   }

   public float getPreviewOffX() {
      return this.minX;
   }

   public float getPreviewOffY() {
      return this.minY;
   }

   public float getPreviewOffZ() {
      return this.minZ;
   }

   public float getPreviewScaleX() {
      return this.maxX - this.minX;
   }

   public float getPreviewScaleY() {
      return this.maxY - this.minY;
   }

   public float getPreviewScaleZ() {
      return this.maxZ - this.minZ;
   }

   public void renderPreview(PoseStack pose, VertexConsumer consumer, int alpha) {
      int red = ColorUtils.red(this.color);
      int green = ColorUtils.green(this.color);
      int blue = ColorUtils.blue(this.color);
      if (this.previewScalingAndOffset()) {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j))
                     .renderPreview(
                        pose.last().pose(),
                        consumer,
                        this.getPreviewOffX(),
                        this.getPreviewOffY(),
                        this.getPreviewOffZ(),
                        this.getPreviewScaleX(),
                        this.getPreviewScaleY(),
                        this.getPreviewScaleZ(),
                        red,
                        green,
                        blue,
                        alpha
                     );
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderPreview(
                  pose.last().pose(),
                  consumer,
                  this.getPreviewOffX(),
                  this.getPreviewOffY(),
                  this.getPreviewOffZ(),
                  this.getPreviewScaleX(),
                  this.getPreviewScaleY(),
                  this.getPreviewScaleZ(),
                  red,
                  green,
                  blue,
                  alpha
               );
            }
         }
      } else {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j)).renderPreview(pose.last().pose(), consumer, red, green, blue, alpha);
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderPreview(pose.last().pose(), consumer, red, green, blue, alpha);
            }
         }
      }
   }

   public void renderLines(PoseStack pose, VertexConsumer consumer, int alpha) {
      int red = ColorUtils.red(this.color);
      int green = ColorUtils.green(this.color);
      int blue = ColorUtils.blue(this.color);
      if (red == 1 && green == 1 && blue == 1) {
         blue = 0;
         green = 0;
         red = 0;
      }

      if (this.previewScalingAndOffset()) {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j))
                     .renderLines(
                        pose.last(),
                        consumer,
                        this.getPreviewOffX(),
                        this.getPreviewOffY(),
                        this.getPreviewOffZ(),
                        this.getPreviewScaleX(),
                        this.getPreviewScaleY(),
                        this.getPreviewScaleZ(),
                        red,
                        green,
                        blue,
                        alpha
                     );
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderLines(
                  pose.last(),
                  consumer,
                  this.getPreviewOffX(),
                  this.getPreviewOffY(),
                  this.getPreviewOffZ(),
                  this.getPreviewScaleX(),
                  this.getPreviewScaleY(),
                  this.getPreviewScaleZ(),
                  red,
                  green,
                  blue,
                  alpha
               );
            }
         }
      } else {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List) {
               for (int j = 0; j < ((List)renderQuads).size(); j++) {
                  ((VectorFan)((List)renderQuads).get(j)).renderLines(pose.last(), consumer, red, green, blue, alpha);
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderLines(pose.last(), consumer, red, green, blue, alpha);
            }
         }
      }
   }

   public void renderLines(PoseStack pose, VertexConsumer consumer, int alpha, Vec3d center, double grow) {
      int red = ColorUtils.red(this.color);
      int green = ColorUtils.green(this.color);
      int blue = ColorUtils.blue(this.color);
      if (red == 1 && green == 1 && blue == 1) {
         blue = 0;
         green = 0;
         red = 0;
      }

      if (this.previewScalingAndOffset()) {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j))
                     .renderLines(
                        pose.last(),
                        consumer,
                        this.getPreviewOffX(),
                        this.getPreviewOffY(),
                        this.getPreviewOffZ(),
                        this.getPreviewScaleX(),
                        this.getPreviewScaleY(),
                        this.getPreviewScaleZ(),
                        red,
                        green,
                        blue,
                        alpha,
                        center,
                        grow
                     );
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderLines(
                  pose.last(),
                  consumer,
                  this.getPreviewOffX(),
                  this.getPreviewOffY(),
                  this.getPreviewOffZ(),
                  this.getPreviewScaleX(),
                  this.getPreviewScaleY(),
                  this.getPreviewScaleZ(),
                  red,
                  green,
                  blue,
                  alpha,
                  center,
                  grow
               );
            }
         }
      } else {
         for (int i = 0; i < Facing.values().length; i++) {
            Object renderQuads = this.getRenderQuads(Facing.values()[i]);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j)).renderLines(pose.last(), consumer, red, green, blue, alpha, center, grow);
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.renderLines(pose.last(), consumer, red, green, blue, alpha, center, grow);
            }
         }
      }
   }

   public boolean isTranslucent() {
      return ColorUtils.isTransparent(this.color) ? true : !this.state.isSolid();
   }

   public List<BakedQuad> getBakedQuad(
      QuadGeneratorContext holder,
      LevelAccessor level,
      @Nullable BlockPos pos,
      BlockPos offset,
      BlockState state,
      BakedModel blockModel,
      ModelData modelData,
      Facing facing,
      RenderType layer,
      RandomSource rand,
      boolean overrideTint,
      int defaultColor
   ) {
      if (pos != null) {
         rand.setSeed(state.getSeed(pos));
      }

      List<BakedQuad> blockQuads = blockModel.getQuads(state, facing.toVanilla(), rand, modelData, layer);
      if (blockQuads.isEmpty()) {
         return Collections.emptyList();
      }

      holder.set(DefaultVertexFormat.BLOCK, this, facing, this.color != -1 ? this.color : defaultColor);
      holder.offset = offset;
      List<BakedQuad> quads = new ArrayList<>();

      for (int i = 0; i < blockQuads.size(); i++) {
         holder.setQuad(blockQuads.get(i), overrideTint, defaultColor);
         int[] data = holder.quad.getVertices();
         int index = 0;
         int uvIndex = index + holder.uvOffset;
         float tempMinX = Float.intBitsToFloat(data[index]);
         float tempMinY = Float.intBitsToFloat(data[index + 1]);
         float tempMinZ = Float.intBitsToFloat(data[index + 2]);
         float tempU = Float.intBitsToFloat(data[uvIndex]);
         holder.uvInverted = false;
         index += VertexFormatUtils.blockFormatIntSize();
         uvIndex = index + holder.uvOffset;
         if (tempMinX != Float.intBitsToFloat(data[index])) {
            if (tempU != Float.intBitsToFloat(data[uvIndex])) {
               holder.uvInverted = Axis.X != facing.getUAxis();
            } else {
               holder.uvInverted = Axis.X != facing.getVAxis();
            }
         } else if (tempMinY != Float.intBitsToFloat(data[index + 1])) {
            if (tempU != Float.intBitsToFloat(data[uvIndex])) {
               holder.uvInverted = Axis.Y != facing.getUAxis();
            } else {
               holder.uvInverted = Axis.Y != facing.getVAxis();
            }
         } else if (tempU != Float.intBitsToFloat(data[uvIndex])) {
            holder.uvInverted = Axis.Z != facing.getUAxis();
         } else {
            holder.uvInverted = Axis.Z != facing.getVAxis();
         }

         float x = Float.intBitsToFloat(data[index]);
         float y = Float.intBitsToFloat(data[index + 1]);
         float z = Float.intBitsToFloat(data[index + 2]);
         float minX = Math.min(x, tempMinX);
         float minY = Math.min(y, tempMinY);
         float minZ = Math.min(z, tempMinZ);
         float maxX = Math.max(x, tempMinX);
         float maxY = Math.max(y, tempMinY);
         float maxZ = Math.max(z, tempMinZ);
         index += VertexFormatUtils.blockFormatIntSize();
         float tempMaxX = Float.intBitsToFloat(data[index]);
         float tempMaxY = Float.intBitsToFloat(data[index + 1]);
         float tempMaxZ = Float.intBitsToFloat(data[index + 2]);
         minX = Math.min(minX, tempMaxX);
         minY = Math.min(minY, tempMaxY);
         minZ = Math.min(minZ, tempMaxZ);
         maxX = Math.max(maxX, tempMaxX);
         maxY = Math.max(maxY, tempMaxY);
         maxZ = Math.max(maxZ, tempMaxZ);
         holder.setBounds(minX, minY, minZ, maxX, maxY, maxZ);
         if (this.intersectsWithFace(facing, holder, offset)) {
            uvIndex = holder.uvOffset;
            float u1 = Float.intBitsToFloat(data[uvIndex]);
            float v1 = Float.intBitsToFloat(data[uvIndex + 1]);
            uvIndex = 2 * VertexFormatUtils.blockFormatIntSize() + holder.uvOffset;
            float u2 = Float.intBitsToFloat(data[uvIndex]);
            float v2 = Float.intBitsToFloat(data[uvIndex + 1]);
            if (holder.uvInverted) {
               holder.sizeU = facing.getV(tempMinX, tempMinY, tempMinZ) < facing.getV(tempMaxX, tempMaxY, tempMaxZ) ? u2 - u1 : u1 - u2;
               holder.sizeV = facing.getU(tempMinX, tempMinY, tempMinZ) < facing.getU(tempMaxX, tempMaxY, tempMaxZ) ? v2 - v1 : v1 - v2;
            } else {
               holder.sizeU = facing.getU(tempMinX, tempMinY, tempMinZ) < facing.getU(tempMaxX, tempMaxY, tempMaxZ) ? u2 - u1 : u1 - u2;
               holder.sizeV = facing.getV(tempMinX, tempMinY, tempMinZ) < facing.getV(tempMaxX, tempMaxY, tempMaxZ) ? v2 - v1 : v1 - v2;
            }

            Object renderQuads = this.getRenderQuads(holder.facing);
            if (renderQuads instanceof List list) {
               for (int j = 0; j < list.size(); j++) {
                  ((VectorFan)list.get(j)).generate(holder, quads);
               }
            } else if (renderQuads instanceof VectorFan fan) {
               fan.generate(holder, quads);
            }
         }
      }

      for (BakedQuad quad : quads) {
         if (quad instanceof CreativeBakedQuad c) {
            c.updateAlpha();
         }
      }

      return quads;
   }

   private static class VectorFanSimple extends VectorFan {
      public VectorFanSimple(Vec3f[] coords) {
         super(coords);
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      public void generate(QuadGeneratorContext holder, List<BakedQuad> quads) {
         int index;
         for (index = 0; index < this.coords.length - 3; index += 2) {
            this.generate(holder, this.coords[0], this.coords[index + 1], this.coords[index + 2], this.coords[index + 3], quads);
         }

         if (index < this.coords.length - 2) {
            this.generate(holder, this.coords[0], this.coords[index + 1], this.coords[index + 2], this.coords[index + 2], quads);
         }
      }

      @Override
      protected boolean doMinMaxLate() {
         return true;
      }
   }
}
