package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.network.ActionAddDataReceiver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.network.packet.ClientBoundDataPacket;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.network.Network")
@Document("vanilla/api/network/Network")
public class CTNetwork {
   @ZenCodeGlobals.Global("network")
   public static final CTNetwork INSTANCE = new CTNetwork();
   public final Map<String, List<CTNetworkReceiver>> clientReceivers = new HashMap<>();

   private CTNetwork() {
   }

   @ZenCodeType.Method
   public void sendTo(ServerPlayer player, String id, IData data) {
      Services.NETWORK.sendPacket(player, new ClientBoundDataPacket(id, data));
   }

   @ZenCodeType.Method
   public void onData(String id, CTNetworkReceiver receiver) {
      CraftTweakerAPI.apply(new ActionAddDataReceiver(id, receiver));
   }

   public void receive(String id, IData data, Player player) {
      if (player.level().isClientSide()) {
         CTNetworkContext context = new CTNetworkContext(id, player);

         for (CTNetworkReceiver receiver : this.clientReceivers.getOrDefault(id, List.of())) {
            receiver.receive(data, context);
         }
      }
   }
}
