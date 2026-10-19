package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.google.common.collect.MapMaker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

public final class AnimationLoader extends SimpleJsonResourceReloadListener {
   public static final AnimationLoader INSTANCE = new AnimationLoader();
   private Map<ResourceLocation, AnimationHolder> animations = new MapMaker().weakValues().concurrencyLevel(1).makeMap();
   private final List<AnimationHolder> strongHolderReferences = new ArrayList<>();

   private AnimationLoader() {
      super(new Gson(), "friendsandfoes/animations/entity");
   }

   public Map<ResourceLocation, AnimationHolder> getAnimations() {
      return this.animations;
   }

   public void setAnimations(Map<ResourceLocation, AnimationHolder> animations) {
      this.animations = animations;
   }

   @Nullable
   public AnimationDefinition getAnimation(ResourceLocation key) {
      AnimationHolder holder = this.animations.get(key);
      return holder != null ? holder.getOrNull() : null;
   }

   public AnimationHolder getAnimationHolder(ResourceLocation key) {
      return this.animations.computeIfAbsent(key, AnimationHolder::new);
   }

   protected void apply(Map<ResourceLocation, JsonElement> entityAnimationsJson, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<ResourceLocation, AnimationDefinition> entityAnimations = new HashMap<>();

      for (Entry<ResourceLocation, JsonElement> entry : entityAnimationsJson.entrySet()) {
         ResourceLocation resourceLocation = entry.getKey();
         JsonElement animationDefinitionJson = entry.getValue();
         AnimationDefinition animationDefinition = (AnimationDefinition)AnimationParser.CODEC.parse(JsonOps.INSTANCE, animationDefinitionJson).getOrThrow();
         entityAnimations.put(resourceLocation, animationDefinition);
      }

      this.apply(entityAnimations);
   }

   public void apply(Map<ResourceLocation, AnimationDefinition> entityAnimations) {
      this.animations.values().forEach(AnimationHolder::unbind);
      this.strongHolderReferences.clear();
      int loaded = 0;

      for (Entry<ResourceLocation, AnimationDefinition> entry : entityAnimations.entrySet()) {
         try {
            String animationName = entry.getKey().getPath().substring(entry.getKey().getPath().lastIndexOf(47) + 1);
            AnimationHolder animationHolder = this.getAnimationHolder(entry.getKey());
            AnimationDefinition parsedAnimation = entry.getValue();
            AnimationDefinition animation = new AnimationDefinition(
               animationName, parsedAnimation.lengthInSeconds(), parsedAnimation.looping(), parsedAnimation.boneAnimations()
            );
            animationHolder.bind(animation);
            this.strongHolderReferences.add(animationHolder);
            loaded++;
         } catch (Exception e) {
            FriendsAndFoes.getLogger().error("Failed to load animation {}", entry.getKey(), e);
         }
      }

      FriendsAndFoes.getLogger().info("Loaded {} entity animations", loaded);
   }
}
