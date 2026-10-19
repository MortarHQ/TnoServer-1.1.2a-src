package com.chronodawn.client.model;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.mobs.SecondwingFowlEntity;
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

public class SecondwingFowlModel extends EntityModel<SecondwingFowlEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "secondwing_fowl"), "main");
   private final ModelPart head;
   private final ModelPart bill;
   private final ModelPart chin;
   private final ModelPart body;
   private final ModelPart leftWing;
   private final ModelPart rightWing;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public SecondwingFowlModel(ModelPart root) {
      this.head = root.getChild("head");
      this.bill = root.getChild("bill");
      this.chin = root.getChild("chin");
      this.body = root.getChild("body");
      this.leftWing = root.getChild("left_wing");
      this.rightWing = root.getChild("right_wing");
      this.leftLeg = root.getChild("left_leg");
      this.rightLeg = root.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(0, 23)
            .addBox(-2.0F, -10.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 15.0F, -4.0F)
      );
      partdefinition.addOrReplaceChild(
         "bill",
         CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 15.0F, -4.0F)
      );
      partdefinition.addOrReplaceChild(
         "chin",
         CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 15.0F, -4.0F)
      );
      partdefinition.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_wing",
         CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.0F, 13.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_wing",
         CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.0F, 13.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(1.0F, 19.0F, 1.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, 19.0F, 1.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   public void setupAnim(SecondwingFowlEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.bill.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.chin.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leftWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.rightWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
