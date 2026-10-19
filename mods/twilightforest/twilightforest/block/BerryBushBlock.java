package twilightforest.block;

import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.ItemAbilities;
import twilightforest.data.tags.BlockTagGenerator;

public class BerryBushBlock extends TFBushBlock implements BonemealableBlock {
   public BerryBushBlock(ResourceKey<LootTable> berryTable, Properties properties) {
      super(berryTable, properties);
   }

   @Override
   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      super.randomTick(state, level, pos, random);
      int height = (int)IntStream.iterate(1, n -> level.getBlockState(pos.below(n)).getBlock() == this, n -> n + 1).count();
      if (random.nextInt(20) == 0 && height < 2 && this.canGrowAt(state, level, pos)) {
         this.tryGrowUpwards(state, level, pos, random);
      }
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter getter, BlockPos pos) {
      return player.getMainHandItem().canPerformAction(ItemAbilities.SHEARS_DIG) ? 0.2F : super.getDestroyProgress(state, player, getter, pos);
   }

   @Override
   public boolean canBePlacedAt(BlockState state) {
      return state.is(BlockTagGenerator.TF_BERRY_BUSHES_SURVIVE);
   }

   protected void tryGrowUpwards(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (random.nextInt(3) == 0 && (Integer)state.getValue(AGE) >= 2) {
         BlockState aboveState = level.getBlockState(pos.above());
         if (aboveState.isAir()) {
            level.setBlock(pos.above(), state.getBlock().defaultBlockState(), 2);
         } else if (aboveState.is(Blocks.SNOW)) {
            level.setBlock(
               pos.above(), (BlockState)state.getBlock().defaultBlockState().setValue(SNOW_LAYERS, (Integer)aboveState.getValue(SnowLayerBlock.LAYERS)), 2
            );
         }
      }
   }

   @Override
   protected boolean canGrowAt(BlockState state, LevelReader level, BlockPos pos) {
      return level.getRawBrightness(pos, 0) >= 8 && super.canGrowAt(state, level, pos);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return (Integer)state.getValue(AGE) < 2 || level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above()).is(Blocks.SNOW);
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      int age = (Integer)state.getValue(AGE);
      if (age < 2) {
         this.grow(state, level, pos, Math.min((Integer)state.getValue(AGE) + 1 + random.nextInt(2), 2));
      }

      this.tryGrowUpwards(state, level, pos, random);
   }
}
