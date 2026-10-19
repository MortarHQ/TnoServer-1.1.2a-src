package mebahelcreaturesdwarven.item;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import mebahelcreaturesdwarven.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.class_1792;
import net.minecraft.class_1826;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7706;
import net.minecraft.class_7923;
import net.minecraft.class_1792.class_1793;

public class ModSpawnEggs {
   public static final class_1792 DWARVEN_SPHERE_SPAWN_EGG = registerSpawnEgg(
      "spawn_egg/dwarven_sphere_spawn_egg", new class_1826(ModEntities.DWARVEN_SPHERE, 13671235, 10618160, new class_1793())
   );
   public static final class_1792 DWARVEN_SPIDER_SPAWN_EGG = registerSpawnEgg(
      "spawn_egg/dwarven_spider_spawn_egg", new class_1826(ModEntities.DWARVEN_SPIDER, 15978112, 11234357, new class_1793())
   );
   public static final class_1792 DWARVEN_SPIDER_GUARDIAN_SPAWN_EGG = registerSpawnEgg(
      "spawn_egg/dwarven_spider_guardian_spawn_egg", new class_1826(ModEntities.DWARVEN_SPIDER_GUARDIAN, 13671235, 10181160, new class_1793())
   );
   public static final class_1792 DWARVEN_CENTURION_SPAWN_EGG = registerSpawnEgg(
      "spawn_egg/dwarven_centurion_spawn_egg", new class_1826(ModEntities.DWARVEN_CENTURION, 13671235, 10181160, new class_1793())
   );

   private static class_1792 registerSpawnEgg(String name, class_1792 item) {
      return (class_1792)class_2378.method_10230(class_7923.field_41178, class_2960.method_60655("mebahelcreaturesdwarven", name), item);
   }

   public static void registerModItems() {
      MebahelCreaturesDwarven.LOGGER.info("[Mebahel's Antique Beasts] Registering Mod Spawn Eggs.");
      ItemGroupEvents.modifyEntriesEvent(class_7706.field_40205).register((ModifyEntries)entries -> {
         entries.method_45421(DWARVEN_SPHERE_SPAWN_EGG);
         entries.method_45421(DWARVEN_SPIDER_SPAWN_EGG);
         entries.method_45421(DWARVEN_SPIDER_GUARDIAN_SPAWN_EGG);
         entries.method_45421(DWARVEN_CENTURION_SPAWN_EGG);
      });
   }
}
