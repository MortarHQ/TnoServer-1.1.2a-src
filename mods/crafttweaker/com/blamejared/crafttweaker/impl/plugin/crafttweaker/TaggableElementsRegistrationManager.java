package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.TaggableElementManagerFactory;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

final class TaggableElementsRegistrationManager {
   void attemptRegistration(Class<?> clazz, ITaggableElementRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(NativeTypeRegistration.class)) {
         this.tryNativeRegistration(clazz, handler);
      } else {
         this.tryClassRegistration(clazz, handler);
      }
   }

   private void tryClassRegistration(Class<?> clazz, ITaggableElementRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(TaggableElement.class)) {
         this.tryElementRegistration(clazz, clazz.getAnnotation(TaggableElement.class), handler);
      }

      if (clazz.isAnnotationPresent(TaggableElementManagerFactory.class)) {
         if (!TagManagerFactory.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Provided manager factory class: '" + clazz + "' is not an instance of TagManagerFactory!");
         }

         this.tryManagerRegistration(clazz, clazz.getAnnotation(TaggableElementManagerFactory.class), handler);
      }
   }

   private void tryNativeRegistration(Class<?> clazz, ITaggableElementRegistrationHandler handler) {
      if (clazz.isAnnotationPresent(TaggableElement.class)) {
         NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
         Class<?> nativeType = ntr.value();
         this.tryElementRegistration(nativeType, clazz.getAnnotation(TaggableElement.class), handler);
      }
   }

   private void tryElementRegistration(Class<?> clazz, TaggableElement data, ITaggableElementRegistrationHandler handler) {
      try {
         ResourceLocation id = ResourceLocation.parse(data.value());
         ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
         handler.registerTaggableElement(registryKey, GenericUtil.uncheck(clazz));
      } catch (ResourceLocationException e) {
         throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
      }
   }

   private void tryManagerRegistration(Class<?> clazz, TaggableElementManagerFactory data, ITaggableElementRegistrationHandler handler) {
      try {
         ResourceLocation id = ResourceLocation.parse(data.value());
         ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
         handler.registerManager(GenericUtil.uncheck(registryKey), InstantiationUtil.getOrCreateInstance((Class<TagManagerFactory>)clazz));
      } catch (ResourceLocationException e) {
         throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
      }
   }
}
