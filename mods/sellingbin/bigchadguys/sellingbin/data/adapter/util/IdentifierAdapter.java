package bigchadguys.sellingbin.data.adapter.util;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

public class IdentifierAdapter implements ISimpleAdapter<ResourceLocation, Tag, JsonElement> {
   private final boolean nullable;

   public IdentifierAdapter(boolean nullable) {
      this.nullable = nullable;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public IdentifierAdapter asNullable() {
      return new IdentifierAdapter(true);
   }

   public void writeBits(ResourceLocation value, BitBuffer buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         buffer.writeIdentifier(value);
      }
   }

   @Override
   public Optional<ResourceLocation> readBits(BitBuffer buffer) {
      return this.nullable && buffer.readBoolean() ? Optional.empty() : Optional.of(buffer.readIdentifier());
   }

   public void writeBytes(ResourceLocation value, ByteBuf buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         String string = value.getNamespace().equals("minecraft") ? value.getPath() : value.toString();
         Adapters.UTF_8.writeBytes(string, buffer);
      }
   }

   @Override
   public Optional<ResourceLocation> readBytes(ByteBuf buffer) {
      return this.nullable && buffer.readBoolean() ? Optional.empty() : Optional.of(ResourceLocation.parse(Adapters.UTF_8.readBytes(buffer).orElseThrow()));
   }

   public void writeData(ResourceLocation value, DataOutput data) throws IOException {
      if (this.nullable) {
         data.writeBoolean(value == null);
      }

      if (value != null) {
         String string = value.getNamespace().equals("minecraft") ? value.getPath() : value.toString();
         Adapters.UTF_8.writeData(string, data);
      }
   }

   @Override
   public Optional<ResourceLocation> readData(DataInput data) throws IOException {
      return this.nullable && data.readBoolean() ? Optional.empty() : Optional.of(ResourceLocation.parse(Adapters.UTF_8.readData(data).orElseThrow()));
   }

   public Optional<Tag> writeNbt(ResourceLocation value) {
      if (value == null) {
         return Optional.empty();
      }

      String string = value.getNamespace().equals("minecraft") ? value.getPath() : value.toString();
      return Optional.of(StringTag.valueOf(string));
   }

   @Override
   public Optional<ResourceLocation> readNbt(Tag nbt) {
      if (nbt instanceof StringTag string) {
         return Optional.ofNullable(ResourceLocation.tryParse(string.getAsString()));
      } else if (nbt instanceof CollectionTag<?> array && array.size() == 1) {
         return this.readNbt((Tag)array.get(0));
      } else if (nbt instanceof CollectionTag<?> array && array.size() == 2) {
         String namespace = Adapters.UTF_8.readNbt((Tag)array.get(0)).orElse(null);
         String path = Adapters.UTF_8.readNbt((Tag)array.get(1)).orElse(null);
         return namespace != null && path != null ? Optional.ofNullable(ResourceLocation.tryParse(namespace + ":" + path)) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   public Optional<JsonElement> writeJson(ResourceLocation value) {
      if (value == null) {
         return Optional.empty();
      }

      String string = value.getNamespace().equals("minecraft") ? value.getPath() : value.toString();
      return Optional.of(new JsonPrimitive(string));
   }

   @Override
   public Optional<ResourceLocation> readJson(JsonElement json) {
      if (json instanceof JsonPrimitive primitive && primitive.isString()) {
         return Optional.ofNullable(ResourceLocation.tryParse(primitive.getAsString()));
      } else if (json instanceof JsonArray array && array.size() == 1) {
         return this.readJson(array.get(0));
      } else if (json instanceof JsonArray array && array.size() == 2) {
         String namespace = Adapters.UTF_8.readJson(array.get(0)).orElse(null);
         String path = Adapters.UTF_8.readJson(array.get(1)).orElse(null);
         return namespace != null && path != null ? Optional.ofNullable(ResourceLocation.tryParse(namespace + ":" + path)) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }
}
