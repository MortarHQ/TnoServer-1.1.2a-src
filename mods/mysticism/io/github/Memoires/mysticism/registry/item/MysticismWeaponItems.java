package io.github.Memoires.mysticism.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.item.weapon.AxiomItem;
import io.github.Memoires.mysticism.item.weapon.RitualScytheItem;
import io.github.Memoires.mysticism.item.weapon.WaltzItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class MysticismWeaponItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mysticism", Registries.ITEM);
   public static final RegistrySupplier<Item> AXIOM = ITEMS.register("axiom", AxiomItem::new);
   public static final RegistrySupplier<Item> RITUAL_SCYTHE = ITEMS.register("ritual_scythe", RitualScytheItem::new);
   public static final RegistrySupplier<Item> WALTZ = ITEMS.register("waltz", WaltzItem::new);

   public static void init() {
      ITEMS.register();
   }
}
