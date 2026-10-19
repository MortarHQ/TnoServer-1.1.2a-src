package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.entity.player.Player;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("vanilla/api/network/NetworkContext")
@Document("vanilla/api/network/NetworkContext")
public class CTNetworkContext {
   private final String id;
   private final Player player;

   public CTNetworkContext(String id, Player player) {
      this.id = id;
      this.player = player;
   }

   @ZenCodeType.Getter("id")
   public String id() {
      return this.id;
   }

   @ZenCodeType.Getter("player")
   public Player player() {
      return this.player;
   }
}
