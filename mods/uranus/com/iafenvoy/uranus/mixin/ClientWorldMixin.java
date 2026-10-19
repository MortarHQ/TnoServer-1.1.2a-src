package com.iafenvoy.uranus.mixin;

import com.iafenvoy.uranus.event.EntityEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientWorldMixin {
   @Inject(method = "addEntity(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   public void onEntityJoin(Entity entity, CallbackInfo ci) {
      if (EntityEvents.ON_JOIN_WORLD.invoker().onJoinWorld(entity, (Level)this)) {
         ci.cancel();
      }
   }
}
