package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.block.entity.GhastTrapBlockEntity;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFSounds;

public class GhastTrapBlock extends BaseEntityBlock {
   public static final int ACTIVATE_EVENT = 0;
   public static final int DEACTIVATE_EVENT = 1;
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final MapCodec<GhastTrapBlock> CODEC = simpleCodec(GhastTrapBlock::new);

   public GhastTrapBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(ACTIVE, false));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{ACTIVE});
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         if (!(Boolean)state.getValue(ACTIVE) && this.isInactiveTrapCharged(level, pos) && level.hasNeighborSignal(pos)) {
            for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(6.0))) {
               ((SimpleAdvancementTrigger)TFAdvancements.ACTIVATED_GHAST_TRAP.get()).trigger(player);
            }

            level.setBlockAndUpdate(pos, (BlockState)state.setValue(ACTIVE, true));
            level.playSound(null, pos, (SoundEvent)TFSounds.JET_START.get(), SoundSource.BLOCKS, 0.3F, 0.6F);
            level.blockEvent(pos, this, 0, 0);
         }
      }
   }

   public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int event, int id) {
      BlockEntity te = level.getBlockEntity(pos);
      return te != null && te.triggerEvent(event, id);
   }

   private boolean isInactiveTrapCharged(Level level, BlockPos pos) {
      return level.getBlockEntity(pos) instanceof GhastTrapBlockEntity ghastTrap && ghastTrap.isCharged();
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
      return new GhastTrapBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.GHAST_TRAP.get(), GhastTrapBlockEntity::tick);
   }
}
