package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.item.block.util.DragonProof;
import com.iafenvoy.iceandfire.item.block.util.DreadBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;

public class DreadBaseBlock extends GenericBlock implements DragonProof, DreadBlock {
   public DreadBaseBlock(boolean plank) {
      super(Properties.ofFullCopy(plank ? Blocks.OAK_PLANKS : Blocks.STONE));
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(UNBREAKABLE, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{UNBREAKABLE});
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
      return state.getValue(UNBREAKABLE) ? 0.0F : super.getDestroyProgress(state, player, world, pos);
   }
}
