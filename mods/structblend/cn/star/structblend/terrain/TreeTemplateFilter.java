package cn.star.structblend.terrain;

import cn.star.structblend.mixin.StructureTemplateAccessor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

final class TreeTemplateFilter {
   private static final Map<StructureTemplate, Boolean> CACHE = Collections.synchronizedMap(new WeakHashMap<>());

   private TreeTemplateFilter() {
   }

   static boolean isTree(StructureTemplate template) {
      return CACHE.computeIfAbsent(template, TreeTemplateFilter::classify);
   }

   private static boolean classify(StructureTemplate template) {
      List<Palette> palettes = ((StructureTemplateAccessor)template).structblend$getPalettes();
      if (palettes.isEmpty()) {
         return false;
      }

      for (Palette palette : palettes) {
         int logs = 0;
         int leaves = 0;
         int rootY = Integer.MAX_VALUE;

         for (StructureBlockInfo block : palette.blocks()) {
            if (block.state().is(BlockTags.LOGS)) {
               logs++;
               rootY = Math.min(rootY, block.pos().getY());
            }

            if (block.state().is(BlockTags.LEAVES)) {
               leaves++;
            }
         }

         if (logs == 0 || leaves < 4) {
            return false;
         }

         for (StructureBlockInfo block : palette.blocks()) {
            BlockState state = block.state();
            if (!state.is(BlockTags.LOGS)
               && !state.is(BlockTags.LEAVES)
               && !state.is(Blocks.MANGROVE_ROOTS)
               && !state.is(Blocks.MUDDY_MANGROVE_ROOTS)
               && (block.pos().getY() > rootY || !isNaturalGround(state))
               && state.blocksMotion()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isNaturalGround(BlockState state) {
      return state.is(BlockTags.DIRT)
         || state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(BlockTags.SAND)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MOSS_BLOCK)
         || state.is(Blocks.SNOW_BLOCK)
         || state.is(Blocks.SNOW);
   }
}
