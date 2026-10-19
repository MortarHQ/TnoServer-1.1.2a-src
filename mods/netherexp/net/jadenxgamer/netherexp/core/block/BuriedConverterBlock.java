package net.jadenxgamer.netherexp.core.block;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BuriedConverterBlock extends DropExperienceBlock {
   private final Supplier<Block> convertsTo;
   private final Supplier<Double> conversionOdds;

   public BuriedConverterBlock(IntProvider xpRange, Supplier<Block> convertsTo, Supplier<Double> conversionOdds, Properties properties) {
      super(xpRange, properties);
      this.convertsTo = convertsTo;
      this.conversionOdds = conversionOdds;
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(random.nextDouble() > this.conversionOdds.get())) {
         int buried = 0;
         Direction[] directions = Direction.values();

         for (Direction direction : directions) {
            if (!level.getBlockState(pos.relative(direction)).isSolid()) {
               break;
            }

            buried++;
         }

         if (buried >= 6) {
            level.setBlock(pos, this.convertsTo.get().defaultBlockState(), 3);
            level.playSound(null, pos, JNESoundEvents.FOSSILIZATION.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }
}
