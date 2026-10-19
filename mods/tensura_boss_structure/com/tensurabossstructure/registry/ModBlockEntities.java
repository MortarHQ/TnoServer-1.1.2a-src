package com.tensurabossstructure.registry;

import com.tensurabossstructure.TensuraBossStructure;
import com.tensurabossstructure.block.entity.BossSpawnerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "tensura_boss_structure");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossSpawnerBlockEntity>> BOSS_SPAWNER = BLOCK_ENTITIES.register(
      "boss_spawner",
      () -> Builder.of(
            BossSpawnerBlockEntity::new,
            new Block[]{
               (Block)ModBlocks.RIMURU_OGRE_FIGHT_SPAWNER.get(),
               (Block)ModBlocks.HINATA_SPAWNER.get(),
               (Block)ModBlocks.ORC_DISASTER_SPAWNER.get(),
               (Block)ModBlocks.LUMINOUS_SPAWNER.get(),
               (Block)ModBlocks.CARRION_SPAWNER.get(),
               (Block)ModBlocks.ELEMENTAL_COLOSSUS_SPAWNER.get(),
               (Block)ModBlocks.OTHERWORLDER_SPAWNER.get(),
               (Block)ModBlocks.SHIZU_SPAWNER.get(),
               (Block)ModBlocks.CHILD_OTHERWORLDER_SPAWNER.get()
            }
         )
         .build(null)
   );

   public static void init(IEventBus bus) {
      BLOCK_ENTITIES.register(bus);
      TensuraBossStructure.LOGGER.info("Block Entities registered!");
   }
}
