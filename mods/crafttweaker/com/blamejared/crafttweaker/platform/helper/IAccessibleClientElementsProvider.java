package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.api.registry.TagAddingRegistryLookup;
import java.util.function.Consumer;
import net.minecraft.core.RegistryAccess;

public interface IAccessibleClientElementsProvider {
   RegistryAccess registryAccess();

   void registryAccess(RegistryAccess var1);

   void runWithRegistryAccess(Consumer<RegistryAccess> var1);

   boolean hasRegistryAccess();

   TagAddingRegistryLookup tagAddingRegistryLookup();
}
