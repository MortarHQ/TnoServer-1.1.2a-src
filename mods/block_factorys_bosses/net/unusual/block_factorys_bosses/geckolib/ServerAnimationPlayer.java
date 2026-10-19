package net.unusual.block_factorys_bosses.geckolib;

import com.mojang.math.Axis;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ServerAnimationPlayer<T extends Entity & GeoEntity> {
   private GeoModel<T> model;
   private final T animatable;

   public ServerAnimationPlayer(GeoModel<T> model, T animatable) {
      this.model = model;
      this.animatable = animatable;
   }

   private Entity getEntity() {
      return this.animatable;
   }

   public GeoModel<T> getModel() {
      return this.model;
   }

   public void setModel(GeoModel<T> model) {
      this.model = model;
   }

   public void triggerAnim(@Nullable String controllerName, String animName) {
      Entity entity = this.getEntity();
      if (!entity.level().isClientSide()) {
         GeckoLibServices.NETWORK.triggerEntityAnim(entity, false, controllerName, animName);
      }

      if (controllerName != null) {
         ((GeoAnimatable)this.animatable).getAnimatableInstanceCache().getManagerForId(entity.getId()).tryTriggerAnimation(controllerName, animName);
      } else {
         ((GeoAnimatable)this.animatable).getAnimatableInstanceCache().getManagerForId(entity.getId()).tryTriggerAnimation(animName);
      }
   }

   public void stopTriggeredAnim(@Nullable String controllerName, @Nullable String animName) {
      Entity entity = this.getEntity();
      if (!entity.level().isClientSide()) {
         GeckoLibServices.NETWORK.stopTriggeredEntityAnim(entity, false, controllerName, animName);
      }

      AnimatableManager<GeoAnimatable> animatableManager = ((GeoAnimatable)this.animatable).getAnimatableInstanceCache().getManagerForId(entity.getId());
      if (animatableManager != null) {
         if (controllerName != null) {
            animatableManager.stopTriggeredAnimation(controllerName, animName);
         } else {
            animatableManager.stopTriggeredAnimation(animName);
         }
      }
   }

   public static <T extends Entity & GeoEntity> void retriggerAnim(
      @Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, String animName
   ) {
      stopTriggeredAnim(serverAnimationPlayer, controllerName, animName);
      triggerAnim(serverAnimationPlayer, controllerName, animName);
   }

   public static <T extends Entity & GeoEntity> void triggerAnim(
      @Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, String animName
   ) {
      if (serverAnimationPlayer != null) {
         serverAnimationPlayer.triggerAnim(controllerName, animName);
      }
   }

   public static <T extends Entity & GeoEntity> void stopTriggeredAnim(
      @Nullable ServerAnimationPlayer<T> serverAnimationPlayer, @Nullable String controllerName, @Nullable String animName
   ) {
      if (serverAnimationPlayer != null) {
         serverAnimationPlayer.stopTriggeredAnim(controllerName, animName);
      }
   }

   public void tickAnimations() {
      Entity entity = this.getEntity();
      this.model.getBakedModel(this.model.getModelResource(this.animatable));
      AnimatableManager<T> manager = ((GeoAnimatable)this.animatable).getAnimatableInstanceCache().getManagerForId(entity.getId());
      AnimationState<T> frameState = new AnimationState(this.animatable, 0.0F, 0.0F, 0.0F, false);
      double animTime = entity.tickCount;
      if (manager.getFirstTickTime() == -1.0) {
         manager.startedAt(animTime);
      }

      manager.updatedAt(animTime);
      frameState.animationTick = animTime;
      AnimationProcessor<T> processor = this.model.getAnimationProcessor();
      processor.preAnimationSetup(frameState, animTime);
      if (!processor.getRegisteredBones().isEmpty()) {
         processor.tickAnimation(this.animatable, this.model, manager, animTime, frameState, false);
      }
   }

   private void applyBoneTransformations(ServerAnimationPlayer.ServerPoseStack poseStack, GeoBone bone) {
      GeoBone parent = bone.getParent();
      if (parent != null) {
         this.applyBoneTransformations(poseStack, parent);
      }

      poseStack.translate(-bone.getPosX() / 16.0F, bone.getPosY() / 16.0F, bone.getPosZ() / 16.0F);
      poseStack.translate(bone.getPivotX() / 16.0F, bone.getPivotY() / 16.0F, bone.getPivotZ() / 16.0F);
      if (bone.getRotZ() != 0.0F) {
         poseStack.mulPose(Axis.ZP.rotation(bone.getRotZ()));
      }

      if (bone.getRotY() != 0.0F) {
         poseStack.mulPose(Axis.YP.rotation(bone.getRotY()));
      }

      if (bone.getRotX() != 0.0F) {
         poseStack.mulPose(Axis.XP.rotation(bone.getRotX()));
      }

      poseStack.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
      poseStack.translate(-bone.getPivotX() / 16.0F, -bone.getPivotY() / 16.0F, -bone.getPivotZ() / 16.0F);
   }

   public void applyWorldSpaceTransformations(ServerAnimationPlayer.ServerPoseStack poseStack) {
      if (this.animatable instanceof ServerAnimationPlayer.CustomTransformationsEntity tentacle) {
         tentacle.applyCustomWorldSpaceTransformations(poseStack);
      }

      poseStack.translate(this.animatable.xo, this.animatable.yo, this.animatable.zo);
      if (this.animatable instanceof LivingEntity livingEntity) {
         float entityScale = livingEntity.getScale();
         poseStack.scale(entityScale, entityScale, entityScale);
         poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - livingEntity.yBodyRot));
      }
   }

   public GeoBone getBone(String boneName) {
      AnimationProcessor<T> processor = this.model.getAnimationProcessor();
      return Objects.requireNonNull(processor.getBone(boneName));
   }

   public Vector3f getBonePosition(ServerAnimationPlayer.ServerPoseStack poseStack, GeoBone bone) {
      poseStack.push();
      this.applyBoneTransformations(poseStack, bone);
      Vector3f position = poseStack.last().transformPosition(new Vector3f(bone.getPivotX(), bone.getPivotY(), bone.getPivotZ()).div(16.0F));
      poseStack.pop();
      return position;
   }

   public Vector3f getBoneWorldPosition(String boneName) {
      ServerAnimationPlayer.ServerPoseStack poseStack = new ServerAnimationPlayer.ServerPoseStack();
      this.applyWorldSpaceTransformations(poseStack);
      GeoBone bone = this.getBone(boneName);
      return this.getBonePosition(poseStack, bone);
   }

   public interface CustomTransformationsEntity {
      void applyCustomWorldSpaceTransformations(ServerAnimationPlayer.ServerPoseStack var1);
   }

   public static class ServerPoseStack implements CommonPoseStack {
      private final Deque<Matrix4f> stack = new ArrayDeque<>();

      public ServerPoseStack() {
         this.stack.addLast(new Matrix4f());
      }

      public void push() {
         this.stack.addLast(new Matrix4f((Matrix4fc)this.stack.getLast()));
      }

      public void pop() {
         this.stack.removeLast();
      }

      public void translate(double x, double y, double z) {
         this.translate((float)x, (float)y, (float)z);
      }

      public void translate(float x, float y, float z) {
         this.stack.getLast().translate(x, y, z);
      }

      public void scale(float x, float y, float z) {
         this.stack.getLast().scale(x, y, z);
      }

      public void mulPose(Matrix4f pose) {
         this.stack.getLast().mul(pose);
      }

      public void mulPose(Quaternionf quaternion) {
         this.stack.getLast().rotate(quaternion);
      }

      public Matrix4f last() {
         return this.stack.getLast();
      }

      @Override
      public void bosses_rise_java$push() {
         this.push();
      }

      @Override
      public void bosses_rise_java$pop() {
         this.pop();
      }

      @Override
      public void bosses_rise_java$translate(double x, double y, double z) {
         this.translate(x, y, z);
      }

      @Override
      public void bosses_rise_java$translate(float x, float y, float z) {
         this.translate(x, y, z);
      }

      @Override
      public void bosses_rise_java$scale(float x, float y, float z) {
         this.scale(x, y, z);
      }

      @Override
      public void bosses_rise_java$mulPose(Matrix4f pose) {
         this.mulPose(pose);
      }

      @Override
      public void bosses_rise_java$mulPose(Quaternionf quaternion) {
         this.mulPose(quaternion);
      }

      @Override
      public Matrix4f bosses_rise_java$last() {
         return this.last();
      }
   }
}
