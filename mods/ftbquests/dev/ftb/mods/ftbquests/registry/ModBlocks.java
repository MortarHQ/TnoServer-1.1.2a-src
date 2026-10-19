package dev.ftb.mods.ftbquests.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.ftb.mods.ftbquests.block.DetectorBlock;
import dev.ftb.mods.ftbquests.block.LootCrateOpenerBlock;
import dev.ftb.mods.ftbquests.block.QuestBarrierBlock;
import dev.ftb.mods.ftbquests.block.StageBarrierBlock;
import dev.ftb.mods.ftbquests.block.TaskScreenBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public class ModBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("ftbquests", Registries.BLOCK);
   public static final RegistrySupplier<Block> BARRIER = BLOCKS.register("barrier", () -> new QuestBarrierBlock(QuestBarrierBlock.PROPS));
   public static final RegistrySupplier<Block> STAGE_BARRIER = BLOCKS.register("stage_barrier", () -> new StageBarrierBlock(QuestBarrierBlock.PROPS));
   public static final RegistrySupplier<Block> DETECTOR = BLOCKS.register("detector", () -> new DetectorBlock(DetectorBlock.PROPS));
   public static final RegistrySupplier<Block> LOOT_CRATE_OPENER = BLOCKS.register(
      "loot_crate_opener", () -> new LootCrateOpenerBlock(LootCrateOpenerBlock.PROPS)
   );
   public static final RegistrySupplier<Block> TASK_SCREEN_1 = BLOCKS.register("screen_1", () -> new TaskScreenBlock(TaskScreenBlock.PROPS, 1));
   public static final RegistrySupplier<Block> TASK_SCREEN_3 = BLOCKS.register("screen_3", () -> new TaskScreenBlock(TaskScreenBlock.PROPS, 3));
   public static final RegistrySupplier<Block> TASK_SCREEN_5 = BLOCKS.register("screen_5", () -> new TaskScreenBlock(TaskScreenBlock.PROPS, 5));
   public static final RegistrySupplier<Block> TASK_SCREEN_7 = BLOCKS.register("screen_7", () -> new TaskScreenBlock(TaskScreenBlock.PROPS, 7));
   public static final RegistrySupplier<Block> AUX_SCREEN = BLOCKS.register("aux_task_screen", () -> new TaskScreenBlock.Aux(TaskScreenBlock.PROPS));

   public static void register() {
      BLOCKS.register();
   }
}
