package com.tensura_tno.registry;

import com.tensura_tno.block.entity.RimuruPlushBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TensuraTNOBlockEntities {
   private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "tensura_tno");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RimuruPlushBlockEntity>> RIMURU_PLUSH = BLOCK_ENTITIES.register(
      "rimuru_plush", () -> Builder.of(RimuruPlushBlockEntity::new, new Block[]{(Block)TensuraTNOBlocks.RIMURU_PLUSH.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RimuruPlushBlockEntity>> LUMINOUS_PLUSH = BLOCK_ENTITIES.register(
      "luminous_plush", () -> Builder.of(TensuraTNOBlockEntities::createLuminous, new Block[]{(Block)TensuraTNOBlocks.LUMINOUS_PLUSH.get()}).build(null)
   );

   private static RimuruPlushBlockEntity createLuminous(BlockPos pos, BlockState state) {
      return new RimuruPlushBlockEntity((BlockEntityType<?>)LUMINOUS_PLUSH.get(), pos, state, "animation.luminous.");
   }

   private TensuraTNOBlockEntities() {
   }

   public static void register(IEventBus modBus) {
      BLOCK_ENTITIES.register(modBus);
   }
}
