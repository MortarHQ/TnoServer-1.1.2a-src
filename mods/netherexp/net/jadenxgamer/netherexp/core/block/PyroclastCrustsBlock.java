package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PyroclastCrustsBlock extends Block {
   public static final IntegerProperty CRUSTS = IntegerProperty.create("crusts", 1, 6);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape FULL_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 0.0, 10.0, 1.0, 10.0);
   private static final VoxelShape EAST_SHAPE = Block.box(6.0, 0.0, 0.0, 16.0, 1.0, 10.0);
   private static final VoxelShape SOUTH_SHAPE = Block.box(6.0, 0.0, 6.0, 16.0, 1.0, 16.0);
   private static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 6.0, 10.0, 1.0, 16.0);

   public PyroclastCrustsBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.defaultBlockState().setValue(CRUSTS, 1)).setValue(FACING, Direction.NORTH));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (!player.isSecondaryUseActive()) {
         int crusts = (Integer)state.getValue(CRUSTS);
         if (crusts < 6 && stack.is(this.asItem())) {
            level.playSound(player, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            level.setBlock(pos, (BlockState)state.cycle(CRUSTS), 3);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         if (stack.isEmpty()) {
            if (player.getAbilities().instabuild) {
               this.consumeCrust(level, pos, state, player, crusts);
               return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            if (player.addItem(new ItemStack(this.asItem()))) {
               this.consumeCrust(level, pos, state, player, crusts);
               return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            return ItemInteractionResult.FAIL;
         }
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   private void consumeCrust(Level level, BlockPos pos, BlockState state, Player player, int crusts) {
      if (crusts == 1) {
         level.removeBlock(pos, false);
         level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
      } else {
         level.setBlock(pos, (BlockState)state.setValue(CRUSTS, crusts - 1), 3);
         level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
      }

      level.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if ((Integer)state.getValue(CRUSTS) > 3) {
         return FULL_SHAPE;
      }

      return switch ((Direction)state.getValue(FACING)) {
         case EAST -> EAST_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         default -> NORTH_SHAPE;
      };
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return level.getFluidState(pos.below()).getType() == Fluids.LAVA;
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if (living.getType().is(JNETags.EntityTypes.CANT_BREAK_CRUSTS)) {
            return;
         }

         level.scheduleTick(pos, JNEBlocks.PYROCLAST_CRUSTS.get(), 20);
      }

      super.entityInside(state, level, pos, entity);
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      level.destroyBlock(pos, false);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{CRUSTS, FACING});
   }
}
