package team.creative.creativecore.common.util.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class NamedHandlerRegistry<T> {
   private final HashMap<String, T> handlers = new LinkedHashMap<>();
   private final HashMap<T, String> handlersInv = new LinkedHashMap<>();
   private T defaultHandler;
   private boolean allowOverwrite = false;

   public static void clearRegistry(NamedHandlerRegistry registry) {
      registry.handlers.clear();
      registry.handlersInv.clear();
      registry.defaultHandler = null;
   }

   public NamedHandlerRegistry(T handler) {
      this.defaultHandler = handler;
   }

   public NamedHandlerRegistry<T> allowOverwrite() {
      this.allowOverwrite = true;
      return this;
   }

   public T getDefault() {
      return this.defaultHandler;
   }

   public void register(String id, T handler) {
      if (!this.allowOverwrite && this.handlers.containsKey(id)) {
         throw new IllegalArgumentException("'" + id + "' already exists");
      }

      this.handlers.put(id, handler);
      this.handlersInv.put(handler, id);
   }

   public void registerDefault(String id, T handler) {
      this.defaultHandler = handler;
      this.register(id, handler);
   }

   public String getId(T type) {
      return this.handlersInv.get(type);
   }

   public T get(String id) {
      return this.handlers.getOrDefault(id, this.defaultHandler);
   }

   public T getOrThrow(String id) {
      T handler = this.handlers.get(id);
      if (handler == null) {
         throw new IllegalArgumentException("'" + id + "' does not exist");
      } else {
         return handler;
      }
   }

   public boolean contains(String id) {
      return this.handlers.containsKey(id);
   }

   public Collection<String> keys() {
      return this.handlers.keySet();
   }

   public Set<Entry<String, T>> entrySet() {
      return this.handlers.entrySet();
   }

   public Collection<T> values() {
      return this.handlers.values();
   }
}
