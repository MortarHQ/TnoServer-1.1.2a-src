package net.unusual.block_factorys_bosses.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.unusual.block_factorys_bosses.BossesRise;

@EventBusSubscriber
public class KrakenShipStructure extends Structure {
   public static final MapCodec<KrakenShipStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.maxDepth),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
            Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 160).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
            DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DimensionPadding.ZERO).forGetter(structure -> structure.dimensionPadding),
            LiquidSettings.CODEC.optionalFieldOf("liquid_settings", LiquidSettings.APPLY_WATERLOGGING).forGetter(structure -> structure.liquidSettings)
         )
         .apply(instance, KrakenShipStructure::new)
   );
   private final Holder<StructureTemplatePool> startPool;
   private final Optional<ResourceLocation> startJigsawName;
   private final int maxDepth;
   private final HeightProvider startHeight;
   private final boolean useExpansionHack;
   private final Optional<Types> projectStartToHeightmap;
   private final int maxDistanceFromCenter;
   private final DimensionPadding dimensionPadding;
   private final LiquidSettings liquidSettings;
   private static final Map<Vec3, BlockState> BLOCK_CACHE = new ConcurrentHashMap<>();
   private static final Map<Long, List<Vec3>> SHIP_KEYS = new ConcurrentHashMap<>();

   public KrakenShipStructure(
      StructureSettings settings,
      Holder<StructureTemplatePool> startPool,
      Optional<ResourceLocation> startJigsawName,
      int maxDepth,
      HeightProvider startHeight,
      boolean useExpansionHack,
      Optional<Types> projectStartToHeightmap,
      int maxDistanceFromCenter,
      DimensionPadding dimensionPadding,
      LiquidSettings liquidSettings
   ) {
      super(settings);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.maxDepth = maxDepth;
      this.startHeight = startHeight;
      this.useExpansionHack = useExpansionHack;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
      this.dimensionPadding = dimensionPadding;
      this.liquidSettings = liquidSettings;
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      int startY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
      BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), startY, chunkPos.getMinBlockZ());
      return JigsawPlacement.addPieces(
         context,
         this.startPool,
         this.startJigsawName,
         this.maxDepth,
         startPos,
         this.useExpansionHack,
         this.projectStartToHeightmap,
         this.maxDistanceFromCenter,
         PoolAliasLookup.create(List.of(), startPos, context.seed()),
         this.dimensionPadding,
         this.liquidSettings
      );
   }

   public StructureType<?> type() {
      return (StructureType<?>)BossesRise.KRAKEN_SHIP.get();
   }

   public static void buildShipCache(ServerLevel level, BlockPos shipPos) {
      StructureManager structureManager = level.structureManager();
      Structure fullStructure = (Structure)level.registryAccess()
         .registryOrThrow(Registries.STRUCTURE)
         .getOrThrow(ResourceKey.create(Registries.STRUCTURE, BossesRise.prefix("kraken_ship")));
      StructureStart fullStart = structureManager.getStructureAt(shipPos, fullStructure);
      if (fullStart.isValid()) {
         ChunkPos chunkPos = fullStart.getChunkPos();
         long shipChunkKey = chunkPos.toLong();
         if (!SHIP_KEYS.containsKey(shipChunkKey)) {
            List<StructurePiece> destPieces = buildDestPieces(level, chunkPos);
            if (!destPieces.isEmpty()) {
               ChunkGenerator gen = level.getChunkSource().getGenerator();
               BlockPos pivot = new BlockPos(chunkPos.getMiddleBlockX(), 0, chunkPos.getMiddleBlockZ());
               Map<Vec3, BlockState> destBlocks = new HashMap<>();

               for (StructurePiece piece : destPieces) {
                  BoundingBox bb = piece.getBoundingBox();
                  Map<Vec3, BlockState> snapshot = captureRegion(level, bb);
                  piece.postProcess(level, structureManager, gen, level.getRandom(), bb, chunkPos, pivot);
                  Map<Vec3, BlockState> after = captureRegion(level, bb);

                  for (Entry<Vec3, BlockState> e : after.entrySet()) {
                     BlockState before = snapshot.get(e.getKey());
                     if (!e.getValue().equals(before)) {
                        destBlocks.put(e.getKey(), e.getValue());
                     }
                  }

                  snapshot.forEach((pos, state) -> level.setBlock(BlockPos.containing(pos), state, 3));
               }

               BLOCK_CACHE.putAll(destBlocks);
               SHIP_KEYS.put(shipChunkKey, new ArrayList<>(destBlocks.keySet()));
            }
         }
      }
   }

   public static void replaceWithDestroyed(ServerLevel level, BlockPos pos) {
      BlockState state = BLOCK_CACHE.get(pos.getCenter());
      if (state != null) {
         level.setBlock(pos, state, 3);
      }
   }

   public static void evictCache(ServerLevel level, BlockPos pos) {
      Structure fullStructure = (Structure)level.registryAccess()
         .registryOrThrow(Registries.STRUCTURE)
         .getOrThrow(ResourceKey.create(Registries.STRUCTURE, BossesRise.prefix("kraken_ship")));
      StructureStart fullStart = level.structureManager().getStructureAt(pos, fullStructure);
      if (fullStart.isValid()) {
         List<Vec3> keys = SHIP_KEYS.remove(fullStart.getChunkPos().toLong());
         if (keys != null) {
            keys.forEach(BLOCK_CACHE::remove);
         }
      }
   }

   private static Map<Vec3, BlockState> captureRegion(ServerLevel level, BoundingBox bb) {
      Map<Vec3, BlockState> map = new HashMap<>();

      for (int x = bb.minX(); x <= bb.maxX(); x++) {
         for (int y = bb.minY(); y <= bb.maxY(); y++) {
            for (int z = bb.minZ(); z <= bb.maxZ(); z++) {
               BlockPos p = new BlockPos(x, y, z);
               map.put(p.getCenter(), level.getBlockState(p));
            }
         }
      }

      return map;
   }

   private static List<StructurePiece> buildDestPieces(ServerLevel level, ChunkPos chunkPos) {
      ChunkGenerator chunkGenerator = level.getChunkSource().getGenerator();
      Registry<StructureTemplatePool> poolRegistry = level.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
      Holder<StructureTemplatePool> destPool = poolRegistry.getHolderOrThrow(
         ResourceKey.create(Registries.TEMPLATE_POOL, BossesRise.prefix("kraken_ship_destroyed_seed"))
      );
      GenerationContext genContext = new GenerationContext(
         level.registryAccess(),
         chunkGenerator,
         chunkGenerator.getBiomeSource(),
         level.getChunkSource().randomState(),
         level.getServer().getStructureManager(),
         level.getSeed(),
         chunkPos,
         level,
         biome -> true
      );
      BlockPos startPos = new BlockPos(chunkPos.getMinBlockX(), -4, chunkPos.getMinBlockZ());
      Optional<GenerationStub> stub = JigsawPlacement.addPieces(
         genContext,
         destPool,
         Optional.empty(),
         4,
         startPos,
         false,
         Optional.of(Types.WORLD_SURFACE_WG),
         160,
         PoolAliasLookup.create(List.of(), startPos, level.getSeed()),
         DimensionPadding.ZERO,
         LiquidSettings.APPLY_WATERLOGGING
      );
      if (stub.isEmpty()) {
         return List.of();
      }

      StructurePiecesBuilder builder = new StructurePiecesBuilder();
      stub.get().generator().ifLeft(consumer -> consumer.accept(builder));
      StructurePiecesBuilder finalBuilder = stub.get().generator().right().orElse(builder);
      return List.copyOf(finalBuilder.build().pieces());
   }

   @SubscribeEvent
   public static void onExplosion(Detonate event) {
      if (event.getLevel() instanceof ServerLevel level) {
         event.getAffectedBlocks().forEach(pos -> {
            Vec3 vec = pos.getCenter();
            BLOCK_CACHE.remove(vec);
            SHIP_KEYS.values().forEach(list -> list.remove(vec));
         });
      }
   }
}
