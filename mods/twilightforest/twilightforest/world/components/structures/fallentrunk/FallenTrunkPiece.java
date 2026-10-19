package twilightforest.world.components.structures.fallentrunk;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.world.components.structures.UtilityPiece;
import twilightforest.world.components.structures.type.FallenTrunkStructure;

public class FallenTrunkPiece extends StructurePiece {
   public static final BlockStateProvider DEFAULT_LOG = BlockStateProvider.simple((Block)TFBlocks.TWILIGHT_OAK_LOG.get());
   public static final int ERODED_LENGTH = 2;
   protected static final float MOSS_CHANCE = 0.44F;
   protected static final List<EntityType<?>> SPAWNER_MONSTERS = List.of(
      (EntityType<?>)TFEntities.SWARM_SPIDER.get(), (EntityType<?>)TFEntities.HOSTILE_WOLF.get(), EntityType.CAVE_SPIDER
   );
   public static final int TERRAFORM_PIECE_SIZE = 10;
   public static final int FEATURE_PIECE_SIZE = 3;
   protected final BlockStateProvider log;
   public final int length;
   public final int radius;
   protected final ResourceKey<LootTable> chestLootTable;
   private final long holeSeed;
   protected final Hole hole;

   public FallenTrunkPiece(
      int length, int radius, BlockStateProvider log, ResourceKey<LootTable> chestLootTable, Direction orientation, BoundingBox boundingBox, long seed
   ) {
      super((StructurePieceType)TFStructurePieceTypes.TFFallenTrunk.value(), 0, boundingBox);
      this.length = length;
      this.radius = radius;
      this.log = log;
      this.chestLootTable = chestLootTable;
      this.holeSeed = seed;
      this.hole = new Hole(this, RandomSource.create(this.holeSeed));
      this.setOrientation(orientation);
   }

