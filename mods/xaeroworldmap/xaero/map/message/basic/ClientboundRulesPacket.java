package xaero.map.message.basic;

import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import xaero.map.mcworld.WorldMapClientWorldDataHelper;

public class ClientboundRulesPacket {
   public final boolean allowCaveModeOnServer;
   public final boolean allowNetherCaveModeOnServer;

   public ClientboundRulesPacket(boolean allowCaveModeOnServer, boolean allowNetherCaveModeOnServer) {
      this.allowCaveModeOnServer = allowCaveModeOnServer;
      this.allowNetherCaveModeOnServer = allowNetherCaveModeOnServer;
   }

   public void write(FriendlyByteBuf u) {
      CompoundTag nbt = new CompoundTag();
      nbt.putBoolean("cm", this.allowCaveModeOnServer);
      nbt.putBoolean("ncm", this.allowNetherCaveModeOnServer);
      u.writeNbt(nbt);
   }

   public static ClientboundRulesPacket read(FriendlyByteBuf buffer) {
      CompoundTag nbt = buffer.readNbt();
      return new ClientboundRulesPacket(nbt.getBoolean("cm"), nbt.getBoolean("ncm"));
   }

   public static class ClientHandler implements Consumer<ClientboundRulesPacket> {
      public void accept(ClientboundRulesPacket message) {
         WorldMapClientWorldDataHelper.getCurrentWorldData().setSyncedRules(message);
      }
   }
}
