package twilightforest.world.components.structures.finalcastle;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerWingComponent;

public class FinalCastleDungeonRoom31Component extends TowerWingComponent {
   public int level;

   public FinalCastleDungeonRoom31Component(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      this((StructurePieceType)TFStructurePieceTypes.TFFCDunR31.get(), nbt);
   }

   public FinalCastleDungeonRoom31Component(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
   }

   public FinalCastleDungeonRoom31Component(StructurePieceType piece, int i, int x, int y, int z, Direction direction, int level) {
      super(piece, i, x, y, z);
      this.setOrientation(direction);
      this.spawnListIndex = 2;
      this.size = 31;
      this.height = 7;
      this.level = level;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(
         x, y, z, -15, 0, -15, this.size - 1, this.height - 1, this.size - 1, Direction.SOUTH, false
      );
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      int mySpread = this.getGenDepth() - parent.getGenDepth();
      int maxSpread = this.level == 1 ? 2 : 3;
      if (mySpread == maxSpread && !this.isExitBuildForLevel(parent)) {
         Rotation direction = RotationUtil.getRandomRotation(rand);

         for (int i = 0; i < 8 && !this.isExitBuildForLevel(parent); i++) {
            direction = direction.getRotated(RotationUtil.ROTATIONS[i & 3]);
            if (this.addDungeonExit(list, rand, direction)) {
               this.setExitBuiltForLevel(parent, true);
            }
         }
      }

      if (mySpread < maxSpread) {
         Rotation direction = RotationUtil.getRandomRotation(rand);

         for (int i = 0; i < 12; i++) {
            direction = direction.getRotated(RotationUtil.ROTATIONS[i & 3]);
            this.addDungeonRoom(parent, list, rand, direction, this.level);
         }
      }
   }

   private boolean isExitBuildForLevel(StructurePiece parent) {
      return parent instanceof FinalCastleDungeonEntranceComponent ? ((FinalCastleDungeonEntranceComponent)parent).hasExit : false;
   }

   private void setExitBuiltForLevel(StructurePiece parent, boolean exit) {
      if (parent instanceof FinalCastleDungeonEntranceComponent) {
         ((FinalCastleDungeonEntranceComponent)parent).hasExit = exit;
      }
   }

   protected boolean addDungeonRoom(StructurePiece parent, StructurePieceAccessor list, RandomSource rand, Rotation rotation, int level) {
      rotation = rotation.getRotated(this.rotation);
      BlockPos rc = this.getNewRoomCoords(rand, rotation);
      FinalCastleDungeonRoom31Component dRoom = new FinalCastleDungeonRoom31Component(
         (StructurePieceType)TFStructurePieceTypes.TFFCDunR31.get(),
         this.genDepth + 1,
         rc.getX(),
         rc.getY(),
         rc.getZ(),
         rotation.rotate(Direction.SOUTH),
         level
      );
      BoundingBox largerBB = BoundingBoxUtils.clone(dRoom.getBoundingBox());
      if (list instanceof StructurePiecesBuilder start) {
         StructurePiece intersect = TFStructureComponentOld.findIntersectingExcluding(start.pieces, largerBB, this);
         if (intersect == null) {
            list.addPiece(dRoom);
            dRoom.addChildren(parent, list, rand);
            return true;
         }
      }

      return false;
   }

   protected boolean addDungeonExit(StructurePieceAccessor list, RandomSource rand, Rotation rotation) {
      rotation = rotation.getRotated(this.rotation);
      BlockPos rc = this.getNewRoomCoords(rand, rotation);
      FinalCastleDungeonExitComponent dRoom = new FinalCastleDungeonExitComponent(
         this.genDepth + 1, rc.getX(), rc.getY(), rc.getZ(), rotation.rotate(Direction.SOUTH), this.level
      );
      if (list instanceof StructurePiecesBuilder start) {
         StructurePiece intersect = TFStructureComponentOld.findIntersectingExcluding(start.pieces, dRoom.getBoundingBox(), this);
         if (intersect == null) {
            list.addPiece(dRoom);
            dRoom.addChildren(this, list, rand);
            return true;
         }
      }

      return false;
   }

