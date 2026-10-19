package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.block.entity.BrazierChestBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrazierChestBlock extends BaseEntityBlock {
   public static final MapCodec<BrazierChestBlock> CODEC = simpleCodec(BrazierChestBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

   public BrazierChestBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(OPEN, false))
            .setValue(LOCKED, true)
      );
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (level.getBlockEntity(pos) instanceof BrazierChestBlockEntity blockEntity) {
         boolean locked = (Boolean)state.getValue(LOCKED);
         if (locked) {
            if (stack.is(JNEItems.SPARK_OF_TREACHERY.get())) {
               fireParticles(level, pos.above());
               blockEntity.refillLoot();
               level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.BRAZIER_CHEST_LIT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               level.setBlock(pos, (BlockState)state.setValue(LOCKED, false), 3);
               if (!player.isCreative()) {
                  stack.shrink(1);
               }

               if (!level.isClientSide) {
                  player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
               }
            } else {
               level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_BRICKS_HIT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
               if (level.isClientSide) {
                  player.displayClientMessage(Component.translatable("block.netherexp.brazier_chest.locked").withStyle(ChatFormatting.DARK_RED), true);
               }
            }
         } else {
            player.openMenu(blockEntity);
         }

         return ItemInteractionResult.SUCCESS;
      } else {
         return ItemInteractionResult.FAIL;
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_SLATE_BRICKS_HIT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      if (level.isClientSide) {
         player.displayClientMessage(Component.translatable("block.netherexp.brazier_chest.locked").withStyle(ChatFormatting.DARK_RED), true);
      }

      return super.useWithoutItem(state, level, pos, player, hitResult);
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      BlockEntity blockEntity = level.getBlockEntity(pos);
      if (blockEntity instanceof BrazierChestBlockEntity) {
         ((BrazierChestBlockEntity)blockEntity).recheckOpen();
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int i = random.nextInt(1);
      if (i == 0 && !(Boolean)state.getValue(LOCKED)) {
         level.addParticle(
            (ParticleOptions)JNEParticleTypes.TREACHEROUS_FLAME.get(),
            pos.getX() + 0.5 + level.random.nextDouble() / 4.0 * (level.random.nextBoolean() ? 1 : -1),
            pos.getY() + 1.1,
            pos.getZ() + 0.5 + level.random.nextDouble() / 4.0 * (level.random.nextBoolean() ? 1 : -1),
            0.0,
            0.07,
            0.0
         );
      }
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BrazierChestBlockEntity(pos, state);
   }

   @NotNull
   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
   }

   @NotNull
   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   @NotNull
   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, OPEN, LOCKED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      return createTickerHelper(
         blockEntityType, JNEBlockEntityType.BRAZIER_CHEST.get(), (level1, pos, state2, blockEntity) -> blockEntity.tick(level1, pos, state2)
      );
   }

   private static void fireParticles(Level level, BlockPos pos) {
      RandomSource randomSource = level.random;
      Direction[] var5 = Direction.values();

      for (Direction direction : var5) {
         BlockPos blockPos2 = pos.relative(direction);
         if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
            Axis axis = direction.getAxis();
            double e = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : randomSource.nextFloat();
            double f = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : randomSource.nextFloat();
            double g = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : randomSource.nextFloat();
            level.addParticle((ParticleOptions)JNEParticleTypes.TREACHEROUS_FLAME.get(), pos.getX() + e, pos.getY() + f, pos.getZ() + g, 0.0, 0.0, 0.0);
         }
      }
   }

   public boolean isSignalSource(BlockState pState) {
      return true;
   }

   public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
      return 15;
   }
}
