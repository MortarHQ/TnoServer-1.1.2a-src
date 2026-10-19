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

public class EntropyKeeperModel<T extends Mob> extends EntityModel<T> {
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;

   public EntropyKeeperModel(ModelPart root) {
      this.root = root.getChild("root");
      this.body = this.root.getChild("body");
      this.head = this.body.getChild("head");
      this.leftArm = this.body.getChild("left_arm");
      this.rightArm = this.body.getChild("right_arm");
      this.leftLeg = this.root.getChild("left_leg");
      this.rightLeg = this.root.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -25.0F, 2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "body5_r1",
         CubeListBuilder.create().texOffs(0, 11).addBox(-4.0F, -22.0F, -1.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "body4_r1",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-5.0F, -32.0F, 2.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(24, 11)
            .addBox(-4.0F, -27.0F, 3.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leftArm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(4.0F, -31.0F, 3.0F));
      leftArm.addOrReplaceChild(
         "left_arm4_r1",
         CubeListBuilder.create().texOffs(40, 33).addBox(5.0F, -8.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, 31.0F, -3.0F, -0.3054F, 0.0F, 0.0436F)
      );
      leftArm.addOrReplaceChild(
         "left_arm3_r1",
         CubeListBuilder.create().texOffs(40, 19).addBox(5.0F, -19.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, 31.0F, -3.0F, -0.1745F, 0.0F, 0.0436F)
      );
      leftArm.addOrReplaceChild(
         "left_arm2_r1",
         CubeListBuilder.create().texOffs(48, 43).addBox(7.0F, -20.0F, 3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, 31.0F, -3.0F, 0.0F, 0.0F, -0.0873F)
      );
      leftArm.addOrReplaceChild(
         "left_arm1_r1",
         CubeListBuilder.create().texOffs(24, 37).addBox(8.0F, -30.0F, 7.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, 31.0F, -3.0F, 0.1745F, 0.0F, -0.1309F)
      );
      PartDefinition rightArm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-4.0F, -31.0F, 3.0F));
      rightArm.addOrReplaceChild(
         "right_arm4_r1",
         CubeListBuilder.create().texOffs(0, 39).addBox(-7.0F, -8.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0F, 31.0F, -3.0F, -0.3054F, 0.0F, -0.0436F)
      );
      rightArm.addOrReplaceChild(
         "right_arm3_r1",
         CubeListBuilder.create().texOffs(32, 37).addBox(-7.0F, -19.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0F, 31.0F, -3.0F, -0.1745F, 0.0F, -0.0436F)
      );
      rightArm.addOrReplaceChild(
         "right_arm2_r1",
         CubeListBuilder.create().texOffs(48, 22).addBox(-10.0F, -20.0F, 3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0F, 31.0F, -3.0F, 0.0F, 0.0F, 0.0873F)
      );
      rightArm.addOrReplaceChild(
         "right_arm1_r1",
         CubeListBuilder.create().texOffs(16, 32).addBox(-10.0F, -30.0F, 7.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0F, 31.0F, -3.0F, 0.1745F, 0.0F, 0.1309F)
      );
      PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -31.0F, 2.0F));
      head.addOrReplaceChild(
         "head2_r1",
         CubeListBuilder.create()
            .texOffs(24, 19)
            .addBox(-2.0F, -41.0F, 3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(32, 6)
            .addBox(-1.0F, -35.0F, 4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 31.0F, -2.0F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3.0F, -18.0F, 3.0F));
      leftLeg.addOrReplaceChild(
         "leg_left4_r1",
         CubeListBuilder.create().texOffs(48, 11).addBox(2.0F, -20.0F, -3.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.0F, 18.0F, -3.0F, -0.2182F, -0.1309F, 0.0F)
      );
      leftLeg.addOrReplaceChild(
         "leg_left3_r1",
         CubeListBuilder.create().texOffs(48, 28).addBox(2.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.0F, 18.0F, -3.0F, 0.0F, -0.1309F, 0.0F)
      );
      leftLeg.addOrReplaceChild(
         "leg_left2_r1",
         CubeListBuilder.create().texOffs(48, 0).addBox(2.0F, -11.0F, 3.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.0F, 18.0F, -3.0F, 0.3054F, -0.1745F, 0.0F)
      );
      leftLeg.addOrReplaceChild(
         "leg_left_r1",
         CubeListBuilder.create().texOffs(0, 32).addBox(2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.0F, 18.0F, -3.0F, 0.7854F, -0.1745F, 0.0F)
      );
      PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, -18.0F, 3.0F));
      rightLeg.addOrReplaceChild(
         "leg_right4_r1",
         CubeListBuilder.create().texOffs(12, 47).addBox(-4.0F, -20.0F, -3.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0F, 18.0F, -3.0F, -0.2182F, 0.1309F, 0.0F)
      );
      rightLeg.addOrReplaceChild(
         "leg_right3_r1",
         CubeListBuilder.create().texOffs(40, 6).addBox(-4.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0F, 18.0F, -3.0F, 0.0F, 0.1309F, 0.0F)
      );
      rightLeg.addOrReplaceChild(
         "leg_right2_r1",
         CubeListBuilder.create().texOffs(40, 43).addBox(-4.0F, -11.0F, 3.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0F, 18.0F, -3.0F, 0.3054F, 0.1745F, 0.0F)
      );
      rightLeg.addOrReplaceChild(
         "leg_right_r1",
         CubeListBuilder.create().texOffs(24, 30).addBox(-4.0F, -1.0F, 0.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.0F, 18.0F, -3.0F, 0.7854F, 0.1745F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
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
      }
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
