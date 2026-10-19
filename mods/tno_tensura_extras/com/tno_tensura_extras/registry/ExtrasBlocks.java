package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.block.CargoInTransitBlock;
import com.tno_tensura_extras.block.entity.CargoInTransitBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class ExtrasBlocks {
   public static final Blocks BLOCKS = DeferredRegister.createBlocks("tno_tensura_extras");
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "tno_tensura_extras");
   public static final Supplier<CargoInTransitBlock> CARGO_IN_TRANSIT = BLOCKS.register(
      "cargo_in_transit",
      () -> new CargoInTransitBlock(
         Properties.of()
            .mapColor(MapColor.NONE)
            .noCollission()
            .noOcclusion()
            .noLootTable()
            .noTerrainParticles()
            .strength(-1.0F, 3600000.8F)
            .pushReaction(PushReaction.BLOCK)
      )
   );
   public static final Supplier<BlockEntityType<CargoInTransitBlockEntity>> CARGO_IN_TRANSIT_ENTITY = BLOCK_ENTITY_TYPES.register(
      "cargo_in_transit", () -> Builder.of(CargoInTransitBlockEntity::new, new Block[]{(Block)CARGO_IN_TRANSIT.get()}).build(null)
   );
}
