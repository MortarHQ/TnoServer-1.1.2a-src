package team.creative.creativecore.common.util.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;

public class LocatedHandlerRegistry<T> {
   private final HashMap<ResourceLocation, T> handlers = new LinkedHashMap<>();
   private final HashMap<T, ResourceLocation> handlersInv = new LinkedHashMap<>();
   private T defaultHandler;
   private boolean allowOverwrite = false;

   public LocatedHandlerRegistry(T handler) {
      this.defaultHandler = handler;
   }

   public LocatedHandlerRegistry<T> allowOverwrite() {
      this.allowOverwrite = true;
      return this;
   }

   public T getDefault() {
      return this.defaultHandler;
   }

   public void register(ResourceLocation id, T handler) {
      if (!this.allowOverwrite && this.handlers.containsKey(id)) {
         throw new IllegalArgumentException("'" + id + "' already exists");
      }

      this.handlers.put(id, handler);
      this.handlersInv.put(handler, id);
   }

   public void registerDefault(ResourceLocation id, T handler) {
      this.defaultHandler = handler;
      this.register(id, handler);
   }

   public ResourceLocation getLocation(T type) {
      return this.handlersInv.get(type);
   }

   public T get(ResourceLocation id) {
      return this.handlers.getOrDefault(id, this.defaultHandler);
   }

   public T getOrThrow(ResourceLocation id) {
      T handler = this.handlers.get(id);
      if (handler == null) {
         throw new IllegalArgumentException("'" + id + "' does not exist");
      } else {
         return handler;
      }
   }

   public boolean contains(ResourceLocation id) {
      return this.handlers.containsKey(id);
   }

   public Collection<ResourceLocation> keys() {
      return this.handlers.keySet();
   }

   public Set<Entry<ResourceLocation, T>> entrySet() {
      return this.handlers.entrySet();
   }

   public Collection<T> values() {
      return this.handlers.values();
   }
}
