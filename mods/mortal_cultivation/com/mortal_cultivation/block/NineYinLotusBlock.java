package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.NineYinLotusBlockEntity;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class NineYinLotusBlock extends BushBlock implements EntityBlock {
   public static final MapCodec<NineYinLotusBlock> CODEC = simpleCodec(NineYinLotusBlock::new);
   public static final BooleanProperty BLOOMING = BooleanProperty.create("blooming");
   private static final VoxelShape LEAF_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);
   private static final VoxelShape FLOWER_SHAPE = Shapes.or(LEAF_SHAPE, Block.box(3.0, 1.5, 3.0, 13.0, 12.0, 13.0));

   public NineYinLotusBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BLOOMING, true));
   }

   public MapCodec<NineYinLotusBlock> codec() {
      return CODEC;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{BLOOMING});
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(BLOOMING) ? FLOWER_SHAPE : LEAF_SHAPE;
   }

   protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return LEAF_SHAPE;
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack(state.getValue(BLOOMING) ? (ItemLike)CultivationItems.NINE_YIN_LOTUS.get() : (ItemLike)CultivationItems.NINE_YIN_LOTUS_LEAF.get());
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(Blocks.WATER) && state.getFluidState().isSource() && level.getFluidState(pos.above()).isEmpty();
   }

   public static boolean isInColdPool(LevelReader level, BlockPos pos) {
      return level.getNoiseBiome(QuartPos.fromBlock(pos.getX()), QuartPos.fromBlock(pos.getY()), QuartPos.fromBlock(pos.getZ()))
         .is(WorldgenRegistry.JIUYOU_COLD_POOL_BIOME);
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return super.canSurvive(state, level, pos) && isInColdPool(level, pos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState state = (BlockState)this.defaultBlockState().setValue(BLOOMING, false);
      return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      if (!level.isClientSide && !(Boolean)state.getValue(BLOOMING) && level.getBlockEntity(pos) instanceof NineYinLotusBlockEntity lotus) {
         lotus.startRegrowth(level.getGameTime());
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!(Boolean)state.getValue(BLOOMING)) {
         return InteractionResult.PASS;
      }

      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      BlockState current = level.getBlockState(pos);
      if (current.is(this) && (Boolean)current.getValue(BLOOMING) && level.getBlockEntity(pos) instanceof NineYinLotusBlockEntity lotus) {
         BlockState var9 = (BlockState)current.setValue(BLOOMING, false);
         if (!level.setBlock(pos, var9, 2)) {
            return InteractionResult.PASS;
         }

         lotus.startRegrowth(level.getGameTime());
         popResource(level, pos, new ItemStack((ItemLike)CultivationItems.NINE_YIN_LOTUS.get()));
         level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
         level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, var9));
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new NineYinLotusBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return !level.isClientSide && !state.getValue(BLOOMING) && type == BlockEntityRegistry.NINE_YIN_LOTUS.get()
         ? (tickLevel, tickPos, tickState, blockEntity) -> {
            if (blockEntity instanceof NineYinLotusBlockEntity lotus) {
               NineYinLotusBlockEntity.serverTick(tickLevel, tickPos, tickState, lotus);
            }
         }
         : null;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(BLOOMING) && random.nextInt(8) == 0) {
         double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.7;
         double y = pos.getY() + 0.3 + random.nextDouble() * 0.3;
         double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.7;
         level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.003, 0.0);
      }
   }
}
