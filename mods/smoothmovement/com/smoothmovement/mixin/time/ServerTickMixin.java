package com.smoothmovement.mixin.time;

import com.smoothmovement.SmoothMovement;
import com.smoothmovement.time.ServerTime;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class ServerTickMixin {
   @Shadow
   @Final
   private long[] tickTimesNanos;

   @Shadow
   public abstract int getTickCount();

   @Inject(method = "tickServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getNanos()J", shift = Shift.AFTER, ordinal = 0))
   private void onServerTick(BooleanSupplier p_129871_, CallbackInfo ci) {
      ServerTime.onTick((MinecraftServer)this, this.tickTimesNanos, this.getTickCount());
      if (ServerTime.artificialLagTPS > 0) {
         double lagModifier = 20.0 / ServerTime.artificialLagTPS - 1.0;
         if (lagModifier > 0.1) {
            try {
               long sleep = (long)(
                  SmoothMovement.rand.nextInt((int)(78.0 * lagModifier)) + SmoothMovement.rand.nextInt((int)(14.0 * lagModifier))
                     + 7.0 * lagModifier
                     + ServerTime.artificialLagBaseMS
               );
               Thread.sleep(sleep);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }
}
