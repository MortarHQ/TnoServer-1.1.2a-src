package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.world.structure.CyclopsCaveStructure;
import com.iafenvoy.iceandfire.world.structure.FireDragonCaveStructure;
import com.iafenvoy.iceandfire.world.structure.FireDragonRoostStructure;
import com.iafenvoy.iceandfire.world.structure.HydraCaveStructure;
import com.iafenvoy.iceandfire.world.structure.IceDragonCaveStructure;
import com.iafenvoy.iceandfire.world.structure.IceDragonRoostStructure;
import com.iafenvoy.iceandfire.world.structure.LightningDragonCaveStructure;
import com.iafenvoy.iceandfire.world.structure.LightningDragonRoostStructure;
import com.iafenvoy.iceandfire.world.structure.PixieVillageStructure;
import com.iafenvoy.iceandfire.world.structure.SirenIslandStructure;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public final class IafStructurePieces {
   public static final DeferredRegister<StructurePieceType> REGISTRY = DeferredRegister.create("iceandfire", Registries.STRUCTURE_PIECE);
   public static final RegistrySupplier<StructurePieceType> FIRE_DRAGON_ROOST = register(
      "fire_dragon_roost", () -> FireDragonRoostStructure.FireDragonRoostPiece::new
   );
   public static final RegistrySupplier<StructurePieceType> ICE_DRAGON_ROOST = register(
      "ice_dragon_roost", () -> IceDragonRoostStructure.IceDragonRoostPiece::new
   );
   public static final RegistrySupplier<StructurePieceType> LIGHTNING_DRAGON_ROOST = register(
      "lightning_dragon_roost", () -> LightningDragonRoostStructure.LightningDragonRoostPiece::new
   );
   public static final RegistrySupplier<StructurePieceType> FIRE_DRAGON_CAVE = register(
      "fire_dragon_cave", () -> FireDragonCaveStructure.FireDragonCavePiece::new
   );
   public static final RegistrySupplier<StructurePieceType> ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> IceDragonCaveStructure.IceDragonCavePiece::new);
   public static final RegistrySupplier<StructurePieceType> LIGHTNING_DRAGON_CAVE = register(
      "lightning_dragon_cave", () -> LightningDragonCaveStructure.LightningDragonCavePiece::new
   );
   public static final RegistrySupplier<StructurePieceType> CYCLOPS_CAVE = register("cyclops_cave", () -> CyclopsCaveStructure.CyclopsCavePiece::new);
   public static final RegistrySupplier<StructurePieceType> HYDRA_CAVE = register("hydra_cave", () -> HydraCaveStructure.HydraCavePiece::new);
   public static final RegistrySupplier<StructurePieceType> SIREN_ISLAND = register("siren_island", () -> SirenIslandStructure.SirenIslandPiece::new);
   public static final RegistrySupplier<StructurePieceType> PIXIE_VILLAGE = register("pixie_village", () -> PixieVillageStructure.PixieVillagePiece::new);

   private static RegistrySupplier<StructurePieceType> register(String id, Supplier<StructurePieceType> type) {
      return REGISTRY.register(id, type);
   }
}
