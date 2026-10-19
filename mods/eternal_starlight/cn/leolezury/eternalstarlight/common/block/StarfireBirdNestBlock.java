package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StarfireBirdNestBlock extends BaseEntityBlock {
   public static final MapCodec<StarfireBirdNestBlock> CODEC = simpleCodec(StarfireBirdNestBlock::new);
   public static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final IntegerProperty EGGS = IntegerProperty.create("eggs", 0, 3);

   protected MapCodec<? extends StarfireBirdNestBlock> codec() {
      return CODEC;
   }

   public StarfireBirdNestBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(EGGS, 0));
   }

   public float getSeedsRenderOffset() {
      return 0.125F;
   }

   public boolean canAccessNestContent(BlockState state) {
      return true;
   }

   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (this.canAccessNestContent(state)
         && stack.is(ESTags.Items.STARFIRE_BIRD_FOOD)
         && level.getBlockEntity(pos) instanceof StarfireBirdNestBlockEntity nest
         && nest.getItems().stream().anyMatch(ItemStack::isEmpty)) {
         if (!level.isClientSide && nest.addSeeds(stack.copyWithCount(1))) {
            nest.setLastSeedPlayer(player);
            if (player instanceof ServerPlayer serverPlayer) {
               ((PlayerTrigger)ESCriteriaTriggers.PUT_SEEDS_INTO_STARFIRE_BIRD_NEST.get()).trigger(serverPlayer);
            }

            stack.consume(1, player);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         if (this.canAccessNestContent(state) && (Integer)state.getValue(EGGS) < 3 && stack.is(ESItems.STARFIRE_BIRD_EGG.get())) {
            stack.consume(1, player);
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(EGGS, (Integer)state.getValue(EGGS) + 1));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
         }

         if (this.canAccessNestContent(state)
            && (Integer)state.getValue(EGGS) > 0
            && (stack.isEmpty() || stack.is(ESItems.STARFIRE_BIRD_EGG.get()) && stack.getCount() < stack.getMaxStackSize())) {
            if (stack.isEmpty()) {
               player.setItemInHand(hand, ESItems.STARFIRE_BIRD_EGG.get().getDefaultInstance());
            } else {
               stack.grow(1);
            }

            level.setBlockAndUpdate(pos, (BlockState)state.setValue(EGGS, (Integer)state.getValue(EGGS) - 1));
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
         } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
         }
      }
   }

   public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.playerDestroy(level, player, pos, state, blockEntity, stack);
      if (!level.isClientSide
         && blockEntity instanceof StarfireBirdNestBlockEntity nest
         && !EnchantmentHelper.hasTag(stack, ESTags.Enchantments.PREVENTS_STARFIRE_BIRD_SPAWNS_WHEN_MINING)) {
         nest.releaseAllOccupants(state, true);
      }
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockState aboveState = level.getBlockState(pos.above());
      return aboveState.is(BlockTags.LEAVES) || aboveState.is(ESTags.Blocks.STARFIRE_BIRD_NESTS);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor level, BlockPos pos, BlockPos blockPos) {
      return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, level, pos, blockPos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, EGGS});
   }

   protected RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new StarfireBirdNestBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return level.isClientSide
         ? createTickerHelper(type, ESBlockEntities.STARFIRE_BIRD_NEST.get(), StarfireBirdNestBlockEntity::clientTick)
         : createTickerHelper(type, ESBlockEntities.STARFIRE_BIRD_NEST.get(), StarfireBirdNestBlockEntity::serverTick);
   }

   public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
      if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
         BlockEntity blockEntity = level.getBlockEntity(blockPos);
         int eggs = (Integer)blockState.getValue(EGGS);
         if (blockEntity instanceof StarfireBirdNestBlockEntity nest
            && (nest.getOccupantCount() > 0 || nest.getItems().stream().anyMatch(stackx -> !stackx.isEmpty()) || eggs > 0)) {
            ItemStack stack = new ItemStack(this);
            stack.applyComponents(nest.collectComponents());
            stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(EGGS, eggs));
            ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
         }
      }

      return super.playerWillDestroy(level, blockPos, blockState, player);
   }

   protected List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      Entity entity = (Entity)builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
      if (!(entity instanceof Player)) {
         BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
         if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
            nest.releaseAllOccupants(state, true);
         }
      }

      BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
      if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
         builder = builder.withDynamicDrop(ResourceLocation.withDefaultNamespace("contents"), consumer -> {
            for (int i = 0; i < nest.getContainerSize(); i++) {
               consumer.accept(nest.getItem(i));
            }
         });
      }

      return super.getDrops(state, builder);
   }

   protected boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }
}
