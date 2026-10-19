package toni.lib.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Generated;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import toni.lib.animation.easing.EasingType;
import toni.lib.animation.effects.IAnimationEffect;
import toni.lib.utils.ColorUtils;

public class AnimationTimeline {
   public final float duration;
   private float current;
   private AnimationKeyframe effected_keyframe = new AnimationKeyframe();
   private AnimationKeyframe keyframe = new AnimationKeyframe();
   private boolean hasSortedTransitions = false;
   private final HashMap<Binding, List<Transition>> transitions = new HashMap<>();
   private final HashMap<Binding, List<AnimationEffect>> effects = new HashMap<>();
   private final Binding[] bindings = Binding.values();

   public static AnimationTimeline builder(float duration) {
      return new AnimationTimeline(duration);
   }

   private AnimationTimeline(float duration) {
      this.duration = duration;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeFloat(this.duration);
      buffer.writeFloat(this.current);
      this.effected_keyframe.encode(buffer);
      this.keyframe.encode(buffer);
      buffer.writeMap(this.transitions, FriendlyByteBuf::writeEnum, (buf, val) -> {
         buf.writeVarInt(val.size());
         val.forEach(i -> i.encode(buf));
      });
      buffer.writeMap(this.effects, FriendlyByteBuf::writeEnum, (buf, val) -> {
         buf.writeVarInt(val.size());
         val.forEach(i -> i.encode(buf));
      });
   }

   public static AnimationTimeline decode(FriendlyByteBuf buffer) {
      float duration = buffer.readFloat();
      float current = buffer.readFloat();
      AnimationKeyframe effected_keyframe = AnimationKeyframe.decode(buffer);
      AnimationKeyframe keyframe = AnimationKeyframe.decode(buffer);
      Map<Binding, ArrayList<Transition>> transitions = buffer.readMap(enumClass -> (Binding)buffer.readEnum(Binding.class), buf -> {
         int size = buffer.readVarInt();
         ArrayList<Transition> list = new ArrayList<>();

         for (int i = 0; i < size; i++) {
            list.add(Transition.decode(buf));
         }

         return list;
      });
      Map<Binding, ArrayList<AnimationEffect>> effects = buffer.readMap(enumClass -> (Binding)buffer.readEnum(Binding.class), buf -> {
         int size = buffer.readVarInt();
         ArrayList<AnimationEffect> list = new ArrayList<>();

         for (int i = 0; i < size; i++) {
            list.add(AnimationEffect.decode(buf));
         }

         return list;
      });
      AnimationTimeline ths = new AnimationTimeline(duration);
      ths.current = current;
      ths.effected_keyframe = effected_keyframe;
      ths.keyframe = keyframe;
      ths.transitions.putAll(transitions);
      ths.effects.putAll(effects);
      return ths;
   }

   public AnimationKeyframe getKeyframe() {
      if (!this.hasSortedTransitions) {
         this.hasSortedTransitions = true;

         for (Entry<Binding, List<Transition>> kvp : this.transitions.entrySet()) {
            kvp.getValue().sort((a, b) -> Float.compare(a.getOut(), b.getOut()));
         }
      }

      for (Entry<Binding, List<Transition>> kvp : this.transitions.entrySet()) {
         float value = kvp.getValue().get(0).getStartValue();

         for (Transition transition : kvp.getValue()) {
            switch (transition.getPosition(this.current)) {
               case DURING:
                  value = transition.eval(kvp.getKey(), this.current);
                  break;
               case AFTER:
                  value = transition.getEndValue();
            }
         }

         this.keyframe.setValue(kvp.getKey(), value);
      }

      for (Binding key : this.bindings) {
         if (!this.effects.containsKey(key)) {
            this.effected_keyframe.setValue(key, this.keyframe.getValue(key));
         } else {
            for (AnimationEffect effect : this.effects.get(key)) {
               if (effect.getPosition(this.current) == IAnimationEffect.Position.DURING) {
                  this.effected_keyframe.setValue(key, this.keyframe.getValue(key) + effect.type.calculate(effect, this.current));
               }
            }
         }
      }

      return this.effected_keyframe;
   }

   public AnimationKeyframe applyPose(GuiGraphics context, float objectWidth, float objectHeight) {
      AnimationKeyframe key = this.getKeyframe();
      PoseUtils.applyScale(context, key.size);
      PoseUtils.applyPosition(context, key.size, objectWidth, objectHeight, key.posX, key.posY, key.posZ);
      PoseUtils.applyYRotation(context, key.size, objectWidth, objectHeight, key.rotY);
      PoseUtils.applyXRotation(context, key.size, objectWidth, objectHeight, key.rotX);
      PoseUtils.applyZRotation(context, key.size, objectWidth, objectHeight, key.rotZ);
      return key;
   }

