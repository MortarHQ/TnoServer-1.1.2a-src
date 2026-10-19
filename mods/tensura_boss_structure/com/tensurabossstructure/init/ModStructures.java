package com.tensurabossstructure.init;

import com.tensurabossstructure.TensuraBossStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModStructures {
   public static final ResourceKey<Structure> RIMURU_OGRE_FIGHT = registerKey("rimuru_ogre_fight");
   public static final ResourceKey<Structure> HINATA_CHURCH = registerKey("hinata_church");
   public static final ResourceKey<Structure> ORC_DISASTER_TEMPLE = registerKey("orc_disaster_temple");
   public static final ResourceKey<Structure> NIGHT_ROSE = registerKey("night_rose");

   private static ResourceKey<Structure> registerKey(String name) {
      return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("tensura_boss_structure", name));
   }

   public static void register() {
      TensuraBossStructure.LOGGER.info("Registering Structures for tensura_boss_structure");
   }
}
