package com.natamus.collective_common_fabric.functions;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_fabric.data.GlobalVariables;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1657;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3195;
import net.minecraft.class_3218;
import net.minecraft.class_3620;
import net.minecraft.class_6862;
import net.minecraft.class_6880;
import net.minecraft.class_6885;

public class BlockPosFunctions {
   private static final HashMap<class_2338, Integer> rgnbcount = new HashMap<>();
   private static final HashMap<class_2338, Integer> rgnbtcount = new HashMap<>();
   private static final HashMap<class_2338, Integer> rgnbmcount = new HashMap<>();

   public static List<class_2338> getBlocksAround(class_2338 pos, boolean down) {
      List<class_2338> around = new ArrayList<>();
      around.add(pos.method_10095());
      around.add(pos.method_10078());
      around.add(pos.method_10072());
      around.add(pos.method_10067());
      around.add(pos.method_10084());
      if (down) {
         around.add(pos.method_10074());
      }

      return around;
   }

   public static List<class_2338> getBlocksNextToEachOther(class_1937 level, class_2338 startPos, List<class_2248> possibleBlocks) {
      return getBlocksNextToEachOther(level, startPos, possibleBlocks, 50);
   }

   public static List<class_2338> getBlocksNextToEachOther(class_1937 level, class_2338 startPos, List<class_2248> possibleBlocks, int maxDistance) {
      List<class_2338> checkedBlocks = new ArrayList<>();
      List<class_2338> blocksAround = new ArrayList<>();
      if (possibleBlocks.contains(level.method_8320(startPos).method_26204())) {
         blocksAround.add(startPos);
         checkedBlocks.add(startPos);
      }

      rgnbcount.put(startPos.method_10062(), 0);
      recursiveGetNextBlocks(level, startPos, startPos, possibleBlocks, blocksAround, checkedBlocks, maxDistance);
      return blocksAround;
   }

   private static void recursiveGetNextBlocks(
      class_1937 level,
      class_2338 startPos,
      class_2338 pos,
      List<class_2248> possibleBlocks,
      List<class_2338> blocksAround,
      List<class_2338> checkedBlocks,
      int maxDistance
   ) {
      int rgnbc = rgnbcount.get(startPos);
      if (rgnbc <= 100) {
         rgnbcount.put(startPos, rgnbc + 1);

         for (class_2338 pba : getBlocksAround(pos, true)) {
            if (!checkedBlocks.contains(pba)) {
               checkedBlocks.add(pba);
               if (possibleBlocks.contains(level.method_8320(pba).method_26204()) && !blocksAround.contains(pba)) {
                  blocksAround.add(pba);
                  if (withinDistance(startPos, pba, maxDistance)) {
                     recursiveGetNextBlocks(level, startPos, pba, possibleBlocks, blocksAround, checkedBlocks, maxDistance);
                  }
               }
            }
         }
      }
   }

   public static List<class_2338> getBlocksNextToEachOtherTag(class_1937 level, class_2338 startPos, List<class_6862<class_2248>> possibleBlockTags) {
      return getBlocksNextToEachOtherTag(level, startPos, possibleBlockTags, 50);
   }

   public static List<class_2338> getBlocksNextToEachOtherTag(
      class_1937 level, class_2338 startPos, List<class_6862<class_2248>> possibleBlockTags, int maxDistance
   ) {
      List<class_2338> checkedBlocks = new ArrayList<>();
      List<class_2338> blocksAround = new ArrayList<>();
      class_2680 startBlockState = level.method_8320(startPos);

      for (class_6862<class_2248> blockTagKey : possibleBlockTags) {
         if (startBlockState.method_26164(blockTagKey)) {
            blocksAround.add(startPos);
            checkedBlocks.add(startPos);
            break;
         }
      }

      rgnbtcount.put(startPos.method_10062(), 0);
      recursiveGetNextBlocksTag(level, startPos, startPos, possibleBlockTags, blocksAround, checkedBlocks, maxDistance);
      return blocksAround;
   }

