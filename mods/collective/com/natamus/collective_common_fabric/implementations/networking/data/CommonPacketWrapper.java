package com.natamus.collective_common_fabric.implementations.networking.data;

import net.minecraft.class_2540;
import net.minecraft.class_8710;
import net.minecraft.class_8710.class_9154;
import org.jetbrains.annotations.NotNull;

public record CommonPacketWrapper<T>(PacketContainer<T> container, T packet) implements class_8710 {
   public void encode(class_2540 buf) {
      this.container().encoder().accept(this.packet(), buf);
   }

   @NotNull
   public class_9154<? extends class_8710> method_56479() {
      return this.container.type();
   }
}
