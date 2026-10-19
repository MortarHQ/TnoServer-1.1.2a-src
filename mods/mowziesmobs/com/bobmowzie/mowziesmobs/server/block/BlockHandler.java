package com.bobmowzie.mowziesmobs.server.block;

import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class BlockHandler {
   public static final DeferredRegister<Block> REG = DeferredRegister.create(Registries.BLOCK, "mowziesmobs");
   public static final DeferredHolder<Block, Block> PAINTED_ACACIA = registerBlockAndItem(
      "painted_acacia", () -> new Block(Properties.ofFullCopy(Blocks.ACACIA_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD))
   );
   public static final DeferredHolder<Block, Block> PAINTED_ACACIA_SLAB = registerBlockAndItem(
      "painted_acacia_slab", () -> new SlabBlock(Properties.ofFullCopy((BlockBehaviour)PAINTED_ACACIA.get()))
   );
   public static final DeferredHolder<Block, Block> THATCH = registerBlockAndItem("thatch_block", () -> new HayBlock(Properties.ofFullCopy(Blocks.HAY_BLOCK)));
   public static final DeferredHolder<Block, Block> GONG = registerBlockAndItem(
      "gong", () -> new GongBlock(Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL))
   );
   public static final DeferredHolder<Block, Block> GONG_PART = REG.register(
      "gong_part",
      () -> new GongBlock.GongPartBlock(Properties.ofFullCopy(Blocks.GOLD_BLOCK).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.ANVIL))
   );
   public static final DeferredHolder<Block, Block> RAKED_SAND = registerBlockAndItem(
      "raked_sand", () -> new RakedSandBlock(new ColorRGBA(14406560), Properties.ofFullCopy(Blocks.SAND), Blocks.SAND.defaultBlockState())
   );
   public static final DeferredHolder<Block, Block> RED_RAKED_SAND = registerBlockAndItem(
      "red_raked_sand", () -> new RakedSandBlock(new ColorRGBA(11098145), Properties.ofFullCopy(Blocks.RED_SAND), Blocks.RED_SAND.defaultBlockState())
   );
   public static final DeferredHolder<Block, Block> CLAWED_LOG = registerBlockAndItem(
      "clawed_log", () -> new Block(Properties.ofFullCopy(Blocks.ACACIA_PLANKS))
   );

   public static DeferredHolder<Block, Block> registerBlockAndItem(String name, Supplier<Block> block) {
      DeferredHolder<Block, Block> blockObj = REG.register(name, block);
      ItemHandler.REG.register(name, () -> new BlockItem((Block)blockObj.get(), new net.minecraft.world.item.Item.Properties()));
      return blockObj;
   }

   public static void init() {
      FireBlock fireblock = (FireBlock)Blocks.FIRE;
      fireblock.setFlammable((Block)THATCH.get(), 60, 20);
      fireblock.setFlammable((Block)PAINTED_ACACIA.get(), 5, 20);
      fireblock.setFlammable((Block)PAINTED_ACACIA_SLAB.get(), 5, 20);
      fireblock.setFlammable((Block)CLAWED_LOG.get(), 5, 5);
   }
}
