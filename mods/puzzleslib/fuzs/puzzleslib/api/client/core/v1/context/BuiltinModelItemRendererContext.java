package fuzs.puzzleslib.api.client.core.v1.context;

import fuzs.puzzleslib.api.client.init.v1.BuiltinItemRenderer;
import fuzs.puzzleslib.api.client.init.v1.ReloadingBuiltInItemRenderer;
import net.minecraft.world.level.ItemLike;

public interface BuiltinModelItemRendererContext {
   void registerItemRenderer(BuiltinItemRenderer var1, ItemLike... var2);

   void registerItemRenderer(ReloadingBuiltInItemRenderer var1, ItemLike... var2);
}
