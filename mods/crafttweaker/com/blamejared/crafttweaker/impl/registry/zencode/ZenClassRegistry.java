package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ZenClassRegistry implements IZenClassRegistry {
   private final Set<Class<?>> blacklistedClasses = new HashSet<>();
   private final Map<IScriptLoader, LoaderSpecificZenClassRegistry> registryMap = new HashMap<>();

   @Override
   public boolean isRegistered(IScriptLoader loader, Class<?> clazz) {
      return this.get(loader).isRegistered(clazz);
   }

   @Override
   public Optional<String> getNameFor(IScriptLoader loader, Class<?> clazz) {
      return this.get(loader).getNameFor(clazz);
   }

   @Override
   public <T> List<Class<? extends T>> getImplementationsOf(IScriptLoader loader, Class<T> checkFor) {
      return this.get(loader).getImplementationsOf(checkFor);
   }

   @Override
   public IZenClassRegistry.IClassData getClassData(IScriptLoader loader) {
      return this.get(loader).getImmutableDataView();
   }

   @Override
   public List<Class<?>> getClassesInPackage(IScriptLoader loader, String packageName) {
      return this.get(loader).getClassesInPackage(packageName);
   }

   @Override
   public List<Class<?>> getGlobalsInPackage(IScriptLoader loader, String packageName) {
      return this.get(loader).getGlobalsInPackage(packageName);
   }

   @Override
   public Set<String> getRootPackages(IScriptLoader loader) {
      return this.get(loader).getRootPackages();
   }

   @Override
   public INativeTypeRegistry getNativeTypeRegistry(IScriptLoader loader) {
      return this.get(loader).getNativeTypeRegistry();
   }

   @Override
   public boolean isBlacklisted(Class<?> cls) {
      return this.blacklistedClasses.contains(cls);
   }

   public void fillLoaderData(Collection<IScriptLoader> loaders) {
      loaders.forEach(it -> this.registryMap.put(it, new LoaderSpecificZenClassRegistry()));
   }

   public void registerNativeType(IScriptLoader loader, NativeTypeInfo info) {
      this.get(loader).registerNativeType(info);
   }

   public void registerZenType(IScriptLoader loader, Class<?> clazz, ZenTypeInfo info, boolean globals) {
      if (this.isIncompatible(clazz)) {
         this.blacklistedClasses.add(clazz);
      } else {
         this.get(loader).registerZenType(clazz, info, globals);
      }
   }

   public void applyInheritanceRules() {
      Map<IScriptLoader, LoaderSpecificZenClassRegistry> snapshot = this.createSnapshot();
      Map<IScriptLoader, LoaderSpecificZenClassRegistry> inherited = this.applyInheritanceRules(snapshot);
      this.registryMap.clear();
      this.registryMap.putAll(inherited);
   }

   private LoaderSpecificZenClassRegistry get(IScriptLoader loader) {
      return this.registryMap.getOrDefault(Objects.requireNonNull(loader, "loader"), LoaderSpecificZenClassRegistry.EMPTY);
   }

   private boolean isIncompatible(Class<?> cls) {
      try {
         cls.getDeclaredFields();
         cls.getFields();
         cls.getDeclaredMethods();
         cls.getMethods();
         cls.getConstructors();
         cls.getDeclaredConstructors();
         return false;
      } catch (Throwable t) {
         CommonLoggers.zenCode().error("Could not register class '{}'! This is most likely a compatibility issue!", cls.getCanonicalName(), t);
         return true;
      }
   }

   private Map<IScriptLoader, LoaderSpecificZenClassRegistry> createSnapshot() {
      return this.registryMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, it -> it.getValue().createSnapshot()));
   }

   private Map<IScriptLoader, LoaderSpecificZenClassRegistry> applyInheritanceRules(Map<IScriptLoader, LoaderSpecificZenClassRegistry> snapshot) {
      return snapshot.entrySet().stream().collect(Collectors.toMap(Entry::getKey, it -> this.applyInheritanceRules(it.getKey(), snapshot)));
   }

   private LoaderSpecificZenClassRegistry applyInheritanceRules(IScriptLoader loader, Map<IScriptLoader, LoaderSpecificZenClassRegistry> snapshot) {
      try {
         LoaderSpecificZenClassRegistry inheritedRegistry = new LoaderSpecificZenClassRegistry();
         Collection<LoaderSpecificZenClassRegistry> inheritanceData = this.computeInheritanceData(loader, snapshot);
         inheritedRegistry.inheritFrom(inheritanceData);
         return inheritedRegistry;
      } catch (Exception e) {
         throw new IllegalStateException("Unable to apply inheritance rules for loader " + loader.name(), e);
      }
   }

   private Collection<LoaderSpecificZenClassRegistry> computeInheritanceData(IScriptLoader loader, Map<IScriptLoader, LoaderSpecificZenClassRegistry> snapshot) {
      return Stream.concat(loader.allInheritedLoaders().stream(), Stream.of(loader)).map(snapshot::get).filter(Objects::nonNull).toList();
   }
}
