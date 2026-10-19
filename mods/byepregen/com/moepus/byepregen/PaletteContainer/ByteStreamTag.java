package com.moepus.byepregen.PaletteContainer;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.PayloadBuilder;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.SerializationScratch;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import org.slf4j.Logger;

public final class ByteStreamTag extends CompoundTag {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final int ROOT_HEADER_SIZE = 3;
   private static final int PAYLOAD_SCAN_FAILED = -1;
   private static final int ANY_NUMERIC_TAG = 99;
   private static final int MATERIALIZE_STACK_DEPTH = 96;
   private static final Set<String> WARNED_MATERIALIZE_CALLERS = ConcurrentHashMap.newKeySet();
   private final ByteStreamTag.LazyPayloadMap tags;
   private final byte[] payload;

   public static ByteStreamTag uniform(int rawId) {
      return new ByteStreamTag(PayloadBuilder.uniform(rawId));
   }

   public static ByteStreamTag packed(SerializationScratch scratch, ArenaBlockStatePalettedContainer container) {
      return new ByteStreamTag(PayloadBuilder.packed(scratch, container));
   }

   private ByteStreamTag(byte[] payload) {
      this(new ByteStreamTag.LazyPayloadMap(payload), payload);
   }

   private ByteStreamTag(ByteStreamTag.LazyPayloadMap tags, byte[] payload) {
      super(tags);
      this.tags = tags;
      this.payload = payload;
   }

   public void write(DataOutput output) throws IOException {
      if (!this.tags.canWritePayload()) {
         super.write(output);
      } else {
         output.write(this.payload);
      }
   }

   public CompoundTag copy() {
      return !this.tags.canWritePayload() ? super.copy() : new ByteStreamTag(this.payload);
   }

   public byte getTagType(String key) {
      if (this.tags.canWritePayload()) {
         int type = payloadTagType(this.payload, key);
         if (type != -1) {
            return (byte)type;
         }
      }

      return super.getTagType(key);
   }

   public boolean contains(String key) {
      if (this.tags.canWritePayload()) {
         int type = payloadTagType(this.payload, key);
         if (type != -1) {
            return type != 0;
         }
      }

      return super.contains(key);
   }

   public boolean contains(String key, int type) {
      if (this.tags.canWritePayload()) {
         int actualType = payloadTagType(this.payload, key);
         if (actualType != -1) {
            return actualType != 0 && (actualType == type || type == 99 && isNumericTag(actualType));
         }
      }

      return super.contains(key, type);
   }

   private static void warnMaterialize() {
      StackTraceElement[] stack = Thread.currentThread().getStackTrace();
      if (WARNED_MATERIALIZE_CALLERS.add(materializeCallerKey(stack))) {
         LOGGER.warn(materializeStackMessage(stack));
      }
   }

   private static int payloadTagType(byte[] payload, String key) {
      try {
         int offset = 0;

         while (offset < payload.length) {
            int type = payload[offset++] & 255;
            if (type == 0) {
               return 0;
            }

            boolean nameMatches = nameEquals(payload, offset, key);
            offset += 2 + readUnsignedShort(payload, offset);
            if (nameMatches) {
               return type;
            }

            offset = skipPayload(payload, offset, type);
         }

         return 0;
      } catch (RuntimeException exception) {
         return -1;
      }
   }

   private static int skipPayload(byte[] payload, int offset, int type) {
      return switch (type) {
         case 1 -> offset + 1;
         case 2 -> offset + 2;
         case 3, 5 -> offset + 4;
         case 4, 6 -> offset + 8;
         case 7 -> offset + 4 + readInt(payload, offset);
         case 8 -> offset + 2 + readUnsignedShort(payload, offset);
         case 9 -> skipList(payload, offset);
         case 10 -> skipCompound(payload, offset);
         case 11 -> offset + 4 + readInt(payload, offset) * 4;
         case 12 -> offset + 4 + readInt(payload, offset) * 8;
         default -> throw new IllegalArgumentException("Unknown NBT tag type: " + type);
      };
   }

   private static int skipList(byte[] payload, int offset) {
      int elementType = payload[offset] & 255;
      int size = readInt(payload, offset + 1);
      offset += 5;

      for (int i = 0; i < size; i++) {
         offset = skipPayload(payload, offset, elementType);
      }

      return offset;
   }

