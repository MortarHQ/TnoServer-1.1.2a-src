package com.benji.netherman.init;

import com.benji.netherman.common.block.entity.AltarBlockEntity;
import com.benji.netherman.common.block.entity.EyeBlockEntity;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import com.benji.netherman.common.block.entity.GhastlyNestBlockEntity;
import com.benji.netherman.common.block.entity.GrandDoorBlockEntity;
import com.benji.netherman.common.block.entity.LabyrinthTeleportBlockEntity;
import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import com.benji.netherman.common.block.entity.NetherSpawnerBlockEntity;
import com.benji.netherman.common.block.entity.PaintingSpawnerBlockEntity;
import com.benji.netherman.common.block.entity.PointedBlackstoneBlockEntity;
import com.benji.netherman.common.block.entity.SamsoniteBellBlockEntity;
import com.benji.netherman.common.block.entity.TotemusBlockEntity;
import com.benji.netherman.common.block.entity.TotemusHoleBlockEntity;
import com.benji.netherman.common.block.entity.TraphiveBlockEntity;
import com.benji.netherman.common.block.entity.VoidNetherCornerBlockEntity;
import com.benji.netherman.common.block.entity.VoidNetherMidBlockEntity;
import com.benji.netherman.common.block.entity.VoidNetherMidCornerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "netherman");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PaintingSpawnerBlockEntity>> PAINTING_SPAWNER = BLOCK_ENTITIES.register(
      "painting_spawner",
      () -> Builder.of(
            PaintingSpawnerBlockEntity::new,
            new Block[]{
               (Block)ModBlocks.ANGEL_SPAWNER.get(),
               (Block)ModBlocks.FACE_SPAWNER.get(),
               (Block)ModBlocks.VILLAGE_SPAWNER.get(),
               (Block)ModBlocks.KING_SPAWNER.get(),
               (Block)ModBlocks.THRONE_SPAWNER.get()
            }
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GhastlyNestBlockEntity>> GHASTLY_NEST = BLOCK_ENTITIES.register(
      "ghastly_nest", () -> Builder.of(GhastlyNestBlockEntity::new, new Block[]{(Block)ModBlocks.GHASTLY_NEST.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidCornerBlockEntity>> VOIDMIDCORNERNETHER = BLOCK_ENTITIES.register(
      "voidnether_midcorner", () -> Builder.of(VoidNetherMidCornerBlockEntity::new, new Block[]{(Block)ModBlocks.VOIDMIDCORNERNETHER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherCornerBlockEntity>> VOIDCORNERNETHER = BLOCK_ENTITIES.register(
      "voidnether_corner", () -> Builder.of(VoidNetherCornerBlockEntity::new, new Block[]{(Block)ModBlocks.VOIDCORNERNETHER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidBlockEntity>> VOIDMIDNETHER = BLOCK_ENTITIES.register(
      "voidnether_mid", () -> Builder.of(VoidNetherMidBlockEntity::new, new Block[]{(Block)ModBlocks.VOIDMIDNETHER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EyeBlockEntity>> EYE = BLOCK_ENTITIES.register(
      "eye_block", () -> Builder.of(EyeBlockEntity::new, new Block[]{(Block)ModBlocks.EYE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LabyrinthTeleportBlockEntity>> LABYRINTH_TELEPORT = BLOCK_ENTITIES.register(
      "labyrinth_teleport", () -> Builder.of(LabyrinthTeleportBlockEntity::new, new Block[]{(Block)ModBlocks.LABYRINTH_TELEPORT.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITIES.register(
      "altar", () -> Builder.of(AltarBlockEntity::new, new Block[]{(Block)ModBlocks.ALTAR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusBlockEntity>> TOTEMUS = BLOCK_ENTITIES.register(
      "totemus", () -> Builder.of(TotemusBlockEntity::new, new Block[]{(Block)ModBlocks.TOTEMUS.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TraphiveBlockEntity>> TRAPHIVE = BLOCK_ENTITIES.register(
      "traphive", () -> Builder.of(TraphiveBlockEntity::new, new Block[]{(Block)ModBlocks.TRAPHIVE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrandDoorBlockEntity>> GRAND_DOOR = BLOCK_ENTITIES.register(
      "grand_door", () -> Builder.of(GrandDoorBlockEntity::new, new Block[]{(Block)ModBlocks.GRAND_DOOR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NetherSpawnerBlockEntity>> NETHER_SPAWNER = BLOCK_ENTITIES.register(
      "nether_spawner", () -> Builder.of(NetherSpawnerBlockEntity::new, new Block[]{(Block)ModBlocks.NETHER_SPAWNER.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PointedBlackstoneBlockEntity>> POINTED_BLACKSTONE = BLOCK_ENTITIES.register(
      "pointed_blackstone", () -> Builder.of(PointedBlackstoneBlockEntity::new, new Block[]{(Block)ModBlocks.POINTED_BLACKSTONE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_DOWN = BLOCK_ENTITIES.register(
      "face_puzzle_right_down",
      () -> Builder.of(
            (pos, state) -> new FacePuzzleBlockEntity((BlockEntityType<?>)FACE_PUZZLE_RIGHT_DOWN.get(), pos, state),
            new Block[]{(Block)ModBlocks.FACE_PUZZLE_RIGHT_DOWN.get()}
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_UP = BLOCK_ENTITIES.register(
      "face_puzzle_left_up",
      () -> Builder.of(
            (pos, state) -> new FacePuzzleBlockEntity((BlockEntityType<?>)FACE_PUZZLE_LEFT_UP.get(), pos, state),
            new Block[]{(Block)ModBlocks.FACE_PUZZLE_LEFT_UP.get()}
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_UP = BLOCK_ENTITIES.register(
      "face_puzzle_right_up",
      () -> Builder.of(
            (pos, state) -> new FacePuzzleBlockEntity((BlockEntityType<?>)FACE_PUZZLE_RIGHT_UP.get(), pos, state),
            new Block[]{(Block)ModBlocks.FACE_PUZZLE_RIGHT_UP.get()}
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_DOWN = BLOCK_ENTITIES.register(
      "face_puzzle_left_down",
      () -> Builder.of(
            (pos, state) -> new FacePuzzleBlockEntity((BlockEntityType<?>)FACE_PUZZLE_LEFT_DOWN.get(), pos, state),
            new Block[]{(Block)ModBlocks.FACE_PUZZLE_LEFT_DOWN.get()}
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MazeDoorBlockEntity>> MAZE_DOOR = BLOCK_ENTITIES.register(
      "maze_door", () -> Builder.of(MazeDoorBlockEntity::new, new Block[]{(Block)ModBlocks.MAZE_DOOR.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusHoleBlockEntity>> TOTEMUS_HOLE = BLOCK_ENTITIES.register(
      "totemus_hole", () -> Builder.of(TotemusHoleBlockEntity::new, new Block[]{(Block)ModBlocks.TOTEMUS_HOLE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SamsoniteBellBlockEntity>> SAMSONITE_BELL = BLOCK_ENTITIES.register(
      "samsonite_bell", () -> Builder.of(SamsoniteBellBlockEntity::new, new Block[]{(Block)ModBlocks.SAMSONITE_BELL.get()}).build(null)
   );

   public static void init(IEventBus bus) {
      BLOCK_ENTITIES.register(bus);
   }
}
