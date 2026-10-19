package com.natamus.collective_common_fabric.implementations.networking.data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_8710;
import net.minecraft.class_9139;
import net.minecraft.class_8710.class_9154;

public record PacketContainer<T>(
   class_9154<? extends class_8710> type,
   Class<T> classType,
   BiConsumer<T, class_2540> encoder,
   Function<class_2540, T> decoder,
   Consumer<PacketContext<T>> handler
) {
   public PacketContainer(
      class_2960 id, Class<T> classType, BiConsumer<T, class_2540> encoder, Function<class_2540, T> decoder, Consumer<PacketContext<T>> handle
   ) {
      this(new class_9154(id), classType, encoder, decoder, handle);
   }

   public <K extends class_8710> class_9154<K> getType() {
      return (class_9154<K>)this.type();
   }

   public class_9139<class_2540, CommonPacketWrapper> getCodec() {
      return class_8710.method_56484(
         (packet, buf) -> this.encoder().accept((T)packet.packet(), buf), buf -> new CommonPacketWrapper<>(this, this.decoder().apply(buf))
      );
   }
}
