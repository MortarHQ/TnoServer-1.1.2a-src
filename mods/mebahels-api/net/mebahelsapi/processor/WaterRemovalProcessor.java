package net.mebahelsapi.processor;

import com.mojang.serialization.MapCodec;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.mebahelsapi.MebahelsAPI;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3491;
import net.minecraft.class_3492;
import net.minecraft.class_3828;
import net.minecraft.class_4538;
import net.minecraft.class_3499.class_3501;

public class WaterRemovalProcessor extends class_3491 {
   private final Map<Integer, class_238> scheduledBoxes = new HashMap<>();
   private class_3492 previousPlacementData = null;
   public static final MapCodec<WaterRemovalProcessor> CODEC = MapCodec.unit(WaterRemovalProcessor::new);

   public class_3501 method_15110(
      class_4538 world, class_2338 pos, class_2338 pivot, class_3501 originalBlockInfo, class_3501 currentBlockInfo, class_3492 placementData
   ) {
      if (this.previousPlacementData == null || placementData != this.previousPlacementData) {
         class_2338 minPos = new class_2338(
            placementData.method_15124().method_35415(), placementData.method_15124().method_35416(), placementData.method_15124().method_35417()
         );
         class_2338 maxPos = new class_2338(
            placementData.method_15124().method_35418(), placementData.method_15124().method_35419(), placementData.method_15124().method_35420()
         );
         class_238 structureBox = new class_238(class_243.method_24954(minPos), class_243.method_24954(maxPos));
         int boxHash = Objects.hash(minPos.method_10063(), maxPos.method_10063());
         if (!this.scheduledBoxes.containsKey(boxHash)) {
            this.scheduledBoxes.put(boxHash, structureBox);
            MebahelsAPI.getWaterRemovalScheduler().schedule(minPos, maxPos, 1);
         }
      }

      this.previousPlacementData = placementData;
      return currentBlockInfo;
   }

   protected class_3828<?> method_16772() {
      return MyProcessors.WATER_REMOVAL_PROCESSOR;
   }
}
