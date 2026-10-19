package net.jadenxgamer.elysium_api.impl.mixin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockBehaviour.class)
public interface BlockAccessor {
   @Invoker("canSurvive")
   boolean elysium_api$canSurvive(BlockState var1, LevelReader var2, BlockPos var3);
}
