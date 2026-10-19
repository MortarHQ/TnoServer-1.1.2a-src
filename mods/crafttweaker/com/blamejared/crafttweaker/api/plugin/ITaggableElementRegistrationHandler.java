package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ITaggableElementRegistrationHandler {
   <T> void registerTaggableElement(ResourceKey<Registry<T>> var1, Class<T> var2);

   <T, U extends ITagManager<?>> void registerManager(ResourceKey<Registry<T>> var1, TagManagerFactory<T, U> var2);
}
