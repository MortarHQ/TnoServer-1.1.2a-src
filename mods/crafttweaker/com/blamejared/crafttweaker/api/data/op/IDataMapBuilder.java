package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.RecordBuilder;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

final class IDataMapBuilder implements RecordBuilder<IData> {
   private final DynamicOps<IData> ops;
   private final Supplier<DataResult<Builder<IData, IData>>> resetState;
   private DataResult<Builder<IData, IData>> builder;

   private IDataMapBuilder(DynamicOps<IData> ops) {
      this.ops = ops;
      this.resetState = () -> DataResult.success(ImmutableMap.builder());
      this.reset();
   }

   static IDataMapBuilder of(DynamicOps<IData> ops) {
      return new IDataMapBuilder(ops);
   }

   public DynamicOps<IData> ops() {
      return this.ops;
   }

   public RecordBuilder<IData> add(IData key, IData value) {
      return this.replace(it -> it.map(b -> b.put(key, value)));
   }

   public RecordBuilder<IData> add(IData key, DataResult<IData> value) {
      return this.add(DataResult.success(key), value);
   }

   public RecordBuilder<IData> add(DataResult<IData> key, DataResult<IData> value) {
      return this.replace(it -> it.apply3(Builder::put, key, value));
   }

   public RecordBuilder<IData> withErrorsFrom(DataResult<?> result) {
      return this.replace(it -> it.flatMap(b -> result.map(x -> b)));
   }

   public RecordBuilder<IData> setLifecycle(Lifecycle lifecycle) {
      return this.replace(it -> it.setLifecycle(lifecycle));
   }

   public RecordBuilder<IData> mapError(UnaryOperator<String> onError) {
      return this.replace(it -> it.mapError(onError));
   }

   public DataResult<IData> build(IData prefix) {
      IData actualPrefix = prefix.getType() == IData.Type.EMPTY ? new MapData() : prefix;
      DataResult<IData> result = this.builder.flatMap(b -> this.ops().mergeToMap(actualPrefix, b.build()));
      this.reset();
      return result;
   }

   public DataResult<IData> build(DataResult<IData> prefix) {
      return prefix.flatMap(this::build);
   }

   public RecordBuilder<IData> add(String key, IData value) {
      return this.add(new StringData(key), value);
   }

   public RecordBuilder<IData> add(String key, DataResult<IData> value) {
      return this.add(new StringData(key), value);
   }

   public <E> RecordBuilder<IData> add(String key, E value, Encoder<E> encoder) {
      return this.add(key, encoder.encode(value, this.ops(), (IData)this.ops().empty()));
   }

   @Override
   public String toString() {
      return "IDataMapBuilder[%s@%s]".formatted(this.ops(), this.builder);
   }

   private RecordBuilder<IData> replace(Function<DataResult<Builder<IData, IData>>, DataResult<Builder<IData, IData>>> block) {
      this.builder = block.apply(this.builder);
      return this;
   }

   private void reset() {
      this.builder = this.resetState.get();
   }
}
