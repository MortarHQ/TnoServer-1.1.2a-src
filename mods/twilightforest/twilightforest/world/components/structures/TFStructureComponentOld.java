package twilightforest.world.components.structures;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.selectors.StrongholdStonesRandomBlockSelectorFactory;

@Deprecated
public abstract class TFStructureComponentOld extends TFStructureComponent implements PieceBeardifierModifier {
   protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
   @Autowired
   private static StrongholdStonesRandomBlockSelectorFactory strongholdStones;

   public TFStructureComponentOld(StructurePieceType piece, CompoundTag nbt) {
      super(piece, nbt);
   }

   @Deprecated
   public TFStructureComponentOld(StructurePieceType type, int i, int x, int y, int z) {
      super(type, i, new BoundingBox(x, y, z, x, y, z));
   }

   public TFStructureComponentOld(StructurePieceType type, int i, BoundingBox box) {
      super(type, i, box);
   }

   public void setOrientation(@Nullable Direction facing) {
      this.orientation = facing;
      this.mirror = Mirror.NONE;
      if (facing == null) {
         this.rotation = Rotation.NONE;
      } else {
         switch (facing) {
            case SOUTH:
               this.rotation = Rotation.CLOCKWISE_180;
               break;
            case WEST:
               this.rotation = Rotation.COUNTERCLOCKWISE_90;
               break;
            case EAST:
               this.rotation = Rotation.CLOCKWISE_90;
               break;
            default:
               this.rotation = Rotation.NONE;
         }
      }
   }

