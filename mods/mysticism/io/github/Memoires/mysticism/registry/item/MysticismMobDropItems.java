package io.github.Memoires.mysticism.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.item.consumable.SimpleFoodItem;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class MysticismMobDropItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mysticism", Registries.ITEM);
   public static final RegistrySupplier<Item> CRYPTID_ESSENCE = ITEMS.register(
      "cryptid_essence", () -> new SimpleFoodItem(TensuraCreativeTabs.MOB_DROPS, MysticismFoodProperties.CRYPTID_ESSENCE)
   );
   public static final RegistrySupplier<Item> FLAME_ESSENCE = ITEMS.register(
      "flame_essence", () -> new SimpleFoodItem(TensuraCreativeTabs.MOB_DROPS, MysticismFoodProperties.FLAME_ESSENCE)
   );
   public static final RegistrySupplier<Item> ICE_ESSENCE = ITEMS.register(
      "ice_essence", () -> new SimpleFoodItem(TensuraCreativeTabs.MOB_DROPS, MysticismFoodProperties.ICE_ESSENCE)
   );
   public static final RegistrySupplier<Item> LIGHTNING_ESSENCE = ITEMS.register(
      "lightning_essence",
      () -> new Item(new Properties().food(MysticismFoodProperties.LIGHTNING_ESSENCE).fireResistant().arch$tab(TensuraCreativeTabs.MOB_DROPS))
   );

   public static void init() {
      ITEMS.register();
   }
}
