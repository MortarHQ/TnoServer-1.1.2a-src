package com.yungnickyoung.minecraft.yungscavebiomes.sandstorm;

import com.google.common.hash.Hashing;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.ServerLevelAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.NotNull;

public class SandstormServerData extends SavedData {
   private static final int SYNC_INTERVAL = 60;
   private final ServerLevel serverLevel;
   private int timeSinceSync;
   private boolean isSandstormActive = false;
   private int currSandstormTicks;
   private int cooldownTicks;
   private int totalSandstormDurationTicks;
   private int totalSandstormCooldownTicks;
   private long sandstormSeed;

   public static Factory<SandstormServerData> factory(ServerLevel serverLevel) {
      return new Factory(() -> new SandstormServerData(serverLevel), (tag, registries) -> new SandstormServerData(serverLevel, tag), DataFixTypes.LEVEL);
   }

   public SandstormServerData(ServerLevel serverLevel) {
      this.serverLevel = serverLevel;
      this.resetSandstormTimeAndTotalDuration();
      this.resetSandstormCooldownAndTotalCoolDown();
      this.setDirty();
   }

   public SandstormServerData(ServerLevel serverLevel, CompoundTag compoundTag) {
      this(serverLevel);
      this.isSandstormActive = compoundTag.getBoolean("isSandstormActive");
      this.sandstormSeed = compoundTag.getLong("sandstormSeed");
      this.currSandstormTicks = compoundTag.getInt("sandstormTime");
      this.cooldownTicks = compoundTag.getInt("sandstormCooldown");
      this.totalSandstormDurationTicks = compoundTag.getInt("totalSandstormDuration");
      this.totalSandstormCooldownTicks = compoundTag.getInt("totalSandstormCooldown");
   }

   public void start() {
      YungsCaveBiomesCommon.LOGGER.debug("Starting sandstorm in {}", this.serverLevel.dimension().location());
      this.resetSandstormTimeAndTotalDuration();
      this.sandstormSeed = Hashing.sha256().hashLong(this.sandstormSeed + ((ServerLevelAccessor)this.serverLevel).getServerLevelData().getGameTime()).asLong();
      this.isSandstormActive = true;
      this.syncToClients();
   }

   public void stop() {
      YungsCaveBiomesCommon.LOGGER.debug("STOPPING SANDSTORM in {}", this.serverLevel.dimension().location());
      this.resetSandstormCooldownAndTotalCoolDown();
      this.isSandstormActive = false;
      this.serverLevel.players().forEach(player -> {
         if (!player.isSpectator() && this.serverLevel.getBiome(player.blockPosition()).is(BiomeModule.LOST_CAVES)) {
            CriteriaModule.SANDSTORM_END.trigger(player);
         }
      });
      this.syncToClients();
   }

   public void tick() {
      if (YungsCaveBiomesCommon.DEBUG_LOG) {
         YungsCaveBiomesCommon.LOGGER
            .info(
               "Sandstorm {} >> {} / {} time, {} / {} cooldown",
               this.serverLevel.dimension().location(),
               this.currSandstormTicks,
               this.totalSandstormDurationTicks,
               this.cooldownTicks,
               this.totalSandstormCooldownTicks
            );
      }

      this.timeSinceSync++;
      if (this.isSandstormActive) {
         this.currSandstormTicks--;
         if (this.currSandstormTicks <= 0) {
            this.stop();
         }
      } else {
         this.cooldownTicks--;
         if (this.cooldownTicks <= 0) {
            this.start();
         }
      }

      if (this.timeSinceSync > 60) {
         this.syncToClients();
         if (YungsCaveBiomesCommon.DEBUG_LOG) {
            YungsCaveBiomesCommon.LOGGER.info("Force syncing sandstorm...");
         }
      }
   }

   @NotNull
   public CompoundTag save(CompoundTag compoundTag, @NotNull Provider registries) {
      compoundTag.putBoolean("isSandstormActive", this.isSandstormActive);
      compoundTag.putLong("sandstormSeed", this.sandstormSeed);
      compoundTag.putInt("sandstormTime", this.currSandstormTicks);
      compoundTag.putInt("sandstormCooldown", this.cooldownTicks);
      compoundTag.putInt("totalSandstormDuration", this.totalSandstormDurationTicks);
      compoundTag.putInt("totalSandstormCooldown", this.totalSandstormCooldownTicks);
      return compoundTag;
   }

   private void resetSandstormTimeAndTotalDuration() {
      this.totalSandstormDurationTicks = Mth.randomBetweenInclusive(
            this.serverLevel.getRandom(),
            YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration,
            YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration
         )
         * 20;
      this.currSandstormTicks = this.totalSandstormDurationTicks;
   }

   private void resetSandstormCooldownAndTotalCoolDown() {
      this.totalSandstormCooldownTicks = Mth.randomBetweenInclusive(
            this.serverLevel.getRandom(),
            YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms,
            YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms
         )
         * 20;
      this.cooldownTicks = this.totalSandstormCooldownTicks;
   }

   private void syncToClients() {
      Services.PLATFORM.syncSandstormDataToClients(this);
      this.timeSinceSync = 0;
      this.setDirty();
   }

   public ServerLevel getServerLevel() {
      return this.serverLevel;
   }

   public boolean isSandstormActive() {
      return this.isSandstormActive;
   }

   public int getCurrSandstormTicks() {
      return this.currSandstormTicks;
   }

   public long getSeed() {
      return this.sandstormSeed;
   }

   public int getTotalSandstormDurationTicks() {
      return this.totalSandstormDurationTicks;
   }
}
