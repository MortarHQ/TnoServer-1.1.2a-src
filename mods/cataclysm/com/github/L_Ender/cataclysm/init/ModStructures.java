package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.structures.Burning_Arena_Structure;
import com.github.L_Ender.cataclysm.structures.Cursed_Pyramid_Structure;
import com.github.L_Ender.cataclysm.structures.RuinedCitadelStructure;
import com.github.L_Ender.cataclysm.structures.Sunken_City_Structure;
import com.github.L_Ender.cataclysm.structures.jisaw.CataclysmJigsawStructure;
import java.util.Locale;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructures {
   public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_DEF_REG = DeferredRegister.create(Registries.STRUCTURE_PIECE, "cataclysm");
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE_DEF_REG = DeferredRegister.create(Registries.STRUCTURE_TYPE, "cataclysm");
   public static final DeferredHolder<StructureType<?>, StructureType<RuinedCitadelStructure>> RUINED_CITADEL = STRUCTURE_TYPE_DEF_REG.register(
      "ruined_citadel", () -> () -> RuinedCitadelStructure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<Burning_Arena_Structure>> BURNING_ARENA = STRUCTURE_TYPE_DEF_REG.register(
      "burning_arena", () -> () -> Burning_Arena_Structure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<Sunken_City_Structure>> SUNKEN_CITY = STRUCTURE_TYPE_DEF_REG.register(
      "sunken_city", () -> () -> Sunken_City_Structure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<Cursed_Pyramid_Structure>> CURSED_PYRAMID = STRUCTURE_TYPE_DEF_REG.register(
      "cursed_pyramid", () -> () -> Cursed_Pyramid_Structure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<CataclysmJigsawStructure>> CATACLYSM_JIGSAW = STRUCTURE_TYPE_DEF_REG.register(
      "cataclysm_jigsaw", () -> () -> CataclysmJigsawStructure.CODEC
   );
   public static final DeferredHolder<StructurePieceType, StructurePieceType> RCP = registerPieceType("ruined_citadel", RuinedCitadelStructure.Piece::new);
   public static final DeferredHolder<StructurePieceType, StructurePieceType> BAP = registerPieceType("burning_arena", Burning_Arena_Structure.Piece::new);
   public static final DeferredHolder<StructurePieceType, StructurePieceType> SCP = registerPieceType("sunken_city", Sunken_City_Structure.Piece::new);
   public static final DeferredHolder<StructurePieceType, StructurePieceType> CPD = registerPieceType("cursed_pyramid", Cursed_Pyramid_Structure.Piece::new);
   public static final ResourceKey<Structure> SOUL_BLACK_SMITH_KEY = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("cataclysm", "soul_black_smith")
   );
   public static final ResourceKey<Structure> RUINED_CITADEL_KEY = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("cataclysm", "ruined_citadel")
   );
   public static final ResourceKey<Structure> BURNING_ARENA_KEY = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("cataclysm", "burning_arena")
   );
   public static final ResourceKey<Structure> ANCIENT_FACTORY_KEY = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("cataclysm", "ancient_factory")
   );
   public static final ResourceKey<Structure> CURSED_PYRAMID_KEY = ResourceKey.create(
      Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("cataclysm", "cursed_pyramid")
   );

   private static DeferredHolder<StructurePieceType, StructurePieceType> registerPieceType(String name, StructurePieceType structurePieceType) {
      return STRUCTURE_PIECE_DEF_REG.register(name.toLowerCase(Locale.ROOT), () -> structurePieceType);
   }
}
