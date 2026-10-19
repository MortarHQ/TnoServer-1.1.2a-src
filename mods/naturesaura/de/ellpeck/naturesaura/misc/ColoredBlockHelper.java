package de.ellpeck.naturesaura.misc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class ColoredBlockHelper {
   public static final List<Block> WOOL = collectBlocks("wool");
   public static final List<Block> TERRACOTTA = collectBlocks("terracotta");
   public static final List<Block> CONCRETE_POWDER = collectBlocks("concrete_powder");
   public static final List<Block> CONCRETE = collectBlocks("concrete");
   public static final List<Block> GLASS = collectBlocks("stained_glass");
   public static final List<Block> GLASS_PANE = collectBlocks("stained_glass_pane");
   public static final List<Block> CARPET = collectBlocks("carpet");
   public static final List<List<Block>> LISTS = Arrays.asList(WOOL, TERRACOTTA, CONCRETE_POWDER, CONCRETE, GLASS, GLASS_PANE, CARPET);

   private static List<Block> collectBlocks(String name) {
      return Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(c -> {
         ResourceLocation loc = ResourceLocation.parse(c.getName() + "_" + name);
         Block block = (Block)BuiltInRegistries.BLOCK.get(loc);
         if (block != null && block != Blocks.AIR) {
            return block;
         } else {
            throw new IllegalStateException("Couldn't find block with name " + loc);
         }
      }).toList();
   }

   public static List<Block> getBlocksContaining(Block block) {
      for (List<Block> list : LISTS) {
         if (list.contains(block)) {
            return list;
         }
      }

      return null;
   }
}
