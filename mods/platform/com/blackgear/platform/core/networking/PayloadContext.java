package com.blackgear.platform.core.networking;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.world.entity.player.Player;

public interface PayloadContext {
   Player player();

   CompletableFuture<Void> enqueueWork(Runnable var1);

   <T> CompletableFuture<T> enqueueWork(Supplier<T> var1);
}
