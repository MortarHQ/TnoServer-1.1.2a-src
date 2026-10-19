package org.openzen.zencode.shared;

import java.util.HashMap;
import java.util.Map;

public class Taggable {
   private final Map<Class<?>, Object> tags = new HashMap<>();

   public <T extends Tag> void setTag(Class<T> typeOfT, T tag) {
      this.tags.put(typeOfT, tag);
   }

   public <T extends Tag> T getTag(Class<T> typeOfT) {
      return (T)this.tags.get(typeOfT);
   }

   public <T extends Tag> boolean hasTag(Class<T> typeOfT) {
      return this.tags.containsKey(typeOfT);
   }

   public void addAllTagsFrom(Taggable other) {
      this.tags.putAll(other.tags);
   }
}
