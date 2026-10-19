package twilightforest.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import twilightforest.init.TFItems;
import twilightforest.init.TFStats;

public class Experiment115Block extends Block {
   public static final IntegerProperty BITES_TAKEN = IntegerProperty.create("omnomnom", 0, 7);
   public static final BooleanProperty REGENERATE = BooleanProperty.create("regenerate");
   private static final VoxelShape QUARTER_SHAPE = box(1.0, 0.0, 1.0, 8.0, 8.0, 8.0);
   private static final VoxelShape HALF_SHAPE = box(1.0, 0.0, 1.0, 8.0, 8.0, 15.0);
   private static final VoxelShape THREE_QUARTER_SHAPE = Shapes.join(HALF_SHAPE, box(8.0, 0.0, 8.0, 15.0, 8.0, 15.0), BooleanOp.OR);
   private static final VoxelShape FULL_SHAPE = box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0);

   public Experiment115Block(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(BITES_TAKEN, 7)).setValue(REGENERATE, false));
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(BITES_TAKEN)) {
         case 2, 3 -> THREE_QUARTER_SHAPE;
         case 4, 5 -> HALF_SHAPE;
         case 6, 7 -> QUARTER_SHAPE;
         default -> FULL_SHAPE;
      };
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      int bitesTaken = (Integer)state.getValue(BITES_TAKEN);
      if (!player.isSecondaryUseActive()) {
         if (stack.is((Item)TFItems.EXPERIMENT_115.get())) {
            if (bitesTaken == 0) {
               return ItemInteractionResult.FAIL;
            }

            level.setBlockAndUpdate(pos, (BlockState)state.setValue(BITES_TAKEN, bitesTaken - 1));
            level.playSound(null, pos, state.getSoundType(level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.consume(1, player);
            if (player instanceof ServerPlayer) {
               CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, pos, stack);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         if (!(Boolean)state.getValue(REGENERATE) && bitesTaken == 0 && stack.is(Items.REDSTONE)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(REGENERATE, true));
            level.playSound(null, pos, state.getSoundType(level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            stack.consume(1, player);
            if (player instanceof ServerPlayer) {
               player.awardStat(Stats.ITEM_USED.get(Items.REDSTONE));
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (!(Boolean)state.getValue(REGENERATE)) {
         if (bitesTaken < 7) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(BITES_TAKEN, bitesTaken + 1));
         } else {
            level.removeBlock(pos, false);
         }

         player.playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.0F);
         if (!player.isCreative()) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack((ItemLike)TFItems.EXPERIMENT_115.get()));
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (!player.canEat(false)) {
         return InteractionResult.PASS;
      }

      player.awardStat((ResourceLocation)TFStats.E115_SLICES_EATEN.get());
      player.getFoodData().eat(4, 0.3F);
      level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
      int i = (Integer)state.getValue(BITES_TAKEN);
      if (i < 7) {
         level.setBlock(pos, (BlockState)state.setValue(BITES_TAKEN, i + 1), 3);
      } else {
         level.removeBlock(pos, false);
      }

      if (player instanceof ServerPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, new ItemStack((ItemLike)TFItems.EXPERIMENT_115.get(), 8 - i));
         player.awardStat(Stats.ITEM_USED.get((Item)TFItems.EXPERIMENT_115.get()));
      }

      return InteractionResult.sidedSuccess(level.isClientSide());
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(REGENERATE) && (Integer)state.getValue(BITES_TAKEN) != 0) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(BITES_TAKEN, (Integer)state.getValue(BITES_TAKEN) - 1));
      }
   }

   public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
      return reader.getBlockState(pos.below()).isSolid();
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !state.canSurvive(level, currentPos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BITES_TAKEN, REGENERATE});
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return 8 - (Integer)state.getValue(BITES_TAKEN) + (state.getValue(REGENERATE) ? 7 : 0);
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public boolean isSignalSource(BlockState state) {
      return (Boolean)state.getValue(REGENERATE);
   }

   public int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return state.getValue(REGENERATE) ? 15 - (Integer)state.getValue(BITES_TAKEN) * 2 : 0;
   }
}
