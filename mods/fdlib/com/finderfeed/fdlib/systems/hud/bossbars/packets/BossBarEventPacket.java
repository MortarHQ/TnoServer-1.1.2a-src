package com.finderfeed.fdlib.systems.hud.bossbars.packets;

import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.hud.bossbars.FDBossBar;
import com.finderfeed.fdlib.systems.hud.bossbars.FDBossbars;
import com.finderfeed.fdlib.systems.hud.bossbars.FDServerBossBar;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:boss_bar_event")
public class BossBarEventPacket extends FDPacket {
   private UUID uuid;
   private int event;
   private int data;

   public BossBarEventPacket(FDServerBossBar serverBossBar, int event, int data) {
      this.uuid = serverBossBar.getUUID();
      this.event = event;
      this.data = data;
   }

   public BossBarEventPacket(FriendlyByteBuf buf) {
      this.uuid = buf.readUUID();
      this.event = buf.readInt();
      this.data = buf.readInt();
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeUUID(this.uuid);
      buf.writeInt(this.event);
      buf.writeInt(this.data);
   }

   @Override
   public void clientAction(IPayloadContext context) {
      FDBossBar bossBar = FDBossbars.getBossBar(this.uuid);
      if (bossBar != null) {
         bossBar.hanldeBarEvent(this.event, this.data);
      }
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
