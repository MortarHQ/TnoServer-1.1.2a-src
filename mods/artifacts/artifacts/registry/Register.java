package artifacts.registry;

import artifacts.Artifacts;
import artifacts.platform.PlatformServices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

public abstract class Register<R> implements Iterable<R> {
   private final ResourceKey<Registry<R>> registry;
   private final List<RegistryHolder<R, ?>> entries = new ArrayList<>();

   public Register(ResourceKey<Registry<R>> registry) {
      this.registry = registry;
   }

   public static <R> Register<R> create(ResourceKey<Registry<R>> registry) {
      return PlatformServices.platformHelper.createRegister(registry);
   }

   public ResourceKey<Registry<R>> getRegistry() {
      return this.registry;
   }

   public Collection<RegistryHolder<R, ?>> getEntries() {
      return this.entries;
   }

   public <T extends R> RegistryHolder<R, T> register(String name, Supplier<T> supplier) {
      RegistryHolder<R, T> holder = new RegistryHolder<>(Artifacts.key(this.registry, name), supplier);
      this.entries.add(holder);
      if (this.getRegistry().equals(Registries.ATTRIBUTE)
         || this.getRegistry().equals(Registries.MOB_EFFECT)
         || this.getRegistry().equals(Registries.DATA_COMPONENT_TYPE)) {
         this.bind(holder);
      }

      return holder;
   }

   @NotNull
   @Override
   public Iterator<R> iterator() {
      return this.entries.stream().map(RegistryHolder::value).iterator();
   }

   public void register() {
      if (!this.getRegistry().equals(Registries.ATTRIBUTE)
         && !this.getRegistry().equals(Registries.MOB_EFFECT)
         && !this.getRegistry().equals(Registries.DATA_COMPONENT_TYPE)) {
         for (RegistryHolder<R, ?> holder : this.getEntries()) {
            this.bind(holder);
         }
      }
   }

   protected abstract <T extends R> void bind(RegistryHolder<R, T> var1);
}
