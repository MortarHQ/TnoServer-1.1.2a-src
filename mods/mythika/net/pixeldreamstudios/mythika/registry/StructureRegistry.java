package net.pixeldreamstudios.mythika.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.pixeldreamstudios.mythika.worldgen.CyclopsCavePiece;
import net.pixeldreamstudios.mythika.worldgen.CyclopsCaveStructure;
import net.pixeldreamstudios.mythika.worldgen.LabyrinthEntrancePiece;
import net.pixeldreamstudios.mythika.worldgen.LabyrinthPiece;
import net.pixeldreamstudios.mythika.worldgen.LabyrinthStructure;

public final class StructureRegistry {
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create("mythika", Registries.STRUCTURE_TYPE);
   public static final DeferredRegister<StructurePieceType> PIECE_TYPES = DeferredRegister.create("mythika", Registries.STRUCTURE_PIECE);
   public static final RegistrySupplier<StructureType<LabyrinthStructure>> LABYRINTH = STRUCTURE_TYPES.register(
      "labyrinth", () -> () -> LabyrinthStructure.CODEC
   );
   public static final RegistrySupplier<StructurePieceType> LABYRINTH_PIECE = PIECE_TYPES.register("labyrinth", () -> LabyrinthPiece::new);
   public static final RegistrySupplier<StructurePieceType> LABYRINTH_ENTRANCE = PIECE_TYPES.register("labyrinth_entrance", () -> LabyrinthEntrancePiece::new);
   public static final RegistrySupplier<StructureType<CyclopsCaveStructure>> CYCLOPS_CAVE = STRUCTURE_TYPES.register(
      "cyclops_cave", () -> () -> CyclopsCaveStructure.CODEC
   );
   public static final RegistrySupplier<StructurePieceType> CYCLOPS_CAVE_PIECE = PIECE_TYPES.register("cyclops_cave", () -> CyclopsCavePiece::new);

   private StructureRegistry() {
   }

   public static void init() {
      STRUCTURE_TYPES.register();
      PIECE_TYPES.register();
   }
}
