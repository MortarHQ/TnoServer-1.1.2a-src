package com.teamresourceful.resourcefullib.client.fluid.registry;

import com.teamresourceful.resourcefullib.client.fluid.data.ClientFluidProperties;
import com.teamresourceful.resourcefullib.common.registry.HolderRegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntries;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.Internal;

public class ResourcefulClientFluidRegistry implements ResourcefulRegistry<ClientFluidProperties> {
   private static final Map<ResourceLocation, ClientFluidProperties> REGISTRY = new ConcurrentHashMap<>();
   private final String modid;
   private final RegistryEntries<ClientFluidProperties> entries = new RegistryEntries<>();

   public ResourcefulClientFluidRegistry(String modid) {
      this.modid = modid;
   }

   public RegistryEntry<ClientFluidProperties> register(String id, ClientFluidProperties.Builder builder) {
      return this.register(id, builder::build);
   }

   @Override
   public <I extends ClientFluidProperties> RegistryEntry<I> register(String id, Supplier<I> supplier) {
      REGISTRY.put(ResourceLocation.fromNamespaceAndPath(this.modid, id), supplier.get());
      return this.entries.add(new ResourcefulClientFluidRegistry.Entry<>(ResourceLocation.fromNamespaceAndPath(this.modid, id), supplier.get()));
   }

   @Deprecated
   @Internal
   @Override
   public HolderRegistryEntry<ClientFluidProperties> registerHolder(String id, Supplier<ClientFluidProperties> supplier) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Use register(String, ClientFluidProperties.Builder) instead.");
   }

   @Override
   public Collection<RegistryEntry<ClientFluidProperties>> getEntries() {
      return this.entries.getEntries();
   }

   @Override
   public void init() {
   }

   @Internal
   public static ClientFluidProperties get(ResourceLocation id) {
      return REGISTRY.get(id);
   }

   private record Entry<T extends ClientFluidProperties>(ResourceLocation id, T data) implements RegistryEntry<T> {
      public T get() {
         return this.data;
      }

      @Override
      public ResourceLocation getId() {
         return this.id;
      }
   }
}
