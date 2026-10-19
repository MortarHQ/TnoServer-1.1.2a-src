package team.lodestar.lodestone.systems.rendering;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import team.lodestar.lodestone.handlers.LodestoneRenderHandler;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.rendering.buffer.LodestoneRenderLayer;
import team.lodestar.lodestone.systems.rendering.cube.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderTypeBuilder;
import team.lodestar.lodestone.systems.rendering.trail.TrailPoint;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointRenderData;

public class VFXBuilders {
   public static final HashMap<VertexFormatElement, VFXBuilders.VertexConsumerActor> CONSUMER_INFO_MAP = new HashMap<>();

   public static Vector3f normal(PoseStack stack) {
      return normal(stack.last().normal());
   }

   public static Vector3f normal(Matrix3f transform) {
      return new Vector3f(0.0F, 1.0F, 0.0F).mul(transform);
   }

   public static VFXBuilders.ScreenVFXBuilder createScreen() {
      return new VFXBuilders.ScreenVFXBuilder();
   }

   public static VFXBuilders.WorldVFXBuilder createWorld() {
      return new VFXBuilders.WorldVFXBuilder();
   }

   static {
      CONSUMER_INFO_MAP.put(VertexFormatElement.POSITION, (consumer, pose, builder, normal, x, y, z, u, v) -> {
         if (pose == null) {
            consumer.addVertex(x, y, z);
         } else {
            consumer.addVertex(pose, x, y, z);
         }
      });
      CONSUMER_INFO_MAP.put(VertexFormatElement.NORMAL, (consumer, pose, builder, normal, x, y, z, u, v) -> {
         if (normal != null) {
            if (pose == null) {
               consumer.setNormal(normal.x, normal.y, normal.z);
            } else {
               consumer.setNormal(pose, normal.x, normal.y, normal.z);
            }
         }
      });
      CONSUMER_INFO_MAP.put(
         VertexFormatElement.COLOR, (consumer, pose, builder, normal, x, y, z, u, v) -> consumer.setColor(builder.r, builder.g, builder.b, builder.a)
      );
      CONSUMER_INFO_MAP.put(VertexFormatElement.UV0, (consumer, pose, builder, normal, x, y, z, u, v) -> consumer.setUv(u, v));
      CONSUMER_INFO_MAP.put(VertexFormatElement.UV2, (consumer, pose, builder, normal, x, y, z, u, v) -> consumer.setLight(builder.light));
   }

   public abstract static class AbstractVFXBuilder {
      float r = 1.0F;
      float g = 1.0F;
      float b = 1.0F;
      float a = 1.0F;
      int light = 15728880;
      float u0 = 0.0F;
      float v0 = 0.0F;
      float u1 = 1.0F;
      float v1 = 1.0F;
      VertexFormat format;
      VFXBuilders.VertexConsumerActor supplier;
      Mode mode = Mode.QUADS;

      public VFXBuilders.AbstractVFXBuilder setFormat(VertexFormat format) {
         ImmutableList<VertexFormatElement> elements = ImmutableList.copyOf(format.getElements());
         return this.setFormatRaw(format).setVertexSupplier((consumer, last, builder, normal, x, y, z, u, v) -> {
            UnmodifiableIterator var11 = elements.iterator();

            while (var11.hasNext()) {
               VertexFormatElement element = (VertexFormatElement)var11.next();
               VFXBuilders.CONSUMER_INFO_MAP.get(element).placeVertex(consumer, last, this, normal, x, y, z, u, v);
            }
         });
      }

