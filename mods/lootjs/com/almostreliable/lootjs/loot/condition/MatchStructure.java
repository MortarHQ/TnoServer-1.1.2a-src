package com.almostreliable.lootjs.loot.condition;

import com.almostreliable.lootjs.LootJSConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

public record MatchStructure(HolderSet<Structure> structures, boolean exact) implements LootItemCondition {
   public boolean test(LootContext context) {
      Vec3 origin = (Vec3)context.getParamOrNull(LootContextParams.ORIGIN);
      if (origin == null) {
         return false;
      }

      BlockPos blockPos = new BlockPos((int)origin.x, (int)origin.y, (int)origin.z);
      StructureManager structureManager = context.getLevel().structureManager();
      if (this.exact) {
         StructureStart start = structureManager.getStructureWithPieceAt(blockPos, this.structures);
         return start.isValid();
      }

      for (Holder<Structure> structure : this.structures) {
         StructureStart start = structureManager.getStructureAt(blockPos, (Structure)structure.value());
         if (start.isValid()) {
            return true;
         }
      }

      return false;
   }

   public LootItemConditionType getType() {
      return (LootItemConditionType)LootJSConditions.ANY_STRUCTURE.value();
   }
}
