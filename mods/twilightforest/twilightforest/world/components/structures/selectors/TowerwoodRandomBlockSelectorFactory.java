package twilightforest.world.components.structures.selectors;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.level.block.Block;
import twilightforest.beanification.Component;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.util.SimpleRandomBlockSelector;

@Component
public class TowerwoodRandomBlockSelectorFactory {
   public SimpleRandomBlockSelector make() {
      return new SimpleRandomBlockSelector(
         List.of(
            Pair.of(((Block)TFBlocks.CRACKED_TOWERWOOD.get()).defaultBlockState(), 0.1F),
            Pair.of(((Block)TFBlocks.MOSSY_TOWERWOOD.get()).defaultBlockState(), 0.1F),
            Pair.of(((Block)TFBlocks.INFESTED_TOWERWOOD.get()).defaultBlockState(), 0.025F),
            Pair.of(((Block)TFBlocks.TOWERWOOD.get()).defaultBlockState(), 0.775F)
         )
      );
   }
}
