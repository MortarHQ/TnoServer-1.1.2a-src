package net.aurelj.dungeons_arise;

import com.mojang.serialization.MapCodec;
import net.aurelj.dungeons_arise.structures.WDAGenericStructures;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WDAStructures {
   public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, "dungeons_arise");
   public static final DeferredHolder<StructureType<?>, StructureType<WDAGenericStructures>> GENERIC_STRUCTURES = DEFERRED_REGISTRY_STRUCTURE.register(
      "generic_structures", () -> explicitStructureTypeTyping(WDAGenericStructures.CODEC)
   );

   private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
      return () -> structureCodec;
   }
}
