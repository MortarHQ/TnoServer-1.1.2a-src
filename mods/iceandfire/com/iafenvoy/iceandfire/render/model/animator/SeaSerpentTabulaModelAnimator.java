package com.iafenvoy.iceandfire.render.model.animator;

import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.render.model.util.SeaSerpentAnimations;
import com.iafenvoy.uranus.client.model.AdvancedModelBox;
import com.iafenvoy.uranus.client.model.ITabulaModelAnimator;
import com.iafenvoy.uranus.client.model.TabulaModel;
import com.iafenvoy.uranus.client.model.util.TabulaModelHandlerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class SeaSerpentTabulaModelAnimator extends IceAndFireTabulaModelAnimator<SeaSerpentEntity> implements ITabulaModelAnimator<SeaSerpentEntity> {
   public final SeaSerpentAnimations[] swimPose = new SeaSerpentAnimations[]{
      SeaSerpentAnimations.SWIM1, SeaSerpentAnimations.SWIM3, SeaSerpentAnimations.SWIM4, SeaSerpentAnimations.SWIM6
   };

   public SeaSerpentTabulaModelAnimator() {
      super(resolve(SeaSerpentAnimations.T_POSE.getModelId()));
   }

   public void setRotationAngles(
      TabulaModel<SeaSerpentEntity> model,
      SeaSerpentEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float rotationYaw,
      float rotationPitch,
      float scale
   ) {
      model.resetToDefaultPose();
      model.getCube("BodyUpper").rotationPointY += 9.0F;
      model.animator.startAnimate(entity);
      this.animate(model, entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale);
      int currentIndex = entity.swimCycle / 10;
      int prevIndex = currentIndex - 1;
      if (prevIndex < 0) {
         prevIndex = 3;
      }

      TabulaModel<SeaSerpentEntity> prevPosition = resolve(this.swimPose[prevIndex].getModelId());
      TabulaModel<SeaSerpentEntity> currentPosition = resolve(this.swimPose[currentIndex].getModelId());
      if (prevPosition != null && currentPosition != null) {
         float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
         float delta = entity.swimCycle / 10.0F % 1.0F + partialTicks / 10.0F;

         for (AdvancedModelBox cube : model.getCubes().values()) {
            if (entity.jumpProgress > 0.0F && !this.isRotationEqual(cube, resolve(SeaSerpentAnimations.JUMPING2.getModelId()).getCube(cube.boxName))) {
               this.transitionTo(cube, resolve(SeaSerpentAnimations.JUMPING2.getModelId()).getCube(cube.boxName), entity.jumpProgress, 5.0F, false);
            }

            if (entity.wantJumpProgress > 0.0F && !this.isRotationEqual(cube, resolve(SeaSerpentAnimations.JUMPING1.getModelId()).getCube(cube.boxName))) {
               this.transitionTo(cube, resolve(SeaSerpentAnimations.JUMPING1.getModelId()).getCube(cube.boxName), entity.wantJumpProgress, 10.0F, false);
            }

            AdvancedModelBox prevPositionCube = prevPosition.getCube(cube.boxName);
            AdvancedModelBox currPositionCube = currentPosition.getCube(cube.boxName);
            float prevX = prevPositionCube.rotateAngleX;
            float prevY = prevPositionCube.rotateAngleY;
            float prevZ = prevPositionCube.rotateAngleZ;
            float x = currPositionCube.rotateAngleX;
            float y = currPositionCube.rotateAngleY;
            float z = currPositionCube.rotateAngleZ;
            this.addToRotateAngle(
               cube, limbSwingAmount, prevX + delta * this.distance(prevX, x), prevY + delta * this.distance(prevY, y), prevZ + delta * this.distance(prevZ, z)
            );
         }

         if (entity.breathProgress > 0.0F) {
            this.progressRotation(model.getCube("Head"), entity.breathProgress, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
            this.progressRotation(model.getCube("HeadFront"), entity.breathProgress, (float)Math.toRadians(-20.0), 0.0F, 0.0F);
            this.progressRotation(model.getCube("Jaw"), entity.breathProgress, (float)Math.toRadians(60.0), 0.0F, 0.0F);
         }

         if (entity.jumpRot > 0.0F) {
            float jumpRot = entity.prevJumpRot + (entity.jumpRot - entity.prevJumpRot) * partialTicks;
            float turn = (float)entity.getDeltaMovement().y * -4.0F;
            AdvancedModelBox var28 = model.getCube("BodyUpper");
            var28.rotateAngleX = var28.rotateAngleX + (float)Math.toRadians(22.5F * turn) * jumpRot;
            var28 = model.getCube("Tail1");
            var28.rotateAngleX = var28.rotateAngleX - (float)Math.toRadians(turn) * jumpRot;
            var28 = model.getCube("Tail2");
            var28.rotateAngleX = var28.rotateAngleX - (float)Math.toRadians(turn) * jumpRot;
            var28 = model.getCube("Tail3");
            var28.rotateAngleX = var28.rotateAngleX - (float)Math.toRadians(turn) * jumpRot;
            var28 = model.getCube("Tail4");
            var28.rotateAngleX = var28.rotateAngleX - (float)Math.toRadians(turn) * jumpRot;
         }

         float prevRenderOffset = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks;
         AdvancedModelBox var33 = model.getCube("Tail1");
         var33.rotateAngleY = var33.rotateAngleY + (entity.getPieceYaw(1, partialTicks) - prevRenderOffset) * (float) (Math.PI / 180.0);
         var33 = model.getCube("Tail2");
         var33.rotateAngleY = var33.rotateAngleY + (entity.getPieceYaw(2, partialTicks) - prevRenderOffset) * (float) (Math.PI / 180.0);
         var33 = model.getCube("Tail3");
         var33.rotateAngleY = var33.rotateAngleY + (entity.getPieceYaw(3, partialTicks) - prevRenderOffset) * (float) (Math.PI / 180.0);
         var33 = model.getCube("Tail4");
         var33.rotateAngleY = var33.rotateAngleY + (entity.getPieceYaw(4, partialTicks) - prevRenderOffset) * (float) (Math.PI / 180.0);
         model.getCube("BodyUpper").rotateAngleX -= rotationPitch * (float) (Math.PI / 180.0);
         if (!entity.isJumpingOutOfWater() || entity.isInWater()) {
            var33 = model.getCube("Tail1");
            var33.rotateAngleX = var33.rotateAngleX - (entity.getPiecePitch(1, partialTicks) - 0.0F) * (float) (Math.PI / 180.0);
            var33 = model.getCube("Tail2");
            var33.rotateAngleX = var33.rotateAngleX - (entity.getPiecePitch(2, partialTicks) - 0.0F) * (float) (Math.PI / 180.0);
            var33 = model.getCube("Tail3");
            var33.rotateAngleX = var33.rotateAngleX - (entity.getPiecePitch(3, partialTicks) - 0.0F) * (float) (Math.PI / 180.0);
            var33 = model.getCube("Tail4");
            var33.rotateAngleX = var33.rotateAngleX - (entity.getPiecePitch(4, partialTicks) - 0.0F) * (float) (Math.PI / 180.0);
         }

         model.animator.endAnimate();
      }
   }

   public void progressRotation(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ) {
      model.rotateAngleX = model.rotateAngleX + progress * (rotX - model.defaultRotationX) / 20.0F;
      model.rotateAngleY = model.rotateAngleY + progress * (rotY - model.defaultRotationY) / 20.0F;
      model.rotateAngleZ = model.rotateAngleZ + progress * (rotZ - model.defaultRotationZ) / 20.0F;
   }

   private void animate(
      TabulaModel<SeaSerpentEntity> model,
      SeaSerpentEntity entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float rotationYaw,
      float rotationPitch,
      float scale
   ) {
      if (model.animator.setAnimation(SeaSerpentEntity.ANIMATION_SPEAK)) {
         model.animator.startKeyframe(5);
         this.rotate(model.animator, model.getCube("Jaw"), 25.0F, 0.0F, 0.0F);
         model.animator.endKeyframe();
         model.animator.setStaticKeyframe(5);
         model.animator.resetKeyframe(5);
      }

      if (model.animator.setAnimation(SeaSerpentEntity.ANIMATION_BITE)) {
         model.animator.startKeyframe(5);
         this.moveToPose(model, resolve(SeaSerpentAnimations.BITE1.getModelId()));
         model.animator.endKeyframe();
         model.animator.startKeyframe(5);
         this.moveToPose(model, resolve(SeaSerpentAnimations.BITE2.getModelId()));
         model.animator.endKeyframe();
         model.animator.setStaticKeyframe(2);
         model.animator.resetKeyframe(3);
      }

      if (model.animator.setAnimation(SeaSerpentEntity.ANIMATION_ROAR)) {
         model.animator.startKeyframe(10);
         this.moveToPose(model, resolve(SeaSerpentAnimations.ROAR1.getModelId()));
         model.animator.endKeyframe();
         model.animator.startKeyframe(10);
         this.moveToPose(model, resolve(SeaSerpentAnimations.ROAR2.getModelId()));
         model.animator.endKeyframe();
         model.animator.startKeyframe(10);
         this.moveToPose(model, resolve(SeaSerpentAnimations.ROAR3.getModelId()));
         model.animator.endKeyframe();
         model.animator.resetKeyframe(10);
      }
   }

   private static TabulaModel<SeaSerpentEntity> resolve(ResourceLocation id) {
      return TabulaModelHandlerHelper.getModel(id);
   }
}
