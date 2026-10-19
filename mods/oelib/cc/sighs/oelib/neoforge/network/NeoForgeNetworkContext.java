package cc.sighs.oelib.neoforge.network;

import cc.sighs.oelib.network.api.INetworkContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworkContext implements INetworkContext {
   private final IPayloadContext context;

   public NeoForgeNetworkContext(IPayloadContext context) {
      this.context = context;
   }

   @Override
   public boolean isClientSide() {
      return this.context.flow().isClientbound();
   }

   @Override
   public boolean isServerSide() {
      return this.context.flow().isServerbound();
   }

   @Override
   public ServerPlayer sender() {
      return this.isServerSide() && this.context.player() instanceof ServerPlayer serverPlayer ? serverPlayer : null;
   }

   @Override
   public Minecraft client() {
      return this.isClientSide() ? Minecraft.getInstance() : null;
   }

   @Override
   public void enqueueWork(Runnable task) {
      this.context.enqueueWork(task);
   }

   @Override
   public RegistryAccess registryAccess() {
      if (this.isServerSide() && this.context.player() instanceof ServerPlayer serverPlayer) {
         return serverPlayer.registryAccess();
      }

      Minecraft mc = Minecraft.getInstance();
      return (RegistryAccess)(mc.level != null ? mc.level.registryAccess() : RegistryAccess.EMPTY);
   }
}
