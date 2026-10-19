package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GuardrailBlock extends FenceBlock {
   public GuardrailBlock() {
      super(Properties.of().destroyTime(2.5F).ignitedByLava().explosionResistance(2.5F).dynamicShape().sound(SoundType.WOOD));
   }

   public boolean connectsTo(BlockState state, boolean isSturdy, Direction dir) {
      return state.getBlock() instanceof GuardrailDiagonalBlock && state.getValue(GuardrailDiagonalBlock.FACING) == dir
         ? true
         : state.getBlock() instanceof GuardrailBlock || isSturdy;
   }
}
