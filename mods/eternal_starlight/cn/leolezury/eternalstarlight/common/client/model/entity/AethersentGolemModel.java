package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.AethersentGolemAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.AethersentGolem;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class AethersentGolemModel<T extends AethersentGolem> extends AnimatedEntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aethersent_golem"), "main");
   private final ModelPart root;
   private final ModelPart upper;
   private final ModelPart body;
   private final ModelPart leftArm;
   private final ModelPart leftMuzzle;
   private final ModelPart rightArm;
   private final ModelPart rightMuzzle;
   private final ModelPart head;
   private final ModelPart eye;
   private final ModelPart lower;

   public AethersentGolemModel(ModelPart root) {
      this.root = root;
      this.upper = root.getChild("upper");
      this.body = this.upper.getChild("body");
      this.leftArm = this.body.getChild("left_arm");
      this.leftMuzzle = this.leftArm.getChild("left_muzzle");
      this.rightArm = this.body.getChild("right_arm");
      this.rightMuzzle = this.rightArm.getChild("right_muzzle");
      this.head = this.upper.getChild("head");
      this.eye = this.head.getChild("eye");
      this.lower = root.getChild("lower");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition upper = partdefinition.addOrReplaceChild("upper", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));
      PartDefinition body = upper.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-3.0F, -10.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(24, 22)
            .addBox(-5.0F, -8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(24, 22)
            .mirror()
            .addBox(3.0F, -8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .mirror(false),
         PartPose.offset(0.0F, -8.0F, 0.0F)
      );
      PartDefinition leftArm = body.addOrReplaceChild(
         "left_arm",
         CubeListBuilder.create().texOffs(32, 16).mirror().addBox(0.0F, -4.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(5.0F, -7.0F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      leftArm.addOrReplaceChild("left_muzzle", CubeListBuilder.create(), PartPose.offset(3.0F, 8.0F, 0.0F));
      PartDefinition rightArm = body.addOrReplaceChild(
         "right_arm",
         CubeListBuilder.create().texOffs(32, 16).addBox(-6.0F, -4.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -7.0F, 0.0F, -1.5708F, 0.0F, 0.0F)
      );
      rightArm.addOrReplaceChild("right_muzzle", CubeListBuilder.create(), PartPose.offset(-3.0F, 8.0F, 0.0F));
      PartDefinition head = upper.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -18.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "eye",
         CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -3.25F, -4.05F)
      );
      PartDefinition antenna = head.addOrReplaceChild(
         "antenna",
         CubeListBuilder.create().texOffs(32, 4).addBox(-2.0F, -2.5F, -1.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.0F, -3.5F, -0.5F)
      );
      antenna.addOrReplaceChild(
         "upper_antenna",
         CubeListBuilder.create().texOffs(44, 7).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-0.5F, -2.5F, 0.5F)
      );
      partdefinition.addOrReplaceChild(
         "lower",
         CubeListBuilder.create()
            .texOffs(0, 44)
            .addBox(-7.0F, -3.0F, -7.0F, 14.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(0, 32)
            .addBox(-2.0F, -11.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.animate(entity.shootAnimationState, AethersentGolemAnimation.SHOOT, ageInTicks);
      this.animate(entity.shootAnimationState, AethersentGolemAnimation.ANTENNA_DETECTED, ageInTicks);
      this.animate(entity.shootEndAnimationState, AethersentGolemAnimation.SHOOT_END, ageInTicks);
      this.leftArm.xRot += headPitch * (float) (Math.PI / 180.0);
      this.rightArm.xRot += headPitch * (float) (Math.PI / 180.0);
      if (entity.useLeftHand) {
         this.rightArm.resetPose();
      } else {
         this.leftArm.resetPose();
      }

      this.lower.yRot = -Mth.rotLerp(Mth.frac(ageInTicks), entity.yBodyRotO, entity.yBodyRot) * (float) (Math.PI / 180.0);
      if (!entity.shootAnimationState.isStarted()) {
         Entity camera = Minecraft.getInstance().getCameraEntity();
         if (camera != null) {
            Vec3 targetEyePos = camera.getEyePosition(0.0F);
            Vec3 golemEyePos = entity.getEyePosition(0.0F);
            if (targetEyePos.y > golemEyePos.y) {
               this.eye.y -= 0.6F;
            }

            Vec3 golemView = entity.getViewVector(0.0F);
            golemView = new Vec3(golemView.x, 0.0, golemView.z);
            Vec3 eyeDiff = new Vec3(golemEyePos.x - targetEyePos.x, 0.0, golemEyePos.z - targetEyePos.z).normalize().yRot((float) (Math.PI / 2));
            double e = golemView.dot(eyeDiff);
            this.eye.x = this.eye.x + Mth.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
         }
      }

      if (entity.shootAnimationState.isStarted() && !entity.shootPosTracked) {
         entity.leftMuzzlePos = ESModelUtil.getModelPartWorldPosition(
            entity, Mth.lerp(Mth.frac(ageInTicks), entity.yBodyRotO, entity.yBodyRot), List.of(this.upper, this.body, this.leftArm, this.leftMuzzle)
         );
         entity.rightMuzzlePos = ESModelUtil.getModelPartWorldPosition(
            entity, Mth.lerp(Mth.frac(ageInTicks), entity.yBodyRotO, entity.yBodyRot), List.of(this.upper, this.body, this.rightArm, this.rightMuzzle)
         );
         entity.shootPosTracked = true;
         entity.shouldAddShootParticle = true;
      }
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
