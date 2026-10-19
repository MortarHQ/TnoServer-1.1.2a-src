package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.test.FDTestBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "fdlib");
   public static final Supplier<BlockEntityType<FDTestBlockEntity>> TEST = BLOCK_ENTITIES.register(
      "test", () -> Builder.of(FDTestBlockEntity::new, new Block[]{FDBlocks.TEST.get()}).build(null)
   );
}
