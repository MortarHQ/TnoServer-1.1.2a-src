package dev.ftb.mods.ftbfiltersystem.registry;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemRegistry;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;

public class FilterRegistry implements FTBFilterSystemRegistry {
   private static final FilterRegistry SERVER_INSTANCE = new FilterRegistry();
   private static final FilterRegistry CLIENT_INSTANCE = new FilterRegistry();
   private final Map<ResourceLocation, FilterRegistry.FilterDetails<?>> filterMap = new ConcurrentHashMap<>();
   private final Map<ResourceLocation, SmartFilter> defaultInstances = new ConcurrentHashMap<>();
   private boolean frozen = false;

   public static FilterRegistry getInstance() {
      return Platform.getEnv() == Dist.CLIENT ? CLIENT_INSTANCE : SERVER_INSTANCE;
   }

   @Override
   public <T extends SmartFilter> void register(ResourceLocation id, SmartFilter.Factory<T> factory, SmartFilter.DefaultFactory<T> defaultFactory) {
      if (this.frozen) {
         throw new IllegalStateException("filter registry is now frozen!");
      }

      this.filterMap.put(id, new FilterRegistry.FilterDetails<>(id, factory, defaultFactory));
   }

   @Override
   public Collection<ResourceLocation> allFilterKeys() {
      return Collections.unmodifiableCollection(this.filterMap.keySet());
   }

   @Override
   public Collection<SmartFilter> defaultFilterInstances() {
      return this.defaultInstances.values();
   }

   public Optional<FilterRegistry.FilterDetails<?>> getDetails(ResourceLocation type) {
      return Optional.ofNullable(this.filterMap.get(type));
   }

   public void freeze() {
      this.frozen = true;
      this.filterMap.forEach((id, entry) -> this.defaultInstances.put(id, entry.defaultSupplier().create(null)));
   }

   public Optional<SmartFilter> createDefaultFilter(SmartFilter.Compound parent, ResourceLocation filterId) {
      return this.filterMap.containsKey(filterId) ? Optional.of(this.filterMap.get(filterId).defaultSupplier().create(parent)) : Optional.empty();
   }

   public record FilterDetails<T extends SmartFilter>(ResourceLocation id, SmartFilter.Factory<T> factory, SmartFilter.DefaultFactory<T> defaultSupplier) {
   }
}
