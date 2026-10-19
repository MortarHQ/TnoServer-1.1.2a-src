package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.animation.soul_knight_wither_skeletonAnimation;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;

public class Modelsoul_knight_wither_skeleton<T extends SoulKnightWitherSkeletonEntity> extends HierarchicalModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelsoul_knight_wither_skeleton"), "main");
   public final ModelPart root;
   public final ModelPart all;
   public final ModelPart right_leg;
   public final ModelPart left_leg;
   public final ModelPart torso;
   public final ModelPart front_skirt;
   public final ModelPart back_skirt;
   public final ModelPart head;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart sword;

   public Modelsoul_knight_wither_skeleton(ModelPart root) {
      this.root = root;
      this.all = root.getChild("all");
      this.right_leg = this.all.getChild("right_leg");
      this.left_leg = this.all.getChild("left_leg");
      this.torso = this.all.getChild("torso");
      this.front_skirt = this.torso.getChild("front_skirt");
      this.back_skirt = this.torso.getChild("back_skirt");
      this.head = this.torso.getChild("head");
      this.left_arm = this.torso.getChild("left_arm");
      this.right_arm = this.torso.getChild("right_arm");
      this.sword = this.right_arm.getChild("sword");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition right_leg = all.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 54).addBox(-1.8F, 0.0F, -1.5F, 3.0F, 17.7F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, -17.9F, 0.0F)
      );
      PartDefinition right_leg_r1 = right_leg.addOrReplaceChild(
         "right_leg_r1",
         CubeListBuilder.create().texOffs(48, 58).addBox(-1.5F, -3.35F, -1.5F, 3.0F, 6.7F, 3.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(-0.3F, 14.35F, 0.0F, 0.0F, 0.0698F, 0.0F)
      );
      PartDefinition left_leg = all.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(52, 38).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 17.7F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.3F, -17.9F, 0.0F)
      );
      PartDefinition left_leg_r1 = left_leg.addOrReplaceChild(
         "left_leg_r1",
         CubeListBuilder.create().texOffs(36, 54).addBox(-1.5F, -3.35F, -1.5F, 3.0F, 6.7F, 3.0F, new CubeDeformation(0.25F)),
         PartPose.offsetAndRotation(0.0F, 14.35F, 0.0F, 0.0F, -0.0698F, 0.0F)
      );
      PartDefinition torso = all.addOrReplaceChild(
         "torso",
         CubeListBuilder.create()
            .texOffs(0, 38)
            .addBox(-4.5F, -12.8F, -1.6F, 9.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(26, 38)
            .addBox(-4.5F, -12.8F, -1.6F, 9.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
         PartPose.offset(0.0F, -17.1F, -0.4F)
      );
      PartDefinition front_skirt = torso.addOrReplaceChild(
         "front_skirt",
         CubeListBuilder.create().texOffs(64, 36).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -0.6F, -1.8F)
      );
      PartDefinition back_skirt = torso.addOrReplaceChild(
         "back_skirt",
         CubeListBuilder.create().texOffs(64, 29).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -0.6F, 2.6F)
      );
      PartDefinition head = torso.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 21)
            .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(-0.25F))
            .texOffs(32, 21)
            .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(36, 0)
            .addBox(-4.0F, -10.1F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, -11.8F, 0.4F)
      );
      PartDefinition head_r1 = head.addOrReplaceChild(
         "head_r1",
         CubeListBuilder.create().texOffs(64, 20).addBox(-0.1F, 0.2F, -2.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.2F, -6.5F, 0.0F, 0.0F, 0.0F, 0.2618F)
      );
      PartDefinition head_r2 = head.addOrReplaceChild(
         "head_r2",
         CubeListBuilder.create().texOffs(36, 63).addBox(0.1F, 0.2F, -2.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.2F, -6.5F, 0.0F, 0.0F, 0.0F, -0.2618F)
      );
      PartDefinition left_arm = torso.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(12, 54)
            .addBox(-0.4F, -1.4F, -1.5F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(60, 58)
            .addBox(2.8F, 2.8F, -2.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.4F, -11.6F, 0.4F)
      );
      PartDefinition left_arm_r1 = left_arm.addOrReplaceChild(
         "left_arm_r1",
         CubeListBuilder.create().texOffs(36, 13).addBox(-2.5F, -1.5F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.1F, 0.1F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition right_arm = torso.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(24, 54).addBox(-2.5F, -1.0F, -1.5F, 3.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.5F, -12.0F, 0.4F)
      );
      PartDefinition sword = right_arm.addOrReplaceChild(
         "sword",
         CubeListBuilder.create()
            .texOffs(54, 13)
            .addBox(-0.5F, -1.0F, -2.475F, 1.0F, 2.0F, 5.0F, new CubeDeformation(-0.01F))
            .texOffs(64, 42)
            .addBox(-0.5F, -3.0F, -3.975F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(0.0F, -1.5F, -21.975F, 0.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-0.5F, 12.7F, 0.475F)
      );
      PartDefinition sword_r1 = sword.addOrReplaceChild(
         "sword_r1",
         CubeListBuilder.create().texOffs(64, 50).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 2.925F, 0.7854F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.head.yRot = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.xRot = headPitch / (180.0F / (float)Math.PI);
      this.animate(entity.attackAnimationState, soul_knight_wither_skeletonAnimation.ATTACK, ageInTicks);
      this.animate(entity.idleAnimationState, soul_knight_wither_skeletonAnimation.IDLE, ageInTicks);
      this.animateWalk(soul_knight_wither_skeletonAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 3.0F);
   }

   public ModelPart root() {
      return this.root;
   }

   private record ModelParts(ModelPart root) {
   }
}