      public VFXBuilders.AbstractVFXBuilder setVertexSupplier(VFXBuilders.VertexConsumerActor supplier) {
         this.supplier = supplier;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder setFormatRaw(VertexFormat format) {
         this.format = format;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder setColor(int rgba) {
         return this.setColor(rgba >> 16 & 0xFF, rgba >> 8 & 0xFF, rgba & 0xFF, rgba >> 24 & 0xFF);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(Color color) {
         return this.setColor(color.getRed(), color.getGreen(), color.getBlue());
      }

      public VFXBuilders.AbstractVFXBuilder setColor(Color color, int a) {
         return this.setColor(color).setAlpha(a);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(Color color, float a) {
         return this.setColor(color).setAlpha(a);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(int r, int g, int b, int a) {
         return this.setColor(r, g, b).setAlpha(a);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(float r, float g, float b, float a) {
         return this.setColor(r, g, b).setAlpha(a);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(int r, int g, int b) {
         return this.setColor(r / 255.0F, g / 255.0F, b / 255.0F);
      }

      public VFXBuilders.AbstractVFXBuilder setColor(float r, float g, float b) {
         this.r = r;
         this.g = g;
         this.b = b;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder multiplyColor(float scalar) {
         return this.multiplyColor(scalar, scalar, scalar);
      }

      public VFXBuilders.AbstractVFXBuilder multiplyColor(float r, float g, float b) {
         return this.setColor(this.r * r, this.g * g, this.b * b);
      }

      public VFXBuilders.AbstractVFXBuilder setAlpha(int a) {
         return this.setAlpha(a / 255.0F);
      }

      public VFXBuilders.AbstractVFXBuilder setAlpha(float a) {
         this.a = a;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder setLight(int light) {
         this.light = light;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSize) {
         return this.setUVWithWidth(u, v, width, height, canvasSize, canvasSize);
      }

      public VFXBuilders.AbstractVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSizeX, float canvasSizeY) {
         return this.setUVWithWidth(u / canvasSizeX, v / canvasSizeY, width / canvasSizeX, height / canvasSizeY);
      }

      public VFXBuilders.AbstractVFXBuilder setUVWithWidth(float u, float v, float width, float height) {
         this.u0 = u;
         this.v0 = v;
         this.u1 = u + width;
         this.v1 = v + height;
         return this;
      }

      public VFXBuilders.AbstractVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSize) {
         return this.setUV(u0, v0, u1, v1, canvasSize, canvasSize);
      }

      public VFXBuilders.AbstractVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSizeX, float canvasSizeY) {
         return this.setUV(u0 / canvasSizeX, v0 / canvasSizeY, u1 / canvasSizeX, v1 / canvasSizeY);
      }

      public VFXBuilders.AbstractVFXBuilder setUV(float u0, float v0, float u1, float v1) {
         this.u0 = u0;
         this.v0 = v0;
         this.u1 = u1;
         this.v1 = v1;
         return this;
      }
   }

   public static class ScreenVFXBuilder extends VFXBuilders.AbstractVFXBuilder {
      float x0 = 0.0F;
      float y0 = 0.0F;
      float x1 = 1.0F;
      float y1 = 1.0F;
      int zLevel;
      Supplier<ShaderInstance> shader;
      ResourceLocation texture;
      Tesselator tesselator = Tesselator.getInstance();

      public VFXBuilders.ScreenVFXBuilder setColor(int rgba) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(rgba);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(Color color) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(color);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(Color color, int a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(color, a);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(Color color, float a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(color, a);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(int r, int g, int b, int a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(r, g, b, a);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(float r, float g, float b, float a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(r, g, b, a);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(int r, int g, int b) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(r, g, b);
      }

      public VFXBuilders.ScreenVFXBuilder setColor(float r, float g, float b) {
         return (VFXBuilders.ScreenVFXBuilder)super.setColor(r, g, b);
      }

      public VFXBuilders.ScreenVFXBuilder multiplyColor(float scalar) {
         return (VFXBuilders.ScreenVFXBuilder)super.multiplyColor(scalar);
      }

      public VFXBuilders.ScreenVFXBuilder multiplyColor(float r, float g, float b) {
         return (VFXBuilders.ScreenVFXBuilder)super.multiplyColor(r, g, b);
      }

      public VFXBuilders.ScreenVFXBuilder setAlpha(int a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setAlpha(a);
      }

      public VFXBuilders.ScreenVFXBuilder setAlpha(float a) {
         return (VFXBuilders.ScreenVFXBuilder)super.setAlpha(a);
      }

      public VFXBuilders.ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSize) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUVWithWidth(u, v, width, height, canvasSize);
      }

      public VFXBuilders.ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSizeX, float canvasSizeY) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUVWithWidth(u, v, width, height, canvasSizeX, canvasSizeY);
      }

      public VFXBuilders.ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUVWithWidth(u, v, width, height);
      }

      public VFXBuilders.ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSize) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUV(u0, v0, u1, v1, canvasSize);
      }

      public VFXBuilders.ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSizeX, float canvasSizeY) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUV(u0, v0, u1, v1, canvasSizeX, canvasSizeY);
      }

