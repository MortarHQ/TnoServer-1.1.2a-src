package com.tom.storagemod.util;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ComponentJoiner {
   static final Set<Characteristics> CH_NOID = Collections.emptySet();
   private MutableComponent value;
   private Component delimiter;

   public ComponentJoiner(Component delimiter) {
      this.delimiter = delimiter;
   }

   public static Collector<Component, ComponentJoiner, Component> joining(final Component empty, final Component delimiter) {
      return new Collector<Component, ComponentJoiner, Component>() {
         @Override
         public Supplier<ComponentJoiner> supplier() {
            return () -> new ComponentJoiner(delimiter);
         }

         @Override
         public Function<ComponentJoiner, Component> finisher() {
            return c -> (Component)(c.value != null ? c.value : empty);
         }

         @Override
         public BinaryOperator<ComponentJoiner> combiner() {
            return ComponentJoiner::merge;
         }

         @Override
         public Set<Characteristics> characteristics() {
            return ComponentJoiner.CH_NOID;
         }

         @Override
         public BiConsumer<ComponentJoiner, Component> accumulator() {
            return ComponentJoiner::add;
         }
      };
   }

   private MutableComponent prepareBuilder() {
      if (this.value != null) {
         this.value.append(this.delimiter);
      } else {
         this.value = Component.empty();
      }

      return this.value;
   }

   public ComponentJoiner add(Component newElement) {
      this.prepareBuilder().append(newElement);
      return this;
   }

   public ComponentJoiner merge(ComponentJoiner other) {
      if (other.value != null) {
         this.prepareBuilder().append(other.value.copy());
      }

      return this;
   }
}
