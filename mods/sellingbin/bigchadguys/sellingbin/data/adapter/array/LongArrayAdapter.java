package bigchadguys.sellingbin.data.adapter.array;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.IBitAdapter;
import bigchadguys.sellingbin.data.adapter.IByteAdapter;
import bigchadguys.sellingbin.data.adapter.IDataAdapter;
import bigchadguys.sellingbin.data.adapter.IJsonAdapter;
import bigchadguys.sellingbin.data.adapter.INbtAdapter;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;

public class LongArrayAdapter implements ISimpleAdapter<long[], Tag, JsonArray> {
   private final Object elementAdapter;
   private final boolean nullable;

   public LongArrayAdapter(Object elementAdapter, boolean nullable) {
      this.elementAdapter = elementAdapter;
      this.nullable = nullable;
   }

   public Object getElementAdapter() {
      return this.elementAdapter;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public LongArrayAdapter asNullable() {
      return new LongArrayAdapter(this.elementAdapter, true);
   }

   public final void writeBits(long[] value, BitBuffer buffer) {
      if (this.elementAdapter instanceof IBitAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeBits(value.length, buffer);

            for (long element : value) {
               adapter.writeBits(element, buffer, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<long[]> readBits(BitBuffer buffer) {
      if (!(this.elementAdapter instanceof IBitAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         long[] value = new long[Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBits(buffer, null).orElse(0L);
         }

         return Optional.of(value);
      }
   }

   public final void writeBytes(long[] value, ByteBuf buffer) {
      if (this.elementAdapter instanceof IByteAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeBytes(value.length, buffer);

            for (long element : value) {
               adapter.writeBytes(element, buffer, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<long[]> readBytes(ByteBuf buffer) {
      if (!(this.elementAdapter instanceof IByteAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         long[] value = new long[Adapters.INT_SEGMENTED_7.readBytes(buffer).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBytes(buffer, null).orElse(0L);
         }

         return Optional.of(value);
      }
   }

   public void writeData(long[] value, DataOutput data) throws IOException {
      if (this.elementAdapter instanceof IDataAdapter adapter) {
         if (this.nullable) {
            data.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeData(value.length, data);

            for (long element : value) {
               adapter.writeData(element, data, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public Optional<long[]> readData(DataInput data) throws IOException {
      if (!(this.elementAdapter instanceof IDataAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && data.readBoolean()) {
            return Optional.empty();
         }

         long[] value = new long[Adapters.INT_SEGMENTED_7.readData(data).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readData(data, null).orElse(0L);
         }

         return Optional.of(value);
      }
   }

   public final Optional<Tag> writeNbt(long[] value) {
      if (!(this.elementAdapter instanceof INbtAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (value == null) {
            return Optional.empty();
         }

         NumericTag[] tags = new NumericTag[value.length];

         for (int i = 0; i < value.length; i++) {
            tags[i] = (NumericTag)adapter.writeNbt(value[i], null).orElseThrow();
         }

         if (Arrays.stream(tags).allMatch(element -> element.getAsByte() == element.getAsLong())) {
            byte[] bytes = new byte[tags.length];

            for (int i = 0; i < tags.length; i++) {
               bytes[i] = tags[i].getAsByte();
            }

            return Optional.of(new ByteArrayTag(bytes));
         } else if (Arrays.stream(tags).allMatch(element -> element.getAsInt() == element.getAsLong())) {
            int[] bytes = new int[tags.length];

            for (int i = 0; i < tags.length; i++) {
               bytes[i] = tags[i].getAsInt();
            }

            return Optional.of(new IntArrayTag(bytes));
         } else {
            long[] longs = new long[tags.length];

            for (int i = 0; i < tags.length; i++) {
               longs[i] = tags[i].getAsInt();
            }

            return Optional.of(new LongArrayTag(longs));
         }
      }
   }

   @Override
   public final Optional<long[]> readNbt(Tag nbt) {
      if (!(this.elementAdapter instanceof INbtAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else if (nbt instanceof NumericTag numeric) {
         return Optional.of(new long[]{numeric.getAsLong()});
      } else if (!(nbt instanceof CollectionTag<?> array)) {
         return Optional.empty();
      } else {
         long[] value = new long[array.size()];

         for (int i = 0; i < array.size(); i++) {
            value[i] = adapter.readNbt((Tag)array.get(i), null).orElse(0L);
         }

         return Optional.of(value);
      }
   }

   public final Optional<JsonArray> writeJson(long[] value) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (value == null) {
            return Optional.empty();
         }

         JsonPrimitive[] primitives = new JsonPrimitive[value.length];

         for (int i = 0; i < value.length; i++) {
            primitives[i] = adapter.writeJson(value[i], null).orElseGet(() -> new JsonPrimitive(0L));
         }

         JsonArray ints = new JsonArray(primitives.length);

         for (JsonPrimitive primitive : primitives) {
            ints.add(primitive.getAsInt());
         }

         return Optional.of(ints);
      }
   }

   public final Optional<long[]> readJson(JsonArray json) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (json == null) {
            return Optional.empty();
         }

         long[] value = new long[json.size()];

         for (int i = 0; i < json.size(); i++) {
            value[i] = adapter.readJson(json.get(i), null).orElse(0L);
         }

         return Optional.of(value);
      }
   }
}
