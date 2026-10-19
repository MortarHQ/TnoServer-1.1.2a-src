package twilightforest.block;

import com.google.common.collect.Iterables;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.Tags.Items;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.CandelabraBlockEntity;
import twilightforest.components.item.CandelabraData;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;

public class CandelabraBlock extends BaseEntityBlock implements LightableBlock, SimpleWaterloggedBlock {
   public static final BooleanProperty ON_WALL = BooleanProperty.create("on_wall");
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final List<BooleanProperty> CANDLES = List.of(
      BooleanProperty.create("has_candle_1"), BooleanProperty.create("has_candle_2"), BooleanProperty.create("has_candle_3")
   );
   public static final VoxelShape CANDLES_NORTH = Shapes.or(
      Block.box(1.0, 7.0, 2.0, 15.0, 15.0, 6.0),
      new VoxelShape[]{Block.box(1.0, 1.0, 3.5, 15.0, 7.0, 4.5), Block.box(7.5, 1.0, 1.0, 8.5, 7.0, 7.0), Block.box(6.0, 2.0, 0.0, 10.0, 6.0, 1.0)}
   );
   public static final VoxelShape CANDLES_SOUTH = Shapes.or(
      Block.box(1.0, 7.0, 10.0, 15.0, 15.0, 14.0),
      new VoxelShape[]{Block.box(1.0, 1.0, 11.5, 15.0, 7.0, 12.5), Block.box(7.5, 1.0, 9.0, 8.5, 7.0, 15.0), Block.box(6.0, 2.0, 15.0, 10.0, 6.0, 16.0)}
   );
   public static final VoxelShape CANDLES_WEST = Shapes.or(
      Block.box(2.0, 7.0, 1.0, 6.0, 15.0, 15.0),
      new VoxelShape[]{Block.box(3.5, 1.0, 1.0, 4.5, 7.0, 15.0), Block.box(1.0, 1.0, 7.5, 7.0, 7.0, 8.5), Block.box(0.0, 2.0, 6.0, 1.0, 6.0, 10.0)}
   );
   public static final VoxelShape CANDLES_EAST = Shapes.or(
      Block.box(10.0, 7.0, 1.0, 14.0, 15.0, 15.0),
      new VoxelShape[]{Block.box(11.5, 1.0, 1.0, 12.5, 7.0, 15.0), Block.box(9.0, 1.0, 7.5, 15.0, 7.0, 8.5), Block.box(15.0, 2.0, 6.0, 16.0, 6.0, 10.0)}
   );
   public static final VoxelShape CANDLES_X = Shapes.or(
      Block.box(6.0, 7.0, 1.0, 10.0, 15.0, 15.0),
      new VoxelShape[]{Block.box(7.5, 1.0, 1.0, 8.5, 7.0, 15.0), Block.box(5.0, 1.0, 7.5, 11.0, 7.0, 8.5), Block.box(6.0, 0.0, 6.0, 10.0, 1.0, 10.0)}
   );
   public static final VoxelShape CANDLES_Z = Shapes.or(
      Block.box(1.0, 7.0, 6.0, 15.0, 15.0, 10.0),
      new VoxelShape[]{Block.box(1.0, 1.0, 7.5, 15.0, 7.0, 8.5), Block.box(7.5, 1.0, 5.0, 8.5, 7.0, 11.0), Block.box(6.0, 0.0, 6.0, 10.0, 1.0, 10.0)}
   );
   public static final List<Vec3> NORTH_OFFSETS = List.of(new Vec3(0.1875, 0.9, 0.25), new Vec3(0.5, 0.9, 0.25), new Vec3(0.8125, 0.9, 0.25));
   public static final List<Vec3> SOUTH_OFFSETS = List.of(new Vec3(0.1875, 0.9, 0.75), new Vec3(0.5, 0.9, 0.75), new Vec3(0.8125, 0.9, 0.75));
   public static final List<Vec3> WEST_OFFSETS = List.of(new Vec3(0.25, 0.9, 0.1875), new Vec3(0.25, 0.9, 0.5), new Vec3(0.25, 0.9, 0.8125));
   public static final List<Vec3> EAST_OFFSETS = List.of(new Vec3(0.75, 0.9, 0.1875), new Vec3(0.75, 0.9, 0.5), new Vec3(0.75, 0.9, 0.8125));
   public static final List<Vec3> X_OFFSETS = List.of(new Vec3(0.5, 0.9, 0.1875), new Vec3(0.5, 0.9, 0.5), new Vec3(0.5, 0.9, 0.8125));
   public static final List<Vec3> Z_OFFSETS = List.of(new Vec3(0.1875, 0.9, 0.5), new Vec3(0.5, 0.9, 0.5), new Vec3(0.8125, 0.9, 0.5));
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final MapCodec<CandelabraBlock> CODEC = simpleCodec(CandelabraBlock::new);

