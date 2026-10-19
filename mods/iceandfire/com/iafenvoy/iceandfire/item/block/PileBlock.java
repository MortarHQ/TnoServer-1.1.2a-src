package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.registry.IafSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PileBlock extends Block {
   public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 8);
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{
      Shapes.empty(),
      Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   public PileBlock() {
      super(
         Properties.of()
            .mapColor(MapColor.DIRT)
            .strength(0.3F, 1.0F)
            .randomTicks()
            .sound(
               new SoundType(
                  1.0F,
                  1.0F,
                  (SoundEvent)IafSounds.GOLD_PILE_BREAK.get(),
                  (SoundEvent)IafSounds.GOLD_PILE_STEP.get(),
                  (SoundEvent)IafSounds.GOLD_PILE_BREAK.get(),
                  (SoundEvent)IafSounds.GOLD_PILE_STEP.get(),
                  (SoundEvent)IafSounds.GOLD_PILE_STEP.get()
               )
            )
            .pushReaction(PushReaction.DESTROY)
      );
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(LAYERS, 1));
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return type == PathComputationType.LAND && (Integer)state.getValue(LAYERS) < 5;
   }

   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPES[state.getValue(LAYERS)];
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPES[state.getValue(LAYERS) - 1];
   }

   public boolean useShapeForLightOcclusion(BlockState state) {
      return true;
   }

   public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
      BlockState blockstate = worldIn.getBlockState(pos.below());
      Block block = blockstate.getBlock();
      if (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.BARRIER) {
         return false;
      } else {
         return block != Blocks.HONEY_BLOCK && block != Blocks.SOUL_SAND
            ? Block.isFaceFull(blockstate.getCollisionShape(worldIn, pos.below()), Direction.UP)
               || block instanceof PileBlock && (Integer)blockstate.getValue(LAYERS) == 8
            : true;
      }
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      return !stateIn.canSurvive(worldIn, currentPos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
      if (blockstate.getBlock() == this) {
         int i = (Integer)blockstate.getValue(LAYERS);
         return (BlockState)blockstate.setValue(LAYERS, Math.min(8, i + 1));
      } else {
         return super.getStateForPlacement(context);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LAYERS});
   }

   protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      ItemStack item = player.getInventory().getSelected();
      if (!item.isEmpty() && item.getItem() != null && item.getItem() == this.asItem() && (Integer)state.getValue(LAYERS) < 8) {
         world.setBlock(pos, (BlockState)state.setValue(LAYERS, (Integer)state.getValue(LAYERS) + 1), 3);
         if (!player.isCreative()) {
            item.shrink(1);
            Inventory inventory = player.getInventory();
            if (item.isEmpty()) {
               inventory.setItem(inventory.selected, ItemStack.EMPTY);
            } else {
               inventory.setItem(inventory.selected, item);
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
