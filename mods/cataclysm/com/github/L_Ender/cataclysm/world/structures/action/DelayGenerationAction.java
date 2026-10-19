package com.github.L_Ender.cataclysm.world.structures.action;

import com.github.L_Ender.cataclysm.structures.jisaw.PieceEntry;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import com.mojang.serialization.MapCodec;

public class DelayGenerationAction extends StructureAction {
   private static final DelayGenerationAction INSTANCE = new DelayGenerationAction();
   public static final MapCodec<DelayGenerationAction> CODEC = MapCodec.unit(() -> INSTANCE);

   @Override
   public StructureActionType<?> type() {
      return StructureActionType.DELAY_GENERATION;
   }

   @Override
   public void apply(StructureContext ctx, PieceEntry targetPieceEntry) {
      targetPieceEntry.setDelayGeneration(true);
   }
}
