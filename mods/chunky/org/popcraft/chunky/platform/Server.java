package org.popcraft.chunky.platform;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.popcraft.chunky.integration.Integration;

public interface Server {
   Map<String, Integration> getIntegrations();

   Optional<World> getWorld(String var1);

   List<World> getWorlds();

   int getMaxWorldSize();

   Sender getConsole();

   Collection<Player> getPlayers();

   Optional<Player> getPlayer(String var1);

   Config getConfig();
}
