package com.chronodawn.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class TimeGuardianModel<T extends Mob> extends EntityModel<T> {
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftForearm;
   private final ModelPart rightForearm;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public TimeGuardianModel(ModelPart root) {
      this.root = root.getChild("root");
      this.body = this.root.getChild("body");
      this.head = this.body.getChild("head");
      this.leftArm = this.body.getChild("left_arm");
      this.rightArm = this.body.getChild("right_arm");
      this.leftForearm = this.leftArm.getChild("arm_left2_r1");
      this.rightForearm = this.rightArm.getChild("arm_right2_r1");
      this.leftLeg = this.root.getChild("left_leg");
      this.rightLeg = this.root.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(72, 50).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition body_r1 = body.addOrReplaceChild(
         "body_r1",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -24.0F, -5.0F, 16.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = body.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(30, 40)
            .addBox(0.0F, -5.0F, -4.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(50, 54)
            .addBox(0.0F, -4.0F, -3.0F, 5.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(8.0F, -29.0F, 0.0F)
      );
      PartDefinition arm_left3_r1 = left_arm.addOrReplaceChild(
         "arm_left3_r1",
         CubeListBuilder.create().texOffs(52, 0).addBox(2.0F, -4.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, 20.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition arm_left2_r1 = left_arm.addOrReplaceChild(
         "arm_left2_r1",
         CubeListBuilder.create().texOffs(60, 14).addBox(2.0F, -3.0F, -3.0F, 5.0F, 12.0F, 6.0F, new CubeDeformation(-0.2F)),
         PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition right_arm = body.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create()
            .texOffs(30, 26)
            .addBox(-7.0F, -5.0F, -4.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(28, 54)
            .addBox(-5.0F, -4.0F, -3.0F, 5.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-8.0F, -29.0F, 0.0F)
      );
      PartDefinition arm_right3_r1 = right_arm.addOrReplaceChild(
         "arm_right3_r1",
         CubeListBuilder.create().texOffs(0, 41).addBox(-8.0F, -4.0F, -4.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, 20.0F, -1.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition arm_right2_r1 = right_arm.addOrReplaceChild(
         "arm_right2_r1",
         CubeListBuilder.create().texOffs(0, 55).addBox(-7.0F, -3.0F, -3.0F, 5.0F, 12.0F, 6.0F, new CubeDeformation(-0.2F)),
         PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -32.0F, 0.0F));
      PartDefinition head_r1 = head.addOrReplaceChild(
         "head_r1",
         CubeListBuilder.create().texOffs(0, 26).addBox(-4.0F, -19.0F, -5.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(60, 41).addBox(1.0F, 14.0F, -5.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -16.0F, 0.0F)
      );
      PartDefinition leg_left_r1 = left_leg.addOrReplaceChild(
         "leg_left_r1",
         CubeListBuilder.create().texOffs(72, 72).addBox(2.0F, -1.0F, -4.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(-1.0F, 9.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leg_left_r2 = left_leg.addOrReplaceChild(
         "leg_left_r2",
         CubeListBuilder.create().texOffs(20, 74).addBox(2.0F, -6.0F, -4.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 9.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      PartDefinition right_leg = root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(60, 32).addBox(-6.0F, 14.0F, -5.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -16.0F, 0.0F)
      );
      PartDefinition leg_right_r1 = right_leg.addOrReplaceChild(
         "leg_right_r1",
         CubeListBuilder.create().texOffs(72, 60).addBox(-5.0F, -1.0F, -4.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(-0.1F)),
         PartPose.offsetAndRotation(-1.0F, 9.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leg_right_r2 = right_leg.addOrReplaceChild(
         "leg_right_r2",
         CubeListBuilder.create().texOffs(0, 73).addBox(-5.0F, -6.0F, -4.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 9.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.xRot = 0.0F;
      this.head.yRot = 0.0F;
      this.body.xRot = 0.0F;
      this.body.yRot = 0.0F;
      this.leftArm.xRot = 0.0F;
      this.leftArm.zRot = 0.0F;
      this.rightArm.xRot = 0.0F;
      this.rightArm.zRot = 0.0F;
      this.leftForearm.xRot = 0.0F;
      this.rightForearm.xRot = 0.0F;
      this.leftLeg.xRot = 0.0F;
      this.rightLeg.xRot = 0.0F;
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
      this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount;
      this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
      if (limbSwingAmount < 0.01F && entity.attackAnim <= 0.0F) {
         float idleSwing = ageInTicks * 0.05F;
         this.rightArm.xRot = this.rightArm.xRot + Mth.cos(idleSwing) * 0.05F;
         this.leftArm.xRot = this.leftArm.xRot + Mth.cos(idleSwing + (float) Math.PI) * 0.05F;
      }

      if (entity.attackAnim > 0.0F) {
         float attackProgress = entity.attackAnim;
         float armRaise = Mth.sin(attackProgress * (float) Math.PI);
         this.rightArm.xRot = -2.0F * armRaise;
         this.leftArm.xRot = -2.0F * armRaise;
         this.rightArm.zRot = 0.3F * armRaise;
         this.leftArm.zRot = -0.3F * armRaise;
         this.leftForearm.xRot = -0.5F * armRaise;
         this.rightForearm.xRot = -0.5F * armRaise;
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
