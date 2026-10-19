package com.finderfeed.fdlib.systems.cutscenes.camera_motion;

import com.finderfeed.fdlib.FDLibCalls;
import com.finderfeed.fdlib.systems.cutscenes.CameraPos;
import com.finderfeed.fdlib.systems.cutscenes.CutsceneData;
import com.finderfeed.fdlib.systems.cutscenes.EasingType;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import java.util.List;
import net.minecraft.world.phys.Vec3;

public class CatmullRomCameraMotion extends CameraMotion {
   @Override
   public Vec3 calculateCameraPosition(CutsceneData data, int currentTime, float partialTick) {
      List<CameraPos> positions = data.getCameraPositions();
      if (positions.isEmpty()) {
         throw new RuntimeException("List of camera positions cannot be empty!");
      }

      int cutsceneTime = data.getCutsceneTime();
      EasingType easingType = data.getTimeEasing();
      float p = easingType.apply((currentTime + partialTick) / cutsceneTime);
      float globalPercent = p * (positions.size() - 1);
      int index = (int)globalPercent;
      float localPercent = globalPercent - index;
      CameraPos pos;
      Vec3 prev = (pos = FDLibCalls.getListValueSafe(index - 1, positions)) == null ? null : pos.getPos();
      Vec3 current = (pos = FDLibCalls.getListValueSafe(index, positions)) == null ? null : pos.getPos();
      Vec3 next = (pos = FDLibCalls.getListValueSafe(index + 1, positions)) == null ? null : pos.getPos();
      Vec3 next2 = (pos = FDLibCalls.getListValueSafe(index + 2, positions)) == null ? null : pos.getPos();
      return FDMathUtil.catmullrom(prev, current, next, next2, localPercent);
   }
}
