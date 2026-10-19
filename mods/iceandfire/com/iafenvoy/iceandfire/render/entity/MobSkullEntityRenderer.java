package com.iafenvoy.iceandfire.render.entity;

import com.google.common.collect.Maps;
import com.iafenvoy.iceandfire.data.IafSkullType;
import com.iafenvoy.iceandfire.entity.MobSkullEntity;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.registry.IafRenderers;
import com.iafenvoy.iceandfire.render.model.AmphithereModel;
import com.iafenvoy.iceandfire.render.model.CockatriceModel;
import com.iafenvoy.iceandfire.render.model.CyclopsModel;
import com.iafenvoy.iceandfire.render.model.HippogryphModel;
import com.iafenvoy.iceandfire.render.model.HydraHeadModel;
import com.iafenvoy.iceandfire.render.model.StymphalianBirdModel;
import com.iafenvoy.iceandfire.render.model.TrollModel;
import com.iafenvoy.iceandfire.render.model.animator.SeaSerpentTabulaModelAnimator;
import com.iafenvoy.uranus.client.model.TabulaModel;
import com.iafenvoy.uranus.client.model.basic.BasicModelPart;
import com.iafenvoy.uranus.client.model.util.TabulaModelHandlerHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MobSkullEntityRenderer extends EntityRenderer<MobSkullEntity> {
   private static final Map<String, ResourceLocation> SKULL_TEXTURE_CACHE = Maps.newHashMap();
   private final HippogryphModel hippogryphModel = new HippogryphModel();
   private final CyclopsModel cyclopsModel = new CyclopsModel();
   private final CockatriceModel cockatriceModel = new CockatriceModel();
   private final StymphalianBirdModel stymphalianBirdModel = new StymphalianBirdModel();
   private final TrollModel trollModel = new TrollModel();
   private final AmphithereModel amphithereModel = new AmphithereModel();
   private final HydraHeadModel hydraModel;
   private final TabulaModel<SeaSerpentEntity> seaSerpentModel = TabulaModelHandlerHelper.getModel(IafRenderers.SEA_SERPENT, SeaSerpentTabulaModelAnimator::new);

   public MobSkullEntityRenderer(Context context) {
      super(context);
      this.hydraModel = new HydraHeadModel(0);
   }

   private static void setRotationAngles(BasicModelPart cube, float rotX) {
      cube.rotateAngleX = rotX;
      cube.rotateAngleY = 0.0F;
      cube.rotateAngleZ = 0.0F;
   }

   public void render(MobSkullEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(Axis.XP.rotationDegrees(-180.0F));
      matrixStackIn.mulPose(Axis.YN.rotationDegrees(180.0F - entity.getYRot()));
      float f = 0.0625F;
      float size = 1.0F;
      matrixStackIn.scale(size, size, size);
      matrixStackIn.translate(0.0F, entity.isOnWall() ? -0.24F : -0.12F, 0.5F);
      this.renderForEnum(entity.getSkullType(), entity.isOnWall(), matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.popPose();
   }

   private void renderForEnum(IafSkullType skull, boolean onWall, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(this.getSkullTexture(skull)));
      switch (skull) {
         case HIPPOGRYPH:
            matrixStackIn.translate(0.0F, -0.0F, -0.2F);
            matrixStackIn.scale(1.2F, 1.2F, 1.2F);
            this.hippogryphModel.resetToDefaultPose();
            setRotationAngles(this.hippogryphModel.Head, onWall ? (float)Math.toRadians(50.0) : (float)Math.toRadians(-5.0));
            this.hippogryphModel.Head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case CYCLOPS:
            matrixStackIn.translate(0.0F, 1.8F, -0.5F);
            matrixStackIn.scale(2.25F, 2.25F, 2.25F);
            this.cyclopsModel.resetToDefaultPose();
            setRotationAngles(this.cyclopsModel.Head, onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.cyclopsModel.Head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case COCKATRICE:
            if (onWall) {
               matrixStackIn.translate(0.0F, 0.0F, 0.35F);
            }

            this.cockatriceModel.resetToDefaultPose();
            setRotationAngles(this.cockatriceModel.head, onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.cockatriceModel.head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case STYMPHALIAN:
            if (!onWall) {
               matrixStackIn.translate(0.0F, 0.0F, -0.35F);
            }

            this.stymphalianBirdModel.resetToDefaultPose();
            setRotationAngles(this.stymphalianBirdModel.HeadBase, onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.stymphalianBirdModel.HeadBase.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case TROLL:
            matrixStackIn.translate(0.0F, 1.0F, -0.35F);
            if (onWall) {
               matrixStackIn.translate(0.0F, 0.0F, 0.35F);
            }

            this.trollModel.resetToDefaultPose();
            setRotationAngles(this.trollModel.head, onWall ? (float)Math.toRadians(50.0) : (float)Math.toRadians(-20.0));
            this.trollModel.head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case AMPHITHERE:
            matrixStackIn.translate(0.0F, -0.2F, 0.7F);
            matrixStackIn.scale(2.0F, 2.0F, 2.0F);
            this.amphithereModel.resetToDefaultPose();
            setRotationAngles(this.amphithereModel.Head, onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.amphithereModel.Head.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case SEASERPENT:
            matrixStackIn.translate(0.0F, -0.35F, 0.8F);
            matrixStackIn.scale(2.5F, 2.5F, 2.5F);
            this.seaSerpentModel.resetToDefaultPose();
            setRotationAngles(this.seaSerpentModel.getCube("Head"), onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.seaSerpentModel.getCube("Head").render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
            break;
         case HYDRA:
            matrixStackIn.translate(0.0F, -0.2F, -0.1F);
            matrixStackIn.scale(2.0F, 2.0F, 2.0F);
            this.hydraModel.resetToDefaultPose();
            setRotationAngles(this.hydraModel.Head1, onWall ? (float)Math.toRadians(50.0) : 0.0F);
            this.hydraModel.Head1.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
      }
   }

   public ResourceLocation getTexture(MobSkullEntity entity) {
      return this.getSkullTexture(entity.getSkullType());
   }

   public ResourceLocation getSkullTexture(IafSkullType skull) {
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
         "iceandfire", "textures/entity/skulls/skull_" + skull.name().toLowerCase(Locale.ROOT) + ".png"
      );
      return SKULL_TEXTURE_CACHE.computeIfAbsent(id.toString(), k -> id);
   }
}
