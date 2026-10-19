package net.jadenxgamer.netherexp.core.block;

import com.mojang.serialization.MapCodec;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DiscernmentGlassBlock extends BaseEntityBlock {
   public static final MapCodec<DiscernmentGlassBlock> CODEC = simpleCodec(DiscernmentGlassBlock::new);
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

   public DiscernmentGlassBlock(Properties properties) {
      super(properties.isValidSpawn(Blocks::never).isViewBlocking((s, g, p) -> false).isRedstoneConductor((s, g, p) -> false).isSuffocating((s, g, p) -> false));
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(POWERED, false));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new DiscernmentGlassBlockEntity(pos, state);
   }

   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof ItemEntity) {
         double slowdown = (Double)JNEConfigs.SOUL_GLASS_MOVEMENT_SLOWDOWN.get();
         entity.makeStuckInBlock(state, new Vec3(slowdown, slowdown, slowdown));
      }
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (context instanceof EntityCollisionContext entityContext
         && level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity
         && entityContext.getEntity() instanceof ItemEntity item) {
         boolean isFilterItem = item.getItem().getItem() == blockEntity.getFilterItem().getItem();
         if ((Boolean)state.getValue(POWERED) && !isFilterItem || !(Boolean)state.getValue(POWERED) && isFilterItem) {
            return Shapes.empty();
         }
      }

      return super.getCollisionShape(state, level, pos, context);
   }

   public RenderShape getRenderShape(BlockState arg) {
      return RenderShape.MODEL;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if (level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity) {
         if (!stack.isEmpty() && blockEntity.getFilterItem().isEmpty()) {
            if (stack.is(this.asItem())) {
               return ItemInteractionResult.FAIL;
            }

            blockEntity.setFilterItem(stack);
            if (!player.getAbilities().instabuild) {
               stack.shrink(1);
            }

            ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
            level.playSound(null, pos, JNESoundEvents.DISCERNMENT_GLASS_ADD.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.sendBlockUpdated(pos, state, state, 2);
            level.blockUpdated(pos, this);
            return ItemInteractionResult.SUCCESS;
         }

         if (stack.isEmpty() && !blockEntity.getFilterItem().isEmpty()) {
            if (!player.getAbilities().instabuild) {
               if (player.getInventory().add(blockEntity.getFilterItem())) {
                  blockEntity.removeFilterItem();
               }
            } else {
               blockEntity.removeFilterItem();
            }

            ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
            level.playSound(null, pos, JNESoundEvents.DISCERNMENT_GLASS_REMOVE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.sendBlockUpdated(pos, state, state, 2);
            level.blockUpdated(pos, this);
            return ItemInteractionResult.SUCCESS;
         }
      }

      return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
      if (!state.is(newState.getBlock())
         && level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity
         && !blockEntity.getFilterItem().isEmpty()) {
         Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), blockEntity.getFilterItem());
      }

      super.onRemove(state, level, pos, newState, movedByPiston);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
      if ((Boolean)state.getValue(POWERED) != level.hasNeighborSignal(pos)) {
         if ((Boolean)state.getValue(POWERED)) {
            level.scheduleTick(pos, this, 4);
         } else {
            level.setBlock(pos, (BlockState)state.cycle(POWERED), 2);
         }
      }
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if ((Boolean)state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
         level.setBlock(pos, (BlockState)state.cycle(POWERED), 2);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{POWERED});
   }

   public boolean hasAnalogOutputSignal(BlockState blockState) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      if (level.getBlockEntity(pos) instanceof DiscernmentGlassBlockEntity blockEntity) {
         return blockEntity.getFilterItem().isEmpty() ? 0 : 15;
      } else {
         return 0;
      }
   }

   protected boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
      return adjacentState.is(this) || super.skipRendering(state, adjacentState, direction);
   }

   protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
      return 1.0F;
   }

   protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
      return true;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
