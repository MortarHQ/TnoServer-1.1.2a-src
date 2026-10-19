package com.teamresourceful.resourcefullib.common.fluid.registry;

import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface ResourcefulFluidRegistry extends ResourcefulRegistry<FluidData> {
   @Deprecated
   @Internal
   @Override
   default <I extends FluidData> RegistryEntry<I> register(String id, Supplier<I> supplier) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Use register(String, FluidProperties.Builder) instead.");
   }

   @Deprecated
   @Internal
   @Override
   default HolderRegistryEntry<FluidData> registerHolder(String id, Supplier<FluidData> supplier) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Use register(String, FluidProperties.Builder) instead.");
   }

   RegistryEntry<FluidData> register(String var1, FluidProperties var2);

   default RegistryEntry<FluidData> register(String id, FluidProperties.Builder builder) {
      return this.register(id, builder.build());
   }

   @Internal
   record Entry(ResourceLocation id, FluidData data) implements RegistryEntry<FluidData> {
      public FluidData get() {
         return this.data;
      }

      @Override
      public ResourceLocation getId() {
         return this.id;
      }
   }
}
