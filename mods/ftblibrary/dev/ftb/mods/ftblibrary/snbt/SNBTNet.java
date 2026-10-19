package dev.ftb.mods.ftblibrary.snbt;

import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public class SNBTNet {
   public static final ByteArrayTag EMPTY_BYTE_ARRAY = new ByteArrayTag(new byte[0]);
   public static final IntArrayTag EMPTY_INT_ARRAY = new IntArrayTag(new int[0]);
   public static final LongArrayTag EMPTY_LONG_ARRAY = new LongArrayTag(new long[0]);

   public static void write(FriendlyByteBuf buf, @Nullable Tag tag) {
      switch (tag) {
         case ByteTag byteTag:
            buf.writeByte(byteTag.getAsByte());
            break;
         case ShortTag shortTag:
            buf.writeShort(shortTag.getAsShort());
            break;
         case IntTag intTag:
            buf.writeInt(intTag.getAsInt());
            break;
         case LongTag longTag:
            buf.writeLong(longTag.getAsLong());
            break;
         case FloatTag floatTag:
            buf.writeFloat(floatTag.getAsFloat());
            break;
         case DoubleTag doubleTag:
            buf.writeDouble(doubleTag.getAsDouble());
            break;
         case ByteArrayTag byteTags:
            writeByteArray(buf, byteTags);
            break;
         case StringTag stringTag:
            buf.writeUtf(stringTag.getAsString(), 32767);
            break;
         case ListTag tags:
            writeList(buf, tags);
            break;
         case CompoundTag compoundTag:
            writeCompound(buf, SNBTCompoundTag.of(compoundTag));
            break;
         case IntArrayTag intTags:
            writeIntArray(buf, intTags);
            break;
         case LongArrayTag longTags:
            writeLongArray(buf, longTags);
            break;
         case null:
         default:
      }
   }

   public static void writeCompound(FriendlyByteBuf buf, @Nullable SNBTCompoundTag tag) {
      if (tag == null) {
         buf.writeVarInt(-1);
      } else {
         buf.writeVarInt(tag.getAllKeys().size());

         for (String s : tag.getAllKeys()) {
            buf.writeUtf(s, 32767);
            buf.writeByte(tag.get(s).getId());
            write(buf, tag.get(s));
         }
      }
   }

   public static void writeList(FriendlyByteBuf buf, @Nullable ListTag tag) {
      if (tag == null) {
         buf.writeVarInt(-1);
      } else {
         buf.writeVarInt(tag.size());
         if (!tag.isEmpty()) {
            buf.writeByte(tag.getElementType());

            for (Tag value : tag) {
               write(buf, value);
            }
         }
      }
   }

   public static void writeByteArray(FriendlyByteBuf buf, @Nullable ByteArrayTag tag) {
      if (tag == null) {
         buf.writeVarInt(-1);
      } else {
         buf.writeVarInt(tag.size());

         for (byte v : tag.getAsByteArray()) {
            buf.writeByte(v);
         }
      }
   }

   public static void writeIntArray(FriendlyByteBuf buf, @Nullable IntArrayTag tag) {
      if (tag == null) {
         buf.writeVarInt(-1);
      } else {
         buf.writeVarInt(tag.size());

         for (int v : tag.getAsIntArray()) {
            buf.writeInt(v);
         }
      }
   }

   public static void writeLongArray(FriendlyByteBuf buf, @Nullable LongArrayTag tag) {
      if (tag == null) {
         buf.writeVarInt(-1);
      } else {
         buf.writeVarInt(tag.size());

         for (long v : tag.getAsLongArray()) {
            buf.writeLong(v);
         }
      }
   }

   @Nullable
   public static Tag read(byte type, FriendlyByteBuf buf) {
      return (Tag)(switch (type) {
         case 0 -> EndTag.INSTANCE;
         case 1 -> ByteTag.valueOf(buf.readByte());
         case 2 -> ShortTag.valueOf(buf.readShort());
         case 3 -> IntTag.valueOf(buf.readInt());
         case 4 -> LongTag.valueOf(buf.readLong());
         case 5 -> FloatTag.valueOf(buf.readFloat());
         case 6 -> DoubleTag.valueOf(buf.readDouble());
         case 7 -> readByteArray(buf);
         case 8 -> StringTag.valueOf(buf.readUtf(32767));
         case 9 -> readList(buf);
         case 10 -> readCompound(buf);
         case 11 -> readIntArray(buf);
         case 12 -> readLongArray(buf);
         default -> null;
      });
   }

   @Nullable
   public static SNBTCompoundTag readCompound(FriendlyByteBuf buf) {
      int len = buf.readVarInt();
      if (len == -1) {
         return null;
      }

      SNBTCompoundTag tag = new SNBTCompoundTag();

      for (int i = 0; i < len; i++) {
         String key = buf.readUtf(32767);
         byte type = buf.readByte();
         tag.put(key, read(type, buf));
      }

      return tag;
   }

   @Nullable
   public static ListTag readList(FriendlyByteBuf buf) {
      int len = buf.readVarInt();
      if (len == -1) {
         return null;
      }

      if (len == 0) {
         return new ListTag();
      }

      byte type = buf.readByte();
      ListTag tag = new ListTag();

      for (int i = 0; i < len; i++) {
         tag.add(read(type, buf));
      }

      return tag;
   }

   public static ByteArrayTag readByteArray(FriendlyByteBuf buf) {
      int len = buf.readVarInt();
      if (len == -1) {
         return null;
      }

      if (len == 0) {
         return EMPTY_BYTE_ARRAY;
      }

      byte[] values = new byte[len];

      for (int i = 0; i < len; i++) {
         values[i] = buf.readByte();
      }

      return new ByteArrayTag(values);
   }

   public static IntArrayTag readIntArray(FriendlyByteBuf buf) {
      int len = buf.readVarInt();
      if (len == -1) {
         return null;
      }

      if (len == 0) {
         return EMPTY_INT_ARRAY;
      }

      int[] values = new int[len];

      for (int i = 0; i < len; i++) {
         values[i] = buf.readInt();
      }

      return new IntArrayTag(values);
   }

   public static LongArrayTag readLongArray(FriendlyByteBuf buf) {
      int len = buf.readVarInt();
      if (len == -1) {
         return null;
      }

      if (len == 0) {
         return EMPTY_LONG_ARRAY;
      }

      long[] values = new long[len];

      for (int i = 0; i < len; i++) {
         values[i] = buf.readLong();
      }

      return new LongArrayTag(values);
   }
}
