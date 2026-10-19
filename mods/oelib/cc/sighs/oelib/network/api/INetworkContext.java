package cc.sighs.oelib.network.api;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkContext {
   boolean isClientSide();

   boolean isServerSide();

   ServerPlayer sender();

   Minecraft client();

   void enqueueWork(Runnable var1);

   RegistryAccess registryAccess();
}
