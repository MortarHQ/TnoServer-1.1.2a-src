package com.natamus.collective_common_fabric.implementations.networking.data;

import net.minecraft.class_1657;

public record PacketContext<T>(class_1657 sender, T message, Side side) {
   public PacketContext(T message, Side side) {
      this(null, message, side);
   }
}
