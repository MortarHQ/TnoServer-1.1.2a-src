package com.cerbon.bosses_of_mass_destruction.structure.void_blossom_cavern;

import com.cerbon.bosses_of_mass_destruction.structure.BMDStructures;
import com.cerbon.bosses_of_mass_destruction.structure.util.CodeStructurePiece;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

public class VoidBlossomArenaStructureFeature extends Structure {
   public static final MapCodec<VoidBlossomArenaStructureFeature> CODEC = simpleCodec(VoidBlossomArenaStructureFeature::new);

   protected VoidBlossomArenaStructureFeature(StructureSettings settings) {
      super(settings);
   }

   @NotNull
   protected Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
      return onTopOfChunkCenter(context, Types.WORLD_SURFACE_WG, collector -> addPieces(collector, context));
   }

   @NotNull
   public StructureType<?> type() {
      return (StructureType<?>)BMDStructures.VOID_BLOSSOM_STRUCTURE_TYPE.get();
   }

   public static void addPieces(StructurePiecesBuilder collector, GenerationContext context) {
      int x = context.chunkPos().getMinBlockX();
      int z = context.chunkPos().getMinBlockZ();
      int y = 35 + context.chunkGenerator().getMinY();
      collector.addPiece(
         new CodeStructurePiece(
            (StructurePieceType)BMDStructures.VOID_BLOSSOM_CAVERN_PIECE.get(),
            new BoundingBox(new BlockPos(x, y, z)).inflatedBy(32),
            new VoidBlossomCavernPieceGenerator()
         )
      );
   }
}
