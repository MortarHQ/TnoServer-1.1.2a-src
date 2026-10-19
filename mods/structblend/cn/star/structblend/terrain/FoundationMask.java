package cn.star.structblend.terrain;

import cn.star.structblend.mixin.StructureTemplateAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

final class FoundationMask {
   private static final Map<StructureTemplate, FoundationMask> CACHE = Collections.synchronizedMap(new WeakHashMap<>());
   private static final FoundationMask EMPTY = new FoundationMask(-1, List.of(), List.of(), List.of(), false, false, false, false, false, false, false);
   private final int layer;
   private final List<FoundationMask.Run> runs;
   private final List<FoundationMask.Run> yardRuns;
   private final List<FoundationMask.BodyRun> bodyRuns;
   private final boolean broad;
   private final boolean verticalBody;
   private final boolean openBase;
   private final boolean solidBase;
   private final boolean floatingHull;
   private final boolean sandstonePlatform;
   private final boolean looseWood;
   static volatile boolean occupiedArea = true;

   private FoundationMask(
      int layer,
      List<FoundationMask.Run> runs,
      List<FoundationMask.Run> yardRuns,
      List<FoundationMask.BodyRun> bodyRuns,
      boolean broad,
      boolean verticalBody,
      boolean openBase,
      boolean solidBase,
      boolean floatingHull,
      boolean sandstonePlatform,
      boolean looseWood
   ) {
      this.layer = layer;
      this.runs = runs;
      this.yardRuns = yardRuns;
      this.bodyRuns = bodyRuns;
      this.broad = broad;
      this.verticalBody = verticalBody;
      this.openBase = openBase;
      this.solidBase = solidBase;
      this.floatingHull = floatingHull;
      this.sandstonePlatform = sandstonePlatform;
      this.looseWood = looseWood;
   }

   static void clearCacheForTest() {
      synchronized (CACHE) {
         CACHE.clear();
      }
   }

   static FoundationMask get(StructureTemplate template) {
      synchronized (CACHE) {
         return CACHE.computeIfAbsent(template, FoundationMask::create);
      }
   }

   int layer() {
      return this.layer;
   }

   List<FoundationMask.Run> runs() {
      return this.runs;
   }

   List<FoundationMask.Run> yardRuns() {
      return this.yardRuns;
   }

   List<FoundationMask.BodyRun> bodyRuns() {
      return this.bodyRuns;
   }

   boolean isEmpty() {
      return this.runs.isEmpty();
   }

   boolean isBroad() {
      return this.broad;
   }

   boolean hasVerticalBody() {
      return this.verticalBody;
   }

   boolean hasOpenBase() {
      return this.openBase;
   }

   boolean hasSolidBase() {
      return this.solidBase;
   }

   boolean isFloatingHull() {
      return this.floatingHull;
   }

   boolean isSandstonePlatform() {
      return this.sandstonePlatform;
   }

   boolean isLooseWood() {
      return this.looseWood;
   }

