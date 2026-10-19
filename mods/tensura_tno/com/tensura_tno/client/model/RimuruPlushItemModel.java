package com.tensura_tno.client.model;

import com.tensura_tno.item.RimuruPlushItem;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.keyframe.Keyframe;
import software.bernie.geckolib.animation.keyframe.KeyframeStack;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.loading.math.MathValue;

public class RimuruPlushItemModel extends RimuruPlushModel<RimuruPlushItem> {
   private final ResourceLocation itemModel;
   private final String animationPrefix;

   public RimuruPlushItemModel() {
      this("rimuru_plush", "animation.rimuru_tempest.");
   }

   public RimuruPlushItemModel(String assetName, String animationPrefix) {
      super(assetName);
      this.itemModel = ResourceLocation.fromNamespaceAndPath("tensura_tno", "geo/item/" + assetName + ".geo.json");
      this.animationPrefix = animationPrefix;
   }

   public ResourceLocation getModelResource(RimuruPlushItem item) {
      return this.itemModel;
   }

   public void handleAnimations(RimuruPlushItem item, long instanceId, AnimationState<RimuruPlushItem> state, float partialTick) {
      this.applyStaticPose(item, (ItemDisplayContext)state.getData(DataTickets.ITEM_RENDER_PERSPECTIVE));
   }

   protected void applyStaticPose(RimuruPlushItem item, ItemDisplayContext context) {
      String pose = context == ItemDisplayContext.GUI ? "inventory_pose" : "held_pose";
      Animation animation = this.getAnimation(item, this.animationPrefix + pose);

      for (BoneAnimation animated : animation.boneAnimations()) {
         GeoBone bone = this.getAnimationProcessor().getBone(animated.boneName());
         if (bone != null) {
            BoneSnapshot initial = bone.getInitialSnapshot();
            KeyframeStack<Keyframe<MathValue>> rotation = animated.rotationKeyFrames();
            KeyframeStack<Keyframe<MathValue>> position = animated.positionKeyFrames();
            KeyframeStack<Keyframe<MathValue>> scale = animated.scaleKeyFrames();
            bone.updateRotation(
               initial.getRotX() + value(rotation.xKeyframes()),
               initial.getRotY() + value(rotation.yKeyframes()),
               initial.getRotZ() + value(rotation.zKeyframes())
            );
            bone.updatePosition(value(position.xKeyframes()), value(position.yKeyframes()), value(position.zKeyframes()));
            bone.updateScale(value(scale.xKeyframes()), value(scale.yKeyframes()), value(scale.zKeyframes()));
         }
      }
   }

   private static float value(List<Keyframe<MathValue>> frames) {
      return (float)frames.getFirst().endValue().get();
   }
}
