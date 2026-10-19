package artifacts.registry;

import com.mojang.datafixers.util.Either;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Holder.Kind;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class RegistryHolder<R, V extends R> implements Holder<R>, Supplier<V> {
   private final ResourceKey<R> key;
   private final Supplier<V> factory;
   private Holder<R> holder;

   public RegistryHolder(ResourceKey<R> key, Supplier<V> factory) {
      this.key = key;
      this.factory = factory;
   }

   public Supplier<V> getFactory() {
      return this.factory;
   }

   public void bind(Holder<R> holder) {
      if (this.holder != null) {
         throw new IllegalStateException();
      }

      this.holder = holder;
   }

   public Holder<R> holder() {
      return this.holder;
   }

   @Override
   public V get() {
      return (V)this.value();
   }

   public R value() {
      return (R)this.holder.value();
   }

   public boolean isBound() {
      return this.holder != null && this.holder.isBound();
   }

   public boolean is(ResourceLocation resourceLocation) {
      return resourceLocation.equals(this.key.location());
   }

   public boolean is(ResourceKey<R> resourceKey) {
      return resourceKey.equals(this.key);
   }

   public boolean is(Predicate<ResourceKey<R>> predicate) {
      return predicate.test(this.key);
   }

   public boolean is(TagKey<R> tagKey) {
      return this.isBound() && this.holder.is(tagKey);
   }

   public boolean is(Holder<R> holder) {
      return this.isBound() && holder.is(holder);
   }

   public Stream<TagKey<R>> tags() {
      return this.isBound() ? this.holder.tags() : Stream.empty();
   }

   public Either<ResourceKey<R>, R> unwrap() {
      return Either.left(this.key);
   }

   public Optional<ResourceKey<R>> unwrapKey() {
      return Optional.of(this.key);
   }

   public Kind kind() {
      return Kind.REFERENCE;
   }

   public boolean canSerializeIn(HolderOwner<R> holderOwner) {
      return this.isBound() && this.holder.canSerializeIn(holderOwner);
   }

   @Override
   public boolean equals(Object obj) {
      return this == obj ? true : obj instanceof Holder<?> h && h.kind() == Kind.REFERENCE && h.unwrapKey().isPresent() && h.unwrapKey().get() == this.key;
   }

   @Override
   public int hashCode() {
      return this.key.hashCode();
   }
}
