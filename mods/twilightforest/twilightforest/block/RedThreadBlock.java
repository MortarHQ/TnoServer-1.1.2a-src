package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import twilightforest.block.entity.RedThreadBlockEntity;
import twilightforest.init.TFBlocks;

public class RedThreadBlock extends MultifaceBlock implements EntityBlock {
   public static final MapCodec<RedThreadBlock> CODEC = simpleCodec(RedThreadBlock::new);

   public RedThreadBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends MultifaceBlock> codec() {
      return CODEC;
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
      return ctx.getItemInHand().is(TFBlocks.RED_THREAD.asItem());
   }

   public MultifaceSpreader getSpreader() {
      return new MultifaceSpreader(this);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new RedThreadBlockEntity(pos, state);
   }
}
