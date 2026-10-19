package com.teamresourceful.resourcefullib.common.codecs.yabn;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.teamresourceful.yabn.YabnCompressor;
import com.teamresourceful.yabn.elements.YabnArray;
import com.teamresourceful.yabn.elements.YabnElement;
import com.teamresourceful.yabn.elements.YabnObject;
import com.teamresourceful.yabn.elements.YabnPrimitive;
import com.teamresourceful.yabn.elements.primitives.BooleanContents;
import com.teamresourceful.yabn.elements.primitives.ByteContents;
import com.teamresourceful.yabn.elements.primitives.DoubleContents;
import com.teamresourceful.yabn.elements.primitives.FloatContents;
import com.teamresourceful.yabn.elements.primitives.IntContents;
import com.teamresourceful.yabn.elements.primitives.LongContents;
import com.teamresourceful.yabn.elements.primitives.PrimitiveContents;
import com.teamresourceful.yabn.elements.primitives.PrimitiveNumberContents;
import com.teamresourceful.yabn.elements.primitives.ShortContents;
import com.teamresourceful.yabn.elements.primitives.StringContents;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class YabnOps implements DynamicOps<YabnElement> {
   public static final YabnOps INSTANCE = new YabnOps(false);
   public static final YabnOps COMPRESSED = new YabnOps(true);
   private final boolean compressed;

   protected YabnOps(boolean compressed) {
      this.compressed = compressed;
   }

   public YabnElement empty() {
      return YabnPrimitive.ofNull();
   }

   public <U> U convertTo(DynamicOps<U> outOps, YabnElement input) {
      if (input instanceof YabnObject) {
         return (U)this.convertMap(outOps, input);
      }

      if (input instanceof YabnArray) {
         return (U)this.convertList(outOps, input);
      }

      if (this.compressed) {
         input = YabnCompressor.compress(input);
      }

      if (input.isNull()) {
         return (U)outOps.empty();
      }

      if (input instanceof YabnPrimitive primitive) {
         PrimitiveContents contents = primitive.contents();
         if (contents instanceof StringContents stringContents) {
            return (U)outOps.createString(stringContents.value());
         } else if (contents instanceof BooleanContents booleanContents) {
            return (U)outOps.createBoolean(booleanContents.value());
         } else if (contents instanceof ByteContents byteContents) {
            return (U)outOps.createByte(byteContents.value());
         } else if (contents instanceof ShortContents shortContents) {
            return (U)outOps.createShort(shortContents.value());
         } else if (contents instanceof IntContents intContents) {
            return (U)outOps.createInt(intContents.value());
         } else if (contents instanceof LongContents longContents) {
            return (U)outOps.createLong(longContents.value());
         } else if (contents instanceof DoubleContents doubleContents) {
            return (U)outOps.createDouble(doubleContents.value());
         } else if (contents instanceof FloatContents floatContents) {
            return (U)outOps.createFloat(floatContents.value());
         } else {
            throw new IllegalStateException("Unknown primitive contents: " + contents);
         }
      } else {
         throw new IllegalArgumentException("Unknown type: " + input.getClass());
      }
   }

   public DataResult<Number> getNumberValue(YabnElement input) {
      if (input instanceof YabnPrimitive primitive) {
         PrimitiveContents contents = primitive.contents();
         if (contents instanceof PrimitiveNumberContents num) {
            return DataResult.success(num.getValue());
         }

         if (contents instanceof BooleanContents bool) {
            return DataResult.success(bool.value() ? 1 : 0);
         }

         if (this.compressed && contents instanceof StringContents str) {
            try {
               return DataResult.success(Integer.parseInt(str.value()));
            } catch (NumberFormatException e) {
               return DataResult.error(() -> "Not a number: " + e + " " + input);
            }
         }
      }

      return DataResult.error(() -> "Not a number: " + input);
   }

   public YabnElement createNumeric(Number i) {
      BigDecimal value = new BigDecimal(i.toString());

      try {
         return YabnCompressor.compressNonFloatingNumber(value.longValueExact());
      } catch (ArithmeticException e) {
         return YabnCompressor.compressFloatingNumber(value.doubleValue());
      }
   }

   public DataResult<Boolean> getBooleanValue(YabnElement input) {
      if (input instanceof YabnPrimitive primitive) {
         PrimitiveContents contents = primitive.contents();
         if (contents instanceof BooleanContents bool) {
            return DataResult.success(bool.value());
         }

         if (contents instanceof PrimitiveNumberContents num) {
            return DataResult.success(num.getAsByte() != 0);
         }
      }

      return DataResult.error(() -> "Not a boolean: " + input);
   }

   public YabnElement createBoolean(boolean value) {
      return YabnPrimitive.ofBoolean(value);
   }

   public DataResult<String> getStringValue(YabnElement input) {
      if (input instanceof YabnPrimitive primitive) {
         if (primitive.contents() instanceof StringContents contents) {
            return DataResult.success(contents.value());
         }

         if (this.compressed && primitive.contents() instanceof PrimitiveNumberContents contents) {
            return DataResult.success(contents.getValue().toString());
         }
      }

      return DataResult.error(() -> "Not a string: " + input);
   }

   public YabnElement createString(String value) {
      return YabnPrimitive.ofString(value);
   }

   public DataResult<YabnElement> mergeToList(YabnElement list, YabnElement value) {
      return this.mergeToList(list, List.of(value));
   }

   public DataResult<YabnElement> mergeToList(YabnElement list, List<YabnElement> values) {
      if (!(list instanceof YabnArray) && list != this.empty()) {
         return DataResult.error(() -> "Not a list: " + list);
      }

      YabnArray array = new YabnArray();
      if (list instanceof YabnArray oldArray) {
         oldArray.elements().forEach(array::add);
      }

      values.forEach(array::add);
      return DataResult.success(array);
   }

   public DataResult<YabnElement> mergeToMap(YabnElement map, YabnElement key, YabnElement value) {
      if (!(map instanceof YabnObject) && map != this.empty()) {
         return DataResult.error(() -> "Not a map: " + map, map);
      }

      if (key instanceof YabnPrimitive primitive && !primitive.isNull() && (primitive.contents() instanceof StringContents || this.compressed)) {
         YabnObject object = new YabnObject();
         if (map instanceof YabnObject oldObject) {
            oldObject.elements().forEach(object::put);
         }

         object.elements().put(getAsString(primitive), value);
         return DataResult.success(object);
      } else {
         return DataResult.error(() -> "key is not a string: " + key, map);
      }
   }

   public DataResult<YabnElement> mergeToMap(YabnElement map, MapLike<YabnElement> values) {
      if (!(map instanceof YabnObject) && map != this.empty()) {
         return DataResult.error(() -> "Not a map: " + map, map);
      }

      YabnObject object = new YabnObject();
      if (map instanceof YabnObject oldObject) {
         oldObject.elements().forEach(object::put);
      }

      List<YabnElement> missed = Lists.newArrayList();
      values.entries().forEach(entry -> {
         YabnElement key = (YabnElement)entry.getFirst();
         if (key instanceof YabnPrimitive primitive && !primitive.isNull() && (primitive.contents() instanceof StringContents || this.compressed)) {
            object.put(getAsString(primitive), (YabnElement)entry.getSecond());
         } else {
            missed.add(key);
         }
      });
      return !missed.isEmpty() ? DataResult.error(() -> "some keys are not strings: " + missed, object) : DataResult.success(object);
   }

   public DataResult<Stream<Pair<YabnElement, YabnElement>>> getMapValues(YabnElement input) {
      if (input instanceof YabnObject object) {
         Stream<Pair<YabnElement, YabnElement>> output = object.elements()
            .entrySet()
            .stream()
            .map(entry -> Pair.of(this.createString((String)entry.getKey()), (YabnElement)entry.getValue()));
         return DataResult.success(output);
      } else {
         return DataResult.error(() -> "Not a YABN Object: " + input);
      }
   }

   public DataResult<Consumer<BiConsumer<YabnElement, YabnElement>>> getMapEntries(YabnElement input) {
      return input instanceof YabnObject object
         ? DataResult.success((Consumer<BiConsumer>)c -> object.elements().forEach((key, value) -> c.accept(this.createString(key), value)))
         : DataResult.error(() -> "Not a YABN Object: " + input);
   }

   public DataResult<MapLike<YabnElement>> getMap(YabnElement input) {
      return input instanceof YabnObject object ? DataResult.success(new YabnObjectMapLike(object)) : DataResult.error(() -> "Not a YABN Object: " + input);
   }

   public YabnElement createMap(Stream<Pair<YabnElement, YabnElement>> map) {
      YabnObject object = new YabnObject();
      map.forEach(p -> object.put((String)this.getStringValue((YabnElement)p.getFirst()).result().get(), (YabnElement)p.getSecond()));
      return object;
   }

   public DataResult<Stream<YabnElement>> getStream(YabnElement input) {
      return input instanceof YabnArray array ? DataResult.success(array.elements().stream()) : DataResult.error(() -> "Not a YABN Array: " + input);
   }

   public DataResult<Consumer<Consumer<YabnElement>>> getList(YabnElement input) {
      return input instanceof YabnArray array
         ? DataResult.success((Consumer<Consumer>)c -> array.elements().forEach(c))
         : DataResult.error(() -> "Not a YABN Array: " + input);
   }

   public YabnElement createList(Stream<YabnElement> input) {
      YabnArray array = new YabnArray();
      input.forEach(array::add);
      return array;
   }

   public YabnElement remove(YabnElement input, String key) {
      if (input instanceof YabnObject object) {
         object.elements().remove(key);
      }

      return input;
   }

   @Override
   public String toString() {
      return "YABN";
   }

   public ListBuilder<YabnElement> listBuilder() {
      return new YabnArrayBuilder();
   }

   public boolean compressMaps() {
      return this.compressed;
   }

   public RecordBuilder<YabnElement> mapBuilder() {
      return new YabnRecordBuilder(this);
   }

   public static String getAsString(YabnPrimitive primitive) {
      PrimitiveContents contents = primitive.contents();
      if (contents instanceof StringContents stringContents) {
         return stringContents.value();
      } else if (contents instanceof PrimitiveNumberContents numberContents) {
         return numberContents.getValue().toString();
      } else if (contents instanceof BooleanContents booleanContents) {
         return booleanContents.value() ? "true" : "false";
      } else {
         return null;
      }
   }
}
