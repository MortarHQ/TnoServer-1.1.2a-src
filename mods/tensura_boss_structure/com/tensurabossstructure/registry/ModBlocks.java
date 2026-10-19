package com.tensurabossstructure.registry;

import com.tensurabossstructure.TensuraBossStructure;
import com.tensurabossstructure.block.BossSpawnerBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "tensura_boss_structure");
   public static final DeferredHolder<Block, Block> RIMURU_OGRE_FIGHT_SPAWNER = BLOCKS.register(
      "rimuru_ogre_fight_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura_neb:rimuru_ogre_fight"
      )
   );
   public static final DeferredHolder<Block, Block> HINATA_SPAWNER = BLOCKS.register(
      "hinata_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura:hinata_sakaguchi"
      )
   );
   public static final DeferredHolder<Block, Block> ORC_DISASTER_SPAWNER = BLOCKS.register(
      "orc_disaster_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura:orc_disaster"
      )
   );
   public static final DeferredHolder<Block, Block> LUMINOUS_SPAWNER = BLOCKS.register(
      "luminous_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura_neb:luminous_valentine"
      )
   );
   public static final DeferredHolder<Block, Block> CARRION_SPAWNER = BLOCKS.register(
      "carrion_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura_neb:carrion"
      )
   );
   public static final DeferredHolder<Block, Block> ELEMENTAL_COLOSSUS_SPAWNER = BLOCKS.register(
      "elemental_colossus_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura:elemental_colossus"
      )
   );
   public static final DeferredHolder<Block, Block> OTHERWORLDER_SPAWNER = BLOCKS.register(
      "otherworlder_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "random_otherworlder"
      )
   );
   public static final DeferredHolder<Block, Block> SHIZU_SPAWNER = BLOCKS.register(
      "shizu_spawner",
      () -> new BossSpawnerBlock(Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "tensura:shizu")
   );
   public static final DeferredHolder<Block, Block> CHILD_OTHERWORLDER_SPAWNER = BLOCKS.register(
      "child_otherworlder_spawner",
      () -> new BossSpawnerBlock(
         Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable(), "random_child_otherworlder"
      )
   );

   public static void init(IEventBus bus) {
      BLOCKS.register(bus);
      TensuraBossStructure.LOGGER.info("Blocks registered!");
   }
}
