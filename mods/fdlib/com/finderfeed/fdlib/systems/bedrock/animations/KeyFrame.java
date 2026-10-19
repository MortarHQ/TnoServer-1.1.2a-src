package com.finderfeed.fdlib.systems.bedrock.animations;

import com.finderfeed.fdlib.shunting_yard.RPNVector3f;
import com.finderfeed.fdlib.shunting_yard.sy_base.ExpressionContext;
import org.joml.Vector3f;

public class KeyFrame {
   public RPNVector3f post;
   public RPNVector3f pre;
   public int time;
   public InterpolationMode interpolationMode;

   public KeyFrame(RPNVector3f post, int time, InterpolationMode mode) {
      this.post = post;
      this.time = time;
      this.interpolationMode = mode;
   }

   public KeyFrame(RPNVector3f pre, RPNVector3f post, int time, InterpolationMode mode) {
      this.pre = pre;
      this.post = post;
      this.time = time;
      this.interpolationMode = mode;
   }

   public Vector3f getPreValue(ExpressionContext context) {
      return this.pre == null ? null : this.pre.get(context);
   }

   public Vector3f getPostValue(ExpressionContext context) {
      return this.post == null ? null : this.post.get(context);
   }
}