   public int getColor() {
      AnimationKeyframe keyframe = this.getKeyframe();
      return ColorUtils.color(
         Mth.clamp((int)(keyframe.alpha * 255.0F + 5.0F), 0, 255),
         ColorUtils.red((int)keyframe.color),
         ColorUtils.green((int)keyframe.color),
         ColorUtils.blue((int)keyframe.color)
      );
   }

   public AnimationTimeline advancePlayhead(float delta) {
      this.current = Math.max(0.0F, Math.min(this.current + delta, this.duration));
      return this;
   }

   public AnimationTimeline resetPlayhead(float newPosition) {
      this.current = Math.max(0.0F, Math.min(newPosition, this.duration));
      return this;
   }

   public AnimationTimeline withColor(int alpha, int red, int green, int blue) {
      this.keyframe.color = ColorUtils.color(alpha, red, green, blue);
      return this;
   }

   public AnimationTimeline withXPosition(float x) {
      this.keyframe.posX = x;
      return this;
   }

   public AnimationTimeline withYPosition(float y) {
      this.keyframe.posY = y;
      return this;
   }

   public AnimationTimeline withZPosition(float z) {
      this.keyframe.posZ = z;
      return this;
   }

   public AnimationTimeline withXRotation(float x) {
      this.keyframe.rotX = x;
      return this;
   }

   public AnimationTimeline withYRotation(float y) {
      this.keyframe.rotY = y;
      return this;
   }

   public AnimationTimeline withZRotation(float z) {
      this.keyframe.rotZ = z;
      return this;
   }

   public AnimationTimeline withSize(float size) {
      this.keyframe.size = size;
      return this;
   }

   public AnimationTimeline transition(Binding binding, float in, float out, float start, float end, EasingType easing) {
      Transition transition = new Transition(in, out, easing, start, end);
      if (!this.transitions.containsKey(binding)) {
         this.transitions.put(binding, new ArrayList<>());
      }

      this.transitions.get(binding).add(transition);
      return this;
   }

   public AnimationTimeline fadeout(float time) {
      return this.transition(Binding.Alpha, this.duration - time, this.duration, 1.0F, 0.0F, EasingType.EaseOutSine);
   }

   public AnimationTimeline fadeout(float time, EasingType easing) {
      return this.transition(Binding.Alpha, this.duration - time, this.duration, 1.0F, 0.0F, easing);
   }

   public AnimationTimeline fadein(float time) {
      return this.transition(Binding.Alpha, 0.0F, time, 0.0F, 1.0F, EasingType.EaseOutSine);
   }

   public AnimationTimeline fadein(float time, EasingType easing) {
      return this.transition(Binding.Alpha, 0.0F, time, 0.0F, 1.0F, easing);
   }

   public AnimationTimeline shake(float time) {
      this.transition(Binding.zRot, time, time + 0.1F, 0.0F, 5.0F, EasingType.EaseInBounce);
      this.transition(Binding.Size, time, time + 0.1F, 1.0F, 1.1F, EasingType.EaseInCubic);
      this.transition(Binding.Size, time + 0.1F, time + 0.2F, 1.0F, 1.0F, EasingType.EaseOutCubic);
      return this.transition(Binding.zRot, time, time + 0.2F, 5.0F, 0.0F, EasingType.EaseOutBounce);
   }

   public AnimationTimeline waveEffect(Binding binding, float intensity, float speed, float in, float out) {
      if (!this.effects.containsKey(binding)) {
         this.effects.put(binding, new ArrayList<>());
      }

      this.effects.get(binding).add(new AnimationEffect(in, out, intensity, speed, AnimationEffect.Type.WAVE));
      return this;
   }

   public AnimationTimeline waveEffect(Binding binding, float intensity, float speed) {
      return this.waveEffect(binding, intensity, speed, 0.0F, this.duration);
   }

   public AnimationTimeline waveEffect() {
      return this.waveEffect(Binding.zRot, 2.5F, 5.0F, 0.0F, this.duration);
   }

   @Generated
   public float getCurrent() {
      return this.current;
   }

   @Generated
   public void setCurrent(float current) {
      this.current = current;
   }
}
