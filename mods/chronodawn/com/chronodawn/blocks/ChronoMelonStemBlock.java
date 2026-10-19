package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModItems;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ChronoMelonStemBlock extends StemBlock {
   public ChronoMelonStemBlock(Properties properties) {
      super(ModBlocks.CHRONO_MELON.getKey(), ModBlocks.ATTACHED_CHRONO_MELON_STEM.getKey(), ModItems.CHRONO_MELON_SEEDS.getKey(), properties);
   }
}
