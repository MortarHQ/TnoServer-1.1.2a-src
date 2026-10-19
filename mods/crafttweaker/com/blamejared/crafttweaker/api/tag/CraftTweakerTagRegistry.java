package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTagNetworkSerialization;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager;
import net.minecraft.tags.TagManager.LoadResult;
import net.minecraft.tags.TagNetworkSerialization.NetworkPayload;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@ZenCodeType.Name("crafttweaker.api.tag.TagManager")
public final class CraftTweakerTagRegistry {
   private static final Supplier<Set<ResourceLocation>> SERVER_ONLY_FOLDERS = Suppliers.memoize(
      () -> Services.REGISTRY
         .serverOnlyRegistries()
         .stream()
         .map(CraftTweakerTagRegistry.INSTANCE::makeTagFolder)
         .<ResourceLocation>map(ResourceLocation::tryParse)
         .filter(Objects::nonNull)
         .collect(Collectors.toSet())
   );
   public static final String GLOBAL_NAME = "tags";
   @ZenCodeGlobals.Global("tags")
   public static final CraftTweakerTagRegistry INSTANCE = new CraftTweakerTagRegistry();
   private final Map<ResourceKey<? extends Registry<?>>, ITagManager<?>> registeredManagers = new HashMap<>();
   private final Set<ResourceKey<? extends Registry<?>>> knownManagers = new HashSet<>();
   private final Set<ResourceKey<? extends Registry<?>>> knownManagersView = Collections.unmodifiableSet(this.knownManagers);

   private CraftTweakerTagRegistry() {
   }

   public ITagManager<?> addManager(Class<? extends ITagManager<?>> cls) {
      ITagManager<?> manager = InstantiationUtil.getOrCreateInstance((Class<ITagManager<?>>)cls);
      Objects.requireNonNull(
         manager, "Error while creating tag manager from class: '" + cls + "'! Make sure it has a default constructor or a public static INSTANCE field!"
      );
      return this.addManager(manager);
   }

   public ITagManager<?> addManager(ITagManager<?> manager) {
      this.registeredManagers.put(manager.resourceKey(), manager);
      if (manager.getClass().equals(KnownTagManager.class)) {
         this.knownManagers.add(manager.resourceKey());
      }

      return manager;
   }

   public Collection<ITagManager<?>> managers() {
      return Collections.unmodifiableCollection(this.registeredManagers.values());
   }

   public Collection<ResourceKey<? extends Registry<?>>> knownManagers() {
      return this.knownManagersView;
   }

   public <T> Optional<ITagManager<?>> findManager(ResourceKey<? extends Registry<T>> key) {
      return Optional.ofNullable(this.registeredManagers.get(key));
   }

   public <T> Optional<KnownTagManager<T>> findKnownManager(ResourceKey<? extends Registry<T>> key) {
      return Optional.ofNullable(this.registeredManagers.get(key)).map(it -> it instanceof KnownTagManager ? (KnownTagManager)it : null);
   }

   public <T> Optional<? extends ITagManager<?>> tagManagerFromFolder(ResourceLocation tagFolder) {
      return this.registeredManagers.values().stream().filter(iTagManager -> tagFolder.equals(ResourceLocation.tryParse(iTagManager.tagFolder()))).findFirst();
   }

   public boolean isServerOnly(ResourceLocation tagFolder) {
      return SERVER_ONLY_FOLDERS.get().contains(tagFolder);
   }

   public boolean isKnownManager(ResourceKey<? extends Registry<?>> key) {
      return this.knownManagers.contains(key);
   }

   public boolean isKnownManager(ResourceLocation tagFolder) {
      return this.tagManagerFromFolder(tagFolder).map(manager -> this.isKnownManager(manager.resourceKey())).orElse(false);
   }

   public <T> ITagManager<?> tagManager(ResourceKey<? extends Registry<T>> key) {
      return this.findManager(key).orElseThrow(() -> new NoSuchElementException("No tag manager found for given key: " + key));
   }

   public <T> KnownTagManager<T> knownTagManager(ResourceKey<? extends Registry<T>> key) {
      return this.<T>findKnownManager(key).orElseThrow(() -> new NoSuchElementException("No known tag manager found for given key: " + key));
   }

   @ZenCodeType.Method
   public <T extends ITagManager<?>> T tagManager(ResourceLocation registryLocation) {
      return (T)this.findManager(ResourceKey.createRegistryKey(registryLocation))
         .orElseThrow(() -> new NoSuchElementException("No tag manager found for given location: " + registryLocation));
   }

   public void bind(TagManager tagManager) {
      this.bind(tagManager.getResult());
   }

   public void bind(Map<ResourceKey<? extends Registry<?>>, NetworkPayload> tags) {
      this.bind(tags, new CraftTweakerTagRegistry.BindContext());
   }

   public void bind(Map<ResourceKey<? extends Registry<?>>, NetworkPayload> tags, CraftTweakerTagRegistry.BindContext context) {
      List<LoadResult<?>> results = new ArrayList<>();
      Set<ResourceKey<?>> knownKeys = new HashSet<>();
      CraftTweakerAPI.getAccessibleElementsProvider()
         .client()
         .runWithRegistryAccess(
            registryAccess -> {
               tags.forEach(
                  (resourceKey, networkPayload) -> {
                     knownKeys.add((ResourceKey<?>)resourceKey);
                     HashMap<Object, Object> resultMap = new HashMap<>();
                     Registry<Object> registry = registryAccess.registryOrThrow(resourceKey);
                     AccessTagNetworkSerialization.crafttweaker$$callDeserializeTagsFromNetwork(
                        GenericUtil.uncheck(resourceKey), registry, networkPayload, (key, holders) -> resultMap.put(key.location(), holders)
                     );
                     results.add(new LoadResult(resourceKey, resultMap));
                  }
               );
               registryAccess.registries().forEach(registryEntry -> {
                  if (!knownKeys.contains(registryEntry.key())) {
                     results.add(new LoadResult(registryEntry.key(), new HashMap()));
                  }
               });
               this.bind(results, context);
            }
         );
   }

   public void bind(List<LoadResult<?>> results) {
      this.bind(results, new CraftTweakerTagRegistry.BindContext());
   }

   public void bind(List<LoadResult<?>> results, CraftTweakerTagRegistry.BindContext context) {
      this.registeredManagers.clear();
      this.knownManagers.clear();

      for (LoadResult loadResult : results) {
         Optional<? extends Class<?>> taggableElement = CraftTweakerAPI.getRegistry().getTaggableElementFor(loadResult.key());
         if (context.registerKnownManagers()) {
            TagManagerFactory taggableElementFactory = CraftTweakerAPI.getRegistry().getTaggableElementFactory(loadResult.key());
            taggableElement.ifPresentOrElse(
               it -> this.addManager(taggableElementFactory.apply(loadResult.key(), (Class<?>)it)).bind(loadResult),
               () -> this.addManager(new UnknownTagManager(loadResult.key())).bind(loadResult)
            );
         } else {
            this.addManager(new UnknownTagManager(loadResult.key())).bind(loadResult);
         }
      }
   }

   public String makeTagFolder(ResourceKey<? extends Registry<?>> key) {
      String tagDir = Registries.tagsDirPath(key);
      if (tagDir.startsWith("tags/")) {
         tagDir = tagDir.substring("tags/".length());
      }

      return tagDir;
   }

   public record BindContext(boolean registerKnownManagers) {
      public BindContext() {
         this(true);
      }
   }
}
