package team.creative.creativecore.common.config.premade.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import team.creative.creativecore.common.util.type.itr.FunctionIterator;

public class RegistryObjectListConfig<T> implements Iterable<T> {
   public final Registry<T> registry;
   private final List<RegistryObjectListConfig<T>.RegistryHolder> content = new ArrayList<>();

   public RegistryObjectListConfig(Registry<T> registry) {
      this.registry = registry;
   }

   public void add(ResourceLocation location) {
      if (!this.contains(location)) {
         this.content.add(new RegistryObjectListConfig.RegistryHolder(location));
      }
   }

   @Override
   public Iterator<T> iterator() {
      return new FunctionIterator<>(this.content, x -> (T)x.get());
   }

   public Iterable<ResourceLocation> locations() {
      return new FunctionIterator<>(this.content, x -> x.location);
   }

   public boolean contains(ResourceLocation location) {
      for (int i = 0; i < this.content.size(); i++) {
         if (this.content.get(i).location.equals(location)) {
            return true;
         }
      }

      return false;
   }

   public ResourceLocation getLocation(int index) {
      return this.content.get(index).location;
   }

   public int size() {
      return this.content.size();
   }

   public class RegistryHolder {
      public final ResourceLocation location;
      private T cache;

      public RegistryHolder(ResourceLocation location) {
         this.location = location;
      }

      @Override
      public int hashCode() {
         return this.location.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         return obj instanceof RegistryObjectListConfig.RegistryHolder h ? h.location.equals(this.location) : super.equals(obj);
      }

      public T get() {
         if (this.cache == null) {
            this.cache = (T)RegistryObjectListConfig.this.registry.get(this.location);
         }

         return this.cache;
      }
   }
}
