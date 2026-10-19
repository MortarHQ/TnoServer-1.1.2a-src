package com.github.L_Ender.cataclysm.world.structures.action;

import com.github.L_Ender.cataclysm.structures.jisaw.PieceEntry;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;

public abstract class StructureAction {
   public abstract StructureActionType<?> type();

   public abstract void apply(StructureContext var1, PieceEntry var2);
}
