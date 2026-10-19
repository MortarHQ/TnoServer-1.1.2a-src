package org.popcraft.chunky.platform;

import java.util.UUID;
import org.popcraft.chunky.platform.util.Location;

public interface Player extends Sender {
   UUID getUUID();

   void teleport(Location var1);

   void sendActionBar(String var1);
}
