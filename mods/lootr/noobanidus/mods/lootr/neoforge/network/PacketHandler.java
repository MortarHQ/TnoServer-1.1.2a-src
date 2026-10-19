package noobanidus.mods.lootr.neoforge.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketCloseCart;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketCloseContainer;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketOpenCart;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketOpenContainer;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketPerformBreakEffect;
import noobanidus.mods.lootr.neoforge.network.toClient.PacketRefreshSection;

@EventBusSubscriber(modid = "lootr")
public class PacketHandler {
   @SubscribeEvent
   public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("lootr-1.21.0-1");
      registerClientToServer(new PacketHandler.PacketRegistrar(registrar, true));
      registerServerToClient(new PacketHandler.PacketRegistrar(registrar, false));
   }

   protected static void registerClientToServer(PacketHandler.PacketRegistrar registrar) {
   }

   protected static void registerServerToClient(PacketHandler.PacketRegistrar registrar) {
      registrar.play(PacketOpenCart.TYPE, PacketOpenCart.STREAM_CODEC);
      registrar.play(PacketOpenContainer.TYPE, PacketOpenContainer.STREAM_CODEC);
      registrar.play(PacketCloseCart.TYPE, PacketCloseCart.STREAM_CODEC);
      registrar.play(PacketCloseContainer.TYPE, PacketCloseContainer.STREAM_CODEC);
      registrar.play(PacketRefreshSection.TYPE, PacketRefreshSection.STREAM_CODEC);
      registrar.play(PacketPerformBreakEffect.TYPE, PacketPerformBreakEffect.STREAM_CODEC);
   }

   protected record PacketRegistrar(PayloadRegistrar registrar, boolean toServer) {
      public <MSG extends ILootrNeoForgePacket> void play(Type<MSG> type, StreamCodec<? super RegistryFriendlyByteBuf, MSG> reader) {
         if (this.toServer) {
            this.registrar.playToServer(type, reader, ILootrNeoForgePacket::handle);
         } else {
            this.registrar.playToClient(type, reader, ILootrNeoForgePacket::handle);
         }
      }
   }
}
