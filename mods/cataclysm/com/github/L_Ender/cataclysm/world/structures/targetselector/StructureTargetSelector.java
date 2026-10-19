package com.github.L_Ender.cataclysm.world.structures.targetselector;

import com.github.L_Ender.cataclysm.structures.jisaw.PieceEntry;
import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;
import java.util.List;

public abstract class StructureTargetSelector {
   public abstract StructureTargetSelectorType<?> type();

   public abstract List<PieceEntry> apply(StructureContext var1);
}
