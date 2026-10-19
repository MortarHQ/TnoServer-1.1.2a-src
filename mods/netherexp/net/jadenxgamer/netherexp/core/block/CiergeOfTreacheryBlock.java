package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.core.block.entity.CiergeOfTreacheryBlockEntity;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CiergeOfTreacheryBlock extends BaseEntityBlock {
   public static final BooleanProperty LIT = BooleanProperty.create("lit");
   public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
   public static final BooleanProperty COMPLETED = BooleanProperty.create("completed");
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   protected static final VoxelShape BROKEN_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);

   public CiergeOfTreacheryBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(LIT, false)).setValue(BROKEN, false))
               .setValue(FACING, Direction.NORTH))
            .setValue(COMPLETED, false)
      );
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
      return createTickerHelper(blockEntityType, JNEBlockEntityType.CIERGE_OF_TREACHERY.get(), CiergeOfTreacheryBlockEntity::tick);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(BROKEN) ? BROKEN_SHAPE : SHAPE;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CiergeOfTreacheryBlockEntity(pos, state);
   }

   @NotNull
   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   @NotNull
   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      return (BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      ItemStack itemStack = player.getItemInHand(hand);
      BlockEntity blockEntity = level.getBlockEntity(pos);
      boolean lit = (Boolean)state.getValue(LIT);
      boolean broken = (Boolean)state.getValue(BROKEN);
      boolean bl = false;
      if (broken) {
         if (itemStack.is(Items.HONEYCOMB)) {
            bl = true;
            if (blockEntity instanceof CiergeOfTreacheryBlockEntity ciergeOfTreacheryBlock) {
               CiergeOfTreacheryBlockEntity.resetValues(ciergeOfTreacheryBlock);
            }

            level.setBlock(pos, (BlockState)state.setValue(BROKEN, false), 2);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SOUL_CANDLE_PLACE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
               itemStack.shrink(1);
            }
         }
      } else if (!lit) {
         if (itemStack.is(Items.FLINT_AND_STEEL) && this.validPath(state, pos, level)) {
            bl = true;
            if (blockEntity instanceof CiergeOfTreacheryBlockEntity ciergeOfTreacheryBlock) {
               CiergeOfTreacheryBlockEntity.findAllNearbyPlayers(ciergeOfTreacheryBlock, pos, level);
            }

            level.setBlock(pos, (BlockState)state.cycle(LIT), 2);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.BRAZIER_CHEST_LIT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            fireParticles(level, pos);
            if (!player.getAbilities().instabuild) {
               itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
         } else if (itemStack.is(Items.FLINT_AND_STEEL) && !this.validPath(state, pos, level)) {
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }

      return bl ? ItemInteractionResult.sidedSuccess(level.isClientSide) : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   private boolean validPath(BlockState state, BlockPos pos, Level level) {
      Direction facing = (Direction)state.getValue(FACING);

      for (int d = 1; d <= 8; d++) {
         BlockPos raycast = pos.relative(facing, d);
         BlockPos raycastAbove = raycast.above();
         if (!level.getBlockState(raycast).isAir() || !level.getBlockState(raycastAbove).isAir()) {
            return false;
         }
      }

      return true;
   }

   @NotNull
   public RenderShape getRenderShape(BlockState arg) {
      return RenderShape.MODEL;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{LIT, COMPLETED, BROKEN, FACING});
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      boolean lit = (Boolean)state.getValue(LIT);
      boolean completed = (Boolean)state.getValue(COMPLETED);
      MutableBlockPos mutable = new MutableBlockPos();

      for (int l = 0; l < 14; l++) {
         mutable.set(x + Mth.nextInt(random, -20, 20), y + random.nextInt(20), z + Mth.nextInt(random, -20, 20));
         BlockState blockState = level.getBlockState(mutable);
         if (!blockState.isSolidRender(level, mutable) && lit && !completed) {
            level.addParticle(
               (ParticleOptions)JNEParticleTypes.RED_SPARKLE.get(),
               mutable.getX() + random.nextDouble(),
               mutable.getY() + random.nextDouble(),
               mutable.getZ() + random.nextDouble(),
               0.0,
               0.0,
               0.0
            );
            int u = random.nextInt(6);
            if (u == 0) {
               level.addParticle(
                  (ParticleOptions)JNEParticleTypes.RED_HAZE.get(),
                  mutable.getX() + random.nextDouble(),
                  mutable.getY() + random.nextDouble(),
                  mutable.getZ() + random.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }

      int i = random.nextInt(1);
      if (i == 0 && lit) {
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

   private static void fireParticles(Level level, BlockPos blockPos) {
      RandomSource randomSource = level.random;
      Direction[] var5 = Direction.values();

      for (Direction direction : var5) {
         BlockPos blockPos2 = blockPos.relative(direction);
         if (!level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) {
            Axis axis = direction.getAxis();
            double e = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : randomSource.nextFloat();
            double f = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : randomSource.nextFloat();
            double g = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : randomSource.nextFloat();
            level.addParticle(
               (ParticleOptions)JNEParticleTypes.TREACHEROUS_FLAME.get(), blockPos.getX() + e, blockPos.getY() + f, blockPos.getZ() + g, 0.0, 0.0, 0.0
            );
         }
      }
   }
}
