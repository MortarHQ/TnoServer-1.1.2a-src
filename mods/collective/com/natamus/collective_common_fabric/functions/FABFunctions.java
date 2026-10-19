package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.config.CollectiveConfigHandler;
import com.natamus.collective_common_fabric.data.GlobalVariables;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2382;
import net.minecraft.class_2508;
import net.minecraft.class_2551;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_6862;

public class FABFunctions {
   private static final Map<class_2248, WeakHashMap<class_1937, List<class_2338>>> getMapFromBlock = new HashMap<>();
   private static final WeakHashMap<class_1937, Map<Date, class_2338>> timeoutpositions = new WeakHashMap<>();

   public static List<class_2586> getBlockEntitiesAroundPosition(class_1937 level, class_2338 pos, Integer radius) {
      List<class_2586> blockentities = new ArrayList<>();
      int chunkradius = (int)Math.ceil(radius.intValue() / 16.0);
      int chunkPosX = pos.method_10263() >> 4;
      int chunkPosZ = pos.method_10260() >> 4;

      for (int x = chunkPosX - chunkradius; x < chunkPosX + chunkradius; x++) {
         for (int z = chunkPosZ - chunkradius; z < chunkPosZ + chunkradius; z++) {
            class_2818 levelChunk = level.method_8497(x, z);
            if (level.method_8477(levelChunk.method_12004().method_8323())) {
               for (class_2586 be : levelChunk.method_12214().values()) {
                  if (!blockentities.contains(be)) {
                     blockentities.add(be);
                  }
               }
            }
         }
      }

      return blockentities;
   }

   public static List<class_2338> getAllTaggedTileEntityPositionsNearbyEntity(class_6862<class_2248> tetag, Integer radius, class_1937 level, class_1297 entity) {
      return getAllTaggedTileEntityPositionsNearbyPosition(tetag, radius, level, entity.method_24515());
   }

   public static List<class_2338> getAllTaggedTileEntityPositionsNearbyPosition(class_6862<class_2248> tetag, Integer radius, class_1937 level, class_2338 pos) {
      List<class_2338> nearbypositions = new ArrayList<>();

      for (class_2586 loadedtileentity : getBlockEntitiesAroundPosition(level, pos, radius)) {
         class_2680 loadedtilestate = loadedtileentity.method_11010();
         if (loadedtilestate != null && loadedtilestate.method_26164(tetag)) {
            class_2338 ltepos = loadedtileentity.method_11016();
            if (ltepos.method_19771(new class_2382(pos.method_10263(), pos.method_10264(), pos.method_10260()), radius.intValue())) {
               nearbypositions.add(loadedtileentity.method_11016());
            }
         }
      }

      return nearbypositions;
   }

   public static List<class_2338> getAllTileEntityPositionsNearbyEntity(class_2591<?> tetype, Integer radius, class_1937 level, class_1297 entity) {
      return getAllTileEntityPositionsNearbyPosition(tetype, radius, level, entity.method_24515());
   }

   public static List<class_2338> getAllTileEntityPositionsNearbyPosition(class_2591<?> tetype, Integer radius, class_1937 level, class_2338 pos) {
      List<class_2338> nearbypositions = new ArrayList<>();

      for (class_2586 loadedtileentity : getBlockEntitiesAroundPosition(level, pos, radius)) {
         class_2591<?> loadedtiletype = loadedtileentity.method_11017();
         if (loadedtiletype != null && loadedtiletype.equals(tetype)) {
            class_2338 ltepos = loadedtileentity.method_11016();
            if (ltepos.method_19771(new class_2382(pos.method_10263(), pos.method_10264(), pos.method_10260()), radius.intValue())) {
               nearbypositions.add(loadedtileentity.method_11016());
            }
         }
      }

      return nearbypositions;
   }

