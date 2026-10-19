package com.smoothmovement.mixin.time;

import com.smoothmovement.Compat;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ServerTime;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelDayTimeMixin extends Level {
   protected ServerLevelDayTimeMixin(
      WritableLevelData p_270739_,
      ResourceKey<Level> p_270683_,
      RegistryAccess p_270200_,
      Holder<DimensionType> p_270240_,
      Supplier<ProfilerFiller> p_270692_,
      boolean p_270904_,
      boolean p_270470_,
      long p_270248_,
      int p_270466_
   ) {
      super(p_270739_, p_270683_, p_270200_, p_270240_, p_270692_, p_270904_, p_270470_, p_270248_, p_270466_);
   }

   @Shadow
   public abstract void setDayTime(long var1);

   @Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V", shift = Shift.AFTER))
   private void adjustDayTime(CallbackInfo ci) {
      if (ServerTime.extraTicks > 0 && ((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableSkyLagAdjustedMovement && !Compat.hourglass) {
         this.setDayTime(this.getDayTime() + ServerTime.extraTicks);
      }
   }
}