   public static BoundingBox getComponentToAddBoundingBox2(int x, int y, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Direction dir) {
      return switch (dir) {
         case WEST -> new BoundingBox(x - maxZ - minZ, y + minY, z + minX, x - minZ, y + maxY + minY, z + maxX + minX);
         case EAST -> new BoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z - minX);
         case NORTH -> new BoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY, z - minZ);
         default -> new BoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);
      };
   }

   protected void setSpawner(WorldGenLevel world, Vec3i pos, BoundingBox sbb, EntityType<?> monsterID) {
      this.setSpawner(world, pos.getX(), pos.getY(), pos.getZ(), sbb, monsterID, v -> {});
   }

   protected void setSpawner(WorldGenLevel world, int x, int y, int z, BoundingBox sbb, EntityType<?> monsterID) {
      this.setSpawner(world, x, y, z, sbb, monsterID, v -> {});
   }

   protected void setSpawner(WorldGenLevel world, int x, int y, int z, BoundingBox sbb, EntityType<?> monsterID, Consumer<SpawnerBlockEntity> spawnerModifier) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      BlockPos pos = new BlockPos(dx, dy, dz);
      setSpawnerInWorld(world, sbb, monsterID, spawnerModifier, pos);
   }

   protected static void setSpawnerInWorld(
      WorldGenLevel world, BoundingBox sbb, EntityType<?> monsterID, Consumer<SpawnerBlockEntity> spawnerModifier, BlockPos pos
   ) {
      if (sbb.isInside(pos)) {
         if (world.getBlockState(pos).getBlock() != Blocks.SPAWNER) {
            world.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
         }

         if (world.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner) {
            spawner.setEntityId(monsterID, world.getRandom());
            spawnerModifier.accept(spawner);
         }
      }
   }

   protected void surroundBlockCardinal(WorldGenLevel world, BlockState block, int x, int y, int z, BoundingBox sbb) {
      this.placeBlock(world, block, x, y, z - 1, sbb);
      this.placeBlock(world, block, x, y, z + 1, sbb);
      this.placeBlock(world, block, x - 1, y, z, sbb);
      this.placeBlock(world, block, x + 1, y, z, sbb);
   }

   protected void surroundBlockCardinalRotated(WorldGenLevel world, BlockState block, int x, int y, int z, BoundingBox sbb) {
      this.placeBlock(world, (BlockState)block.setValue(StairBlock.FACING, Direction.NORTH), x, y, z - 1, sbb);
      this.placeBlock(world, (BlockState)block.setValue(StairBlock.FACING, Direction.SOUTH), x, y, z + 1, sbb);
      this.placeBlock(world, (BlockState)block.setValue(StairBlock.FACING, Direction.WEST), x - 1, y, z, sbb);
      this.placeBlock(world, (BlockState)block.setValue(StairBlock.FACING, Direction.EAST), x + 1, y, z, sbb);
   }

   protected void surroundBlockCorners(WorldGenLevel world, BlockState block, int x, int y, int z, BoundingBox sbb) {
      this.placeBlock(world, block, x - 1, y, z - 1, sbb);
      this.placeBlock(world, block, x - 1, y, z + 1, sbb);
      this.placeBlock(world, block, x + 1, y, z - 1, sbb);
      this.placeBlock(world, block, x + 1, y, z + 1, sbb);
   }

   protected void setSpawnerRotated(WorldGenLevel world, int x, int y, int z, Rotation rotation, EntityType<?> monsterID, BoundingBox sbb) {
      Direction oldBase = this.fakeBaseMode(rotation);
      this.setSpawner(world, x, y, z, sbb, monsterID);
      this.setOrientation(oldBase);
   }

   protected void placeTreasureAtCurrentPosition(WorldGenLevel world, int x, int y, int z, ResourceKey<LootTable> treasureType, BoundingBox sbb) {
      this.placeTreasureAtCurrentPosition(world, x, y, z, treasureType, false, sbb);
   }

   protected void placeTreasureAtCurrentPosition(
      WorldGenLevel world, int x, int y, int z, ResourceKey<LootTable> treasureType, boolean trapped, BoundingBox sbb
   ) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      this.placeTreasureAtWorldPosition(world, treasureType, trapped, sbb, new BlockPos(dx, dy, dz));
   }

   protected void placeTreasureAtWorldPosition(WorldGenLevel world, ResourceKey<LootTable> treasureType, boolean trapped, BoundingBox sbb, BlockPos pos) {
      if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != (trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST)) {
         TFLootTables.generateChest(world, pos, this.getOrientation(), trapped, treasureType);
      }
   }

   protected void placeTreasureRotated(
      WorldGenLevel world, int x, int y, int z, Direction facing, Rotation rotation, ResourceKey<LootTable> treasureType, BoundingBox sbb
   ) {
      this.placeTreasureRotated(world, x, y, z, facing, rotation, treasureType, false, sbb);
   }

   protected void placeTreasureRotated(
      WorldGenLevel world, int x, int y, int z, Direction facing, Rotation rotation, ResourceKey<LootTable> treasureType, boolean trapped, BoundingBox sbb
   ) {
      if (facing == null) {
         TwilightForestMod.LOGGER.error("Loot Chest at {}, {}, {} has null direction, setting it to north", x, y, z);
         facing = Direction.NORTH;
      }

      int dx = this.getXWithOffsetRotated(x, z, rotation);
      int dy = this.getWorldY(y);
      int dz = this.getZWithOffsetRotated(x, z, rotation);
      BlockPos pos = new BlockPos(dx, dy, dz);
      if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != (trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST)) {
         TFLootTables.generateChest(world, pos, facing, trapped, treasureType);
      }
   }

   protected void manualTreaurePlacement(
      WorldGenLevel world, int x, int y, int z, Direction facing, ResourceKey<LootTable> treasureType, boolean trapped, BoundingBox sbb
   ) {
      int lootx = this.getWorldX(x, z);
      int looty = this.getWorldY(y);
      int lootz = this.getWorldZ(x, z);
      BlockPos lootPos = new BlockPos(lootx, looty, lootz);
      this.placeBlock(
         world,
         (BlockState)((BlockState)(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST).defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT))
            .setValue(ChestBlock.FACING, facing),
         x,
         y,
         z,
         sbb
      );
      TFLootTables.generateChestContents(world, lootPos, treasureType);
   }

   protected void setDoubleLootChest(
      WorldGenLevel world,
      int x,
      int y,
      int z,
      int otherx,
      int othery,
      int otherz,
      @Nullable Direction facing,
      ResourceKey<LootTable> treasureType,
      BoundingBox sbb,
      boolean trapped
   ) {
      this.setDoubleLootChest(world, x, y, z, otherx, othery, otherz, facing, treasureType, treasureType, sbb, trapped);
   }

   protected void setDoubleLootChest(
      WorldGenLevel world,
      int x,
      int y,
      int z,
      int otherx,
      int othery,
      int otherz,
      @Nullable Direction facing,
      ResourceKey<LootTable> treasureType,
      ResourceKey<LootTable> secondaryLootType,
      BoundingBox sbb,
      boolean trapped
   ) {
      if (facing == null) {
         TwilightForestMod.LOGGER.error("Loot Chest at {}, {}, {} has null direction, setting it to north", x, y, z);
         facing = Direction.NORTH;
      }

      boolean flipContents = world.getRandom().nextBoolean();
      BlockPos firstChestPos = new BlockPos(this.getWorldX(x, z), this.getWorldY(y), this.getWorldZ(x, z));
      BlockPos secondChestPos = new BlockPos(this.getWorldX(otherx, otherz), this.getWorldY(othery), this.getWorldZ(otherx, otherz));
      this.placeBlock(
         world,
         (BlockState)((BlockState)(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST).defaultBlockState().setValue(ChestBlock.TYPE, ChestType.LEFT))
            .setValue(ChestBlock.FACING, facing),
         x,
         y,
         z,
         sbb
      );
      this.placeBlock(
         world,
         (BlockState)((BlockState)(trapped ? Blocks.TRAPPED_CHEST : Blocks.CHEST).defaultBlockState().setValue(ChestBlock.TYPE, ChestType.RIGHT))
            .setValue(ChestBlock.FACING, facing),
         otherx,
         othery,
         otherz,
         sbb
      );
      TFLootTables.generateChestContents(world, flipContents ? secondChestPos : firstChestPos, treasureType);
      TFLootTables.generateChestContents(world, flipContents ? firstChestPos : secondChestPos, secondaryLootType);
   }

   protected void placeTripwire(WorldGenLevel world, int x, int y, int z, int size, Direction facing, BoundingBox sbb) {
      int dx = facing.getStepX();
      int dz = facing.getStepZ();
      BlockState tripwireHook = Blocks.TRIPWIRE_HOOK.defaultBlockState();
      this.placeBlock(world, (BlockState)tripwireHook.setValue(TripWireHookBlock.FACING, facing.getOpposite()), x, y, z, sbb);
      this.placeBlock(world, (BlockState)tripwireHook.setValue(TripWireHookBlock.FACING, facing), x + dx * size, y, z + dz * size, sbb);
      BlockState tripwire = Blocks.TRIPWIRE.defaultBlockState();

      for (int i = 1; i < size; i++) {
         this.placeBlock(world, tripwire, x + dx * i, y, z + dz * i, sbb);
      }
   }

   protected void placeSignAtCurrentPosition(WorldGenLevel world, int x, int y, int z, String string0, String string1, BoundingBox sbb) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      BlockPos pos = new BlockPos(dx, dy, dz);
      if (sbb.isInside(pos) && world.getBlockState(pos).getBlock() != Blocks.OAK_SIGN) {
         world.setBlock(
            pos, (BlockState)Blocks.OAK_SIGN.defaultBlockState().setValue(StandingSignBlock.ROTATION, this.getOrientation().get2DDataValue() * 4), 2
         );
         if (world.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            sign.frontText = sign.frontText.setMessage(1, Component.literal(string0)).setMessage(2, Component.literal(string1));
         }
      }
   }

   protected int[] offsetTowerCoords(int x, int y, int z, int towerSize, Direction direction) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      if (direction == Direction.SOUTH) {
         return new int[]{dx + 1, dy - 1, dz - towerSize / 2};
      } else if (direction == Direction.WEST) {
         return new int[]{dx + towerSize / 2, dy - 1, dz + 1};
      } else if (direction == Direction.NORTH) {
         return new int[]{dx - 1, dy - 1, dz + towerSize / 2};
      } else {
         return direction == Direction.EAST ? new int[]{dx - towerSize / 2, dy - 1, dz - 1} : new int[]{x, y, z};
      }
   }

   protected BlockPos offsetTowerCCoords(int x, int y, int z, int towerSize, Direction direction) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      switch (direction) {
         case SOUTH:
            return new BlockPos(dx + 1, dy - 1, dz - towerSize / 2);
         case WEST:
            return new BlockPos(dx + towerSize / 2, dy - 1, dz + 1);
         case EAST:
            return new BlockPos(dx - towerSize / 2, dy - 1, dz - 1);
         case NORTH:
            return new BlockPos(dx - 1, dy - 1, dz + towerSize / 2);
         default:
            return new BlockPos(x, y, z);
      }
   }

   protected int getWorldX(int x, int z) {
      Direction enumfacing = this.getOrientation();
      if (enumfacing == null) {
         return x;
      }

      return switch (enumfacing) {
         case SOUTH -> this.boundingBox.minX() + x;
         case WEST -> this.boundingBox.maxX() - z;
         case EAST -> this.boundingBox.minX() + z;
         case NORTH -> this.boundingBox.maxX() - x;
         default -> x;
      };
   }

   protected int getWorldZ(int x, int z) {
      Direction enumfacing = this.getOrientation();
      if (enumfacing == null) {
         return z;
      }

      return switch (enumfacing) {
         case SOUTH -> this.boundingBox.minZ() + z;
         case WEST -> this.boundingBox.minZ() + x;
         case EAST -> this.boundingBox.maxZ() - x;
         case NORTH -> this.boundingBox.maxZ() - z;
         default -> z;
      };
   }

   private Direction fakeBaseMode(Rotation rotationsCW) {
      Direction oldBaseMode = this.getOrientation();
      if (oldBaseMode != null) {
         Direction pretendBaseMode = oldBaseMode;
         pretendBaseMode = rotationsCW.rotate(pretendBaseMode);
         this.setOrientation(pretendBaseMode);
      }

      return oldBaseMode;
   }

   protected int getXWithOffsetRotated(int x, int z, Rotation rotationsCW) {
      Direction oldMode = this.fakeBaseMode(rotationsCW);
      int ret = this.getWorldX(x, z);
      this.setOrientation(oldMode);
      return ret;
   }

   protected int getZWithOffsetRotated(int x, int z, Rotation rotationsCW) {
      Direction oldMode = this.fakeBaseMode(rotationsCW);
      int ret = this.getWorldZ(x, z);
      this.setOrientation(oldMode);
      return ret;
   }

   protected void setBlockStateRotated(WorldGenLevel world, BlockState state, int x, int y, int z, Rotation rotationsCW, BoundingBox sbb) {
      Direction oldMode = this.fakeBaseMode(rotationsCW);
      this.placeBlock(world, state, x, y, z, sbb);
      this.setOrientation(oldMode);
   }

   protected BlockState getBlock(BlockGetter world, int x, int y, int z, BoundingBox sbb) {
      return super.getBlock(world, x, y, z, sbb);
   }

   @Override
   protected void placeBlock(WorldGenLevel worldIn, BlockState blockstateIn, int x, int y, int z, BoundingBox sbb) {
      super.placeBlock(worldIn, blockstateIn, x, y, z, sbb);
   }

   public BlockState getBlockStateFromPosRotated(WorldGenLevel world, int x, int y, int z, BoundingBox sbb, Rotation rotationsCW) {
      Direction oldMode = this.fakeBaseMode(rotationsCW);
      BlockState ret = this.getBlock(world, x, y, z, sbb);
      this.setOrientation(oldMode);
      return ret;
   }

   protected void fillBlocksRotated(
      WorldGenLevel world, BoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, BlockState state, Rotation rotation
   ) {
      Direction oldBase = this.fakeBaseMode(rotation);
      this.generateBox(world, sbb, minX, minY, minZ, maxX, maxY, maxZ, state, state, false);
      this.setOrientation(oldBase);
   }

   protected void randomlyFillBlocksRotated(
      WorldGenLevel worldIn,
      BoundingBox boundingboxIn,
      RandomSource rand,
      float chance,
      int minX,
      int minY,
      int minZ,
      int maxX,
      int maxY,
      int maxZ,
      BlockState blockstate1,
      BlockState blockstate2,
      Rotation rotation
   ) {
      Direction oldBase = this.fakeBaseMode(rotation);
      boolean minimumLightLevel = true;
      this.generateMaybeBox(worldIn, boundingboxIn, rand, chance, minX, minY, minZ, maxX, maxY, maxZ, blockstate1, blockstate2, false, true);
      this.setOrientation(oldBase);
   }

   public void replaceAirAndLiquidDownwardsRotated(WorldGenLevel world, BlockState state, int x, int y, int z, Rotation rotation, BoundingBox sbb) {
      Direction oldBaseMode = this.fakeBaseMode(rotation);
      this.fillColumnDown(world, state, x, y, z, sbb);
      this.setOrientation(oldBaseMode);
   }

   protected void fillAirRotated(WorldGenLevel world, BoundingBox sbb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Rotation rotation) {
      Direction oldBaseMode = this.fakeBaseMode(rotation);
      this.generateAirBox(world, sbb, minX, minY, minZ, maxX, maxY, maxZ);
      this.setOrientation(oldBaseMode);
   }

   protected void fillWithAir(
      WorldGenLevel world, BoundingBox boundingBox, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Predicate<BlockState> predicate
   ) {
      this.fillWithBlocks(world, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, Blocks.AIR.defaultBlockState(), predicate);
   }

   protected void fillWithBlocks(
      WorldGenLevel world,
      BoundingBox boundingBox,
      int xMin,
      int yMin,
      int zMin,
      int xMax,
      int yMax,
      int zMax,
      BlockState state,
      Predicate<BlockState> predicate
   ) {
      this.fillWithBlocks(world, boundingBox, xMin, yMin, zMin, xMax, yMax, zMax, state, state, predicate);
   }

   protected void fillWithBlocks(
      WorldGenLevel world,
      BoundingBox boundingBox,
      int xMin,
      int yMin,
      int zMin,
      int xMax,
      int yMax,
      int zMax,
      BlockState borderState,
      BlockState interiorState,
      Predicate<BlockState> predicate
   ) {
      for (int y = yMin; y <= yMax; y++) {
         for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
               if (predicate.test(this.getBlock(world, x, y, z, boundingBox))) {
                  boolean isBorder = yMin != yMax && (y == yMin || y == yMax)
                     || xMin != xMax && (x == xMin || x == xMax)
                     || zMin != zMax && (z == zMin || z == zMax);
                  this.placeBlock(world, isBorder ? borderState : interiorState, x, y, z, boundingBox);
               }
            }
         }
      }
   }

   protected static BlockSelector getStrongholdStones() {
      return strongholdStones.make();
   }

   protected Direction getStructureRelativeRotation(Rotation rotationsCW) {
      return rotationsCW.rotate(this.getOrientation());
   }

   protected int getAverageGroundLevel(WorldGenLevel world, ChunkGenerator generator, BoundingBox boundingBox) {
      int yTotal = 0;
      int count = 0;
      int yStart = Mth.clamp(generator.getSeaLevel(), this.boundingBox.minY(), this.boundingBox.maxY());

      for (int z = this.boundingBox.minZ(); z <= this.boundingBox.maxZ(); z++) {
         for (int x = this.boundingBox.minX(); x <= this.boundingBox.maxX(); x++) {
            BlockPos pos = new BlockPos(x, yStart, z);
            if (boundingBox.isInside(pos)) {
               BlockPos topPos = world.getHeightmapPos(Types.WORLD_SURFACE_WG, pos);
               yTotal += Math.max(topPos.getY(), generator.getSpawnHeight(world));
               count++;
            }
         }
      }

      return count == 0 ? Integer.MIN_VALUE : yTotal / count;
   }

   protected int findGroundLevel(WorldGenLevel world, BoundingBox sbb, int start, Predicate<BlockState> predicate) {
      Vec3i center = BoundingBoxUtils.getCenter(sbb);
      MutableBlockPos pos = new MutableBlockPos(center.getX(), 0, center.getZ());

      for (int y = start; y > 0; y--) {
         pos.setY(y);
         if (predicate.test(world.getBlockState(pos))) {
            return y;
         }
      }

      return 0;
   }

   protected boolean isBoundingBoxOutsideBiomes(WorldGenLevel world, Predicate<Biome> predicate, BlockPos structurePos) {
      int minX = this.boundingBox.minX() - 1;
      int minZ = this.boundingBox.minZ() - 1;
      int maxX = this.boundingBox.maxX() + 1;
      int maxZ = this.boundingBox.maxZ() + 1;

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            if (!predicate.test(
               (Biome)world.getUncachedNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(structurePos.getY()), QuartPos.fromBlock(z)).value()
            )) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   public static StructurePiece findIntersectingExcluding(List<StructurePiece> list, BoundingBox toCheck, StructurePiece exclude) {
      for (StructurePiece structurecomponent : list) {
         if (structurecomponent != exclude && structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersects(toCheck)) {
            return structurecomponent;
         }
      }

      return null;
   }

   public BlockPos getBlockPosWithOffset(int x, int y, int z) {
      return new BlockPos(this.getWorldX(x, z), this.getWorldY(y), this.getWorldZ(x, z));
   }

   protected static BlockState getStairState(BlockState stairState, Direction direction, boolean isTopHalf) {
      return (BlockState)((BlockState)stairState.setValue(StairBlock.FACING, direction)).setValue(StairBlock.HALF, isTopHalf ? Half.TOP : Half.BOTTOM);
   }

   protected static BlockState getSlabState(BlockState inputBlockState, SlabType half) {
      return (BlockState)inputBlockState.setValue(SlabBlock.TYPE, half);
   }

   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.NONE;
   }

   public int getGroundLevelDelta() {
      return 0;
   }
}
