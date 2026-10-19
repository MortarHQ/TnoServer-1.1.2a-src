package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MayappleBlock extends TFPlantBlock {
   public static final MapCodec<MayappleBlock> CODEC = simpleCodec(MayappleBlock::new);
   private static final VoxelShape MAYAPPLE_SHAPE = box(4.0, 0.0, 4.0, 13.0, 6.0, 13.0);

   public MayappleBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return MAYAPPLE_SHAPE;
   }
}
