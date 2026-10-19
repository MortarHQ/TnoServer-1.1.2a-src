package twilightforest.world.components.structures.selectors;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.level.block.Block;
import twilightforest.beanification.Component;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.util.SimpleRandomBlockSelector;

@Component
public class MazestoneRandomBlockSelectoryFactory {
   public SimpleRandomBlockSelector make() {
      return new SimpleRandomBlockSelector(
         List.of(
            Pair.of(((Block)TFBlocks.MOSSY_MAZESTONE.get()).defaultBlockState(), 0.2F),
            Pair.of(((Block)TFBlocks.CRACKED_MAZESTONE.get()).defaultBlockState(), 0.3F),
            Pair.of(((Block)TFBlocks.MAZESTONE_BRICK.get()).defaultBlockState(), 0.5F)
         )
      );
   }
}
