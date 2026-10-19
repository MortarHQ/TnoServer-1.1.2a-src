package bigchadguys.sellingbin.data.adapter.array;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.IBitAdapter;
import bigchadguys.sellingbin.data.adapter.IByteAdapter;
import bigchadguys.sellingbin.data.adapter.IComplexAdapter;
import bigchadguys.sellingbin.data.adapter.IDataAdapter;
import bigchadguys.sellingbin.data.adapter.IJsonAdapter;
import bigchadguys.sellingbin.data.adapter.INbtAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class ArrayAdapter<T> implements IComplexAdapter<T[], Tag, JsonElement, Object> {
   private final IntFunction<T[]> constructor;
   private final Object elementAdapter;
   private final Supplier<T> defaultValue;
   private final boolean nullable;

   public ArrayAdapter(IntFunction<T[]> constructor, Object elementAdapter, Supplier<T> defaultValue, boolean nullable) {
      this.constructor = constructor;
      this.elementAdapter = elementAdapter;
      this.defaultValue = defaultValue;
      this.nullable = nullable;
   }

   public IntFunction<T[]> getConstructor() {
      return this.constructor;
   }

   public Object getElementAdapter() {
      return this.elementAdapter;
   }

   public Supplier<T> getDefaultValue() {
      return this.defaultValue;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public ArrayAdapter<T> asNullable() {
      return new ArrayAdapter<>(this.constructor, this.elementAdapter, this.defaultValue, true);
   }

   public final void writeBits(T[] value, BitBuffer buffer, Object context) {
      if (this.elementAdapter instanceof IBitAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_3.writeBits(value.length, buffer);

            for (T element : value) {
               adapter.writeBits(element, buffer, context);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<T[]> readBits(BitBuffer buffer, Object context) {
      if (!(this.elementAdapter instanceof IBitAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         T[] value = (T[])((Object[])this.constructor.apply(Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow()));

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBits(buffer, context).orElseGet(this.defaultValue);
         }

         return Optional.of((T)value);
      }
   }

   public final void writeBytes(T[] value, ByteBuf buffer, Object context) {
      if (this.elementAdapter instanceof IByteAdapter adapter) {
         if (this.nullable) {
            buffer.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_3.writeBytes(value.length, buffer);

            for (T element : value) {
               adapter.writeBytes(element, buffer, context);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public final Optional<T[]> readBytes(ByteBuf buffer, Object context) {
      if (!(this.elementAdapter instanceof IByteAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && buffer.readBoolean()) {
            return Optional.empty();
         }

         T[] value = (T[])((Object[])this.constructor.apply(Adapters.INT_SEGMENTED_3.readBytes(buffer).orElseThrow()));

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readBytes(buffer, context).orElseGet(this.defaultValue);
         }

         return Optional.of((T)value);
      }
   }

   public void writeData(T[] value, DataOutput data, Object context) throws IOException {
      if (this.elementAdapter instanceof IDataAdapter adapter) {
         if (this.nullable) {
            data.writeBoolean(value == null);
         }

         if (value != null) {
            Adapters.INT_SEGMENTED_3.writeData(value.length, data);

            for (T element : value) {
               adapter.writeData(element, data, context);
            }
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public Optional<T[]> readData(DataInput data, Object context) throws IOException {
      if (!(this.elementAdapter instanceof IDataAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (this.nullable && data.readBoolean()) {
            return Optional.empty();
         }

         T[] value = (T[])((Object[])this.constructor.apply(Adapters.INT_SEGMENTED_3.readData(data).orElseThrow()));

         for (int i = 0; i < value.length; i++) {
            value[i] = adapter.readData(data, context).orElseGet(this.defaultValue);
         }

         return Optional.of((T)value);
      }
   }

   public final Optional<Tag> writeNbt(T[] value, Object context) {
      if (!(this.elementAdapter instanceof INbtAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (value == null) {
            return Optional.empty();
         }

         ListTag list = new ListTag();

         for (T element : value) {
            list.add((Tag)adapter.writeNbt(element, context).orElseGet(() -> (T)adapter.writeNbt(this.defaultValue.get(), context).orElseThrow()));
         }

         return Optional.of(list);
      }
   }

   @Override
   public final Optional<T[]> readNbt(Tag nbt, Object context) {
      if (!(this.elementAdapter instanceof INbtAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else if (!(nbt instanceof ListTag list)) {
         return Optional.empty();
      } else {
         Object[] value = (Object[])this.constructor.apply(list.size());

         for (int i = 0; i < list.size(); i++) {
            value[i] = adapter.readNbt(list.get(i), context).orElseGet(this.defaultValue);
         }

         return (Optional<T[]>)Optional.of(value);
      }
   }

   public final Optional<JsonElement> writeJson(T[] value, Object context) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else {
         if (value == null) {
            return Optional.empty();
         }

         JsonArray array = new JsonArray();

         for (T element : value) {
            array.add(adapter.writeJson(element, context).orElseGet(() -> (JsonElement)adapter.writeJson(this.defaultValue.get(), context).orElse(null)));
         }

         return Optional.of(array);
      }
   }

   @Override
   public final Optional<T[]> readJson(JsonElement json, Object context) {
      if (!(this.elementAdapter instanceof IJsonAdapter adapter)) {
         throw new UnsupportedOperationException();
      } else if (json == null) {
         return Optional.empty();
      } else if (!(json instanceof JsonArray array)) {
         return Optional.empty();
      } else {
         T[] value = (T[])((Object[])this.constructor.apply(array.size()));

         for (int i = 0; i < array.size(); i++) {
            value[i] = adapter.readJson(array.get(i), context).orElseGet(this.defaultValue);
         }

         return Optional.of((T)value);
      }
   }
}