      public VFXBuilders.ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1) {
         return (VFXBuilders.ScreenVFXBuilder)super.setUV(u0, v0, u1, v1);
      }

      public VFXBuilders.ScreenVFXBuilder setFormat(VertexFormat format) {
         return (VFXBuilders.ScreenVFXBuilder)super.setFormat(format);
      }

      public VFXBuilders.ScreenVFXBuilder setVertexSupplier(VFXBuilders.VertexConsumerActor supplier) {
         return (VFXBuilders.ScreenVFXBuilder)super.setVertexSupplier(supplier);
      }

      public VFXBuilders.ScreenVFXBuilder setFormatRaw(VertexFormat format) {
         return (VFXBuilders.ScreenVFXBuilder)super.setFormatRaw(format);
      }

      public VFXBuilders.ScreenVFXBuilder setShader(Supplier<ShaderInstance> shader) {
         this.shader = shader;
         return this.updateVertexFormat();
      }

      public VFXBuilders.ScreenVFXBuilder setShader(ShaderInstance shader) {
         this.shader = () -> shader;
         return this.updateVertexFormat();
      }

      public Supplier<ShaderInstance> getShader() {
         if (this.shader == null) {
            this.setShader(GameRenderer::getPositionTexShader);
         }

         return this.shader;
      }

      public VFXBuilders.ScreenVFXBuilder setTexture(ResourceLocation texture) {
         this.texture = texture;
         return this;
      }

      public VFXBuilders.ScreenVFXBuilder setSprite(ResourceLocation location) {
         TextureAtlasSprite sprite = Minecraft.getInstance().getGuiSprites().getSprite(location);
         this.u0 = sprite.getU0();
         this.v0 = sprite.getV0();
         this.u1 = sprite.getU1();
         this.v1 = sprite.getV1();
         return this.setTexture(sprite.atlasLocation());
      }

      public final VFXBuilders.ScreenVFXBuilder updateVertexFormat() {
         return this.setFormat(this.getShader().get().getVertexFormat());
      }

      public VFXBuilders.ScreenVFXBuilder setLight(int light) {
         this.light = light;
         return this;
      }

      public VFXBuilders.ScreenVFXBuilder setPositionWithWidth(float x, float y, float width, float height) {
         return this.setPosition(x, y, x + width, y + height);
      }

      public VFXBuilders.ScreenVFXBuilder setPosition(float x0, float y0, float x1, float y1) {
         this.x0 = x0;
         this.y0 = y0;
         this.x1 = x1;
         this.y1 = y1;
         return this;
      }

      public VFXBuilders.ScreenVFXBuilder setZLevel(int z) {
         this.zLevel = z;
         return this;
      }

      public VFXBuilders.ScreenVFXBuilder blit(GuiGraphics graphics) {
         return this.blit(graphics.pose());
      }

