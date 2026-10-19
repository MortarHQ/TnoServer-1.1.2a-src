package xaero.hud.packet.basic;

import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import xaero.common.minimap.mcworld.MinimapClientWorldDataHelper;

public class ClientboundRulesPacket {
   public final boolean allowCaveModeOnServer;
   public final boolean allowNetherCaveModeOnServer;
   public final boolean allowRadarOnServer;

   public ClientboundRulesPacket(boolean allowCaveModeOnServer, boolean allowNetherCaveModeOnServer, boolean allowRadarOnServer) {
      this.allowCaveModeOnServer = allowCaveModeOnServer;
      this.allowNetherCaveModeOnServer = allowNetherCaveModeOnServer;
      this.allowRadarOnServer = allowRadarOnServer;
   }

   public void write(FriendlyByteBuf u) {
      CompoundTag nbt = new CompoundTag();
      nbt.putBoolean("cm", this.allowCaveModeOnServer);
      nbt.putBoolean("ncm", this.allowNetherCaveModeOnServer);
      nbt.putBoolean("r", this.allowRadarOnServer);
      u.writeNbt(nbt);
   }

   public static ClientboundRulesPacket read(FriendlyByteBuf buffer) {
      CompoundTag nbt = buffer.readNbt();
      return new ClientboundRulesPacket(nbt.getBoolean("cm"), nbt.getBoolean("ncm"), nbt.getBoolean("r"));
   }

   public static class ClientHandler implements Consumer<ClientboundRulesPacket> {
      public void accept(ClientboundRulesPacket message) {
         MinimapClientWorldDataHelper.getCurrentWorldData().setSyncedRules(message);
      }
   }
}
