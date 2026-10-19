package mebahelcreaturesdwarven.item;

import mebahelcreaturesdwarven.MebahelCreaturesDwarven;
import mebahelcreaturesdwarven.block.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import net.minecraft.class_2378;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class ModItemGroups {
   public static final class_1761 ANTIQUE_BEASTS_GROUP = (class_1761)class_2378.method_10230(
      class_7923.field_44687,
      class_2960.method_60655("mebahelcreaturesdwarven", "mebahelcreaturesdwarven"),
      FabricItemGroup.builder()
         .method_47321(class_2561.method_43471("itemgroup.mebahelcreaturesdwarven"))
         .method_47320(() -> new class_1799(ModItems.DWEMER_METAL_GEAR))
         .method_47317((displayContext, entries) -> {
            entries.method_45421(ModBlocks.DWEMER_CHEST);
            entries.method_45421(ModBlocks.DWARVEN_SPIDER_BLOCK);
            entries.method_45421(ModBlocks.DWEMER_METAL_BLOCK);
            entries.method_45421(ModBlocks.DWEMER_METAL_PIPE);
            entries.method_45421(ModBlocks.DWEMER_METAL_PIPE_GEAR);
            entries.method_45421(ModItems.DWEMER_METAL_GEAR);
            entries.method_45421(ModItems.DWEMER_METAL_SCRAP);
            entries.method_45421(ModItems.DWEMER_METAL_INGOT);
            entries.method_45421(ModItems.DWEMER_DAGGER);
            entries.method_45421(ModItems.DWEMER_SWORD);
            entries.method_45421(ModItems.DWEMER_MACE);
            entries.method_45421(ModItems.DWEMER_WARHAMMER);
            entries.method_45421(ModItems.DWEMER_AXE);
            entries.method_45421(ModItems.DWEMER_WAR_AXE);
            entries.method_45421(ModSpawnEggs.DWARVEN_SPIDER_SPAWN_EGG);
            entries.method_45421(ModSpawnEggs.DWARVEN_SPIDER_GUARDIAN_SPAWN_EGG);
            entries.method_45421(ModSpawnEggs.DWARVEN_SPHERE_SPAWN_EGG);
            entries.method_45421(ModSpawnEggs.DWARVEN_CENTURION_SPAWN_EGG);
         })
         .method_47324()
   );

   public static void registerItemGroups() {
      MebahelCreaturesDwarven.LOGGER.info("Registering item groups");
   }
}
