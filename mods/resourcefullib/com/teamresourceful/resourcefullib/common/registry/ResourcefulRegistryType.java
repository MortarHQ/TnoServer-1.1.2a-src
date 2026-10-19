package com.teamresourceful.resourcefullib.common.registry;

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry;
import org.jetbrains.annotations.ApiStatus.Experimental;

public final class ResourcefulRegistryType<D, T extends ResourcefulRegistry<D>> {
   @Experimental
   public static final ResourcefulRegistryType<FluidData, ResourcefulFluidRegistry> FLUID = new ResourcefulRegistryType<>(ResourcefulFluidRegistry.class);
   private final Class<T> type;

   private ResourcefulRegistryType(Class<T> type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return "ResourcefulRegistryType{type=" + this.type + "}";
   }
}
