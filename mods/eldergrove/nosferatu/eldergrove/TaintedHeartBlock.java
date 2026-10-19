package nosferatu.eldergrove;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class TaintedHeartBlock extends Block {
   public static final int SPREAD_RADIUS = 12;
   private static final int SPREAD_ATTEMPTS = 16;
   private static final int TICK_DELAY = 60;
   private static final int MAX_SURFACE_DELTA = 12;
   private static final int VERTICAL_RADIUS = 12;

   public TaintedHeartBlock(Properties properties) {
      super(properties);
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      if (!level.isClientSide && !state.is(oldState.getBlock())) {
         level.scheduleTick(pos, this, 60);
      }
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      spreadTaint(level, pos, random);
      level.scheduleTick(pos, this, 60);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      spreadTaint(level, pos, random);
   }

   private static void spreadTaint(ServerLevel level, BlockPos heartPos, RandomSource random) {
      for (int attempt = 0; attempt < 16; attempt++) {
         int offsetX = random.nextInt(25) - 12;
         int offsetZ = random.nextInt(25) - 12;
         if (offsetX * offsetX + offsetZ * offsetZ <= 144) {
            BlockPos column = heartPos.offset(offsetX, 0, offsetZ);
            if (level.hasChunkAt(column)) {
               BlockPos target;
               if ((attempt & 1) == 0) {
                  int surfaceY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, column.getX(), column.getZ()) - 1;
                  if (Math.abs(surfaceY - heartPos.getY()) > 12) {
                     continue;
                  }

                  target = new BlockPos(column.getX(), surfaceY, column.getZ());
               } else {
                  int offsetY = random.nextInt(25) - 12;
                  target = heartPos.offset(offsetX, offsetY, offsetZ);
                  if (target.getY() < level.getMinBuildHeight() || target.getY() >= level.getMaxBuildHeight()) {
                     continue;
                  }
               }

               BlockState current = level.getBlockState(target);
               if (isConvertibleSurface(current) && isExposed(level, target)) {
                  BlockState replacement = current.is(BlockTags.BASE_STONE_OVERWORLD)
                     ? ((Block)EldergroveBlocks.TAINTED_ROCK.get()).defaultBlockState()
                     : (
                        random.nextInt(5) == 0
                           ? ((Block)EldergroveBlocks.TAINTED_CRUST.get()).defaultBlockState()
                           : ((Block)EldergroveBlocks.TAINTED_SOIL.get()).defaultBlockState()
                     );
                  level.setBlock(target, replacement, 3);
                  EldergroveBiomeSpreader.setTaintedBiome(level, target);
                  placeTaintedGrowth(level, target.above(), random);
                  return;
               }
            }
         }
      }
   }

   private static boolean isConvertibleSurface(BlockState state) {
      return state.is(BlockTags.DIRT)
         || state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MUD)
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get())
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS.get())
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS_DEEP.get());
   }

   private static boolean isExposed(ServerLevel level, BlockPos pos) {
      for (Direction direction : Direction.values()) {
         BlockState neighbor = level.getBlockState(pos.relative(direction));
         if (neighbor.isAir() || neighbor.canBeReplaced()) {
            return true;
         }
      }

      return false;
   }

   private static void placeTaintedGrowth(ServerLevel level, BlockPos pos, RandomSource random) {
      BlockState current = level.getBlockState(pos);
      if (current.getFluidState().isEmpty() && (current.isAir() || current.canBeReplaced())) {
         BlockState growth = switch (random.nextInt(6)) {
            case 0 -> ((Block)EldergroveBlocks.TAINTED_GROWTH.get()).defaultBlockState();
            case 1 -> ((Block)EldergroveBlocks.TAINTED_TENDRIL.get()).defaultBlockState();
            default -> ((Block)EldergroveBlocks.TAINTED_FIBRE.get()).defaultBlockState();
         };
         if (growth.canSurvive(level, pos)) {
            level.setBlock(pos, growth, 2);
         }
      }
   }
}
