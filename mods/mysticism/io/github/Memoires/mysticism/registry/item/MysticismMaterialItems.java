package io.github.Memoires.mysticism.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.item.misc.DarknessCoreItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class MysticismMaterialItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mysticism", Registries.ITEM);
   public static final RegistrySupplier<Item> DARKNESS_CORE = ITEMS.register("darkness_core", DarknessCoreItem::new);

   public static void init() {
      ITEMS.register();
   }
}
