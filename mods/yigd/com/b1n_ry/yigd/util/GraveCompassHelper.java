package com.b1n_ry.yigd.util;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GraveCompassHelper {
   private static final Map<ResourceKey<Level>, GraveCompassHelper.KDNode> GRAVE_POSITIONS = new HashMap<>();

   public static void giveCompass(ServerPlayer player, UUID graveId, BlockPos gravePos, ResourceKey<Level> worldKey) {
      ItemStack compass = Items.COMPASS.getDefaultInstance();
      BlockPos closestPos = null;
      if (YigdConfig.getConfig().extraFeatures.graveCompass.pointToClosest != ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED) {
         closestPos = findClosest(player.getUUID(), worldKey, player.blockPosition());
      }

      if (closestPos != null) {
         compass.set(Yigd.GRAVE_LOCATION, new GlobalPos(worldKey, closestPos));
      } else {
         compass.set(Yigd.GRAVE_ID, graveId);
         compass.set(Yigd.GRAVE_LOCATION, new GlobalPos(worldKey, gravePos));
      }

      compass.set(DataComponents.CUSTOM_NAME, Component.translatable("item.yigd.grave_compass").withStyle(style -> style.withItalic(false)));
      player.addItem(compass);
   }

   public static void updateClosestNbt(ResourceKey<Level> worldKey, BlockPos pos, UUID holderId, ItemStack compass) {
      if (YigdConfig.getConfig().extraFeatures.graveCompass.pointToClosest != ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED) {
         if (compass.has(Yigd.GRAVE_LOCATION)) {
            BlockPos closestPos = findClosest(holderId, worldKey, pos);
            if (closestPos != null) {
               compass.set(Yigd.GRAVE_LOCATION, new GlobalPos(worldKey, closestPos));
            }
         }
      }
   }

   public static void addGravePosition(ResourceKey<Level> worldKey, BlockPos gravePos, UUID ownerId) {
      GraveCompassHelper.KDNode root = GRAVE_POSITIONS.get(worldKey);
      int[] pos = new int[]{gravePos.getX(), gravePos.getY(), gravePos.getZ()};
      if (root == null) {
         GRAVE_POSITIONS.put(worldKey, new GraveCompassHelper.KDNode(ownerId, pos, null, null));
      } else {
         addGravePosition(root, ownerId, pos, 0);
      }
   }

   private static void addGravePosition(GraveCompassHelper.KDNode parent, UUID ownerId, int[] pos, int depth) {
      if (depth <= 100) {
         int cmp = depth % 3;
         if (pos[cmp] < parent.pos[cmp]) {
            if (parent.left == null) {
               parent.left = new GraveCompassHelper.KDNode(ownerId, pos, null, null);
            } else {
               addGravePosition(parent.left, ownerId, pos, depth + 1);
            }
         } else if (parent.right == null) {
            parent.right = new GraveCompassHelper.KDNode(ownerId, pos, null, null);
         } else {
            addGravePosition(parent.right, ownerId, pos, depth + 1);
         }
      }
   }

   public static void setClaimed(ResourceKey<Level> worldKey, BlockPos gravePos) {
      GraveCompassHelper.KDNode root = GRAVE_POSITIONS.get(worldKey);
      if (root != null) {
         int[] pos = new int[]{gravePos.getX(), gravePos.getY(), gravePos.getZ()};
         setClaimed(root, pos, 0);
      }
   }

   private static void setClaimed(GraveCompassHelper.KDNode node, int[] pos, int depth) {
      if (node != null) {
         if (Arrays.equals(node.pos, pos)) {
            node.isUnclaimed = false;
         } else {
            int cmp = depth % 3;
            if (node.pos[cmp] < pos[cmp]) {
               setClaimed(node.right, pos, depth + 1);
            } else {
               setClaimed(node.left, pos, depth + 1);
            }
         }
      }
   }

   @Nullable
   public static BlockPos findClosest(UUID ownerId, ResourceKey<Level> worldKey, BlockPos pos) {
      ExtraFeaturesConfig.GraveCompassConfig config = YigdConfig.getConfig().extraFeatures.graveCompass;
      if (config.pointToClosest == ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED) {
         return null;
      }

      GraveCompassHelper.KDNode root = GRAVE_POSITIONS.get(worldKey);
      if (root == null) {
         return null;
      }

      int[] searchPos = new int[]{pos.getX(), pos.getY(), pos.getZ()};
      int[] closest = findClosest(root, ownerId, searchPos, 0, config);
      return closest == null ? null : new BlockPos(closest[0], closest[1], closest[2]);
   }

   private static int[] findClosest(GraveCompassHelper.KDNode node, UUID ownerId, int[] pos, int depth, ExtraFeaturesConfig.GraveCompassConfig config) {
      if (node != null && depth <= 100) {
         int cmp = depth % 3;
         int[] closest = null;
         if ((config.pointToClosest == ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.ALL || node.ownerId.equals(ownerId)) && node.isUnclaimed) {
            closest = node.pos;
         }

         GraveCompassHelper.KDNode nextNode;
         GraveCompassHelper.KDNode otherNode;
         if (pos[cmp] < node.pos[cmp]) {
            nextNode = node.left;
            otherNode = node.right;
         } else {
            nextNode = node.right;
            otherNode = node.left;
         }

         int[] closestChild = findClosest(nextNode, ownerId, pos, depth + 1, config);
         if (closestChild != null && (closest == null || distanceSquared(closestChild, pos) < distanceSquared(closest, pos))) {
            closest = closestChild;
         }

         if (closestChild == null || Math.pow(node.pos[cmp] - pos[cmp], 2.0) < distanceSquared(closest, pos)) {
            closestChild = findClosest(otherNode, ownerId, pos, depth + 1, config);
            if (closestChild != null && (closest == null || distanceSquared(closestChild, pos) < distanceSquared(closest, pos))) {
               closest = closestChild;
            }
         }

         return closest;
      } else {
         return null;
      }
   }

   private static double distanceSquared(int[] pos1, int[] pos2) {
      return Math.pow(pos1[0] - pos2[0], 2.0) + Math.pow(pos1[1] - pos2[1], 2.0) + Math.pow(pos1[2] - pos2[2], 2.0);
   }

   private static class KDNode {
      @NotNull
      private final UUID ownerId;
      private boolean isUnclaimed = true;
      private final int @NotNull [] pos;
      @Nullable
      private GraveCompassHelper.KDNode left;
      @Nullable
      private GraveCompassHelper.KDNode right;

      public KDNode(@NotNull UUID ownerId, int @NotNull [] pos, @Nullable GraveCompassHelper.KDNode left, @Nullable GraveCompassHelper.KDNode right) {
         this.ownerId = ownerId;
         this.pos = pos;
         this.left = left;
         this.right = right;
      }
   }
}
