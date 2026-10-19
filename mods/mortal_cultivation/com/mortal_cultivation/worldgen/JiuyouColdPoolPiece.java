package com.mortal_cultivation.worldgen;

import com.mortal_cultivation.block.NineYinLotusBlock;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class JiuyouColdPoolPiece extends StructurePiece {
   private final long seed;

   public JiuyouColdPoolPiece(BlockPos center, long seed) {
      super(
         (StructurePieceType)WorldgenRegistry.JIUYOU_COLD_POOL_PIECE.get(),
         0,
         new BoundingBox(center.getX() - 27, center.getY() + -13, center.getZ() - 27, center.getX() + 27, center.getY() + 16, center.getZ() + 27)
      );
      this.seed = seed;
   }

   public JiuyouColdPoolPiece(CompoundTag tag) {
      super((StructurePieceType)WorldgenRegistry.JIUYOU_COLD_POOL_PIECE.get(), tag);
      this.seed = tag.getLong("LayoutSeed");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putLong("LayoutSeed", this.seed);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structures, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot
   ) {
      int centerX = this.boundingBox.minX() + 27;
      int centerY = this.boundingBox.minY() - -13;
      int centerZ = this.boundingBox.minZ() + 27;
      JiuyouColdPoolLayout layout = new JiuyouColdPoolLayout(this.seed);
      this.paintBiome(level, chunkPos, layout, centerX, centerY, centerZ);
      MutableBlockPos cursor = new MutableBlockPos();
      int minX = Math.max(chunkBox.minX(), this.boundingBox.minX());
      int maxX = Math.min(chunkBox.maxX(), this.boundingBox.maxX());
      int minZ = Math.max(chunkBox.minZ(), this.boundingBox.minZ());
      int maxZ = Math.min(chunkBox.maxZ(), this.boundingBox.maxZ());

      for (int x = minX; x <= maxX; x++) {
         for (int z = minZ; z <= maxZ; z++) {
            int dx = x - centerX;
            int dz = z - centerZ;
            JiuyouColdPoolLayout.Column column = layout.column(dx, dz);
            if (column != null) {
               for (int y = column.floor() - 2; y <= column.ceiling() + 2; y++) {
                  BlockState state = switch (JiuyouColdPoolLayout.cell(column, y)) {
                     case ROCK -> rock(layout.sample(50 + y / 3, dx / 2, dz / 2));
                     case WATER -> Blocks.WATER.defaultBlockState();
                     case AIR -> Blocks.CAVE_AIR.defaultBlockState();
                     case UNCHANGED -> null;
                  };
                  if (state != null) {
                     place(level, chunkBox, cursor.set(x, centerY + y, z), state);
                  }
               }

               if (!(column.radius() >= 0.97)) {
                  if (layout.sample(70L, dx, dz) < 0.055) {
                     int length = 1 + (int)(layout.sample(71L, dx, dz) * 4.0);
                     dripstone(
                        level,
                        chunkBox,
                        cursor,
                        x,
                        centerY + column.ceiling(),
                        z,
                        Direction.DOWN,
                        Math.min(length, column.ceiling() - Math.max(column.floor(), 0) - 2)
                     );
                  }

                  if (column.floor() >= 0 && layout.sample(80L, dx, dz) < 0.065) {
                     int length = 1 + (int)(layout.sample(81L, dx, dz) * 3.0);
                     dripstone(level, chunkBox, cursor, x, centerY + column.floor(), z, Direction.UP, Math.min(length, column.ceiling() - column.floor() - 3));
                  } else if (column.floor() >= 0 && layout.sample(90L, dx / 2, dz / 2) < 0.075) {
                     place(level, chunkBox, cursor.set(x, centerY + column.floor() + 1, z), rock(layout.sample(91L, dx, dz)));
                  }

                  if (JiuyouColdPoolLayout.cell(column, 0) == JiuyouColdPoolLayout.Cell.WATER
                     && JiuyouColdPoolLayout.cell(column, 1) == JiuyouColdPoolLayout.Cell.AIR
                     && layout.sample(100L, dx, dz) < 0.025) {
                     BlockPos lotusPos = cursor.set(x, centerY + 0 + 1, z);
                     BlockState lotus = ((NineYinLotusBlock)BlockRegistry.NINE_YIN_LOTUS.get()).defaultBlockState();
                     if (chunkBox.isInside(lotusPos)
                        && level.isEmptyBlock(lotusPos)
                        && level.getBlockState(lotusPos.below()).is(Blocks.WATER)
                        && level.getFluidState(lotusPos.below()).isSource()
                        && lotus.canSurvive(level, lotusPos)) {
                        place(level, chunkBox, lotusPos, lotus);
                     }
                  }
               }
            }
         }
      }
   }

   private void paintBiome(WorldGenLevel level, ChunkPos pos, JiuyouColdPoolLayout layout, int x, int y, int z) {
      Holder<Biome> biome = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(WorldgenRegistry.JIUYOU_COLD_POOL_BIOME);
      ChunkAccess chunk = level.getChunk(pos.x, pos.z);
      int firstSection = Math.floorDiv(this.boundingBox.minY(), 16);
      int lastSection = Math.floorDiv(this.boundingBox.maxY(), 16);

      for (int sectionY = firstSection; sectionY <= lastSection; sectionY++) {
         LevelChunkSection section = chunk.getSection(chunk.getSectionIndexFromSectionY(sectionY));
         section.fillBiomesFromNoise(
            (qx, qy, qz, sampler) -> layout.biomeContains(QuartPos.toBlock(qx) + 2 - x, QuartPos.toBlock(qy) + 2 - y, QuartPos.toBlock(qz) + 2 - z)
               ? biome
               : section.getNoiseBiome(qx & 3, qy & 3, qz & 3),
            level.getLevel().getChunkSource().randomState().sampler(),
            QuartPos.fromBlock(pos.getMinBlockX()),
            QuartPos.fromSection(sectionY),
            QuartPos.fromBlock(pos.getMinBlockZ())
         );
      }

      chunk.setUnsaved(true);
   }

   private static BlockState rock(double sample) {
      return (sample < 0.58 ? Blocks.BLACKSTONE : (sample < 0.86 ? Blocks.DEEPSLATE : Blocks.BASALT)).defaultBlockState();
   }

   private static void dripstone(WorldGenLevel level, BoundingBox chunkBox, MutableBlockPos cursor, int x, int y, int z, Direction direction, int length) {
      if (length >= 1) {
         place(level, chunkBox, cursor.set(x, y, z), Blocks.DRIPSTONE_BLOCK.defaultBlockState());

         for (int i = 1; i <= length; i++) {
            DripstoneThickness thickness = i == length
               ? DripstoneThickness.TIP
               : (i == length - 1 ? DripstoneThickness.FRUSTUM : (i == 1 ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE));
            BlockState state = (BlockState)((BlockState)Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, direction))
               .setValue(PointedDripstoneBlock.THICKNESS, thickness);
            place(level, chunkBox, cursor.set(x, y + direction.getStepY() * i, z), state);
         }
      }
   }

   private static void place(WorldGenLevel level, BoundingBox chunkBox, BlockPos pos, BlockState state) {
      if (chunkBox.isInside(pos) && !level.getBlockState(pos).is(Blocks.BEDROCK) && !level.getBlockState(pos).hasBlockEntity()) {
         level.setBlock(pos, state, 2);
      }
   }
}
