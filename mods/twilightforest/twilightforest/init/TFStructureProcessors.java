package twilightforest.init;

import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.world.components.feature.templates.GraveyardFeature;
import twilightforest.world.components.processors.BoxCuttingProcessor;
import twilightforest.world.components.processors.CobbleVariants;
import twilightforest.world.components.processors.InfestBlocksProcessor;
import twilightforest.world.components.processors.NagastoneVariants;
import twilightforest.world.components.processors.SmartGrassProcessor;
import twilightforest.world.components.processors.SmoothStoneVariants;
import twilightforest.world.components.processors.SoftReplaceProcessor;
import twilightforest.world.components.processors.SpawnerProcessor;
import twilightforest.world.components.processors.StateTransfiguringProcessor;
import twilightforest.world.components.processors.StoneBricksVariants;
import twilightforest.world.components.processors.TargetedRotProcessor;
import twilightforest.world.components.processors.UpdateMarkingProcessor;
import twilightforest.world.components.processors.VerticalDecayProcessor;
import twilightforest.world.components.processors.WoodMultiPaletteSwizzle;
import twilightforest.world.components.processors.WoodPaletteSwizzle;
import twilightforest.world.components.structures.courtyard.CourtyardTerraceTemplateProcessor;

public class TFStructureProcessors {
   public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(
      Registries.STRUCTURE_PROCESSOR, "twilightforest"
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CobbleVariants>> COBBLE_VARIANTS = registerProcessor(
      "cobble_variants", () -> () -> CobbleVariants.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SmoothStoneVariants>> SMOOTH_STONE_VARIANTS = registerProcessor(
      "smooth_stone_variants", () -> () -> SmoothStoneVariants.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<StoneBricksVariants>> STONE_BRICK_VARIANTS = registerProcessor(
      "stone_brick_variants", () -> () -> StoneBricksVariants.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<InfestBlocksProcessor>> INFEST_BLOCKS = registerProcessor(
      "infest_blocks", () -> () -> InfestBlocksProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<NagastoneVariants>> NAGASTONE_VARIANTS = registerProcessor(
      "nagastone_variants", () -> () -> NagastoneVariants.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<StateTransfiguringProcessor>> STATE_TRANSFIGURING = registerProcessor(
      "state_transfiguring", () -> () -> StateTransfiguringProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<WoodPaletteSwizzle>> PLANK_SWIZZLE = registerProcessor(
      "wood_swizzle", () -> () -> WoodPaletteSwizzle.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SmartGrassProcessor>> SMART_GRASS = registerProcessor(
      "smart_grass", () -> () -> SmartGrassProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<BoxCuttingProcessor>> BOX_CUTTING_PROCESSOR = registerProcessor(
      "box_cutting", () -> () -> BoxCuttingProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<TargetedRotProcessor>> TARGETED_ROT = registerProcessor(
      "targeted_rot", () -> () -> TargetedRotProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<GraveyardFeature.WebTemplateProcessor>> WEB = registerProcessor(
      "web", () -> () -> GraveyardFeature.WebTemplateProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CourtyardTerraceTemplateProcessor>> COURTYARD_TERRACE = registerProcessor(
      "courtyard_terrace", () -> () -> CourtyardTerraceTemplateProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SoftReplaceProcessor>> SOFT_REPLACE = registerProcessor(
      "soft_replace", () -> () -> SoftReplaceProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SpawnerProcessor>> SPAWNER_PROCESSOR = registerProcessor(
      "spawner_processor", () -> () -> SpawnerProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<UpdateMarkingProcessor>> UPDATE_MARKING_PROCESSOR = registerProcessor(
      "update_marking", () -> () -> UpdateMarkingProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<VerticalDecayProcessor>> VERTICAL_DECAY = registerProcessor(
      "vertical_decay", () -> () -> VerticalDecayProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<WoodMultiPaletteSwizzle>> PLANK_MULTISWIZZLE = registerProcessor(
      "wood_multiswizzle", () -> () -> WoodMultiPaletteSwizzle.CODEC
   );

   public static <P extends StructureProcessor> DeferredHolder<StructureProcessorType<?>, StructureProcessorType<P>> registerProcessor(
      String name, Supplier<StructureProcessorType<P>> processor
   ) {
      return STRUCTURE_PROCESSORS.register(name, processor);
   }
}
