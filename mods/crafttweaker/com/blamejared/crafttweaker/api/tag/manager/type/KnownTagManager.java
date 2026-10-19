package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagAdd;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagClear;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagCreate;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager.LoadResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tag/manager/type/KnownTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.KnownTagManager")
public class KnownTagManager<T> implements ITagManager<KnownTag<T>> {
   private final ResourceKey<? extends Registry<T>> resourceKey;
   private final Class<T> elementClass;
   private final MutableLoadResult<T> backingResult;
   private Map<ResourceLocation, KnownTag<T>> tagCache;

   public KnownTagManager(ResourceKey<? extends Registry<T>> resourceKey, Class<T> elementClass) {
      this.resourceKey = resourceKey;
      this.elementClass = elementClass;
      this.backingResult = new MutableLoadResult<>();
      this.tagCache = new HashMap<>();
   }

   @Override
   public Optional<Class<?>> elementClass() {
      return Optional.of(this.elementClass);
   }

   @Override
   public ResourceKey<? extends Registry<T>> resourceKey() {
      return this.resourceKey;
   }

   @SafeVarargs
   @ZenCodeType.Method
   public final void addElements(KnownTag<T> to, T... values) {
      if (!this.exists(to)) {
         CraftTweakerAPI.apply(new ActionKnownTagCreate<>(to));
      }

      CraftTweakerAPI.apply(new ActionKnownTagAdd<>(to, List.of(values)));
      this.recalculate();
   }

   public void addId(KnownTag<T> to, ResourceLocation... values) {
      if (!this.exists(to)) {
         CraftTweakerAPI.apply(new ActionKnownTagCreate<>(to));
      }

      List<T> actualValues = Arrays.stream(values)
         .map(resourceLocation -> Services.REGISTRY.holderFromLocationOrThrow(this.resourceKey(), resourceLocation))
         .<Object>map(Holder::value)
         .map(o -> (T)o)
         .toList();
      CraftTweakerAPI.apply(new ActionKnownTagAdd<>(to, actualValues));
      this.recalculate();
   }

   @ZenCodeType.Method
   public final void removeId(KnownTag<T> from, ResourceLocation... values) {
      if (!this.exists(from)) {
         throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
      }

      List<T> actualValues = Arrays.stream(values)
         .map(resourceLocation -> Services.REGISTRY.holderFromLocationOrThrow(this.resourceKey(), resourceLocation))
         .<Object>map(Holder::value)
         .map(o -> (T)o)
         .toList();
      CraftTweakerAPI.apply(new ActionKnownTagRemove<>(from, actualValues));
      this.recalculate();
   }

   public void clear(KnownTag<T> from) {
      if (!this.exists(from)) {
         throw new IllegalArgumentException("Cannot clear elements of an empty tag: " + from);
      }

      CraftTweakerAPI.apply(new ActionKnownTagClear<>(from));
      this.recalculate();
   }

   @ZenCodeType.Method
   public List<T> elements(KnownTag<T> of) {
      return !this.exists(of) ? List.of() : this.getInternal(of).stream().<T>map(Holder::value).collect(Collectors.toList());
   }

   @SafeVarargs
   @ZenCodeType.Method
   public final void removeElements(KnownTag<T> from, T... values) {
      if (!this.exists(from)) {
         throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
      }

      CraftTweakerAPI.apply(new ActionKnownTagRemove<>(from, List.of(values)));
      this.recalculate();
   }

   @ZenCodeType.Method
   public KnownTag<T> tag(String id) {
      return this.tag(ResourceLocation.parse(id));
   }

   @ZenCodeType.Method
   public KnownTag<T> tag(ResourceLocation id) {
      return this.tagMap().getOrDefault(id, new KnownTag<>(id, this));
   }

   @Override
   public void recalculate() {
      this.tagCache = this.backingResult
         .tagMap()
         .keySet()
         .stream()
         .map(id -> Pair.of(id, new KnownTag<>(id, this)))
         .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("tagMap")
   @Override
   public Map<ResourceLocation, KnownTag<T>> tagMap() {
      if (this.tagCache.isEmpty()) {
         this.recalculate();
      }

      return this.tagCache;
   }

   @Override
   public Map<ResourceLocation, Collection<Holder<?>>> internalTags() {
      return GenericUtil.uncheck(Collections.unmodifiableMap(this.backingResult.tagMap()));
   }

   @Nullable
   public Collection<Holder<T>> getInternal(KnownTag<T> tag) {
      return this.backingResult.tagMap().get(tag.id());
   }

   @Nullable
   public Collection<Holder<?>> getInternalRaw(KnownTag<T> tag) {
      return GenericUtil.uncheck(this.getInternal(tag));
   }

   @Override
   public List<ResourceLocation> tagKeys() {
      return new ArrayList<>(this.tagMap().keySet());
   }

   @Override
   public <U> void addTag(ResourceLocation id, Collection<Holder<U>> tag) {
      this.backingResult.addTag(id, GenericUtil.uncheck(tag));
      this.recalculate();
   }

   @Override
   public void bind(LoadResult<?> result) {
      this.backingResult.bind((LoadResult<T>)result);
   }

   @ZenCodeType.Method
   public List<KnownTag<T>> getTagsFor(T element) {
      return this.tags().stream().filter(tag -> tag.contains(element)).toList();
   }
}
