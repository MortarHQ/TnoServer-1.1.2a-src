package twilightforest.world.components.structures.selectors;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.level.block.Block;
import twilightforest.beanification.Component;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.util.SimpleRandomBlockSelector;

@Component
public class CastleRandomBlockSelectorFactory {
   public SimpleRandomBlockSelector make() {
      return new SimpleRandomBlockSelector(
         List.of(
            Pair.of(((Block)TFBlocks.WORN_CASTLE_BRICK.get()).defaultBlockState(), 0.1F),
            Pair.of(((Block)TFBlocks.CRACKED_CASTLE_BRICK.get()).defaultBlockState(), 0.1F),
            Pair.of(((Block)TFBlocks.CASTLE_BRICK.get()).defaultBlockState(), 0.8F)
         )
      );
   }
}
