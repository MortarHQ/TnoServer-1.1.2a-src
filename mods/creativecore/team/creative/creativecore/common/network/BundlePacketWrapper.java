package team.creative.creativecore.common.network;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;

public class BundlePacketWrapper<T extends PacketListener> extends BundlePacket<T> {
   public BundlePacketWrapper(Iterable<Packet<? super T>> list) {
      super(list);
   }

   public void handle(T listener) {
      for (Packet<? super T> packet : this.subPackets()) {
         packet.handle(listener);
      }
   }

   public PacketType<? extends BundlePacket<T>> type() {
      throw new UnsupportedOperationException();
   }
}
