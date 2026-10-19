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
import net.unusual.block_factorys_bosses.client.animation.soul_skeletonAnimation;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;

public class Modelsoul_skeleton<T extends SoulSkeletonEntity> extends HierarchicalModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelsoul_skeleton"), "main");
   public final ModelPart root;
   public final ModelPart all;
   public final ModelPart right_leg;
   public final ModelPart left_leg;
   public final ModelPart torso;
   public final ModelPart skirt;
   public final ModelPart head;
   public final ModelPart left_arm;
   public final ModelPart right_arm;
   public final ModelPart sword;

   public Modelsoul_skeleton(ModelPart root) {
      this.root = root;
      this.all = root.getChild("all");
      this.right_leg = this.all.getChild("right_leg");
      this.left_leg = this.all.getChild("left_leg");
      this.torso = this.all.getChild("torso");
      this.skirt = this.torso.getChild("skirt");
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
         CubeListBuilder.create().texOffs(24, 43).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 14.7F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, -13.7F, 0.0F)
      );
      PartDefinition left_leg = all.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(32, 43).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 14.7F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.0F, -13.7F, 0.0F)
      );
      PartDefinition torso = all.addOrReplaceChild(
         "torso",
         CubeListBuilder.create()
            .texOffs(0, 32)
            .addBox(-4.0F, -12.8F, -1.6F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(32, 20)
            .addBox(-4.0F, -12.8F, -1.6F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)),
         PartPose.offset(0.0F, -13.9F, -0.4F)
      );
      PartDefinition torso_r1 = torso.addOrReplaceChild(
         "torso_r1",
         CubeListBuilder.create().texOffs(40, 46).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -2.3F, -1.6F, -0.0865F, 0.0114F, 0.1304F)
      );
      PartDefinition torso_r2 = torso.addOrReplaceChild(
         "torso_r2",
         CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.15F)),
         PartPose.offsetAndRotation(0.0F, -1.8F, 0.4F, 0.0F, 0.0F, 0.0873F)
      );
      PartDefinition skirt = torso.addOrReplaceChild(
         "skirt",
         CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -3.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.01F)),
         PartPose.offset(0.0F, 0.2F, 0.4F)
      );
      PartDefinition head = torso.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.25F))
            .texOffs(0, 16)
            .addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -11.8F, 0.4F)
      );
      PartDefinition left_arm = torso.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-1.9667F, -1.3F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 48)
            .addBox(5.7333F, -1.3F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
            .texOffs(24, 38)
            .addBox(3.7333F, -2.3F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.15F)),
         PartPose.offsetAndRotation(4.7667F, -11.0F, 0.4F, 0.0F, 0.0F, 1.5708F)
      );
      PartDefinition right_arm = torso.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(32, 0).addBox(-11.9F, -1.3F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.9F, -10.8F, 0.4F, 0.0F, 0.0F, -1.5708F)
      );
      PartDefinition sword = right_arm.addOrReplaceChild(
         "sword",
         CubeListBuilder.create()
            .texOffs(44, 38)
            .addBox(-1.0F, -0.5F, -1.7778F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(40, 43)
            .addBox(-3.0F, -0.5F, -3.7778F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(45, 32)
            .addBox(-0.5F, 0.0F, -17.7778F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(16, 48)
            .addBox(-1.5F, 0.0F, -15.7778F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(40, 46)
            .addBox(-2.5F, 0.0F, -13.7778F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-9.4F, -0.3F, 0.7778F)
      );
      PartDefinition sword_r1 = sword.addOrReplaceChild(
         "sword_r1",
         CubeListBuilder.create().texOffs(8, 48).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.5F, 2.2222F, 0.0F, -0.7854F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.head.yRot = netHeadYaw / (180.0F / (float)Math.PI);
      this.head.xRot = headPitch / (180.0F / (float)Math.PI);
      this.animate(entity.attackAnimationState, soul_skeletonAnimation.ATTACK, ageInTicks);
      this.animate(entity.idleAnimationState, soul_skeletonAnimation.IDLE, ageInTicks);
      this.animateWalk(soul_skeletonAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 3.0F);
   }

   public ModelPart root() {
      return this.root;
   }

   private record ModelParts(ModelPart root) {
   }
}
