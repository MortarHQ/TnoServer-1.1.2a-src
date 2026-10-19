package com.shin.tensura_kumodesu.registry.blocks;

import com.shin.tensura_kumodesu.blocks.TaratectEggBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class KumodesuBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("tensura_kumodesu", Registries.BLOCK);
   public static final RegistrySupplier<Block> TARATECT_EGG = BLOCKS.register(
      "taratect_egg",
      () -> new TaratectEggBlock(Properties.of().strength(3.0F).requiresCorrectToolForDrops().randomTicks().noOcclusion().sound(SoundType.METAL).forceSolidOn())
   );

   public static void init() {
      BLOCKS.register();
   }
}
