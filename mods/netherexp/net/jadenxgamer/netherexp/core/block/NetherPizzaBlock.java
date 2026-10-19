package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherPizzaBlock extends Block {
   public static final IntegerProperty SLICES = IntegerProperty.create("slices", 1, 4);
   public static final VoxelShape SHAPE_4 = Block.box(-1.0, 0.0, -1.0, 17.0, 2.0, 17.0);
   public static final VoxelShape SHAPE_3 = Shapes.join(Block.box(-1.0, 0.0, 8.0, 8.0, 2.0, 17.0), Block.box(-1.0, 0.0, -1.0, 17.0, 2.0, 8.0), BooleanOp.OR);
   public static final VoxelShape SHAPE_2 = Block.box(-1.0, 0.0, -1.0, 17.0, 2.0, 8.0);
   public static final VoxelShape SHAPE_1 = Block.box(-1.0, 0.0, -1.0, 8.0, 2.0, 8.0);

   public NetherPizzaBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SLICES, 4));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(SLICES)) {
         case 1 -> SHAPE_1;
         case 2 -> SHAPE_2;
         case 3 -> SHAPE_3;
         default -> SHAPE_4;
      };
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!player.isSecondaryUseActive()) {
         int slices = (Integer)state.getValue(SLICES);
         if (player.addItem(new ItemStack((ItemLike)JNEItems.NETHER_PIZZA_SLICE.get()))) {
            if (slices > 1) {
               level.setBlock(pos, (BlockState)state.setValue(SLICES, slices - 1), 3);
            } else {
               level.removeBlock(pos, false);
               level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return (Integer)state.getValue(SLICES) * 3;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SLICES});
   }
}
