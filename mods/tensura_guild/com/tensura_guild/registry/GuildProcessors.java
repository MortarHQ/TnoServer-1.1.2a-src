package com.tensura_guild.registry;

import com.tensura_guild.TensuraGuild;
import com.tensura_guild.world.processor.GroundLevelProcessor;
import com.tensura_guild.world.processor.SmartGrassProcessor;
import com.tensura_guild.world.structure.WaterloggingFixProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildProcessors {
   public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, "tensura_guild");
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
      TensuraGuild.LOGGER.info("Structure processors registered!");
   }
}
