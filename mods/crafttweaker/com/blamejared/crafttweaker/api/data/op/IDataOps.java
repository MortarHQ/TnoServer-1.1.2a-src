package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.EmptyData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import net.minecraft.Optionull;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;

public final class IDataOps implements DynamicOps<IData> {
   public static final IDataOps INSTANCE = new IDataOps();

   private IDataOps() {
   }

   public RegistryOps<IData> withRegistryAccess() {
      return this.withRegistryAccess(CraftTweakerAPI.getAccessibleElementsProvider().registryAccess());
   }

   public RegistryOps<IData> withTagAddingRegistryAccess() {
      return CraftTweakerAPI.getAccessibleElementsProvider().tagAddingRegistryLookup().createSerializationContext(this);
   }

   public RegistryOps<IData> withRegistryAccess(RegistryAccess access) {
      return access.createSerializationContext(this);
   }

   public IData empty() {
      return EmptyData.INSTANCE;
   }

   public <U> U convertTo(DynamicOps<U> outOps, IData input) {
      if (this == outOps) {
         return GenericUtil.uncheck(input.copy());
      }

      return (U)(switch (input.getType()) {
         case BOOL -> (Object)outOps.createBoolean(input.asBool());
         case BYTE_ARRAY -> (Object)outOps.createByteList(ByteBuffer.wrap(input.asByteArray()));
         case BYTE -> (Object)outOps.createByte(input.asByte());
         case DOUBLE -> (Object)outOps.createDouble(input.asDouble());
         case FLOAT -> (Object)outOps.createFloat(input.asFloat());
         case INT_ARRAY -> (Object)outOps.createIntList(IntStream.of(input.asIntArray()));
         case INT -> (Object)outOps.createInt(input.asInt());
         case LIST -> (Object)this.convertList(outOps, input);
         case LONG_ARRAY -> (Object)outOps.createLongList(LongStream.of(input.asLongArray()));
         case LONG -> (Object)outOps.createLong(input.asLong());
         case MAP -> (Object)this.convertMap(outOps, input);
         case SHORT -> (Object)outOps.createShort(input.asShort());
         case STRING -> (Object)outOps.createString(input.getAsString());
         case EMPTY -> (Object)outOps.empty();
      });
   }

   public DataResult<Number> getNumberValue(IData input) {
      return switch (input.getType()) {
         case BYTE -> DataResult.success(input.asByte());
         case DOUBLE -> DataResult.success(input.asDouble());
         case FLOAT -> DataResult.success(input.asFloat());
         default -> this.noType(input, "number");
         case INT -> DataResult.success(input.asInt());
         case LONG -> DataResult.success(input.asLong());
         case SHORT -> DataResult.success(input.asShort());
      };
   }

   public IData createNumeric(Number i) {
      return new DoubleData(i.doubleValue());
   }

   public DataResult<String> getStringValue(IData input) {
      return input.getType() == IData.Type.STRING ? DataResult.success(input.getAsString()) : this.noType(input, "string");
   }

   public IData createString(String value) {
      return new StringData(value);
   }

   public DataResult<IData> mergeToList(IData list, IData value) {
      return (DataResult<IData>)Optionull.mapOrElse(
         this.adaptToStreamedList(list), it -> DataResult.success((IData)it.apply(Stream.of(value))), () -> this.noType(list, "list")
      );
   }

   public DataResult<IData> mergeToMap(IData map, IData key, IData value) {
      if (map.getType() != IData.Type.MAP && map != this.empty()) {
         return this.noType(map, "map");
      }

      if (key.getType() != IData.Type.STRING && map != this.empty()) {
         return this.noType(key, "string");
      }

      MapData other = map == this.empty() ? new MapData() : GenericUtil.uncheck(map.copyInternal());
      other.put(key.getAsString(), value);
      return DataResult.success(other);
   }

   public DataResult<Stream<Pair<IData, IData>>> getMapValues(IData input) {
      return input.getType() == IData.Type.MAP
         ? DataResult.success(input.getKeys().stream().map(it -> Pair.of(new StringData(it), input.getAt(it))))
         : this.noType(input, "map");
   }

   public IData createMap(Stream<Pair<IData, IData>> map) {
      MapData data = new MapData();
      map.forEach(pair -> data.put(((IData)pair.getFirst()).getAsString(), ((IData)pair.getSecond()).copyInternal()));
      return data;
   }

   public DataResult<Stream<IData>> getStream(IData input) {
      return switch (input.getType()) {
         case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY -> DataResult.success(input.asList().stream());
         default -> this.noType(input, "list");
         case LIST -> {
            ListData data = GenericUtil.uncheck(input);
            boolean mightBeWrapper = data.getInternal().getElementType() == 10;
            Stream<IData> result = data.asList().stream().map(mightBeWrapper ? this::unwrapIfNeeded : Function.identity());
            yield DataResult.success(result);
         }
      };
   }

