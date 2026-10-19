package com.blamejared.crafttweaker.api.tag.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@Document("vanilla/api/tag/type/UnknownTag")
@ZenCodeType.Name("crafttweaker.api.tag.type.UnknownTag")
public class UnknownTag implements MCTag {
   @Nonnull
   private final ResourceLocation id;
   @Nonnull
   private final UnknownTagManager manager;

   public UnknownTag(@Nonnull ResourceLocation id, @Nonnull UnknownTagManager manager) {
      this.id = id;
      this.manager = manager;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("id")
   @Override
   public ResourceLocation id() {
      return this.id;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("manager")
   public UnknownTagManager manager() {
      return this.manager;
   }

   @ZenCodeType.Caster(implicit = true)
   @Override
   public String toString() {
      return this.getCommandString();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         UnknownTag mcTag = (UnknownTag)o;
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
   public Many<UnknownTag> withAmount(int amount) {
      return new Many<>(this, amount, ts -> ts.getCommandString() + " * " + amount);
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster(implicit = true)
   public Many<UnknownTag> asTagWithAmount() {
      return this.withAmount(1);
   }
}
