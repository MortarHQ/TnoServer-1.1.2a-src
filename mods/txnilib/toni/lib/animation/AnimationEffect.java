package toni.lib.animation;

import lombok.Generated;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import toni.lib.animation.effects.IAnimationEffect;

public class AnimationEffect implements IAnimationEffect {
   public float in;
   public float out;
   public float intensity;
   public float speed;
   public AnimationEffect.Type type;

   public AnimationEffect(float in, float out, float intensity, float speed, AnimationEffect.Type type) {
      this.in = in;
      this.out = out;
      this.type = type;
      this.intensity = intensity;
      this.speed = speed;
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeFloat(this.in);
      buf.writeFloat(this.out);
      buf.writeFloat(this.intensity);
      buf.writeFloat(this.speed);
      buf.writeEnum(this.type);
   }

   public static AnimationEffect decode(FriendlyByteBuf buf) {
      float in = buf.readFloat();
      float out = buf.readFloat();
      float intensity = buf.readFloat();
      float speed = buf.readFloat();
      AnimationEffect.Type type = (AnimationEffect.Type)buf.readEnum(AnimationEffect.Type.class);
      return new AnimationEffect(in, out, intensity, speed, type);
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
   public float getIntensity() {
      return this.intensity;
   }

   @Generated
   public void setIntensity(float intensity) {
      this.intensity = intensity;
   }

   @Generated
   public float getSpeed() {
      return this.speed;
   }

   @Generated
   public void setSpeed(float speed) {
      this.speed = speed;
   }

   public enum Type implements IAnimationFunction {
      WAVE {
         @Override
         public float calculate(AnimationEffect effect, float time) {
            return effect.intensity * Mth.cos(effect.speed * time);
         }
      };
   }
}