   public IData createList(Stream<IData> input) {
      return Objects.requireNonNull(this.adaptToStreamedList(this.empty())).apply(input);
   }

   public IData remove(IData input, String key) {
      if (input.getType() != IData.Type.MAP) {
         return input;
      }

      MapData data = GenericUtil.uncheck(input.copyInternal());
      data.remove(key);
      return data;
   }

   public IData emptyMap() {
      return new MapData();
   }

   public IData emptyList() {
      return new ListData();
   }

   public Number getNumberValue(IData input, Number defaultValue) {
      return this.getNumberValue(input).result().orElse(defaultValue);
   }

   public IData createByte(byte value) {
      return new ByteData(value);
   }

   public IData createShort(short value) {
      return new ShortData(value);
   }

   public IData createInt(int value) {
      return new IntData(value);
   }

   public IData createLong(long value) {
      return new LongData(value);
   }

   public IData createFloat(float value) {
      return new FloatData(value);
   }

   public IData createDouble(double value) {
      return new DoubleData(value);
   }

   public DataResult<Boolean> getBooleanValue(IData input) {
      return switch (input.getType()) {
         case BOOL -> DataResult.success(input.asBool());
         case BYTE -> DataResult.success(input.asByte() != 0);
         default -> this.noType(input, "boolean");
      };
   }

   public IData createBoolean(boolean value) {
      return new BoolData(value);
   }

   public DataResult<IData> mergeToList(IData list, List<IData> values) {
      return values.stream()
         .reduce(
            DataResult.success(list == this.empty() ? new ListData() : list),
            (result, value) -> result.flatMap(l -> this.mergeToList(l, value)),
            OpUtils.noCombiner()
         );
   }

   public DataResult<IData> mergeToMap(IData map, Map<IData, IData> values) {
      return values.entrySet()
         .stream()
         .reduce(
            DataResult.success(map == this.empty() ? new MapData() : map),
            (result, entry) -> result.flatMap(m -> this.mergeToMap(m, (IData)entry.getKey(), (IData)entry.getValue())),
            OpUtils.noCombiner()
         );
   }

   public DataResult<IData> mergeToMap(IData map, MapLike<IData> values) {
      return values.entries()
         .reduce(
            DataResult.success(map == this.empty() ? new MapData() : map),
            (result, pair) -> result.flatMap(m -> this.mergeToMap(m, (IData)pair.getFirst(), (IData)pair.getSecond())),
            OpUtils.noCombiner()
         );
   }

   public DataResult<IData> mergeToPrimitive(IData prefix, IData value) {
      return prefix.getType() != IData.Type.EMPTY
         ? DataResult.error(() -> "Unable to append " + value + " to " + prefix + " primitively", value)
         : DataResult.success(value);
   }

   public DataResult<Consumer<BiConsumer<IData, IData>>> getMapEntries(IData input) {
      if (input.getType() != IData.Type.MAP) {
         return this.noType(input, "map");
      }

      MapData map = GenericUtil.uncheck(input);
      Stream<Pair<IData, IData>> entries = map.getKeys().stream().map(it -> Pair.of(new StringData(it), map.getAt(it)));
      return DataResult.success((Consumer<BiConsumer>)acceptor -> entries.forEach(pair -> acceptor.accept((IData)pair.getFirst(), (IData)pair.getSecond())));
   }

   public DataResult<MapLike<IData>> getMap(IData input) {
      if (input.getType() != IData.Type.MAP) {
         return this.noType(input, "map");
      }

      MapData data = GenericUtil.uncheck(input);
      MapLike<IData> mapLike = MapLikeMapData.of(data);
      return DataResult.success(mapLike);
   }

   public IData createMap(Map<IData, IData> map) {
      return this.createMap(map.entrySet().stream().map(it -> Pair.of(it.getKey(), it.getValue())));
   }

   public DataResult<Consumer<Consumer<IData>>> getList(IData input) {
      return switch (input.getType()) {
         case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY -> DataResult.success((Consumer<Consumer>)acceptor -> input.asList().forEach(acceptor));
         default -> this.noType(input, "list");
         case LIST -> {
            ListData data = GenericUtil.uncheck(input);
            boolean mightBeWrapper = data.getInternal().getElementType() == 10;
            Stream<IData> dataStream = data.asList().stream().map(mightBeWrapper ? this::unwrapIfNeeded : Function.identity());
            yield DataResult.success((Consumer<Consumer>)acceptor -> dataStream.forEach(acceptor));
         }
      };
   }

