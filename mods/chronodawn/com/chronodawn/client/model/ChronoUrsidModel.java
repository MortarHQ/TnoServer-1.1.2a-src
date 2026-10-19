package com.chronodawn.client.model;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.ChronoUrsidEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ChronoUrsidModel extends EntityModel<ChronoUrsidEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "chrono_ursid"), "main");
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart leg1;
   private final ModelPart leg2;
   private final ModelPart leg3;
   private final ModelPart leg4;
   private float standAnimationScale;

   public ChronoUrsidModel(ModelPart root) {
      this.head = root.getChild("head");
      this.body = root.getChild("body");
      this.leg1 = root.getChild("leg1");
      this.leg2 = root.getChild("leg2");
      this.leg3 = root.getChild("leg3");
      this.leg4 = root.getChild("leg4");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(90, 45)
            .addBox(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(90, 75)
            .addBox(-2.5F, 1.0F, -5.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(68, 15)
            .addBox(2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(68, 18)
            .addBox(-4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 10.0F, -16.0F)
      );
      PartDefinition body = partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(0, 25)
            .addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 9.0F, 12.0F, 1.5708F, 0.0F, 0.0F)
      );
      PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(2.0F, -7.0F, 4.5F));
      PartDefinition clock = back.addOrReplaceChild(
         "clock",
         CubeListBuilder.create()
            .texOffs(48, 25)
            .addBox(-1.0F, -6.5F, -6.5F, 2.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
            .texOffs(0, 49)
            .addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offset(3.0F, 0.0F, 0.0F)
      );
      clock.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F)
      );
      clock.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(60, 51).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      clock.addOrReplaceChild(
         "cube_r3",
         CubeListBuilder.create().texOffs(30, 51).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0472F, 0.0F, 0.0F)
      );
      clock.addOrReplaceChild(
         "cube_r4",
         CubeListBuilder.create().texOffs(0, 64).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, 0.0F, 0.0F)
      );
      clock.addOrReplaceChild(
         "cube_r5",
         CubeListBuilder.create().texOffs(30, 66).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.618F, 0.0F, 0.0F)
      );
      PartDefinition clockwork = back.addOrReplaceChild(
         "clockwork",
         CubeListBuilder.create()
            .texOffs(60, 66)
            .addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F))
            .texOffs(22, 96)
            .addBox(-1.0F, -4.0F, -3.5F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F)
      );
      clockwork.addOrReplaceChild(
         "cube_r6",
         CubeListBuilder.create().texOffs(30, 81).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F)
      );
      clockwork.addOrReplaceChild(
         "cube_r7",
         CubeListBuilder.create().texOffs(80, 0).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      clockwork.addOrReplaceChild(
         "cube_r8",
         CubeListBuilder.create().texOffs(0, 79).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0472F, 0.0F, 0.0F)
      );
      clockwork.addOrReplaceChild(
         "cube_r9",
         CubeListBuilder.create().texOffs(78, 30).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0944F, 0.0F, 0.0F)
      );
      clockwork.addOrReplaceChild(
         "cube_r10",
         CubeListBuilder.create().texOffs(78, 15).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 13.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.618F, 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "leg1",
         CubeListBuilder.create()
            .texOffs(40, 96)
            .addBox(-1.0F, 5.0F, -1.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(86, 81)
            .addBox(-1.0F, -5.0F, -2.0F, 5.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.5F, 14.0F, 6.0F)
      );
      partdefinition.addOrReplaceChild(
         "leg2",
         CubeListBuilder.create()
            .texOffs(60, 81)
            .addBox(-4.0F, -5.0F, -2.0F, 5.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(60, 99)
            .addBox(-3.0F, 5.0F, -1.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.5F, 14.0F, 6.0F)
      );
      partdefinition.addOrReplaceChild(
         "leg3",
         CubeListBuilder.create()
            .texOffs(50, 15)
            .addBox(-1.0F, 5.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(90, 59)
            .addBox(-1.0F, -5.0F, -2.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(3.5F, 14.0F, -8.0F)
      );
      partdefinition.addOrReplaceChild(
         "leg4",
         CubeListBuilder.create()
            .texOffs(80, 99)
            .addBox(-3.0F, 5.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(0, 94)
            .addBox(-4.0F, -5.0F, -2.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-3.5F, 14.0F, -8.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void prepareMobModel(ChronoUrsidEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
      super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
      this.standAnimationScale = entity.getStandingAnimationScale(partialTick);
   }

   public void setupAnim(ChronoUrsidEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float stand = this.standAnimationScale;
      stand *= stand;
      float ground = 1.0F - stand;
      this.body.xRot = 1.5708F - stand * (float) (Math.PI * 2.0 / 5.0);
      this.body.y = 9.0F - stand * 4.0F;
      this.head.xRot = headPitch * (float) (Math.PI / 180.0) + stand * (float) (-Math.PI / 4);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.y = 10.0F - stand * 27.0F;
      this.head.z = -16.0F + stand * 18.0F;
      this.leg3.y = 14.0F - stand * 22.0F;
      this.leg4.y = 14.0F - stand * 22.0F;
      this.leg3.z = -8.0F + stand * 9.0F;
      this.leg4.z = -8.0F + stand * 9.0F;
      this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * ground - stand * 0.3F;
      this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * ground - stand * 0.3F;
      this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * ground - stand * 1.0F;
      this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * ground - stand * 1.0F;
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
