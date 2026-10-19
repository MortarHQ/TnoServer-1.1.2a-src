package net.unusual.block_factorys_bosses.mixins.client;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.EasingType;
import software.bernie.geckolib.animation.keyframe.AnimationPoint;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeLocation;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(PlayerModel.class)
public abstract class PlayerAnimationMixin<T extends LivingEntity> extends HumanoidModel<T> {
   @Shadow
   @Final
   private ModelPart cloak;
   @Unique
   private ModelPart br$root = null;

   public PlayerAnimationMixin(ModelPart root) {
      super(root);
   }

   @Inject(method = "<init>", at = @At("TAIL"))
   private void injectInit(ModelPart root, boolean slim, CallbackInfo ci) {
      this.br$root = root;
   }

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
   private void setup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
      this.body.resetPose();
      this.head.resetPose();
      this.leftLeg.resetPose();
      this.rightLeg.resetPose();
      this.leftArm.resetPose();
      this.rightArm.resetPose();
      this.cloak.resetPose();
   }

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
   private void animateBones(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
      if (entity instanceof Player player) {
         Minecraft mc = Minecraft.getInstance();
         if (player != mc.player || mc.options.getCameraType() != CameraType.FIRST_PERSON) {
            PlayerAnimationHandler attachment = PlayerAnimationHandler.fromPlayer(player);
            int tick = attachment.getTick();
            if (tick > 0) {
               PlayerAnimationHandler.PlayerAnimation playerAnimation = attachment.getCurrentAnimation();
               if (playerAnimation != null) {
                  BakedAnimations bakedAnimations = (BakedAnimations)GeckoLibCache.getBakedAnimations().get(playerAnimation.path());
                  if (bakedAnimations != null) {
                     Animation animation = bakedAnimations.getAnimation(playerAnimation.name());
                     if (animation != null) {
                        float partialTick = mc.getTimer().getGameTimeDeltaPartialTick(false);
                        float factor = 1.0F - (tick - partialTick) / attachment.getDuration();
                        double adjustedTick = animation.length() * factor;
                        float lerp = Math.min(playerAnimation.lerp(), tick <= 3 && attachment.canLerp() ? (tick - partialTick) / 3.0F : 1.0F);
                        float anti = 1.0F - lerp;

                        for (BoneAnimation boneAnimation : animation.boneAnimations()) {
                           Vector3f rotations = br$getAnimationPointAtTick(boneAnimation.rotationKeyFrames(), adjustedTick, true);
                           Vector3f positions = br$getAnimationPointAtTick(boneAnimation.positionKeyFrames(), adjustedTick, false);

                           for (ModelPart bone : this.br$getPartsFor(boneAnimation.boneName())) {
                              if (rotations != null) {
                                 bone.xRot = rotations.x + bone.xRot * anti;
                                 bone.yRot = rotations.y + bone.yRot * anti;
                                 bone.zRot = rotations.z + bone.zRot * anti;
                              }

                              if (positions != null) {
                                 PartPose pose = bone.getInitialPose();
                                 bone.x = Mth.lerp(anti, positions.x + pose.x, bone.x);
                                 bone.y = Mth.lerp(anti, positions.y + pose.y, bone.y);
                                 bone.z = Mth.lerp(anti, positions.z + pose.z, bone.z);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Unique
   private List<ModelPart> br$getPartsFor(String name) {
      List<ModelPart> list = new ArrayList<>();
      if (this.br$root.hasChild(name)) {
         list.add(this.br$root.getChild(name));

         String other = switch (name) {
            case "body" -> "jacket";
            case "head" -> "hat";
            case "left_arm" -> "left_sleeve";
            case "right_arm" -> "right_sleeve";
            case "left_leg" -> "left_pants";
            case "right_leg" -> "right_pants";
            default -> null;
         };
         if (other != null && this.br$root.hasChild(other)) {
            list.add(this.br$root.getChild(other));
         }
      }

      return list;
   }

   @Unique
   @Nullable
   private static Vector3f br$getAnimationPointAtTick(KeyframeStack<Keyframe<MathValue>> keyFrames, double tick, boolean isRotation) {
      if (keyFrames.xKeyframes().isEmpty()) {
         return null;
      }

      Vector3f vec3f = new Vector3f();

      for (Axis axis : Axis.values()) {
         List<Keyframe<MathValue>> frames = switch (axis) {
            case X -> keyFrames.xKeyframes();
            case Y -> keyFrames.yKeyframes();
            case Z -> keyFrames.zKeyframes();
            default -> throw new MatchException(null, null);
         };
         KeyframeLocation<Keyframe<MathValue>> location = br$getCurrentKeyFrameLocation(frames, tick);
         Keyframe<MathValue> currentFrame = location.keyframe();
         double startValue = currentFrame.startValue().get();
         double endValue = currentFrame.endValue().get();
         if (isRotation) {
            if (!(currentFrame.startValue() instanceof Constant)) {
               startValue = Math.toRadians(startValue);
               if (axis == Axis.X || axis == Axis.Y) {
                  startValue *= -1.0;
               }
            }

            if (!(currentFrame.endValue() instanceof Constant)) {
               endValue = Math.toRadians(endValue);
               if (axis == Axis.X || axis == Axis.Y) {
                  endValue *= -1.0;
               }
            }
         }

         float var = (float)EasingType.lerpWithOverride(
            new AnimationPoint(currentFrame, location.startTick(), currentFrame.length(), startValue, endValue), null
         );
         if (axis == Axis.Y || isRotation && axis == Axis.X) {
            var *= -1.0F;
         }

         vec3f.setComponent(axis.ordinal(), var);
      }

      return vec3f;
   }

   @Unique
   private static KeyframeLocation<Keyframe<MathValue>> br$getCurrentKeyFrameLocation(List<Keyframe<MathValue>> frames, double ageInTicks) {
      double totalFrameTime = 0.0;

      for (Keyframe<MathValue> frame : frames) {
         totalFrameTime += frame.length();
         if (totalFrameTime > ageInTicks) {
            return new KeyframeLocation(frame, ageInTicks - (totalFrameTime - frame.length()));
         }
      }

      return new KeyframeLocation(frames.getLast(), ageInTicks);
   }
}
