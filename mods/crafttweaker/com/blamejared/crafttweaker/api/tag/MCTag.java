package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@Document("vanilla/api/tag/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public interface MCTag extends CommandStringDisplayable, Comparable<MCTag> {
   Comparator<MCTag> COMPARATOR = Comparator.comparing(MCTag::manager)
      .thenComparing(mcTag -> mcTag.id().getNamespace())
      .thenComparing(mcTag -> mcTag.id().getPath());

   @ZenCodeType.Method
   @ZenCodeType.Getter("exists")
   default boolean exists() {
      return this.manager().exists(this.id());
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("id")
   ResourceLocation id();

   @ZenCodeType.Method
   default void addId(ResourceLocation... elements) {
      ((ITagManager<Object>)this.manager()).addId(GenericUtil.uncheck(this), elements);
   }

   @ZenCodeType.Method
   default void add(MCTag... tags) {
      this.addId(Arrays.stream(tags).map(MCTag::idElements).flatMap(Collection::stream).toArray(ResourceLocation[]::new));
   }

   @ZenCodeType.Method
   default void removeId(ResourceLocation... elements) {
      ((ITagManager<Object>)this.manager()).removeId(GenericUtil.uncheck(this), elements);
   }

   @ZenCodeType.Method
   default void remove(MCTag... tags) {
      this.removeId(Arrays.stream(tags).map(MCTag::idElements).flatMap(Collection::stream).toArray(ResourceLocation[]::new));
   }

   @ZenCodeType.Method
   default void clear() {
      ((ITagManager<Object>)this.manager()).clear(GenericUtil.uncheck(this));
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   default boolean contains(ResourceLocation id) {
      return this.idElements().contains(id);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("idElements")
   default List<ResourceLocation> idElements() {
      return ((ITagManager<Object>)this.manager()).idElements(GenericUtil.uncheck(this));
   }

   ITagManager<?> manager();

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   default boolean equals(MCTag other) {
      return this.id().equals(other.id()) && this.manager().equals(other.manager());
   }

   default <T extends Collection<Holder<?>>> T getInternal() {
      return (T)((ITagManager<Object>)this.manager()).getInternalRaw(GenericUtil.uncheck(this));
   }

   default <T extends TagKey<?>> T getTagKey() {
      return GenericUtil.uncheck(TagKey.create(GenericUtil.uncheck(this.manager().resourceKey()), this.id()));
   }

   default int compareTo(@Nonnull MCTag o) {
      return COMPARATOR.compare(this, o);
   }

   @Override
   default String getCommandString() {
      return "<tag:" + this.manager().tagFolder() + ":" + this.id() + ">";
   }
}
