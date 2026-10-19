package earth.terrarium.handcrafted.client.renderer.fancypainting;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.handcrafted.common.entities.FancyPainting;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import org.jetbrains.annotations.NotNull;

public class FancyPaintingRenderer extends EntityRenderer<FancyPainting> {
   private static final ResourceLocation FRAME_SMALL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "handcrafted", "textures/painting/small_painting_frame.png"
   );
   private static final ResourceLocation FRAME_MEDIUM_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "handcrafted", "textures/painting/medium_painting_frame.png"
   );
   private static final ResourceLocation FRAME_LARGE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "handcrafted", "textures/painting/large_painting_frame.png"
   );
   private static final ResourceLocation FRAME_TALL_TEXTURE = ResourceLocation.fromNamespaceAndPath("handcrafted", "textures/painting/tall_painting_frame.png");
   private static final ResourceLocation FRAME_WIDE_TEXTURE = ResourceLocation.fromNamespaceAndPath("handcrafted", "textures/painting/wide_painting_frame.png");
   private final ModelPart small;
   private final ModelPart medium;
   private final ModelPart large;
   private final ModelPart tall;
   private final ModelPart wide;
   private final Map<PaintingVariant, ResourceLocation> textures = new HashMap<>();

   public FancyPaintingRenderer(Context context) {
      super(context);
      this.small = context.bakeLayer(FancyPaintingModel.LAYER_LOCATION_SMALL).getChild("main");
      this.medium = context.bakeLayer(FancyPaintingModel.LAYER_LOCATION_MEDIUM).getChild("main");
      this.large = context.bakeLayer(FancyPaintingModel.LAYER_LOCATION_LARGE).getChild("main");
      this.tall = context.bakeLayer(FancyPaintingModel.LAYER_LOCATION_TALL).getChild("main");
      this.wide = context.bakeLayer(FancyPaintingModel.LAYER_LOCATION_WIDE).getChild("main");
   }

   public void render(FancyPainting entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      PaintingVariant variant = (PaintingVariant)entity.getVariant().value();
      Direction direction = entity.getDirection();
      VertexConsumer frameVertex = buffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(entity)));
      int width = variant.width() * 16;
      int height = variant.height() * 16;
      CloseablePoseStack ignored = new CloseablePoseStack(poseStack);

      try {
         poseStack.scale(0.8F, 0.8F, 0.8F);
         poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
         poseStack.translate(0.0F, 0.875F, 0.46125F);
         poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
         this.getFrame(variant).render(poseStack, frameVertex, packedLight, OverlayTexture.NO_OVERLAY);
         ResourceLocation texture = this.textures
            .computeIfAbsent(variant, v -> ResourceLocation.fromNamespaceAndPath("handcrafted", "textures/painting/" + v.assetId().getPath() + ".png"));
         VertexConsumer paintingVertex = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
         poseStack.translate(width / 2.0F / -16.0F, -0.125 + 0.5 * ((32 - height) / 16.0F), 0.46125);
         poseStack.scale(width / 16.0F, height / 16.0F, 1.0F);
         renderPainting(poseStack.last(), paintingVertex, direction, packedLight);
      } catch (Throwable var16) {
         try {
            ignored.close();
         } catch (Throwable var15) {
            var16.addSuppressed(var15);
         }

         throw var16;
      }

      ignored.close();
   }

   private ModelPart getFrame(PaintingVariant variant) {
      int width = variant.width();
      int height = variant.height();
      if (width == 1 && height == 1) {
         return this.small;
      } else if (width == 2 && height == 2) {
         return this.medium;
      } else if (width == 3 && height == 2) {
         return this.large;
      } else if (width == 1 && height == 2) {
         return this.tall;
      } else if (width == 2 && height == 1) {
         return this.wide;
      } else {
         throw new IllegalStateException("Unknown painting variant: " + variant);
      }
   }

   private static void renderPainting(Pose pose, VertexConsumer consumer, Direction dir, int light) {
      Vec3i normal = dir.getNormal();
      consumer.addVertex(pose.pose(), 0.0F, 0.0F, 0.0F)
         .setColor(-1)
         .setUv(0.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(pose, normal.getX(), normal.getY(), normal.getZ());
      consumer.addVertex(pose.pose(), 0.0F, 1.0F, 0.0F)
         .setColor(-1)
         .setUv(0.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(pose, normal.getX(), normal.getY(), normal.getZ());
      consumer.addVertex(pose.pose(), 1.0F, 1.0F, 0.0F)
         .setColor(-1)
         .setUv(1.0F, 1.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(pose, normal.getX(), normal.getY(), normal.getZ());
      consumer.addVertex(pose.pose(), 1.0F, 0.0F, 0.0F)
         .setColor(-1)
         .setUv(1.0F, 0.0F)
         .setOverlay(OverlayTexture.NO_OVERLAY)
         .setLight(light)
         .setNormal(pose, normal.getX(), normal.getY(), normal.getZ());
   }

   @NotNull
   public ResourceLocation getTextureLocation(FancyPainting entity) {
      int width = ((PaintingVariant)entity.getVariant().value()).width();
      int height = ((PaintingVariant)entity.getVariant().value()).height();
      if (width == 1 && height == 1) {
         return FRAME_SMALL_TEXTURE;
      } else if (width == 2 && height == 2) {
         return FRAME_MEDIUM_TEXTURE;
      } else if (width == 3 && height == 2) {
         return FRAME_LARGE_TEXTURE;
      } else if (width == 1 && height == 2) {
         return FRAME_TALL_TEXTURE;
      } else if (width == 2 && height == 1) {
         return FRAME_WIDE_TEXTURE;
      } else {
         throw new IllegalStateException("Unknown painting variant: " + entity.getVariant().value());
      }
   }
}
