package toni.lib.animation;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import lombok.Generated;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import toni.lib.animation.easing.EasingFunctionArgs;
import toni.lib.animation.easing.EasingManager;
import toni.lib.animation.easing.EasingType;
import toni.lib.animation.effects.IAnimationEffect;
import toni.lib.utils.ColorUtils;

public class Transition implements IAnimationEffect {
   private AnimationTimeline parent;
   private float in;
   private float out;
   private float startValue;
   private float endValue;
   private EasingType easingFunctionTypeCache;
   private Double easingFunctionArgCache;
   private Double2DoubleFunction easingFunction;

   public void encode(FriendlyByteBuf buf) {
      buf.writeFloat(this.in);
      buf.writeFloat(this.out);
      buf.writeFloat(this.startValue);
      buf.writeFloat(this.endValue);
      buf.writeEnum(this.easingFunctionTypeCache);
      buf.writeDouble(this.easingFunctionArgCache);
   }

   public static Transition decode(FriendlyByteBuf buf) {
      float in = buf.readFloat();
      float out = buf.readFloat();
      float startValue = buf.readFloat();
      float endValue = buf.readFloat();
      EasingType easingFunctionTypeCache = (EasingType)buf.readEnum(EasingType.class);
      double easingFunctionArgCache = buf.readDouble();
      return new Transition(in, out, easingFunctionTypeCache, startValue, endValue, easingFunctionArgCache);
   }

   public Transition(float in, float out, EasingType easing, float start, float end) {
      this.in = in;
      this.out = out;
      this.startValue = start;
      this.endValue = end;
      this.easingFunctionTypeCache = easing;
      this.easingFunctionArgCache = 1.0;
      this.easingFunction = EasingManager.getEasingFunction.apply(new EasingFunctionArgs(easing, null));
   }

   public Transition(float in, float out, EasingType easing, float start, float end, double elasticity) {
      this.in = in;
      this.out = out;
      this.startValue = start;
      this.endValue = end;
      this.easingFunctionTypeCache = easing;
      this.easingFunctionArgCache = elasticity;
      this.easingFunction = EasingManager.getEasingFunction.apply(new EasingFunctionArgs(easing, elasticity));
   }

   public float eval(Binding binding, float current) {
      double lerp = Mth.clamp((double)(current - this.in) / (this.out - this.in), 0.0, 1.0);
      lerp = (Double)this.easingFunction.apply(lerp);
      lerp = Mth.clamp(lerp, 0.0, 1.0);
      return binding == Binding.Color
         ? ColorUtils.lerp((float)lerp, (int)this.startValue, (int)this.endValue)
         : (float)Mth.lerp(lerp, this.startValue, this.endValue);
   }

   @Generated
   @Override
   public float getIn() {
      return this.in;
   }

   @Generated
   public void setIn(float in) {
      this.in = in;
   }

   @Generated
   @Override
   public float getOut() {
      return this.out;
   }

   @Generated
   public void setOut(float out) {
      this.out = out;
   }

   @Generated
   public float getStartValue() {
      return this.startValue;
   }

   @Generated
   public void setStartValue(float startValue) {
      this.startValue = startValue;
   }

   @Generated
   public float getEndValue() {
      return this.endValue;
   }

   @Generated
   public void setEndValue(float endValue) {
      this.endValue = endValue;
   }
}
