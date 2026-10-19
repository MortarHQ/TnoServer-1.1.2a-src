package com.chronodawn.mixin;

import com.chronodawn.ChronoDawn;
import com.chronodawn.api.ChronoDawnTimeHolder;
import com.chronodawn.data.ChronoDawnTimeData;
import com.chronodawn.registry.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Level.class, priority = 1100)
public abstract class LevelGetTimeMixin implements ChronoDawnTimeHolder {
   @Unique
   private Long chronodawn$independentTime = null;

   @Override
   public Long chronodawn$getIndependentTime() {
      return this.chronodawn$independentTime;
   }

   @Override
   public void chronodawn$setIndependentTime(Long time) {
      this.chronodawn$independentTime = time;
   }

   @Inject(method = "getDayTime()J", at = @At("RETURN"), cancellable = true)
   private void interceptGetDayTime(CallbackInfoReturnable<Long> cir) {
      Level level = (Level)this;
      if (level instanceof ServerLevel serverLevel && serverLevel.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
         if (this.chronodawn$independentTime == null) {
            ChronoDawnTimeData data = ChronoDawnTimeData.get(serverLevel);
            this.chronodawn$independentTime = data.getIndependentTime();
            if (this.chronodawn$independentTime == 0L) {
               this.chronodawn$independentTime = (Long)cir.getReturnValue();
               data.setIndependentTime(this.chronodawn$independentTime);
               ChronoDawn.LOGGER.debug("LevelGetTimeMixin [SERVER]: Initialized independent time to {} (from shared time)", this.chronodawn$independentTime);
            } else {
               ChronoDawn.LOGGER.debug("LevelGetTimeMixin [SERVER]: Loaded independent time = {} (from SavedData)", this.chronodawn$independentTime);
            }
         }

         cir.setReturnValue(this.chronodawn$independentTime);
      }
   }

   @Inject(method = "isDay()Z", at = @At("RETURN"), cancellable = true)
   private void interceptIsDay(CallbackInfoReturnable<Boolean> cir) {
      Level level = (Level)this;
      if (level instanceof ServerLevel && level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION) && this.chronodawn$independentTime != null) {
         long timeOfDay = this.chronodawn$independentTime % 24000L;
         boolean isDay = timeOfDay < 12000L;
         cir.setReturnValue(isDay);
      }
   }

   @Inject(method = "getSkyDarken()I", at = @At("RETURN"), cancellable = true)
   private void interceptGetSkyDarken(CallbackInfoReturnable<Integer> cir) {
      Level level = (Level)this;
      if (level instanceof ServerLevel && level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION) && this.chronodawn$independentTime != null) {
         long timeOfDay = this.chronodawn$independentTime % 24000L;
         int skyDarken = this.calculateSkyDarken(timeOfDay);
         cir.setReturnValue(skyDarken);
      }
   }

   @Unique
   private int calculateSkyDarken(long timeOfDay) {
      return timeOfDay < 12000L ? 0 : 11;
   }
}
