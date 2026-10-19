package mcjty.incontrol.tools.typed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AttributeMap {
   private final Map<Key<?>, Object> values = new HashMap<>();

   public <A> boolean has(@Nonnull Key<A> key) {
      return this.values.containsKey(key);
   }

   public <A> void set(@Nonnull Key<A> key, A value) {
      this.values.put(key, value);
   }

   public boolean isEmpty() {
      return this.values.isEmpty();
   }

   public Set<Key<?>> getKeys() {
      return this.values.keySet();
   }

   public <A> void setNonnull(@Nonnull Key<A> key, A value) {
      if (value != null) {
         this.values.put(key, value);
      }
   }

   public <A> void consume(@Nonnull Key<A> key, Consumer<A> consumer) {
      if (this.has(key)) {
         consumer.accept(this.get(key));
         this.values.remove(key);
      }
   }

   @Nullable
   public <A> A consumeAndFetch(@Nonnull Key<A> key) {
      return this.consumeAndFetch(key, null);
   }

   @Nullable
   public <A> A consumeAndFetch(@Nonnull Key<A> key, A def) {
      if (this.has(key)) {
         A value = this.get(key);
         this.values.remove(key);
         return value;
      } else {
         return def;
      }
   }

   public <A> void consumeAsList(@Nonnull Key<A> key, Consumer<List<A>> consumer) {
      if (this.has(key)) {
         consumer.accept(this.getList(key));
         this.values.remove(key);
      }
   }

   public <A, B> void consume2(@Nonnull Key<A> key1, @Nonnull Key<B> key2, BiConsumer<A, B> consumer) {
      if (this.has(key1) || this.has(key2)) {
         consumer.accept(this.get(key1), this.get(key2));
         this.values.remove(key1);
         this.values.remove(key2);
      }
   }

   public <A> void consumeOrElse(@Nonnull Key<A> key, Consumer<A> consumer, Runnable elseRun) {
      if (this.has(key)) {
         consumer.accept(this.get(key));
         this.values.remove(key);
      } else {
         elseRun.run();
      }
   }

   public <A> A get(@Nonnull Key<A> key) {
      return (A)this.values.get(key);
   }

   public <A> Optional<A> getOptional(@Nonnull Key<A> key) {
      return Optional.ofNullable((A)this.values.get(key));
   }

   public <A> void addList(@Nonnull Key<A> key, A value) {
      if (!this.values.containsKey(key)) {
         this.values.put(key, new ArrayList());
      }

      List<A> l = (List<A>)this.values.get(key);
      l.add(value);
   }

   public <A> void addListNonnull(@Nonnull Key<A> key, A value) {
      if (value != null) {
         if (!this.values.containsKey(key)) {
            this.values.put(key, new ArrayList());
         }

         List<A> l = (List<A>)this.values.get(key);
         l.add(value);
      }
   }

   public <A> List<A> getList(@Nonnull Key<A> key) {
      return !this.values.containsKey(key) ? Collections.emptyList() : (List)this.values.get(key);
   }
}
