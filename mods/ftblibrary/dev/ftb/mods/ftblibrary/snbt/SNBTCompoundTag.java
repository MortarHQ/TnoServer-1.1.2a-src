package dev.ftb.mods.ftblibrary.snbt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public class SNBTCompoundTag extends CompoundTag {
   public static final StreamCodec<FriendlyByteBuf, SNBTCompoundTag> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, SNBTCompoundTag>() {
      public SNBTCompoundTag decode(FriendlyByteBuf object) {
         return SNBTNet.readCompound(object);
      }

      public void encode(FriendlyByteBuf object, SNBTCompoundTag object2) {
         SNBTNet.write(object, object2);
      }
   };
   boolean singleLine = false;
   private HashMap<String, SNBTTagProperties> properties;

   public SNBTCompoundTag() {
      super(new LinkedHashMap());
   }

   public static SNBTCompoundTag of(@Nullable Tag tag) {
      if (tag instanceof SNBTCompoundTag) {
         return (SNBTCompoundTag)tag;
      }

      if (!(tag instanceof CompoundTag)) {
         return new SNBTCompoundTag();
      }

      SNBTCompoundTag tag1 = new SNBTCompoundTag();

      for (String s : ((CompoundTag)tag).getAllKeys()) {
         tag1.put(s, ((CompoundTag)tag).get(s));
      }

      return tag1;
   }

   SNBTTagProperties getOrCreateProperties(String key) {
      if (this.properties == null) {
         this.properties = new HashMap<>();
      }

      SNBTTagProperties p = this.properties.get(key);
      if (p == null) {
         p = new SNBTTagProperties();
         this.properties.put(key, p);
      }

      return p;
   }

   SNBTTagProperties getProperties(String key) {
      if (this.properties != null) {
         SNBTTagProperties p = this.properties.get(key);
         if (p != null) {
            return p;
         }
      }

      return SNBTTagProperties.DEFAULT;
   }

   public void comment(String key, String... comment) {
      if (comment.length > 0) {
         this.comment(key, String.join("\n", comment));
      }
   }

   public void comment(String key, String comment) {
      String s = comment == null ? "" : comment.trim();
      if (!s.isEmpty()) {
         this.getOrCreateProperties(key).comment = comment;
      }
   }

   public String getComment(String key) {
      return this.getProperties(key).comment;
   }

   public void singleLine() {
      this.singleLine = true;
   }

   public void singleLine(String key) {
      this.getOrCreateProperties(key).singleLine = true;
   }

   public void putBoolean(String key, boolean value) {
      this.getOrCreateProperties(key).valueType = value ? 2 : 1;
      super.putBoolean(key, value);
   }

   public boolean isBoolean(String key) {
      int t = this.getProperties(key).valueType;
      return t == 2 || t == 1;
   }

   public SNBTCompoundTag getCompound(String string) {
      return of(this.get(string));
   }

   public void putNumber(String key, Number number) {
      if (number instanceof Double) {
         this.putDouble(key, number.doubleValue());
      } else if (number instanceof Float) {
         this.putFloat(key, number.floatValue());
      } else if (number instanceof Long) {
         this.putLong(key, number.longValue());
      } else if (number instanceof Integer) {
         this.putInt(key, number.intValue());
      } else if (number instanceof Short) {
         this.putShort(key, number.shortValue());
      } else if (number instanceof Byte) {
         this.putByte(key, number.byteValue());
      } else if (number.toString().contains(".")) {
         this.putDouble(key, number.doubleValue());
      } else {
         this.putInt(key, number.intValue());
      }
   }

   public void putNull(String key) {
      this.put(key, EndTag.INSTANCE);
   }

   @Nullable
   public ListTag getNullableList(String key, byte type) {
      Tag tag = this.get(key);
      return !(tag instanceof ListTag) || !((ListTag)tag).isEmpty() && type != 0 && ((ListTag)tag).getElementType() != type ? null : (ListTag)tag;
   }

   public <T extends Tag> List<T> getList(String key, Class<T> type) {
      if (this.get(key) instanceof CollectionTag<?> l) {
         if (l.isEmpty()) {
            return Collections.emptyList();
         }

         List<T> list = new ArrayList<>(l.size());

         for (Tag t : l) {
            if (type.isAssignableFrom(t.getClass())) {
               list.add((T)t);
            }
         }

         return list;
      } else {
         return Collections.emptyList();
      }
   }

   public CompoundTag merge(CompoundTag other, boolean overwrite) {
      for (String key : other.getAllKeys()) {
         Tag tag = other.get(key);
         if (tag != null && (overwrite || !this.contains(key))) {
            if (tag.getId() == 10) {
               if (this.contains(key, 10)) {
                  this.getCompound(key).merge((CompoundTag)tag, overwrite);
               } else {
                  this.put(key, tag.copy());
               }
            } else {
               this.put(key, tag.copy());
            }
         }
      }

      return this;
   }
}
