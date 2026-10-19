package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.processors.BossRoomProtectionProcessor;
import com.chronodawn.worldgen.processors.CopyFluidLevelProcessor;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ModStructureProcessorTypes {
   public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(
      "chronodawn", Registries.STRUCTURE_PROCESSOR
   );
   public static final RegistrySupplier<StructureProcessorType<CopyFluidLevelProcessor>> COPY_FLUID_LEVEL = STRUCTURE_PROCESSOR_TYPES.register(
      "copy_fluid_level", () -> new StructureProcessorType<CopyFluidLevelProcessor>() {
         public MapCodec<CopyFluidLevelProcessor> codec() {
            return CopyFluidLevelProcessor.CODEC;
         }
      }
   );
   public static final RegistrySupplier<StructureProcessorType<BossRoomProtectionProcessor>> BOSS_ROOM_PROTECTION = STRUCTURE_PROCESSOR_TYPES.register(
      "boss_room_protection", () -> new StructureProcessorType<BossRoomProtectionProcessor>() {
         public MapCodec<BossRoomProtectionProcessor> codec() {
            return BossRoomProtectionProcessor.CODEC;
         }
      }
   );

   public static void register() {
      STRUCTURE_PROCESSOR_TYPES.register();
      ChronoDawn.LOGGER.debug("Registered ModStructureProcessorTypes");
   }
}
