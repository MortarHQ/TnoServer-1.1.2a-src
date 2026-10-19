package com.blackgear.vanillabackport.common.api.variant.spawn;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;

public interface PriorityProvider<Context, Condition extends PriorityProvider.SelectorCondition<Context>> {
   List<PriorityProvider.Selector<Context, Condition>> selectors();

   static <C, T> Stream<T> select(Stream<T> stream, Function<T, PriorityProvider<C, ?>> function, C context) {
      List<PriorityProvider.UnpackedEntry<C, T>> list = new ArrayList<>();
      stream.forEach(
         object -> {
            PriorityProvider<C, ?> provider = function.apply((T)object);

            for (PriorityProvider.Selector<C, ?> selector : provider.selectors()) {
               list.add(
                  new PriorityProvider.UnpackedEntry<>(
                     (T)object,
                     selector.priority(),
                     (PriorityProvider.SelectorCondition<C>)DataFixUtils.orElseGet(selector.condition(), PriorityProvider.SelectorCondition::alwaysTrue)
                  )
               );
            }
         }
      );
      list.sort(PriorityProvider.UnpackedEntry.HIGHEST_PRIORITY_FIRST);
      Iterator<PriorityProvider.UnpackedEntry<C, T>> iterator = list.iterator();
      int priority = Integer.MIN_VALUE;

      while (iterator.hasNext()) {
         PriorityProvider.UnpackedEntry<C, T> entry = iterator.next();
         if (entry.priority < priority) {
            iterator.remove();
         } else if (entry.condition.test(context)) {
            priority = entry.priority;
         } else {
            iterator.remove();
         }
      }

      return list.stream().map(PriorityProvider.UnpackedEntry::entry);
   }

   static <C, T> Optional<T> pick(Stream<T> stream, Function<T, PriorityProvider<C, ?>> function, RandomSource random, C context) {
      List<T> list = select(stream, function, context).toList();
      return Util.getRandomSafe(list, random);
   }

   static <Context, Condition extends PriorityProvider.SelectorCondition<Context>> List<PriorityProvider.Selector<Context, Condition>> single(
      Condition condition, int priority
   ) {
      return List.of(new PriorityProvider.Selector<>(condition, priority));
   }

   static <Context, Condition extends PriorityProvider.SelectorCondition<Context>> List<PriorityProvider.Selector<Context, Condition>> alwaysTrue(int priority) {
      return List.of(new PriorityProvider.Selector<>(priority));
   }

   record Selector<Context, Condition extends PriorityProvider.SelectorCondition<Context>>(Optional<Condition> condition, int priority) {
      public Selector(Condition condition, int priority) {
         this(Optional.of(condition), priority);
      }

      public Selector(int priority) {
         this(Optional.empty(), priority);
      }

      public static <Context, Condition extends PriorityProvider.SelectorCondition<Context>> Codec<PriorityProvider.Selector<Context, Condition>> codec(
         Codec<Condition> codec
      ) {
         return RecordCodecBuilder.create(
            instance -> instance.group(
                  codec.optionalFieldOf("condition").forGetter(PriorityProvider.Selector::condition),
                  Codec.INT.fieldOf("priority").forGetter(PriorityProvider.Selector::priority)
               )
               .apply(instance, PriorityProvider.Selector::new)
         );
      }
   }

   interface SelectorCondition<C> extends Predicate<C> {
      static <C> PriorityProvider.SelectorCondition<C> alwaysTrue() {
         return context -> true;
      }
   }

   record UnpackedEntry<C, T>(T entry, int priority, PriorityProvider.SelectorCondition<C> condition) {
      public static final Comparator<PriorityProvider.UnpackedEntry<?, ?>> HIGHEST_PRIORITY_FIRST = Comparator.comparingInt(
            PriorityProvider.UnpackedEntry::priority
         )
         .reversed();
   }
}