   private static void recursiveGetNextBlocksTag(
      class_1937 level,
      class_2338 startPos,
      class_2338 pos,
      List<class_6862<class_2248>> possibleBlockTags,
      List<class_2338> blocksAround,
      List<class_2338> checkedBlocks,
      int maxDistance
   ) {
      int rgnbc = rgnbtcount.get(startPos);
      if (rgnbc <= 100) {
         rgnbtcount.put(startPos, rgnbc + 1);

         for (class_2338 pba : getBlocksAround(pos, true)) {
            if (!checkedBlocks.contains(pba)) {
               checkedBlocks.add(pba);
               class_2680 pbaState = level.method_8320(pba);

               for (class_6862<class_2248> blockTagKey : possibleBlockTags) {
                  if (pbaState.method_26164(blockTagKey)) {
                     if (!blocksAround.contains(pba)) {
                        blocksAround.add(pba);
                        if (withinDistance(startPos, pba, maxDistance)) {
                           recursiveGetNextBlocksTag(level, startPos, pba, possibleBlockTags, blocksAround, checkedBlocks, maxDistance);
                        }
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   public static List<class_2338> getBlocksNextToEachOtherMaterial(class_1937 level, class_2338 startPos, List<class_3620> possibleMaterials) {
      return getBlocksNextToEachOtherMaterial(level, startPos, possibleMaterials, 50);
   }

   public static List<class_2338> getBlocksNextToEachOtherMaterial(class_1937 level, class_2338 startPos, List<class_3620> possibleMaterials, int maxDistance) {
      List<class_2338> checkedBlocks = new ArrayList<>();
      List<class_2338> blocksAround = new ArrayList<>();
      if (possibleMaterials.contains(level.method_8320(startPos).method_26205(level, startPos))) {
         blocksAround.add(startPos);
         checkedBlocks.add(startPos);
      }

      rgnbmcount.put(startPos.method_10062(), 0);
      recursiveGetNextBlocksMaterial(level, startPos, startPos, possibleMaterials, blocksAround, checkedBlocks, maxDistance);
      return blocksAround;
   }

   private static void recursiveGetNextBlocksMaterial(
      class_1937 level,
      class_2338 startPos,
      class_2338 pos,
      List<class_3620> possibleMaterials,
      List<class_2338> blocksAround,
      List<class_2338> checkedBlocks,
      int maxDistance
   ) {
      int rgnbmc = rgnbmcount.get(startPos);
      if (rgnbmc <= 100) {
         rgnbmcount.put(startPos, rgnbmc + 1);

         for (class_2338 pba : getBlocksAround(pos, true)) {
            if (!checkedBlocks.contains(pba)) {
               checkedBlocks.add(pba);
               if (possibleMaterials.contains(level.method_8320(pba).method_26205(level, pba)) && !blocksAround.contains(pba)) {
                  blocksAround.add(pba);
                  if (withinDistance(startPos, pba, maxDistance)) {
                     recursiveGetNextBlocksMaterial(level, startPos, pba, possibleMaterials, blocksAround, checkedBlocks, maxDistance);
                  }
               }
            }
         }
      }
   }

   public static class_2338 getSurfaceBlockPos(class_3218 serverLevel, int x, int z) {
      return getSurfaceBlockPos(serverLevel, x, z, false);
   }

   public static class_2338 getSurfaceBlockPos(class_3218 serverLevel, int x, int z, boolean ignoreTrees) {
      int height = serverLevel.method_31605();
      int lowestY = serverLevel.method_31607();
      class_2338 returnPos = new class_2338(x, height - 1, z);
      if (!WorldFunctions.isNether(serverLevel)) {
         class_2338 pos = new class_2338(x, height, z);

         for (int y = height; y > lowestY; y--) {
            boolean continueCycle = false;
            class_2680 blockState = serverLevel.method_8320(pos);
            if (ignoreTrees) {
               class_2248 block = blockState.method_26204();
               if (CompareBlockFunctions.isTreeLeaf(block) || CompareBlockFunctions.isTreeLog(block)) {
                  continueCycle = true;
               }
            }

            if (!continueCycle) {
               class_3620 material = blockState.method_26205(serverLevel, pos);
               if (blockState.method_26193(serverLevel, pos) >= 15 || GlobalVariables.surfacematerials.contains(material)) {
                  returnPos = pos.method_10084().method_10062();
                  break;
               }
            }

            pos = pos.method_10074();
         }
      } else {
         int maxHeight = 128;
         class_2338 pos = new class_2338(x, lowestY, z);

         for (int y = lowestY; y < maxHeight; y++) {
            class_2680 blockState = serverLevel.method_8320(pos);
            if (blockState.method_26204().equals(class_2246.field_10124)) {
               class_2680 upstate = serverLevel.method_8320(pos.method_10084());
               if (upstate.method_26204().equals(class_2246.field_10124)) {
                  returnPos = pos.method_10062();
                  break;
               }
            }

            pos = pos.method_10084();
         }
      }

      return returnPos;
   }

   public static class_2338 getCenterNearbyVillage(class_3218 serverLevel) {
      return getNearbyVillage(serverLevel, new class_2338(0, 0, 0));
   }

   public static class_2338 getNearbyVillage(class_3218 serverLevel, class_2338 nearPos) {
      return getNearbyVillage(serverLevel, nearPos, "#minecraft:village");
   }

   public static class_2338 getNearbyVillage(class_3218 serverLevel, class_2338 nearPos, String villageTag) {
      class_2338 closestvillage = null;
      if (!serverLevel.method_8503().method_27728().method_28057().method_28029()) {
         return null;
      }

      String rawOutput = CommandFunctions.getRawCommandOutput(serverLevel, class_243.method_24955(nearPos), "/locate structure " + villageTag);
      if (rawOutput.contains("[") && rawOutput.contains("]") && rawOutput.contains(", ")) {
         String[] coords;
         try {
            if (rawOutput.contains(":")) {
               rawOutput = rawOutput.split(":", 2)[1];
            }

            String rawcoords = rawOutput.split("\\[")[1].split("]")[0];
            coords = rawcoords.split(", ");
         } catch (IndexOutOfBoundsException ex) {
            return null;
         }

         if (coords.length == 3) {
            String sx = coords[0];
            String sz = coords[2];
            if (NumberFunctions.isNumeric(sx) && NumberFunctions.isNumeric(sz)) {
               return getSurfaceBlockPos(serverLevel, Integer.parseInt(sx), Integer.parseInt(sz));
            }
         }
      }

      return closestvillage;
   }

   public static class_2338 getCenterNearbyBiome(class_3218 serverLevel, String biome) {
      return getNearbyBiome(serverLevel, new class_2338(0, 0, 0), biome);
   }

   public static class_2338 getNearbyBiome(class_3218 serverLevel, class_2338 nearPos, String biome) {
      String rawOutput = CommandFunctions.getRawCommandOutput(serverLevel, class_243.method_24955(nearPos), "/locate biome " + biome);
      if (rawOutput.contains("nearest") && rawOutput.contains("[")) {
         String rawcoords = rawOutput.split("nearest")[1].split("\\[")[1].split("]")[0];
         String[] coords = rawcoords.split(", ");
         if (coords.length == 3) {
            String sx = coords[0];
            String sz = coords[2];
            if (NumberFunctions.isNumeric(sx) && NumberFunctions.isNumeric(sz)) {
               return getSurfaceBlockPos(serverLevel, Integer.parseInt(sx), Integer.parseInt(sz));
            }
         }
      }

      return null;
   }

   public static class_2338 getCenterNearbyStructure(class_3218 serverLevel, class_6885<class_3195> structure) {
      return getNearbyStructure(serverLevel, structure, new class_2338(0, 0, 0));
   }

   public static class_2338 getNearbyStructure(class_3218 serverLevel, class_6885<class_3195> structure, class_2338 nearPos) {
      return getNearbyStructure(serverLevel, structure, nearPos, 9999);
   }

   public static class_2338 getNearbyStructure(class_3218 serverLevel, class_6885<class_3195> structure, class_2338 nearPos, int radius) {
      Pair<class_2338, class_6880<class_3195>> pair = serverLevel.method_14178().method_12129().method_12103(serverLevel, structure, nearPos, radius, false);
      if (pair == null) {
         return null;
      }

      class_2338 villagePos = (class_2338)pair.getFirst();
      if (villagePos == null) {
         return null;
      }

      class_2338 spawnpos = null;

      for (int y = serverLevel.method_31605() - 1; y > 0; y--) {
         class_2338 checkpos = new class_2338(villagePos.method_10263(), y, villagePos.method_10260());
         if (!serverLevel.method_8320(checkpos).method_26204().equals(class_2246.field_10124)) {
            spawnpos = checkpos.method_10084().method_10062();
            break;
         }
      }

      return spawnpos;
   }

   public static class_2338 getBlockPlayerIsLookingAt(class_1937 level, class_1657 player, boolean stopOnLiquid) {
      class_239 raytraceresult = RayTraceFunctions.rayTrace(level, player, stopOnLiquid);
      double posX = raytraceresult.method_17784().field_1352;
      double posY = Math.floor(raytraceresult.method_17784().field_1351);
      double posZ = raytraceresult.method_17784().field_1350;
      return class_2338.method_49637(posX, posY, posZ);
   }

   public static class_2338 getRandomCoordinatesInNearestUngeneratedChunk(class_3218 serverLevel, class_2338 aroundPosition) {
      List<String> regionList = new ArrayList<>();

      try {
         File regionFolder = new File(WorldFunctions.getWorldPath(serverLevel) + File.separator + "region");
         File[] listOfRegionFiles = regionFolder.listFiles();

         for (File regionFile : listOfRegionFiles) {
            if (regionFile.isFile()) {
               regionList.add(regionFile.getName().replaceAll("r.", "").replaceAll(".mca", ""));
            }
         }
      } catch (NullPointerException ignored) {
         return null;
      }

      class_1923 chunkPos = serverLevel.method_8500(aroundPosition).method_12004();
      int curRegionX = chunkPos.method_17885();
      int curRegionZ = chunkPos.method_17886();
      int currentRange = 1;
      int loops = 0;
      String closestUngeneratedRegionString = "";

      while (closestUngeneratedRegionString.equals("")) {
         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               int regionX = curRegionX + x * currentRange;
               int regionZ = curRegionZ + z * currentRange;
               String regionString = regionX + "." + regionZ;
               if (!regionList.contains(regionString)) {
                  closestUngeneratedRegionString = regionString;
                  break;
               }
            }

            if (!closestUngeneratedRegionString.equals("")) {
               break;
            }
         }

         currentRange++;
         if (++loops > 50) {
            return null;
         }
      }

      String[] cursspl = closestUngeneratedRegionString.split("\\.");

      int outputRegionX;
      int outputRegionZ;
      try {
         outputRegionX = Integer.parseInt(cursspl[0]);
         outputRegionZ = Integer.parseInt(cursspl[1]);
      } catch (NumberFormatException ex) {
         return null;
      }

      int minXRange = outputRegionX * 512 - 256;
      int maxXRange = outputRegionX * 512 + 256;
      int minZRange = outputRegionZ * 512 - 256;
      int maxZRange = outputRegionZ * 512 + 256;
      int randomXCoord = ThreadLocalRandom.current().nextInt(minXRange, maxXRange + 1);
      int randomZCoord = ThreadLocalRandom.current().nextInt(minZRange, maxZRange + 1);
      int randomYCoord = getSurfaceBlockPos(serverLevel, randomXCoord, randomZCoord).method_10264();
      return new class_2338(randomXCoord, randomYCoord, randomZCoord);
   }

   public static Boolean isOnSurface(class_1937 level, class_2338 pos) {
      return level.method_8311(pos);
   }

   public static Boolean isOnSurface(class_1937 level, class_243 vecPos) {
      return isOnSurface(level, class_2338.method_49637(vecPos.field_1352, vecPos.field_1351, vecPos.field_1350));
   }

   public static Boolean withinDistance(class_2338 start, class_2338 end, int distance) {
      return withinDistance(start, end, (double)distance);
   }

   public static Boolean withinDistance(class_2338 start, class_2338 end, double distance) {
      return start.method_19771(end, distance);
   }

   public static class_2338 getBlockPosFromHitResult(class_239 hitResult) {
      class_243 vec = hitResult.method_17784();
      return class_2338.method_49637(vec.field_1352, vec.field_1351, vec.field_1350);
   }
}