   private static FoundationMask create(StructureTemplate template) {
      if (DecorativeTemplateFilter.isDecoration(template)) {
         return EMPTY;
      }

      Vec3i size = template.getSize();
      if (size.getX() >= 2 && size.getZ() >= 2 && size.getY() >= 1) {
         List<Palette> palettes = ((StructureTemplateAccessor)template).structblend$getPalettes();
         if (palettes.isEmpty()) {
            return EMPTY;
         }

         int limit = Math.min(size.getY(), 8);
         int[] coverage = new int[limit];
         List<StructureBlockInfo> blocks = palettes.get(0).blocks();

         for (StructureBlockInfo block : blocks) {
            int y = block.pos().getY();
            if (y >= 0 && y < limit && block.state().blocksMotion()) {
               coverage[y]++;
            }
         }

         int bestLayer = -1;
         int minCoverage = Math.max(4, Math.min(32, size.getX() * size.getZ() / 20));

         for (int y = 0; y < limit; y++) {
            if (coverage[y] >= minCoverage) {
               bestLayer = y;
               break;
            }
         }

         if (bestLayer < 0) {
            return EMPTY;
         }

         boolean[] contact = new boolean[size.getX() * size.getZ()];
         int[] body = new int[contact.length];
         Arrays.fill(body, Integer.MAX_VALUE);
         int contactCount = 0;
         int woodenContacts = 0;
         int woodenUpperBlocks = 0;
         int sandstoneContacts = 0;
         int upperBlocks = 0;
         int highestSolid = bestLayer;

         for (StructureBlockInfo block : blocks) {
            if (block.state().blocksMotion()) {
               int y = block.pos().getY();
               boolean wooden = block.state().is(BlockTags.PLANKS)
                  || block.state().is(BlockTags.LOGS)
                  || block.state().is(BlockTags.WOODEN_SLABS)
                  || block.state().is(BlockTags.WOODEN_STAIRS);
               if (y >= bestLayer + 2) {
                  upperBlocks++;
                  highestSolid = Math.max(highestSolid, y);
                  if (wooden) {
                     woodenUpperBlocks++;
                  }
               }

               int x = block.pos().getX();
               int z = block.pos().getZ();
               boolean inside = x >= 0 && x < size.getX() && z >= 0 && z < size.getZ();
               if (inside && y >= bestLayer) {
                  body[z * size.getX() + x] = Math.min(body[z * size.getX() + x], y);
               }

               if (y == bestLayer && inside) {
                  int index = z * size.getX() + x;
                  if (!contact[index]) {
                     contact[index] = true;
                     contactCount++;
                     if (wooden) {
                        woodenContacts++;
                     }

                     if (block.state().is(BlockTags.SAND) || BuiltInRegistries.BLOCK.getKey(block.state().getBlock()).getPath().contains("sandstone")) {
                        sandstoneContacts++;
                     }
                  }
               }
            }
         }

         List<FoundationMask.Run> runs = runs(contact, size);
         int area = size.getX() * size.getZ();
         int footprint = area;
         if (occupiedArea) {
            boolean[] occupied = new boolean[contact.length];
            int occupiedCount = 0;

            for (StructureBlockInfo block : blocks) {
               int x = block.pos().getX();
               int z = block.pos().getZ();
               if (block.pos().getY() >= bestLayer && x >= 0 && x < size.getX() && z >= 0 && z < size.getZ() && !occupied[z * size.getX() + x]) {
                  occupied[z * size.getX() + x] = true;
                  occupiedCount++;
               }
            }

            footprint = Math.max(occupiedCount, 1);
         }

         boolean broad = area >= 256 && contactCount >= Math.max(64, footprint / 5);
         boolean verticalBody = highestSolid >= bestLayer + 3 && upperBlocks >= Math.max(8, contactCount / 4);
         int densestLowLayer = 0;

         for (int count : coverage) {
            densestLowLayer = Math.max(densestLowLayer, count);
         }

         boolean openBase = area >= 256 && densestLowLayer < footprint / 10;
         boolean solidBase = contactCount >= Math.max(16, footprint / 4);
         boolean elongated = 2 * Math.max(size.getX(), size.getZ()) >= 3 * Math.min(size.getX(), size.getZ());
         boolean floatingHull = elongated && contactCount >= 16 && woodenContacts * 4 >= contactCount * 3;
         boolean sandstonePlatform = contactCount >= Math.max(16, area / 4) && sandstoneContacts >= 16 && sandstoneContacts * 4 >= contactCount * 3;
         boolean looseWood = area >= 64
            && !solidBase
            && contactCount * 2 < footprint
            && highestSolid - bestLayer <= 5
            && woodenUpperBlocks >= 8
            && upperBlocks * 2 < footprint
            && woodenUpperBlocks * 3 >= upperBlocks * 2;
         List<FoundationMask.Run> yardRuns = List.of();
         if (broad && solidBase && verticalBody && !openBase && !floatingHull && !sandstonePlatform && !looseWood && bestLayer + 1 < size.getY()) {
            boolean[] yard = new boolean[contact.length];

            for (StructureBlockInfo block : blocks) {
               int x = block.pos().getX();
               int z = block.pos().getZ();
               if (block.pos().getY() == bestLayer + 1 && x >= 0 && x < size.getX() && z >= 0 && z < size.getZ()) {
                  yard[z * size.getX() + x] = !contact[z * size.getX() + x];
               }
            }

            yardRuns = runs(yard, size);
         }

         return new FoundationMask(
            bestLayer, runs, yardRuns, bodyRuns(body, size), broad, verticalBody, openBase, solidBase, floatingHull, sandstonePlatform, looseWood
         );
      } else {
         return EMPTY;
      }
   }

   private static List<FoundationMask.BodyRun> bodyRuns(int[] lowest, Vec3i size) {
      List<FoundationMask.BodyRun> runs = new ArrayList<>();

      for (int z = 0; z < size.getZ(); z++) {
         for (int x = 0; x < size.getX(); x++) {
            int y = lowest[z * size.getX() + x];
            if (y != Integer.MAX_VALUE) {
               int first = x;

               while (x + 1 < size.getX() && lowest[z * size.getX() + x + 1] == y) {
                  x++;
               }

               runs.add(new FoundationMask.BodyRun(first, x, z, y));
            }
         }
      }

      return List.copyOf(runs);
   }

   private static List<FoundationMask.Run> runs(boolean[] cells, Vec3i size) {
      List<FoundationMask.Run> runs = new ArrayList<>();

      for (int z = 0; z < size.getZ(); z++) {
         for (int x = 0; x < size.getX(); x++) {
            if (cells[z * size.getX() + x]) {
               int first = x;

               while (x + 1 < size.getX() && cells[z * size.getX() + x + 1]) {
                  x++;
               }

               runs.add(new FoundationMask.Run(first, x, z));
            }
         }
      }

      return List.copyOf(runs);
   }

   record BodyRun(int minX, int maxX, int z, int lowest) {
   }

   record Run(int minX, int maxX, int z) {
   }
}
