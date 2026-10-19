package com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.pools.EmptyPoolElement;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class MowzieJigsawManager {
   static final Logger LOGGER = LogUtils.getLogger();
   private static Map<String, Integer> poolPlaceOrder = new HashMap<>();
   public static Comparator<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> placeOrderComparator = (p1, p2) -> {
      int p1Order = poolPlaceOrder.getOrDefault(((StructureBlockInfo)p1.getFirst()).nbt().getString("pool"), 0);
      int p2Order = poolPlaceOrder.getOrDefault(((StructureBlockInfo)p2.getFirst()).nbt().getString("pool"), 0);
      int result = Integer.compare(p1Order, p2Order);
      if (result == 0) {
         result = Integer.compare(((MowzieJigsawManager.PieceState)p1.getSecond()).depth, ((MowzieJigsawManager.PieceState)p2.getSecond()).depth);
      }

      if (result == 0) {
         result = Integer.compare(p1.hashCode(), p2.hashCode());
      }

      return result;
   };

   public static Optional<GenerationStub> addPieces(
      GenerationContext context,
      Holder<StructureTemplatePool> pool,
      BlockPos genPos,
      boolean villageBoundaryAdjust,
      boolean useTerrainHeight,
      int maxDistFromStart,
      String pathJigsawName,
      String interiorJigsawName,
      Set<String> mustConnectPools,
      Set<String> replacePools,
      String deadEndConnectorPool,
      int maxDepth
   ) {
      WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
      worldgenrandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);
      RegistryAccess registryaccess = context.registryAccess();
      ChunkGenerator chunkgenerator = context.chunkGenerator();
      StructureTemplateManager structuremanager = context.structureTemplateManager();
      LevelHeightAccessor levelheightaccessor = context.heightAccessor();
      Predicate<Holder<Biome>> predicate = context.validBiome();
      Registry<StructureTemplatePool> registry = registryaccess.registryOrThrow(Registries.TEMPLATE_POOL);
      Rotation rotation = Rotation.getRandom(worldgenrandom);
      StructureTemplatePool structuretemplatepool = (StructureTemplatePool)pool.value();
      StructurePoolElement structurepoolelement = structuretemplatepool.getRandomTemplate(worldgenrandom);
      if (structurepoolelement == EmptyPoolElement.INSTANCE) {
         return Optional.empty();
      }

      PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(
         structuremanager,
         structurepoolelement,
         genPos,
         structurepoolelement.getGroundLevelDelta(),
         rotation,
         structurepoolelement.getBoundingBox(structuremanager, genPos, rotation),
         LiquidSettings.IGNORE_WATERLOGGING
      );
      BoundingBox pieceBoundingBox = poolelementstructurepiece.getBoundingBox();
      BlockPos offset = BlockPos.ZERO;
      if (structurepoolelement instanceof MowziePoolElement) {
         offset = ((MowziePoolElement)structurepoolelement).bounds.offset;
         offset = offset.rotate(rotation);
      }

      int centerX = (pieceBoundingBox.maxX() + pieceBoundingBox.minX()) / 2;
      int centerZ = (pieceBoundingBox.maxZ() + pieceBoundingBox.minZ()) / 2;
      int height;
      if (useTerrainHeight) {
         height = genPos.getY()
            + chunkgenerator.getFirstFreeHeight(
               centerX + offset.getX(), centerZ + offset.getZ(), Types.WORLD_SURFACE_WG, levelheightaccessor, context.randomState()
            )
            + offset.getY();
      } else {
         height = genPos.getY();
      }

      if (!predicate.test(
         chunkgenerator.getBiomeSource()
            .getNoiseBiome(QuartPos.fromBlock(centerX), QuartPos.fromBlock(height), QuartPos.fromBlock(centerZ), context.randomState().sampler())
      )) {
         return Optional.empty();
      }

      int l = pieceBoundingBox.minY() + poolelementstructurepiece.getGroundLevelDelta();
      poolelementstructurepiece.move(0, height - l, 0);
      return Optional.of(
         new GenerationStub(
            new BlockPos(centerX, height, centerZ),
            builder -> {
               List<PoolElementStructurePiece> list = Lists.newArrayList();
               list.add(poolelementstructurepiece);
               if (maxDepth >= 0) {
                  AABB aabb = new AABB(
                     centerX - maxDistFromStart,
                     height - maxDistFromStart,
                     centerZ - maxDistFromStart,
                     centerX + maxDistFromStart + 1,
                     height + maxDistFromStart + 1,
                     centerZ + maxDistFromStart + 1
                  );
                  VoxelShape shape = Shapes.join(Shapes.create(aabb), Shapes.create(AABB.of(pieceBoundingBox)), BooleanOp.ONLY_FIRST);
                  MutableObject<VoxelShape> free = new MutableObject(shape);
                  MutableObject<VoxelShape> interiorFree = new MutableObject(Shapes.empty());
                  MutableObject<Map<String, VoxelShape>> specialBounds = new MutableObject(new HashMap());
                  MowzieJigsawManager.Placer placer = new MowzieJigsawManager.Placer(
                     registry,
                     maxDepth,
                     chunkgenerator,
                     structuremanager,
                     list,
                     worldgenrandom,
                     pathJigsawName,
                     interiorJigsawName,
                     free,
                     interiorFree,
                     specialBounds
                  );
                  String tag = null;
                  if (poolelementstructurepiece.getElement() instanceof MowziePoolElement) {
                     tag = ((MowziePoolElement)poolelementstructurepiece.getElement()).getRandomTag(worldgenrandom);
                  }

                  MowzieJigsawManager.PieceState startingPiece = new MowzieJigsawManager.PieceState(poolelementstructurepiece, 0, null, tag);

                  for (StructureBlockInfo structureBlockInfo : placer.getJigsawBlocksFromPieceState(startingPiece)) {
                     placer.placing.add(new Pair(structureBlockInfo, startingPiece));
                  }

                  while (!placer.placing.isEmpty()) {
                     Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = placer.placing.first();
                     placer.placing.remove(nextJigsawBlock);
                     if (((MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond()).depth > maxDepth) {
                        break;
                     }

                     placer.tryPlacingChildren(
                        (StructureBlockInfo)nextJigsawBlock.getFirst(),
                        (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                        villageBoundaryAdjust,
                        levelheightaccessor,
                        context.randomState()
                     );
                  }

                  MowzieJigsawManager.Placer fallbackPlacer = new MowzieJigsawManager.FallbackPlacer(
                     registry,
                     maxDepth,
                     chunkgenerator,
                     structuremanager,
                     list,
                     worldgenrandom,
                     pathJigsawName,
                     interiorJigsawName,
                     free,
                     interiorFree,
                     specialBounds,
                     placer
                  );

                  while (!fallbackPlacer.placing.isEmpty()) {
                     Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = fallbackPlacer.placing.first();
                     fallbackPlacer.placing.remove(nextJigsawBlock);
                     fallbackPlacer.tryPlacingChildren(
                        (StructureBlockInfo)nextJigsawBlock.getFirst(),
                        (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                        villageBoundaryAdjust,
                        levelheightaccessor,
                        context.randomState()
                     );
                  }

                  MowzieJigsawManager.Placer interiorPlacer = new MowzieJigsawManager.InteriorPlacer(
                     registry,
                     maxDepth,
                     chunkgenerator,
                     structuremanager,
                     list,
                     worldgenrandom,
                     pathJigsawName,
                     interiorJigsawName,
                     free,
                     interiorFree,
                     specialBounds,
                     fallbackPlacer
                  );

                  while (!interiorPlacer.placing.isEmpty()) {
                     Pair<StructureBlockInfo, MowzieJigsawManager.PieceState> nextJigsawBlock = interiorPlacer.placing.first();
                     interiorPlacer.placing.remove(nextJigsawBlock);
                     interiorPlacer.tryPlacingChildren(
                        (StructureBlockInfo)nextJigsawBlock.getFirst(),
                        (MowzieJigsawManager.PieceState)nextJigsawBlock.getSecond(),
                        villageBoundaryAdjust,
                        levelheightaccessor,
                        context.randomState()
                     );
                  }

                  list.sort((p1, p2) -> {
                     int i2 = 0;
                     int i1 = 0;
                     if (p1.getElement() instanceof MowziePoolElement) {
                        i1 = ((MowziePoolElement)p1.getElement()).genOrder;
                     }

                     if (p2.getElement() instanceof MowziePoolElement) {
                        i2 = ((MowziePoolElement)p2.getElement()).genOrder;
                     }

                     return Integer.compare(i1, i2);
                  });
                  list.forEach(builder::addPiece);
               }
            }
         )
      );
   }

   static {
      poolPlaceOrder.put("mowziesmobs:monastery/interior/wall_corner_pool", -2);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/wall_middle_pool", -2);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/center_pool", -3);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/blocker_pool", -4);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/tower_stairs_1_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/tower_stairs_2_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/room_stairs_1_pool", -5);
      poolPlaceOrder.put("mowziesmobs:monastery/interior/room_stairs_2_pool", -5);
   }

   static final class FallbackPlacer extends MowzieJigsawManager.Placer {
      FallbackPlacer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds,
         MowzieJigsawManager.Placer previousPlacer
      ) {
         super(p_210323_, p_210324_, p_210326_, p_210327_, p_210328_, p_210329_, pathJigsawName, interiorJigsawName, free, interiorFree, specialBounds);
         this.placing.addAll(previousPlacer.placing);
         this.placing.addAll(previousPlacer.fallbacks);
         this.interior.addAll(previousPlacer.interior);
         this.numPaths = previousPlacer.numPaths;
      }

      @Override
      List<StructurePoolElement> addPoolElements(MowzieJigsawManager.PieceState pieceState, StructureTemplatePool pool, StructureTemplatePool fallbackPool) {
         List<StructurePoolElement> structurePoolElements = Lists.newArrayList();
         structurePoolElements.addAll(fallbackPool.getShuffledTemplates(this.random));
         return structurePoolElements;
      }
   }

   static final class InteriorPlacer extends MowzieJigsawManager.Placer {
      InteriorPlacer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds,
         MowzieJigsawManager.Placer previousPlacer
      ) {
         super(p_210323_, p_210324_, p_210326_, p_210327_, p_210328_, p_210329_, pathJigsawName, interiorJigsawName, free, interiorFree, specialBounds);
         this.placing.addAll(previousPlacer.interior);
         this.free = interiorFree;
         this.numPaths = previousPlacer.numPaths;
      }

      @Override
      protected boolean skipJigsawBlock(StructureBlockInfo thisPieceJigsawBlock, MowzieJigsawManager.PieceState pieceState) {
         return false;
      }

      @Override
      protected boolean checkBounds(
         StructurePoolElement nextPieceCandidate,
         MowzieJigsawManager.PieceState pieceState,
         BoundingBox nextPieceBoundingBoxPlaced,
         BlockPos nextPiecePos,
         Rotation nextPieceRotation,
         int i2
      ) {
         boolean ignoreBounds = false;
         BoundingBox spaceCheckBounds = nextPieceBoundingBoxPlaced;
         if (nextPieceCandidate instanceof MowziePoolElement) {
            ignoreBounds = ((MowziePoolElement)nextPieceCandidate).ignoresBounds();
            spaceCheckBounds = ((MowziePoolElement)nextPieceCandidate)
               .getCheckBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation)
               .moved(0, i2, 0);
         }

         return ignoreBounds
            || !Shapes.joinIsNotEmpty((VoxelShape)this.free.getValue(), Shapes.create(AABB.of(spaceCheckBounds).deflate(0.25)), BooleanOp.ONLY_SECOND);
      }
   }

   public interface PieceFactory {
      PoolElementStructurePiece create(StructureTemplateManager var1, StructurePoolElement var2, BlockPos var3, int var4, Rotation var5, BoundingBox var6);
   }

   public record PieceSelection(
      MowzieJigsawManager.PieceState pieceState,
      StructurePoolElement origPiece,
      StructurePoolElement nextPiece,
      PoolElementStructurePiece poolelementstructurepiece,
      StructureBlockInfo origJigsaw,
      StructureBlockInfo connectedJigsaw,
      StructureBlockInfo nextJigsaw,
      BoundingBox nextPieceBoundingBoxPlaced,
      BoundingBox nextPieceInteriorBoundingBox,
      int l2
   ) {
   }

   static class PieceState {
      final PoolElementStructurePiece piece;
      final int depth;
      final MowzieJigsawManager.PieceState parent;
      final Set<MowzieJigsawManager.PieceState> children;
      final String tag;

      PieceState(PoolElementStructurePiece p_210311_, int p_210313_, MowzieJigsawManager.PieceState parent, String tag) {
         this.piece = p_210311_;
         this.depth = p_210313_;
         this.parent = parent;
         this.children = new HashSet<>();
         this.tag = tag;
      }
   }

   static class Placer {
      final Registry<StructureTemplatePool> pools;
      final int maxDepth;
      final ChunkGenerator chunkGenerator;
      final StructureTemplateManager structureManager;
      final List<? super PoolElementStructurePiece> pieces;
      protected final RandomSource random;
      protected final String pathJigsawName;
      protected final String interiorJigsawName;
      MutableObject<VoxelShape> free;
      MutableObject<VoxelShape> interiorFree;
      MutableObject<Map<String, VoxelShape>> specialBounds;
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> placing = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> fallbacks = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      final SortedSet<Pair<StructureBlockInfo, MowzieJigsawManager.PieceState>> interior = new TreeSet<>(MowzieJigsawManager.placeOrderComparator);
      protected int numPaths;

      Placer(
         Registry<StructureTemplatePool> p_210323_,
         int p_210324_,
         ChunkGenerator p_210326_,
         StructureTemplateManager p_210327_,
         List<? super PoolElementStructurePiece> p_210328_,
         RandomSource p_210329_,
         String pathJigsawName,
         String interiorJigsawName,
         MutableObject<VoxelShape> free,
         MutableObject<VoxelShape> interiorFree,
         MutableObject<Map<String, VoxelShape>> specialBounds
      ) {
         this.pools = p_210323_;
         this.maxDepth = p_210324_;
         this.chunkGenerator = p_210326_;
         this.structureManager = p_210327_;
         this.pieces = p_210328_;
         this.random = p_210329_;
         this.numPaths = 0;
         this.pathJigsawName = pathJigsawName;
         this.interiorJigsawName = interiorJigsawName;
         this.free = free;
         this.interiorFree = interiorFree;
         this.specialBounds = specialBounds;
      }

      void tryPlacingChildren(
         StructureBlockInfo thisPieceJigsawBlock,
         MowzieJigsawManager.PieceState pieceState,
         boolean villageBoundaryAdjust,
         LevelHeightAccessor heightAccessor,
         RandomState state
      ) {
         if (!this.skipJigsawBlock(thisPieceJigsawBlock, pieceState)) {
            if (thisPieceJigsawBlock.nbt().getString("target").equals(this.pathJigsawName) && this.numPaths > 0) {
               this.numPaths--;
            }

            String pool = this.selectPool(thisPieceJigsawBlock);
            ResourceLocation poolResourceLocation = ResourceLocation.tryParse(pool);
            Optional<StructureTemplatePool> poolOptional = this.pools.getOptional(poolResourceLocation);
            if (poolOptional.isPresent() && (poolOptional.get().size() != 0 || Objects.equals(poolResourceLocation, Pools.EMPTY.location()))) {
               Holder<StructureTemplatePool> fallbackPoolHolder = poolOptional.get().getFallback();
               if (((StructureTemplatePool)fallbackPoolHolder.value()).size() == 0 && !fallbackPoolHolder.is(Pools.EMPTY)) {
                  MowzieJigsawManager.LOGGER
                     .warn(
                        "Empty or non-existent fallback pool: {}",
                        fallbackPoolHolder.unwrapKey().map(p_255599_ -> p_255599_.location().toString()).orElse("<unregistered>")
                     );
               } else {
                  MowzieJigsawManager.PieceSelection pieceSelection = this.selectPiece(
                     pieceState,
                     poolOptional.get(),
                     (StructureTemplatePool)fallbackPoolHolder.value(),
                     villageBoundaryAdjust,
                     thisPieceJigsawBlock,
                     heightAccessor,
                     state
                  );
                  if (pieceSelection != null) {
                     this.addNextPieceState(pieceSelection);
                  }
               }
            } else {
               MowzieJigsawManager.LOGGER.warn("Empty or non-existent pool: {}", poolResourceLocation);
            }
         }
      }

      protected boolean skipJigsawBlock(StructureBlockInfo thisPieceJigsawBlock, MowzieJigsawManager.PieceState pieceState) {
         if (thisPieceJigsawBlock.nbt().getString("target").equals(this.interiorJigsawName)) {
            this.interior.add(Pair.of(thisPieceJigsawBlock, pieceState));
            return true;
         } else {
            return false;
         }
      }

      List<StructureBlockInfo> getJigsawBlocksFromPieceState(MowzieJigsawManager.PieceState pieceState) {
         StructurePoolElement structurepoolelement = pieceState.piece.getElement();
         BlockPos blockpos = pieceState.piece.getPosition();
         Rotation rotation = pieceState.piece.getRotation();
         return structurepoolelement.getShuffledJigsawBlocks(this.structureManager, blockpos, rotation, this.random);
      }

      String selectPool(StructureBlockInfo thisPieceJigsawBlock) {
         return thisPieceJigsawBlock.nbt().getString("pool");
      }

      List<StructurePoolElement> addPoolElements(MowzieJigsawManager.PieceState pieceState, StructureTemplatePool pool, StructureTemplatePool fallbackPool) {
         List<StructurePoolElement> structurePoolElements = Lists.newArrayList();
         structurePoolElements.addAll(pool.getShuffledTemplates(this.random));
         return structurePoolElements;
      }

      boolean isJigsawBlockFacingFreeSpace(StructureBlockInfo jigsawBlockInfo, MutableObject<VoxelShape> freeSpace) {
         Direction direction = JigsawBlock.getFrontFacing(jigsawBlockInfo.state());
         BlockPos thisJigsawBlockPos = jigsawBlockInfo.pos();
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.relative(direction);
         AABB aabb = new AABB(nextJigsawBlockPos);
         return !Shapes.joinIsNotEmpty((VoxelShape)freeSpace.getValue(), Shapes.create(aabb.deflate(0.25)), BooleanOp.ONLY_SECOND);
      }

      boolean canJigsawBlockFitNextPiece(StructureBlockInfo jigsawBlockInfo, MutableObject<VoxelShape> freeSpace) {
         Direction direction = JigsawBlock.getFrontFacing(jigsawBlockInfo.state());
         BlockPos thisJigsawBlockPos = jigsawBlockInfo.pos();
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.relative(direction);
         AABB aabb = new AABB(nextJigsawBlockPos);
         Vec3i offset = direction.getNormal();
         aabb = aabb.inflate(2.0).move(offset.getX() * 2, offset.getY() * 2, offset.getZ() * 2);
         return !Shapes.joinIsNotEmpty((VoxelShape)freeSpace.getValue(), Shapes.create(aabb.deflate(0.25)), BooleanOp.ONLY_SECOND);
      }

      MowzieJigsawManager.PieceSelection selectPiece(
         MowzieJigsawManager.PieceState pieceState,
         StructureTemplatePool poolOptional,
         StructureTemplatePool fallbackPoolOptional,
         boolean villageBoundaryAdjust,
         StructureBlockInfo thisPieceJigsawBlock,
         LevelHeightAccessor heightAccessor,
         RandomState state
      ) {
         BoundingBox thisPieceBoundingBox = pieceState.piece.getBoundingBox();
         int thisPieceMinY = thisPieceBoundingBox.minY();
         Direction direction = JigsawBlock.getFrontFacing(thisPieceJigsawBlock.state());
         BlockPos thisJigsawBlockPos = thisPieceJigsawBlock.pos();
         BlockPos nextJigsawBlockPos = thisJigsawBlockPos.relative(direction);
         int thisPieceHeightFromBottomToJigsawBlock = thisJigsawBlockPos.getY() - thisPieceMinY;
         StructurePoolElement structurepoolelement = pieceState.piece.getElement();
         Projection structuretemplatepool$projection = structurepoolelement.getProjection();
         boolean thisPieceIsRigid = structuretemplatepool$projection == Projection.RIGID;
         int k = -1;
         List<StructurePoolElement> structurePoolElements = this.addPoolElements(pieceState, poolOptional, fallbackPoolOptional);
         structurePoolElements.sort((p1, p2) -> {
            int i2x = 0;
            int i1 = 0;
            if (p1 instanceof MowziePoolElement) {
               i1 = ((MowziePoolElement)p1).priority;
            }

            if (p2 instanceof MowziePoolElement) {
               i2x = ((MowziePoolElement)p2).priority;
            }

            return Integer.compare(i1, i2x);
         });

         label169:
         for (StructurePoolElement nextPieceCandidate : structurePoolElements) {
            if (nextPieceCandidate != FallbackPoolElement.INSTANCE) {
               if (nextPieceCandidate != EmptyPoolElement.INSTANCE
                  && (
                     !(nextPieceCandidate instanceof SinglePoolElement)
                        || !((SinglePoolElement)nextPieceCandidate).toString().equals("Single[Left[minecraft:empty]]")
                  )) {
                  if (nextPieceCandidate instanceof MowziePoolElement mowziePoolElement && !mowziePoolElement.checkCriteria(pieceState, this)) {
                     continue;
                  }

                  Iterator var46 = Rotation.getShuffled(this.random).iterator();

                  Rotation nextPieceRotation;
                  StructureBlockInfo nextPieceJigsawBlock;
                  BlockPos nextPiecePos;
                  boolean nextPieceIsRigid;
                  int nextPieceJigsawBlockY;
                  int k1;
                  int l1;
                  int i2;
                  BoundingBox nextPieceBoundingBoxPlaced;
                  BlockPos blockpos5;
                  label155:
                  while (true) {
                     if (!var46.hasNext()) {
                        continue label169;
                     }

                     nextPieceRotation = (Rotation)var46.next();
                     List<StructureBlockInfo> nextPieceJigsawBlocks = nextPieceCandidate.getShuffledJigsawBlocks(
                        this.structureManager, BlockPos.ZERO, nextPieceRotation, this.random
                     );
                     BoundingBox nextPieceBoundingBoxOrigin = nextPieceCandidate.getBoundingBox(this.structureManager, BlockPos.ZERO, nextPieceRotation);
                     int largestSizeOfNextNextPiece;
                     if (villageBoundaryAdjust && nextPieceBoundingBoxOrigin.getYSpan() <= 16) {
                        largestSizeOfNextNextPiece = nextPieceJigsawBlocks.stream()
                           .mapToInt(
                              blockInfo -> {
                                 if (!nextPieceBoundingBoxOrigin.isInside(blockInfo.pos().relative(JigsawBlock.getFrontFacing(blockInfo.state())))) {
                                    return 0;
                                 }

                                 ResourceLocation resourcelocation2 = ResourceLocation.tryParse(blockInfo.nbt().getString("pool"));
                                 Optional<StructureTemplatePool> optional2 = this.pools.getOptional(resourcelocation2);
                                 Optional<StructureTemplatePool> optional3 = optional2.flatMap(
                                    p_210344_ -> this.pools.getOptional((ResourceKey)p_210344_.getFallback().unwrapKey().get())
                                 );
                                 int j3 = optional2.<Integer>map(structureTemplatePool -> structureTemplatePool.getMaxSize(this.structureManager)).orElse(0);
                                 int k3 = optional3.<Integer>map(structureTemplatePool -> structureTemplatePool.getMaxSize(this.structureManager)).orElse(0);
                                 return Math.max(j3, k3);
                              }
                           )
                           .max()
                           .orElse(0);
                     } else {
                        largestSizeOfNextNextPiece = 0;
                     }

                     for (nextPieceJigsawBlock : nextPieceJigsawBlocks) {
                        boolean canAttach;
                        if (nextPieceCandidate instanceof MowziePoolElement && ((MowziePoolElement)nextPieceCandidate).twoWay()) {
                           canAttach = MowziePoolElement.canAttachTwoWays(thisPieceJigsawBlock, nextPieceJigsawBlock);
                        } else {
                           canAttach = JigsawBlock.canAttach(thisPieceJigsawBlock, nextPieceJigsawBlock);
                        }

                        if (canAttach) {
                           BlockPos nextPieceJigsawBlockPos = nextPieceJigsawBlock.pos();
                           nextPiecePos = nextJigsawBlockPos.subtract(nextPieceJigsawBlockPos);
                           BoundingBox nextPieceBoundingBox = nextPieceCandidate.getBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation);
                           int nextPieceMinY = nextPieceBoundingBox.minY();
                           if (nextPieceCandidate instanceof MowziePoolElement) {
                              nextPieceMinY -= ((MowziePoolElement)nextPieceCandidate).bounds.boundsMinOffset.getY();
                           }

                           Projection structuretemplatepool$projection1 = nextPieceCandidate.getProjection();
                           nextPieceIsRigid = structuretemplatepool$projection1 == Projection.RIGID;
                           nextPieceJigsawBlockY = nextPieceJigsawBlockPos.getY();
                           k1 = thisPieceHeightFromBottomToJigsawBlock
                              - nextPieceJigsawBlockY
                              + JigsawBlock.getFrontFacing(thisPieceJigsawBlock.state()).getStepY();
                           if (thisPieceIsRigid && nextPieceIsRigid) {
                              l1 = thisPieceMinY + k1;
                           } else {
                              if (k == -1) {
                                 k = this.chunkGenerator
                                    .getFirstFreeHeight(thisJigsawBlockPos.getX(), thisJigsawBlockPos.getZ(), Types.WORLD_SURFACE_WG, heightAccessor, state);
                              }

                              l1 = k - nextPieceJigsawBlockY;
                           }

                           i2 = l1 - nextPieceMinY;
                           nextPieceBoundingBoxPlaced = nextPieceBoundingBox.moved(0, i2, 0);
                           blockpos5 = nextPiecePos.offset(0, i2, 0);
                           if (largestSizeOfNextNextPiece > 0) {
                              int j2 = Math.max(largestSizeOfNextNextPiece + 1, nextPieceBoundingBoxPlaced.maxY() - nextPieceBoundingBoxPlaced.minY());
                              nextPieceBoundingBoxPlaced.encapsulate(
                                 new BlockPos(nextPieceBoundingBoxPlaced.minX(), nextPieceBoundingBoxPlaced.minY() + j2, nextPieceBoundingBoxPlaced.minZ())
                              );
                           }

                           if (nextPieceCandidate instanceof MowziePoolElement) {
                              Optional<Integer> maxHeight = ((MowziePoolElement)nextPieceCandidate).conditions.maxHeight;
                              Optional<Integer> minHeight = ((MowziePoolElement)nextPieceCandidate).conditions.minHeight;
                              if (maxHeight.isPresent() || minHeight.isPresent()) {
                                 int freeHeight = this.chunkGenerator
                                    .getFirstFreeHeight(
                                       nextPieceBoundingBoxPlaced.minX() + nextPieceBoundingBoxPlaced.getXSpan() / 2,
                                       nextPieceBoundingBoxPlaced.minZ() + nextPieceBoundingBoxPlaced.getZSpan() / 2,
                                       Types.WORLD_SURFACE_WG,
                                       heightAccessor,
                                       state
                                    );
                                 if (maxHeight.isPresent() && nextPieceMinY - freeHeight > maxHeight.get()
                                    || minHeight.isPresent() && nextPieceMinY - freeHeight < minHeight.get()) {
                                    continue;
                                 }
                              }
                           }

                           if (this.checkBounds(nextPieceCandidate, pieceState, nextPieceBoundingBoxPlaced, nextPiecePos, nextPieceRotation, i2)) {
                              if (!(nextPieceCandidate instanceof MowziePoolElement mowziePoolElement)) {
                                 break label155;
                              }

                              Optional<String> needsOverlapBounds = mowziePoolElement.bounds.needsOverlapBounds;
                              if (!needsOverlapBounds.isPresent()
                                 || ((Map)this.specialBounds.getValue()).containsKey(needsOverlapBounds.get())
                                    && Shapes.joinIsNotEmpty(
                                       (VoxelShape)((Map)this.specialBounds.getValue()).get(needsOverlapBounds.get()),
                                       Shapes.create(AABB.of(nextPieceBoundingBoxPlaced).deflate(0.25)),
                                       BooleanOp.AND
                                    )) {
                                 Optional<String> forbiddenOverlapBounds = mowziePoolElement.bounds.forbiddenOverlapBounds;
                                 if (!forbiddenOverlapBounds.isPresent()
                                    || !((Map)this.specialBounds.getValue()).containsKey(forbiddenOverlapBounds.get())
                                    || !Shapes.joinIsNotEmpty(
                                       (VoxelShape)((Map)this.specialBounds.getValue()).get(forbiddenOverlapBounds.get()),
                                       Shapes.create(AABB.of(nextPieceBoundingBoxPlaced).deflate(0.25)),
                                       BooleanOp.AND
                                    )) {
                                    break label155;
                                 }
                              }
                           }
                        }
                     }
                  }

                  int pieceGroundLevelDelta = pieceState.piece.getGroundLevelDelta();
                  int k2;
                  if (nextPieceIsRigid) {
                     k2 = pieceGroundLevelDelta - k1;
                  } else {
                     k2 = nextPieceCandidate.getGroundLevelDelta();
                  }

                  PoolElementStructurePiece poolelementstructurepiece = new PoolElementStructurePiece(
                     this.structureManager,
                     nextPieceCandidate,
                     blockpos5,
                     k2,
                     nextPieceRotation,
                     nextPieceBoundingBoxPlaced,
                     LiquidSettings.IGNORE_WATERLOGGING
                  );
                  int l2;
                  if (thisPieceIsRigid) {
                     l2 = thisPieceMinY + thisPieceHeightFromBottomToJigsawBlock;
                  } else if (nextPieceIsRigid) {
                     l2 = l1 + nextPieceJigsawBlockY;
                  } else {
                     if (k == -1) {
                        k = this.chunkGenerator
                           .getFirstFreeHeight(thisJigsawBlockPos.getX(), thisJigsawBlockPos.getZ(), Types.WORLD_SURFACE_WG, heightAccessor, state);
                     }

                     l2 = k + k1 / 2;
                  }

                  BoundingBox interiorBounds = null;
                  if (nextPieceCandidate instanceof MowziePoolElement) {
                     interiorBounds = ((MowziePoolElement)nextPieceCandidate).getInteriorBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation);
                     if (interiorBounds != null) {
                        interiorBounds = interiorBounds.moved(0, i2, 0);
                     }
                  }

                  return new MowzieJigsawManager.PieceSelection(
                     pieceState,
                     structurepoolelement,
                     nextPieceCandidate,
                     poolelementstructurepiece,
                     thisPieceJigsawBlock,
                     nextPieceJigsawBlock,
                     null,
                     nextPieceBoundingBoxPlaced,
                     interiorBounds,
                     l2
                  );
               }

               return null;
            }
            break;
         }

         this.fallbacks.add(new Pair(thisPieceJigsawBlock, pieceState));
         return null;
      }

      protected boolean checkBounds(
         StructurePoolElement nextPieceCandidate,
         MowzieJigsawManager.PieceState pieceState,
         BoundingBox nextPieceBoundingBoxPlaced,
         BlockPos nextPiecePos,
         Rotation nextPieceRotation,
         int i2
      ) {
         boolean ignoreBounds = false;
         BoundingBox spaceCheckBounds = nextPieceBoundingBoxPlaced;
         if (nextPieceCandidate instanceof MowziePoolElement) {
            ignoreBounds = ((MowziePoolElement)nextPieceCandidate).ignoresBounds();
            spaceCheckBounds = ((MowziePoolElement)nextPieceCandidate)
               .getCheckBoundingBox(this.structureManager, nextPiecePos, nextPieceRotation)
               .moved(0, i2, 0);
         }

         VoxelShape freeSpace = Shapes.joinUnoptimized(
            (VoxelShape)this.free.getValue(), Shapes.create(AABB.of(pieceState.piece.getBoundingBox())), BooleanOp.OR
         );
         return ignoreBounds || !Shapes.joinIsNotEmpty(freeSpace, Shapes.create(AABB.of(spaceCheckBounds).deflate(0.25)), BooleanOp.ONLY_SECOND);
      }

      void addNextPieceState(MowzieJigsawManager.PieceSelection pieceSelection) {
         if (!(pieceSelection.nextPiece instanceof MowziePoolElement)
            || !((MowziePoolElement)pieceSelection.nextPiece).ignoresBounds() && ((MowziePoolElement)pieceSelection.nextPiece).placeBounds()) {
            this.free
               .setValue(
                  Shapes.joinUnoptimized(
                     (VoxelShape)this.free.getValue(), Shapes.create(AABB.of(pieceSelection.nextPieceBoundingBoxPlaced)), BooleanOp.ONLY_FIRST
                  )
               );
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement && pieceSelection.nextPieceInteriorBoundingBox != null) {
            this.interiorFree
               .setValue(
                  Shapes.joinUnoptimized(
                     (VoxelShape)this.interiorFree.getValue(), Shapes.create(AABB.of(pieceSelection.nextPieceInteriorBoundingBox)), BooleanOp.OR
                  )
               );
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement mowziePoolElement) {
            Optional<String> nextPieceSpecialBounds = mowziePoolElement.bounds.specialBounds;
            if (nextPieceSpecialBounds.isPresent()) {
               if (((Map)this.specialBounds.getValue()).containsKey(nextPieceSpecialBounds.get())) {
                  ((Map)this.specialBounds.getValue())
                     .put(
                        nextPieceSpecialBounds.get(),
                        Shapes.joinUnoptimized(
                           (VoxelShape)((Map)this.specialBounds.getValue()).get(nextPieceSpecialBounds.get()),
                           Shapes.create(AABB.of(pieceSelection.nextPieceBoundingBoxPlaced)),
                           BooleanOp.OR
                        )
                     );
               } else {
                  ((Map)this.specialBounds.getValue()).put(nextPieceSpecialBounds.get(), Shapes.create(AABB.of(pieceSelection.nextPieceBoundingBoxPlaced)));
               }
            }
         }

         BoundingBox thisPieceBoundingBox = pieceSelection.pieceState.piece.getBoundingBox();
         int thisPieceMinY = thisPieceBoundingBox.minY();
         int thisPieceHeightFromBottomToJigsawBlock = pieceSelection.origJigsaw.pos().getY() - thisPieceMinY;
         int pieceGroundLevelDelta = pieceSelection.pieceState.piece.getGroundLevelDelta();
         int k1 = thisPieceHeightFromBottomToJigsawBlock
            - pieceSelection.connectedJigsaw.pos().getY()
            + JigsawBlock.getFrontFacing(pieceSelection.origJigsaw.state()).getStepY();
         int k2;
         if (pieceSelection.nextPiece.getProjection() == Projection.RIGID) {
            k2 = pieceGroundLevelDelta - k1;
         } else {
            k2 = pieceSelection.nextPiece.getGroundLevelDelta();
         }

         pieceSelection.pieceState
            .piece
            .addJunction(
               new JigsawJunction(
                  pieceSelection.connectedJigsaw.pos().getX(),
                  pieceSelection.l2 - thisPieceHeightFromBottomToJigsawBlock + pieceGroundLevelDelta,
                  pieceSelection.connectedJigsaw.pos().getZ(),
                  k1,
                  pieceSelection.nextPiece.getProjection()
               )
            );
         pieceSelection.poolelementstructurepiece
            .addJunction(
               new JigsawJunction(
                  pieceSelection.origJigsaw.pos().getX(),
                  pieceSelection.l2 - pieceSelection.connectedJigsaw.pos().getY() + k2,
                  pieceSelection.origJigsaw.pos().getZ(),
                  -k1,
                  pieceSelection.origPiece.getProjection()
               )
            );
         this.pieces.add(pieceSelection.poolelementstructurepiece);
         String tag = null;
         if (pieceSelection.nextPiece instanceof MowziePoolElement mowziePoolElement) {
            if (mowziePoolElement.tags.inheritsTag) {
               tag = pieceSelection.pieceState.tag;
            } else {
               tag = ((MowziePoolElement)pieceSelection.nextPiece).getRandomTag(this.random);
            }
         }

         MowzieJigsawManager.PieceState nextPieceState = new MowzieJigsawManager.PieceState(
            pieceSelection.poolelementstructurepiece, pieceSelection.pieceState.depth + 1, pieceSelection.pieceState, tag
         );
         List<StructureBlockInfo> nextJigsaws = this.getJigsawBlocksFromPieceState(nextPieceState);
         if (!(pieceSelection.poolelementstructurepiece.getElement() instanceof MowziePoolElement)
            || !((MowziePoolElement)pieceSelection.poolelementstructurepiece.getElement()).twoWay()) {
            nextJigsaws.removeIf(jigsawx -> {
               Direction direction = JigsawBlock.getFrontFacing(pieceSelection.origJigsaw.state());
               BlockPos thisJigsawBlockPos = pieceSelection.origJigsaw.pos();
               BlockPos nextJigsawBlockPos = thisJigsawBlockPos.relative(direction);
               return jigsawx.pos().equals(nextJigsawBlockPos);
            });
         }

         if (pieceSelection.nextPiece instanceof MowziePoolElement && ((MowziePoolElement)pieceSelection.nextPiece).conditions.numPathsOverride.isPresent()) {
            this.numPaths = this.numPaths + ((MowziePoolElement)pieceSelection.nextPiece).conditions.numPathsOverride.get();
         } else {
            for (StructureBlockInfo jigsaw : nextJigsaws) {
               if (jigsaw.nbt().getString("target").equals(this.pathJigsawName)) {
                  this.numPaths++;
               }
            }
         }

         for (StructureBlockInfo jigsaw : nextJigsaws) {
            this.placing.add(new Pair(jigsaw, nextPieceState));
         }

         pieceSelection.pieceState.children.add(nextPieceState);
      }
   }
}
