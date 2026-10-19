package cc.sighs.oelib.config.api;

import net.minecraft.server.level.ServerPlayer;

@FunctionalInterface
public interface IConfigPermissionChecker {
   boolean canUpdate(ServerPlayer var1);
}
