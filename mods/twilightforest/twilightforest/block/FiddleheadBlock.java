package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FiddleheadBlock extends TFPlantBlock {
   public static final MapCodec<FiddleheadBlock> CODEC = simpleCodec(FiddleheadBlock::new);
   private static final VoxelShape FIDDLEHEAD_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 14.0, 13.0);

   public FiddleheadBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return FIDDLEHEAD_SHAPE;
   }
}
