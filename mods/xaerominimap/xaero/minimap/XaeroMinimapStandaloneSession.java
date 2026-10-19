package xaero.minimap;

import java.io.IOException;
import net.minecraft.client.multiplayer.ClientPacketListener;
import xaero.common.HudMod;
import xaero.common.XaeroMinimapSession;
import xaero.minimap.controls.MinimapControlsHandler;

public class XaeroMinimapStandaloneSession extends XaeroMinimapSession {
   public XaeroMinimapStandaloneSession(HudMod modMain) {
      super(modMain);
   }

   @Override
   public void init(ClientPacketListener connection) throws IOException {
      super.init(connection);
      this.controls = new MinimapControlsHandler(this.modMain, this);
   }
}
