package bigchadguys.sellingbin.data.adapter.util;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public class RegistryAdapter<V> implements ISimpleAdapter<V, Tag, JsonElement> {
   private final Supplier<Registry<V>> registry;
   private final boolean nullable;

   public RegistryAdapter(Supplier<Registry<V>> registry, boolean nullable) {
      this.registry = registry;
      this.nullable = nullable;
   }

   public Registry<V> getRegistry() {
      return this.registry.get();
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public RegistryAdapter<V> asNullable() {
      return new RegistryAdapter<>(this.registry, true);
   }

   @Override
   public void writeBits(V value, BitBuffer buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         Adapters.IDENTIFIER.writeBits(this.registry.get().getKey(value), buffer);
      }
   }

   @Override
   public Optional<V> readBits(BitBuffer buffer) {
      if (this.nullable && buffer.readBoolean()) {
         return Optional.empty();
      }

      ResourceLocation id = Adapters.IDENTIFIER.readBits(buffer).orElseThrow();
      return this.getRegistry().getOptional(id);
   }

   @Override
   public void writeBytes(V value, ByteBuf buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         Adapters.IDENTIFIER.writeBytes(this.registry.get().getKey(value), buffer);
      }
   }

   @Override
   public Optional<V> readBytes(ByteBuf buffer) {
      if (this.nullable && buffer.readBoolean()) {
         return Optional.empty();
      }

      ResourceLocation id = Adapters.IDENTIFIER.readBytes(buffer).orElseThrow();
      return this.getRegistry().getOptional(id);
   }

   @Override
   public void writeData(V value, DataOutput data) throws IOException {
      if (this.nullable) {
         data.writeBoolean(value == null);
      }

      if (value != null) {
         Adapters.IDENTIFIER.writeData(this.registry.get().getKey(value), data);
      }
   }

   @Override
   public Optional<V> readData(DataInput data) throws IOException {
      if (this.nullable && data.readBoolean()) {
         return Optional.empty();
      }

      ResourceLocation id = Adapters.IDENTIFIER.readData(data).orElseThrow();
      return this.getRegistry().getOptional(id);
   }

   @Override
   public Optional<Tag> writeNbt(V value) {
      return value == null ? Optional.empty() : Adapters.IDENTIFIER.writeNbt(this.getRegistry().getKey(value));
   }

   @Override
   public Optional<V> readNbt(Tag nbt) {
      ResourceLocation id = Adapters.IDENTIFIER.readNbt(nbt).orElse(null);
      return this.getRegistry().getOptional(id);
   }

   @Override
   public Optional<JsonElement> writeJson(V value) {
      return value == null ? Optional.empty() : Adapters.IDENTIFIER.writeJson(this.getRegistry().getKey(value));
   }

   @Override
   public Optional<V> readJson(JsonElement json) {
      ResourceLocation id = Adapters.IDENTIFIER.readJson(json).orElse(null);
      return this.getRegistry().getOptional(id);
   }
}
