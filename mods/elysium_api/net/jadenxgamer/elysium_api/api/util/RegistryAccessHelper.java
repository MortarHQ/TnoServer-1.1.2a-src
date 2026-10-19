package net.jadenxgamer.elysium_api.api.util;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public final class RegistryAccessHelper {
   private RegistryAccessHelper() {
   }

   public static Optional<RegistryAccess> getServer() {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      return server == null ? Optional.empty() : Optional.of(server.registryAccess());
   }

   public static Optional<RegistryAccess> getClient() {
      if (FMLEnvironment.dist != Dist.CLIENT) {
         return Optional.empty();
      }

      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      return connection == null ? Optional.empty() : Optional.of(connection.registryAccess());
   }

   public static Optional<RegistryAccess> fromLevel(Level level) {
      return level == null ? Optional.empty() : Optional.of(level.registryAccess());
   }

   public static Optional<RegistryAccess> getCurrent() {
      return FMLEnvironment.dist == Dist.CLIENT ? getClient().or(RegistryAccessHelper::getServer) : getServer();
   }

   public static boolean isRegistryAccessAvailable() {
      return FMLEnvironment.dist == Dist.CLIENT ? Minecraft.getInstance().getConnection() != null : ServerLifecycleHooks.getCurrentServer() != null;
   }

   public static boolean isServerAvailable() {
      return ServerLifecycleHooks.getCurrentServer() != null;
   }

   public static boolean isClientAvailable() {
      return FMLEnvironment.dist == Dist.CLIENT && Minecraft.getInstance().getConnection() != null;
   }
}
