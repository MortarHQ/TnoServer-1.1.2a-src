package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

final class TaggableElementsRegistrationHandler implements ITaggableElementRegistrationHandler {
   private final List<TaggableElementsRegistrationHandler.ElementData> elementRequests = new ArrayList<>();
   private final List<TaggableElementsRegistrationHandler.ManagerData> managerRequests = new ArrayList<>();

   private TaggableElementsRegistrationHandler() {
   }

   static TaggableElementsRegistrationHandler of(Consumer<ITaggableElementRegistrationHandler> consumer) {
      TaggableElementsRegistrationHandler handler = new TaggableElementsRegistrationHandler();
      consumer.accept(handler);
      return handler;
   }

   @Override
   public <T> void registerTaggableElement(ResourceKey<Registry<T>> key, Class<T> elementClass) {
      this.elementRequests.add(new TaggableElementsRegistrationHandler.ElementData(key, elementClass));
   }

   @Override
   public <T, U extends ITagManager<?>> void registerManager(ResourceKey<Registry<T>> key, TagManagerFactory<T, U> factory) {
      this.managerRequests.add(new TaggableElementsRegistrationHandler.ManagerData(key, factory));
   }

   List<TaggableElementsRegistrationHandler.ElementData> elementRequests() {
      return Collections.unmodifiableList(this.elementRequests);
   }

   List<TaggableElementsRegistrationHandler.ManagerData> managerRequests() {
      return Collections.unmodifiableList(this.managerRequests);
   }

   record ElementData(ResourceKey<?> key, Class<?> elementClass) {
   }

   record ManagerData(ResourceKey<?> key, TagManagerFactory<?, ?> factory) {
   }
}
