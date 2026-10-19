package me.decce.gnetum;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.Deque;
import java.util.NoSuchElementException;
import me.decce.gnetum.mixins.PoseStackAccessor;

public class PoseStackHelper {
   private static int initialSize;
   private static Pose bottomPose;

   public static void beginHudRendering(PoseStack poseStack) {
      PoseStackAccessor accessor = (PoseStackAccessor)poseStack;
      Deque<Pose> internalStack = accessor.getPoseStack();
      initialSize = internalStack.size();
      bottomPose = internalStack.getFirst();
   }

   public static void endHudRendering(PoseStack poseStack) {
      PoseStackAccessor accessor = (PoseStackAccessor)poseStack;
      Deque<Pose> internalStack = accessor.getPoseStack();

      while (internalStack.size() > initialSize) {
         poseStack.popPose();
      }
   }

   public static void checked(PoseStack poseStack, Runnable runnable) {
      if (!checkedImpl(poseStack, runnable) && Gnetum.rendering) {
         Gnetum.disableCachingForCurrentElement("Pose Stack");
      }
   }

   private static boolean checkedImpl(PoseStack poseStack, Runnable runnable) {
      PoseStackAccessor accessor = (PoseStackAccessor)poseStack;
      Deque<Pose> internalStack = accessor.getPoseStack();
      int previousSize = internalStack.size();

      try {
         runnable.run();
      } catch (NoSuchElementException e) {
         while (internalStack.size() < previousSize) {
            safePushPose(poseStack);
         }

         return false;
      }

      if (internalStack.size() == previousSize) {
         return true;
      }

      while (internalStack.size() < previousSize) {
         safePushPose(poseStack);
      }

      return false;
   }

   private static void safePushPose(PoseStack poseStack) {
      PoseStackAccessor accessor = (PoseStackAccessor)poseStack;
      Deque<Pose> internalStack = accessor.getPoseStack();
      if (internalStack.isEmpty()) {
         internalStack.addLast(bottomPose);
      } else {
         poseStack.pushPose();
      }
   }
}
