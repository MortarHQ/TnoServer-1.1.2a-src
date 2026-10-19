package it.hurts.octostudios.octolib.module.config.network;

import dev.architectury.networking.NetworkManager.PacketContext;
import it.hurts.octostudios.octolib.OctoLib;
import it.hurts.octostudios.octolib.module.network.Packet;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class UnholyAbominationPacket extends Packet {
   public static final Type<UnholyAbominationPacket> TYPE = Packet.createType("octolib", "no_just_no");
   public static final StreamCodec<RegistryFriendlyByteBuf, UnholyAbominationPacket> STREAM_CODEC = Packet.createCodec(
      UnholyAbominationPacket::write, UnholyAbominationPacket::new
   );
   String path;

   public UnholyAbominationPacket(RegistryFriendlyByteBuf buf) {
      this.path = buf.readUtf();
   }

   public UnholyAbominationPacket(String path) {
      this.path = path;
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUtf(this.path);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   protected void handleClient(PacketContext packetContext) {
      try {
         Class<?> clazz = Class.forName(this.path);
         if (clazz.getConstructor().newInstance() instanceof Screen screen) {
            Minecraft.getInstance().setScreen(screen);
         } else {
            OctoLib.LOGGER.warn("ok that's not a screen, idk what it is, but not a screen: {}", this.path);
         }
      } catch (ClassNotFoundException e) {
         OctoLib.LOGGER.warn("No class has been found: {}", this.path);
      } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
         OctoLib.LOGGER.warn("no: {}", this.path);
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
