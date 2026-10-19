package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

public abstract class ActionKnownTagModify<T> extends ActionKnownTag<T> {
   private final List<T> values;

   public ActionKnownTagModify(KnownTag<T> mcTag, List<T> values) {
      super(mcTag);
      this.values = values;
   }

   protected List<Holder<T>> holderValues() {
      return Lists.transform(this.values(), input -> this.makeHolder(Either.left(input)));
   }

   public List<T> values() {
      return this.values;
   }

   @Override
   public boolean validate(Logger logger) {
      if (this.values() == null) {
         logger.error("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
         return false;
      } else if (this.values().isEmpty()) {
         logger.error("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
         return false;
      } else {
         return super.validate(logger);
      }
   }

   public String describeValues() {
      return this.values()
         .stream()
         .map(
            it -> Services.REGISTRY
               .key(GenericUtil.uncheck(this.manager().resourceKey()), (T)it)
               .map(ResourceLocation::toString)
               .orElseGet(() -> Objects.toString(it))
         )
         .collect(Collectors.joining(", ", "[", "]"));
   }
}
