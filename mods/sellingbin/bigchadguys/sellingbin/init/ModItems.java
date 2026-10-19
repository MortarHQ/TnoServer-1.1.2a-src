package bigchadguys.sellingbin.init;

import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems extends ModRegistries {
   public static void register() {
   }

   public static <V extends Item> RegistrySupplier<V> register(ResourceLocation id, Supplier<V> item) {
      return register(ITEMS, id, item);
   }

   public static <V extends Item> RegistrySupplier<V> register(String name, Supplier<V> item) {
      return register(ITEMS, name, item);
   }
}
