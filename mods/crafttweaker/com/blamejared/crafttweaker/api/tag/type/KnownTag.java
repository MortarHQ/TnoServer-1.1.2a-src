package com.blamejared.crafttweaker.api.tag.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tag/type/KnownTag")
@ZenCodeType.Name("crafttweaker.api.tag.type.KnownTag")
public class KnownTag<T> implements MCTag, Iterable<T> {
   @Nonnull
   private final ResourceLocation id;
   @Nonnull
   private final KnownTagManager<T> manager;

   public KnownTag(@Nonnull ResourceLocation id, @Nonnull KnownTagManager<T> manager) {
      this.id = id;
      this.manager = manager;
   }

   @Override
   public <U extends Collection<Holder<?>>> U getInternal() {
      return GenericUtil.uncheck(this.manager().getInternal(this));
   }

   @SafeVarargs
   @ZenCodeType.Method
   public final void add(T... elements) {
      this.manager().addElements(this, elements);
   }

   @SafeVarargs
   @ZenCodeType.Method
   public final void remove(T... elements) {
      this.manager().removeElements(this, elements);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("elements")
   public List<T> elements() {
      return this.manager().elements(this);
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
   public boolean contains(T element) {
      return this.elements().contains(element);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("id")
   @Override
   public ResourceLocation id() {
      return this.id;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("manager")
   public KnownTagManager<T> manager() {
      return this.manager;
   }

   @ZenCodeType.Caster(implicit = true)
   @Override
   public String toString() {
      return this.getCommandString();
   }

   @Nonnull
   @Override
   public Iterator<T> iterator() {
      return this.elements().iterator();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         KnownTag<?> mcTag = (KnownTag<?>)o;
         return !this.id.equals(mcTag.id) ? false : this.manager.equals(mcTag.manager);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.id.hashCode();
      return 31 * result + this.manager.hashCode();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
   public Many<KnownTag<T>> withAmount(int amount) {
      return new Many<>(this, amount, ts -> ts.getCommandString() + " * " + amount);
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   public Many<KnownTag<T>> asTagWithAmount() {
      return this.withAmount(1);
   }
}
