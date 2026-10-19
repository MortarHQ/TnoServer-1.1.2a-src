package com.finderfeed.fdlib.systems.music.packets;

import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.music.FDMusicSystem;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:end_music_packet")
public class FDMusicEndPacket extends FDPacket {
   private UUID data;
   private int fadeOutTime;

   public FDMusicEndPacket(UUID uuid, int fadeOutTime) {
      this.data = uuid;
      this.fadeOutTime = fadeOutTime;
   }

   public FDMusicEndPacket(RegistryFriendlyByteBuf buf) {
      this.data = buf.readUUID();
      this.fadeOutTime = buf.readInt();
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUUID(this.data);
      buf.writeInt(this.fadeOutTime);
   }

   @Override
   public void clientAction(IPayloadContext context) {
      FDMusicSystem.endMusic(this.data, this.fadeOutTime);
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
