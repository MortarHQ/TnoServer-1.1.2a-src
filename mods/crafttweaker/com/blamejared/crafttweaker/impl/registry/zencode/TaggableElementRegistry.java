package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TaggableElementRegistry {
   private final Map<ResourceKey<?>, TagManagerFactory<?, ?>> managerData = new HashMap<>();
   private final Map<ResourceKey<?>, Class<?>> elementData = new HashMap<>();

   public <T, U extends ITagManager<?>> void registerManager(ResourceKey<T> id, TagManagerFactory<T, U> factory) {
      TagManagerFactory<?, ?> old = this.managerData.get(id);
      if (old != null) {
         throw new IllegalArgumentException("Attempted taggable element manager overriding on id " + id + ": old " + old + ", new " + factory);
      }

      this.managerData.put(id, factory);
   }

   public <T> void registerElement(ResourceKey<T> id, Class<T> clazz) {
      Class<?> old = this.elementData.get(id);
      if (old != null) {
         throw new IllegalArgumentException(
            "Attempted taggable element overriding on id " + id + ": old " + old.getCanonicalName() + ", new " + clazz.getCanonicalName()
         );
      }

      this.elementData.put(id, clazz);
   }

   public <T> Optional<Class<T>> getTaggableElement(ResourceKey<T> key) {
      return Optional.ofNullable(this.elementData.get(key)).map(it -> (Class<T>)it);
   }

   public Map<ResourceKey<?>, Class<?>> getTaggableElements() {
      return ImmutableMap.copyOf(this.elementData);
   }

   public <T> Optional<TagManagerFactory<T, ? extends ITagManager<?>>> getManagerFactory(ResourceKey<Registry<T>> key) {
      return Optional.ofNullable(this.managerData.get(key)).map(it -> (TagManagerFactory<T, ? extends ITagManager<?>>)it);
   }

   public Map<ResourceKey<?>, TagManagerFactory<?, ?>> getManagers() {
      return ImmutableMap.copyOf(this.managerData);
   }
}
