package net.jadenxgamer.elysium_api.impl.core.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.jadenxgamer.elysium_api.api.tags.ElysiumTags;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.jetbrains.annotations.NotNull;

public class MultilayerJigsawStructure extends Structure {
   public static final DimensionPadding DEFAULT_DIMENSION_PADDING = DimensionPadding.ZERO;
   public static final LiquidSettings DEFAULT_LIQUID_SETTINGS = LiquidSettings.APPLY_WATERLOGGING;
   private final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   private final int size;
   private final HeightProvider startHeight;
   private final Optional<Types> projectStartToHeightmap;
   private final int maxDistanceFromCenter;
   private final List<PoolAliasBinding> poolAliases;
   private final DimensionPadding dimensionPadding;
   private final LiquidSettings liquidSettings;
   public static final MapCodec<MultilayerJigsawStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
            Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
            DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING).forGetter(structure -> structure.dimensionPadding),
            LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings)
         )
         .apply(instance, MultilayerJigsawStructure::new)
   );

   public MultilayerJigsawStructure(
      StructureSettings config,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int size,
      HeightProvider startHeight,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      List<PoolAliasBinding> poolAliases,
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
      this.poolAliases = poolAliases;
      this.dimensionPadding = dimensionPadding;
      this.liquidSettings = liquidSettings;
   }

   public @NotNull Decoration step() {
      return Decoration.SURFACE_STRUCTURES;
   }

   @NotNull
   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      WorldgenRandom random = context.random();
      int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
      int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
      WorldGenerationContext heightContext = new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor());
      int y = this.startHeight.sample(random, heightContext);
      NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());
      int seaLevel = context.chunkGenerator().getSeaLevel();
      BlockPos placementPos = this.findPlacementPosition(noiseColumn, x, y, z, seaLevel);
      if (placementPos == null) {
         return Optional.empty();
      }

      PoolAliasLookup aliasLookup = PoolAliasLookup.create(this.poolAliases, placementPos, context.seed());
      return JigsawPlacement.addPieces(
         context,
         this.startPool,
         this.startJigsawName,
         this.size,
         placementPos,
         false,
         this.projectStartToHeightmap,
         this.maxDistanceFromCenter,
         aliasLookup,
         this.dimensionPadding,
         this.liquidSettings
      );
   }

   private BlockPos findPlacementPosition(NoiseColumn noiseColumn, int x, int startY, int z, int seaLevel) {
      MutableBlockPos mutablePos = new MutableBlockPos(x, startY, z);

      for (int currentY = startY; currentY > seaLevel; currentY--) {
         BlockState currentBlock = noiseColumn.getBlock(currentY);
         BlockState belowBlock = noiseColumn.getBlock(currentY - 1);
         if (currentBlock.isAir() && this.isSuitableSurface(belowBlock, mutablePos.setY(currentY - 1))) {
            return new BlockPos(x, currentY - 1, z);
         }
      }

      return null;
   }

   private boolean isSuitableSurface(BlockState state, BlockPos pos) {
      return state.is(ElysiumTags.Blocks.MULTILAYER_JIGSAW_NON_SOLID_VALID) || state.isFaceSturdy(EmptyBlockGetter.INSTANCE, pos, Direction.UP);
   }

   @NotNull
   public StructureType<?> type() {
      return ElysiumRegistries.MULTILAYERED_JIGSAW.get();
   }
}
