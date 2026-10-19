package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.entity.AzureMossQilinEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.WorldgenRegistry;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class QilinBambooGrovePiece extends StructurePiece {
   private static final int RADIUS = 14;
   private static final int ARENA_RADIUS = 8;
   private final BlockPos origin;
   private final long layoutSeed;
   private boolean bossSpawned;

   public QilinBambooGrovePiece(BlockPos origin, long layoutSeed) {
      super(
         (StructurePieceType)WorldgenRegistry.QILIN_BAMBOO_GROVE_PIECE.get(),
         0,
         new BoundingBox(origin.getX() - 22, origin.getY() - 9, origin.getZ() - 22, origin.getX() + 22, origin.getY() + 48, origin.getZ() + 22)
      );
      this.origin = origin.immutable();
      this.layoutSeed = layoutSeed;
   }

   public QilinBambooGrovePiece(CompoundTag tag) {
      super((StructurePieceType)WorldgenRegistry.QILIN_BAMBOO_GROVE_PIECE.get(), tag);
      this.origin = new BlockPos(tag.getInt("OriginX"), tag.getInt("OriginY"), tag.getInt("OriginZ"));
      this.layoutSeed = tag.getLong("LayoutSeed");
      this.bossSpawned = tag.getBoolean("BossSpawned");
   }

   protected synchronized void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putInt("OriginX", this.origin.getX());
      tag.putInt("OriginY", this.origin.getY());
      tag.putInt("OriginZ", this.origin.getZ());
      tag.putLong("LayoutSeed", this.layoutSeed);
      tag.putBoolean("BossSpawned", this.bossSpawned);
   }

   public BlockPos getLocatorPosition() {
      return this.origin;
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structures, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot
   ) {
      for (int x = Math.max(chunkBox.minX(), this.origin.getX() - 14); x <= Math.min(chunkBox.maxX(), this.origin.getX() + 14); x++) {
         for (int z = Math.max(chunkBox.minZ(), this.origin.getZ() - 14); z <= Math.min(chunkBox.maxZ(), this.origin.getZ() + 14); z++) {
            int dx = x - this.origin.getX();
            int dz = z - this.origin.getZ();
            if (dx * dx + dz * dz <= 196) {
               this.prepareColumn(level, chunkBox, x, z, dx, dz);
            }
         }
      }

      if (chunkPos.equals(new ChunkPos(this.origin)) && chunkBox.isInside(this.origin)) {
         this.spawnBoss(level);
      }
   }

   private void prepareColumn(WorldGenLevel level, BoundingBox box, int x, int z, int dx, int dz) {
      MutableBlockPos pos = new MutableBlockPos(x, this.origin.getY() + 8, z);
      int oldGround = Integer.MIN_VALUE;

      for (int y = this.origin.getY() + 8; y >= this.origin.getY() - 8; y--) {
         BlockState state = level.getBlockState(pos.setY(y));
         if (soil(state)) {
            oldGround = y;
            break;
         }

         if (!vegetation(state)) {
            return;
         }
      }

      if (oldGround != Integer.MIN_VALUE) {
         int distance = Mth.ceil(Math.sqrt(dx * dx + dz * dz));
         int slope = Math.max(0, distance - 8);
         int ground = this.origin.getY() - 1 + Mth.clamp(oldGround - this.origin.getY() + 1, -slope, slope);
         int bottom = Math.min(oldGround, ground);
         int top = Math.min(this.origin.getY() + 48, level.getMaxBuildHeight() - 1);

         for (int y = bottom; y <= top; y++) {
            BlockState state = level.getBlockState(pos.setY(y));
            if (!soil(state) && !vegetation(state)) {
               return;
            }
         }

         RandomSource columnRandom = RandomSource.create(SpiritSkyIslandLayout.mix(this.layoutSeed ^ x * 73428767L ^ z * 19349663L));

         for (int y = bottom; y < ground; y++) {
            place(level, box, pos.setY(y), Blocks.DIRT.defaultBlockState());
         }

         BlockState surface = columnRandom.nextInt(5) == 0
            ? Blocks.MOSS_BLOCK.defaultBlockState()
            : (distance > 8 && columnRandom.nextInt(4) == 0 ? Blocks.PODZOL.defaultBlockState() : Blocks.GRASS_BLOCK.defaultBlockState());
         place(level, box, pos.setY(ground), surface);

         for (int y = ground + 1; y <= top; y++) {
            if (!level.isEmptyBlock(pos.setY(y))) {
               place(level, box, pos, Blocks.AIR.defaultBlockState());
            }
         }

         boolean approach = Math.abs((this.layoutSeed & 1L) == 0L ? dx : dz) <= 1;
         if (distance >= 10 && !approach && columnRandom.nextFloat() < 0.58F) {
            int height = 7 + columnRandom.nextInt(7);

            for (int y = 0; y < height; y++) {
               BambooLeaves leaves = y == height - 1 ? BambooLeaves.LARGE : (y >= height - 3 ? BambooLeaves.SMALL : BambooLeaves.NONE);
               place(
                  level,
                  box,
                  pos.setY(ground + 1 + y),
                  (BlockState)((BlockState)((BlockState)Blocks.BAMBOO.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1))
                        .setValue(BlockStateProperties.BAMBOO_LEAVES, leaves))
                     .setValue(BlockStateProperties.STAGE, y == height - 1 ? 1 : 0)
               );
            }
         }
      }
   }

   private synchronized void spawnBoss(WorldGenLevel level) {
      if (!this.bossSpawned) {
         UUID id = new UUID(SpiritSkyIslandLayout.mix(this.layoutSeed ^ this.origin.asLong()), SpiritSkyIslandLayout.mix(~this.layoutSeed));
         if (!(level instanceof WorldGenRegion) && level.getLevel().getEntity(id) != null) {
            this.bossSpawned = true;
         } else if (soil(level.getBlockState(this.origin.below()))) {
            AzureMossQilinEntity qilin = (AzureMossQilinEntity)((EntityType)CultivationEntities.AZURE_MOSS_QILIN.get()).create(level.getLevel());
            if (qilin != null) {
               qilin.setUUID(id);
               qilin.moveTo(this.origin.getX() + 0.5, this.origin.getY(), this.origin.getZ() + 0.5, (float)Math.floorMod(this.layoutSeed, 360L), 0.0F);
               qilin.setPersistenceRequired();
               qilin.restrictTo(this.origin, 22);
               qilin.finalizeSpawn(level, level.getCurrentDifficultyAt(this.origin), MobSpawnType.STRUCTURE, null);
               if (qilin.isSpawnCancelled() || level.addFreshEntity(qilin)) {
                  this.bossSpawned = true;
               }
            }
         }
      }
   }

   private static boolean soil(BlockState state) {
      return !state.hasBlockEntity()
         && state.getFluidState().isEmpty()
         && (state.is(BlockTags.DIRT) || state.is(Blocks.MOSS_BLOCK) || state.is(BlockTags.BASE_STONE_OVERWORLD));
   }

   private static boolean vegetation(BlockState state) {
      return !state.hasBlockEntity()
         && state.getFluidState().isEmpty()
         && (
            state.isAir()
               || state.is(BlockTags.REPLACEABLE_BY_TREES)
               || state.is(BlockTags.LOGS)
               || state.is(BlockTags.LEAVES)
               || state.getBlock() instanceof BushBlock
               || state.is(Blocks.BAMBOO)
               || state.is(Blocks.BAMBOO_SAPLING)
         );
   }

   private static void place(WorldGenLevel level, BoundingBox box, BlockPos pos, BlockState state) {
      if (box.isInside(pos) && !level.isOutsideBuildHeight(pos)) {
         level.setBlock(pos, state, 2);
      }
   }
}
