package me.wesley1808.servercore.mixin.features.activation_range;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import java.util.function.BooleanSupplier;
import me.wesley1808.servercore.common.activation_range.ActivationRange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
   @Shadow
   @Final
   private MinecraftServer server;

   @Inject(
      method = "tick(Ljava/util/function/BooleanSupplier;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/entity/EntityTickList;forEach(Ljava/util/function/Consumer;)V")
   )
   private void servercore$activateEntities(BooleanSupplier booleanSupplier, CallbackInfo ci) {
      int currentTick = this.server.getTickCount();
      if (currentTick % 20 == 0) {
         ActivationRange.activateEntities((ServerLevel)this, currentTick);
      }
   }

   @WrapWithCondition(
      method = "tickNonPassenger(Lnet/minecraft/world/entity/Entity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V")
   )
   private boolean servercore$shouldTickEntity(Entity entity) {
      if (ActivationRange.checkIfActive(entity, this.server.getTickCount())) {
         entity.servercore$setInactive(false);
         entity.tickCount++;
         return true;
      } else {
         entity.servercore$setInactive(true);
         entity.servercore$inactiveTick();
         return false;
      }
   }

   @WrapWithCondition(
      method = "tickPassenger(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;rideTick()V")
   )
   private boolean servercore$shouldTickPassenger(Entity passenger, Entity vehicle, Entity ignored) {
      if (ActivationRange.checkIfActive(passenger, this.server.getTickCount())) {
         passenger.servercore$setInactive(false);
         passenger.tickCount++;
         return true;
      } else {
         passenger.setDeltaMovement(Vec3.ZERO);
         passenger.servercore$setInactive(true);
         passenger.servercore$inactiveTick();
         vehicle.positionRider(passenger);
         return false;
      }
   }

   @Redirect(
      method = {
            "tickNonPassenger(Lnet/minecraft/world/entity/Entity;)V", "tickPassenger(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;)V"
      },
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/Entity;tickCount:I", opcode = 181)
   )
   private void servercore$redirectTickCount(Entity entity, int value) {
      entity.servercore$incFullTickCount();
   }
}
