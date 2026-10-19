package com.progwml6.ironchest.common.block.entity;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.regular.entity.CopperChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.CrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DirtChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.GoldChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.IronChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.ObsidianChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCopperChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDirtChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedGoldChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedObsidianChestBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IronChestsBlockEntityTypes {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "ironchest");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IronChestBlockEntity>> IRON_CHEST = BLOCK_ENTITIES.register(
      "iron_chest", () -> typeOf(IronChestBlockEntity::new, (Block)IronChestsBlocks.IRON_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GoldChestBlockEntity>> GOLD_CHEST = BLOCK_ENTITIES.register(
      "gold_chest", () -> typeOf(GoldChestBlockEntity::new, (Block)IronChestsBlocks.GOLD_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DiamondChestBlockEntity>> DIAMOND_CHEST = BLOCK_ENTITIES.register(
      "diamond_chest", () -> typeOf(DiamondChestBlockEntity::new, (Block)IronChestsBlocks.DIAMOND_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperChestBlockEntity>> COPPER_CHEST = BLOCK_ENTITIES.register(
      "copper_chest", () -> typeOf(CopperChestBlockEntity::new, (Block)IronChestsBlocks.COPPER_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalChestBlockEntity>> CRYSTAL_CHEST = BLOCK_ENTITIES.register(
      "crystal_chest", () -> typeOf(CrystalChestBlockEntity::new, (Block)IronChestsBlocks.CRYSTAL_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObsidianChestBlockEntity>> OBSIDIAN_CHEST = BLOCK_ENTITIES.register(
      "obsidian_chest", () -> typeOf(ObsidianChestBlockEntity::new, (Block)IronChestsBlocks.OBSIDIAN_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DirtChestBlockEntity>> DIRT_CHEST = BLOCK_ENTITIES.register(
      "dirt_chest", () -> typeOf(DirtChestBlockEntity::new, (Block)IronChestsBlocks.DIRT_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedIronChestBlockEntity>> TRAPPED_IRON_CHEST = BLOCK_ENTITIES.register(
      "trapped_iron_chest", () -> typeOf(TrappedIronChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_IRON_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedGoldChestBlockEntity>> TRAPPED_GOLD_CHEST = BLOCK_ENTITIES.register(
      "trapped_gold_chest", () -> typeOf(TrappedGoldChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_GOLD_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedDiamondChestBlockEntity>> TRAPPED_DIAMOND_CHEST = BLOCK_ENTITIES.register(
      "trapped_diamond_chest", () -> typeOf(TrappedDiamondChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedCopperChestBlockEntity>> TRAPPED_COPPER_CHEST = BLOCK_ENTITIES.register(
      "trapped_copper_chest", () -> typeOf(TrappedCopperChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_COPPER_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedCrystalChestBlockEntity>> TRAPPED_CRYSTAL_CHEST = BLOCK_ENTITIES.register(
      "trapped_crystal_chest", () -> typeOf(TrappedCrystalChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedObsidianChestBlockEntity>> TRAPPED_OBSIDIAN_CHEST = BLOCK_ENTITIES.register(
      "trapped_obsidian_chest", () -> typeOf(TrappedObsidianChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get())
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TrappedDirtChestBlockEntity>> TRAPPED_DIRT_CHEST = BLOCK_ENTITIES.register(
      "trapped_dirt_chest", () -> typeOf(TrappedDirtChestBlockEntity::new, (Block)IronChestsBlocks.TRAPPED_DIRT_CHEST.get())
   );

   private static <T extends BlockEntity> BlockEntityType<T> typeOf(BlockEntitySupplier<T> entity, Block... blocks) {
      return Builder.of(entity, blocks).build(null);
   }
}
