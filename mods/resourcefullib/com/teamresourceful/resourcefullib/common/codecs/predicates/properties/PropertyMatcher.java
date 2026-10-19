package com.teamresourceful.resourcefullib.common.codecs.predicates.properties;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

public interface PropertyMatcher {
   Codec<PropertyMatcher> CODEC = Codec.either(ExactPropertyMatcher.CODEC, RangePropertyMatcher.CODEC)
      .flatXmap(
         either -> DataResult.success((Record)either.map(p -> p, p -> p)),
         matcher -> {
            if (matcher instanceof ExactPropertyMatcher exactMatcher) {
               return DataResult.success(Either.left(exactMatcher));
            } else {
               return matcher instanceof RangePropertyMatcher rangeMatcher
                  ? DataResult.success(Either.right(rangeMatcher))
                  : DataResult.error(() -> "Unknown PropertyMatcher type");
            }
         }
      );

   default <S extends StateHolder<?, S>> boolean match(String name, StateDefinition<?, S> definition, S holder) {
      Property<?> property = definition.getProperty(name);
      return property != null && this.match(holder, property);
   }

   <T extends Comparable<T>> boolean match(StateHolder<?, ?> var1, Property<T> var2);

   Codec<? extends PropertyMatcher> codec();

   Optional<String> value(RandomSource var1);
}
