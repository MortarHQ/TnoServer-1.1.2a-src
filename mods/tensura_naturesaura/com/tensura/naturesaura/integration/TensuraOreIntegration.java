package com.tensura.naturesaura.integration;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.misc.WeightedOre;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public final class TensuraOreIntegration {
   private static final ResourceLocation MAGIC_STONE_ORES = ResourceLocation.fromNamespaceAndPath("tensura_naturesaura", "ores/magic_stone");
   private static final int MAGIC_ORE_WEIGHT = 100;

   private TensuraOreIntegration() {
   }

   public static void onCommonSetup(FMLCommonSetupEvent event) {
      if (ModList.get().isLoaded("tensura")) {
         event.enqueueWork(TensuraOreIntegration::registerMagicOre);
      }
   }

   private static void registerMagicOre() {
      NaturesAuraAPI.OVERWORLD_ORES.removeIf(ore -> ore.tag.equals(MAGIC_STONE_ORES));
      NaturesAuraAPI.OVERWORLD_ORES.add(new WeightedOre(MAGIC_STONE_ORES, 100));
   }
}
