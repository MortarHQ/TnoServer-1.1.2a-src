package nosferatu.eldergrove;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EldergroveSpreadingCoreBlock extends Block {
   public static final int AURA_RADIUS = 14;
   private static final int SPREAD_ATTEMPTS = 3;
   private static final int TICK_DELAY = 80;
   private static final int SURFACE_SEARCH_RANGE = 10;

   public EldergroveSpreadingCoreBlock(Properties properties) {
      super(properties);
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
      if (!level.isClientSide && !state.is(oldState.getBlock())) {
         level.scheduleTick(pos, this, 80);
      }
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      spreadBiomeAura(level, pos, random);
      level.scheduleTick(pos, this, 80);
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      spreadBiomeAura(level, pos, random);
   }

   private static void spreadBiomeAura(ServerLevel level, BlockPos heartPos, RandomSource random) {
      for (int i = 0; i < 3; i++) {
         int offsetX = random.nextInt(29) - 14;
         int offsetZ = random.nextInt(29) - 14;
         if (offsetX * offsetX + offsetZ * offsetZ <= 196) {
            BlockPos targetColumn = heartPos.offset(offsetX, 0, offsetZ);
            BlockPos surface = findNaturalSurface(level, targetColumn, heartPos.getY());
            if (surface != null) {
               EldergroveBiomeSpreader.setEldergroveBiome(level, surface);
            }
         }
      }
   }

   private static BlockPos findNaturalSurface(ServerLevel level, BlockPos columnPos, int centerY) {
      int minY = Math.max(level.getMinBuildHeight(), centerY - 10);
      int maxY = Math.min(level.getMaxBuildHeight() - 1, centerY + 10);

      for (int y = maxY; y >= minY; y--) {
         BlockPos pos = new BlockPos(columnPos.getX(), y, columnPos.getZ());
         if (EldergroveBiomeSpreader.isNaturalSurface(level, pos)) {
            return pos;
         }
      }

      return null;
   }
}
