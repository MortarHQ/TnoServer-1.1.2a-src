package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;

public class CloudComponent extends StructurePiece {
   private static final int OFFSET = -32;
   private static final int WIDTH = 96;
   private static final int DEPTH = 8;

   public CloudComponent(int genDepth, int centerChunkX, int yHeight, int centerChunkZ) {
      super((StructurePieceType)TFStructurePieceTypes.TFCloud.value(), genDepth, makeBoundingBox(centerChunkX + -32 & -16, yHeight, centerChunkZ + -32 & -16));
   }

   @NotNull
   private static BoundingBox makeBoundingBox(int minX, int maxY, int minZ) {
      return new BoundingBox(minX & -16, maxY - 8, minZ & -16, minX + 96 | 15, maxY, minZ + 96 | 15);
   }

   public CloudComponent(StructurePieceSerializationContext ctx, CompoundTag tag) {
      super((StructurePieceType)TFStructurePieceTypes.TFCloud.value(), tag);
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
   }

   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator generator,
      RandomSource randomSource,
      BoundingBox chunkBox,
      ChunkPos chunkPos,
      BlockPos structureBottomCenter
   ) {
      BlockPos chunkWorldPos = chunkPos.getWorldPosition();
      int genCenterX = this.boundingBox.minX() - -32 - chunkWorldPos.getX() + 16;
      int genCenterZ = this.boundingBox.minZ() - -32 - chunkWorldPos.getZ() + 16;
      generateCloud(level.getChunk(chunkPos.x, chunkPos.z), genCenterX, genCenterZ, this.boundingBox.maxY(), randomSource);
   }

   private static void generateCloud(ChunkAccess chunkAccess, int hx, int hz, int cloudHeight, RandomSource random) {
      boolean isCenter = hx == 0 && hz == 0;
      ChunkPos center = chunkAccess.getPos();
      BlockPos chunkBlockPos = center.getWorldPosition();
      BlockState wispyCloud = ((Block)TFBlocks.WISPY_CLOUD.get()).defaultBlockState();
      BlockState fluffyCloud = ((Block)TFBlocks.FLUFFY_CLOUD.get()).defaultBlockState();
      int regionX = center.x + 8 >> 4;
      int regionZ = center.z + 8 >> 4;
      long seed = regionX * 3129871L ^ regionZ * 116129781L;
      seed = seed * seed * 42317861L + seed * 7L;
      int num0 = 5 * (int)(seed >> 12 & 3L) - 4 * (int)(seed >> 15 & 3L);
      int num1 = 4 * (int)(seed >> 18 & 3L) - 5 * (int)(seed >> 21 & 3L);
      int num2 = 5 * (int)(seed >> 9 & 3L) - 4 * (int)(seed >> 6 & 3L);
      int num3 = 4 * (int)(seed >> 3 & 3L) - 5 * (int)(seed & 3L);

      for (int bx = 0; bx < 4; bx++) {
         int dx = bx * 4 - hx - 2;
         int dx2 = dx + num0;
         int dx3 = dx + num2;

         for (int bz = 0; bz < 4; bz++) {
            int dz = bz * 4 - hz - 2;
            int dz2 = dz + num1;
            int dz3 = dz + num3;
            float dist0 = Mth.sqrt(dx * dx + dz * dz) / 4.0F;
            float dist2 = Mth.sqrt(dx2 * dx2 + dz2 * dz2) / 3.5F;
            float dist3 = Mth.sqrt(dx3 * dx3 + dz3 * dz3) / 4.5F;
            double dist = Math.min(dist0, Math.min(dist2, dist3));
            float pr = random.nextFloat();
            double cv = dist - 7.0 - pr * 3.0F;
            int y = cloudHeight;
            int depth = 4;
            if (!isCenter && pr < 0.1F) {
               y++;
            }

            if (pr > 0.6F) {
               depth++;
            }

            if (pr > 0.9F) {
               depth++;
            }

            gen4x4Cloud(chunkAccess, bx, bz, chunkBlockPos, dist, cv, y, wispyCloud, depth, fluffyCloud, isCenter);
         }
      }
   }

   private static void gen4x4Cloud(
      ChunkAccess chunkAccess,
      int bx,
      int bz,
      BlockPos chunkBlockPos,
      double dist,
      double cv,
      int y,
      BlockState wispyCloud,
      int depth,
      BlockState fluffyCloud,
      boolean isCenterChunk
   ) {
      BlockState topBlock = isCenterChunk ? fluffyCloud : wispyCloud;

      for (int sx = 0; sx < 4; sx++) {
         int lx = bx * 4 + sx;

         for (int sz = 0; sz < 4; sz++) {
            int lz = bz * 4 + sz;
            BlockPos columnPos = chunkBlockPos.offset(lx, 0, lz);
            if (!(dist < 7.0) && !(cv < 0.05F)) {
               if (dist < 8.0 || cv < 1.0) {
                  for (int d = 1; d < depth; d++) {
                     setIfAir(chunkAccess, columnPos.atY(y - d), wispyCloud);
                  }
               }
            } else {
               setIfAir(chunkAccess, columnPos.atY(y), topBlock);

               for (int d = 1; d < depth; d++) {
                  setIfAir(chunkAccess, columnPos.atY(y - d), fluffyCloud);
               }

               setIfAir(chunkAccess, columnPos.atY(y - depth), wispyCloud);
            }
         }
      }
   }

   private static void setIfAir(ChunkAccess chunkAccess, BlockPos topPos, BlockState blockState) {
      if (chunkAccess.getBlockState(topPos).isAir()) {
         chunkAccess.setBlockState(topPos, blockState, false);
      }
   }
}
