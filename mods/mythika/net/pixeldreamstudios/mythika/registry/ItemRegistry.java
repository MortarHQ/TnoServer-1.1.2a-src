package net.pixeldreamstudios.mythika.registry;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;

public final class ItemRegistry {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mythika", Registries.ITEM);
   public static final RegistrySupplier<Item> CYCLOPS_SPAWN_EGG = ITEMS.register(
      "cyclops_spawn_egg", () -> new SpawnEggItem(EntityRegistry.CYCLOPS.get(), 7037781, 14209208, new Properties())
   );
   public static final RegistrySupplier<Item> MINOTAUR_SPAWN_EGG = ITEMS.register(
      "minotaur_spawn_egg", () -> new SpawnEggItem(EntityRegistry.MINOTAUR.get(), 4861725, 12093775, new Properties())
   );
   public static final RegistrySupplier<Item> SATYR_SPAWN_EGG = ITEMS.register(
      "satyr_spawn_egg", () -> new SpawnEggItem(EntityRegistry.SATYR.get(), 9067060, 14731686, new Properties())
   );

   public static List<RegistrySupplier<Item>> tabContents() {
      return List.of(CYCLOPS_SPAWN_EGG, MINOTAUR_SPAWN_EGG, SATYR_SPAWN_EGG);
   }

   private ItemRegistry() {
   }

   public static void init() {
      ITEMS.register();
   }
}
