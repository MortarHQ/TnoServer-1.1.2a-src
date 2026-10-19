package twilightforest.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFDataMaps;
import twilightforest.util.datamaps.CrumbledBlock;

public class CrumbleDispenseBehavior extends DefaultDispenseItemBehavior {
   boolean fired = false;

   protected ItemStack execute(BlockSource source, ItemStack stack) {
      ServerLevel level = source.level();
      BlockPos pos = source.pos().relative((Direction)source.state().getValue(DispenserBlock.FACING));
      BlockState state = level.getBlockState(pos);
      if (stack.getMaxDamage() != stack.getDamageValue() + 1) {
         CrumbledBlock resultBlock = (CrumbledBlock)state.getBlock().builtInRegistryHolder().getData(TFDataMaps.CRUMBLE_HORN);
         if (resultBlock != null) {
            if (resultBlock.result() == Blocks.AIR) {
               level.destroyBlock(pos, true);
            } else {
               level.setBlock(pos, resultBlock.result().withPropertiesOf(state), 3);
               level.levelEvent(2001, pos, Block.getId(state));
            }

            stack.hurtAndBreak(1, level, null, item -> {});
            this.fired = true;
         }
      }

      return stack;
   }

   protected void playSound(BlockSource source) {
      if (this.fired) {
         super.playSound(source);
         this.fired = false;
      } else {
         source.level().levelEvent(1001, source.pos(), 0);
      }
   }
}
