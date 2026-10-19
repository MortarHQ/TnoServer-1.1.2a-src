package twilightforest;

import com.google.common.reflect.Reflection;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.Configurable;
import twilightforest.compat.CosmeticArmorCompat;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.config.ConfigSetup;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFArmorMaterials;
import twilightforest.init.TFAttributes;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFCaveCarvers;
import twilightforest.init.TFCreativeTabs;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDataSerializers;
import twilightforest.init.TFDensityFunctions;
import twilightforest.init.TFEnchantmentEffects;
import twilightforest.init.TFEntities;
import twilightforest.init.TFFeatureModifiers;
import twilightforest.init.TFFeatures;
import twilightforest.init.TFGameRules;
import twilightforest.init.TFItemSubPredicates;
import twilightforest.init.TFItems;
import twilightforest.init.TFLoot;
import twilightforest.init.TFLootModifiers;
import twilightforest.init.TFMapDecorations;
import twilightforest.init.TFMenuTypes;
import twilightforest.init.TFMobEffects;
import twilightforest.init.TFPOITypes;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFRecipes;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.init.TFStructurePlacementTypes;
import twilightforest.init.TFStructureProcessors;
import twilightforest.init.TFStructureTypes;
import twilightforest.init.custom.BiomeLayerTypes;
import twilightforest.init.custom.ChunkBlanketProcessors;
import twilightforest.init.custom.Enforcements;
import twilightforest.init.custom.ItemDisplays;
import twilightforest.init.custom.TemplateMarkerHandlers;
import twilightforest.init.custom.TravellersModifierTypes;
import twilightforest.util.TFRemapper;

@Configurable
@Mod("twilightforest")
public final class TwilightForestMod {
   public static final String ID = "twilightforest";
   private static final String MODEL_DIR = "textures/entity/";
   private static final String GUI_DIR = "textures/gui/";
   private static final String ENVIRO_DIR = "textures/environment/";
   public static final Logger LOGGER = LogManager.getLogger("twilightforest");

   public TwilightForestMod(IEventBus bus, Dist dist) {
      Reflection.initialize(new Class[]{ConfigSetup.class});
      ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ConfigurationScreen::new);
      TFGameRules.register();
      TFItems.ITEMS.register(bus);
      TFStats.STATS.register(bus);
      TFLoot.NUMBERS.register(bus);
      TFBlocks.BLOCKS.register(bus);
      TFPOITypes.POIS.register(bus);
      TFSounds.SOUNDS.register(bus);
      TFLoot.FUNCTIONS.register(bus);
      TFLoot.CONDITIONS.register(bus);
      TFEntities.ENTITIES.register(bus);
      TFFeatures.FEATURES.register(bus);
      TFCreativeTabs.TABS.register(bus);
      TFLoot.CONDITIONALS.register(bus);
      TFEntities.SPAWN_EGGS.register(bus);
      ItemDisplays.DISPLAYS.register(bus);
      TFMenuTypes.CONTAINERS.register(bus);
      TFRecipes.RECIPE_TYPES.register(bus);
      TFAttributes.ATTRIBUTES.register(bus);
      TFAdvancements.TRIGGERS.register(bus);
      TFMobEffects.MOB_EFFECTS.register(bus);
      TFItemSubPredicates.TYPES.register(bus);
      Enforcements.ENFORCEMENTS.register(bus);
      TFCaveCarvers.CARVER_TYPES.register(bus);
      TFDataComponents.COMPONENTS.register(bus);
      TFRecipes.RECIPE_SERIALIZERS.register(bus);
      TFMapDecorations.DECORATIONS.register(bus);
      TFParticleType.PARTICLE_TYPES.register(bus);
      TravellersModifierTypes.TYPES.register(bus);
      TFBlockEntities.BLOCK_ENTITIES.register(bus);
      TFLootModifiers.LOOT_MODIFIERS.register(bus);
      TFArmorMaterials.ARMOR_MATERIALS.register(bus);
      TFStructureTypes.STRUCTURE_TYPES.register(bus);
      TFFeatureModifiers.TRUNK_PLACERS.register(bus);
      BiomeLayerTypes.BIOME_LAYER_TYPES.register(bus);
      TFDataAttachments.ATTACHMENT_TYPES.register(bus);
      TFDataSerializers.DATA_SERIALIZERS.register(bus);
      TFFeatureModifiers.FOLIAGE_PLACERS.register(bus);
      TFFeatureModifiers.TREE_DECORATORS.register(bus);
      TFEnchantmentEffects.ENTITY_EFFECTS.register(bus);
      TFFeatureModifiers.PLACEMENT_MODIFIERS.register(bus);
      TFDensityFunctions.DENSITY_FUNCTION_TYPES.register(bus);
      TFStructureProcessors.STRUCTURE_PROCESSORS.register(bus);
      TFStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(bus);
      ChunkBlanketProcessors.CHUNK_BLANKETING_TYPES.register(bus);
      TFStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
      TemplateMarkerHandlers.TEMPLATE_MARKER_HANDLER_TYPES.register(bus);
      TFRemapper.addRegistryAliases();
      if (ModList.get().isLoaded("curios")) {
         loadCuriosCompat(bus);
      }

      if (ModList.get().isLoaded("cosmeticarmorreworked")) {
         NeoForge.EVENT_BUS.addListener(CosmeticArmorCompat::keepCosmeticArmor);
      }

      BeanContext.injectInto(this);
   }

   private static void loadCuriosCompat(IEventBus bus) {
      NeoForge.EVENT_BUS.addListener(CuriosCompat::keepCurios);
      bus.addListener(CuriosCompat::registerCuriosCapabilities);
      bus.addListener(CuriosCompat::registerCurioRenderers);
      bus.addListener(CuriosCompat::registerCurioLayers);
   }

   public static ResourceLocation prefix(String name) {
      return ResourceLocation.fromNamespaceAndPath("twilightforest", name.toLowerCase(Locale.ROOT));
   }

   public static ResourceLocation getModelTexture(String name) {
      return ResourceLocation.fromNamespaceAndPath("twilightforest", "textures/entity/" + name);
   }

   public static ResourceLocation getGuiTexture(String name) {
      return ResourceLocation.fromNamespaceAndPath("twilightforest", "textures/gui/" + name);
   }

   public static ResourceLocation getEnvTexture(String name) {
      return ResourceLocation.fromNamespaceAndPath("twilightforest", "textures/environment/" + name);
   }

   static {
      BeanContext.init("twilightforest");
   }
}
