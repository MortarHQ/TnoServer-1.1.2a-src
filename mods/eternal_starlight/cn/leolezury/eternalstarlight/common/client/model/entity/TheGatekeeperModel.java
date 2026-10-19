package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.TheGatekeeperAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.HumanoidArm;

public class TheGatekeeperModel<T extends TheGatekeeper> extends AnimatedEntityModel<T> implements ArmedModel {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper"), "main");
   public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper_slim"), "main");
   public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper"), "outer");
   public static final ModelLayerLocation SLIM_OUTER_LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("the_gatekeeper_slim"), "outer");
   private final ModelPart root;
   private final ModelPart body;
   private final ModelPart head;
   private final ModelPart leftArm;
   private final ModelPart rightArm;
   private final ModelPart leftHand;
   private final ModelPart rightHand;
   private final ModelPart leftLeg;
   private final ModelPart rightLeg;
   private final boolean slim;
   public final List<String> allPartNames;
   public float alphaFactor = 1.0F;

   public TheGatekeeperModel(ModelPart root, boolean slim) {
      this.root = root.getChild("root");
      this.body = this.root.getChild("body");
      this.head = this.body.getChild("head");
      this.leftArm = this.body.getChild("left_arm");
      this.rightArm = this.body.getChild("right_arm");
      this.leftHand = this.leftArm.getChild("left_hand");
      this.rightHand = this.rightArm.getChild("right_hand");
      this.leftLeg = this.root.getChild("left_leg");
      this.rightLeg = this.root.getChild("right_leg");
      this.slim = slim;
      this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
   }

   public static LayerDefinition createBodyLayer(boolean slim) {
      return createBodyLayer(slim, 0.0F);
   }

   public static LayerDefinition createBodyLayer(boolean slim, float deformation) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(deformation)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "jacket",
         CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)),
         PartPose.offset(0.0F, 12.0F, 0.0F)
      );
      if (slim) {
         PartDefinition leftArm = body.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)),
            PartPose.offset(5.0F, -10.0F, 0.0F)
         );
         leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(0.5F, 8.0F, 0.0F));
         leftArm.addOrReplaceChild(
            "left_sleeve",
            CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
         PartDefinition rightArm = body.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)),
            PartPose.offset(-5.0F, -10.0F, 0.0F)
         );
         rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-0.5F, 8.0F, 0.0F));
         rightArm.addOrReplaceChild(
            "right_sleeve",
            CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
      } else {
         PartDefinition leftArm = body.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)),
            PartPose.offset(5.0F, -10.0F, 0.0F)
         );
         leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));
         leftArm.addOrReplaceChild(
            "left_sleeve",
            CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
         PartDefinition rightArm = body.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.01F)),
            PartPose.offset(-5.0F, -10.0F, 0.0F)
         );
         rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));
         rightArm.addOrReplaceChild(
            "right_sleeve",
            CubeListBuilder.create().texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F + 0.01F)),
            PartPose.offset(0.0F, 0.0F, 0.0F)
         );
      }

      PartDefinition head = body.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(deformation)),
         PartPose.offset(0.0F, -12.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "hat",
         CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(deformation + 0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition leftLeg = root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)),
         PartPose.offset(2.0F, 0.0F, 0.0F)
      );
      leftLeg.addOrReplaceChild(
         "left_pants",
         CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition rightLeg = root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation)),
         PartPose.offset(-2.0F, 0.0F, 0.0F)
      );
      rightLeg.addOrReplaceChild(
         "right_pants",
         CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(deformation + 0.25F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root.getAllParts().forEach(ModelPart::resetPose);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      if (!entity.isActivated()) {
         this.animate(entity.sitAnimationState, TheGatekeeperAnimation.SIT, ageInTicks);
         this.animate(entity.standAnimationState, TheGatekeeperAnimation.STAND, ageInTicks);
         this.animate(entity.talkAnimationState, TheGatekeeperAnimation.TALK, ageInTicks);
      } else if (entity.deathTime <= 0) {
         if (entity.getBehaviorState() != 1
            && entity.getBehaviorState() != 4
            && entity.getBehaviorState() != 5
            && entity.getBehaviorState() != 6
            && entity.getBehaviorState() != 7
            && entity.getBehaviorState() != 8) {
            this.animateWalk(TheGatekeeperAnimation.RUN, limbSwing, limbSwingAmount, 2.0F, 1.0F);
         }

         if (entity.getBehaviorTicks() >= 0 && entity.getBehaviorState() != 0) {
            int state = entity.getBehaviorState();
            switch (state) {
               case 1:
                  this.animate(entity.stepBackAnimationState, TheGatekeeperAnimation.STEP_BACK, ageInTicks);
                  break;
               case 2:
                  this.animate(entity.jumpStartAnimationState, TheGatekeeperAnimation.JUMP_START, ageInTicks);
                  break;
               case 3:
                  this.animate(entity.jumpTransitionAnimationState, TheGatekeeperAnimation.JUMP_TRANSITION, ageInTicks);
                  break;
               case 4:
                  this.animate(entity.jumpEndAnimationState, TheGatekeeperAnimation.JUMP_END, ageInTicks);
                  break;
               case 5:
                  this.animate(entity.greatswordAnimationState, TheGatekeeperAnimation.GREATSWORD, ageInTicks);
                  break;
               case 6:
                  this.animate(entity.hammerAnimationState, TheGatekeeperAnimation.HAMMER, ageInTicks);
                  break;
               case 7:
                  this.animate(entity.dashAnimationState, TheGatekeeperAnimation.DASH, ageInTicks);
                  break;
               case 8:
                  this.animate(entity.greatswordComboAnimationState, TheGatekeeperAnimation.GREATSWORD_COMBO, ageInTicks);
                  break;
               case 9:
                  this.animate(entity.bowAnimationState, TheGatekeeperAnimation.BOW, ageInTicks);
                  break;
               case 10:
                  this.animate(entity.bowComboAnimationState, TheGatekeeperAnimation.BOW_COMBO, ageInTicks);
                  break;
               case 11:
                  this.animate(entity.castFireballAnimationState, TheGatekeeperAnimation.MAGIC, ageInTicks);
                  break;
               case 12:
                  this.animate(entity.teleportAnimationState, TheGatekeeperAnimation.MAGIC, ageInTicks);
                  break;
               case 13:
                  this.animate(entity.eatAnimationState, TheGatekeeperAnimation.EAT, ageInTicks);
                  break;
               case 14:
                  this.animate(entity.eatFailAnimationState, TheGatekeeperAnimation.EAT_FAIL, ageInTicks);
            }
         } else {
            this.animate(entity.blockAnimationState, TheGatekeeperAnimation.BLOCK, ageInTicks);
         }
      }
   }

   public void translateToHand(HumanoidArm arm, PoseStack stack) {
      this.root.translateAndRotate(stack);
      this.body.translateAndRotate(stack);
      ModelPart armPart = arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
      ModelPart handPart = arm == HumanoidArm.LEFT ? this.leftHand : this.rightHand;
      armPart.translateAndRotate(stack);
      if (this.slim) {
         float f = 0.5F * (arm == HumanoidArm.RIGHT ? 1 : -1);
         handPart.x += f;
         handPart.translateAndRotate(stack);
         handPart.x -= f;
      } else {
         handPart.translateAndRotate(stack);
      }

      stack.translate(0.0, -0.55, 0.0);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public RenderType renderType(ResourceLocation resourceLocation) {
      return RenderType.entityTranslucent(resourceLocation);
   }

   @Override
   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      super.renderToBuffer(
         poseStack,
         vertexConsumer,
         packedLight,
         packedOverlay,
         ARGB32.color(Math.round(ARGB32.alpha(color) * this.alphaFactor), ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color))
      );
   }
}
