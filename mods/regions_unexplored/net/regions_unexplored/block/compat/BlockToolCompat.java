package net.regions_unexplored.block.compat;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.regions_unexplored.block.set.WoodSet;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.util.BlockCompatUtil;

public class BlockToolCompat {
   public static void setup() {
      for (WoodSet set : RUBlocks.WOOD_SETS) {
         Block log = set.getLog();
         Block strippedLog = set.getStrippedLog();
         if (log != null && strippedLog != null) {
            BlockCompatUtil.registerStrippableBlock(log, strippedLog);
         }

         Block wood = set.getWood();
         Block strippedWood = set.getStrippedWood();
         if (wood != null && strippedWood != null) {
            BlockCompatUtil.registerStrippableBlock(wood, strippedWood);
         }
      }

      BlockCompatUtil.registerStrippableBlock(RUBlocks.ASHEN_WOOD_SET.getLog(), RUBlocks.DEAD_WOOD_SET.getStrippedLog());
      BlockCompatUtil.registerStrippableBlock(RUBlocks.ASHEN_WOOD_SET.getWood(), RUBlocks.DEAD_WOOD_SET.getStrippedWood());
      BlockCompatUtil.registerStrippableBlock(RUBlocks.SILVER_BIRCH_WOOD_SET.getLog(), Blocks.STRIPPED_BIRCH_LOG);
      BlockCompatUtil.registerStrippableBlock(RUBlocks.SILVER_BIRCH_WOOD_SET.getWood(), Blocks.STRIPPED_BIRCH_WOOD);
      BlockCompatUtil.registerStrippableBlock(RUBlocks.BRIMWOOD_WOOD_SET.getLogMagma(), RUBlocks.BRIMWOOD_WOOD_SET.getStrippedLog());
      BlockCompatUtil.registerShovelled(RUBlocks.PEAT_PODZOL.get(), RUBlocks.PEAT_DIRT_PATH.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.PEAT_DIRT.get(), RUBlocks.PEAT_DIRT_PATH.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.SILT_PODZOL.get(), RUBlocks.SILT_DIRT_PATH.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.SILT_DIRT.get(), RUBlocks.SILT_DIRT_PATH.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.CHALK_GRASS_BLOCK.get(), RUBlocks.CHALK.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.CHALK_GRASS_BLOCK.get(), RUBlocks.CHALK.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.CHALK_GRASS_BLOCK.get(), RUBlocks.CHALK.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.ARGILLITE_GRASS_BLOCK.get(), RUBlocks.ARGILLITE.get().defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.STONE_GRASS_BLOCK.get(), Blocks.STONE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.VIRIDESCENT_NYLIUM.get(), Blocks.STONE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.PRISMOSS.get(), Blocks.STONE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.MOSSY_STONE.get(), Blocks.STONE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.DEEPSLATE_GRASS_BLOCK.get(), Blocks.DEEPSLATE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.DEEPSLATE_VIRIDESCENT_NYLIUM.get(), Blocks.DEEPSLATE.defaultBlockState());
      BlockCompatUtil.registerShovelled(RUBlocks.DEEPSLATE_PRISMOSS.get(), Blocks.DEEPSLATE.defaultBlockState());
   }
}
