package com.benji.netherman.init;

import com.benji.netherman.common.worldgen.structure.MegaJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureTypes {
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, "netherman");
   public static final DeferredHolder<StructureType<?>, StructureType<MegaJigsawStructure>> MEGA_JIGSAW = STRUCTURE_TYPES.register(
      "mega_jigsaw", () -> () -> MegaJigsawStructure.CODEC
   );

   public static void init(IEventBus bus) {
      STRUCTURE_TYPES.register(bus);
   }
}
