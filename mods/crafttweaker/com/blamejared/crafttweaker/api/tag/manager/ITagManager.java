package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager.LoadResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@Document("vanilla/api/tag/manager/ITagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.ITagManager")
public interface ITagManager<T extends MCTag> extends CommandStringDisplayable, Iterable<T>, Comparable<ITagManager<?>> {
   @ZenCodeType.Method
   @ZenCodeType.Getter("tagFolder")
   default String tagFolder() {
      return CraftTweakerTagRegistry.INSTANCE.makeTagFolder(this.resourceKey());
   }

   @ZenCodeType.Method
   T tag(String var1);

   @ZenCodeType.Method
   T tag(ResourceLocation var1);

   @ZenCodeType.Method
   @ZenCodeType.Getter("tagMap")
   Map<ResourceLocation, T> tagMap();

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean exists(String id) {
      return this.exists(ResourceLocation.parse(id));
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean exists(ResourceLocation id) {
      return this.tagMap().containsKey(id);
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean exists(T tag) {
      return this.exists(tag.id());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("tagKeys")
   List<ResourceLocation> tagKeys();

   @Override
   default String getCommandString() {
      return "<tagmanager:" + this.tagFolder() + ">";
   }

   <U> void addTag(ResourceLocation var1, Collection<Holder<U>> var2);

   Map<ResourceLocation, Collection<Holder<?>>> internalTags();

   void bind(LoadResult<?> var1);

   default Optional<Class<?>> elementClass() {
      return Optional.empty();
   }

   @ZenCodeType.Method
   void addId(T var1, ResourceLocation... var2);

   @ZenCodeType.Method
   void removeId(T var1, ResourceLocation... var2);

   @ZenCodeType.Method
   void clear(T var1);

   @ZenCodeType.Method
   default List<ResourceLocation> idElements(T of) {
      return !this.exists(of)
         ? List.of()
         : this.getInternalRaw(of)
            .stream()
            .<Optional>map(Holder::unwrapKey)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .<ResourceLocation>map(ResourceKey::location)
            .collect(Collectors.toList());
   }

   @Nullable
   Collection<Holder<?>> getInternalRaw(T var1);

   @ZenCodeType.Method
   @ZenCodeType.Getter("tags")
   default List<T> tags() {
      return new ArrayList<>(this.tagMap().values());
   }

   @ZenCodeType.Method
   default List<T> getTagsFor(ResourceLocation element) {
      return this.tags().stream().filter(tag -> tag.contains(element)).toList();
   }

   ResourceKey<? extends Registry<?>> resourceKey();

   void recalculate();

   default T tag(TagKey<?> key) {
      return this.tag(key.location());
   }

   @Nonnull
   @Override
   default Iterator<T> iterator() {
      return this.tags().iterator();
   }

   default int compareTo(@Nonnull ITagManager<?> o) {
      return this.tagFolder().compareTo(o.tagFolder());
   }
}
