package net.blay09.mods.waystones.worldgen;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.event.ConfigLoadedEvent;
import net.blay09.mods.balm.api.event.server.ServerStartingEvent;
import net.blay09.mods.balm.api.world.BalmWorldGen;
import net.blay09.mods.balm.api.world.BiomePredicate;
import net.blay09.mods.waystones.block.ModBlocks;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.blay09.mods.waystones.config.WorldGenStyle;
import net.blay09.mods.waystones.mixin.StructureTemplatePoolAccessor;
import net.blay09.mods.waystones.tag.ModBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class ModWorldGen {
   private static final ResourceLocation waystone = ResourceLocation.fromNamespaceAndPath("waystones", "waystone");
   private static final ResourceLocation mossyWaystone = ResourceLocation.fromNamespaceAndPath("waystones", "mossy_waystone");
   private static final ResourceLocation sandyWaystone = ResourceLocation.fromNamespaceAndPath("waystones", "sandy_waystone");
   private static final ResourceLocation blackstoneWaystone = ResourceLocation.fromNamespaceAndPath("waystones", "blackstone_waystone");
   private static final ResourceLocation deepslateWaystone = ResourceLocation.fromNamespaceAndPath("waystones", "deepslate_waystone");
   private static final ResourceLocation endStoneWaystone = ResourceLocation.fromNamespaceAndPath("waystones", "end_stone_waystone");
   private static final ResourceLocation villageWaystoneStructure = ResourceLocation.fromNamespaceAndPath("waystones", "village/common/waystone");
   private static final ResourceLocation desertVillageWaystoneStructure = ResourceLocation.fromNamespaceAndPath("waystones", "village/desert/waystone");
   private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
      Registries.PROCESSOR_LIST, ResourceLocation.fromNamespaceAndPath("minecraft", "empty")
   );
   public static DeferredObject<PlacementModifierType<WaystonePlacement>> waystonePlacement;

   public static void initialize(BalmWorldGen worldGen) {
      worldGen.registerFeature(waystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.waystone.defaultBlockState()));
      worldGen.registerFeature(mossyWaystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.mossyWaystone.defaultBlockState()));
      worldGen.registerFeature(sandyWaystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.sandyWaystone.defaultBlockState()));
      worldGen.registerFeature(blackstoneWaystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.blackstoneWaystone.defaultBlockState()));
      worldGen.registerFeature(deepslateWaystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.deepslateWaystone.defaultBlockState()));
      worldGen.registerFeature(endStoneWaystone, () -> new WaystoneFeature(NoneFeatureConfiguration.CODEC, ModBlocks.endStoneWaystone.defaultBlockState()));
      waystonePlacement = worldGen.registerPlacementModifier(id("waystone"), () -> () -> WaystonePlacement.CODEC);
      ResourceLocation waystonesCommonConfig = ResourceLocation.fromNamespaceAndPath("waystones", "common");
      Runnable configLoadHandler = () -> {
         worldGen.addFeatureToBiomes(
            matchesTag(ModBiomeTags.HAS_STRUCTURE_SANDY_WAYSTONE), Decoration.VEGETAL_DECORATION, getWaystoneFeature(WorldGenStyle.SANDY)
         );
         worldGen.addFeatureToBiomes(
            matchesTag(ModBiomeTags.HAS_STRUCTURE_MOSSY_WAYSTONE), Decoration.VEGETAL_DECORATION, getWaystoneFeature(WorldGenStyle.MOSSY)
         );
         worldGen.addFeatureToBiomes(
            matchesTag(ModBiomeTags.HAS_STRUCTURE_BLACKSTONE_WAYSTONE), Decoration.VEGETAL_DECORATION, getWaystoneFeature(WorldGenStyle.BLACKSTONE)
         );
         worldGen.addFeatureToBiomes(
            matchesTag(ModBiomeTags.HAS_STRUCTURE_END_STONE_WAYSTONE), Decoration.VEGETAL_DECORATION, getWaystoneFeature(WorldGenStyle.END_STONE)
         );
         worldGen.addFeatureToBiomes(matchesTag(ModBiomeTags.HAS_STRUCTURE_WAYSTONE), Decoration.VEGETAL_DECORATION, getWaystoneFeature(WorldGenStyle.DEFAULT));
      };
      Balm.getEvents().onEvent(ConfigLoadedEvent.class, event -> {
         if (event.getSchema().identifier().equals(waystonesCommonConfig)) {
            configLoadHandler.run();
         }
      });
      if (Balm.getConfig().getActiveConfig(ResourceLocation.fromNamespaceAndPath("waystones", "common")) != null) {
         configLoadHandler.run();
      }

      Balm.getEvents().onEvent(ServerStartingEvent.class, event -> setupDynamicRegistries(event.getServer().registryAccess()));
   }

   private static BiomePredicate matchesTag(TagKey<Biome> tag) {
      return (resourceLocation, biome) -> biome.is(tag);
   }

   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("waystones", name);
   }

   private static ResourceLocation getWaystoneFeature(WorldGenStyle biomeWorldGenStyle) {
      WorldGenStyle worldGenStyle = WaystonesConfig.getActive().worldGen.wildWaystoneStyle;

      return switch (worldGenStyle) {
         case SANDY -> sandyWaystone;
         case MOSSY -> mossyWaystone;
         case BLACKSTONE -> blackstoneWaystone;
         case DEEPSLATE -> deepslateWaystone;
         case END_STONE -> endStoneWaystone;
         case BIOME -> {
            switch (biomeWorldGenStyle) {
               case SANDY:
                  yield sandyWaystone;
               case MOSSY:
                  yield mossyWaystone;
               case BLACKSTONE:
                  yield blackstoneWaystone;
               case DEEPSLATE:
                  yield deepslateWaystone;
               case END_STONE:
                  yield endStoneWaystone;
               default:
                  yield waystone;
            }
         }
         default -> waystone;
      };
   }

   public static void setupDynamicRegistries(RegistryAccess registryAccess) {
      if (WaystonesConfig.getActive().worldGen.spawnInVillages != WaystonesConfigData.VillageWaystoneGeneration.DISABLED) {
         addWaystoneStructureToVillageConfig(registryAccess, "village/plains/houses", villageWaystoneStructure, 1);
         addWaystoneStructureToVillageConfig(registryAccess, "village/snowy/houses", villageWaystoneStructure, 1);
         addWaystoneStructureToVillageConfig(registryAccess, "village/savanna/houses", villageWaystoneStructure, 1);
         addWaystoneStructureToVillageConfig(registryAccess, "village/desert/houses", desertVillageWaystoneStructure, 1);
         addWaystoneStructureToVillageConfig(registryAccess, "village/taiga/houses", villageWaystoneStructure, 1);
      }
   }

   private static void addWaystoneStructureToVillageConfig(RegistryAccess registryAccess, String villagePiece, ResourceLocation waystoneStructure, int weight) {
      Holder<StructureProcessorList> emptyProcessorList = registryAccess.registryOrThrow(Registries.PROCESSOR_LIST).getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);
      LegacySinglePoolElement piece = (LegacySinglePoolElement)StructurePoolElement.legacy(waystoneStructure.toString(), emptyProcessorList)
         .apply(Projection.RIGID);
      if (piece instanceof WaystoneStructurePoolElement element) {
         element.waystones$setIsWaystone(true);
      }

      StructureTemplatePool pool = (StructureTemplatePool)registryAccess.registryOrThrow(Registries.TEMPLATE_POOL)
         .getOptional(ResourceLocation.withDefaultNamespace(villagePiece))
         .orElse(null);
      if (pool != null) {
         StructureTemplatePoolAccessor poolAccessor = (StructureTemplatePoolAccessor)pool;
         ObjectArrayList<StructurePoolElement> listOfPieces = new ObjectArrayList(poolAccessor.getTemplates());

         for (int i = 0; i < weight; i++) {
            listOfPieces.add(piece);
         }

         poolAccessor.setTemplates(listOfPieces);
         List<Pair<StructurePoolElement, Integer>> listOfWeightedPieces = new ArrayList<>(poolAccessor.getRawTemplates());
         listOfWeightedPieces.add(new Pair(piece, weight));
         poolAccessor.setRawTemplates(listOfWeightedPieces);
      }
   }
}
