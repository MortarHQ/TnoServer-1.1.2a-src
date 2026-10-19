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
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;

public class IntArrayAdapter implements ISimpleAdapter<int[], Tag, JsonArray> {
   private final Object elementAdapter;
   private final boolean nullable;

   public IntArrayAdapter(Object elementAdapter, boolean nullable) {
      this.elementAdapter = elementAdapter;
      this.nullable = nullable;
   }

   public Object getElementAdapter() {
      return this.elementAdapter;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public IntArrayAdapter asNullable() {
      return new IntArrayAdapter(this.elementAdapter, true);
   }

   public final void writeBits(int[] value, BitBuffer buffer) {
      if (this.elementAdapter instanceof IBitAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeBits(value.length, buffer);

            for (int element : value) {
               adapter.writeBits(element, buffer, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<int[]> readBits(BitBuffer buffer) {
      if (!(this.elementAdapter instanceof IBitAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         int[] value = new int[Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBits(buffer, null).orElse(0);
         }

         return Optional.of(value);
      }
   }

   public final void writeBytes(int[] value, ByteBuf buffer) {
      if (this.elementAdapter instanceof IByteAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeBytes(value.length, buffer);

            for (int element : value) {
               adapter.writeBytes(element, buffer, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<int[]> readBytes(ByteBuf buffer) {
      if (!(this.elementAdapter instanceof IByteAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         int[] value = new int[Adapters.INT_SEGMENTED_7.readBytes(buffer).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBytes(buffer, null).orElse(0);
         }

         return Optional.of(value);
      }
   }

   public void writeData(int[] value, DataOutput data) throws IOException {
      if (this.elementAdapter instanceof IDataAdapter adapter) {
         if (this.nullable) {
            data.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_7.writeData(value.length, data);

            for (int element : value) {
               adapter.writeData(element, data, null);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public Optional<int[]> readData(DataInput data) throws IOException {
      if (!(this.elementAdapter instanceof IDataAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && data.readBoolean()) {
            return Optional.empty();
         }

         int[] value = new int[Adapters.INT_SEGMENTED_7.readData(data).orElseThrow()];

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readData(data, null).orElse(0);
         }

         return Optional.of(value);
      }
   }

   public final Optional<Tag> writeNbt(int[] value) {
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

         if (Arrays.stream(tags).allMatch(element -> element.getAsByte() == element.getAsInt())) {
            byte[] bytes = new byte[tags.length];

            for (int i = 0; i < tags.length; i++) {
               bytes[i] = tags[i].getAsByte();
            }

            return Optional.of(new ByteArrayTag(bytes));
         } else {
            int[] ints = new int[tags.length];

            for (int i = 0; i < tags.length; i++) {
               ints[i] = tags[i].getAsInt();
            }

            return Optional.of(new IntArrayTag(ints));
         }
      }
   }

   @Override
   public final Optional<int[]> readNbt(Tag nbt) {
      if (!(this.elementAdapter instanceof INbtAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else if (nbt instanceof NumericTag numeric) {
         return Optional.of(new int[]{numeric.getAsInt()});
      } else if (!(nbt instanceof CollectionTag<?> array)) {
         return Optional.empty();
      } else {
         int[] value = new int[array.size()];

         for (int i = 0; i < array.size(); i++) {
            value[i] = adapter.readNbt((Tag)array.get(i), null).orElse(0);
         }

         return Optional.of(value);
      }
   }

   public final Optional<JsonArray> writeJson(int[] value) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (value == null) {
            return Optional.empty();
         }

         JsonPrimitive[] primitives = new JsonPrimitive[value.length];

         for (int i = 0; i < value.length; i++) {
            primitives[i] = adapter.writeJson(value[i], null).orElseGet(() -> new JsonPrimitive(0));
         }

         JsonArray ints = new JsonArray(primitives.length);

         for (JsonPrimitive primitive : primitives) {
            ints.add(primitive.getAsInt());
         }

         return Optional.of(ints);
      }
   }

   public final Optional<int[]> readJson(JsonArray json) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (json == null) {
            return Optional.empty();
         }

         int[] value = new int[json.size()];

         for (int i = 0; i < json.size(); i++) {
            value[i] = adapter.readJson(json.get(i), null).orElse(0);
         }

         return Optional.of(value);
      }
   }
}
