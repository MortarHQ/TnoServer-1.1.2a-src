package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessReloadableServerResources;
import java.util.function.Consumer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;

public interface IAccessibleServerElementsProvider {
   ReloadableServerResources resources();

   AccessReloadableServerResources accessibleResources();

   boolean hasResources();

   void resources(ReloadableServerResources var1);

   RegistryAccess registryAccess();

   void registryAccess(RegistryAccess var1);

   void runWithRegistryAccess(Consumer<RegistryAccess> var1);

   boolean hasRegistryAccess();

   TagAddingRegistryLookup tagAddingRegistryLookup();
}
