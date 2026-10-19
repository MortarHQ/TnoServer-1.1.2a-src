package dev.ftb.mods.ftblibrary.snbt.config;

import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;

public class StringMapValue extends BaseValue<Map<String, String>> {
   public StringMapValue(@Nullable SNBTConfig c, String n, Map<String, String> def) {
      super(c, n, def);
      super.set(new HashMap<>(def));
   }

   @Override
   public void write(SNBTCompoundTag tag) {
      Map<String, String> map = this.get();
      SNBTCompoundTag mapTag = new SNBTCompoundTag();

      for (Entry<String, String> entry : map.entrySet()) {
         mapTag.putString(entry.getKey(), entry.getValue());
      }

      tag.put(this.key, mapTag);
   }

   @Override
   public void read(SNBTCompoundTag tag) {
      Map<String, String> map = new HashMap<>();
      SNBTCompoundTag compound = tag.getCompound(this.key);

      for (String key : compound.getAllKeys()) {
         map.put(key, compound.getString(key));
      }

      this.set(map);
   }
}
