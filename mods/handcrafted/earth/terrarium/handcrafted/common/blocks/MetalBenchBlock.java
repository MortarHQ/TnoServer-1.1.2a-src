package earth.terrarium.handcrafted.common.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.ModularSeatBlock;
import earth.terrarium.handcrafted.common.blocks.base.properties.ModularSeatProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MetalBenchBlock extends ModularSeatBlock {
   public static final MapCodec<MetalBenchBlock> CODEC = simpleCodec(MetalBenchBlock::new);
   public static final AABB SEAT = new AABB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
   public static final VoxelShape VOXEL_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0);

   public MetalBenchBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, ModularSeatProperty.SINGLE)).setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return VOXEL_SHAPE;
   }

   @Override
   public AABB getSeatSize(BlockState state) {
      return SEAT;
   }
}
