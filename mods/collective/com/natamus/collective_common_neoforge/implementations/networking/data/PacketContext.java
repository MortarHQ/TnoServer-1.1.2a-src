package com.natamus.collective_common_neoforge.implementations.networking.data;

import net.minecraft.world.entity.player.Player;

public record PacketContext<T>(Player sender, T message, Side side) {
   public PacketContext(T message, Side side) {
      this(null, message, side);
   }
}
