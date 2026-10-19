package twilightforest.world.components.layer.vanillalegacy.context;

import twilightforest.world.components.layer.vanillalegacy.Area;

public interface BigContext<R extends Area> extends Context {
   R createResult(Area var1);

   R createResult(Area var1, R var2);

   R createResult(Area var1, R var2, R var3);
}
