package com.finderfeed.fdlib.systems.bedrock.animations.animation_system;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.AnimationContext;
import com.finderfeed.fdlib.systems.bedrock.animations.TransitionAnimation;
import com.finderfeed.fdlib.util.FDByteBufCodecs;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AnimationTicker {
   public static final StreamCodec<FriendlyByteBuf, AnimationTicker> NO_NEXT_NETWORK_CODEC = FDByteBufCodecs.composite(
      ByteBufCodecs.FLOAT,
      ticker -> ticker.elapsedTime,
      ByteBufCodecs.FLOAT,
      ticker -> ticker.speedModifier,
      ByteBufCodecs.INT,
      ticker -> ticker.toNullTransitionTime,
      ByteBufCodecs.BOOL,
      ticker -> ticker.reversed,
      ByteBufCodecs.STRING_UTF8,
      ticker -> ticker.loopMode.name(),
      ByteBufCodecs.STRING_UTF8,
      ticker -> ticker.animation.getName().toString(),
      ByteBufCodecs.BOOL,
      v -> v.important,
      (elapsedTime, speedModifier, toNull, reversed, loopModeName, animationName, important) -> {
         ResourceLocation location = ResourceLocation.tryParse(animationName);
         Animation animation = (Animation)FDRegistries.ANIMATIONS.get(location);
         if (animation == null) {
            throw new RuntimeException("Unknown animation received from server: " + animationName);
         }

         Animation.LoopMode mode = Animation.LoopMode.valueOf(loopModeName);
         AnimationTicker ticker = new AnimationTicker(animation);
         ticker.loopMode = mode;
         ticker.reversed = reversed;
         ticker.elapsedTime = elapsedTime;
         ticker.speedModifier = speedModifier;
         ticker.toNullTransitionTime = toNull;
         ticker.important = important;
         return ticker;
      }
   );
   public static final StreamCodec<FriendlyByteBuf, AnimationTicker> NETWORK_CODEC = new StreamCodec<FriendlyByteBuf, AnimationTicker>() {
      public AnimationTicker decode(FriendlyByteBuf buf) {
         boolean hasNext = buf.readBoolean();
         AnimationTicker next = null;
         if (hasNext) {
            next = (AnimationTicker)AnimationTicker.NETWORK_CODEC.decode(buf);
         }

         AnimationTicker thisTicker = (AnimationTicker)AnimationTicker.NO_NEXT_NETWORK_CODEC.decode(buf);
         thisTicker.next = next;
         return thisTicker;
      }

      public void encode(FriendlyByteBuf buf, AnimationTicker ticker) {
         AnimationTicker next = ticker.next;
         buf.writeBoolean(next != null);
         if (next != null) {
            AnimationTicker.NETWORK_CODEC.encode(buf, next);
         }

         AnimationTicker.NO_NEXT_NETWORK_CODEC.encode(buf, ticker);
      }
   };
   private AnimationTicker next = null;
   private float elapsedTime = 0.0F;
   private float speedModifier = 1.0F;
   private int toNullTransitionTime;
   private boolean reversed;
   private Animation.LoopMode loopMode;
   private Animation animation;
   private boolean important = false;

   public AnimationTicker(AnimationTicker other) {
      this.elapsedTime = other.getElapsedTime();
      this.speedModifier = other.speedModifier;
      this.toNullTransitionTime = other.toNullTransitionTime;
      this.loopMode = other.loopMode;
      this.animation = other.getAnimation();
      this.reversed = other.reversed;
      this.next = other.next;
      this.important = other.important;
   }

   public AnimationTicker(Animation animation) {
      this.animation = animation;
      this.toNullTransitionTime = animation.getAnimTime();
      this.loopMode = animation.getDefaultLoopMode();
   }

   public void tick() {
      if (this.getLoopMode() != Animation.LoopMode.LOOP) {
         this.elapsedTime = Mth.clamp(this.elapsedTime + this.speedModifier, 0.0F, this.animation.getAnimTime());
      } else {
         this.elapsedTime = this.elapsedTime + this.speedModifier;
         if (this.animation instanceof TransitionAnimation transitionAnimation && this.elapsedTime >= this.animation.getAnimTime()) {
            this.animation = transitionAnimation.getTransitionTo();
         }

         this.elapsedTime = this.elapsedTime % this.animation.getAnimTime();
      }
   }

   public void resetTime() {
      this.elapsedTime = 0.0F;
   }

   public void addVariables(AnimationContext context, float partialTicks) {
      context.variables.put("query.anim_time", (this.elapsedTime + partialTicks * this.speedModifier) / 20.0F);
      context.variables.put("math.pi", (float) Math.PI);
   }

   public boolean hasEnded() {
      return this.loopMode == Animation.LoopMode.LOOP ? false : this.elapsedTime == this.animation.getAnimTime();
   }

   public boolean isImportant() {
      return this.important;
   }

   public AnimationTicker getNext() {
      return this.next;
   }

   public boolean isReversed() {
      return this.reversed;
   }

   public Animation getAnimation() {
      return this.animation;
   }

   public float getElapsedTime() {
      return this.elapsedTime;
   }

   public float getTime(float partialTicks) {
      partialTicks *= this.getSpeedModifier();
      if (this.loopMode != Animation.LoopMode.LOOP) {
         return this.reversed
            ? Mth.clamp(this.animation.getAnimTime() - this.getElapsedTime() - partialTicks, 0.0F, this.animation.getAnimTime())
            : Mth.clamp(this.getElapsedTime() + partialTicks, 0.0F, this.animation.getAnimTime());
      } else if (this.reversed) {
         float time = this.animation.getAnimTime() - this.getElapsedTime() - partialTicks;
         return time < 0.0F ? this.animation.getAnimTime() + time : time;
      } else {
         return (this.getElapsedTime() + partialTicks) % this.animation.getAnimTime();
      }
   }

   public float getSpeedModifier() {
      return this.speedModifier;
   }

   public int getToNullTransitionTime() {
      return this.toNullTransitionTime;
   }

   public Animation.LoopMode getLoopMode() {
      return this.loopMode;
   }

   public void setAnimation(Animation animation) {
      this.animation = animation;
   }

   public void setSpeedModifier(float speedModifier) {
      this.speedModifier = speedModifier;
   }

   public void setLoopMode(Animation.LoopMode loopMode) {
      this.loopMode = loopMode;
   }

   public void setToNullTransitionTime(int toNullTransitionTime) {
      this.toNullTransitionTime = toNullTransitionTime;
   }

   public static AnimationTicker.Builder builder(Animation animation) {
      return new AnimationTicker.Builder(animation);
   }

   public static AnimationTicker.Builder builder(Supplier<Animation> animation) {
      return new AnimationTicker.Builder(animation.get());
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         AnimationTicker that = (AnimationTicker)o;
         return this.animation.equals(that.animation)
            && that.toNullTransitionTime == this.toNullTransitionTime
            && this.loopMode == that.loopMode
            && Float.compare(this.speedModifier, that.speedModifier) == 0
            && this.reversed == that.reversed
            && Objects.equals(this.next, that.next);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.elapsedTime, this.speedModifier, this.toNullTransitionTime, this.loopMode, this.animation);
   }

   public static class Builder {
      private AnimationTicker ticker;

      public Builder(Animation animation) {
         this.ticker = new AnimationTicker(animation);
      }

      public AnimationTicker.Builder important() {
         this.ticker.important = true;
         return this;
      }

      public AnimationTicker.Builder nextAnimation(AnimationTicker ticker) {
         this.ticker.next = ticker;
         return this;
      }

      public AnimationTicker.Builder setLoopMode(Animation.LoopMode mode) {
         this.ticker.loopMode = mode;
         return this;
      }

      public AnimationTicker.Builder setToNullTransitionTime(int time) {
         this.ticker.toNullTransitionTime = time;
         return this;
      }

      public AnimationTicker.Builder setSpeed(float modifier) {
         this.ticker.speedModifier = modifier;
         return this;
      }

      public AnimationTicker.Builder startTime(float startTime) {
         this.ticker.elapsedTime = Mth.clamp(startTime, 0.0F, this.ticker.animation.getAnimTime());
         return this;
      }

      public AnimationTicker.Builder reversed() {
         this.ticker.reversed = true;
         return this;
      }

      public AnimationTicker build() {
         return this.ticker;
      }
   }
}