   public DataResult<ByteBuffer> getByteBuffer(IData input) {
      return input.getType() == IData.Type.BYTE_ARRAY ? DataResult.success(ByteBuffer.wrap(input.asByteArray())) : super.getByteBuffer(input);
   }

   public IData createByteList(ByteBuffer input) {
      byte[] array = new byte[input.limit()];
      input.get(array, 0, array.length);
      return new ByteArrayData(array);
   }

   public DataResult<IntStream> getIntStream(IData input) {
      return input.getType() == IData.Type.INT_ARRAY ? DataResult.success(IntStream.of(input.asIntArray())) : super.getIntStream(input);
   }

   public IData createIntList(IntStream input) {
      return new IntArrayData(input.toArray());
   }

   public DataResult<LongStream> getLongStream(IData input) {
      return input.getType() == IData.Type.LONG_ARRAY ? DataResult.success(LongStream.of(input.asLongArray())) : super.getLongStream(input);
   }

   public IData createLongList(LongStream input) {
      return new LongArrayData(input.toArray());
   }

   public DataResult<IData> get(IData input, String key) {
      return input.getType() == IData.Type.MAP
         ? (DataResult)Optionull.mapOrElse(input.getAt(key), DataResult::success, () -> DataResult.error(() -> "No such key %s in %s".formatted(key, input)))
         : this.noType(input, "map");
   }

   public DataResult<IData> getGeneric(IData input, IData key) {
      return key.getType() == IData.Type.STRING ? this.get(input, key.getAsString()) : DataResult.error(() -> "No such key %s in %s".formatted(key, input));
   }

   public IData set(IData input, String key, IData value) {
      return this.mergeToMap(input, new StringData(key), value).result().orElse(input);
   }

   public IData update(IData input, String key, Function<IData, IData> function) {
      return this.updateGeneric(input, new StringData(key), function);
   }

   public IData updateGeneric(IData input, IData key, Function<IData, IData> function) {
      return this.mergeToMap(input, key, function.apply(this.getGeneric(input, key).result().orElseGet(this::empty))).result().orElse(input);
   }

   public ListBuilder<IData> listBuilder() {
      return IDataListBuilder.of(this);
   }

   public RecordBuilder<IData> mapBuilder() {
      return IDataMapBuilder.of(this);
   }

   public <E> Function<E, DataResult<IData>> withEncoder(Encoder<E> encoder) {
      return element -> encoder.encode(element, this, this.empty());
   }

   public <E> Function<IData, DataResult<Pair<E, IData>>> withDecoder(Decoder<E> decoder) {
      return data -> decoder.decode(this, data);
   }

   public <E> Function<IData, DataResult<E>> withParser(Decoder<E> decoder) {
      return data -> decoder.parse(this, data);
   }

   public <U> U convertList(DynamicOps<U> outOps, IData input) {
      Stream<U> listStream = this.getStream(input).map(stream -> stream.map(it -> this.convertTo(outOps, it))).result().orElseGet(Stream::empty);
      return (U)outOps.createList(listStream);
   }

   public <U> U convertMap(DynamicOps<U> outOps, IData input) {
      Stream<Pair<U, U>> mapStream = this.getMapValues(input)
         .map(stream -> stream.map(it -> it.mapFirst(f -> this.convertTo(outOps, f))))
         .map(stream -> stream.map(it -> it.mapSecond(s -> this.convertTo(outOps, s))))
         .result()
         .orElseGet(Stream::empty);
      return (U)outOps.createMap(mapStream);
   }

   @Override
   public String toString() {
      return "IData";
   }

   private <R> DataResult<R> noType(IData data, String expect) {
      return DataResult.error(() -> data.getType() + " is not a valid type for " + expect);
   }

   private Function<Stream<IData>, IData> adaptToStreamedList(IData data) {
      ListDataAdapter adapter = this.adaptToList(data);
      return adapter == null ? null : it -> it.reduce(adapter, Function::apply, OpUtils.noCombiner()).finish();
   }

   private ListDataAdapter adaptToList(IData data) {
      return switch (data.getType()) {
         case BYTE_ARRAY, INT_ARRAY, LIST, LONG_ARRAY -> this.orEmpty(data, SameTypeListDataAdapter::list);
         default -> null;
         case EMPTY -> DecidingListDataAdapter.of();
      };
   }

   private ListDataAdapter orEmpty(IData data, Function<IData, ListDataAdapter> alternative) {
      return data.asList().isEmpty() ? DecidingListDataAdapter.of() : alternative.apply(data);
   }

   private IData unwrapIfNeeded(IData other) {
      if (other.getType() != IData.Type.MAP) {
         return other;
      }

      MapData data = GenericUtil.uncheck(other);
      Set<String> keys = data.getKeys();
      return keys.size() == 1 && keys.contains("$$wrapped$$") ? data.getAt("$$wrapped$$") : data;
   }
}
