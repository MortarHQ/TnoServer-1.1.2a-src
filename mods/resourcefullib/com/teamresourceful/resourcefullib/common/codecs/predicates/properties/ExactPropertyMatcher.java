package com.teamresourceful.resourcefullib.common.codecs.predicates.properties;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

public record ExactPropertyMatcher(String value) implements PropertyMatcher {
   public static final Codec<ExactPropertyMatcher> CODEC = Codec.STRING.xmap(ExactPropertyMatcher::new, ExactPropertyMatcher::value);

   @Override
   public <T extends Comparable<T>> boolean match(StateHolder<?, ?> holder, Property<T> property) {
      T comparable = (T)holder.getValue(property);
      return property.getValue(this.value).map(val -> comparable.compareTo((T)val) == 0).orElse(false);
   }

   @Override
   public Codec<ExactPropertyMatcher> codec() {
      return CODEC;
   }

   @Override
   public Optional<String> value(RandomSource source) {
      return Optional.of(this.value);
   }
}
