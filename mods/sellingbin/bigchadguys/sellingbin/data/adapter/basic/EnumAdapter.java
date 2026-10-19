package bigchadguys.sellingbin.data.adapter.basic;

import bigchadguys.sellingbin.data.adapter.Adapters;
import bigchadguys.sellingbin.data.adapter.ISimpleAdapter;
import bigchadguys.sellingbin.data.adapter.number.IntAdapter;
import bigchadguys.sellingbin.data.bit.BitBuffer;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.nbt.Tag;

public class EnumAdapter<E extends Enum<E>> implements ISimpleAdapter<E, Tag, JsonElement> {
   private static final IntAdapter ORDINAL = Adapters.INT_SEGMENTED_3;
   private static final StringAdapter NAME = Adapters.UTF_8;
   private final Class<E> type;
   private final EnumAdapter.Mode mode;
   private final boolean nullable;

   public EnumAdapter(Class<E> type, EnumAdapter.Mode mode, boolean nullable) {
      this.type = type;
      this.mode = mode;
      this.nullable = nullable;
   }

   public Class<E> getType() {
      return this.type;
   }

   public EnumAdapter.Mode getMode() {
      return this.mode;
   }

   public boolean isNullable() {
      return this.nullable;
   }

   public EnumAdapter<E> asNullable() {
      return new EnumAdapter<>(this.type, this.mode, true);
   }

   public void writeBits(E value, BitBuffer buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         if (this.mode == EnumAdapter.Mode.ORDINAL) {
            ORDINAL.writeBits(value.ordinal(), buffer);
         } else if (this.mode == EnumAdapter.Mode.NAME) {
            NAME.writeBits(value.name(), buffer);
         }
      }
   }

   @Override
   public Optional<E> readBits(BitBuffer buffer) {
      if (this.nullable && buffer.readBoolean()) {
         return Optional.empty();
      }

      if (this.mode == EnumAdapter.Mode.ORDINAL) {
         return Optional.of(this.type.getEnumConstants()[ORDINAL.readBits(buffer).orElseThrow()]);
      }

      if (this.mode == EnumAdapter.Mode.NAME) {
         try {
            return Optional.of(Enum.valueOf(this.type, NAME.readBits(buffer).orElseThrow()));
         } catch (IllegalArgumentException exception) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public void writeBytes(E value, ByteBuf buffer) {
      if (this.nullable) {
         buffer.writeBoolean(value == null);
      }

      if (value != null) {
         if (this.mode == EnumAdapter.Mode.ORDINAL) {
            ORDINAL.writeBytes(value.ordinal(), buffer);
         } else if (this.mode == EnumAdapter.Mode.NAME) {
            NAME.writeBytes(value.name(), buffer);
         }
      }
   }

   @Override
   public Optional<E> readBytes(ByteBuf buffer) {
      if (this.nullable && buffer.readBoolean()) {
         return Optional.empty();
      }

      if (this.mode == EnumAdapter.Mode.ORDINAL) {
         return Optional.of(this.type.getEnumConstants()[ORDINAL.readBytes(buffer).orElseThrow()]);
      }

      if (this.mode == EnumAdapter.Mode.NAME) {
         try {
            return Optional.of(Enum.valueOf(this.type, NAME.readBytes(buffer).orElseThrow()));
         } catch (IllegalArgumentException exception) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public void writeData(E value, DataOutput data) throws IOException {
      if (this.nullable) {
         data.writeBoolean(value == null);
      }

      if (value != null) {
         if (this.mode == EnumAdapter.Mode.ORDINAL) {
            ORDINAL.writeData(value.ordinal(), data);
         } else if (this.mode == EnumAdapter.Mode.NAME) {
            NAME.writeData(value.name(), data);
         }
      }
   }

   @Override
   public Optional<E> readData(DataInput data) throws IOException {
      if (this.nullable && data.readBoolean()) {
         return Optional.empty();
      }

      if (this.mode == EnumAdapter.Mode.ORDINAL) {
         return Optional.of(this.type.getEnumConstants()[ORDINAL.readData(data).orElseThrow()]);
      }

      if (this.mode == EnumAdapter.Mode.NAME) {
         try {
            return Optional.of(Enum.valueOf(this.type, NAME.readData(data).orElseThrow()));
         } catch (IllegalArgumentException exception) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public Optional<Tag> writeNbt(E value) {
      if (value == null) {
         return Optional.empty();
      } else if (this.mode == EnumAdapter.Mode.ORDINAL) {
         return ORDINAL.writeNbt(value.ordinal());
      } else {
         return this.mode == EnumAdapter.Mode.NAME ? NAME.writeNbt(value.name()) : Optional.empty();
      }
   }

   @Override
   public Optional<E> readNbt(Tag nbt) {
      if (this.mode == EnumAdapter.Mode.ORDINAL) {
         Optional<Integer> numeric = Adapters.INT.readNbt(nbt);
         if (numeric.isPresent()) {
            return Optional.of(this.type.getEnumConstants()[numeric.get()]);
         }

         numeric = Adapters.UTF_8.readNbt(nbt);
         if (numeric.isPresent()) {
            try {
               return Optional.of(Enum.valueOf(this.type, (String)numeric.get()));
            } catch (IllegalArgumentException exception) {
               return Optional.empty();
            }
         }
      } else if (this.mode == EnumAdapter.Mode.NAME) {
         Optional<String> string = Adapters.UTF_8.readNbt(nbt);
         if (string.isPresent()) {
            try {
               return Optional.of(Enum.valueOf(this.type, string.get()));
            } catch (IllegalArgumentException exception) {
               return Optional.empty();
            }
         }

         string = Adapters.INT.readNbt(nbt);
         if (string.isPresent()) {
            return Optional.of(this.type.getEnumConstants()[string.get()]);
         }
      }

      return Optional.empty();
   }

   public Optional<JsonElement> writeJson(E value) {
      if (value == null) {
         return Optional.empty();
      } else if (this.mode == EnumAdapter.Mode.ORDINAL) {
         return ORDINAL.writeJson(value.ordinal());
      } else {
         return this.mode == EnumAdapter.Mode.NAME ? NAME.writeJson(value.name()) : Optional.empty();
      }
   }

   @Override
   public Optional<E> readJson(JsonElement json) {
      if (this.mode == EnumAdapter.Mode.ORDINAL) {
         Optional<Integer> numeric = Adapters.INT.readJson(json);
         if (numeric.isPresent()) {
            return Optional.of(this.type.getEnumConstants()[numeric.get()]);
         }

         numeric = Adapters.UTF_8.readJson(json);
         if (numeric.isPresent()) {
            try {
               return Optional.of(Enum.valueOf(this.type, (String)numeric.get()));
            } catch (IllegalArgumentException exception) {
               return Optional.empty();
            }
         }
      } else if (this.mode == EnumAdapter.Mode.NAME) {
         Optional<String> string = Adapters.UTF_8.readJson(json);
         if (string.isPresent()) {
            try {
               return Optional.of(Enum.valueOf(this.type, string.get()));
            } catch (IllegalArgumentException exception) {
               return Optional.empty();
            }
         }

         string = Adapters.INT.readJson(json);
         if (string.isPresent()) {
            return Optional.of(this.type.getEnumConstants()[string.get()]);
         }
      }

      return Optional.empty();
   }

   public enum Mode {
      ORDINAL,
      NAME;
   }
}
