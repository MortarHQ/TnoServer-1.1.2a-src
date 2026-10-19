package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

final class IDataListBuilder implements ListBuilder<IData> {
   private final DynamicOps<IData> ops;
   private final Supplier<DataResult<Builder<IData>>> resetState;
   private DataResult<Builder<IData>> builder;

   private IDataListBuilder(DynamicOps<IData> ops) {
      this.ops = ops;
      this.resetState = () -> DataResult.success(ImmutableList.builder());
      this.reset();
   }

   static IDataListBuilder of(DynamicOps<IData> ops) {
      return new IDataListBuilder(ops);
   }

   public DynamicOps<IData> ops() {
      return this.ops;
   }

   public DataResult<IData> build(IData prefix) {
      DataResult<IData> result = this.builder.flatMap(b -> this.ops().mergeToList(prefix, b.build()));
      this.reset();
      return result;
   }

   public ListBuilder<IData> add(IData value) {
      return this.replace(it -> it.map(b -> b.add(value)));
   }

   public ListBuilder<IData> add(DataResult<IData> value) {
      return this.replace(it -> it.apply2(Builder::add, value));
   }

   public ListBuilder<IData> withErrorsFrom(DataResult<?> result) {
      return this.replace(it -> it.flatMap(b -> result.map(x -> b)));
   }

   public ListBuilder<IData> mapError(UnaryOperator<String> onError) {
      return this.replace(it -> it.mapError(onError));
   }

   public DataResult<IData> build(DataResult<IData> prefix) {
      return prefix.flatMap(this::build);
   }

   public <E> ListBuilder<IData> add(E value, Encoder<E> encoder) {
      return this.add(encoder.encode(value, this.ops(), (IData)this.ops().empty()));
   }

   public <E> ListBuilder<IData> addAll(Iterable<E> values, Encoder<E> encoder) {
      return StreamSupport.stream(values.spliterator(), false)
         .reduce(GenericUtil.uncheck(this), (builder, value) -> builder.add(value, encoder), OpUtils.noCombiner());
   }

   @Override
   public String toString() {
      return "IDataListBuilder[%s@%s]".formatted(this.ops(), this.builder);
   }

   private ListBuilder<IData> replace(Function<DataResult<Builder<IData>>, DataResult<Builder<IData>>> block) {
      this.builder = block.apply(this.builder);
      return this;
   }

   private void reset() {
      this.builder = this.resetState.get();
   }
}
