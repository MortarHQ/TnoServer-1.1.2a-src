package io.github.manasmods.tensura_neb.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.manasmods.tensura.client.TensuraColors;
import io.github.manasmods.tensura.client.entity.human.PlayerLikeModel;
import io.github.manasmods.tensura.client.entity.human.PlayerLikeRenderer;
import io.github.manasmods.tensura_neb.entity.LuminousValentineEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class LuminousRenderer<T extends LuminousValentineEntity> extends PlayerLikeRenderer<T> {
   public static final LayerDefinition CHEST_LAYER = LuminousRenderer.LuminousChestModel.createBodyLayer();

   public LuminousRenderer(Context pContext) {
      super(pContext, new PlayerLikeModel(pContext.bakeLayer(ModelLayers.PLAYER_SLIM), true), 0.5F);
      this.addLayer(
         new HumanoidArmorLayer(
            this,
            new HumanoidModel(pContext.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
            new HumanoidModel(pContext.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),
            pContext.getModelManager()
         )
      );
      this.addLayer(new LuminousRenderer.Chest(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(T entity) {
      return entity.getTextureLocation();
   }

   private static RenderType getRenderType(ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }

   public static class Chest<T extends LuminousValentineEntity> extends RenderLayer<T, PlayerModel<T>> {
      public static ModelLayerLocation CHEST = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("tensura", "luminous_chest"), "main");
      private final LuminousRenderer.LuminousChestModel<T> model = new LuminousRenderer.LuminousChestModel(
         Minecraft.getInstance().getEntityModels().bakeLayer(CHEST)
      );

      public Chest(RenderLayerParent<T, PlayerModel<T>> pRenderer) {
         super(pRenderer);
      }

      public void render(
         PoseStack pMatrixStack,
         MultiBufferSource pBuffer,
         int pPackedLight,
         T entity,
         float pLimbSwing,
         float pLimbSwingAmount,
         float partialTicks,
         float pAgeInTicks,
         float pNetHeadYaw,
         float pHeadPitch
      ) {
         int color = -1;
         if (entity.isInvisible()) {
            Player player = Minecraft.getInstance().player;
            if (player == null || entity.isInvisibleTo(player)) {
               return;
            }

            color = TensuraColors.getARGBWithAlpha(color, 0.1F);
         }

         ModelPart parentBody = ((PlayerModel)this.getParentModel()).body;
         this.model.body.xRot = parentBody.xRot;
         this.model.body.yRot = parentBody.yRot;
         this.model.body.zRot = parentBody.zRot;
         VertexConsumer vertexconsumer = pBuffer.getBuffer(LuminousRenderer.getRenderType(entity.getTextureLocation()));
         pMatrixStack.pushPose();
         this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, color);
         pMatrixStack.popPose();
      }
   }

   public static class LuminousChestModel<T extends LivingEntity> extends EntityModel<T> {
      private final ModelPart waist;
      public final ModelPart body;
      private final ModelPart chest;

      public LuminousChestModel(ModelPart root) {
         this.waist = root.getChild("Waist");
         this.body = this.waist.getChild("Body");
         this.chest = this.body.getChild("Chest");
      }

      public static LayerDefinition createBodyLayer() {
         return createBodyLayer(0.0F);
      }

      public static LayerDefinition createBodyLayer(float extraInflation) {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition waist = partdefinition.addOrReplaceChild("Waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
         PartDefinition body = waist.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
         PartDefinition chest = body.addOrReplaceChild("Chest", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -1.0F));
         chest.addOrReplaceChild(
            "Layer_r1",
            CubeListBuilder.create().texOffs(17, 24).addBox(-4.0F, 0.9F, -0.7F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.009F + extraInflation)),
            PartPose.offsetAndRotation(0.0F, 2.3952F, -1.6749F, 2.5744F, 0.0F, 0.0F)
         );
         chest.addOrReplaceChild(
            "Layer_r2",
            CubeListBuilder.create()
               .texOffs(17, 33)
               .addBox(-4.0F, -1.0F, -1.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.2F + extraInflation))
               .texOffs(17, 17)
               .addBox(-4.0F, -1.0F, -1.0F, 8.0F, 4.0F, 3.0F, new CubeDeformation(0.01F + extraInflation)),
            PartPose.offsetAndRotation(0.0F, -1.6048F, -0.6749F, -0.5672F, 0.0F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 64, 64);
      }

      public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      }

      public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
         this.waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      }
   }
}
