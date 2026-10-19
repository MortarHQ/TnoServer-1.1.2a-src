package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFStructurePieceTypes;

public class UtilityPiece extends StructurePiece {
   public final boolean allowFeatures;

   public UtilityPiece(int genDepth, BoundingBox boundingBox) {
      this(genDepth, boundingBox, true);
   }

   public UtilityPiece(int genDepth, BoundingBox boundingBox, boolean allowFeatures) {
      super((StructurePieceType)TFStructurePieceTypes.TFUtilityPiece.value(), genDepth, boundingBox);
      this.allowFeatures = allowFeatures;
   }

   public UtilityPiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.TFUtilityPiece.value(), compoundTag);
      this.allowFeatures = compoundTag.getBoolean("allow_features");
   }

   protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
      tag.putBoolean("allow_features", this.allowFeatures);
   }

   public void postProcess(
      WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos
   ) {
   }
}
