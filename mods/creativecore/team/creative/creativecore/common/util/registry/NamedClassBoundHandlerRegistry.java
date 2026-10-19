package team.creative.creativecore.common.util.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class NamedClassBoundHandlerRegistry<C, T> {
   private final HashMap<String, T> handlers = new LinkedHashMap<>();
   private final HashMap<T, String> handlersInv = new LinkedHashMap<>();
   private final HashMap<Class<? extends C>, T> classHandlers = new LinkedHashMap<>();
   private T defaultHandler;
   private boolean allowOverwrite = false;

   public static void clearRegistry(NamedClassBoundHandlerRegistry registry) {
      registry.handlers.clear();
      registry.handlersInv.clear();
      registry.defaultHandler = null;
   }

   public NamedClassBoundHandlerRegistry<C, T> allowOverwrite() {
      this.allowOverwrite = true;
      return this;
   }

   public T getDefault() {
      return this.defaultHandler;
   }

   public void register(String id, Class clazz, T handler) {
      if (!this.allowOverwrite && this.handlers.containsKey(id)) {
         throw new IllegalArgumentException("'" + id + "' already exists");
      }

      this.handlers.put(id, handler);
      this.handlersInv.put(handler, id);
      this.classHandlers.put(clazz, handler);
   }

   public void registerDefault(String id, Class<? extends C> clazz, T handler) {
      this.defaultHandler = handler;
      this.register(id, clazz, handler);
   }

   public String getId(T type) {
      return this.handlersInv.get(type);
   }

   public String getId(Class<? extends C> clazz) {
      return this.handlersInv.get(this.get(clazz));
   }

   public T get(String id) {
      return this.handlers.getOrDefault(id, this.defaultHandler);
   }

   public T get(Class<? extends C> clazz) {
      return this.classHandlers.getOrDefault(clazz, this.defaultHandler);
   }

   public T getOrThrow(String id) {
      T handler = this.handlers.get(id);
      if (handler == null) {
         throw new IllegalArgumentException("'" + id + "' does not exist");
      } else {
         return handler;
      }
   }

   public T getOrThrow(Class<? extends C> clazz) {
      T handler = this.classHandlers.get(clazz);
      if (handler == null) {
         throw new IllegalArgumentException("'" + clazz.getName() + "' does not exist");
      } else {
         return handler;
      }
   }

   public boolean contains(String id) {
      return this.handlers.containsKey(id);
   }

   public boolean contains(Class clazz) {
      return this.classHandlers.containsKey(clazz);
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

   public Set<Class<? extends C>> classes() {
      return this.classHandlers.keySet();
   }
}
