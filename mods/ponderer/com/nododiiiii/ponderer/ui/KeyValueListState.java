package com.nododiiiii.ponderer.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class KeyValueListState extends DynamicListState<KeyValueListState.KeyValue> {
   public KeyValueListState(String snapshotPrefix, int minimumSize) {
      super(snapshotPrefix, new DynamicListState.ItemCodec<KeyValueListState.KeyValue>() {
         public void snapshot(String prefix, KeyValueListState.KeyValue item, Map<String, String> snapshot) {
            snapshot.put(prefix + "_key", item.key());
            snapshot.put(prefix + "_val", item.value());
         }

         public KeyValueListState.KeyValue restore(String prefix, Map<String, String> snapshot) {
            return new KeyValueListState.KeyValue(snapshot.getOrDefault(prefix + "_key", ""), snapshot.getOrDefault(prefix + "_val", ""));
         }
      }, () -> new KeyValueListState.KeyValue("", ""), minimumSize);
   }

   public void updateKey(int index, String key) {
      if (index >= 0 && index < this.size()) {
         KeyValueListState.KeyValue current = this.get(index);
         this.set(index, new KeyValueListState.KeyValue(key == null ? "" : key, current.value()));
      }
   }

   public void updateValue(int index, String value) {
      if (index >= 0 && index < this.size()) {
         KeyValueListState.KeyValue current = this.get(index);
         this.set(index, new KeyValueListState.KeyValue(current.key(), value == null ? "" : value));
      }
   }

   public void replaceFromMap(@Nullable Map<String, String> map) {
      List<KeyValueListState.KeyValue> values = new ArrayList<>();
      if (map != null) {
         for (Entry<String, String> entry : map.entrySet()) {
            values.add(new KeyValueListState.KeyValue(entry.getKey(), entry.getValue()));
         }
      }

      this.replaceAll(values);
   }

   @Nullable
   public Map<String, String> toFilteredMap() {
      Map<String, String> map = new LinkedHashMap<>();

      for (KeyValueListState.KeyValue item : this.items()) {
         String key = item.key().trim();
         String value = item.value().trim();
         if (!key.isEmpty() && !value.isEmpty()) {
            map.put(key, value);
         }
      }

      return map.isEmpty() ? null : map;
   }

   public record KeyValue(String key, String value) {
   }
}
