package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.client.render.animation.RoyalEndermanAnimations;
import com.mongoose.royalvariations.common.entities.neutral.RoyalEnderman;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class RoyalEndermanModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart main;
   private final ModelPart body_main;
   private final ModelPart central_halo;
   private final ModelPart right_halo;
   private final ModelPart left_halo;
   private final ModelPart Body;
   private final ModelPart RightArm;
   private final ModelPart Head;
   private final ModelPart head_top;
   private final ModelPart left_eyelid;
   private final ModelPart right_eyelid;
   private final ModelPart left_eye;
   private final ModelPart right_eye;
   private final ModelPart third_eye;
   private final ModelPart third_pupil;
   private final ModelPart crown;
   private final ModelPart LeftArm;
   private final ModelPart RightLeg;
   private final ModelPart LeftLeg;

   public RoyalEndermanModel(ModelPart root) {
      this.root = root;
      this.main = root.getChild("main");
      this.body_main = this.main.getChild("body_main");
      this.central_halo = this.body_main.getChild("central_halo");
      this.right_halo = this.body_main.getChild("right_halo");
      this.left_halo = this.body_main.getChild("left_halo");
      this.Body = this.body_main.getChild("Body");
      this.RightArm = this.body_main.getChild("RightArm");
      this.Head = this.body_main.getChild("Head");
      this.head_top = this.Head.getChild("head_top");
      this.left_eyelid = this.head_top.getChild("left_eyelid");
      this.right_eyelid = this.head_top.getChild("right_eyelid");
      this.left_eye = this.head_top.getChild("left_eye");
      this.right_eye = this.head_top.getChild("right_eye");
      this.third_eye = this.head_top.getChild("third_eye");
      this.third_pupil = this.third_eye.getChild("third_pupil");
      this.crown = this.head_top.getChild("crown");
      this.LeftArm = this.body_main.getChild("LeftArm");
      this.RightLeg = this.main.getChild("RightLeg");
      this.LeftLeg = this.main.getChild("LeftLeg");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));
      PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 4.6F, 0.0F));
      PartDefinition central_halo = body_main.addOrReplaceChild(
         "central_halo",
         CubeListBuilder.create().texOffs(50, 48).addBox(-6.5F, -6.5F, 0.0F, 13.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -29.3F, 4.4F)
      );
      PartDefinition right_halo = body_main.addOrReplaceChild(
         "right_halo",
         CubeListBuilder.create().texOffs(0, 64).addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-11.0F, -20.3F, 4.4F)
      );
      PartDefinition left_halo = body_main.addOrReplaceChild(
         "left_halo",
         CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(11.0F, -20.3F, 4.4F)
      );
      PartDefinition Body = body_main.addOrReplaceChild(
         "Body",
         CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-4.0F, -10.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 32)
            .addBox(-4.5F, 2.0F, -2.0F, 9.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(56, 0)
            .addBox(-4.0F, -1.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
            .texOffs(64, 20)
            .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.12F))
            .texOffs(26, 48)
            .addBox(-4.0F, -10.2F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.15F))
            .texOffs(0, 53)
            .addBox(-4.0F, -10.2F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(26, 37)
            .addBox(-2.6F, -7.7F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
            .texOffs(26, 39)
            .addBox(1.6F, -7.7F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
            .texOffs(26, 41)
            .addBox(-0.5F, -5.6F, -2.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.11F)),
         PartPose.offset(0.0F, -0.6F, 0.0F)
      );
      PartDefinition RightArm = body_main.addOrReplaceChild(
         "RightArm",
         CubeListBuilder.create()
            .texOffs(32, 16)
            .mirror()
            .addBox(-1.1F, -1.4F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(58, 61)
            .mirror()
            .addBox(-1.1F, 14.6F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.1F))
            .mirror(false)
            .texOffs(50, 61)
            .mirror()
            .addBox(-1.1F, -1.4F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.2F))
            .mirror(false)
            .texOffs(14, 64)
            .mirror()
            .addBox(-1.1F, -1.4F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F))
            .mirror(false)
            .texOffs(56, 35)
            .mirror()
            .addBox(-6.3F, -6.7F, 0.0F, 7.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offsetAndRotation(-5.4F, -9.2F, 0.0F, 0.0F, 0.0F, 0.0698F)
      );
      PartDefinition Head = body_main.addOrReplaceChild(
         "Head",
         CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -6.7F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.55F)),
         PartPose.offset(0.0F, -10.9F, 0.0F)
      );
      PartDefinition head_top = Head.addOrReplaceChild(
         "head_top",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.7F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F)),
         PartPose.offset(0.0F, -1.0F, 0.0F)
      );
      PartDefinition left_eyelid = head_top.addOrReplaceChild(
         "left_eyelid",
         CubeListBuilder.create().texOffs(22, 64).addBox(-1.5F, -0.5F, -0.1F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(1.9F, -1.2F, -3.3F)
      );
      PartDefinition right_eyelid = head_top.addOrReplaceChild(
         "right_eyelid",
         CubeListBuilder.create().texOffs(22, 64).mirror().addBox(-1.5F, -0.5F, -0.1F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(-1.9F, -1.2F, -3.3F)
      );
      PartDefinition left_eye = head_top.addOrReplaceChild(
         "left_eye",
         CubeListBuilder.create().texOffs(26, 35).addBox(-0.5F, -0.5F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.07F)),
         PartPose.offset(2.2F, -1.26F, -3.48F)
      );
      PartDefinition right_eye = head_top.addOrReplaceChild(
         "right_eye",
         CubeListBuilder.create().texOffs(26, 35).mirror().addBox(-0.5F, -0.5F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.07F)).mirror(false),
         PartPose.offset(-2.2F, -1.26F, -3.48F)
      );
      PartDefinition third_eye = head_top.addOrReplaceChild(
         "third_eye",
         CubeListBuilder.create().texOffs(64, 16).addBox(-2.0F, -1.5F, -0.35F, 4.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)),
         PartPose.offset(0.0F, -3.25F, -3.5F)
      );
      PartDefinition third_pupil = third_eye.addOrReplaceChild(
         "third_pupil",
         CubeListBuilder.create().texOffs(26, 32).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.5F)),
         PartPose.offset(0.0F, 0.0F, -0.15F)
      );
      PartDefinition crown = head_top.addOrReplaceChild(
         "crown",
         CubeListBuilder.create().texOffs(56, 7).addBox(-2.5F, -2.9F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F)),
         PartPose.offset(0.0F, -7.1F, 0.0F)
      );
      PartDefinition LeftArm = body_main.addOrReplaceChild(
         "LeftArm",
         CubeListBuilder.create()
            .texOffs(32, 16)
            .addBox(-0.9F, -1.4F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(58, 61)
            .addBox(-0.9F, 14.6F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.1F))
            .texOffs(50, 61)
            .addBox(-0.9F, -1.4F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.2F))
            .texOffs(14, 64)
            .addBox(-0.9F, -1.4F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.3F))
            .texOffs(56, 35)
            .addBox(-0.7F, -6.7F, 0.0F, 7.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.4F, -9.2F, 0.0F, 0.0F, 0.0F, -0.0698F)
      );
      PartDefinition RightLeg = main.addOrReplaceChild(
         "RightLeg",
         CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.0F, 4.0F, 0.0F)
      );
      PartDefinition RightLeg_r1 = RightLeg.addOrReplaceChild(
         "RightLeg_r1",
         CubeListBuilder.create().texOffs(56, 16).addBox(-1.0F, -3.1F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.2F)),
         PartPose.offsetAndRotation(-0.1F, 1.1F, 0.0F, 0.0F, 0.0F, 0.0698F)
      );
      PartDefinition LeftLeg = main.addOrReplaceChild(
         "LeftLeg",
         CubeListBuilder.create().texOffs(48, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.0F, 4.0F, 0.0F)
      );
      PartDefinition LeftLeg_r1 = LeftLeg.addOrReplaceChild(
         "LeftLeg_r1",
         CubeListBuilder.create().texOffs(56, 16).mirror().addBox(-1.0F, -3.1F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false),
         PartPose.offsetAndRotation(0.1F, 1.1F, 0.0F, 0.0F, 0.0F, -0.0698F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      if (entity instanceof RoyalEnderman enderman) {
         if (!enderman.isDeadOrDying()) {
            this.animateHeadLookTarget(netHeadYaw, headPitch);
         }

         this.animate(enderman.idleAnimationState, RoyalEndermanAnimations.IDLE, ageInTicks);
         this.animate(enderman.toIdleAnimationState, RoyalEndermanAnimations.TO_IDLE, ageInTicks);
         this.animate(enderman.crownAnimationState, RoyalEndermanAnimations.CROWN, ageInTicks);
         this.animate(enderman.tremorAnimationState, RoyalEndermanAnimations.TREMOR, ageInTicks);
         this.animate(enderman.blinkAnimationState, RoyalEndermanAnimations.BLINK, ageInTicks);
         this.animate(enderman.attackAnimationState, RoyalEndermanAnimations.ATTACK, ageInTicks);
         this.animate(enderman.blessAnimationState, RoyalEndermanAnimations.BLESS, ageInTicks);
         if (enderman.isCreepy()) {
            this.animateWalk(RoyalEndermanAnimations.AGGRESSION, limbSwing, limbSwingAmount, 2.5F, 20.0F);
         } else {
            this.animateWalk(RoyalEndermanAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
         }
      }
   }

   private void animateHeadLookTarget(float netHeadYaw, float headPitch) {
      this.Head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.Head.xRot = headPitch * (float) (Math.PI / 180.0);
   }

   public ModelPart root() {
      return this.root;
   }
}
