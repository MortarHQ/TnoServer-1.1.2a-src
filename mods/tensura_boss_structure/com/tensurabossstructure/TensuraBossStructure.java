package com.tensurabossstructure;

import com.tensurabossstructure.config.BossConfig;
import com.tensurabossstructure.init.ModStructures;
import com.tensurabossstructure.registry.ModBlockEntities;
import com.tensurabossstructure.registry.ModBlocks;
import com.tensurabossstructure.registry.ModItems;
import com.tensurabossstructure.registry.ModLootModifiers;
import com.tensurabossstructure.registry.ModProcessors;
import com.tensurabossstructure.registry.ModStructureTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("tensura_boss_structure")
public class TensuraBossStructure {
   public static final String MOD_ID = "tensura_boss_structure";
   public static final Logger LOGGER = LoggerFactory.getLogger("tensura_boss_structure");

   public TensuraBossStructure(IEventBus modEventBus, ModContainer modContainer) {
      LOGGER.info("=== Tensura Boss Structure mod starting initialization ===");
      LOGGER.info("=== Mixin should be loaded by now ===");
      modContainer.registerConfig(Type.COMMON, BossConfig.SPEC);
      ModBlocks.init(modEventBus);
      ModBlockEntities.init(modEventBus);
      ModItems.init(modEventBus);
      ModStructureTypes.init(modEventBus);
      ModProcessors.init(modEventBus);
      ModLootModifiers.init(modEventBus);
      ModStructures.register();
      LOGGER.info("Tensura Boss Structure mod initialized!");
   }
}
