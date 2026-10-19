package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.client.render.animation.RoyalCreeperAnimations;
import com.mongoose.royalvariations.common.entities.hostile.RoyalCreeper;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class RoyalCreeperModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart main;
   private final ModelPart body_main;
   private final ModelPart body;
   private final ModelPart base;
   private final ModelPart cape;
   private final ModelPart clothes;
   private final ModelPart mainhead;
   private final ModelPart head;
   private final ModelPart skull;
   private final ModelPart facial_glow;
   private final ModelPart left_eye;
   private final ModelPart right_eye;
   private final ModelPart crown;
   private final ModelPart legs;
   private final ModelPart right_leg1;
   private final ModelPart left_leg1;
   private final ModelPart right_leg2;
   private final ModelPart leg_ring;
   private final ModelPart left_leg2;

   public RoyalCreeperModel(ModelPart root) {
      this.root = root;
      this.main = root.getChild("main");
      this.body_main = this.main.getChild("body_main");
      this.body = this.body_main.getChild("body");
      this.base = this.body.getChild("base");
      this.cape = this.body.getChild("cape");
      this.clothes = this.body.getChild("clothes");
      this.mainhead = this.body_main.getChild("mainhead");
      this.head = this.mainhead.getChild("head");
      this.skull = this.head.getChild("skull");
      this.facial_glow = this.head.getChild("facial_glow");
      this.left_eye = this.head.getChild("left_eye");
      this.right_eye = this.head.getChild("right_eye");
      this.crown = this.head.getChild("crown");
      this.legs = this.main.getChild("legs");
      this.right_leg1 = this.legs.getChild("right_leg1");
      this.left_leg1 = this.legs.getChild("left_leg1");
      this.right_leg2 = this.legs.getChild("right_leg2");
      this.leg_ring = this.right_leg2.getChild("leg_ring");
      this.left_leg2 = this.legs.getChild("left_leg2");
   }

   public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 12.8F, 0.0F));
      PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 3.2F, 0.0F));
      PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.3F, 0.0F));
      PartDefinition base = body.addOrReplaceChild(
         "base", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(0.0F, 9.3F, 0.0F)
      );
      PartDefinition cape = body.addOrReplaceChild("cape", CubeListBuilder.create(), PartPose.offset(0.0F, -6.2613F, 2.0244F));
      PartDefinition body_r1 = cape.addOrReplaceChild(
         "body_r1",
         CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -6.5F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 4.4613F, 0.9756F, 0.2793F, 0.0F, 0.0F)
      );
      PartDefinition clothes = body.addOrReplaceChild(
         "clothes",
         CubeListBuilder.create()
            .texOffs(24, 29)
            .addBox(-4.5F, -18.9F, -2.2F, 9.0F, 3.0F, 5.0F, new CubeDeformation(0.3F))
            .texOffs(24, 16)
            .addBox(-4.0F, -18.1F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.2F))
            .texOffs(32, 9)
            .addBox(-4.0F, -17.0F, -2.0F, 8.0F, 6.0F, 1.0F, new CubeDeformation(0.25F)),
         PartPose.offset(0.0F, 9.4F, 0.0F)
      );
      PartDefinition body_r2 = clothes.addOrReplaceChild(
         "body_r2",
         CubeListBuilder.create().texOffs(20, 32).mirror().addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-5.8393F, -17.2393F, -0.2F, -0.3954F, -0.2634F, -1.3432F)
      );
      PartDefinition body_r3 = clothes.addOrReplaceChild(
         "body_r3",
         CubeListBuilder.create().texOffs(18, 32).mirror().addBox(-0.5F, -3.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-5.8393F, -18.5393F, 0.0F, -0.4638F, -0.0867F, -0.9569F)
      );
      PartDefinition body_r4 = clothes.addOrReplaceChild(
         "body_r4",
         CubeListBuilder.create().texOffs(34, 42).mirror().addBox(-0.5F, -4.0F, 0.0F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-5.2393F, -20.2393F, 0.3F, -0.4512F, 0.1408F, -0.5036F)
      );
      PartDefinition body_r5 = clothes.addOrReplaceChild(
         "body_r5",
         CubeListBuilder.create().texOffs(34, 42).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.2393F, -20.2393F, 0.3F, -0.4512F, -0.1408F, 0.5036F)
      );
      PartDefinition body_r6 = clothes.addOrReplaceChild(
         "body_r6",
         CubeListBuilder.create().texOffs(20, 32).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8393F, -17.2393F, -0.2F, -0.3954F, 0.2634F, 1.3432F)
      );
      PartDefinition body_r7 = clothes.addOrReplaceChild(
         "body_r7",
         CubeListBuilder.create().texOffs(18, 32).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.8393F, -18.5393F, 0.0F, -0.4638F, 0.0867F, 0.9569F)
      );
      PartDefinition mainhead = body_main.addOrReplaceChild("mainhead", CubeListBuilder.create(), PartPose.offset(0.0F, -10.3F, 0.0F));
      PartDefinition head = mainhead.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -0.6F, 0.0F));
      PartDefinition skull = head.addOrReplaceChild(
         "skull", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation), PartPose.offset(0.0F, 18.9F, 0.0F)
      );
      PartDefinition facial_glow = head.addOrReplaceChild(
         "facial_glow",
         CubeListBuilder.create().texOffs(1, 49).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -3.1F, 0.2F)
      );
      PartDefinition left_eye = head.addOrReplaceChild(
         "left_eye",
         CubeListBuilder.create().texOffs(0, 47).mirror().addBox(-0.5F, -0.5F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false),
         PartPose.offset(1.6F, -3.7F, -3.9F)
      );
      PartDefinition right_eye = head.addOrReplaceChild(
         "right_eye",
         CubeListBuilder.create().texOffs(0, 47).addBox(-0.5F, -0.5F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)),
         PartPose.offset(-1.6F, -3.7F, -3.9F)
      );
      PartDefinition crown = head.addOrReplaceChild(
         "crown",
         CubeListBuilder.create().texOffs(32, 0).addBox(-2.5F, -2.9F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(-0.4F)),
         PartPose.offset(0.0F, -8.9F, 0.0F)
      );
      PartDefinition legs = main.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(-2.0F, 5.2F, 4.0F));
      PartDefinition right_leg1 = legs.addOrReplaceChild(
         "right_leg1",
         CubeListBuilder.create().texOffs(18, 37).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, cubeDeformation),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition left_leg1 = legs.addOrReplaceChild(
         "left_leg1", CubeListBuilder.create().texOffs(18, 37).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, cubeDeformation), PartPose.offset(4.0F, 0.0F, 0.0F)
      );
      PartDefinition right_leg2 = legs.addOrReplaceChild(
         "right_leg2",
         CubeListBuilder.create().texOffs(18, 37).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, cubeDeformation),
         PartPose.offset(0.0F, 0.0F, -8.0F)
      );
      PartDefinition leg_ring = right_leg2.addOrReplaceChild(
         "leg_ring",
         CubeListBuilder.create().texOffs(34, 37).addBox(-4.0F, -3.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)),
         PartPose.offset(2.0F, 6.0F, 4.0F)
      );
      PartDefinition left_leg2 = legs.addOrReplaceChild(
         "left_leg2",
         CubeListBuilder.create().texOffs(18, 37).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, cubeDeformation),
         PartPose.offset(4.0F, 0.0F, -8.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      if (entity instanceof RoyalCreeper creeper) {
         if (!creeper.isDeadOrDying()) {
            this.animateHeadLookTarget(netHeadYaw, headPitch);
         }

         this.animate(creeper.idleAnimationState, RoyalCreeperAnimations.IDLE, ageInTicks);
         this.animate(creeper.toIdleAnimationState, RoyalCreeperAnimations.TO_IDLE, ageInTicks);
         this.animate(creeper.blessAnimationState, RoyalCreeperAnimations.BLESS, ageInTicks);
         this.animate(creeper.detonateAnimationState, RoyalCreeperAnimations.DETONATE, ageInTicks);
         this.animate(creeper.blinkAnimationState, RoyalCreeperAnimations.BLINK, ageInTicks);
         this.animate(creeper.crownAnimationState, RoyalCreeperAnimations.CROWN, ageInTicks);
         this.animateWalk(creeper.isAggressive() ? RoyalCreeperAnimations.AGGRESSION : RoyalCreeperAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
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
