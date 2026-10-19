package xaero.map;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import xaero.lib.client.gui.config.context.BuiltInEditConfigScreenContexts;
import xaero.map.gui.GuiWorldMapSettings;

public class WorldMapClientOnlyNeoForge extends WorldMapClientOnly {
   @Override
   public void preInit(String modId) {
      super.preInit(modId);
      ModContainer modContainer = (ModContainer)ModList.get().getModContainerById(modId).get();
      modContainer.registerExtensionPoint(
         IConfigScreenFactory.class, (IConfigScreenFactory)(mc, current) -> new GuiWorldMapSettings(current, BuiltInEditConfigScreenContexts.CLIENT)
      );
   }
}
