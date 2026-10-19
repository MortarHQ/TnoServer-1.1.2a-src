package com.blamejared.crafttweaker.api.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager.LoadResult;

public class MutableLoadResult<T> {
   private LoadResult<T> result;

   public MutableLoadResult() {
   }

   public MutableLoadResult(LoadResult<T> result) {
      this.result = result;
   }

   public void addTag(ResourceLocation id, Collection<Holder<T>> tag) {
      this.tagMap().put(id, tag);
   }

   public ResourceKey<? extends Registry<T>> key() {
      Objects.requireNonNull(this.result, "Cannot get key before the result has been bound!");
      return this.result.key();
   }

   public Map<ResourceLocation, Collection<Holder<T>>> tagMap() {
      Objects.requireNonNull(this.result, "Cannot get tagMap before the result has been bound!");
      return this.result.tags();
   }

   public LoadResult<T> result() {
      Objects.requireNonNull(this.result, "Cannot get result before it has been bound!");
      return this.result;
   }

   public void bind(LoadResult<T> result) {
      if (this.result != null) {
         throw new IllegalStateException("Unable to bind a MutableLoadResult twice!");
      }

      this.result = result;
      this.tagMap().replaceAll((key, value) -> new ArrayList<>((Collection<? extends Holder<T>>)value));
   }
}
