package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public final class AnimationHolder {
   public static final AnimationDefinition EMPTY_ANIMATION = new AnimationDefinition("empty", 0.0F, false, Map.of());
   private final ResourceLocation key;
   @Nullable
   private AnimationDefinition value;
   private boolean absentWarned;

   AnimationHolder(ResourceLocation key) {
      this.key = key;
   }

   void unbind() {
      this.value = null;
      this.absentWarned = false;
   }

   void bind(AnimationDefinition value) {
      this.value = value;
   }

   public ResourceLocation key() {
      return this.key;
   }

   public AnimationDefinition get() {
      AnimationDefinition result = this.value;
      if (result == null) {
         if (!this.absentWarned) {
            this.absentWarned = true;
            FriendsAndFoes.getLogger().warn("Missing entity animation {}", this.key);
         }

         return EMPTY_ANIMATION;
      } else {
         return result;
      }
   }

   @Nullable
   public AnimationDefinition getOrNull() {
      return this.value;
   }

   public boolean isBound() {
      return this.value != null;
   }
}
