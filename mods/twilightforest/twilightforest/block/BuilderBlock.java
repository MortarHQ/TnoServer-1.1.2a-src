package twilightforest.block;

import com.mojang.serialization.MapCodec;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.block.entity.CarminiteBuilderBlockEntity;
import twilightforest.enums.TowerDeviceVariant;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class BuilderBlock extends BaseEntityBlock {
   public static final EnumProperty<TowerDeviceVariant> STATE = EnumProperty.create("state", TowerDeviceVariant.class);
   public static final MapCodec<BuilderBlock> CODEC = simpleCodec(BuilderBlock::new);

   public BuilderBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(STATE, TowerDeviceVariant.BUILDER_INACTIVE));
   }

   public static void activateBuiltBlocks(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() == TFBlocks.BUILT_BLOCK.get() && !(Boolean)state.getValue(TranslucentBuiltBlock.ACTIVE)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(TranslucentBuiltBlock.ACTIVE, true));
         level.playSound(null, pos, (SoundEvent)TFSounds.BUILDER_REPLACE.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
         level.scheduleTick(pos, state.getBlock(), 10);
      }
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{STATE});
   }

   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!level.isClientSide() && state.getValue(STATE) == TowerDeviceVariant.BUILDER_INACTIVE && level.hasNeighborSignal(pos)) {
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(STATE, TowerDeviceVariant.BUILDER_ACTIVE));
         level.playSound(null, pos, (SoundEvent)TFSounds.BUILDER_ON.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
      }
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         TowerDeviceVariant variant = (TowerDeviceVariant)state.getValue(STATE);
         if (variant == TowerDeviceVariant.BUILDER_INACTIVE && level.hasNeighborSignal(pos)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(STATE, TowerDeviceVariant.BUILDER_ACTIVE));
            level.playSound(null, pos, (SoundEvent)TFSounds.BUILDER_ON.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
            level.scheduleTick(pos, this, 4);
         }

         if (variant == TowerDeviceVariant.BUILDER_ACTIVE && !level.hasNeighborSignal(pos)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(STATE, TowerDeviceVariant.BUILDER_INACTIVE));
            level.playSound(null, pos, (SoundEvent)TFSounds.BUILDER_OFF.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
            level.scheduleTick(pos, this, 4);
         }

         if (variant == TowerDeviceVariant.BUILDER_TIMEOUT && !level.hasNeighborSignal(pos)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(STATE, TowerDeviceVariant.BUILDER_INACTIVE));
         }
      }
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      TowerDeviceVariant variant = (TowerDeviceVariant)state.getValue(STATE);
      if (variant == TowerDeviceVariant.BUILDER_ACTIVE && level.hasNeighborSignal(pos)) {
         this.letsBuild(level, pos);
      }

      if (variant == TowerDeviceVariant.BUILDER_INACTIVE || variant == TowerDeviceVariant.BUILDER_TIMEOUT) {
         ((CarminiteBuilderBlockEntity)Objects.requireNonNull(level.getBlockEntity(pos))).resetStats();

         for (Direction e : Direction.values()) {
            activateBuiltBlocks(level, pos.relative(e));
         }
      }
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
      if (!newState.is(state.getBlock())) {
         for (Direction e : Direction.values()) {
            activateBuiltBlocks(level, pos.relative(e));
         }
      }

      super.onRemove(state, level, pos, newState, moving);
   }

   private void letsBuild(Level level, BlockPos pos) {
      CarminiteBuilderBlockEntity tileEntity = (CarminiteBuilderBlockEntity)level.getBlockEntity(pos);
      if (tileEntity != null && !tileEntity.makingBlocks) {
         tileEntity.startBuilding();
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (state.getValue(STATE) == TowerDeviceVariant.BUILDER_ACTIVE) {
         this.sparkle(level, pos);
      }
   }

   public void sparkle(Level level, BlockPos pos) {
      RandomSource random = level.getRandom();

      for (Direction direction : Direction.values()) {
         BlockPos blockpos = pos.relative(direction);
         if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
            Axis axis = direction.getAxis();
            double d1 = axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
            double d2 = axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
            double d3 = axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0, 0.0, 0.0);
         }
      }
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new CarminiteBuilderBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return state.getValue(STATE) == TowerDeviceVariant.BUILDER_ACTIVE
         ? createTickerHelper(type, (BlockEntityType)TFBlockEntities.TOWER_BUILDER.get(), CarminiteBuilderBlockEntity::tick)
         : null;
   }
}
