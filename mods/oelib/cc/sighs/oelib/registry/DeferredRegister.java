package cc.sighs.oelib.registry;

import cc.sighs.oelib.registry.action.RegistryBatchAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class DeferredRegister<T> {
   private final ResourceKey<? extends Registry<T>> registryKey;
   private final String modid;
   private final List<RegisterSupplier<? extends T>> entries = new ArrayList<>();

   private DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String modid) {
      this.registryKey = registryKey;
      this.modid = modid;
   }

   public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String modid) {
      return new DeferredRegister<>(registryKey, modid);
   }

   public <R extends T> RegisterSupplier<R> register(String name, Supplier<? extends R> supplier) {
      ResourceLocation id = ResourceLocation.fromNamespaceAndPath(this.modid, name);
      ResourceKey<? extends Registry<R>> castedRegistryKey = this.registryKey;
      RegisterSupplier<R> entry = new RegisterSupplier<>(castedRegistryKey, id, supplier);
      this.entries.add(entry);
      return entry;
   }

   public void register() {
      RegistryBatchAction<T> action = new RegistryBatchAction<>(this.registryKey, this.modid, Collections.unmodifiableList(this.entries));
      RegistrationDispatcher.perform(action);
   }

   public ResourceKey<? extends Registry<T>> registryKey() {
      return this.registryKey;
   }

   public String modid() {
      return this.modid;
   }

   public List<RegisterSupplier<? extends T>> entries() {
      return Collections.unmodifiableList(this.entries);
   }
}
