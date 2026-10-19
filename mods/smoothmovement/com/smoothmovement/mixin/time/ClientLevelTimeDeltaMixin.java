package com.smoothmovement.mixin.time;

import com.smoothmovement.SmoothMovement;
import com.smoothmovement.config.CommonConfiguration;
import com.smoothmovement.time.ClientLevelDeltaTime;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.neoforged.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelTimeDeltaMixin extends Level implements ClientLevelDeltaTime {
   @Unique
   private double smoothPacketTickInterval = 20.0;
   @Unique
   private double slownessFactor = 1.0;
   @Unique
   private int serverTPS = 20;
   @Unique
   private long lastPacketGameTime = -1L;
   @Unique
   private double visualDayTime = 0.0;
   @Unique
   private double visualDayTimeSpeed = 1.0;
   @Unique
   private long lastServerDayTime = -1L;
   @Unique
   private int tooFastPackets = 0;
   @Unique
   private int ticksSincePacket = 0;
   @Unique
   int totalPackets = 0;
   @Unique
   private boolean serversideSmoothMovement = false;

   protected ClientLevelTimeDeltaMixin(
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

   @Override
   public double getSlownessFactor() {
      return this.slownessFactor;
   }

   @Override
   public float getVisualDayTimeSpeed() {
      return (float)this.visualDayTimeSpeed;
   }

   @Override
   public void onTimePacket(long newGameTime, long serverDayTime) {
      if (this.ticksSincePacket != 0) {
         if (!this.serversideSmoothMovement) {
            this.onServerPacketCalculateSlowness();
         }

         if (this.visualDayTime == 0.0) {
            this.visualDayTime = serverDayTime;
            this.lastServerDayTime = serverDayTime;
         } else {
            if (this.lastPacketGameTime == -1L) {
               this.lastPacketGameTime = this.getGameTime() - 20L;
            }

            long timeSinceLastPacket = this.getGameTime() - this.lastPacketGameTime;
            if (timeSinceLastPacket != 0L) {
               this.lastPacketGameTime = newGameTime;
               long elapsedServerDayTime = serverDayTime - this.lastServerDayTime;
               this.lastServerDayTime = serverDayTime;
               double serverTimeSpeed = (double)elapsedServerDayTime / timeSinceLastPacket;
               this.visualDayTimeSpeed = this.visualDayTimeSpeed * 0.8 + serverTimeSpeed * 0.2;
               if (Math.abs(this.visualDayTime - serverDayTime) > 600.0) {
                  this.visualDayTimeSpeed = 1.0;
                  this.visualDayTime = serverDayTime;
               }
            }
         }
      }
   }

   @Unique
   private void onServerPacketCalculateSlowness() {
      if (this.totalPackets < 5) {
         this.totalPackets++;
      } else {
         if (this.ticksSincePacket < 20) {
            this.tooFastPackets++;
         } else if (this.tooFastPackets > 0) {
            this.tooFastPackets--;
         }

         int packetTickInterval = this.tooFastPackets < 10 ? 20 : 1;
         double packetTimeDiffCapped = Math.min(this.ticksSincePacket, this.smoothPacketTickInterval * 2.5);
         float adjustment = packetTimeDiffCapped > this.smoothPacketTickInterval ? 0.35F : 0.08F;
         this.smoothPacketTickInterval = this.smoothPacketTickInterval + (packetTimeDiffCapped - this.smoothPacketTickInterval) * adjustment;
         this.smoothPacketTickInterval = Mth.clamp(this.smoothPacketTickInterval, packetTickInterval, packetTickInterval * 10);
         this.slownessFactor = this.smoothPacketTickInterval / packetTickInterval;
         if (this.serversideSmoothMovement && !FMLEnvironment.production && Math.abs(20.0 / this.slownessFactor - this.serverTPS) > 2.0) {
            SmoothMovement.LOGGER.warn("large tps diff! client:" + 20.0 / this.slownessFactor + " server:" + this.serverTPS);
         }

         this.ticksSincePacket = 0;
      }
   }

   @Override
   public void onScoreBoardPacket(int score) {
      this.serverTPS = score;
      this.serversideSmoothMovement = true;
      this.onServerPacketCalculateSlowness();
      this.ticksSincePacket = 0;
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void onTick(BooleanSupplier p_104727_, CallbackInfo ci) {
      this.ticksSincePacket++;
   }

   @Inject(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;setDayTime(J)V"))
   private void adjustVisualTime(CallbackInfo ci) {
      this.visualDayTime = this.visualDayTime + this.visualDayTimeSpeed;
   }

   public float getTimeOfDay(float partialTick) {
      if (!((CommonConfiguration)CommonConfiguration.config.getCommonConfig()).enableSkySmoothing) {
         return super.getTimeOfDay(partialTick);
      }

      double time = this.visualDayTime + partialTick * this.visualDayTimeSpeed;
      double d0 = Mth.frac(time / 24000.0 - 0.25);
      double d1 = 0.5 - Math.cos(d0 * Math.PI) / 2.0;
      float smoothTimeValue = (float)(d0 * 2.0 + d1) / 3.0F;
      float dimensionValue = this.dimensionType().timeOfDay((long)time);
      return Math.abs(smoothTimeValue - dimensionValue) > 0.01 ? dimensionValue : smoothTimeValue;
   }
}
