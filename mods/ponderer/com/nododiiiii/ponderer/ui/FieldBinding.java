package com.nododiiiii.ponderer.ui;

import java.util.Map;
import javax.annotation.Nullable;

public interface FieldBinding<T> extends SnapshotParticipant {
   T get();

   void set(@Nullable T var1);

   @Nullable
   default String snapshotKey() {
      return null;
   }

   @Nullable
   T deserialize(String var1);

   @Nullable
   default String serialize(@Nullable T value) {
      return value == null ? null : String.valueOf(value);
   }

   default boolean supportsSnapshot() {
      return this.snapshotKey() != null;
   }

   @Override
   default void snapshot(Map<String, String> snapshot) {
      String key = this.snapshotKey();
      if (key != null) {
         String serialized = this.serialize(this.get());
         if (serialized != null) {
            snapshot.put(key, serialized);
         }
      }
   }

   @Override
   default void restore(Map<String, String> snapshot) {
      String key = this.snapshotKey();
      if (key != null && snapshot.containsKey(key)) {
         this.set(this.deserialize(snapshot.get(key)));
      }
   }
}
