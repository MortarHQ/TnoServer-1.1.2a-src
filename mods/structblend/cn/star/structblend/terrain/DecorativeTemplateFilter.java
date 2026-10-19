package cn.star.structblend.terrain;

import cn.star.structblend.mixin.PoolPieceAccessor;
import cn.star.structblend.mixin.SinglePoolElementInvoker;
import cn.star.structblend.mixin.StructureTemplateAccessor;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class DecorativeTemplateFilter {
   private static final Map<StructureTemplate, Boolean> CACHE = Collections.synchronizedMap(new WeakHashMap<>());
   private static final Map<StructureStart, Boolean> START_CACHE = Collections.synchronizedMap(new WeakHashMap<>());

   private DecorativeTemplateFilter() {
   }

   public static boolean isDecoration(StructureTemplate template) {
      return CACHE.computeIfAbsent(template, DecorativeTemplateFilter::classify);
   }

   public static boolean isDecoration(StructurePiece piece) {
      if (piece instanceof PoolElementStructurePiece pool && pool.getElement().getProjection() == Projection.RIGID) {
         StructurePoolElement element = pool.getElement();
         if (element.getClass() != SinglePoolElement.class && element.getClass() != LegacySinglePoolElement.class) {
            return false;
         }

         SinglePoolElementInvoker accessor = (SinglePoolElementInvoker)element;
         if (!((StructureProcessorList)accessor.structblend$getProcessors().value()).list().isEmpty()) {
            return false;
         }

         StructureTemplateManager manager = ((PoolPieceAccessor)pool).structblend$getTemplateManager();
         return isDecoration(accessor.structblend$getTemplate(manager));
      } else {
         return false;
      }
   }

   public static boolean isDecoration(StructureStart start) {
      if (start.isValid() && !start.getPieces().isEmpty()) {
         Boolean cached = START_CACHE.get(start);
         if (cached != null) {
            return cached;
         }

         boolean decoration = true;

         for (StructurePiece piece : start.getPieces()) {
            if (!isDecoration(piece)) {
               decoration = false;
               break;
            }
         }

         START_CACHE.put(start, decoration);
         return decoration;
      } else {
         return false;
      }
   }

   private static boolean classify(StructureTemplate template) {
      StructureTemplateAccessor accessor = (StructureTemplateAccessor)template;
      List<Palette> palettes = accessor.structblend$getPalettes();
      if (!palettes.isEmpty() && accessor.structblend$getEntities().isEmpty()) {
         for (Palette palette : palettes) {
            for (StructureBlockInfo info : palette.blocks()) {
               if (info.nbt() != null || info.state().hasBlockEntity()) {
                  return false;
               }
            }
         }

         if (!TreeTemplateFilter.isTree(template)) {
            Vec3i size = template.getSize();
            if (size.getX() >= 1 && size.getZ() >= 1 && size.getY() >= 1 && size.getX() <= 16 && size.getZ() <= 16 && size.getY() <= 32) {
               for (Palette palette : palettes) {
                  if (!naturalDecoration(palette.blocks(), size.getX(), size.getY(), size.getZ())) {
                     return false;
                  }
               }

               return true;
            } else {
               return false;
            }
         } else {
            for (Palette palette : palettes) {
               for (StructureBlockInfo info : palette.blocks()) {
                  BlockState state = info.state();
                  if (!state.isAir()
                     && !naturalGround(state)
                     && !plant(state)
                     && !state.is(BlockTags.LOGS)
                     && !state.is(Blocks.MANGROVE_ROOTS)
                     && !state.is(Blocks.MUDDY_MANGROVE_ROOTS)) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean naturalDecoration(List<StructureBlockInfo> blocks, int width, int height, int depth) {
      int plants = 0;
      int ground = 0;
      int minPlantY = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      int maxY = Integer.MIN_VALUE;
      int minX = width;
      int maxX = -1;
      int minZ = depth;
      int maxZ = -1;

      for (StructureBlockInfo info : blocks) {
         BlockState state = info.state();
         if (!state.isAir()) {
            int x = info.pos().getX();
            int y = info.pos().getY();
            int z = info.pos().getZ();
            if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) {
               return false;
            }

            if (plant(state)) {
               plants++;
               minPlantY = Math.min(minPlantY, y);
            } else {
               if (!naturalGround(state)) {
                  return false;
               }

               ground++;
               minY = Math.min(minY, y);
               maxY = Math.max(maxY, y);
               minX = Math.min(minX, x);
               maxX = Math.max(maxX, x);
               minZ = Math.min(minZ, z);
               maxZ = Math.max(maxZ, z);
            }
         }
      }

      if (plants > 0 && (ground == 0 || maxY - minY <= 1 && maxY <= minPlantY)) {
         return true;
      }

      if (ground != 0 && width <= 8 && depth <= 8 && height <= 4 && ground <= 64 && maxY - minY >= 1) {
         int[] columns = new int[width * depth];
         int[] layers = new int[maxY - minY + 1];

         for (StructureBlockInfo info : blocks) {
            if (naturalGround(info.state())) {
               int y = info.pos().getY() - minY;
               int index = info.pos().getZ() * width + info.pos().getX();
               if ((columns[index] & 1 << y) != 0) {
                  return false;
               }

               columns[index] |= 1 << y;
               layers[y]++;
            }
         }

         for (int bits : columns) {
            if ((bits & bits + 1) != 0) {
               return false;
            }
         }

         if (layers[0] == (maxX - minX + 1) * (maxZ - minZ + 1)) {
            return false;
         }

         for (int y = 1; y < layers.length; y++) {
            if (layers[y] >= layers[y - 1]) {
               return false;
            }
         }

         for (int z = 0; z < depth; z++) {
            if (!solidSpan(columns, z * width, 1, width)) {
               return false;
            }
         }

         for (int x = 0; x < width; x++) {
            if (!solidSpan(columns, x, width, depth)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean solidSpan(int[] columns, int offset, int stride, int length) {
      boolean occupied = false;
      boolean gap = false;

      for (int i = 0; i < length; i++) {
         if (columns[offset + stride * i] != 0) {
            if (gap) {
               return false;
            }

            occupied = true;
         } else if (occupied) {
            gap = true;
         }
      }

      return true;
   }

   private static boolean naturalGround(BlockState state) {
      return state.is(BlockTags.DIRT)
         || state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(BlockTags.BASE_STONE_NETHER)
         || state.is(BlockTags.SAND)
         || state.is(Blocks.SANDSTONE)
         || state.is(Blocks.RED_SANDSTONE)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MOSS_BLOCK)
         || state.is(Blocks.SNOW_BLOCK)
         || state.is(Blocks.SNOW);
   }

   private static boolean plant(BlockState state) {
      Block block = state.getBlock();
      return block instanceof BushBlock
         || block instanceof CactusBlock
         || block instanceof SugarCaneBlock
         || block instanceof BambooStalkBlock
         || block instanceof BambooSaplingBlock
         || block instanceof VineBlock
         || block instanceof GrowingPlantBlock
         || state.is(BlockTags.LEAVES)
         || state.is(Blocks.MOSS_CARPET);
   }
}
