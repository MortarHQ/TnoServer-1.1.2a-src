package com.finderfeed.fdlib.systems.impact_frames;

import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:impact_frames_packet")
public class ImpactFramesPacket extends FDPacket {
   private List<ImpactFrame> frames;

   public ImpactFramesPacket(List<ImpactFrame> frames) {
      this.frames = frames;
   }

   public ImpactFramesPacket(FriendlyByteBuf buf) {
      this.frames = new ArrayList<>();
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         this.frames.add((ImpactFrame)ImpactFrame.STREAM_CODEC.decode(buf));
      }
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.frames.size());

      for (ImpactFrame frame : this.frames) {
         ImpactFrame.STREAM_CODEC.encode(buf, frame);
      }
   }

   @Override
   public void clientAction(IPayloadContext context) {
      this.frames.forEach(ImpactFramesHandler::addImpactFrame);
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
