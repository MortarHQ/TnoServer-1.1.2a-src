package fuzs.puzzleslib.api.core.v1;

import fuzs.puzzleslib.impl.core.CommonFactories;
import java.util.List;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface Proxy {
   Proxy INSTANCE = ModLoaderEnvironment.INSTANCE.isClient() ? CommonFactories.INSTANCE.getClientProxy() : CommonFactories.INSTANCE.getServerProxy();

   Player getClientPlayer();

   Level getClientLevel();

   ClientPacketListener getClientPacketListener();

   boolean hasControlDown();

   boolean hasShiftDown();

   boolean hasAltDown();

   List<Component> splitTooltipLines(Component var1);
}
