package fuzs.illagerinvasion.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

public class LabyrinthStructure extends JigsawStructure {
   private static final int SEA_LEVEL = 63;
   private static final int SAFE_UNDERGROUND_TUNNEL_HEIGHT = 16;
   public static final MapCodec<LabyrinthStructure> CODEC = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
               settingsCodec(instance),
               StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
               ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
               Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.maxDepth),
               HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
               Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
               Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
               Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
               Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
               DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING).forGetter(structure -> structure.dimensionPadding),
               LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings)
            )
            .apply(instance, LabyrinthStructure::new)
      )
      .validate(labyrinthStructure -> verifyRange(labyrinthStructure).map(LabyrinthStructure.class::cast));

   public LabyrinthStructure(
      StructureSettings settings,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int maxDepth,
      HeightProvider startHeight,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      List<PoolAliasBinding> poolAliases,
      DimensionPadding dimensionPadding,
      LiquidSettings liquidSettings
   ) {
      super(
         settings,
         startPool,
         startJigsawName,
         maxDepth,
         startHeight,
         useExpansionHack,
         projectStartToHeightmap,
         maxDistanceFromCenter,
         poolAliases,
         dimensionPadding,
         liquidSettings
      );
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      return super.findGenerationPoint(context).filter(generationStub -> generationStub.position().getY() <= 47);
   }

   public StructureType<?> type() {
      return (StructureType<?>)ModRegistry.LABYRINTH_STRUCTURE_TYPE.value();
   }
}
