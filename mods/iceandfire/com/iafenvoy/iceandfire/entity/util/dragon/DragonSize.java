package com.iafenvoy.iceandfire.entity.util.dragon;

import java.util.List;
import net.minecraft.util.Mth;

public record DragonSize(float x0, float x1) {
   private static final List<DragonSize> GROWTH_STAGES = List.of(
      new DragonSize(1.0F, 3.0F), new DragonSize(3.0F, 7.0F), new DragonSize(7.0F, 12.5F), new DragonSize(12.5F, 20.0F), new DragonSize(20.0F, 30.0F)
   );

   public float step() {
      return this.x1 - this.x0;
   }

   public static DragonSize getSize(int stage) {
      return GROWTH_STAGES.get(Mth.clamp(stage - 1, 0, GROWTH_STAGES.size()));
   }
}
