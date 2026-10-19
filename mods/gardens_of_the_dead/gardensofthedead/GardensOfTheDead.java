package gardensofthedead;

import gardensofthedead.network.NetworkHandler;
import gardensofthedead.registry.ModBlockEntityTypes;
import gardensofthedead.registry.ModBlocks;
import gardensofthedead.registry.ModFeatures;
import gardensofthedead.registry.ModItems;
import gardensofthedead.registry.ModLootConditions;
import gardensofthedead.registry.ModParticleTypes;
import gardensofthedead.registry.ModPlacementModifiers;
import gardensofthedead.registry.ModSoundEvents;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GardensOfTheDead {
   public static final String MOD_ID = "gardens_of_the_dead";
   public static final Logger LOGGER = LogManager.getLogger();

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("gardens_of_the_dead", path);
   }

   public static void init() {
      NetworkHandler.register();
      ModBlocks.BLOCKS.register();
      ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register();
      ModItems.CREATIVE_MODE_TABS.register();
      ModItems.ITEMS.register();
      ModFeatures.FEATURES.register();
      ModPlacementModifiers.PLACEMENT_MODIFIER_TYPES.register();
      ModParticleTypes.PARTICLE_TYPES.register();
      ModSoundEvents.SOUND_EVENTS.register();
      ModLootConditions.LOOT_CONDITIONS.register();
   }
}
