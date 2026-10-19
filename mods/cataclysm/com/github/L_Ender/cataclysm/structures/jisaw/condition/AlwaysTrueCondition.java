package com.github.L_Ender.cataclysm.structures.jisaw.condition;

import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import com.mojang.serialization.MapCodec;

public class AlwaysTrueCondition extends StructureCondition {
   private static final AlwaysTrueCondition INSTANCE = new AlwaysTrueCondition();
   public static final MapCodec<AlwaysTrueCondition> CODEC = MapCodec.unit(() -> INSTANCE);

   @Override
   public StructureConditionType<?> type() {
      return StructureConditionType.ALWAYS_TRUE;
   }

   @Override
   public boolean passes(StructureContext ctx) {
      return true;
   }
}
