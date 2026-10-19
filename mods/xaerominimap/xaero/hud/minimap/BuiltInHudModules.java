package xaero.hud.minimap;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import xaero.common.gui.GuiMinimapMain;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.module.MinimapRenderer;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.module.HudModule;
import xaero.hud.module.ModuleManager;

public class BuiltInHudModules {
   public static final HudModule<MinimapSession> MINIMAP = new HudModule<>(
      ResourceLocation.fromNamespaceAndPath("xaerominimap", "minimap"),
      Component.translatable("gui.xaero_minimap"),
      WaypointsManager::new,
      MinimapRenderer::new,
      GuiMinimapMain::new,
      MinimapProfiledConfigOptions.DISPLAY_MINIMAP
   );

   public static void addAll(ModuleManager manager) {
      manager.register(MINIMAP);
   }
}
