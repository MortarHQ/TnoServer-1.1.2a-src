package twilightforest.world.components.layer.vanillalegacy;

import java.util.function.LongFunction;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;

public interface BiomeLayerFactory {
   LazyArea build(LongFunction<LazyAreaContext> var1);

   BiomeLayerType getType();
}
