package net.aurelj.dungeons_arise.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.aurelj.dungeons_arise.WDAStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

public class WDAGenericStructures extends Structure {
   public static final MapCodec<WDAGenericStructures> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
            DimensionPadding.CODEC
               .optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING)
               .forGetter(structure -> structure.dimensionPadding),
            LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings)
         )
         .apply(instance, WDAGenericStructures::new)
   );
   private final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   private final int size;
   private final HeightProvider startHeight;
   private final Optional<Types> projectStartToHeightmap;
   private final int maxDistanceFromCenter;
   private final DimensionPadding dimensionPadding;
   private final LiquidSettings liquidSettings;

   public WDAGenericStructures(
      StructureSettings config,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int size,
      HeightProvider startHeight,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      DimensionPadding dimensionPadding,
      LiquidSettings liquidSettings
   ) {
      super(config);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.size = size;
      this.startHeight = startHeight;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
      this.dimensionPadding = dimensionPadding;
      this.liquidSettings = liquidSettings;
   }

   private static boolean extraSpawningChecks(GenerationContext context) {
      ChunkPos chunkpos = context.chunkPos();
      return context.chunkGenerator()
            .getFirstOccupiedHeight(
               chunkpos.getMinBlockX(), chunkpos.getMinBlockZ(), Types.MOTION_BLOCKING_NO_LEAVES, context.heightAccessor(), context.randomState()
            )
         < 150;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      if (!extraSpawningChecks(context)) {
         return Optional.empty();
      }

      int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      ChunkPos chunkPos = context.chunkPos();
      BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());
      return ModifiedJigsawPlacement.addPieces(
         context,
         this.startPool,
         this.startJigsawName,
         this.size,
         blockPos,
         false,
         this.projectStartToHeightmap,
         this.maxDistanceFromCenter,
         PoolAliasLookup.EMPTY,
         this.dimensionPadding,
         this.liquidSettings
      );
   }

   public StructureType<?> type() {
      return (StructureType<?>)WDAStructures.GENERIC_STRUCTURES.get();
   }
}
