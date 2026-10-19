package com.bobmowzie.mowziesmobs.client.model.tools;

import com.ilexiconn.llibrary.client.model.Transform;
import com.ilexiconn.llibrary.client.model.tools.BasicModelRenderer;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.HashMap;
import net.minecraft.util.Mth;

public class MMModelAnimator {
   private int tempTick = 0;
   private int prevTempTick;
   private float delta;
   private boolean correctAnimation = false;
   private IAnimatedEntity entity;
   private final HashMap<BasicModelRenderer, Transform> transformMap = new HashMap<>();
   private final HashMap<BasicModelRenderer, Transform> prevTransformMap = new HashMap<>();

   private MMModelAnimator() {
   }

   public static MMModelAnimator create() {
      return new MMModelAnimator();
   }

   public IAnimatedEntity getEntity() {
      return this.entity;
   }

   public void update(IAnimatedEntity entity, float delta) {
      this.tempTick = this.prevTempTick = 0;
      this.delta = delta;
      this.correctAnimation = false;
      this.entity = entity;
      this.transformMap.clear();
      this.prevTransformMap.clear();
   }

   public boolean setAnimation(Animation animation) {
      this.tempTick = this.prevTempTick = 0;
      if (this.entity != null) {
         this.correctAnimation = this.entity.getAnimation() == animation;
      }

      return this.correctAnimation;
   }

   public void startKeyframe(int duration) {
      if (this.correctAnimation) {
         this.prevTempTick = this.tempTick;
         this.tempTick += duration;
      }
   }

   public void setStaticKeyframe(int duration) {
      this.startKeyframe(duration);
      this.endKeyframe(true);
   }

   public void resetKeyframe(int duration) {
      this.startKeyframe(duration);
      this.endKeyframe();
   }

   public void rotate(BasicModelRenderer box, float x, float y, float z) {
      if (this.correctAnimation) {
         this.getTransform(box).addRotation(x, y, z);
      }
   }

   public void move(BasicModelRenderer box, float x, float y, float z) {
      if (this.correctAnimation) {
         this.getTransform(box).addOffset(x, y, z);
      }
   }

   private Transform getTransform(BasicModelRenderer box) {
      return this.transformMap.computeIfAbsent(box, b -> new Transform());
   }

   public void endKeyframe() {
      this.endKeyframe(false);
   }

   private void endKeyframe(boolean stationary) {
      if (this.correctAnimation) {
         int animationTick = this.entity.getAnimationTick();
         if (animationTick >= this.prevTempTick && animationTick < this.tempTick) {
            if (stationary) {
               for (BasicModelRenderer box : this.prevTransformMap.keySet()) {
                  Transform transform = this.prevTransformMap.get(box);
                  box.rotateAngleX = box.rotateAngleX + transform.getRotationX();
                  box.rotateAngleY = box.rotateAngleY + transform.getRotationY();
                  box.rotateAngleZ = box.rotateAngleZ + transform.getRotationZ();
                  box.rotationPointX = box.rotationPointX + transform.getOffsetX();
                  box.rotationPointY = box.rotationPointY + transform.getOffsetY();
                  box.rotationPointZ = box.rotationPointZ + transform.getOffsetZ();
               }
            } else {
               float tick = (animationTick - this.prevTempTick + this.delta) / (this.tempTick - this.prevTempTick);
               float inc = Mth.sin((float)(tick * Math.PI / 2.0));
               float dec = 1.0F - inc;

               for (BasicModelRenderer box : this.prevTransformMap.keySet()) {
                  Transform transform = this.prevTransformMap.get(box);
                  box.rotateAngleX = box.rotateAngleX + dec * transform.getRotationX();
                  box.rotateAngleY = box.rotateAngleY + dec * transform.getRotationY();
                  box.rotateAngleZ = box.rotateAngleZ + dec * transform.getRotationZ();
                  box.rotationPointX = box.rotationPointX + dec * transform.getOffsetX();
                  box.rotationPointY = box.rotationPointY + dec * transform.getOffsetY();
                  box.rotationPointZ = box.rotationPointZ + dec * transform.getOffsetZ();
               }

               for (BasicModelRenderer box : this.transformMap.keySet()) {
                  Transform transform = this.transformMap.get(box);
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
