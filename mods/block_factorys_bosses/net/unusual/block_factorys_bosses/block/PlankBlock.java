package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractStackableBlock;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlankBlock extends AbstractStackableBlock implements EntityBlock {
   public static final int MIN_STACK = 1;
   public static final int MAX_STACK = 5;
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final IntegerProperty PLANKS = IntegerProperty.create("planks", 1, 5);
   private static final VoxelShape FLOOR = box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);

   public PlankBlock() {
      super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 5.0F).noOcclusion().noCollission().isRedstoneConductor((bs, br, bp) -> false), PLANKS, 1, 5);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(PLANKS, 1));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PLANKS});
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      if (!this.canSurvive(this.defaultBlockState(), context.getLevel(), context.getClickedPos())) {
         return null;
      }

      BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
      return blockstate.is(this)
         ? (BlockState)blockstate.setValue(this.stackableProperty, Math.min(this.maxStack, (Integer)blockstate.getValue(this.stackableProperty) + 1))
         : (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return FLOOR;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
      return 0;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PlankBlockEntity(pos, state);
   }
}