      public VFXBuilders.ScreenVFXBuilder blit(PoseStack stack) {
         RenderSystem.setShader(this.getShader());
         if (this.texture != null) {
            RenderSystem.setShaderTexture(0, this.texture);
         }

         BufferBuilder bufferBuilder = this.tesselator.begin(this.mode, this.format);
         this.supplier.placeVertex(bufferBuilder, stack, this, this.x0, this.y1, this.zLevel, this.u0, this.v1);
         this.supplier.placeVertex(bufferBuilder, stack, this, this.x1, this.y1, this.zLevel, this.u1, this.v1);
         this.supplier.placeVertex(bufferBuilder, stack, this, this.x1, this.y0, this.zLevel, this.u1, this.v0);
         this.supplier.placeVertex(bufferBuilder, stack, this, this.x0, this.y0, this.zLevel, this.u0, this.v0);
         BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
         return this;
      }
   }

   public interface VertexConsumerActor {
      void placeVertex(
         VertexConsumer var1, Pose var2, VFXBuilders.AbstractVFXBuilder var3, Vector3f var4, float var5, float var6, float var7, float var8, float var9
      );

      default void placeVertex(VertexConsumer consumer, PoseStack stack, VFXBuilders.AbstractVFXBuilder builder, float x, float y, float z, float u, float v) {
         Vector3f normal = VFXBuilders.normal(stack);
         this.placeVertex(consumer, stack.last(), builder, normal, x, y, z, u, v);
      }

      default void placeVertex(VertexConsumer consumer, Pose pose, VFXBuilders.AbstractVFXBuilder builder, float x, float y, float z, float u, float v) {
         this.placeVertex(consumer, pose, builder, null, x, y, z, u, v);
      }

      default void placeVertex(VertexConsumer consumer, VFXBuilders.AbstractVFXBuilder builder, Vector3f normal, float x, float y, float z, float u, float v) {
         this.placeVertex(consumer, null, builder, normal, x, y, z, u, v);
      }

      default void placeVertex(VertexConsumer consumer, VFXBuilders.AbstractVFXBuilder builder, float x, float y, float z, float u, float v) {
         this.placeVertex(consumer, builder, null, x, y, z, u, v);
      }
   }

   public static class WorldVFXBuilder extends VFXBuilders.AbstractVFXBuilder {
      private final Minecraft minecraft = Minecraft.getInstance();
      @Nonnull
      protected LodestoneRenderLayer renderLayer = LodestoneRenderHandler.DEFERRED_RENDER;
      protected MultiBufferSource bufferSource;
      protected RenderType renderType;
      protected VertexConsumer vertexConsumer;
      protected boolean usePartialTicks;
      protected float partialTicks;

      public VFXBuilders.WorldVFXBuilder setColor(int rgba) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(rgba);
      }

      public VFXBuilders.WorldVFXBuilder setColor(Color color) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(color);
      }

      public VFXBuilders.WorldVFXBuilder setColor(Color color, int a) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(color, a);
      }

      public VFXBuilders.WorldVFXBuilder setColor(Color color, float a) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(color, a);
      }

      public VFXBuilders.WorldVFXBuilder setColor(int r, int g, int b, int a) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(r, g, b, a);
      }

      public VFXBuilders.WorldVFXBuilder setColor(float r, float g, float b, float a) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(r, g, b, a);
      }

      public VFXBuilders.WorldVFXBuilder setColor(int r, int g, int b) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(r, g, b);
      }

      public VFXBuilders.WorldVFXBuilder setColor(float r, float g, float b) {
         return (VFXBuilders.WorldVFXBuilder)super.setColor(r, g, b);
      }

      public VFXBuilders.WorldVFXBuilder multiplyColor(float scalar) {
         return (VFXBuilders.WorldVFXBuilder)super.multiplyColor(scalar);
      }

      public VFXBuilders.WorldVFXBuilder multiplyColor(float r, float g, float b) {
         return (VFXBuilders.WorldVFXBuilder)super.multiplyColor(r, g, b);
      }

      public VFXBuilders.WorldVFXBuilder setAlpha(int a) {
         return (VFXBuilders.WorldVFXBuilder)super.setAlpha(a);
      }

      public VFXBuilders.WorldVFXBuilder setAlpha(float a) {
         return (VFXBuilders.WorldVFXBuilder)super.setAlpha(a);
      }

      public VFXBuilders.WorldVFXBuilder setLight(int light) {
         return (VFXBuilders.WorldVFXBuilder)super.setLight(light);
      }

      public VFXBuilders.WorldVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSize) {
         return (VFXBuilders.WorldVFXBuilder)super.setUVWithWidth(u, v, width, height, canvasSize);
      }

      public VFXBuilders.WorldVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSizeX, float canvasSizeY) {
         return (VFXBuilders.WorldVFXBuilder)super.setUVWithWidth(u, v, width, height, canvasSizeX, canvasSizeY);
      }

      public VFXBuilders.WorldVFXBuilder setUVWithWidth(float u, float v, float width, float height) {
         return (VFXBuilders.WorldVFXBuilder)super.setUVWithWidth(u, v, width, height);
      }

      public VFXBuilders.WorldVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSize) {
         return (VFXBuilders.WorldVFXBuilder)super.setUV(u0, v0, u1, v1, canvasSize);
      }

      public VFXBuilders.WorldVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSizeX, float canvasSizeY) {
         return (VFXBuilders.WorldVFXBuilder)super.setUV(u0, v0, u1, v1, canvasSizeX, canvasSizeY);
      }

      public VFXBuilders.WorldVFXBuilder setUV(float u0, float v0, float u1, float v1) {
         return (VFXBuilders.WorldVFXBuilder)super.setUV(u0, v0, u1, v1);
      }

      public VFXBuilders.WorldVFXBuilder setFormatRaw(VertexFormat format) {
         return (VFXBuilders.WorldVFXBuilder)super.setFormatRaw(format);
      }

      public VFXBuilders.WorldVFXBuilder setVertexSupplier(VFXBuilders.VertexConsumerActor supplier) {
         return (VFXBuilders.WorldVFXBuilder)super.setVertexSupplier(supplier);
      }

      public VFXBuilders.WorldVFXBuilder setFormat(VertexFormat format) {
         return (VFXBuilders.WorldVFXBuilder)super.setFormat(format);
      }

      public VFXBuilders.WorldVFXBuilder setRenderType(LodestoneRenderTypeBuilder renderType) {
         return this.setRenderType(renderType.getRenderType());
      }

      public VFXBuilders.WorldVFXBuilder setRenderType(RenderType renderType) {
         return this.setRenderTypeRaw(renderType).setFormat(renderType.format()).setVertexConsumer(this.getBufferSource().getBuffer(renderType));
      }

      public VFXBuilders.WorldVFXBuilder setRenderTypeRaw(RenderType renderType) {
         this.renderType = renderType;
         return this;
      }

      public VFXBuilders.WorldVFXBuilder setVertexConsumer(VertexConsumer vertexConsumer) {
         this.vertexConsumer = vertexConsumer;
         return this;
      }

      public VertexConsumer getVertexConsumer() {
         if (this.vertexConsumer == null) {
            this.setVertexConsumer(this.getBufferSource().getBuffer(this.getRenderType()));
         }

         return this.vertexConsumer;
      }

      public VFXBuilders.WorldVFXBuilder replaceBufferSource(LodestoneRenderLayer renderLayer) {
         this.renderLayer = renderLayer;
         this.vertexConsumer = null;
         return this;
      }

      public VFXBuilders.WorldVFXBuilder replaceBufferSource(MultiBufferSource bufferSource) {
         this.bufferSource = bufferSource;
         this.vertexConsumer = null;
         return this;
      }

      protected MultiBufferSource getBufferSource() {
         if (this.bufferSource == null) {
            this.replaceBufferSource(this.renderLayer.getTarget());
         }

         return this.bufferSource;
      }

      public VFXBuilders.WorldVFXBuilder usePartialTicks(float partialTicks) {
         this.usePartialTicks = true;
         this.partialTicks = partialTicks;
         return this;
      }

      public VFXBuilders.WorldVFXBuilder setLightLevel(BlockPos pos) {
         ClientLevel level = this.minecraft.level;
         int light = level.hasChunkAt(pos) ? LevelRenderer.getLightColor(level, pos) : 0;
         return this.setLight(light);
      }

      protected RenderType getRenderType() {
         return this.renderType;
      }

      protected VertexFormat getFormat() {
         return this.format;
      }

      public VFXBuilders.VertexConsumerActor getSupplier() {
         return this.supplier;
      }

      protected Vec3 getCameraPosition() {
         return this.minecraft.getBlockEntityRenderDispatcher().camera.getPosition();
      }

      protected Matrix4f getOffsetViewMatrix() {
         Matrix4f pose = new Matrix4f(LodestoneRenderHandler.MODEL_VIEW);
         Vector3f cameraPosition = this.getCameraPosition().toVector3f();
         return pose.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
      }

      public VFXBuilders.WorldVFXBuilder renderBeam(@Nullable Pose last, BlockPos start, BlockPos end, float width) {
         return this.renderBeam(last, start.getCenter(), end.getCenter(), width, this.getCameraPosition());
      }

      public VFXBuilders.WorldVFXBuilder renderBeam(@Nullable Pose last, Vec3 start, Vec3 end, float width) {
         return this.renderBeam(last, start, end, width, this.getCameraPosition());
      }

      public VFXBuilders.WorldVFXBuilder renderBeam(@Nullable Pose last, Vec3 start, Vec3 end, float width, Consumer<VFXBuilders.WorldVFXBuilder> consumer) {
         return this.renderBeam(last, start, end, width, this.getCameraPosition(), consumer);
      }

      public VFXBuilders.WorldVFXBuilder renderBeam(@Nullable Pose last, Vec3 start, Vec3 end, float width, Vec3 cameraPosition) {
         return this.renderBeam(last, start, end, width, cameraPosition, builder -> {});
      }

      public VFXBuilders.WorldVFXBuilder renderBeam(
         @Nullable Pose last, Vec3 start, Vec3 end, float width, Vec3 cameraPosition, Consumer<VFXBuilders.WorldVFXBuilder> consumer
      ) {
         Vec3 delta = end.subtract(start);
         Vec3 normal = start.subtract(cameraPosition).cross(delta).normalize().multiply(width / 2.0F, width / 2.0F, width / 2.0F);
         Vec3[] positions = new Vec3[]{start.subtract(normal), start.add(normal), end.add(normal), end.subtract(normal)};
         this.supplier.placeVertex(this.getVertexConsumer(), last, this, (float)positions[0].x, (float)positions[0].y, (float)positions[0].z, this.u0, this.v1);
         this.supplier.placeVertex(this.getVertexConsumer(), last, this, (float)positions[1].x, (float)positions[1].y, (float)positions[1].z, this.u1, this.v1);
         consumer.accept(this);
         this.supplier.placeVertex(this.getVertexConsumer(), last, this, (float)positions[2].x, (float)positions[2].y, (float)positions[2].z, this.u1, this.v0);
         this.supplier.placeVertex(this.getVertexConsumer(), last, this, (float)positions[3].x, (float)positions[3].y, (float)positions[3].z, this.u0, this.v0);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderTrail(TrailPointBuilder trailPoints, float width) {
         return this.renderTrail(trailPoints, f -> width, f -> {});
      }

      public VFXBuilders.WorldVFXBuilder renderTrail(TrailPointBuilder trailPoints, Function<Float, Float> widthFunc) {
         return this.renderTrail(trailPoints, widthFunc, f -> {});
      }

      public VFXBuilders.WorldVFXBuilder renderTrail(TrailPointBuilder builder, Function<Float, Float> widthFunc, Consumer<Float> vfxOperator) {
         List<TrailPoint> trailPoints = builder.getTrailPoints();
         if (trailPoints.size() < 2) {
            return this;
         }

         Matrix4f pose = this.getOffsetViewMatrix();
         List<Vector4f> positions = this.usePartialTicks ? builder.build(pose, this.partialTicks) : builder.build(pose);
         positions.getLast().set(TrailPoint.getMatrixPosition(builder.getOrigin(), pose));
         int count = trailPoints.size() - 1;
         float increment = 1.0F / count;
         TrailPointRenderData[] renderData = new TrailPointRenderData[trailPoints.size()];

         for (int i = 1; i < count; i++) {
            float width = widthFunc.apply(increment * i);
            Vector4f previous = positions.get(i - 1);
            Vector4f current = positions.get(i);
            Vector4f next = positions.get(i + 1);
            renderData[i] = new TrailPointRenderData(current, RenderHelper.perpendicularTrailPoints(previous, next, width));
         }

         Vector4f first = positions.get(0);
         Vector4f second = positions.get(1);
         Vector4f secondToLast = positions.get(count - 1);
         Vector4f last = positions.get(count);
         renderData[0] = new TrailPointRenderData(first, RenderHelper.perpendicularTrailPoints(first, second, widthFunc.apply(0.0F)));
         renderData[count] = new TrailPointRenderData(last, RenderHelper.perpendicularTrailPoints(secondToLast, last, widthFunc.apply(1.0F)));
         return this.renderConnectedPoints(renderData, this.u0, this.v0, this.u1, this.v1, vfxOperator);
      }

      public VFXBuilders.WorldVFXBuilder renderConnectedPoints(
         TrailPointRenderData[] points, float u0, float v0, float u1, float v1, Consumer<Float> vfxOperator
      ) {
         this.replaceBufferSource(this.renderLayer.getTrailTarget());
         int count = points.length - 1;
         float increment = 1.0F / count;
         vfxOperator.accept(0.0F);
         points[0].renderStart(this.getVertexConsumer(), this, u0, v0, u1);

         for (int i = 1; i < count; i++) {
            float current = Mth.lerp(i * increment, v0, v1);
            vfxOperator.accept(current);
            points[i].renderMid(this.getVertexConsumer(), this, u0, current, u1, current);
         }

         vfxOperator.accept(1.0F);
         points[count].renderEnd(this.getVertexConsumer(), this, u0, u1, v1);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderCube(PoseStack poseStack, CubeVertexData cubeVertexData) {
         Vector3f[] topVertices = cubeVertexData.topVertices();
         Vector3f[] bottomVertices = cubeVertexData.bottomVertices();

         for (Vector3f[] offsets : cubeVertexData.offsetMap()) {
            this.renderQuad(poseStack, offsets);
         }

         this.renderQuad(poseStack, new Vector3f[]{bottomVertices[3], bottomVertices[2], bottomVertices[1], bottomVertices[0]});
         this.renderQuad(poseStack, topVertices);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderCubeSides(PoseStack poseStack, CubeVertexData cubeVertexData, Direction... directions) {
         return this.renderCubeSides(poseStack, cubeVertexData, Arrays.asList(directions));
      }

      public VFXBuilders.WorldVFXBuilder renderCubeSides(PoseStack poseStack, CubeVertexData cubeVertexData, Collection<Direction> directions) {
         for (Direction direction : directions) {
            this.renderCubeSide(poseStack, cubeVertexData, direction);
         }

         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderCubeSide(PoseStack poseStack, CubeVertexData cubeVertexData, Direction direction) {
         Vector3f[] vertices = cubeVertexData.getVerticesByDirection(direction);
         this.renderQuad(poseStack, vertices);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack) {
         return this.renderQuad(stack, 1.0F);
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack, float size) {
         return this.renderQuad(stack, size, size);
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack, float width, float height) {
         Vector3f[] positions = new Vector3f[]{
            new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F)
         };
         return this.renderQuad(stack, positions, width, height);
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack, Vector3f[] positions, float size) {
         return this.renderQuad(stack, positions, size, size);
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack, Vector3f[] positions, float width, float height) {
         for (Vector3f position : positions) {
            position.mul(width, height, width);
         }

         return this.renderQuad(stack, positions);
      }

      public VFXBuilders.WorldVFXBuilder renderQuad(PoseStack stack, Vector3f[] positions) {
         VertexConsumer consumer = this.getVertexConsumer();
         this.supplier.placeVertex(consumer, stack, this, positions[0].x(), positions[0].y(), positions[0].z(), this.u0, this.v1);
         this.supplier.placeVertex(consumer, stack, this, positions[1].x(), positions[1].y(), positions[1].z(), this.u1, this.v1);
         this.supplier.placeVertex(consumer, stack, this, positions[2].x(), positions[2].y(), positions[2].z(), this.u1, this.v0);
         this.supplier.placeVertex(consumer, stack, this, positions[3].x(), positions[3].y(), positions[3].z(), this.u0, this.v0);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder placeVertex(PoseStack stack, float x, float y, float z) {
         this.supplier.placeVertex(this.getVertexConsumer(), stack.last(), this, x, y, z, this.u0, this.v1);
         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderSphere(PoseStack stack, float radius, int longs, int lats) {
         float startU = this.u0;
         float startV = this.v0;
         float endU = (float) (Math.PI * 2) * this.u1;
         float endV = (float) Math.PI * this.v1;
         float stepU = (endU - startU) / longs;
         float stepV = (endV - startV) / lats;

         for (int i = 0; i < longs; i++) {
            for (int j = 0; j < lats; j++) {
               float u = i * stepU + startU;
               float v = j * stepV + startV;
               float un = i + 1 == longs ? endU : (i + 1) * stepU + startU;
               float vn = j + 1 == lats ? endV : (j + 1) * stepV + startV;
               Vector3f p0 = RenderHelper.parametricSphere(u, v, radius);
               Vector3f p1 = RenderHelper.parametricSphere(u, vn, radius);
               Vector3f p2 = RenderHelper.parametricSphere(un, v, radius);
               Vector3f p3 = RenderHelper.parametricSphere(un, vn, radius);
               float textureU = u / endU * radius;
               float textureV = v / endV * radius;
               float textureUN = un / endU * radius;
               float textureVN = vn / endV * radius;
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p0.x(), p0.y(), p0.z(), textureU, textureV);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p2.x(), p2.y(), p2.z(), textureUN, textureV);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p1.x(), p1.y(), p1.z(), textureU, textureVN);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p3.x(), p3.y(), p3.z(), textureUN, textureVN);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p1.x(), p1.y(), p1.z(), textureU, textureVN);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p2.x(), p2.y(), p2.z(), textureUN, textureV);
            }
         }

         return this;
      }

      public VFXBuilders.WorldVFXBuilder renderTorus(PoseStack stack, float majorRadius, float minorRadius, int majorSegments, int minorSegments) {
         float TAU = (float) (Math.PI * 2);

         for (int i = 0; i < majorSegments; i++) {
            float u0n = (float)i / majorSegments;
            float u1n = (float)(i + 1) / majorSegments;
            float u0 = u0n * TAU;
            float u1 = u1n * TAU;

            for (int j = 0; j < minorSegments; j++) {
               float v0n = (float)j / minorSegments;
               float v1n = (float)(j + 1) / minorSegments;
               float v0 = v0n * TAU;
               float v1 = v1n * TAU;
               Vector3f p0 = RenderHelper.parametricTorus(u0, v0, majorRadius, minorRadius);
               Vector3f p1 = RenderHelper.parametricTorus(u0, v1, majorRadius, minorRadius);
               Vector3f p2 = RenderHelper.parametricTorus(u1, v0, majorRadius, minorRadius);
               Vector3f p3 = RenderHelper.parametricTorus(u1, v1, majorRadius, minorRadius);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p0.x(), p0.y(), p0.z(), u0n, v0n);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p2.x(), p2.y(), p2.z(), u1n, v0n);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p1.x(), p1.y(), p1.z(), u0n, v1n);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p3.x(), p3.y(), p3.z(), u1n, v1n);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p1.x(), p1.y(), p1.z(), u0n, v1n);
               this.supplier.placeVertex(this.getVertexConsumer(), stack, this, p2.x(), p2.y(), p2.z(), u1n, v0n);
            }
         }

         return this;
      }
   }
}
