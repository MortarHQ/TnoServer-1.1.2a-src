package com.github.L_Ender.cataclysm.structures.jisaw.condition;

import com.github.L_Ender.cataclysm.structures.jisaw.context.StructureContext;

public abstract class StructureCondition {
   public static final StructureCondition ALWAYS_TRUE = new AlwaysTrueCondition();

   public abstract StructureConditionType<?> type();

   public abstract boolean passes(StructureContext var1);
}
