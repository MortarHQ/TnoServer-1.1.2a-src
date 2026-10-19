package io.github.manasmods.tensura_neb.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.registry.item.misc.TensuraCreativeTabs;
import io.github.manasmods.tensura_neb.item.NightRoseItem;
import io.github.manasmods.tensura_neb.item.WhiteTigerAzureDragonHalberdItem;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;

public class NebItems {
   private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("tensura_neb", Registries.ITEM);
   public static final RegistrySupplier<NightRoseItem> NIGHT_ROSE = ITEMS.register("night_rose", NightRoseItem::new);
   public static final RegistrySupplier<WhiteTigerAzureDragonHalberdItem> WHITE_TIGER_AZURE_DRAGON_HALBERD = ITEMS.register(
      "white_tiger_azure_dragon_halberd", WhiteTigerAzureDragonHalberdItem::new
   );
   public static final RegistrySupplier<Item> RIMURU_SPAWN_EGG = ITEMS.register(
      "rimuru_ogre_fight_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)NebEntityTypes.RIMURU_OGRE_FIGHT.get(),
         new Color(190, 224, 235).getRGB(),
         new Color(55, 81, 126).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );
   public static final RegistrySupplier<Item> CARRION_SPAWN_EGG = ITEMS.register(
      "carrion_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)NebEntityTypes.CARRION.get(),
         new Color(226, 223, 217).getRGB(),
         new Color(196, 158, 89).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );
   public static final RegistrySupplier<Item> LUMINOUS_SPAWN_EGG = ITEMS.register(
      "luminous_valentine_spawn_egg",
      () -> new SpawnEggItem(
         (EntityType)NebEntityTypes.LUMINOUS_VALENTINE.get(),
         new Color(176, 185, 203).getRGB(),
         new Color(49, 50, 55).getRGB(),
         new Properties().arch$tab(TensuraCreativeTabs.SPAWN_EGG)
      )
   );

   public static void init() {
      ITEMS.register();
   }
}
