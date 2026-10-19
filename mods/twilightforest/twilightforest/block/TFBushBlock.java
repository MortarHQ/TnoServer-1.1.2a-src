package twilightforest.block;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public abstract class TFBushBlock extends Block implements SnowLoggable {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   public static final int MAX_AGE = 3;
   private final ResourceKey<LootTable> berryLoot;
   private static final VoxelShape SMALL_SNOWY_BUSH_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);
   private static final VoxelShape MEDIUM_SNOWY_BUSH_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);
   private static final VoxelShape[] SMALL_BUSH_SHAPES = (VoxelShape[])Util.make(new VoxelShape[9], shapes -> {
      shapes[0] = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

      for (int i = 1; i <= 8; i++) {
         shapes[i] = Shapes.or(SMALL_SNOWY_BUSH_SHAPE, SNOW_SHAPE_BY_LAYER[i]);
      }
   });
   private static final VoxelShape[] MEDIUM_BUSH_SHAPES = (VoxelShape[])Util.make(new VoxelShape[9], shapes -> {
      shapes[0] = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

      for (int i = 1; i <= 8; i++) {
         shapes[i] = Shapes.or(MEDIUM_SNOWY_BUSH_SHAPE, SNOW_SHAPE_BY_LAYER[i]);
      }
   });

   public TFBushBlock(ResourceKey<LootTable> berryLoot, Properties properties) {
      super(properties);
      this.berryLoot = berryLoot;
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(AGE, 0)).setValue(SNOW_LAYERS, 0));
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState maybeSnow = context.getLevel().getBlockState(context.getClickedPos());
      return maybeSnow.is(Blocks.SNOW) && maybeSnow.getValue(SnowLayerBlock.LAYERS) == 1
         ? (BlockState)super.getStateForPlacement(context).setValue(SNOW_LAYERS, 1)
         : super.getStateForPlacement(context);
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Integer)state.getValue(SNOW_LAYERS) > 0 && !Blocks.SNOW.defaultBlockState().canSurvive(level, pos)) {
         level.setBlock(pos, (BlockState)state.setValue(SNOW_LAYERS, 0), 2);
         level.levelEvent(2001, pos, Block.getId(Blocks.SNOW.defaultBlockState()));
      }

      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE}).add(new Property[]{SNOW_LAYERS});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch (state.getValue(AGE)) {
         case 1 -> MEDIUM_BUSH_SHAPES[state.getValue(SNOW_LAYERS)];
         case 2, 3 -> Shapes.block();
         default -> SMALL_BUSH_SHAPES[state.getValue(SNOW_LAYERS)];
      };
   }

   protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
      return 1.0F;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Integer)state.getValue(AGE) < 3 && random.nextInt(20) == 0 && this.canGrowAt(state, level, pos)) {
         this.grow(state, level, pos, (Integer)state.getValue(AGE) + 1);
      }

      this.meltSnow(state, level, pos);
   }

   protected void grow(BlockState state, ServerLevel level, BlockPos pos, int age) {
      level.setBlock(pos, (BlockState)state.setValue(AGE, age), 2);
      if (age == 2 && (Integer)state.getValue(SNOW_LAYERS) > 0) {
         BlockState aboveState = level.getBlockState(pos.above());
         if (aboveState.canBeReplaced()) {
            level.setBlock(pos.above(), Blocks.SNOW.defaultBlockState(), 2);
         } else if (aboveState.getBlock() instanceof SnowLoggable && (Integer)aboveState.getValue(SNOW_LAYERS) == 0) {
            level.setBlock(pos.above(), (BlockState)aboveState.setValue(SNOW_LAYERS, 1), 2);
         }
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if ((Integer)state.getValue(AGE) == 3) {
         if (level instanceof ServerLevel serverLevel) {
            dropFromBlockInteractLootTable(serverLevel, this.berryLoot, state, pos, (level1, stack) -> ItemHandlerHelper.giveItemToPlayer(player, stack));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            BlockState newState = (BlockState)state.setValue(AGE, 2);
            level.setBlock(pos, newState, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, newState));
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useWithoutItem(state, level, pos, player, hitResult);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (stack.is(Items.SNOW) && (Integer)state.getValue(SNOW_LAYERS) != 8 && Blocks.SNOW.defaultBlockState().canSurvive(level, pos)) {
         BlockState newState = (BlockState)state.setValue(SNOW_LAYERS, (Integer)state.getValue(SNOW_LAYERS) + 1);
         if (this.isColliding(player, pos, newState)) {
            return ItemInteractionResult.FAIL;
         }

         if (!player.hasInfiniteMaterials()) {
            stack.shrink(1);
         }

         level.setBlock(pos, newState, 19);
         level.playSound(player, pos, SoundEvents.SNOW_PLACE, SoundSource.BLOCKS);
         updateSnowBeneath(level, pos);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   private static void updateSnowBeneath(Level level, BlockPos pos) {
      if (level.getBlockState(pos.below()).getBlock() instanceof SpreadingSnowyDirtBlock) {
         level.setBlock(pos.below(), (BlockState)level.getBlockState(pos.below()).setValue(SnowyDirtBlock.SNOWY, true), 2);
      }
   }

   protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) {
      if (player.isSecondaryUseActive() || (Integer)state.getValue(SNOW_LAYERS) <= 0) {
         super.spawnDestroyParticles(level, player, pos, state);
      }
   }

   public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
      if (!player.isSecondaryUseActive() && (Integer)state.getValue(SNOW_LAYERS) > 0) {
         this.handleBreakingLogic(level, pos, state, player, null);
         return false;
      } else {
         return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
      }
   }

   @Nullable
   public PushReaction getPistonPushReaction(BlockState state) {
      return state.getValue(AGE) < 2 ? PushReaction.DESTROY : null;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState stateBelow = level.getBlockState(pos.below());
      boolean isSameMatureBush = stateBelow.is(state.getBlock()) && (Integer)stateBelow.getValue(AGE) >= 2;
      return this.canBePlacedAt(stateBelow) || isSameMatureBush;
   }

   public abstract boolean canBePlacedAt(BlockState var1);

   protected boolean canGrowAt(BlockState state, LevelReader level, BlockPos pos) {
      return this.canSurvive(state, level, pos);
   }

   protected static void dropFromBlockInteractLootTable(
      ServerLevel serverlevel, ResourceKey<LootTable> resourcekey, BlockState blockstate, BlockPos pos, BiConsumer<ServerLevel, ItemStack> biconsumer
   ) {
      dropFromLootTable(
         serverlevel,
         resourcekey,
         builder -> builder.withParameter(LootContextParams.BLOCK_STATE, blockstate)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
            .create(LootContextParamSets.BLOCK),
         biconsumer
      );
   }

   protected static void dropFromLootTable(
      ServerLevel serverlevel,
      ResourceKey<LootTable> resourcekey,
      Function<net.minecraft.world.level.storage.loot.LootParams.Builder, LootParams> function,
      BiConsumer<ServerLevel, ItemStack> biconsumer
   ) {
      LootTable loottable = serverlevel.getServer().reloadableRegistries().getLootTable(resourcekey);
      LootParams lootparams = function.apply(new net.minecraft.world.level.storage.loot.LootParams.Builder(serverlevel));
      List<ItemStack> list = loottable.getRandomItems(lootparams);
      if (!list.isEmpty()) {
         list.forEach(itemstack -> biconsumer.accept(serverlevel, itemstack));
      }
   }
}
