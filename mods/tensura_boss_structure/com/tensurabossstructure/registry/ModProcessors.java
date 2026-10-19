package com.tensurabossstructure.registry;

import com.tensurabossstructure.TensuraBossStructure;
import com.tensurabossstructure.world.processor.GroundLevelProcessor;
import com.tensurabossstructure.world.processor.SmartGrassProcessor;
import com.tensurabossstructure.world.structure.WaterloggingFixProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModProcessors {
   public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(
      Registries.STRUCTURE_PROCESSOR, "tensura_boss_structure"
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<WaterloggingFixProcessor>> WATERLOGGING_FIX = PROCESSORS.register(
      "no_waterlog_processor", () -> () -> WaterloggingFixProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SmartGrassProcessor>> SMART_GRASS = PROCESSORS.register(
      "smart_grass", () -> () -> SmartGrassProcessor.CODEC
   );
   public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<GroundLevelProcessor>> GROUND_LEVEL = PROCESSORS.register(
      "ground_level", () -> () -> GroundLevelProcessor.CODEC
   );

   public static void init(IEventBus bus) {
      PROCESSORS.register(bus);
      TensuraBossStructure.LOGGER.info("Structure processors registered!");
   }
}
