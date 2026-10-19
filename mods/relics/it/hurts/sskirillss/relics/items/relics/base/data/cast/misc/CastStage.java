package it.hurts.sskirillss.relics.items.relics.base.data.cast.misc;

import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public enum CastStage {
   START(0),
   TICK(1),
   END(2);

   public static final IntFunction<CastStage> BY_ID = ByIdMap.continuous(CastStage::getId, values(), OutOfBoundsStrategy.ZERO);
   private final int id;

   public int getId() {
      return this.id;
   }

   CastStage(final int id) {
      this.id = id;
   }
}
