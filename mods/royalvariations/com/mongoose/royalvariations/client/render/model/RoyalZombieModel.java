package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.client.render.animation.RoyalZombieAnimations;
import com.mongoose.royalvariations.common.entities.hostile.RoyalZombie;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class RoyalZombieModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart main;
   private final ModelPart body_main;
   private final ModelPart body;
   private final ModelPart cloak;
   private final ModelPart cloak_2;
   private final ModelPart mainhead;
   private final ModelPart head;
   private final ModelPart left_eyebrow;
   private final ModelPart right_eyebrow;
   private final ModelPart crown;
   private final ModelPart right_arm;
   private final ModelPart left_arm;
   private final ModelPart staff;
   private final ModelPart sparc;
   private final ModelPart left_leg;
   private final ModelPart right_leg;

   public RoyalZombieModel(ModelPart root) {
      this.root = root;
      this.main = root.getChild("main");
      this.body_main = this.main.getChild("body_main");
      this.body = this.body_main.getChild("body");
      this.cloak = this.body.getChild("cloak");
      this.cloak_2 = this.cloak.getChild("cloak_2");
      this.mainhead = this.body_main.getChild("mainhead");
      this.head = this.mainhead.getChild("head");
      this.left_eyebrow = this.head.getChild("left_eyebrow");
      this.right_eyebrow = this.head.getChild("right_eyebrow");
      this.crown = this.head.getChild("crown");
      this.right_arm = this.body_main.getChild("right_arm");
      this.left_arm = this.body_main.getChild("left_arm");
      this.staff = this.left_arm.getChild("staff");
      this.sparc = this.staff.getChild("sparc");
      this.left_leg = this.main.getChild("left_leg");
      this.right_leg = this.main.getChild("right_leg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 6.2F, 0.0F));
      PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 3.2F, 0.0F));
      PartDefinition body = body_main.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-4.0F, -9.5F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 70)
            .addBox(-4.0F, -0.5F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
            .texOffs(23, 54)
            .addBox(-4.0F, -9.6F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.2F))
            .texOffs(64, 53)
            .addBox(-4.0F, -9.6F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.3F))
            .texOffs(0, 117)
            .addBox(-5.0F, -10.3F, -1.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.3F))
            .texOffs(0, 83)
            .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.2F)),
         PartPose.offset(0.0F, 0.1F, 0.0F)
      );
      PartDefinition cloak = body.addOrReplaceChild(
         "cloak", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -8.8111F, 2.1894F, -0.0524F, 0.0F, 0.0F)
      );
      PartDefinition Body_r1 = cloak.addOrReplaceChild(
         "Body_r1",
         CubeListBuilder.create().texOffs(46, 20).addBox(-4.0F, -0.3F, -0.1F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -0.3889F, -0.0894F, 0.1396F, 0.0F, 0.0F)
      );
      PartDefinition cloak_2 = cloak.addOrReplaceChild("cloak_2", CubeListBuilder.create(), PartPose.offset(0.0F, 9.2978F, 1.6613F));
      PartDefinition Body_r2 = cloak_2.addOrReplaceChild(
         "Body_r2",
         CubeListBuilder.create()
            .texOffs(70, 10)
            .addBox(-4.0F, 15.7F, -0.1F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.15F))
            .texOffs(71, 23)
            .addBox(-4.0F, 9.7F, -0.1F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -9.6867F, -1.7507F, 0.1396F, 0.0F, 0.0F)
      );
      PartDefinition mainhead = body_main.addOrReplaceChild("mainhead", CubeListBuilder.create(), PartPose.offset(0.0F, -9.4F, 0.0F));
      PartDefinition head = mainhead.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(104, 1)
            .addBox(-4.0F, -3.0F, -4.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.12F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_eyebrow = head.addOrReplaceChild(
         "left_eyebrow",
         CubeListBuilder.create().texOffs(97, 26).mirror().addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).mirror(false),
         PartPose.offset(2.0F, -4.5F, -4.0F)
      );
      PartDefinition right_eyebrow = head.addOrReplaceChild(
         "right_eyebrow",
         CubeListBuilder.create().texOffs(97, 26).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)),
         PartPose.offset(-2.0F, -4.5F, -4.0F)
      );
      PartDefinition crown = head.addOrReplaceChild(
         "crown",
         CubeListBuilder.create().texOffs(44, 6).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F)),
         PartPose.offset(0.0F, -10.0F, 0.0F)
      );
      PartDefinition right_arm = body_main.addOrReplaceChild(
         "right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, -7.5F, -0.4F, 0.0878F, 0.0686F, 0.0137F)
      );
      PartDefinition RightArm_r1 = right_arm.addOrReplaceChild(
         "RightArm_r1",
         CubeListBuilder.create()
            .texOffs(48, 38)
            .addBox(-2.0F, -1.9F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F))
            .texOffs(24, 0)
            .addBox(-2.0F, 3.6F, -1.9F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offsetAndRotation(-1.0F, 0.0F, 0.4F, -1.5359F, 0.0F, 0.0F)
      );
      PartDefinition RightArm_r2 = right_arm.addOrReplaceChild(
         "RightArm_r2",
         CubeListBuilder.create()
            .texOffs(112, 36)
            .addBox(-2.0F, 4.6F, -2.6F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
            .texOffs(0, 32)
            .addBox(-2.0F, -2.4F, -2.6F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, 0.7F, 0.0F, -1.5359F, 0.0F, 0.0F)
      );
      PartDefinition left_arm = body_main.addOrReplaceChild(
         "left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(4.1F, -7.2F, 0.0F, 0.0017F, -0.0638F, -0.0165F)
      );
      PartDefinition LeftArm_r1 = left_arm.addOrReplaceChild(
         "LeftArm_r1",
         CubeListBuilder.create()
            .texOffs(24, 0)
            .addBox(-2.0F, 3.7F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F))
            .texOffs(48, 38)
            .mirror()
            .addBox(-2.0F, -1.9F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.2F))
            .mirror(false)
            .texOffs(112, 36)
            .mirror()
            .addBox(-2.0F, 5.1F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
            .mirror(false)
            .texOffs(0, 32)
            .mirror()
            .addBox(-2.0F, -1.9F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(1.9F, -0.3F, 0.0F, -1.4486F, 0.0F, 0.0F)
      );
      PartDefinition staff = left_arm.addOrReplaceChild("staff", CubeListBuilder.create(), PartPose.offset(1.9F, 0.6171F, -8.2467F));
      PartDefinition LeftArm_r2 = staff.addOrReplaceChild(
         "LeftArm_r2",
         CubeListBuilder.create()
            .texOffs(73, 124)
            .mirror()
            .addBox(0.5F, 9.1F, 1.2F, -1.0F, -1.0F, -7.0F, new CubeDeformation(-0.16F))
            .mirror(false)
            .texOffs(33, 38)
            .mirror()
            .addBox(-1.5F, 7.1F, -8.8F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(106, 67)
            .mirror()
            .addBox(-0.5F, 8.1F, -2.8F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.2F))
            .mirror(false)
            .texOffs(16, 34)
            .mirror()
            .addBox(-0.5F, 8.1F, -7.8F, 1.0F, 1.0F, 13.0F, new CubeDeformation(0.1F))
            .mirror(false),
         PartPose.offsetAndRotation(0.0F, -1.5171F, 8.2467F, -1.4486F, 0.0F, 0.0F)
      );
      PartDefinition sparc = staff.addOrReplaceChild("sparc", CubeListBuilder.create(), PartPose.offset(0.0F, -7.7146F, -1.1789F));
      PartDefinition LeftArm_r3 = sparc.addOrReplaceChild(
         "LeftArm_r3",
         CubeListBuilder.create().texOffs(60, 123).mirror().addBox(5.5F, 10.1F, -5.8F, -3.0F, -3.0F, -3.0F, new CubeDeformation(-0.1F)).mirror(false),
         PartPose.offsetAndRotation(-4.0F, 6.1975F, 9.4255F, -1.4486F, 0.0F, 0.0F)
      );
      PartDefinition left_leg = main.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create()
            .texOffs(24, 16)
            .mirror()
            .addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(32, 5)
            .addBox(-1.9F, 7.2F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offset(1.9F, 5.8F, 0.0F)
      );
      PartDefinition right_leg = main.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create()
            .texOffs(24, 16)
            .addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(32, 5)
            .addBox(-2.1F, 7.2F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offset(-1.9F, 5.8F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      if (entity instanceof RoyalZombie royalZombie) {
         if (!royalZombie.isDeadOrDying()) {
            this.animateHeadLookTarget(netHeadYaw, headPitch);
         }

         this.animate(royalZombie.idleAnimationState, RoyalZombieAnimations.IDLE, ageInTicks);
         this.animate(royalZombie.toIdleAnimationState, RoyalZombieAnimations.TO_IDLE, ageInTicks);
         this.animate(royalZombie.crownAnimationState, RoyalZombieAnimations.CROWN, ageInTicks);
         this.animate(royalZombie.attackAnimationState, RoyalZombieAnimations.ATTACK, ageInTicks);
         this.animate(royalZombie.blessAnimationState, RoyalZombieAnimations.BLESSING, ageInTicks);
         if (royalZombie.isAggressive()) {
            this.animateWalk(RoyalZombieAnimations.AGGRESSIVE, limbSwing, limbSwingAmount, 2.5F, 20.0F);
         } else {
            this.animateWalk(RoyalZombieAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
         }
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
