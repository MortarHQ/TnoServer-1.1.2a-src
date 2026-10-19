package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.client.render.animation.RoyalSkeletonAnimations;
import com.mongoose.royalvariations.common.entities.hostile.RoyalSkeleton;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class RoyalSkeletonModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart main;
   private final ModelPart right_leg;
   private final ModelPart left_leg;
   private final ModelPart body_main;
   private final ModelPart body;
   private final ModelPart mainhead;
   private final ModelPart head;
   private final ModelPart crown;
   private final ModelPart left_arm;
   private final ModelPart right_arm;
   private final ModelPart bow;
   private final ModelPart arrow;
   private final ModelPart bowstring;
   private final ModelPart rib_1;
   private final ModelPart rib_2;

   public RoyalSkeletonModel(ModelPart root) {
      this.root = root;
      this.main = root.getChild("main");
      this.right_leg = this.main.getChild("right_leg");
      this.left_leg = this.main.getChild("left_leg");
      this.body_main = root.getChild("body_main");
      this.body = this.body_main.getChild("body");
      this.mainhead = this.body_main.getChild("mainhead");
      this.head = this.mainhead.getChild("head");
      this.crown = this.head.getChild("crown");
      this.left_arm = this.body_main.getChild("left_arm");
      this.right_arm = this.body_main.getChild("right_arm");
      this.bow = this.right_arm.getChild("bow");
      this.arrow = this.bow.getChild("arrow");
      this.bowstring = this.bow.getChild("bowstring");
      this.rib_1 = this.bow.getChild("rib_1");
      this.rib_2 = this.bow.getChild("rib_2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
      PartDefinition right_leg = main.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create()
            .texOffs(8, 64)
            .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(24, 64)
            .addBox(0.0F, 8.0F, 1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(60, 45)
            .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, 6.0F, 0.0F)
      );
      PartDefinition left_leg = main.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create()
            .texOffs(8, 64)
            .mirror()
            .addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(70, 37)
            .addBox(-1.0F, 1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.1F))
            .texOffs(60, 45)
            .mirror()
            .addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(24, 64)
            .mirror()
            .addBox(0.0F, 8.0F, 1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offset(2.0F, 6.0F, 0.0F)
      );
      PartDefinition body_main = partdefinition.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 8.9F, 0.0F));
      PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-1.0355F, 0.6541F, -0.1601F));
      PartDefinition Body_r5_r1 = body.addOrReplaceChild(
         "Body_r5_r1",
         CubeListBuilder.create().texOffs(44, 25).addBox(1.0F, -1.6F, -1.2F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 2.2F, 4.6F, 0.0394F, -0.0568F, -0.6023F)
      );
      PartDefinition Body_r6_r1 = body.addOrReplaceChild(
         "Body_r6_r1",
         CubeListBuilder.create().texOffs(44, 25).addBox(-4.0F, -1.5F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.0384F, -0.7498F, 3.7001F, -1.5314F, -0.0568F, -0.6023F)
      );
      PartDefinition Body_r5_r2 = body.addOrReplaceChild(
         "Body_r5_r2",
         CubeListBuilder.create().texOffs(44, 25).addBox(-4.0F, -1.5F, 0.1F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.5384F, -2.3187F, 3.3147F, -1.5314F, -0.0568F, -0.6023F)
      );
      PartDefinition Body_r4_r1 = body.addOrReplaceChild(
         "Body_r4_r1",
         CubeListBuilder.create().texOffs(44, 25).addBox(1.0F, -1.6F, -1.2F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.5F, 0.6311F, 4.2146F, 0.0394F, -0.0568F, -0.6023F)
      );
      PartDefinition Body_r3_r1 = body.addOrReplaceChild(
         "Body_r3_r1",
         CubeListBuilder.create().texOffs(62, 9).addBox(-4.0F, -1.6F, -1.9F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.5F, 1.0272F, 4.1589F, 0.0394F, -0.0568F, -0.6023F)
      );
      PartDefinition Body_r3_r2 = body.addOrReplaceChild(
         "Body_r3_r2",
         CubeListBuilder.create()
            .texOffs(70, 25)
            .addBox(-3.1F, -22.7F, -3.6F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.2F))
            .texOffs(52, 48)
            .addBox(-4.1F, -23.7F, -3.6F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(28, 48)
            .addBox(-4.1F, -23.7F, -3.6F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.15F))
            .texOffs(52, 55)
            .addBox(-4.1F, -14.2F, -3.6F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
            .texOffs(32, 72)
            .addBox(-3.1F, -14.2F, -3.6F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.15F))
            .texOffs(38, 0)
            .addBox(-4.1F, -23.7F, -3.6F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.2F, 13.3081F, 1.6858F, 0.0F, 0.0F, 0.0F)
      );
      PartDefinition Body_r5_r3 = body.addOrReplaceChild(
         "Body_r5_r3",
         CubeListBuilder.create().texOffs(44, 16).addBox(-3.0622F, -0.1068F, -4.0093F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.3259F, -10.7218F, 0.5266F, 0.0343F, -0.0209F, -0.1393F)
      );
      PartDefinition Body_r4_r2 = body.addOrReplaceChild(
         "Body_r4_r2",
         CubeListBuilder.create().texOffs(0, 19).addBox(-8.0F, 0.097F, -0.5142F, 14.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.1F, -10.392F, 1.7864F, 0.0873F, 0.0F, 0.0F)
      );
      PartDefinition mainhead = body_main.addOrReplaceChild("mainhead", CubeListBuilder.create(), PartPose.offset(0.0F, -11.3F, 0.0F));
      PartDefinition head = mainhead.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-0.001F, 0.0937F, -0.1091F));
      PartDefinition head_r1_r1 = head.addOrReplaceChild(
         "head_r1_r1",
         CubeListBuilder.create().texOffs(0, 28).addBox(-4.0032F, -31.3721F, -5.5995F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.0053F, 24.572F, 1.6006F, 0.0F, -0.0033F, 1.0E-4F)
      );
      PartDefinition crown = head.addOrReplaceChild(
         "crown", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0063F, -8.6452F, 0.4039F, -0.1396F, -0.0033F, 1.0E-4F)
      );
      PartDefinition head_r2_r1 = crown.addOrReplaceChild(
         "head_r2_r1",
         CubeListBuilder.create().texOffs(62, 0).addBox(-4.0032F, -35.9721F, -2.5995F, 5.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F)),
         PartPose.offsetAndRotation(1.5032F, 32.7278F, 4.4257F, 0.1396F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = body_main.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(4.9918F, -7.6007F, 0.0291F));
      PartDefinition LeftArm_r2_r1 = left_arm.addOrReplaceChild(
         "LeftArm_r2_r1",
         CubeListBuilder.create()
            .texOffs(72, 14)
            .addBox(4.0521F, -22.9258F, -2.6F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.2F))
            .texOffs(24, 72)
            .addBox(4.0521F, -19.1258F, -2.6F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.05F))
            .texOffs(0, 64)
            .mirror()
            .addBox(4.0521F, -22.9258F, -2.6F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(-1.4135F, 22.0056F, 1.8932F, 0.0251F, -0.0293F, -0.1614F)
      );
      PartDefinition right_arm = body_main.addOrReplaceChild(
         "right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.9045F, -8.4992F, -0.135F, -0.0799F, 0.0398F, 0.0152F)
      );
      PartDefinition RightArm_r3_r1 = right_arm.addOrReplaceChild(
         "RightArm_r3_r1",
         CubeListBuilder.create()
            .texOffs(64, 70)
            .addBox(-6.5713F, -18.6178F, -2.8396F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
            .texOffs(38, 16)
            .addBox(-6.5713F, -20.0178F, -2.3396F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(70, 31)
            .addBox(-6.5713F, -23.0178F, -2.8396F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(16, 64)
            .addBox(-6.0713F, -22.5178F, -2.3396F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F))
            .texOffs(16, 74)
            .addBox(-6.0713F, -15.0178F, -2.3396F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F))
            .texOffs(0, 64)
            .addBox(-6.0713F, -22.7178F, -2.3396F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.4399F, 22.361F, 2.1535F, 0.0312F, -0.0237F, 0.2425F)
      );
      PartDefinition bow = right_arm.addOrReplaceChild(
         "bow",
         CubeListBuilder.create().texOffs(64, 62).addBox(-0.5F, -1.0F, -2.9F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.2F)),
         PartPose.offsetAndRotation(-1.9955F, 11.1432F, -0.5861F, 0.0175F, 0.0F, 0.2269F)
      );
      PartDefinition arrow = bow.addOrReplaceChild(
         "arrow",
         CubeListBuilder.create().texOffs(60, 25).addBox(0.0F, -7.0F, -2.5F, 0.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -1.0F, -0.2F)
      );
      PartDefinition RightArm_r4_r1 = arrow.addOrReplaceChild(
         "RightArm_r4_r1",
         CubeListBuilder.create().texOffs(60, 25).addBox(0.0F, -7.0F, -2.5F, 0.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F)
      );
      PartDefinition bowstring = bow.addOrReplaceChild(
         "bowstring",
         CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -9.0F, 1.0F, 1.0F, 18.0F, new CubeDeformation(-0.2F)),
         PartPose.offset(0.0F, -6.9F, 0.1F)
      );
      PartDefinition rib_1 = bow.addOrReplaceChild("rib_1", CubeListBuilder.create(), PartPose.offset(-0.1F, -0.155F, -2.5726F));
      PartDefinition RightArm_r4_r2 = rib_1.addOrReplaceChild(
         "RightArm_r4_r2",
         CubeListBuilder.create()
            .texOffs(44, 72)
            .addBox(-0.3F, -1.2F, -10.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(32, 28)
            .addBox(0.2F, -2.2F, -13.0F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(28, 62)
            .addBox(-0.3F, -1.2F, -7.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.1F, -0.1F, -0.5F, -0.7854F, 0.0F, 0.0F)
      );
      PartDefinition rib_2 = bow.addOrReplaceChild("rib_2", CubeListBuilder.create(), PartPose.offset(0.0F, -0.105F, 2.7274F));
      PartDefinition RightArm_r5_r1 = rib_2.addOrReplaceChild(
         "RightArm_r5_r1",
         CubeListBuilder.create()
            .texOffs(52, 72)
            .addBox(-0.5F, -1.45F, 7.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(0, 44)
            .addBox(0.0F, -2.45F, -0.5F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(46, 62)
            .addBox(-0.5F, -1.45F, -0.5F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.3F, 0.5F, 0.7854F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      if (entity instanceof RoyalSkeleton royalSkeleton) {
         if (!royalSkeleton.isDeadOrDying()) {
            this.animateHeadLookTarget(netHeadYaw, headPitch);
         }

         this.animate(royalSkeleton.idleAnimationState, RoyalSkeletonAnimations.IDLE, ageInTicks);
         this.animate(royalSkeleton.toIdleAnimationState, RoyalSkeletonAnimations.TO_IDLE, ageInTicks);
         this.animate(royalSkeleton.prepareAnimationState, RoyalSkeletonAnimations.PREPARE, ageInTicks);
         this.animate(royalSkeleton.aggressionAnimationState, RoyalSkeletonAnimations.AGGRESSION, ageInTicks);
         this.animate(royalSkeleton.standDownAnimationState, RoyalSkeletonAnimations.STAND_DOWN, ageInTicks);
         this.animate(royalSkeleton.crownAnimationState, RoyalSkeletonAnimations.CROWN, ageInTicks);
         if (royalSkeleton.isAggressive()) {
            boolean flag = royalSkeleton.getFallFlyingTicks() > 4;
            float f = 1.0F;
            if (flag) {
               f = (float)royalSkeleton.getDeltaMovement().lengthSqr();
               f /= 0.2F;
               f *= f * f;
            }

            if (f < 1.0F) {
               f = 1.0F;
            }

            this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
            this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
         } else {
            this.animateWalk(RoyalSkeletonAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
         }

         this.arrow.visible = !royalSkeleton.isCurrentAnimation(RoyalSkeleton.IDLE);
      }
   }

   private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
      this.mainhead.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.mainhead.xRot = headPitch * (float) (Math.PI / 180.0);
   }

   public ModelPart root() {
      return this.root;
   }
}
