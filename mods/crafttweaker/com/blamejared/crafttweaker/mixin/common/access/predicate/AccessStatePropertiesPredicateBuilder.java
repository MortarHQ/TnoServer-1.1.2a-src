package com.blamejared.crafttweaker.mixin.common.access.predicate;

import net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder;
import net.minecraft.advancements.critereon.StatePropertiesPredicate.PropertyMatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Builder.class)
public interface AccessStatePropertiesPredicateBuilder {
   @Accessor("matchers")
   com.google.common.collect.ImmutableList.Builder<PropertyMatcher> crafttweaker$getMatchers();
}
