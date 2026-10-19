package com.teamresourceful.resourcefullib.common.nbt.validators;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.teamresourceful.resourcefullib.common.codecs.CodecExtras;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class ValidatorCodec<I extends Tag> implements Codec<Validator<I>> {
   private final Codec<Validator<I>> codec;

   public ValidatorCodec(Codec<? extends Validator<I>> defaultCodec, Consumer<ValidatorCodec.AddCallback<I>> consumer) {
      Builder<String, MapCodec<Validator<I>>> builder = ImmutableMap.builder();
      consumer.accept((key, codec) -> builder.put(key, MapCodec.assumeMapUnsafe(codec)));
      Map<String, MapCodec<Validator<I>>> validators = builder.build();
      this.codec = CodecExtras.eitherRight(Codec.either(defaultCodec, Codec.STRING.dispatch(Validator::id, validators::get)));
   }

   public <T> DataResult<Pair<Validator<I>, T>> decode(DynamicOps<T> ops, T input) {
      return this.codec.decode(ops, input);
   }

   public <T> DataResult<T> encode(Validator<I> input, DynamicOps<T> ops, T prefix) {
      return this.codec.encode(input, ops, prefix);
   }

   @FunctionalInterface
   public interface AddCallback<T extends Tag> {
      void add(String var1, Codec<? extends Validator<T>> var2);
   }
}
