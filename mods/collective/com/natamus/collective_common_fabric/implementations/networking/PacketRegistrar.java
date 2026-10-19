package com.natamus.collective_common_fabric.implementations.networking;

import com.natamus.collective_common_fabric.implementations.networking.data.PacketContext;
import com.natamus.collective_common_fabric.implementations.networking.data.Side;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_2960;

public interface PacketRegistrar {
   Side getSide();

   <T> PacketRegistrar registerPacket(
      class_2960 var1, Class<T> var2, BiConsumer<T, class_2540> var3, Function<class_2540, T> var4, Consumer<PacketContext<T>> var5
   );
}
