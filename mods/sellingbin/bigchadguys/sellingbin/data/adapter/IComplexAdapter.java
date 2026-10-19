package bigchadguys.sellingbin.data.adapter;

import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import net.minecraft.nbt.Tag;

public interface IComplexAdapter<T, N extends Tag, J extends JsonElement, C> extends IAdapter<T, N, J, C> {
   default void writeBits(T value, BitBuffer buffer) {
      this.writeBits(value, buffer, null);
   }

   default Optional<T> readBits(BitBuffer buffer) {
      return this.readBits(buffer, null);
   }

   default void writeBytes(T value, ByteBuf buffer) {
      this.writeBytes(value, buffer, null);
   }

   default Optional<T> readBytes(ByteBuf buffer) {
      return this.readBytes(buffer, null);
   }

   default void writeData(T value, DataOutput data) throws IOException {
      this.writeData(value, data, null);
   }

   default Optional<T> readData(DataInput data) throws IOException {
      return this.readData(data, null);
   }

   default Optional<N> writeNbt(T value) {
      return this.writeNbt(value, null);
   }

   default Optional<T> readNbt(N nbt) {
      return this.readNbt(nbt, null);
   }

   default Optional<J> writeJson(T value) {
      return this.writeJson(value, null);
   }

   default Optional<T> readJson(J json) {
      return this.readJson(json, null);
   }

   @Override
   void writeBits(T var1, BitBuffer var2, C var3);

   @Override
   Optional<T> readBits(BitBuffer var1, C var2);

   @Override
   void writeBytes(T var1, ByteBuf var2, C var3);

   @Override
   Optional<T> readBytes(ByteBuf var1, C var2);

   @Override
   void writeData(T var1, DataOutput var2, C var3) throws IOException;

   @Override
   Optional<T> readData(DataInput var1, C var2) throws IOException;

   @Override
   Optional<N> writeNbt(T var1, C var2);

   @Override
   Optional<T> readNbt(N var1, C var2);

   @Override
   Optional<J> writeJson(T var1, C var2);

   @Override
   Optional<T> readJson(J var1, C var2);

   @Override
   default JsonElement serialize(T value, Type source, JsonSerializationContext context) {
      return this.writeJson(value).map(json -> (JsonElement)json).orElse(JsonNull.INSTANCE);
   }

   @Override
   default T deserialize(JsonElement json, Type source, JsonDeserializationContext context) {
      return this.readJson((J)json).orElse(null);
   }
}
