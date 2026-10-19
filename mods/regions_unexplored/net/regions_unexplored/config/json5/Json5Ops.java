package net.regions_unexplored.config.json5;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import de.marhali.json5.Json5Array;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Null;
import de.marhali.json5.Json5Object;
import de.marhali.json5.Json5Primitive;
import de.marhali.json5.config.Json5Options;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class Json5Ops implements DynamicOps<Json5Element> {
   public static final Json5Ops INSTANCE = new Json5Ops();
   public static final Json5Options OPTIONS = Json5Options.builder().prettyPrinting().writeComments().build();

   private Json5Ops() {
   }

   private static Json5Object sorted(Json5Object object) {
      Json5Object newObject = new Json5Object();
      object.entrySet().stream().sorted(Entry.comparingByKey()).forEach(entry -> newObject.add((String)entry.getKey(), (Json5Element)entry.getValue()));
      return newObject;
   }

   public Json5Element empty() {
      return new Json5Null();
   }

   public <U> U convertTo(DynamicOps<U> outOps, Json5Element input) {
      if (input instanceof Json5Object) {
         return (U)this.convertMap(outOps, input);
      }

      if (input instanceof Json5Array) {
         return (U)this.convertList(outOps, input);
      }

      if (input instanceof Json5Null) {
         return (U)outOps.empty();
      }

      Json5Primitive primitive = input.getAsJson5Primitive();
      if (primitive.isString()) {
         return (U)outOps.createString(primitive.getAsString());
      }

      if (primitive.isBoolean()) {
         return (U)outOps.createBoolean(primitive.getAsBoolean());
      }

      BigDecimal value = primitive.getAsBigDecimal();

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
   }

   public DataResult<Number> getNumberValue(Json5Element input) {
      if (input instanceof Json5Primitive) {
         if (input.getAsJson5Primitive().isNumber()) {
            return DataResult.success(input.getAsNumber());
         }

         if (input.getAsJson5Primitive().isString()) {
            try {
               return DataResult.success(Integer.parseInt(input.getAsString()));
            } catch (NumberFormatException e) {
               return DataResult.error(() -> "Not a number: " + e + " " + input);
            }
         }
      }

      return DataResult.error(() -> "Not a number: " + input);
   }

   public Json5Element createNumeric(Number i) {
      return Json5Primitive.fromNumber(i);
   }

   public DataResult<Boolean> getBooleanValue(Json5Element input) {
      return input instanceof Json5Primitive && input.getAsJson5Primitive().isBoolean()
         ? DataResult.success(input.getAsBoolean())
         : DataResult.error(() -> "Not a boolean: " + input);
   }

   public Json5Element createBoolean(boolean value) {
      return Json5Primitive.fromBoolean(value);
   }

   public DataResult<String> getStringValue(Json5Element input) {
      return input instanceof Json5Primitive && input.getAsJson5Primitive().isString()
         ? DataResult.success(input.getAsString())
         : DataResult.error(() -> "Not a string: " + input);
   }

   public Json5Element createString(String value) {
      return Json5Primitive.fromString(value);
   }

   public DataResult<Json5Element> mergeToList(Json5Element list, Json5Element value) {
      if (!(list instanceof Json5Array) && list != this.empty()) {
         return DataResult.error(() -> "mergeToList called with not a list: " + list, list);
      }

      Json5Array result = new Json5Array();
      if (list != this.empty()) {
         result.addAll(list.getAsJson5Array());
      }

      result.add(value);
      return DataResult.success(result);
   }

   public DataResult<Json5Element> mergeToList(Json5Element list, List<Json5Element> values) {
      if (!(list instanceof Json5Array) && list != this.empty()) {
         return DataResult.error(() -> "mergeToList called with not a list: " + list, list);
      }

      Json5Array result = new Json5Array();
      if (list != this.empty()) {
         result.addAll(list.getAsJson5Array());
      }

      values.forEach(result::add);
      return DataResult.success(result);
   }

   public DataResult<Json5Element> mergeToMap(Json5Element map, Json5Element key, Json5Element value) {
      if (!(map instanceof Json5Object) && map != this.empty()) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + map, map);
      }

      if (key instanceof Json5Primitive && key.getAsJson5Primitive().isString()) {
         Json5Object output = new Json5Object();
         if (map != this.empty()) {
            map.getAsJson5Object().entrySet().forEach(entry -> output.add((String)entry.getKey(), (Json5Element)entry.getValue()));
         }

         output.add(key.getAsString(), value);
         return DataResult.success(sorted(output));
      } else {
         return DataResult.error(() -> "key is not a string: " + key, map);
      }
   }

   public DataResult<Json5Element> mergeToMap(Json5Element map, MapLike<Json5Element> values) {
      if (!(map instanceof Json5Object) && map != this.empty()) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + map, map);
      }

      Json5Object output = new Json5Object();
      if (map != this.empty()) {
         map.getAsJson5Object().entrySet().forEach(entry -> output.add((String)entry.getKey(), (Json5Element)entry.getValue()));
      }

      List<Json5Element> missed = Lists.newArrayList();
      values.entries().forEach(entry -> {
         Json5Element key = (Json5Element)entry.getFirst();
         if (key instanceof Json5Primitive && key.getAsJson5Primitive().isString()) {
            output.add(key.getAsString(), (Json5Element)entry.getSecond());
         } else {
            missed.add(key);
         }
      });
      return !missed.isEmpty() ? DataResult.error(() -> "some keys are not strings: " + missed, output) : DataResult.success(sorted(output));
   }

   public DataResult<Stream<Pair<Json5Element, Json5Element>>> getMapValues(Json5Element input) {
      return !(input instanceof Json5Object)
         ? DataResult.error(() -> "Not a JSON object: " + input)
         : DataResult.success(
            input.getAsJson5Object()
               .entrySet()
               .stream()
               .map(
                  entry -> Pair.of(
                     Json5Primitive.fromString((String)entry.getKey()), entry.getValue() instanceof Json5Null ? null : (Json5Element)entry.getValue()
                  )
               )
         );
   }

   public DataResult<Consumer<BiConsumer<Json5Element, Json5Element>>> getMapEntries(Json5Element input) {
      return !(input instanceof Json5Object) ? DataResult.error(() -> "Not a JSON object: " + input) : DataResult.success((Consumer<BiConsumer>)c -> {
         for (Entry<String, Json5Element> entry : input.getAsJson5Object().entrySet()) {
            c.accept(this.createString(entry.getKey()), entry.getValue() instanceof Json5Null ? null : entry.getValue());
         }
      });
   }

   public DataResult<MapLike<Json5Element>> getMap(Json5Element input) {
      if (!(input instanceof Json5Object)) {
         return DataResult.error(() -> "Not a JSON object: " + input);
      }

      final Json5Object object = input.getAsJson5Object();
      return DataResult.success(new MapLike<Json5Element>() {
         @Nullable
         public Json5Element get(Json5Element key) {
            Json5Element element = object.get(key.getAsString());
            return element instanceof Json5Null ? null : element;
         }

         @Nullable
         public Json5Element get(String key) {
            Json5Element element = object.get(key);
            return element instanceof Json5Null ? null : element;
         }

         public Stream<Pair<Json5Element, Json5Element>> entries() {
            return object.entrySet().stream().map(e -> Pair.of(Json5Primitive.fromString((String)e.getKey()), (Json5Element)e.getValue()));
         }

         @Override
         public String toString() {
            return "MapLike[" + object + "]";
         }
      });
   }

   public Json5Element createMap(Stream<Pair<Json5Element, Json5Element>> map) {
      Json5Object result = new Json5Object();
      map.forEach(p -> result.add(((Json5Element)p.getFirst()).getAsString(), (Json5Element)p.getSecond()));
      return sorted(result);
   }

   public DataResult<Stream<Json5Element>> getStream(Json5Element input) {
      return input instanceof Json5Array
         ? DataResult.success(
            StreamSupport.<Json5Element>stream(input.getAsJson5Array().spliterator(), false).map(e -> (Json5Element)(e instanceof Json5Null ? null : e))
         )
         : DataResult.error(() -> "Not a json array: " + input);
   }

   public DataResult<Consumer<Consumer<Json5Element>>> getList(Json5Element input) {
      return input instanceof Json5Array ? DataResult.success((Consumer<Consumer>)c -> {
         for (Json5Element element : input.getAsJson5Array()) {
            c.accept(element instanceof Json5Null ? null : element);
         }
      }) : DataResult.error(() -> "Not a json array: " + input);
   }

   public Json5Element createList(Stream<Json5Element> input) {
      Json5Array result = new Json5Array();
      input.forEach(result::add);
      return result;
   }

   public Json5Element remove(Json5Element input, String key) {
      if (input instanceof Json5Object) {
         Json5Object result = new Json5Object();
         input.getAsJson5Object()
            .entrySet()
            .stream()
            .filter(entry -> !Objects.equals(entry.getKey(), key))
            .forEach(entry -> result.add((String)entry.getKey(), (Json5Element)entry.getValue()));
         return result;
      } else {
         return input;
      }
   }

   @Override
   public String toString() {
      return "JSON5";
   }

   public ListBuilder<Json5Element> listBuilder() {
      return new Json5Ops.ArrayBuilder();
   }

   public boolean compressMaps() {
      return false;
   }

   public RecordBuilder<Json5Element> mapBuilder() {
      return new Json5Ops.Json5RecordBuilder();
   }

   private static final class ArrayBuilder implements ListBuilder<Json5Element> {
      private DataResult<Json5Array> builder = DataResult.success(new Json5Array(), Lifecycle.stable());

      public DynamicOps<Json5Element> ops() {
         return Json5Ops.INSTANCE;
      }

      public ListBuilder<Json5Element> add(Json5Element value) {
         this.builder = this.builder.map(b -> {
            b.add(value);
            return b;
         });
         return this;
      }

      public ListBuilder<Json5Element> add(DataResult<Json5Element> value) {
         this.builder = this.builder.apply2stable((b, element) -> {
            b.add(element);
            return b;
         }, value);
         return this;
      }

      public ListBuilder<Json5Element> withErrorsFrom(DataResult<?> result) {
         this.builder = this.builder.flatMap(r -> result.map(__ -> r));
         return this;
      }

      public ListBuilder<Json5Element> mapError(UnaryOperator<String> onError) {
         this.builder = this.builder.mapError(onError);
         return this;
      }

      public DataResult<Json5Element> build(Json5Element prefix) {
         DataResult<Json5Element> result = this.builder.flatMap(b -> {
            if (!(prefix instanceof Json5Array) && !(prefix instanceof Json5Null)) {
               return DataResult.error(() -> "Cannot append a list to not a list: " + prefix, prefix);
            }

            Json5Array array = new Json5Array();
            if (!(prefix instanceof Json5Null)) {
               array.addAll(prefix.getAsJson5Array());
            }

            array.addAll(b);
            return DataResult.success(array, Lifecycle.stable());
         });
         this.builder = DataResult.success(new Json5Array(), Lifecycle.stable());
         return result;
      }
   }

   public class Json5RecordBuilder extends AbstractStringBuilder<Json5Element, Json5Object> {
      private final Map<String, String> comments = new HashMap<>();

      protected Json5RecordBuilder() {
         super(Json5Ops.this);
      }

      protected Json5Object initBuilder() {
         return new Json5Object();
      }

      protected Json5Object append(String key, Json5Element value, Json5Object builder) {
         builder.add(key, value);
         return builder;
      }

      protected DataResult<Json5Element> build(Json5Object builder, Json5Element prefix) {
         if (prefix != null && !(prefix instanceof Json5Null)) {
            if (prefix instanceof Json5Object) {
               Json5Object result = new Json5Object();

               for (Entry<String, Json5Element> entry : prefix.getAsJson5Object().entrySet()) {
                  String key = entry.getKey();
                  result.add(key, entry.getValue());
                  if (this.comments.containsKey(key)) {
                     result.setComment(this.comments.get(key));
                  }
               }

               for (Entry<String, Json5Element> entry : builder.entrySet()) {
                  String key = entry.getKey();
                  result.add(key, entry.getValue());
                  if (this.comments.containsKey(key)) {
                     result.setComment(this.comments.get(key));
                  }
               }

               return DataResult.success(Json5Ops.sorted(result));
            } else {
               return DataResult.error(() -> "mergeToMap called with not a map: " + prefix, prefix);
            }
         } else {
            this.comments.forEach((keyx, comment) -> {
               if (builder.has(keyx)) {
                  builder.get(keyx).setComment(comment);
               }
            });
            return DataResult.success(Json5Ops.sorted(builder));
         }
      }

      public void putComment(String key, String comment) {
         this.comments.put(key, comment);
      }
   }
}
