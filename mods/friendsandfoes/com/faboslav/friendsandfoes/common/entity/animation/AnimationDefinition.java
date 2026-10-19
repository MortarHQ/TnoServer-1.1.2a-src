package com.faboslav.friendsandfoes.common.entity.animation;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AnimationDefinition(String name, float lengthInSeconds, boolean looping, Map<String, List<AnimationChannel>> boneAnimations) {
   public float lengthInSeconds(float speedModifier) {
      return this.lengthInSeconds * speedModifier;
   }

   public int lengthInTicks() {
      return (int)Math.ceil(this.lengthInSeconds() * 20.0F) + 1;
   }

   public int lengthInTicks(float speedModifier) {
      return (int)Math.ceil(this.lengthInSeconds(speedModifier) * 20.0F) + 1;
   }

   public static class Builder {
      private final String name;
      private final float lengthInSeconds;
      private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
      private boolean looping;

      public Builder(String name, float lengthInSeconds) {
         this.name = name;
         this.lengthInSeconds = lengthInSeconds;
      }

      public static AnimationDefinition.Builder create(String name, float lengthInSeconds) {
         return new AnimationDefinition.Builder(name, lengthInSeconds);
      }

      public AnimationDefinition.Builder looping() {
         this.looping = true;
         return this;
      }

      public AnimationDefinition.Builder addAnimation(String string, AnimationChannel animationChannel) {
         this.animationByBone.computeIfAbsent(string, stringx -> new ArrayList<>()).add(animationChannel);
         return this;
      }

      public AnimationDefinition build() {
         return new AnimationDefinition(this.name, this.lengthInSeconds, this.looping, this.animationByBone);
      }
   }
}