   public static class_2338 getRequestedBlockAroundEntitySpawn(
      class_2248 rawqueryblock, Integer radius, Double radiusmodifier, class_1937 level, class_1297 entity
   ) {
      class_2248 requestedblock = processCommonBlock(rawqueryblock);
      WeakHashMap<class_1937, List<class_2338>> levelblocks = getMap(requestedblock);
      class_2338 epos = entity.method_24515();
      class_2338 removeblockpos = null;
      List<class_2338> currentblocks;
      if (levelblocks.containsKey(level)) {
         currentblocks = levelblocks.get(level);
         List<class_2338> cbtoremove = new ArrayList<>();

         for (class_2338 cblock : currentblocks) {
            if (!level.method_8398().method_12123(cblock.method_10263() >> 4, cblock.method_10260() >> 4)) {
               cbtoremove.add(cblock);
            } else if (!level.method_8320(cblock).method_26204().equals(requestedblock)) {
               cbtoremove.add(cblock);
            } else if (cblock.method_19771(epos, radius.intValue() * radiusmodifier)) {
               return cblock.method_10062();
            }
         }

         if (cbtoremove.size() > 0) {
            for (class_2338 tr : cbtoremove) {
               currentblocks.remove(tr);
            }
         }
      } else {
         currentblocks = new ArrayList<>();
      }

      Map<Date, class_2338> timeouts;
      if (timeoutpositions.containsKey(level)) {
         timeouts = timeoutpositions.get(level);
         List<Date> totoremove = new ArrayList<>();
         if (timeouts.size() > 0) {
            Date now = new Date();

            for (Date todate : timeouts.keySet()) {
               class_2338 toepos = timeouts.get(todate);
               if (removeblockpos != null && toepos.method_19771(removeblockpos, 64.0)) {
                  totoremove.add(todate);
               }

               long ms = now.getTime() - todate.getTime();
               if (ms > CollectiveConfigHandler.findABlockCheckAroundEntitiesDelayMs) {
                  totoremove.add(todate);
               } else if (toepos.method_19771(epos, radius.intValue() * radiusmodifier)) {
                  return null;
               }
            }
         }

         if (totoremove.size() > 0) {
            for (Date tr : totoremove) {
               timeouts.remove(tr);
            }
         }
      } else {
         timeouts = new HashMap<>();
      }

      if (GlobalVariables.blocksWithTileEntity.containsKey(requestedblock)) {
         List<class_2586> blockentities = getBlockEntitiesAroundPosition(level, epos, radius);
         class_2591<?> tiletypetofind = GlobalVariables.blocksWithTileEntity.get(requestedblock);

         for (class_2586 loadedtileentity : blockentities) {
            class_2591<?> loadedtiletype = loadedtileentity.method_11017();
            if (loadedtiletype != null && loadedtiletype.equals(tiletypetofind)) {
               class_2338 ltepos = loadedtileentity.method_11016();
               if (ltepos.method_19771(epos, radius.intValue() * radiusmodifier)) {
                  currentblocks.add(ltepos.method_10062());
                  levelblocks.put(level, currentblocks);
                  getMapFromBlock.put(requestedblock, levelblocks);
                  return ltepos.method_10062();
               }
            }
         }
      } else {
         int r = radius;

         for (int x = -r; x < r; x++) {
            for (int y = -r; y < r; y++) {
               for (int z = -r; z < r; z++) {
                  class_2338 cpos = epos.method_10089(x).method_10076(y).method_10086(z);
                  class_2680 state = level.method_8320(cpos);
                  if (state.method_26204().equals(requestedblock)) {
                     currentblocks.add(cpos.method_10062());
                     levelblocks.put(level, currentblocks);
                     getMapFromBlock.put(requestedblock, levelblocks);
                     return cpos.method_10062();
                  }
               }
            }
         }
      }

      timeouts.put(new Date(), epos.method_10062());
      timeoutpositions.put(level, timeouts);
      return null;
   }

   public static class_2338 updatePlacedBlock(class_2248 requestedblock, class_2338 bpos, class_1937 level) {
      class_2680 state = level.method_8320(bpos);
      if (state.method_26204().equals(requestedblock)) {
         WeakHashMap<class_1937, List<class_2338>> levelblocks = getMap(requestedblock);
         List<class_2338> currentblocks;
         if (levelblocks.containsKey(level)) {
            currentblocks = levelblocks.get(level);
         } else {
            currentblocks = new ArrayList<>();
         }

         if (!currentblocks.contains(bpos)) {
            currentblocks.add(bpos);
            levelblocks.put(level, currentblocks);
            getMapFromBlock.put(requestedblock, levelblocks);
         }

         return bpos;
      } else {
         return null;
      }
   }

   private static WeakHashMap<class_1937, List<class_2338>> getMap(class_2248 requestedblock) {
      WeakHashMap<class_1937, List<class_2338>> levelblocks;
      if (getMapFromBlock.containsKey(requestedblock)) {
         levelblocks = getMapFromBlock.get(requestedblock);
      } else {
         levelblocks = new WeakHashMap<>();
      }

      return levelblocks;
   }

   private static class_2248 processCommonBlock(class_2248 requestedblock) {
      class_2248 blocktoreturn = requestedblock;
      if (requestedblock instanceof class_2508 || requestedblock instanceof class_2551) {
         blocktoreturn = class_2246.field_10121;
      }

      return blocktoreturn;
   }
}
