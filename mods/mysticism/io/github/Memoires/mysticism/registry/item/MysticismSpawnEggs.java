package io.github.Memoires.mysticism.registry.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;

public class MysticismSpawnEggs {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("mysticism", Registries.ITEM);
   public static final RegistrySupplier<Item> OKAMI = ITEMS.register(
      "okami_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)MysticismMonsterEntityTypes.OKAMI.get(),
         new Color(150, 120, 100).getRGB(),
         new Color(150, 150, 150).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );
   public static final RegistrySupplier<Item> MEMOIRES = ITEMS.register(
      "memoires_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)MysticismMonsterEntityTypes.MEMOIRES.get(),
         new Color(90, 130, 180).getRGB(),
         new Color(220, 150, 80).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );
   public static final RegistrySupplier<Item> SHADOW_IMP = ITEMS.register(
      "shadow_imp_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)MysticismMonsterEntityTypes.SHADOW_IMP.get(),
         new Color(20, 20, 20).getRGB(),
         new Color(130, 30, 160).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );

   public static void init() {
      ITEMS.register();
   }
}
