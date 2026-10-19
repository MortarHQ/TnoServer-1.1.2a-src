package it.hurts.sskirillss.relics.items.relics.base.data.cast.misc;

import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;

public enum CastType {
   NONE(0),
   INSTANTANEOUS(1),
   INTERRUPTIBLE(2),
   CYCLICAL(3),
   TOGGLEABLE(4),
   CHARGEABLE(5);

   public static final IntFunction<CastType> BY_ID = ByIdMap.continuous(CastType::getId, values(), OutOfBoundsStrategy.ZERO);
   private final int id;

   public int getId() {
      return this.id;
   }

   CastType(final int id) {
      this.id = id;
   }
}
