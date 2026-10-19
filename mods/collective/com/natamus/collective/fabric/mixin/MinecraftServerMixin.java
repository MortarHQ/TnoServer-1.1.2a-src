package com.natamus.collective.fabric.mixin;

import com.mojang.datafixers.DataFixer;
import com.natamus.collective.fabric.callbacks.CollectiveLifecycleEvents;
import com.natamus.collective.fabric.callbacks.CollectiveMinecraftServerEvents;
import com.natamus.collective.fabric.callbacks.CollectiveWorldEvents;
import java.net.Proxy;
import net.minecraft.class_3218;
import net.minecraft.class_3283;
import net.minecraft.class_3950;
import net.minecraft.class_5268;
import net.minecraft.class_6904;
import net.minecraft.class_7497;
import net.minecraft.class_32.class_5143;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MinecraftServer.class, priority = 1001)
public class MinecraftServerMixin {
   @Inject(
      method = "<init>(Ljava/lang/Thread;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/server/WorldStem;Ljava/net/Proxy;Lcom/mojang/datafixers/DataFixer;Lnet/minecraft/server/Services;Lnet/minecraft/server/level/progress/ChunkProgressListenerFactory;)V",
      at = @At("TAIL")
   )
   public void MinecraftServer_init(
      Thread thread,
      class_5143 levelStorageAccess,
      class_3283 packRepository,
      class_6904 worldStem,
      Proxy proxy,
      DataFixer dataFixer,
      class_7497 services,
      class_3950 chunkProgressListenerFactory,
      CallbackInfo ci
   ) {
      ((MinecraftServer)this)
         .execute(() -> ((CollectiveLifecycleEvents.Minecraft_Loaded)CollectiveLifecycleEvents.MINECRAFT_LOADED.invoker()).onMinecraftLoad(false));
   }

   @Inject(method = "setInitialSpawn", at = @At("RETURN"))
   private static void MinecraftServer_setInitialSpawn(class_3218 serverLevel, class_5268 serverLevelData, boolean bl, boolean bl2, CallbackInfo ci) {
      ((CollectiveMinecraftServerEvents.Set_Spawn)CollectiveMinecraftServerEvents.WORLD_SET_SPAWN.invoker()).onSetSpawn(serverLevel, serverLevelData);
   }

   @ModifyVariable(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;close()V", ordinal = 0))
   public class_3218 MinecraftServer_stopServer(class_3218 serverlevel1) {
      ((CollectiveWorldEvents.World_Unload)CollectiveWorldEvents.WORLD_UNLOAD.invoker()).onWorldUnload(serverlevel1);
      return serverlevel1;
   }
}
