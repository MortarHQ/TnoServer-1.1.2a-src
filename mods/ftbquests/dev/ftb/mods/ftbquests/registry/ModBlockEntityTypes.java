package dev.ftb.mods.ftbquests.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.ftb.mods.ftbquests.block.LootCrateOpenerBlock;
import dev.ftb.mods.ftbquests.block.TaskScreenBlock;
import dev.ftb.mods.ftbquests.block.entity.DetectorBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.LootCrateOpenerBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.QuestBarrierBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.StageBarrierBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenAuxBlockEntity;
import dev.ftb.mods.ftbquests.block.entity.TaskScreenBlockEntity;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModBlockEntityTypes {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create("ftbquests", Registries.BLOCK_ENTITY_TYPE);
   public static final RegistrySupplier<BlockEntityType<QuestBarrierBlockEntity>> BARRIER = register("barrier", QuestBarrierBlockEntity::new, ModBlocks.BARRIER);
   public static final RegistrySupplier<BlockEntityType<StageBarrierBlockEntity>> STAGE_BARRIER = register(
      "stage_barrier", StageBarrierBlockEntity::new, ModBlocks.STAGE_BARRIER
   );
   public static final RegistrySupplier<BlockEntityType<DetectorBlockEntity>> DETECTOR = register("detector", DetectorBlockEntity::new, ModBlocks.DETECTOR);
   public static final RegistrySupplier<BlockEntityType<LootCrateOpenerBlockEntity>> LOOT_CRATE_OPENER = register(
      "loot_crate_opener", LootCrateOpenerBlock.blockEntityProvider(), ModBlocks.LOOT_CRATE_OPENER
   );
   public static final RegistrySupplier<BlockEntityType<TaskScreenBlockEntity>> CORE_TASK_SCREEN = register(
      "core_task_screen",
      TaskScreenBlock.blockEntityProvider(),
      Set.of(ModBlocks.TASK_SCREEN_1, ModBlocks.TASK_SCREEN_3, ModBlocks.TASK_SCREEN_5, ModBlocks.TASK_SCREEN_7)
   );
   public static final RegistrySupplier<BlockEntityType<TaskScreenAuxBlockEntity>> AUX_TASK_SCREEN = register(
      "aux_task_screen", TaskScreenBlock.blockEntityAuxProvider(), ModBlocks.AUX_SCREEN
   );

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(
      String id, BlockEntitySupplier<T> factory, Collection<RegistrySupplier<Block>> blocks
   ) {
      return BLOCK_ENTITIES.register(id, () -> Builder.of(factory, blocks.stream().map(Supplier::get).toArray(Block[]::new)).build(null));
   }

   public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String id, BlockEntitySupplier<T> factory, Supplier<Block> block) {
      return BLOCK_ENTITIES.register(id, () -> Builder.of(factory, new Block[]{block.get()}).build(null));
   }

   public static void register() {
      BLOCK_ENTITIES.register();
   }
}
