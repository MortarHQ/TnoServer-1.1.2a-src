package twilightforest.world.components.structures.lichtowerrevamp;

import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.joml.SimplexNoise;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.SortablePiece;

public class LichYardBox extends StructurePiece implements PieceBeardifierModifier, SortablePiece, SpawnIndexProvider {
   @Autowired
   private static LichTowerUtil lichTowerUtil;
   private final float edgeFeatheringRange;
   private final Direction direction;
   private final boolean doDirtMotley;
   private final float scale;
   private final float offset;

   public LichYardBox(BoundingBox boundingBox, float edgeFeatheringRange, Direction direction, boolean doDirtMotley, float scale, float offset) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_YARD_PATH.value(), 0, boundingBox);
      this.edgeFeatheringRange = edgeFeatheringRange;
      this.direction = direction;
      this.doDirtMotley = doDirtMotley;
      this.scale = scale;
      this.offset = offset;
   }

   public LichYardBox(StructurePieceSerializationContext ctx, CompoundTag tag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_YARD_PATH.value(), tag);
      this.edgeFeatheringRange = tag.getFloat("feather");
      this.direction = tag.contains("direction") ? Direction.values()[tag.getInt("direction")] : Direction.UP;
      this.doDirtMotley = tag.getBoolean("dirt_mix");
      this.scale = tag.getFloat("dirt_scale");
      this.offset = tag.getFloat("offset");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putFloat("feather", this.edgeFeatheringRange);
      tag.putInt("direction", this.direction.ordinal());
      tag.putBoolean("dirt_mix", this.doDirtMotley);
      tag.putFloat("dirt_scale", this.scale);
      tag.putFloat("offset", this.offset);
   }

   private BlockState pickDirt(int x, int y, int z, RandomSource random) {
      float scale = this.scale * 2.5F;
      float randF = random.nextFloat();
      float noise = randF < 0.25F ? randF * 4.0F : SimplexNoise.noise(x * scale, y * scale + 1024.0F, z * scale) * 0.5F + 0.5F;
      if (noise > 0.6F) {
         return Blocks.COARSE_DIRT.defaultBlockState();
      } else {
         return noise > 0.4F ? Blocks.DIRT.defaultBlockState() : Blocks.ROOTED_DIRT.defaultBlockState();
      }
   }

   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGen,
      RandomSource random,
      BoundingBox chunkBounds,
      ChunkPos chunkPos,
      BlockPos structureCenterPos
   ) {
      BoundingBox boxIntersection = BoundingBoxUtils.getIntersectionOfSBBs(this.boundingBox, chunkBounds);
      if (boxIntersection != null && this.scale != 0.0F) {
         ChunkAccess chunk = level.getChunk(chunkPos.getWorldPosition());
         BoundingBox fenceBounds = this.generateFence() ? BoundingBoxUtils.safeRetract(this.boundingBox, this.direction.getOpposite(), 4) : this.boundingBox;

         for (int z = boxIntersection.minZ(); z <= boxIntersection.maxZ(); z++) {
            for (int x = boxIntersection.minX(); x <= boxIntersection.maxX(); x++) {
               this.processPos(level, random, x, z, chunk, fenceBounds);
            }
         }
      }
   }

   private void processPos(WorldGenLevel level, RandomSource random, int x, int z, ChunkAccess chunk, BoundingBox fenceBounds) {
      int y = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
      BlockPos placeAt = new BlockPos(x, y, z);
      if (level.getBlockState(placeAt).is(BlockTags.DIRT)) {
         int xBorderDist = Math.min(x - this.boundingBox.minX(), this.boundingBox.maxX() - x);
         int zBorderDist = Math.min(z - this.boundingBox.minZ(), this.boundingBox.maxZ() - z);
         float borderDist = Math.min(xBorderDist, zBorderDist) + this.offset;
         float featherLevel = borderDist > this.edgeFeatheringRange ? 1.0F : Mth.clamp(borderDist / this.edgeFeatheringRange, 0.0F, 1.0F);
         float noise = SimplexNoise.noise(x * this.scale, y * this.scale, z * this.scale) * 0.5F - 0.5F;
         float featheredNoise = noise + featherLevel;
         if (featheredNoise < 0.0F) {
            if (this.generateFence() && fenceBounds.intersects(x, z, x, z)) {
               float fenceNoise = SimplexNoise.noise(x * 0.15F, y * 0.15F - 1024.0F, z * 0.15F) * 0.5F;
               if (Math.abs(fenceNoise) > 0.15F) {
                  int noiseRounded = Math.round(fenceNoise + 0.5F);
                  if (this.direction.getAxis() == Axis.Z
                     ? x == this.boundingBox.minX() + noiseRounded || x == this.boundingBox.maxX() - noiseRounded
                     : z == this.boundingBox.minZ() + noiseRounded || z == this.boundingBox.maxZ() - noiseRounded) {
                     BlockPos fenceAt = placeAt.above();
                     level.setBlock(fenceAt, Blocks.SPRUCE_FENCE.defaultBlockState(), 3);
                     chunk.markPosForPostprocessing(fenceAt);
                  }
               }
            }
         } else {
            BlockState state = this.doDirtMotley ? this.pickDirt(x, y, z, random) : Blocks.DIRT_PATH.defaultBlockState();
            level.setBlock(placeAt, state, 3);
            if (this.doDirtMotley && random.nextFloat() < 0.0125F) {
               level.setBlock(placeAt.above(), Blocks.DEAD_BUSH.defaultBlockState(), 3);
            } else {
               level.setBlock(placeAt.above(), Blocks.AIR.defaultBlockState(), 3);
            }

            level.setBlock(placeAt.above(2), Blocks.AIR.defaultBlockState(), 3);
         }
      }
   }

   private boolean generateFence() {
      return !this.doDirtMotley;
   }

   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   public TerrainAdjustment getTerrainAdjustment() {
      return this.doDirtMotley ? TerrainAdjustment.BEARD_BOX : TerrainAdjustment.NONE;
   }

   public int getGroundLevelDelta() {
      return 0;
   }

   public static void beginYard(LichTowerFoyer foyerPiece, GenerationContext context, StructurePiecesBuilder pieces) {
      WorldgenRandom random = context.random();
      StructureTemplateManager structureManager = context.structureTemplateManager();
      int ySurface = foyerPiece.getBoundingBox().minY() + foyerPiece.getGroundLevelDelta();
      JigsawRecord path = foyerPiece.matchSpareJigsaws(r -> "twilightforest:lich_tower/path".equals(r.target())).getFirst();
      if (path != null) {
         int pathLength = random.nextInt(24, 32);
         Direction direction = foyerPiece.getRotation().rotate(Direction.SOUTH);
         BlockPos generatePos = foyerPiece.templatePosition().offset(path.pos());
         BlockPos fenceCenter = generatePos.relative(direction, pathLength);
         LichPerimeterFence.generateFence(foyerPiece, context, pieces, structureManager, random, direction, fenceCenter.atY(ySurface));
         BlockPos nearVestibule = generatePos.relative(direction, 1).above(4);
         BlockPos nearFence = fenceCenter.relative(direction.getOpposite(), 6).below(4);
         generateYard(foyerPiece, pieces, nearVestibule, nearFence, random, direction, context);
         Stream<BlockPos> foyerRootPos = Stream.of(
            foyerPiece.getBoundingBox().getCenter().above(10), BoundingBoxUtils.bottomCenterOf(foyerPiece.getBoundingBox()).below(10)
         );
         Stream<BlockPos> fencePostPos = pieces.pieces
            .stream()
            .filter(p -> p instanceof LichPerimeterFence)
            .flatMap(f -> ((LichPerimeterFence)f).fencePostPositions());
         Optional<BoundingBox> fullYard = BoundingBox.encapsulatingPositions(
            Streams.concat(new Stream[]{foyerRootPos, fencePostPos}).collect(Collectors.toUnmodifiableSet())
         );
         if (!fullYard.isEmpty()) {
            BoundingBox yardBox = BoundingBoxUtils.safeRetract(
               BoundingBoxUtils.setY(fullYard.get().inflatedBy(3), ySurface, ySurface + 10), foyerPiece.getSourceJigsaw().orientation().top().getOpposite(), 5
            );
            LichYardBox lichYardDirt = new LichYardBox(yardBox, 8.0F, Direction.UP, true, 0.1F, 0.0F);
            pieces.addPiece(lichYardDirt);
            lichYardDirt.addDecoration(foyerPiece, pieces, random, context);
         }
      }
   }

   private static void generateYard(
      LichTowerFoyer foyerPiece,
      StructurePiecesBuilder pieces,
      BlockPos nearVestibule,
      BlockPos nearFence,
      WorldgenRandom random,
      Direction dirFromVestibule,
      GenerationContext context
   ) {
      int ySurface = foyerPiece.getBoundingBox().minY() + foyerPiece.getGroundLevelDelta();
      List<LichYardBox> paths = new ArrayList<>();
      BoundingBox firstPathBox = BoundingBoxUtils.setY(BoundingBoxUtils.wrappedCoordinates(3, nearVestibule, nearFence).inflatedBy(1), ySurface, ySurface + 10);
      Axis axisFromVestibule = dirFromVestibule.getAxis();
      LichYardBox lichYardBox = new LichYardBox(firstPathBox, 2.5F, dirFromVestibule, false, 0.35F, -1.0F);
      pieces.addPiece(lichYardBox);
      paths.add(lichYardBox);
      BlockPos randomPos = lerpBlockPos(Mth.lerp(random.nextFloat(), 0.2F, 0.8F), nearVestibule, nearFence);
      int crossPathSpan = 24;
      BlockPos pathLeft = randomPos.relative(dirFromVestibule.getClockWise(), crossPathSpan);
      BlockPos pathRight = randomPos.relative(dirFromVestibule.getCounterClockWise(), crossPathSpan);
      BoundingBox crossPathBox = BoundingBoxUtils.setY(BoundingBoxUtils.wrappedCoordinates(1, pathLeft, pathRight), ySurface, ySurface + 10);
      LichYardBox crossPath = new LichYardBox(crossPathBox, -1.0F, dirFromVestibule.getClockWise(), false, 0.0F, 0.0F);
      pieces.addPiece(crossPath);
      paths.add(crossPath);
      paths.add(putSidePath(pieces, nearVestibule.atY(ySurface), dirFromVestibule, dirFromVestibule.getClockWise(), pathLeft, crossPathSpan));
      paths.add(putSidePath(pieces, nearVestibule.atY(ySurface), dirFromVestibule, dirFromVestibule.getCounterClockWise(), pathRight, crossPathSpan));
      BoundingBox boxLightPlace = firstPathBox.inflatedBy(axisFromVestibule == Axis.Z ? 3 : 0, 0, axisFromVestibule == Axis.X ? 3 : 0);
      LichYardLights lichYardLights = new LichYardLights(boxLightPlace, axisFromVestibule);
      pieces.addPiece(lichYardLights);
      lichYardLights.addChildren(foyerPiece, pieces, random);

      for (LichYardBox piece : paths) {
         piece.addDecoration(foyerPiece, pieces, random, context);
      }
   }

   private static LichYardBox putSidePath(
      StructurePiecesBuilder structurePiecesBuilder, BlockPos nearVestibule, Direction dirFromVestibule, Direction sideDirection, BlockPos pathEnd, int spread
   ) {
      BlockPos fromVestibule = nearVestibule.relative(sideDirection, 24);
      BoundingBox pathBox = BoundingBoxUtils.setY(
         BoundingBoxUtils.wrappedCoordinates(1, pathEnd, fromVestibule.relative(dirFromVestibule.getOpposite(), spread)),
         nearVestibule.getY(),
         nearVestibule.getY() + 10
      );
      LichYardBox path = new LichYardBox(pathBox, -1.0F, dirFromVestibule, false, 0.0F, 0.0F);
      structurePiecesBuilder.addPiece(path);
      return path;
   }

   private static BlockPos lerpBlockPos(float delta, BlockPos first, BlockPos second) {
      return new BlockPos(
         Mth.lerpDiscrete(delta, first.getX(), second.getX()),
         Mth.lerpDiscrete(delta, first.getY(), second.getY()),
         Mth.lerpDiscrete(delta, first.getZ(), second.getZ())
      );
   }

   public void addDecoration(TwilightJigsawPiece parent, StructurePieceAccessor pieces, RandomSource random, GenerationContext context) {
      this.addChildren(parent, pieces, random);
      Axis axis = this.direction.getAxis();
      if (axis != Axis.Y && this.scale == 0.0F) {
         int baseY = this.boundingBox.minY();

         for (int i = 0; i < 5; i++) {
            Direction side = Direction.fromAxisAndDirection(axis, random.nextBoolean() ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE).getClockWise();
            BlockPos randomPos = BoundingBoxUtils.lerpPosInside(this.boundingBox, axis, Mth.lerp(random.nextFloat(), 0.05F, 0.95F))
               .relative(side, random.nextIntBetweenInclusive(2, 4));
            FrontAndTop orientation = FrontAndTop.fromFrontAndTop(side, Direction.UP);
            ResourceLocation templateId = lichTowerUtil.rollGrave(random);
            JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
               randomPos.atY(baseY - 1), BlockPos.ZERO, orientation, context.structureTemplateManager(), templateId, "twilightforest:lich_tower/grave", random
            );
            if (placeableJunction != null) {
               LichYardGrave grave = new LichYardGrave(context.structureTemplateManager(), placeableJunction, templateId);
               if (pieces.findCollisionPiece(grave.getBoundingBox()) == null) {
                  pieces.addPiece(grave);
                  grave.addJigsaws(parent, pieces, context);
               }
            }
         }
      }
   }

   @Override
   public int getSortKey() {
      return this.doDirtMotley ? Integer.MIN_VALUE : -2147483393;
   }

   @Override
   public int getSpawnIndex() {
      return 0;
   }
}
