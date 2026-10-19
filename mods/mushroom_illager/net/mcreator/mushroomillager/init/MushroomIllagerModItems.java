package net.mcreator.mushroomillager.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class MushroomIllagerModItems {
   public static final Items REGISTRY = DeferredRegister.createItems("mushroom_illager");
   public static final DeferredItem<Item> MUSHROOM_ILLAGER_SPAWN_EGG = REGISTRY.register(
      "mushroom_illager_spawn_egg", () -> new DeferredSpawnEggItem(MushroomIllagerModEntities.MUSHROOM_ILLAGER, -6710887, -65536, new Properties())
   );
}
