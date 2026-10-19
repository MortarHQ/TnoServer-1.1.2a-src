package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveClientEvents;
import java.util.function.Supplier;
import net.minecraft.class_5321;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_6880;
import net.minecraft.class_761;
import net.minecraft.class_638.class_5271;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_638.class, priority = 1001)
public class ClientLevelMixin {
   @Inject(
      method = "<init>(Lnet/minecraft/client/multiplayer/ClientPacketListener;Lnet/minecraft/client/multiplayer/ClientLevel$ClientLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;IILjava/util/function/Supplier;Lnet/minecraft/client/renderer/LevelRenderer;ZJ)V",
      at = @At("TAIL")
   )
   public void ClientLevelMixin_init(
      class_634 clientPacketListener,
      class_5271 clientLevelData,
      class_5321<?> resourceKey,
      class_6880<?> holder,
      int i,
      int j,
      Supplier<?> supplier,
      class_761 levelRenderer,
      boolean bl,
      long l,
      CallbackInfo ci
   ) {
      ((CollectiveClientEvents.Client_World_Load)CollectiveClientEvents.CLIENT_WORLD_LOAD.invoker()).onClientWorldLoad((class_638)this);
   }
}
