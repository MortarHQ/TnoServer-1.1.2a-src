package team.lodestar.lodestone;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.lodestar.lodestone.compability.CuriosCompat;
import team.lodestar.lodestone.config.ClientConfig;
import team.lodestar.lodestone.registry.common.LodestoneAttachmentTypes;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;
import team.lodestar.lodestone.registry.common.LodestoneBlockEntities;
import team.lodestar.lodestone.registry.common.LodestoneCommandArgumentTypes;
import team.lodestar.lodestone.registry.common.LodestoneEnchantmentComponents;
import team.lodestar.lodestone.registry.common.LodestonePlacementFillers;
import team.lodestar.lodestone.registry.common.LodestoneRecipeSerializers;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;

@Mod("lodestone")
public class LodestoneLib {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final String LODESTONE = "lodestone";
   public static final RandomSource RANDOM = RandomSource.create();

   public LodestoneLib() {
      IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();
      IEventBus forgeBus = NeoForge.EVENT_BUS;
      ModLoadingContext.get().getActiveContainer().registerConfig(Type.CLIENT, ClientConfig.SPEC);
      LodestoneBlockEntities.BLOCK_ENTITY_TYPES.register(modBus);
      LodestoneParticleTypes.PARTICLES.register(modBus);
      LodestoneAttributes.ATTRIBUTES.register(modBus);
      LodestoneRecipeSerializers.RECIPE_SERIALIZERS.register(modBus);
      LodestoneAttachmentTypes.ATTACHMENT_TYPES.register(modBus);
      LodestonePlacementFillers.MODIFIERS.register(modBus);
      LodestoneEnchantmentComponents.ENCHANTMENT_COMPONENTS.register(modBus);
      LodestoneWorldEventTypes.WORLD_EVENT_TYPES.register(modBus);
      LodestoneCommandArgumentTypes.register(modBus);
      CuriosCompat.init();
   }

   public static ResourceLocation lodestonePath(String path) {
      return ResourceLocation.fromNamespaceAndPath("lodestone", path);
   }
}
