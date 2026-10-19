package com.finderfeed.fdlib.systems.screen.screen_effect.instances;

import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffect;
import com.finderfeed.fdlib.systems.screen.screen_effect.instances.datas.ScreenColorData;
import com.finderfeed.fdlib.util.rendering.FDRenderUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class ScreenColorEffect extends ScreenEffect<ScreenColorData> {
   public ScreenColorEffect(ScreenColorData data, int inTime, int stayTime, int outTime) {
      super(data, inTime, stayTime, outTime);
   }

   @Override
   public void render(GuiGraphics graphics, DeltaTracker deltaTracker, int currentTick, float screenWidth, float screenHeight) {
      float pticks = deltaTracker.getGameTimeDeltaPartialTick(false);
      ScreenColorData colorData = this.getData();
      float p = 0.0F;
      if (this.isInTime(currentTick)) {
         p = this.getInTimePercent(currentTick, pticks);
      } else if (this.isStayTime(currentTick)) {
         p = 1.0F;
      } else if (this.isOutTime(currentTick)) {
         p = 1.0F - this.getOutTimePercent(currentTick, pticks);
      }

      FDRenderUtil.fill(graphics.pose(), 0.0F, 0.0F, screenWidth, screenHeight, colorData.r, colorData.g, colorData.b, colorData.a * p);
   }
}
