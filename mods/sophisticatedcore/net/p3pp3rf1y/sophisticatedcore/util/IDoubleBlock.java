package net.p3pp3rf1y.sophisticatedcore.util;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IDoubleBlock {
   Optional<BlockPos> getOtherPosition(BlockState var1, BlockPos var2);
}