   public CandelabraBlock(Properties properties) {
      super(properties);
      BlockState state = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any())
                     .setValue(LIGHTING, LightableBlock.Lighting.NONE))
                  .setValue(FACING, Direction.NORTH))
               .setValue(ON_WALL, false))
            .setValue(LIGHTING, LightableBlock.Lighting.NONE))
         .setValue(WATERLOGGED, false);

      for (BooleanProperty booleanproperty : CANDLES) {
         state = (BlockState)state.setValue(booleanproperty, false);
      }

      this.registerDefaultState(state);
   }

   public static int getCandleCount(BlockState state) {
      int candleCount = 0;

      for (BooleanProperty property : CANDLES) {
         if ((Boolean)state.getValue(property)) {
            candleCount++;
         }
      }

      return candleCount;
   }

   public static boolean canSurvive(LevelReader reader, BlockPos pos, boolean onWall, Direction facing) {
      return canSupportCenter(reader, onWall ? pos.relative(facing) : pos.below(), Direction.UP);
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public int getLightEmission(BlockState state, BlockGetter getter, BlockPos pos) {
      int candleCount = getCandleCount(state);

      return switch ((LightableBlock.Lighting)state.getValue(LIGHTING)) {
         case DIM, OMINOUS -> 2 * candleCount;
         case NORMAL -> 5 * candleCount;
         default -> 0;
      };
   }

   @Override
   public Iterable<Vec3> getParticleOffsets(BlockState state, LevelAccessor accessor, BlockPos pos) {
      if ((Boolean)state.getValue(ON_WALL)) {
         return switch ((Direction)state.getValue(FACING)) {
            case SOUTH -> SOUTH_OFFSETS;
            case WEST -> WEST_OFFSETS;
            case EAST -> EAST_OFFSETS;
            default -> NORTH_OFFSETS;
         };
      } else {
         return ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? X_OFFSETS : Z_OFFSETS;
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      if ((Boolean)state.getValue(ON_WALL)) {
         return switch ((Direction)state.getValue(FACING)) {
            case SOUTH -> CANDLES_SOUTH;
            case WEST -> CANDLES_WEST;
            case EAST -> CANDLES_EAST;
            default -> CANDLES_NORTH;
         };
      } else {
         return ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? CANDLES_X : CANDLES_Z;
      }
   }

   public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
      return ItemAbilities.FIRESTARTER_LIGHT == itemAbility && this.canBeLit(state)
         ? (BlockState)state.setValue(LIGHTING, LightableBlock.Lighting.NORMAL)
         : super.getToolModifiedState(state, context, itemAbility, simulate);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
   ) {
      if (!stack.is(ItemTags.CANDLES) && !player.isShiftKeyDown()) {
         if (stack.is(Items.DUSTS_REDSTONE) && state.getValue(LIGHTING) == LightableBlock.Lighting.NORMAL) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(LIGHTING, LightableBlock.Lighting.DIM));
            stack.consume(1, player);
            level.playSound(null, pos, (SoundEvent)TFSounds.CANDELABRA_LIGHT.get(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            if (level.isClientSide()) {
               this.eruptFlameParticles((ParticleOptions)TFParticleType.DIM_FLAME.get(), level, pos, state);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         if ((stack.is(ItemTagGenerator.SCEPTERS) || stack.is(TFItems.EXANIMATE_ESSENCE)) && state.getValue(LIGHTING) == LightableBlock.Lighting.NORMAL) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(LIGHTING, LightableBlock.Lighting.OMINOUS));
            level.playSound(null, pos, (SoundEvent)TFSounds.CANDELABRA_OMINOUS.get(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            if (level.isClientSide()) {
               this.eruptFlameParticles((ParticleOptions)TFParticleType.OMINOUS_FLAME.get(), level, pos, state);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if (level.getBlockEntity(pos) instanceof CandelabraBlockEntity candelabra) {
         int i = this.getSlot(
            (Direction)state.getValue(FACING),
            result.getLocation().subtract(result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ())
         );
         if ((Boolean)state.getValue((Property)CANDLES.get(i)) && player.isShiftKeyDown()) {
            ItemStack itemstack = new ItemStack(candelabra.removeCandle(i));
            level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
            if (player.hasInfiniteMaterials()) {
               if (!player.getInventory().contains(itemstack)) {
                  player.getInventory().add(itemstack);
               }
            } else if (!player.getInventory().add(itemstack)) {
               player.drop(itemstack, false);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         if (!(Boolean)state.getValue((Property)CANDLES.get(i)) && stack.is(ItemTags.CANDLES) && stack.getItem() instanceof BlockItem block) {
            if (!level.isClientSide()) {
               player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
               candelabra.setCandle(i, block.getBlock());
               level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
               stack.consume(1, player);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }

         return ItemInteractionResult.CONSUME;
      }

      return this.tryLightCandles(stack, state, level, pos, player);
   }

   private void eruptFlameParticles(ParticleOptions particle, Level level, BlockPos pos, BlockState state) {
      for (int i = 0; i < 3; i++) {
         if ((Boolean)state.getValue((Property)CANDLES.get(i))) {
            Vec3 vec = (Vec3)Iterables.get(this.getParticleOffsets(state, level, pos), i);

            for (int j = 0; j < 5; j++) {
               level.addParticle(
                  particle,
                  pos.getX() + vec.x,
                  pos.getY() + vec.y,
                  pos.getZ() + vec.z,
                  (level.getRandom().nextDouble() - 0.5) * 0.05,
                  0.015F,
                  (level.getRandom().nextDouble() - 0.5) * 0.05
               );
            }
         }
      }
   }

   protected void updateNeighborsBasedOnRotation(Level level, BlockPos pos, BlockState state) {
      if ((Boolean)state.getValue(ON_WALL)) {
         Direction direction = (Direction)state.getValue(FACING);
         BlockPos blockpos = pos.relative(direction);
         level.neighborChanged(blockpos, this, pos);
         level.updateNeighborsAtExceptFromFacing(blockpos, this, direction.getOpposite());
      } else {
         level.neighborChanged(pos.below(), this, pos);
         level.updateNeighborsAtExceptFromFacing(pos.below(), this, Direction.UP);
      }
   }

   protected int getSlot(Direction blockDir, Vec3 hitVec) {
      Vec3i up = new Vec3i(0, 1, 0);
      Vec3i dir = up.cross(blockDir.getNormal());
      boolean reverse = blockDir == Direction.NORTH || blockDir == Direction.EAST;
      double cx = dir.getX() * hitVec.x() + dir.getZ() * hitVec.z();
      if (cx <= 0.0) {
         cx++;
      }

      if (cx <= 0.375) {
         return reverse ? 2 : 0;
      } else {
         return cx <= 0.6875 ? 1 : (reverse ? 0 : 2);
      }
   }

   public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
      this.lightCandlesWithProjectile(level, state, result, projectile);
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction clickDirection = context.getClickedFace();
      boolean onBottomBlock = clickDirection == Direction.UP;
      Direction[] placements = context.getNearestLookingDirections();
      BlockPos placePos = context.getClickedPos();
      Level level = context.getLevel();
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      boolean flag = fluidstate.is(Fluids.WATER);
      if (onBottomBlock) {
         if (canSurvive(level, placePos, false, context.getHorizontalDirection())) {
            return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())).setValue(ON_WALL, false))
               .setValue(WATERLOGGED, flag);
         }

         for (Direction nextSide : placements) {
            if (nextSide.getAxis().isHorizontal() && canSurvive(level, placePos, true, nextSide)) {
               return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, nextSide)).setValue(ON_WALL, true))
                  .setValue(WATERLOGGED, flag);
            }
         }
      } else {
         for (Direction nextSide : placements) {
            if (nextSide.getAxis().isHorizontal() && canSurvive(level, placePos, true, nextSide)) {
               return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, nextSide)).setValue(ON_WALL, true))
                  .setValue(WATERLOGGED, flag);
            }
         }

         if (canSurvive(level, placePos, false, context.getHorizontalDirection())) {
            return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())).setValue(ON_WALL, false))
               .setValue(WATERLOGGED, flag);
         }
      }

      return null;
   }

   @Override
   public boolean canBeLit(BlockState state) {
      return state.getValue(LIGHTING) == LightableBlock.Lighting.NONE && !(Boolean)state.getValue(WATERLOGGED) && getCandleCount(state) > 0;
   }

   public boolean placeLiquid(LevelAccessor accessor, BlockPos pos, BlockState state, FluidState fluid) {
      if (!(Boolean)state.getValue(BlockStateProperties.WATERLOGGED) && fluid.is(Fluids.WATER)) {
         boolean flag = state.getValue(LIGHTING) != LightableBlock.Lighting.NONE;
         if (flag) {
            this.extinguish(null, state, accessor, pos);
         }

         accessor.setBlock(pos, (BlockState)((BlockState)state.setValue(WATERLOGGED, true)).setValue(LIGHTING, LightableBlock.Lighting.NONE), 3);
         accessor.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(accessor));
         return true;
      } else {
         return false;
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, ON_WALL, LIGHTING, WATERLOGGED});
      CANDLES.forEach(xva$0 -> builder.add(new Property[]{xva$0}));
   }

   public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
      return canSurvive(reader, pos, (Boolean)state.getValue(ON_WALL), (Direction)state.getValue(FACING));
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      if (state.getValue(LIGHTING) != LightableBlock.Lighting.NONE) {
         for (int i = 0; i < 3; i++) {
            if ((Boolean)state.getValue((Property)CANDLES.get(i))) {
               Vec3 vec = (Vec3)Iterables.get(this.getParticleOffsets(state, level, pos), i);
               this.addParticlesAndSound(level, pos, vec.x, vec.y, vec.z, rand, (LightableBlock.Lighting)state.getValue(LIGHTING));
            }
         }
      }
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor accessor, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         accessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
      }

      return super.updateShape(state, facing, facingState, accessor, currentPos, facingPos);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CandelabraBlockEntity(pos, state);
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> drops = super.getDrops(state, builder);
      Optional<ItemStack> base = drops.stream().filter(item -> item.is(this.asItem())).findFirst();
      if (base.isPresent()) {
         BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
         if (blockEntity instanceof CandelabraBlockEntity candelabra) {
            RegistryAccess access = blockEntity.getLevel().registryAccess();
            if (!((ItemStack)builder.getParameter(LootContextParams.TOOL)).isEmpty()
               && ((ItemStack)builder.getParameter(LootContextParams.TOOL))
                     .getEnchantmentLevel(access.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH))
                  > 0) {
               ItemStack newStack = new ItemStack(this);
               newStack.applyComponents(candelabra.collectComponents());
               drops.remove(base.get());
               drops.add(newStack);
            } else {
               candelabra.getCandles().ordered().forEach(block -> drops.add(new ItemStack(block)));
            }
         }
      }

      return drops;
   }

   public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
      return state.getValue(LIGHTING) == LightableBlock.Lighting.DIM
         && (Boolean)state.getValue(ON_WALL)
         && ((Direction)state.getValue(FACING)).getOpposite() == direction;
   }

   public int getSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
      if (state.getValue(LIGHTING) == LightableBlock.Lighting.DIM) {
         if ((Boolean)state.getValue(ON_WALL)) {
            return state.getValue(FACING) == direction.getOpposite() ? getCandleCount(state) * 3 : 0;
         } else {
            return direction == Direction.UP ? getCandleCount(state) * 3 : 0;
         }
      } else {
         return 0;
      }
   }

   public int getDirectSignal(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
      return state.getSignal(getter, pos, direction);
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      int candleCount = getCandleCount(state);

      return switch ((LightableBlock.Lighting)state.getValue(LIGHTING)) {
         case DIM -> 3 + candleCount;
         case OMINOUS -> 6 + candleCount;
         case NORMAL -> 9 + candleCount;
         default -> candleCount;
      };
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
      this.updateNeighborsBasedOnRotation(level, pos, state);
      if (level.getBlockEntity(pos) instanceof CandelabraBlockEntity candelabra && !newState.is(state.getBlock())) {
         candelabra.updateState(0);
      }

      super.onPlace(state, level, pos, newState, moving);
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
      if (!state.is(newState.getBlock())) {
         this.updateNeighborsBasedOnRotation(level, pos, state);
      }

      super.onRemove(state, level, pos, newState, moving);
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      if (level.getBlockEntity(pos) instanceof CandelabraBlockEntity candelabra && candelabra.getCandles() != CandelabraData.EMPTY) {
         ItemStack itemstack = new ItemStack(this);
         itemstack.applyComponents(candelabra.collectComponents());
         return itemstack;
      } else {
         return super.getCloneItemStack(state, target, level, pos, player);
      }
   }
}