   private static int skipCompound(byte[] payload, int offset) {
      while (true) {
         int type = payload[offset++] & 255;
         if (type == 0) {
            return offset;
         }

         offset += 2 + readUnsignedShort(payload, offset);
         offset = skipPayload(payload, offset, type);
      }
   }

   private static boolean nameEquals(byte[] payload, int offset, String key) {
      int length = readUnsignedShort(payload, offset);
      if (length != key.length()) {
         return false;
      }

      offset += 2;

      for (int i = 0; i < length; i++) {
         if ((payload[offset + i] & 255) != key.charAt(i)) {
            return false;
         }
      }

      return true;
   }

   private static int readUnsignedShort(byte[] payload, int offset) {
      return (payload[offset] & 0xFF) << 8 | payload[offset + 1] & 0xFF;
   }

   private static int readInt(byte[] payload, int offset) {
      return (payload[offset] & 0xFF) << 24 | (payload[offset + 1] & 0xFF) << 16 | (payload[offset + 2] & 0xFF) << 8 | payload[offset + 3] & 0xFF;
   }

   private static boolean isNumericTag(int type) {
      return type >= 1 && type <= 6;
   }

   private static String materializeCallerKey(StackTraceElement[] stack) {
      for (int i = 2; i < stack.length; i++) {
         String className = stack[i].getClassName();
         if (!isInternalMaterializeFrame(className)) {
            return className + "#" + stack[i].getMethodName() + ":" + stack[i].getLineNumber();
         }
      }

      return "unknown";
   }

   private static boolean isInternalMaterializeFrame(String className) {
      return className.startsWith(ByteStreamTag.class.getName()) || className.equals(CompoundTag.class.getName()) || className.equals(Thread.class.getName());
   }

   private static String materializeStackMessage(StackTraceElement[] stack) {
      int end = Math.min(stack.length, 96);
      StringBuilder message = new StringBuilder("Arena block state ByteStreamTag was lazily materialized; falling back to normal CompoundTag writes");

      for (int i = 2; i < end; i++) {
         message.append(System.lineSeparator()).append("\tat ").append(stack[i]);
      }

      return message.toString();
   }

   private static final class LazyPayloadMap extends AbstractMap<String, Tag> {
      private final byte[] payload;
      private Map<String, Tag> delegate;
      private boolean modified;

      LazyPayloadMap(byte[] payload) {
         this.payload = payload;
      }

      boolean canWritePayload() {
         return this.delegate == null && !this.modified;
      }

      @Override
      public Set<Entry<String, Tag>> entrySet() {
         return this.materialized().entrySet();
      }

      public Tag get(Object key) {
         return this.materialized().get(key);
      }

      @Override
      public boolean containsKey(Object key) {
         return this.materialized().containsKey(key);
      }

      @Override
      public int size() {
         return this.materialized().size();
      }

      public Tag put(String key, Tag value) {
         this.modified = true;
         return this.materialized().put(key, value);
      }

      public Tag remove(Object key) {
         this.modified = true;
         return this.materialized().remove(key);
      }

      @Override
      public void clear() {
         this.modified = true;
         this.materialized().clear();
      }

      @Override
      public void putAll(Map<? extends String, ? extends Tag> map) {
         if (!map.isEmpty()) {
            this.modified = true;
         }

         this.materialized().putAll(map);
      }

      private Map<String, Tag> materialized() {
         if (this.delegate == null) {
            ByteStreamTag.warnMaterialize();
            this.delegate = readPayload(this.payload);
         }

         return this.delegate;
      }

      private static Map<String, Tag> readPayload(byte[] payload) {
         byte[] root = new byte[payload.length + 3];
         root[0] = 10;
         System.arraycopy(payload, 0, root, 3, payload.length);

         try {
            CompoundTag tag = NbtIo.read(new DataInputStream(new ByteArrayInputStream(root)));
            Map<String, Tag> map = new HashMap<>(tag.size());

            for (String key : tag.getAllKeys()) {
               map.put(key, tag.get(key));
            }

            return map;
         } catch (IOException exception) {
            throw new IllegalStateException("Failed to materialize arena block state payload", exception);
         }
      }
   }
}
