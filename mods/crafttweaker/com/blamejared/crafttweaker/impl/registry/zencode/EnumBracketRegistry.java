package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;

public final class EnumBracketRegistry {
   private final Map<IScriptLoader, EnumBracketRegistry.EnumData> data = new HashMap<>();

   public <T extends Enum<T>> void register(IScriptLoader loader, ResourceLocation id, Class<T> clazz) {
      EnumBracketRegistry.EnumData data = this.data.computeIfAbsent(loader, it -> {
         throw new IllegalStateException("No data for loader " + loader + ": this cannot happen");
      });
      Map<ResourceLocation, Class<? extends Enum<?>>> enums = data.enums();
      Class<? extends Enum<?>> old = enums.get(id);
      if (old != null) {
         throw new IllegalArgumentException("Attempted enum overriding on id " + id + ": old " + old.getName() + ", new " + clazz.getName());
      }

      enums.put(id, clazz);
   }

   public void fillLoaderData(Collection<IScriptLoader> loader) {
      loader.forEach(it -> this.data.put(it, new EnumBracketRegistry.EnumData()));
   }

   public void applyInheritanceRules() {
      Map<IScriptLoader, EnumBracketRegistry.EnumData> snapshot = this.createSnapshot();
      Map<IScriptLoader, EnumBracketRegistry.EnumData> inherited = this.applyInheritanceRules(snapshot);
      this.data.clear();
      this.data.putAll(inherited);
   }

   public <T extends Enum<T>> Optional<Class<T>> getEnum(IScriptLoader loader, ResourceLocation type) {
      return Optional.ofNullable(this.data.get(loader)).flatMap(it -> Optional.ofNullable(it.enums().get(type))).map(it -> (Class<T>)it);
   }

   public Map<ResourceLocation, Class<? extends Enum<?>>> getEnums(IScriptLoader loader) {
      return ImmutableMap.copyOf(this.data.getOrDefault(loader, new EnumBracketRegistry.EnumData()).enums());
   }

   private Map<IScriptLoader, EnumBracketRegistry.EnumData> createSnapshot() {
      return this.data
         .entrySet()
         .stream()
         .collect(Collectors.toMap(Entry::getKey, it -> new EnumBracketRegistry.EnumData(new HashMap<>(it.getValue().enums()))));
   }

   private Map<IScriptLoader, EnumBracketRegistry.EnumData> applyInheritanceRules(Map<IScriptLoader, EnumBracketRegistry.EnumData> snapshot) {
      return snapshot.entrySet().stream().collect(Collectors.toMap(Entry::getKey, it -> this.applyInheritanceRules(it.getKey(), snapshot)));
   }

   private EnumBracketRegistry.EnumData applyInheritanceRules(IScriptLoader loader, Map<IScriptLoader, EnumBracketRegistry.EnumData> snapshot) {
      Map<ResourceLocation, Class<? extends Enum<?>>> inheritedData = new HashMap<>();
      Collection<Map<ResourceLocation, Class<? extends Enum<?>>>> inheritanceData = this.computeInheritanceData(loader, snapshot);
      inheritanceData.forEach(it -> {
         try {
            this.tryMerge(inheritedData, (Map<ResourceLocation, Class<? extends Enum<?>>>)it);
         } catch (Exception e) {
            throw new IllegalStateException("Unable to apply inheritance rules for " + loader.name(), e);
         }
      });
      return new EnumBracketRegistry.EnumData(inheritedData);
   }

   private Collection<Map<ResourceLocation, Class<? extends Enum<?>>>> computeInheritanceData(
      IScriptLoader loader, Map<IScriptLoader, EnumBracketRegistry.EnumData> snapshot
   ) {
      return Stream.concat(loader.allInheritedLoaders().stream(), Stream.of(loader))
         .map(snapshot::get)
         .filter(Objects::nonNull)
         .map(EnumBracketRegistry.EnumData::enums)
         .toList();
   }

   private void tryMerge(Map<ResourceLocation, Class<? extends Enum<?>>> loaderEnums, Map<ResourceLocation, Class<? extends Enum<?>>> inheritedEnums) {
      inheritedEnums.forEach((id, clazz) -> {
         if (loaderEnums.containsKey(id)) {
            throw new IllegalStateException(id + " is already assigned to an enum");
         }

         loaderEnums.put(id, (Class<? extends Enum<?>>)clazz);
      });
   }

   private record EnumData(Map<ResourceLocation, Class<? extends Enum<?>>> enums) {
      EnumData() {
         this(new HashMap<>());
      }
   }
}
