package org.crypticdev.ancient_artifacts.mixin.fabric;

import dev.architectury.platform.Platform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.PlayerFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerFabricMixin implements PlayerFabric {
   @Unique
   private long lastCombatTick = 0L;
   @Unique
   private long firstCombatTick = 0L;

   @Unique
   @Override
   public long ancient_artifacts$getLastCombatTick() {
      return this.lastCombatTick;
   }

   @Unique
   @Override
   public long ancient_artifacts$getFirstCombatTick() {
      return this.firstCombatTick;
   }

   @Unique
   @Override
   public void ancient_artifacts$setLastCombatTick(long lastCombatTick) {
      this.lastCombatTick = lastCombatTick;
   }

   @Unique
   @Override
   public void ancient_artifacts$setFirstCombatTick(long firstCombatTick) {
      this.firstCombatTick = firstCombatTick;
   }

   @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
   private void readCombatData(CompoundTag tag, CallbackInfo ci) {
      if (Platform.isFabric()) {
         if (tag.contains("LastCombat")) {
            this.lastCombatTick = tag.getLong("LastCombat");
         }

         if (tag.contains("FirstCombat")) {
            this.firstCombatTick = tag.getLong("FirstCombat");
         }
      }
   }

   @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
   private void writeCombatData(CompoundTag tag, CallbackInfo ci) {
      if (Platform.isFabric()) {
         tag.putLong("LastCombat", this.lastCombatTick);
         tag.putLong("FirstCombat", this.firstCombatTick);
      }
   }
}
