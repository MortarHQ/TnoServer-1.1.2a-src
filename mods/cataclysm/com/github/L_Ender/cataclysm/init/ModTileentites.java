package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfAmethyst_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfFire_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfVoid_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Cataclysm_Skull_BlockEntity;
import com.github.L_Ender.cataclysm.blockentities.Cursed_tombstone_Entity;
import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.ObsidianExplosionTrapBricks_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.SandstoneIgniteTrap_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Statue_Block_Entity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTileentites {
   public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "cataclysm");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObsidianExplosionTrapBricks_Block_Entity>> OBSIDIAN_EXPLOSION_TRAP_BRICKS = TILE_ENTITY_TYPES.register(
      "obsidian_explosion_trap_bricks",
      () -> Builder.of(ObsidianExplosionTrapBricks_Block_Entity::new, new Block[]{(Block)ModBlocks.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SandstoneIgniteTrap_Block_Entity>> SANDSTONE_IGNITE_TRAP = TILE_ENTITY_TYPES.register(
      "sadsotne_ignite_trap", () -> Builder.of(SandstoneIgniteTrap_Block_Entity::new, new Block[]{(Block)ModBlocks.SANDSTONE_IGNITE_TRAP.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarOfVoid_Block_Entity>> ALTAR_OF_VOID = TILE_ENTITY_TYPES.register(
      "altar_of_void", () -> Builder.of(AltarOfVoid_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_VOID.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarOfFire_Block_Entity>> ALTAR_OF_FIRE = TILE_ENTITY_TYPES.register(
      "altar_of_fire", () -> Builder.of(AltarOfFire_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_FIRE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarOfAmethyst_Block_Entity>> ALTAR_OF_AMETHYST = TILE_ENTITY_TYPES.register(
      "altar_of_amethyst", () -> Builder.of(AltarOfAmethyst_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_AMETHYST.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarOfAbyss_Block_Entity>> ALTAR_OF_ABYSS = TILE_ENTITY_TYPES.register(
      "altar_of_abyss", () -> Builder.of(AltarOfAbyss_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_ABYSS.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Cursed_tombstone_Entity>> CURSED_TOMBSTONE = TILE_ENTITY_TYPES.register(
      "cursed_tombstone", () -> Builder.of(Cursed_tombstone_Entity::new, new Block[]{(Block)ModBlocks.CURSED_TOMBSTONE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Abyssal_Egg_Block_Entity>> ABYSSAL_EGG = TILE_ENTITY_TYPES.register(
      "abyssal_egg", () -> Builder.of(Abyssal_Egg_Block_Entity::new, new Block[]{(Block)ModBlocks.ABYSSAL_EGG.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EMP_Block_Entity>> EMP = TILE_ENTITY_TYPES.register(
      "emp", () -> Builder.of(EMP_Block_Entity::new, new Block[]{(Block)ModBlocks.EMP.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Cataclysm_Skull_BlockEntity>> CATACLYSM_SKULL = TILE_ENTITY_TYPES.register(
      "cataclysm_skull",
      () -> Builder.of(
            Cataclysm_Skull_BlockEntity::new,
            new Block[]{
               (Block)ModBlocks.KOBOLEDIATOR_SKULL.get(),
               (Block)ModBlocks.KOBOLEDIATOR_WALL_SKULL.get(),
               (Block)ModBlocks.APTRGANGR_HEAD.get(),
               (Block)ModBlocks.APTRGANGR_WALL_HEAD.get(),
               (Block)ModBlocks.DRAUGR_HEAD.get(),
               (Block)ModBlocks.DRAUGR_WALL_HEAD.get()
            }
         )
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Mechanical_fusion_Anvil_Block_Entity>> MECHANICAL_FUSION_ANVIL = TILE_ENTITY_TYPES.register(
      "mechanical_fusion_anvil",
      () -> Builder.of(Mechanical_fusion_Anvil_Block_Entity::new, new Block[]{(Block)ModBlocks.MECHANICAL_FUSION_ANVIL.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Door_Of_Seal_BlockEntity>> DOOR_OF_SEAL = TILE_ENTITY_TYPES.register(
      "door_of_seal", () -> Builder.of(Door_Of_Seal_BlockEntity::new, new Block[]{(Block)ModBlocks.DOOR_OF_SEAL.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Statue_Block_Entity>> GODDESS_STATUE = TILE_ENTITY_TYPES.register(
      "goddess_statue", () -> Builder.of(Statue_Block_Entity::new, new Block[]{(Block)ModBlocks.GODDESS_STATUE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<Boss_Respawn_Spawner_Block_Entity>> BOSS_RESPAWNER = TILE_ENTITY_TYPES.register(
      "boss_respawner", () -> Builder.of(Boss_Respawn_Spawner_Block_Entity::new, new Block[]{(Block)ModBlocks.BOSS_RESPAWNER.get()}).build(null)
   );
}
