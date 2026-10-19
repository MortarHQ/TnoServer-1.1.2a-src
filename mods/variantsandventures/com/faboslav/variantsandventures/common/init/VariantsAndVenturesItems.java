package com.faboslav.variantsandventures.common.init;

import com.faboslav.variantsandventures.common.events.item.RegisterItemColorEvent;
import com.faboslav.variantsandventures.common.events.lifecycle.SetupEvent;
import com.faboslav.variantsandventures.common.mixin.SpawnEggItemAccessor;
import com.mojang.datafixers.util.Pair;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;

public final class VariantsAndVenturesItems {
   public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, "variantsandventures");
   private static final List<Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem>> SPAWN_EGGS = new ArrayList<>();
   public static final RegistryEntry<Item> GELID_SPAWN_EGG = registerSpawnEgg("gelid_spawn_egg", VariantsAndVenturesEntityTypes.GELID, -15692132, -2164745);
   public static final RegistryEntry<Item> MURK_SPAWN_EGG = registerSpawnEgg("murk_spawn_egg", VariantsAndVenturesEntityTypes.MURK, -4083231, -6130007);
   public static final RegistryEntry<Item> THICKET_SPAWN_EGG = registerSpawnEgg(
      "thicket_spawn_egg", VariantsAndVenturesEntityTypes.THICKET, -16753833, -13940461
   );
   public static final RegistryEntry<Item> VERDANT_SPAWN_EGG = registerSpawnEgg(
      "verdant_spawn_egg", VariantsAndVenturesEntityTypes.VERDANT, -7632267, -11903711
   );

   private static RegistryEntry<Item> registerSpawnEgg(
      String id, Supplier<? extends EntityType<? extends Mob>> typeIn, int primaryColorIn, int secondaryColorIn
   ) {
      return ITEMS.register(id, () -> {
         SpawnEggItem spawnEgg = new SpawnEggItem(typeIn.get(), primaryColorIn, secondaryColorIn, new Properties().stacksTo(64));
         SPAWN_EGGS.add(new Pair(typeIn, spawnEgg));
         return spawnEgg;
      });
   }

   public static void registerItemColors(RegisterItemColorEvent event) {
      for (Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem> entry : SPAWN_EGGS) {
         event.register((stack, tintIndex) -> ((SpawnEggItem)entry.getSecond()).getColor(tintIndex), (ItemLike)entry.getSecond());
      }
   }

   public static void registerSpawnEggs(SetupEvent event) {
      Map<EntityType<? extends Mob>, SpawnEggItem> spawnEggMap = SpawnEggItemAccessor.variantsandventures$getSpawnEggs();

      for (Pair<Supplier<? extends EntityType<? extends Mob>>, SpawnEggItem> entry : SPAWN_EGGS) {
         spawnEggMap.put((EntityType<? extends Mob>)((Supplier)entry.getFirst()).get(), (SpawnEggItem)entry.getSecond());
      }
   }

   private VariantsAndVenturesItems() {
   }
}
