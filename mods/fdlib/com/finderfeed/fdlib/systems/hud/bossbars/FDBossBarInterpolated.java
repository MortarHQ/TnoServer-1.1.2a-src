package com.finderfeed.fdlib.systems.hud.bossbars;

import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public abstract class FDBossBarInterpolated extends FDBossBar {
   private float oldPercentage = 1.0F;
   private float interpolatedPercentage = 1.0F;
   private int interpolationTime;
   private int currentInterpolationTime = 0;

   public FDBossBarInterpolated(UUID uuid, int entityId, int interpolationTime) {
      super(uuid, entityId);
      this.interpolationTime = interpolationTime;
   }

   @Override
   public void render(GuiGraphics graphics, float partialTicks) {
      if (this.currentInterpolationTime > 0) {
         float p = FDEasings.easeIn(Mth.clamp(this.currentInterpolationTime - partialTicks, 0.0F, this.interpolationTime) / this.interpolationTime);
         this.interpolatedPercentage = FDMathUtil.lerp(this.getPercentage(), this.oldPercentage, p);
      } else {
         this.oldPercentage = this.getPercentage();
         this.interpolatedPercentage = this.getPercentage();
      }

      this.renderInterpolatedBossBar(graphics, partialTicks, this.interpolatedPercentage);
   }

   public abstract void renderInterpolatedBossBar(GuiGraphics var1, float var2, float var3);

   @Override
   public void tick(float topOffset) {
      this.currentInterpolationTime = Mth.clamp(this.currentInterpolationTime - 1, 0, this.interpolationTime);
   }

   @Override
   public void setPercentage(float percentage) {
      if (percentage != this.getPercentage()) {
         if (this.currentInterpolationTime > 0) {
            this.oldPercentage = this.getInterpolatedPercentage();
         } else {
            this.oldPercentage = this.getPercentage();
         }

         this.currentInterpolationTime = this.interpolationTime;
      }

      super.setPercentage(percentage);
   }

   public float getInterpolatedPercentage() {
      return this.interpolatedPercentage;
   }

   public int getCurrentInterpolationTime() {
      return this.currentInterpolationTime;
   }

   @Override
   public float getPercentage() {
      return super.getPercentage();
   }

   public float getOldPercentage() {
      return this.oldPercentage;
   }

   public int getInterpolationTime() {
      return this.interpolationTime;
   }
}
