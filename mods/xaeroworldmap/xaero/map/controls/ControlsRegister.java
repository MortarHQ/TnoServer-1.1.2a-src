package xaero.map.controls;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.KeyMapping;

public class ControlsRegister {
   public static final KeyMapping keyOpenMap = new KeyMapping("gui.xaero_open_map", 77, "Xaero's World Map");
   public static final KeyMapping keyOpenSettings = new KeyMapping("gui.xaero_open_settings", 93, "Xaero's World Map");
   public static final KeyMapping keyOpenServerSettings = new KeyMapping("gui.xaero_world_map_server_settings", -1, "Xaero's World Map");
   public static final KeyMapping keyZoomIn = new KeyMapping("gui.xaero_map_zoom_in", -1, "Xaero's World Map");
   public static final KeyMapping keyZoomOut = new KeyMapping("gui.xaero_map_zoom_out", -1, "Xaero's World Map");
   public static final KeyMapping keyQuickConfirm = new KeyMapping("gui.xaero_quick_confirm", 344, "Xaero's World Map");
   public static final KeyMapping keyToggleDimension = new KeyMapping("gui.xaero_toggle_dimension", -1, "Xaero's World Map");
   public static KeyMapping keyToggleTrackedPlayers;
   public static KeyMapping keyTogglePacChunkClaims;
   public final List<KeyMapping> keybindings = Lists.newArrayList(
      new KeyMapping[]{keyOpenMap, keyOpenSettings, keyOpenServerSettings, keyZoomIn, keyZoomOut, keyQuickConfirm, keyToggleDimension}
   );

   public void register(Consumer<KeyMapping> registry) {
      for (KeyMapping kb : this.keybindings) {
         registry.accept(kb);
      }

      try {
         Class.forName("xaero.common.IXaeroMinimap");
      } catch (ClassNotFoundException cnfe) {
         keyToggleTrackedPlayers = new KeyMapping("gui.xaero_toggle_tracked_players", -1, "Xaero's World Map");
         registry.accept(keyToggleTrackedPlayers);
         keyTogglePacChunkClaims = new KeyMapping("gui.xaero_toggle_pac_chunk_claims", -1, "Xaero's World Map");
         registry.accept(keyTogglePacChunkClaims);
      }
   }
}
