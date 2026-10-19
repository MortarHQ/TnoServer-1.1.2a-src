package net.witherspawnanimation.init;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;
import net.witherspawnanimation.block.NoWBlock;

public class WitherSpawnAnimationModBlocks {
   public static final Blocks REGISTRY = DeferredRegister.createBlocks("wither_spawn_animation");
   public static final DeferredBlock<Block> NO_W = REGISTRY.register("no_w", NoWBlock::new);
}
