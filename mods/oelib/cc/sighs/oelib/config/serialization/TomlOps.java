package cc.sighs.oelib.config.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class TomlOps implements DynamicOps<Object> {
   public static final TomlOps INSTANCE = new TomlOps(false);
   public static final TomlOps COMPRESSED = new TomlOps(true);
   private static final Object EMPTY = new Object();
   private final boolean compressed;

   private TomlOps(boolean compressed) {
      this.compressed = compressed;
   }

   private static List<Object> asList(Object value) {
      return (List<Object>)value;
   }

   private static Map<String, Object> asMap(Object value) {
      return (Map<String, Object>)value;
   }

   public Object empty() {
      return EMPTY;
   }

   public <U> U convertTo(DynamicOps<U> outOps, Object input) {
      if (input instanceof Map) {
         return (U)this.convertMap(outOps, input);
      }

      if (input instanceof List) {
         return (U)this.convertList(outOps, input);
      }

      if (input == EMPTY) {
         return (U)outOps.empty();
      }

      if (input instanceof String) {
         return (U)outOps.createString((String)input);
      }

      if (input instanceof Boolean) {
         return (U)outOps.createBoolean((Boolean)input);
      }

      if (input instanceof Number) {
         BigDecimal value = input instanceof BigDecimal ? (BigDecimal)input : new BigDecimal(input.toString());

         try {
            long l = value.longValueExact();
            if ((byte)l == l) {
               return (U)outOps.createByte((byte)l);
            } else if ((short)l == l) {
               return (U)outOps.createShort((short)l);
            } else {
               return (U)((int)l == l ? outOps.createInt((int)l) : outOps.createLong(l));
            }
         } catch (ArithmeticException e) {
            double d = value.doubleValue();
            return (U)((float)d == d ? outOps.createFloat((float)d) : outOps.createDouble(d));
         }
      } else {
         return (U)outOps.empty();
      }
   }

   public DataResult<Number> getNumberValue(Object input) {
      if (input instanceof Number) {
         return DataResult.success((Number)input);
      }

      if (this.compressed && input instanceof String) {
         try {
            return DataResult.success(Integer.parseInt((String)input));
         } catch (NumberFormatException e) {
            return DataResult.error(() -> "Not a number: " + e + " " + input);
         }
      } else {
         return DataResult.error(() -> "Not a number: " + input);
      }
   }

   public Object createNumeric(Number i) {
      return i;
   }

   public DataResult<Boolean> getBooleanValue(Object input) {
      return input instanceof Boolean ? DataResult.success((Boolean)input) : DataResult.error(() -> "Not a boolean: " + input);
   }

   public Object createBoolean(boolean value) {
      return value;
   }

   public DataResult<String> getStringValue(Object input) {
      if (input instanceof String) {
         return DataResult.success((String)input);
      } else {
         return this.compressed && input instanceof Number ? DataResult.success(input.toString()) : DataResult.error(() -> "Not a string: " + input);
      }
   }

   public Object createString(String value) {
      return value;
   }

   public DataResult<Object> mergeToList(Object list, Object value) {
      if (!(list instanceof List) && list != this.empty()) {
         return DataResult.error(() -> "mergeToList called with not a list: " + list, list);
      }

      List<Object> result = new ArrayList<>();
      if (list != this.empty()) {
         result.addAll(asList(list));
      }

      result.add(value);
      return DataResult.success(result);
   }

   public DataResult<Object> mergeToList(Object list, List<Object> values) {
      if (!(list instanceof List) && list != this.empty()) {
         return DataResult.error(() -> "mergeToList called with not a list: " + list, list);
      }

      List<Object> result = new ArrayList<>();
      if (list != this.empty()) {
         result.addAll(asList(list));
      }

      result.addAll(values);
      return DataResult.success(result);
   }

   public DataResult<Object> mergeToMap(Object map, Object key, Object value) {
      if (!(map instanceof Map) && map != this.empty()) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + map, map);
      }

      if (!(key instanceof String keyString)) {
         if (!this.compressed) {
            return DataResult.error(() -> "key is not a string: " + key, map);
         }

         keyString = Objects.toString(key);
      }

      Map<String, Object> output = new LinkedHashMap<>();
      if (map != this.empty()) {
         output.putAll(asMap(map));
      }

      output.put(keyString, value);
      return DataResult.success(output);
   }

   public DataResult<Object> mergeToMap(Object map, MapLike<Object> values) {
      if (!(map instanceof Map) && map != this.empty()) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + map, map);
      }

      Map<String, Object> output = new LinkedHashMap<>();
      if (map != this.empty()) {
         output.putAll(asMap(map));
      }

      List<Object> missed = new ArrayList<>();
      values.entries().forEach(entry -> {
         Object key = entry.getFirst();
         if (!(key instanceof String keyString)) {
            if (!this.compressed) {
               missed.add(key);
               return;
            }

            keyString = Objects.toString(key);
         }

         output.put(keyString, entry.getSecond());
      });
      return !missed.isEmpty() ? DataResult.error(() -> "some keys are not strings: " + missed, output) : DataResult.success(output);
   }

   public DataResult<Stream<Pair<Object, Object>>> getMapValues(Object input) {
      if (!(input instanceof Map)) {
         return DataResult.error(() -> "Not a map: " + input);
      }

      Map<String, Object> object = asMap(input);
      return DataResult.success(object.entrySet().stream().map(entry -> {
         Object value = entry.getValue();
         if (value == EMPTY) {
            value = null;
         }

         return Pair.of(entry.getKey(), value);
      }));
   }

   public DataResult<Consumer<BiConsumer<Object, Object>>> getMapEntries(Object input) {
      if (!(input instanceof Map)) {
         return DataResult.error(() -> "Not a map: " + input);
      }

      Map<String, Object> object = asMap(input);
      return DataResult.success((Consumer<BiConsumer>)c -> {
         for (Entry<String, Object> entry : object.entrySet()) {
            Object value = entry.getValue();
            if (value == EMPTY) {
               value = null;
            }

            c.accept(entry.getKey(), value);
         }
      });
   }

   public DataResult<MapLike<Object>> getMap(Object input) {
      if (!(input instanceof Map)) {
         return DataResult.error(() -> "Not a map: " + input);
      }

      final Map<String, Object> object = asMap(input);
      return DataResult.success(new MapLike<Object>() {
         public Object get(Object key) {
            Object element = object.get(Objects.toString(key));
            return element == TomlOps.EMPTY ? null : element;
         }

         public Object get(String key) {
            Object element = object.get(key);
            return element == TomlOps.EMPTY ? null : element;
         }

         public Stream<Pair<Object, Object>> entries() {
            return object.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue()));
         }

         @Override
         public String toString() {
            return "MapLike[" + object + "]";
         }
      });
   }

   public Object createMap(Stream<Pair<Object, Object>> map) {
      Map<String, Object> result = new LinkedHashMap<>();
      map.forEach(p -> result.put(Objects.toString(p.getFirst()), p.getSecond()));
      return result;
   }

   public DataResult<Stream<Object>> getStream(Object input) {
      if (input instanceof List) {
         List<Object> list = asList(input);
         return DataResult.success(list.stream().map(e -> e == EMPTY ? null : e));
      } else {
         return DataResult.error(() -> "Not a list: " + input);
      }
   }

   public DataResult<Consumer<Consumer<Object>>> getList(Object input) {
      if (input instanceof List) {
         List<Object> list = asList(input);
         return DataResult.success((Consumer<Consumer>)c -> {
            for (Object element : list) {
               c.accept(element == EMPTY ? null : element);
            }
         });
      } else {
         return DataResult.error(() -> "Not a list: " + input);
      }
   }

   public Object createList(Stream<Object> input) {
      List<Object> result = new ArrayList<>();
      input.forEach(result::add);
      return result;
   }

   public Object remove(Object input, String key) {
      if (input instanceof Map) {
         Map<String, Object> result = new LinkedHashMap<>();
         asMap(input).entrySet().stream().filter(entry -> !Objects.equals(entry.getKey(), key)).forEach(entry -> result.put(entry.getKey(), entry.getValue()));
         return result;
      } else {
         return input;
      }
   }

   @Override
   public String toString() {
      return "TOML";
   }

   public ListBuilder<Object> listBuilder() {
      return new TomlOps.ArrayBuilder();
   }

   public boolean compressMaps() {
      return this.compressed;
   }

   public RecordBuilder<Object> mapBuilder() {
      return new TomlOps.TomlRecordBuilder();
   }

   private static final class ArrayBuilder implements ListBuilder<Object> {
      private DataResult<List<Object>> builder = DataResult.success(new ArrayList(), Lifecycle.stable());

      public DynamicOps<Object> ops() {
         return TomlOps.INSTANCE;
      }

      public ListBuilder<Object> add(Object value) {
         this.builder = this.builder.map(b -> {
            b.add(value);
            return b;
         });
         return this;
      }

      public ListBuilder<Object> add(DataResult<Object> value) {
         this.builder = this.builder.apply2stable((b, element) -> {
            b.add(element);
            return b;
         }, value);
         return this;
      }

      public ListBuilder<Object> withErrorsFrom(DataResult<?> result) {
         this.builder = this.builder.flatMap(r -> result.map(v -> r));
         return this;
      }

      public ListBuilder<Object> mapError(UnaryOperator<String> onError) {
         this.builder = this.builder.mapError(onError);
         return this;
      }

      public DataResult<Object> build(Object prefix) {
         DataResult<Object> result = this.builder.flatMap(b -> {
            if (!(prefix instanceof List) && prefix != this.ops().empty()) {
               return DataResult.error(() -> "Cannot append a list to not a list: " + prefix, prefix);
            }

            List<Object> array = new ArrayList<>();
            if (prefix != this.ops().empty()) {
               array.addAll(TomlOps.asList(prefix));
            }

            array.addAll(b);
            return DataResult.success(array, Lifecycle.stable());
         });
         this.builder = DataResult.success(new ArrayList(), Lifecycle.stable());
         return result;
      }
   }

   private class TomlRecordBuilder extends AbstractStringBuilder<Object, Map<String, Object>> {
      protected TomlRecordBuilder() {
         super(TomlOps.this);
      }

      protected Map<String, Object> initBuilder() {
         return new LinkedHashMap<>();
      }

      protected Map<String, Object> append(String key, Object value, Map<String, Object> builder) {
         builder.put(key, value);
         return builder;
      }

      protected DataResult<Object> build(Map<String, Object> builder, Object prefix) {
         if (prefix == null || prefix == TomlOps.EMPTY) {
            return DataResult.success(builder);
         } else if (prefix instanceof Map) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.putAll(TomlOps.asMap(prefix));
            result.putAll(builder);
            return DataResult.success(result);
         } else {
            return DataResult.error(() -> "mergeToMap called with not a map: " + prefix, prefix);
         }
      }
   }
}
