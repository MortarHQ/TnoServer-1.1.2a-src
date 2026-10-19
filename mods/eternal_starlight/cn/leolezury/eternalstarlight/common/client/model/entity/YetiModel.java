package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.YetiAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Yeti;
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

public class YetiModel<T extends Yeti> extends AnimatedEntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("yeti"), "main");
   private final ModelPart root;
   private final ModelPart eye;
   private final ModelPart fur;
   private final ModelPart body;

   public YetiModel(ModelPart root) {
      this.root = root.getChild("root");
      this.fur = root.getChild("root").getChild("head").getChild("fur");
      this.body = root.getChild("root").getChild("head").getChild("body");
      this.eye = root.getChild("root").getChild("head").getChild("eye");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));
      root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.5F, 2.0F, 1.0F)
      );
      root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-2.5F, 2.0F, 1.0F)
      );
      PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.5F, 5.0F, -2.0F));
      head.addOrReplaceChild(
         "nose",
         CubeListBuilder.create().texOffs(31, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -8.0F, -1.0F)
      );
      head.addOrReplaceChild(
         "eye",
         CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -10.0F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 3.0F)
      );
      head.addOrReplaceChild(
         "body",
         CubeListBuilder.create()
            .texOffs(2, 1)
            .addBox(-5.0F, -16.0F, -1.0F, 9.0F, 13.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(9, 8)
            .addBox(-5.0F, -16.0F, -2.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "fur",
         CubeListBuilder.create()
            .texOffs(0, 34)
            .addBox(-6.0F, -16.0F, -1.0F, 11.0F, 13.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(8, 42)
            .addBox(-6.0F, -16.0F, -2.0F, 11.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.fur.visible = entity.hasFur();
      this.body.visible = !entity.hasFur();
      this.animate(entity.idleAnimationState, YetiAnimation.IDLE, ageInTicks);
      if (entity.getRollState() != 2) {
         this.animateWalk(YetiAnimation.WALK, limbSwing, limbSwingAmount, this.young ? 3.0F : 5.0F, 1.0F);
      }

      this.root.xRot = 0.0F;
      if (this.young) {
         this.root.xScale = 0.8F;
         this.root.yScale = 0.8F;
         this.root.zScale = 0.8F;
      }

      switch (entity.getRollState()) {
         case 1: {
            this.animate(entity.rollStartAnimationState, YetiAnimation.ROLL_START, ageInTicks);
            float rollAngle = Mth.rotLerp(
               Minecraft.getInstance()
                  .getTimer()
                  .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
               entity.prevRollAngle,
               entity.rollAngle
            );
            this.root.xRot = rollAngle * (float) (Math.PI / 180.0);
            break;
         }
         case 2: {
            this.animate(entity.rollAnimationState, YetiAnimation.ROLL, ageInTicks);
            float rollAngle = Mth.rotLerp(
               Minecraft.getInstance()
                  .getTimer()
                  .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
               entity.prevRollAngle,
               entity.rollAngle
            );
            this.root.xRot = rollAngle * (float) (Math.PI / 180.0);
            break;
         }
         case 3: {
            this.animate(entity.rollEndAnimationState, YetiAnimation.ROLL_END, ageInTicks);
            float progress = Math.min(10.0F, (float)entity.rollEndAnimationState.getAccumulatedTime() / 1000.0F * 20.0F) / 10.0F;
            float rollAngle = Mth.rotLerp(progress, entity.rollAngle, 0.0F);
            this.root.xRot = rollAngle * (float) (Math.PI / 180.0);
         }
      }

      Entity camera = Minecraft.getInstance().getCameraEntity();
      if (camera != null) {
         Vec3 targetEyePos = camera.getEyePosition(0.0F);
         Vec3 yetiEyePos = entity.getEyePosition(0.0F);
         if (targetEyePos.y > yetiEyePos.y) {
            this.eye.y--;
         }

         Vec3 yetiView = entity.getViewVector(0.0F);
         yetiView = new Vec3(yetiView.x, 0.0, yetiView.z);
         Vec3 eyeDiff = new Vec3(yetiEyePos.x - targetEyePos.x, 0.0, yetiEyePos.z - targetEyePos.z).normalize().yRot((float) (Math.PI / 2));
         double e = yetiView.dot(eyeDiff);
         this.eye.x = this.eye.x + Mth.sqrt((float)Math.abs(e)) * 2.0F * (float)Math.signum(e);
      }
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
