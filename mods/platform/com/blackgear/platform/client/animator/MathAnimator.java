package com.blackgear.platform.client.animator;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public record MathAnimator(Map<String, List<AnimatedChannel>> animationsByBone) {
   public static void animate(HierarchicalModel<?> model, float animationProgress, MathAnimator builder) {
      float animTime = animationProgress / 20.0F;
      Vector3f vector = new Vector3f();

      for (Entry<String, List<AnimatedChannel>> animation : builder.animationsByBone().entrySet()) {
         Optional<ModelPart> entry = model.getAnyDescendantWithName(animation.getKey());
         if (!entry.isEmpty()) {
            ModelPart part = entry.get();

            for (AnimatedChannel channel : animation.getValue()) {
               for (AnimatedPoint point : channel.targets()) {
                  vector.set(point.getX(animTime), point.getY(animTime), point.getZ(animTime));
                  point.target().apply(part, vector);
               }
            }
         }
      }
   }

   public static void animate(AnimatedModel model, float animationProgress, MathAnimator builder) {
      float animTime = animationProgress / 20.0F;
      Vector3f vector = new Vector3f();

      for (Entry<String, List<AnimatedChannel>> animation : builder.animationsByBone().entrySet()) {
         Optional<ModelPart> entry = model.getAnyDescendantWithName(animation.getKey());
         if (!entry.isEmpty()) {
            ModelPart part = entry.get();

            for (AnimatedChannel channel : animation.getValue()) {
               for (AnimatedPoint point : channel.targets()) {
                  vector.set(point.getX(animTime), point.getY(animTime), point.getZ(animTime));
                  point.target().apply(part, vector);
               }
            }
         }
      }
   }

   public static class Builder {
      private final Map<String, List<AnimatedChannel>> animationByBone = Maps.newHashMap();

      public MathAnimator.Builder addAnimation(String bone, AnimatedChannel animation) {
         this.animationByBone.computeIfAbsent(bone, key -> new ArrayList<>()).add(animation);
         return this;
      }

      public MathAnimator build() {
         Map<String, List<AnimatedChannel>> map = new HashMap<>();
         this.animationByBone.forEach((key, channels) -> map.put(key, List.copyOf((Collection<? extends AnimatedChannel>)channels)));
         return new MathAnimator(Map.copyOf(map));
      }
   }
}
