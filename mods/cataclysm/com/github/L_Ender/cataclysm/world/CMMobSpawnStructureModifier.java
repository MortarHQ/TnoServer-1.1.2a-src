package com.github.L_Ender.cataclysm.world;

import com.github.L_Ender.cataclysm.init.ModStructureModifiers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.common.world.ModifiableStructureInfo.StructureInfo.Builder;
import net.neoforged.neoforge.common.world.StructureModifier.Phase;

public class CMMobSpawnStructureModifier implements StructureModifier {
   public static final CMMobSpawnStructureModifier INSTANCE = new CMMobSpawnStructureModifier();

   public void modify(Holder<Structure> structure, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         CMWorldRegistry.modifyStructure(structure, builder);
      }
   }

   public MapCodec<? extends StructureModifier> codec() {
      return (MapCodec<? extends StructureModifier>)ModStructureModifiers.ADD_SPAWNS_STRUCTURE_MODIFIER_TYPE.get();
   }
}
