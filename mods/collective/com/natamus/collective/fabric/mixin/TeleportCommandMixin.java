package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import java.util.Set;
import net.minecraft.class_1297;
import net.minecraft.class_2168;
import net.minecraft.class_2709;
import net.minecraft.class_3143;
import net.minecraft.class_3218;
import net.minecraft.class_3143.class_3144;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = class_3143.class, priority = 1001)
public abstract class TeleportCommandMixin {
   @Inject(
      method = "performTeleport(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/server/level/ServerLevel;DDDLjava/util/Set;FFLnet/minecraft/server/commands/TeleportCommand$LookAt;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void performTeleport(
      class_2168 commandSourceStack,
      class_1297 entity,
      class_3218 serverLevel,
      double d,
      double e,
      double f,
      Set<class_2709> set,
      float g,
      float h,
      class_3144 lookAt,
      CallbackInfo ci
   ) {
      if (!((CollectiveEntityEvents.Entity_On_Teleport_Command)CollectiveEntityEvents.ON_ENTITY_TELEPORT_COMMAND.invoker())
         .onTeleportCommand(serverLevel, entity, d, e, f)) {
         ci.cancel();
      }
   }
}