   private BlockPos getNewRoomCoords(RandomSource rand, Rotation rotation) {
      int offset = rand.nextInt(15) - 9;
      if (rand.nextBoolean()) {
         offset += this.size;
      }
      return switch (rotation) {
         case CLOCKWISE_90 -> new BlockPos(this.boundingBox.minX() + offset, this.boundingBox.minY(), this.boundingBox.maxZ() + 9);
         case CLOCKWISE_180 -> new BlockPos(this.boundingBox.minX() - 9, this.boundingBox.minY(), this.boundingBox.minZ() + offset);
         case COUNTERCLOCKWISE_90 -> new BlockPos(this.boundingBox.minX() + offset, this.boundingBox.minY(), this.boundingBox.minZ() - 9);
         default -> new BlockPos(this.boundingBox.maxX() + 9, this.boundingBox.minY(), this.boundingBox.minZ() + offset);
      };
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      Predicate<Biome> plateauBiomes = biome -> biome == world.registryAccess().registryOrThrow(Registries.BIOME).get(TFBiomes.THORNLANDS)
         || biome == world.registryAccess().registryOrThrow(Registries.BIOME).get(TFBiomes.FINAL_PLATEAU);
      if (!this.isBoundingBoxOutsideBiomes(world, plateauBiomes, blockPos)) {
         RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
         Predicate<BlockState> replacing = state -> state.isAir()
            || state.is(TFBlocks.DEADROCK)
            || state.is(TFBlocks.CRACKED_DEADROCK)
            || state.is(TFBlocks.WEATHERED_DEADROCK);
         this.fillWithAir(world, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, replacing);
         BlockState floor = ((Block)TFBlocks.CASTLE_BRICK.get()).defaultBlockState();
         BlockState border = ((Block)TFBlocks.THICK_CASTLE_BRICK.get()).defaultBlockState();
         int cs = 7;
         this.fillWithBlocks(world, sbb, 7, -1, 7, this.size - 1 - 7, -1, this.size - 1 - 7, border, floor, replacing);
         this.fillWithBlocks(world, sbb, 7, this.height, 7, this.size - 1 - 7, this.height, this.size - 1 - 7, border, floor, replacing);
         BlockState forceField = this.getForceFieldColor(decoRNG);
         BlockState castleMagic = this.getRuneColor(forceField);

         for (Rotation rotation : RotationUtil.ROTATIONS) {
            this.fillBlocksRotated(world, sbb, 7, 0, 8, 7, this.height - 1, this.size - 2 - 7, forceField, rotation);

            for (int z = 7; z < this.size - 1 - 7; z += 4) {
               this.fillBlocksRotated(world, sbb, 7, 0, z, 7, this.height - 1, z, castleMagic, rotation);
               int y = (z - 7) % 8 == 0 ? decoRNG.nextInt(3) : decoRNG.nextInt(3) + 4;
               this.fillBlocksRotated(world, sbb, 7, y, z + 1, 7, y, z + 3, castleMagic, rotation);
            }
         }
      }
   }

   protected BlockState getRuneColor(BlockState forceFieldColor) {
      return forceFieldColor.is(TFBlocks.GREEN_FORCE_FIELD)
         ? ((Block)TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get()).defaultBlockState()
         : ((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get()).defaultBlockState();
   }

   protected BlockState getForceFieldColor(RandomSource decoRNG) {
      int i = decoRNG.nextInt(2) + 3;
      return i == 3 ? ((Block)TFBlocks.GREEN_FORCE_FIELD.get()).defaultBlockState() : ((Block)TFBlocks.BLUE_FORCE_FIELD.get()).defaultBlockState();
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.BURY;
   }

   @Override
   public int getGroundLevelDelta() {
      return 0;
   }
}
