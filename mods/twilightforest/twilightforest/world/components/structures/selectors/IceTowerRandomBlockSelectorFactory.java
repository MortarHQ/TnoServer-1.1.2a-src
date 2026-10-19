package twilightforest.world.components.structures.selectors;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.level.block.Block;
import twilightforest.beanification.Component;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.util.SimpleRandomBlockSelector;

@Component
public class IceTowerRandomBlockSelectorFactory {
   public SimpleRandomBlockSelector make() {
      return new SimpleRandomBlockSelector(List.of(Pair.of(((Block)TFBlocks.AURORA_BLOCK.get()).defaultBlockState(), 1.0F)));
   }
}
