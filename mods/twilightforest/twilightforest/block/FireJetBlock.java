package twilightforest.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.entity.FireJetBlockEntity;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.data.tags.FluidTagGenerator;
import twilightforest.enums.FireJetVariant;
import twilightforest.init.TFBlockEntities;

public class FireJetBlock extends BaseEntityBlock {
   public static final EnumProperty<FireJetVariant> STATE = EnumProperty.create("state", FireJetVariant.class);
   public static final MapCodec<FireJetBlock> CODEC = simpleCodec(FireJetBlock::new);

   public FireJetBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(STATE, FireJetVariant.IDLE));
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

   @Nullable
   public PathType getBlockPathType(BlockState state, BlockGetter getter, BlockPos pos, @Nullable Mob mob) {
      return state.getValue(STATE) == FireJetVariant.IDLE ? null : PathType.DAMAGE_FIRE;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isClientSide() && state.getValue(STATE) == FireJetVariant.IDLE) {
         BlockPos lavaPos = this.findLavaAround(level, pos.below());
         if (this.isLava(level, lavaPos)) {
            level.setBlockAndUpdate(lavaPos, Blocks.AIR.defaultBlockState());
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(STATE, FireJetVariant.POPPING));
         }
      }
   }

   private BlockPos findLavaAround(Level level, BlockPos pos) {
      if (this.isLava(level, pos)) {
         return pos;
      }

      for (int i = 0; i < 3; i++) {
         BlockPos randPos = pos.offset(level.getRandom().nextInt(3) - 1, 0, level.getRandom().nextInt(3) - 1);
         if (this.isLava(level, randPos)) {
            return randPos;
         }
      }

      return pos;
   }

   private boolean isLava(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      return state.is(BlockTagGenerator.FIRE_JET_FUEL) || state.getFluidState().is(FluidTagGenerator.FIRE_JET_FUEL);
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new FireJetBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.FLAME_JET.get(), FireJetBlockEntity::tick);
   }
}