   public FallenTrunkPiece(StructurePieceSerializationContext context, CompoundTag tag) {
      super((StructurePieceType)TFStructurePieceTypes.TFFallenTrunk.value(), tag);
      this.length = tag.getInt("length");
      this.radius = tag.getInt("radius");
      RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, context.registryAccess());
      this.log = BlockStateProvider.CODEC.parse(ops, tag.getCompound("log")).result().orElse(DEFAULT_LOG);
      this.chestLootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(tag.getString("chest_loot_table")));
      this.holeSeed = tag.getInt("hole_seed");
      this.hole = new Hole(this, RandomSource.create(this.holeSeed));
   }

   protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("length", this.length);
      tag.putInt("radius", this.radius);
      tag.put(
         "log",
         (Tag)BlockStateProvider.CODEC.encodeStart(NbtOps.INSTANCE, this.log).resultOrPartial(TwilightForestMod.LOGGER::error).orElseGet(CompoundTag::new)
      );
      tag.putString("chest_loot_table", this.chestLootTable.location().toString());
      tag.putLong("hole_seed", this.holeSeed);
   }

   public void addChildren(@NotNull StructurePiece parent, StructurePieceAccessor list, @NotNull RandomSource rand) {
      StructurePiece terraformingPiece = new UtilityPiece(0, this.boundingBox.inflatedBy(10));
      list.addPiece(terraformingPiece);
      StructurePiece featurePiece = new UtilityPiece(0, this.boundingBox.inflatedBy(3), false);
      list.addPiece(featurePiece);
   }

   public void postProcess(
      @NotNull WorldGenLevel level,
      @NotNull StructureManager structureManager,
      @NotNull ChunkGenerator generator,
      @NotNull RandomSource randomSource,
      @NotNull BoundingBox box,
      @NotNull ChunkPos chunkPos,
      @NotNull BlockPos pos
   ) {
      RandomSource random = RandomSource.create(pos.asLong());
      if (this.radius == FallenTrunkStructure.radiuses.get(0)) {
         this.generateSmallFallenTrunk(level, random, box, pos, random.nextBoolean());
      }

      if (this.radius == FallenTrunkStructure.radiuses.get(1)) {
         this.generateFallenTrunk(level, random, box, pos, random.nextBoolean(), false);
      }

      if (this.radius == FallenTrunkStructure.radiuses.get(2)) {
         this.generateFallenTrunk(level, random, box, pos, false, random.nextBoolean());
      }
   }

   private void generateSmallFallenTrunk(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, boolean hasHole) {
      for (int dx = 0; dx <= 3; dx++) {
         for (int dy = 0; dy <= 3; dy++) {
            if (Math.abs(dx - 1.5) + Math.abs(dy - 1.5) == 2.0) {
               this.generateTrunkRod(level, random, box, pos, dx, dy, hasHole);
            }
         }
      }
   }

   private void generateFallenTrunk(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, boolean hasHole, boolean hasSpawnerAndChests) {
      this.generateTrunk(level, random, box, pos, hasHole);
      if (hasSpawnerAndChests) {
         this.generateSpawnerAndChests(level, random, box);
      }
   }

   private void generateTrunk(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, boolean hasHole) {
      int hollow = this.radius / 2;
      int diameter = this.radius * 2;

      for (int dx = 0; dx <= diameter; dx++) {
         for (int dy = 0; dy <= diameter; dy++) {
            int dist = this.getDist(dx, dy);
            if (dist <= this.radius && dist > hollow) {
               this.generateTrunkRod(level, random, box, pos, dx, dy, hasHole);
            }
         }
      }
   }

   private void generateTrunkRod(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, int dx, int dy, boolean hasHole) {
      this.generateTrunkMainRod(level, random, box, pos, dx, dy, hasHole);
      this.generateErodedEnds(level, random, box, pos, dx, dy, hasHole);
   }

   private void generateTrunkMainRod(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, int dx, int dy, boolean hasHole) {
      for (int dz = 2; dz < this.length - 1 - 2; dz++) {
         BlockPos offsetPos = pos.offset(dx, dy, dz);
         this.placeLog(level, this.getLogState(random, offsetPos), dx, dy, dz, box, random, hasHole);
      }
   }

   private void generateErodedEnds(WorldGenLevel level, RandomSource random, BoundingBox box, BlockPos pos, int dx, int dy, boolean hasHole) {
      for (int dz = 1; dz >= 0 && !random.nextBoolean(); dz--) {
         BlockPos offsetPos = pos.offset(dx, dy, dz);
         this.placeLog(level, this.getLogState(random, offsetPos), dx, dy, dz, box, random, hasHole);
      }

      for (int dz = this.length - 1 - 2; dz < this.length - 1 && !random.nextBoolean(); dz++) {
         BlockPos offsetPos = pos.offset(dx, dy, dz);
         this.placeLog(level, this.getLogState(random, offsetPos), dx, dy, dz, box, random, hasHole);
      }
   }

   private void generateSpawnerAndChests(WorldGenLevel level, RandomSource random, BoundingBox box) {
      BlockPos spawnerPos = new BlockPos(this.radius, 2, (this.length - 1) / 2);
      this.placeBlock(level, Blocks.SPAWNER.defaultBlockState(), spawnerPos.getX(), spawnerPos.getY(), spawnerPos.getZ(), box);
      BlockPos worldSpawnerPos = this.getWorldPos(spawnerPos.getX(), spawnerPos.getY(), spawnerPos.getZ());
      RandomSource spawnerRandom = RandomSource.create(random.nextLong());
      if (box.isInside(worldSpawnerPos.getX(), worldSpawnerPos.getY(), worldSpawnerPos.getZ())
         && level.getBlockEntity(worldSpawnerPos) instanceof SpawnerBlockEntity spawner) {
         spawner.setEntityId((EntityType)Util.getRandom(SPAWNER_MONSTERS, spawnerRandom), spawnerRandom);
      }

      Direction orientation = this.getOrientation().getClockWise();
      if (this.mirror == Mirror.LEFT_RIGHT) {
         orientation = orientation.getOpposite();
      }

      Set<Vec3i> possibleChestsOffsets = new HashSet<>();

      for (int i = 0; i <= 6; i++) {
         possibleChestsOffsets.add(new Vec3i(2, 1, -3 + i));
      }

      possibleChestsOffsets.add(new Vec3i(1, 0, -3));
      possibleChestsOffsets.add(new Vec3i(1, 0, 2));

      for (Vec3i vec3i : possibleChestsOffsets.stream().toList()) {
         possibleChestsOffsets.add(new Vec3i(-vec3i.getX(), vec3i.getY(), vec3i.getZ()));
      }

      Vec3i chestOffset = (Vec3i)Util.getRandom(possibleChestsOffsets.stream().toList(), random);
      BlockPos chestSpawnerPos = spawnerPos.offset(chestOffset);
      BlockState chestState = (BlockState)((ChestBlock)TFBlocks.TWILIGHT_OAK_CHEST.get())
         .defaultBlockState()
         .setValue(ChestBlock.FACING, chestOffset.getX() < 0 ? orientation : orientation.getOpposite());
      BlockPos chestPos = this.getWorldPos(chestSpawnerPos.getX(), chestSpawnerPos.getY(), chestSpawnerPos.getZ());
      RandomSource chestRandom = RandomSource.create(random.nextLong());
      this.createChest(level, box, chestRandom, chestPos, this.chestLootTable, chestState);
   }

   private int getDist(int dx, int dy) {
      int ax = Math.abs(dx - this.radius);
      int az = Math.abs(dy - this.radius);
      return (int)(Math.max(ax, az) + Math.min(ax, az) * 0.5);
   }

   private BlockState getLogState(RandomSource random, BlockPos pos) {
      return (BlockState)this.log.getState(random, pos).trySetValue(RotatedPillarBlock.AXIS, Axis.Z);
   }

   private void placeLog(WorldGenLevel level, BlockState blockstate, int x, int y, int z, BoundingBox boundingbox, RandomSource random, boolean hasHole) {
      RandomSource randomChild = RandomSource.create(random.nextLong());
      if (!hasHole || z <= 2 || z >= this.length - 1 - 2 - 1 || !this.getAllAbsoluteHoleBlockPos().contains(this.getWorldPos(x, y, z))) {
         BlockState blockState = this.getBlock(level, x, y, z, boundingbox);
         if (blockState.is(BlockTags.REPLACEABLE_BY_TREES) || blockState.is(BlockTags.FLOWERS) || blockState.isEmpty() || randomChild.nextBoolean()) {
            this.placeBlock(level, blockstate, x, y, z, boundingbox);
            if (randomChild.nextFloat() <= 0.44F
               && boundingbox.isInside(this.getWorldPos(x, y + 1, z))
               && this.getBlock(level, x, y + 1, z, boundingbox).is(BlockTags.REPLACEABLE)) {
               this.placeBlock(level, ((Block)TFBlocks.MOSS_PATCH.get()).defaultBlockState(), x, y + 1, z, boundingbox);
               level.blockUpdated(this.getWorldPos(x, y + 1, z), (Block)TFBlocks.MOSS_PATCH.get());
               level.getChunk(this.getWorldPos(x, y + 1, z)).markPosForPostprocessing(this.getWorldPos(x, y + 1, z));
            }
         }
      }
   }

   @NotNull
   public Direction getOrientation() {
      return Objects.requireNonNull(this.orientation);
   }

   protected int getSideLength() {
      return this.radius == 1 ? 2 : this.radius * 2 - 1;
   }

   protected int convertXYtoLength(int x, int y) {
      return this.convertXYtoLength(this.getSideLength(), x, y);
   }

   protected int convertXYtoLength(int sideLength, int x, int y) {
      int length = 0;
      if (x == 0) {
         length = y;
      } else if (y == sideLength + 1) {
         length = sideLength + x;
      } else if (x == sideLength + 1) {
         length = 3 * sideLength - y + 1;
      } else if (y == 0) {
         length = 4 * sideLength - x;
      }

      return length - 1;
   }

   public int[] convertLengthToXY(int length) {
      return this.convertLengthToXY(this.getSideLength(), length);
   }

   public int[] convertLengthToXY(int sideLength, int length) {
      if (length < sideLength) {
         return new int[]{0, length + 1};
      } else if (length < 2 * sideLength) {
         return new int[]{length + 1 - sideLength, sideLength + 1};
      } else {
         return length < 3 * sideLength ? new int[]{sideLength + 1, 3 * sideLength - length} : new int[]{4 * sideLength - (length + 1), 0};
      }
   }

   private Set<BlockPos> getAllAbsoluteHoleBlockPos() {
      int zOffset = 3;
      Set<BlockPos> holeBlockPos = new HashSet<>();

      for (int xy = 0; xy < this.hole.sizeXY; xy++) {
         for (int z = 0; z < this.hole.sizeZ; z++) {
            if (this.hole.isInHole(xy, z)) {
               int[] separatedXY = this.convertLengthToXY(xy);
               holeBlockPos.add(this.getWorldPos(separatedXY[0], separatedXY[1], z + zOffset));
            }
         }
      }

      return holeBlockPos;
   }

   private Set<BlockPos> getAllForbiddenMoundBaseBlockPos() {
      Set<BlockPos> allAbsoluteHoleBlockPos = this.getAllAbsoluteHoleBlockPos();
      Set<BlockPos> forbiddenBlockPos = new HashSet<>(allAbsoluteHoleBlockPos);

      for (BlockPos blockPos : allAbsoluteHoleBlockPos) {
         for (Direction direction : Direction.values()) {
            forbiddenBlockPos.add(blockPos.relative(direction).atY(this.boundingBox.minY()));
         }
      }

      return forbiddenBlockPos;
   }

   public Set<BlockPos> getAllPotentialBaseMoundBlockPos(boolean isOnRightSide) {
      Set<BlockPos> potentialBlockPos = new HashSet<>();
      int forbiddenLength = Math.ceilDiv(this.length, 4) + 2;

      for (int offset = forbiddenLength; offset <= this.length - forbiddenLength; offset++) {
         if (isOnRightSide) {
            potentialBlockPos.add(this.getWorldPos(Math.min(this.boundingBox.getXSpan(), this.boundingBox.getZSpan()) - 1, 0, offset));
         } else {
            potentialBlockPos.add(this.getWorldPos(0, 0, offset));
         }
      }

      return potentialBlockPos;
   }

   public Set<BlockPos> getAllowedBaseMoundBlockPos(boolean isOnRightSide) {
      Set<BlockPos> allowedBlockPos = this.getAllPotentialBaseMoundBlockPos(isOnRightSide);
      allowedBlockPos.removeAll(this.getAllForbiddenMoundBaseBlockPos());
      return allowedBlockPos;
   }
}
