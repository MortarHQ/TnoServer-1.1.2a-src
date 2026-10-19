package com.finderfeed.fdlib.systems.broken_screen_effect;

import com.finderfeed.fdlib.util.math.ComplexEasingFunction;
import com.finderfeed.fdlib.util.rendering.FDEasings;
import net.minecraft.util.Mth;

public class ShatteredScreenEffectInstance {
   public ComplexEasingFunction easing;
   public int currentTick = 0;
   protected int fullLength;
   public ShatteredScreenSettings settings;

   public ShatteredScreenEffectInstance(ShatteredScreenSettings settings) {
      this.settings = settings;
      this.fullLength = settings.inTime + settings.stayTime + settings.outTime;
      this.easing = ComplexEasingFunction.builder()
         .addArea(settings.inTime, FDEasings::easeOut)
         .addArea(settings.stayTime, FDEasings::one)
         .addArea(settings.outTime, FDEasings::reversedEaseOut)
         .build();
   }

   public void tick() {
      this.currentTick = Mth.clamp(this.currentTick + 1, 0, this.fullLength);
   }

   public float getCurrentPercent(float pticks) {
      return this.easing.apply(Mth.clamp(this.currentTick + pticks, 0.0F, this.fullLength));
   }

   public boolean hasEnded() {
      return this.currentTick >= this.fullLength;
   }
}
