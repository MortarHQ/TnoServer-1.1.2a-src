package twilightforest.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.joml.Vector3f;

public class TrollsteinnBlock extends Block {
   public static final int LIGHT_THRESHOLD = 7;
   private static final BooleanProperty DOWN_LIT = BooleanProperty.create("down");
   private static final BooleanProperty UP_LIT = BooleanProperty.create("up");
   private static final BooleanProperty NORTH_LIT = BooleanProperty.create("north");
   private static final BooleanProperty SOUTH_LIT = BooleanProperty.create("south");
   private static final BooleanProperty WEST_LIT = BooleanProperty.create("west");
   private static final BooleanProperty EAST_LIT = BooleanProperty.create("east");
   private static final Map<Direction, BooleanProperty> PROPERTY_MAP = ImmutableMap.builder()
      .put(Direction.DOWN, DOWN_LIT)
      .put(Direction.UP, UP_LIT)
      .put(Direction.NORTH, NORTH_LIT)
      .put(Direction.SOUTH, SOUTH_LIT)
      .put(Direction.WEST, WEST_LIT)
      .put(Direction.EAST, EAST_LIT)
      .build();
   DustParticleOptions PURPL = new DustParticleOptions(new Vector3f(0.25F, 0.0F, 0.5F), 1.0F);

   public TrollsteinnBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(DOWN_LIT, false))
                        .setValue(UP_LIT, false))
                     .setValue(NORTH_LIT, false))
                  .setValue(SOUTH_LIT, false))
               .setValue(WEST_LIT, false))
            .setValue(EAST_LIT, false)
      );
   }

   public static int calculateServerSkyDarken(Level level) {
      double rainEffect = 1.0 - level.getRainLevel(1.0F) * 5.0F / 16.0;
      double thunderEffect = 1.0 - level.getThunderLevel(1.0F) * 5.0F / 16.0;
      double dayCycleEffect = 0.5 + 2.0 * Mth.clamp(Mth.cos(level.getTimeOfDay(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
      return (int)((1.0 - dayCycleEffect * rainEffect * thunderEffect) * 11.0);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{DOWN_LIT, UP_LIT, NORTH_LIT, SOUTH_LIT, WEST_LIT, EAST_LIT});
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      BlockState newState = state;

      for (Direction direction : Direction.values()) {
         newState = (BlockState)newState.setValue((Property)PROPERTY_MAP.get(direction), level.getMaxLocalRawBrightness(pos.relative(direction)) > 7);
      }

      if (!newState.equals(state)) {
         level.setBlockAndUpdate(pos, newState);
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      int peak = 0;

      for (Direction direction : Direction.values()) {
         peak = Math.max(level.getMaxLocalRawBrightness(pos.relative(direction)), peak);
      }

      return peak;
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      BlockState ret = this.defaultBlockState();

      for (Entry<Direction, BooleanProperty> e : PROPERTY_MAP.entrySet()) {
         Level level = ctx.getLevel();
         BlockPos pos = ctx.getClickedPos();
         int light = level.getMaxLocalRawBrightness(pos.relative(e.getKey()), level.isClientSide() ? calculateServerSkyDarken(level) : level.getSkyDarken());
         ret = (BlockState)ret.setValue((Property)e.getValue(), light > 7);
      }

      return ret;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
      if (rand.nextBoolean()) {
         this.sparkle(level, pos);
      }
   }

   private void sparkle(Level level, BlockPos pos) {
      RandomSource random = level.getRandom();

      for (Direction direction : Direction.values()) {
         BlockPos blockpos = pos.relative(direction);
         if (!level.getBlockState(blockpos).isSolidRender(level, blockpos) && level.getMaxLocalRawBrightness(pos.relative(direction)) <= 7) {
            Axis direction$axis = direction.getAxis();
            double d1 = direction$axis == Axis.X ? 0.5 + 0.5625 * direction.getStepX() : random.nextFloat();
            double d2 = direction$axis == Axis.Y ? 0.5 + 0.5625 * direction.getStepY() : random.nextFloat();
            double d3 = direction$axis == Axis.Z ? 0.5 + 0.5625 * direction.getStepZ() : random.nextFloat();
            level.addParticle(this.PURPL, pos.getX() + d1, pos.getY() + d2, pos.getZ() + d3, 0.0, 0.0, 0.0);
         }
      }
   }
}
