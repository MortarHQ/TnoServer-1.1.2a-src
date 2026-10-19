package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.structures.jisaw.element.CataclysmJigsawSinglePoolElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModJigsaw {
   public static final DeferredRegister<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT = DeferredRegister.create(
      BuiltInRegistries.STRUCTURE_POOL_ELEMENT, "cataclysm"
   );
   public static final DeferredHolder<StructurePoolElementType<?>, StructurePoolElementType<CataclysmJigsawSinglePoolElement>> CATACLYSM_ELEMENT = STRUCTURE_POOL_ELEMENT.register(
      "cataclysm_element", () -> () -> CataclysmJigsawSinglePoolElement.CODEC
   );
}
