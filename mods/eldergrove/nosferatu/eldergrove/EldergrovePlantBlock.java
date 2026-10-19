package nosferatu.eldergrove;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EldergrovePlantBlock extends BushBlock {
   public EldergrovePlantBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return null;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.DIRT)
         || state.is(Blocks.GRASS_BLOCK)
         || state.is(Blocks.PODZOL)
         || state.is(Blocks.MOSS_BLOCK)
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS_FAINT.get())
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS.get())
         || state.is((Block)EldergroveBlocks.ELDERGROVE_GRASS_DEEP.get())
         || state.is((Block)EldergroveBlocks.TAINTED_SOIL.get())
         || state.is((Block)EldergroveBlocks.TAINTED_CRUST.get());
   }
}
