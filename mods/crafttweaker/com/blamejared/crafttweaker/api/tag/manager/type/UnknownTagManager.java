package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagAdd;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagClear;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagCreate;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager.LoadResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@Document("vanilla/api/tag/manager/type/UnknownTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.UnknownTagManager")
public class UnknownTagManager implements ITagManager<UnknownTag> {
   private final ResourceKey<? extends Registry<?>> resourceKey;
   private final MutableLoadResult<?> backingResult;
   private Map<ResourceLocation, UnknownTag> tagCache;

   public UnknownTagManager(ResourceKey<? extends Registry<?>> resourceKey) {
      this.resourceKey = resourceKey;
      this.backingResult = new MutableLoadResult();
      this.tagCache = new HashMap<>();
   }

   @Override
   public ResourceKey<? extends Registry<?>> resourceKey() {
      return this.resourceKey;
   }

   @ZenCodeType.Method
   public final void addId(UnknownTag to, ResourceLocation... values) {
      if (!this.exists(to)) {
         CraftTweakerAPI.apply(new ActionUnknownTagCreate(to));
      }

      CraftTweakerAPI.apply(new ActionUnknownTagAdd(to, List.of(values)));
      this.recalculate();
   }

   @ZenCodeType.Method
   public final void removeId(UnknownTag from, ResourceLocation... values) {
      if (!this.exists(from)) {
         throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
      }

      CraftTweakerAPI.apply(new ActionUnknownTagRemove(from, List.of(values)));
      this.recalculate();
   }

   public void clear(UnknownTag from) {
      if (!this.exists(from)) {
         throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
      }

      CraftTweakerAPI.apply(new ActionUnknownTagClear(from));
      this.recalculate();
   }

   @ZenCodeType.Method
   public UnknownTag tag(String id) {
      return this.tag(ResourceLocation.parse(id));
   }

   @ZenCodeType.Method
   public UnknownTag tag(ResourceLocation id) {
      return this.tagMap().getOrDefault(id, new UnknownTag(id, this));
   }

   @Override
   public void recalculate() {
      this.tagCache = this.backingResult
         .tagMap()
         .keySet()
         .stream()
         .map(id -> Pair.of(id, new UnknownTag(id, this)))
         .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("tagMap")
   @Override
   public Map<ResourceLocation, UnknownTag> tagMap() {
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
   public Collection<Holder<?>> getInternalRaw(UnknownTag tag) {
      return GenericUtil.uncheck(this.backingResult.tagMap().get(tag.id()));
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
      this.backingResult.bind(GenericUtil.uncheck(result));
   }
}
