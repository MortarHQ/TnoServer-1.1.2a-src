package com.chronodawn.mixin;

import com.chronodawn.api.ChronoDawnTimeHolder;
import com.chronodawn.data.ChronoDawnTimeData;
import com.chronodawn.registry.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerLevel.class, priority = 1100)
public abstract class ServerLevelSetTimeMixin {
   @Inject(method = "setDayTime(J)V", at = @At("HEAD"), cancellable = true)
   private void interceptSetDayTime(long time, CallbackInfo ci) {
      ServerLevel level = (ServerLevel)this;
      if (level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         ChronoDawnTimeHolder holder = (ChronoDawnTimeHolder)level;
         holder.chronodawn$setIndependentTime(time);
         ChronoDawnTimeData data = ChronoDawnTimeData.get(level);
         data.setIndependentTime(time);
         ci.cancel();
      }
   }
}
