package xaero.common;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class PlatformContextLoaderClientOnlyNeoForge extends PlatformContextLoaderClientOnly {
   @Override
   public void preInit(String modId, IXaeroMinimap modMain) {
      ModContainer modContainer = (ModContainer)ModList.get().getModContainerById(modId).get();
      modContainer.registerExtensionPoint(
         IConfigScreenFactory.class, (IConfigScreenFactory)(mod, current) -> modMain.getGuiHelper().getMainSettingsScreen(current)
      );
   }
}
