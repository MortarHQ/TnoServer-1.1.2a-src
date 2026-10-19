package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.systems.multiblock.TestMultiBlock;
import com.finderfeed.fdlib.test.FDTestBlock;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks("fdlib");
   public static final Supplier<Block> TEST = BLOCKS.register("test", () -> new FDTestBlock(Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
   public static final Supplier<Block> TEST_MULTIBLOCK = BLOCKS.register(
      "test_multiblock", () -> new TestMultiBlock(Properties.ofFullCopy(Blocks.STONE).noOcclusion())
   );
}
