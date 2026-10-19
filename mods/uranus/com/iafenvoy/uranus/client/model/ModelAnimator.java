package com.iafenvoy.uranus.client.model;

import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ModelAnimator {
   private final HashMap<AdvancedModelBox, com.iafenvoy.uranus.client.model.tabula.Transform> transformMap;
   private final HashMap<AdvancedModelBox, com.iafenvoy.uranus.client.model.tabula.Transform> prevTransformMap;
   private int tempTick = 0;
   private int prevTempTick;
   private boolean correctAnimation = false;
   private IAnimatedEntity entity;

   protected ModelAnimator() {
      this.transformMap = new HashMap<>();
      this.prevTransformMap = new HashMap<>();
   }

   public static ModelAnimator create() {
      return new ModelAnimator();
   }

   public IAnimatedEntity getEntity() {
      return this.entity;
   }

   public void startAnimate(@NotNull IAnimatedEntity entity) {
      this.tempTick = this.prevTempTick = 0;
      this.correctAnimation = false;
      this.entity = entity;
      this.transformMap.clear();
      this.prevTransformMap.clear();
   }

   public void endAnimate() {
      this.entity = null;
   }

   @Deprecated(forRemoval = true)
   public void update(IAnimatedEntity entity) {
      if (entity != null) {
         this.startAnimate(entity);
      } else {
         this.endAnimate();
      }
   }

   public boolean setAnimation(Animation animation) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      this.tempTick = this.prevTempTick = 0;
      this.correctAnimation = this.entity.getAnimation() == animation;
      return this.correctAnimation;
   }

   public void startKeyframe(int duration) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      if (this.correctAnimation) {
         this.prevTempTick = this.tempTick;
         this.tempTick += duration;
      }
   }

   public void setStaticKeyframe(int duration) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      this.startKeyframe(duration);
      this.endKeyframe(true);
   }

   public void resetKeyframe(int duration) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      this.startKeyframe(duration);
      this.endKeyframe();
   }

   public void rotate(AdvancedModelBox box, float x, float y, float z) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      if (this.correctAnimation) {
         this.getTransform(box).addRotation(x, y, z);
      }
   }

   public void move(AdvancedModelBox box, float x, float y, float z) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      if (this.correctAnimation) {
         this.getTransform(box).addOffset(x, y, z);
      }
   }

   private com.iafenvoy.uranus.client.model.tabula.Transform getTransform(AdvancedModelBox box) {
      return this.transformMap.computeIfAbsent(box, b -> new com.iafenvoy.uranus.client.model.tabula.Transform());
   }

   public void endKeyframe() {
      this.endKeyframe(false);
   }

   private void endKeyframe(boolean stationary) {
      if (this.entity == null) {
         throw new IllegalStateException("Not start animate yet!");
      }

      if (this.correctAnimation) {
         int animationTick = this.entity.getAnimationTick();
         if (animationTick >= this.prevTempTick && animationTick < this.tempTick) {
            if (stationary) {
               for (AdvancedModelBox box : this.prevTransformMap.keySet()) {
                  com.iafenvoy.uranus.client.model.tabula.Transform transform = this.prevTransformMap.get(box);
                  box.rotateAngleX = box.rotateAngleX + transform.getRotationX();
                  box.rotateAngleY = box.rotateAngleY + transform.getRotationY();
                  box.rotateAngleZ = box.rotateAngleZ + transform.getRotationZ();
                  box.rotationPointX = box.rotationPointX + transform.getOffsetX();
                  box.rotationPointY = box.rotationPointY + transform.getOffsetY();
                  box.rotationPointZ = box.rotationPointZ + transform.getOffsetZ();
               }
            } else {
               float tick = (animationTick - this.prevTempTick + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false))
                  / (this.tempTick - this.prevTempTick);
               float inc = Mth.sin((float)(tick * Math.PI / 2.0));
               float dec = 1.0F - inc;

               for (AdvancedModelBox box : this.prevTransformMap.keySet()) {
                  com.iafenvoy.uranus.client.model.tabula.Transform transform = this.prevTransformMap.get(box);
                  box.rotateAngleX = box.rotateAngleX + dec * transform.getRotationX();
                  box.rotateAngleY = box.rotateAngleY + dec * transform.getRotationY();
                  box.rotateAngleZ = box.rotateAngleZ + dec * transform.getRotationZ();
                  box.rotationPointX = box.rotationPointX + dec * transform.getOffsetX();
                  box.rotationPointY = box.rotationPointY + dec * transform.getOffsetY();
                  box.rotationPointZ = box.rotationPointZ + dec * transform.getOffsetZ();
               }

               for (AdvancedModelBox box : this.transformMap.keySet()) {
                  com.iafenvoy.uranus.client.model.tabula.Transform transform = this.transformMap.get(box);
                  box.rotateAngleX = box.rotateAngleX + inc * transform.getRotationX();
                  box.rotateAngleY = box.rotateAngleY + inc * transform.getRotationY();
                  box.rotateAngleZ = box.rotateAngleZ + inc * transform.getRotationZ();
                  box.rotationPointX = box.rotationPointX + inc * transform.getOffsetX();
                  box.rotationPointY = box.rotationPointY + inc * transform.getOffsetY();
                  box.rotationPointZ = box.rotationPointZ + inc * transform.getOffsetZ();
               }
            }
         }

         if (!stationary) {
            this.prevTransformMap.clear();
            this.prevTransformMap.putAll(this.transformMap);
            this.transformMap.clear();
         }
      }
   }
}
