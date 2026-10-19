package cc.sighs.oelib.platform;

import java.nio.file.Path;
import java.util.Collection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public interface IPlatform {
   Path getConfigPath();

   boolean isDevelopmentEnv();

   boolean isClient();

   boolean isServer();

   boolean isFabric();

   boolean isNeoForge();

   boolean isModLoaded(String var1);

   Collection<ServerPlayer> getAllPlayers(MinecraftServer var1);

   MinecraftServer getCurrentServer();
}
