package com.chronodawn.blocks;

import com.chronodawn.registry.ModBlocks;
import com.chronodawn.registry.ModItems;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AttachedChronoMelonStemBlock extends AttachedStemBlock {
   public AttachedChronoMelonStemBlock(Properties properties) {
      super(ModBlocks.CHRONO_MELON.getKey(), ModBlocks.CHRONO_MELON_STEM.getKey(), ModItems.CHRONO_MELON_SEEDS.getKey(), properties);
   }
}
