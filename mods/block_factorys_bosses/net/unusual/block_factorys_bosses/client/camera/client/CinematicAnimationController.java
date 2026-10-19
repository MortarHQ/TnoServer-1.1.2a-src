package net.unusual.block_factorys_bosses.client.camera.client;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController.AnimationStateHandler;
import software.bernie.geckolib.animation.AnimationController.CustomKeyframeHandler;
import software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class CinematicAnimationController<T extends GeoAnimatable> extends AnimationController<T> implements CustomKeyframeHandler<T> {
   private CustomKeyframeHandler<T> customKeyframeHandler;
   @Nullable
   private CinematicAnimationController.AnimationEndListener<T> animationEndListener;

   public CinematicAnimationController(T animatable, AnimationStateHandler<T> animationHandler) {
      this(animatable, "base_controller", animationHandler);
   }

   public CinematicAnimationController(T animatable, String name, AnimationStateHandler<T> animationHandler) {
      this(animatable, name, 0, animationHandler);
   }

   public CinematicAnimationController(T animatable, int transitionTickTime, AnimationStateHandler<T> animationHandler) {
      this(animatable, "base_controller", transitionTickTime, animationHandler);
   }

   public CinematicAnimationController(T animatable, String name, int transitionTickTime, AnimationStateHandler<T> animationHandler) {
      super(animatable, name, transitionTickTime, animationHandler);
      super.setCustomInstructionKeyframeHandler(this);
      this.setAnimationEndListener((animatable1, animation) -> {});
   }

   public CinematicAnimationController(
      T animatable,
      String name,
      int transitionTickTime,
      AnimationStateHandler<T> animationHandler,
      CinematicAnimationController.AnimationEndListener<T> animationEndListener
   ) {
      super(animatable, name, transitionTickTime, animationHandler);
      super.setCustomInstructionKeyframeHandler(this);
      this.setAnimationEndListener(animationEndListener);
   }

   public CinematicAnimationController<T> setAnimationEndListener(@Nullable CinematicAnimationController.AnimationEndListener<T> listener) {
      this.animationEndListener = listener;
      return this;
   }

   public void process(
      GeoModel<T> model,
      AnimationState<T> state,
      Map<String, GeoBone> bones,
      Map<String, BoneSnapshot> snapshots,
      double seekTime,
      boolean crashWhenCantFindBone
   ) {
      RawAnimation triggeredBefore = this.getTriggeredAnimation();
      super.process(model, state, bones, snapshots, seekTime, crashWhenCantFindBone);
      if (this.animationEndListener != null && triggeredBefore != null && this.getTriggeredAnimation() == null) {
         this.animationEndListener.onAnimationEnd((T)this.animatable, triggeredBefore);
      }
   }

   protected boolean stopTriggeredAnimation() {
      RawAnimation endingAnim = this.getTriggeredAnimation();
      boolean stopped = super.stopTriggeredAnimation();
      if (stopped && this.animationEndListener != null) {
         this.animationEndListener.onAnimationEnd((T)this.animatable, endingAnim);
      }

      return stopped;
   }

   public AnimationController<T> setCustomInstructionKeyframeHandler(CustomKeyframeHandler<T> customKeyframeHandler) {
      this.customKeyframeHandler = customKeyframeHandler;
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   public void handle(CustomInstructionKeyframeEvent<T> event) {
      CustomInstructionKeyframeData data = event.getKeyframeData();
      if (data != null) {
         if (!data.getInstructions().startsWith("cinematic_")) {
            if (this.customKeyframeHandler != null) {
               this.customKeyframeHandler.handle(event);
            }
         } else {
            String[] splits = data.getInstructions().split(";");
            if (splits.length >= 2 && !splits[1].isEmpty()) {
               if (splits[0].endsWith("start")) {
                  String cameraID = splits.length > 2 ? splits[2] : null;
                  if (this.animatable instanceof CinematicEntity cinematicEntity) {
                     BossesRiseClientCinematicCamera.startCinematicCamera(splits[1], cinematicEntity, cameraID);
                  }
               } else {
                  BossesRiseClientCinematicCamera.stopCinematicCamera(splits[1], (CinematicEntity)this.animatable);
               }
            } else {
               if (splits[0].endsWith("end")) {
                  BossesRiseClientCinematicCamera.stopCinematicCamera((CinematicEntity)this.animatable);
               }
            }
         }
      }
   }

   @FunctionalInterface
   public interface AnimationEndListener<T extends GeoAnimatable> {
      default void onAnimationEnd(T animatable, @Nullable RawAnimation animation) {
         this.executeOnAnimationEnd(animatable, animation);
         Level level = null;
         if (animatable instanceof Entity e) {
            level = e.level();
         }

         if (animatable instanceof BlockEntity be) {
            level = be.getLevel();
         }

         if (level != null) {
            if (level.isClientSide()) {
               BossesRiseClientCinematicCamera.stopCinematicCamera();
            }
         }
      }

      void executeOnAnimationEnd(T var1, @Nullable RawAnimation var2);
   }
}
