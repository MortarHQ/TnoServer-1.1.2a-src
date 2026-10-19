package artifacts.component;

import artifacts.component.ability.SwimInAir;
import artifacts.equipment.EquipmentHelper;
import artifacts.network.NetworkHandler;
import artifacts.network.SwimPacket;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

public class SwimData {
   protected boolean isSwimming;
   protected boolean hasTouchedWater;
   protected double swimProgress;

   public boolean isSwimming() {
      return this.isSwimming;
   }

   public boolean isWet() {
      return this.hasTouchedWater;
   }

   public double getSwimProgress() {
      return this.swimProgress;
   }

   public void update(Player player) {
      if (player.isInWater() || player.isInLava() || player.fallDistance > 6.0F) {
         this.hasTouchedWater = true;
      } else if (player.onGround() || player.getAbilities().flying) {
         this.hasTouchedWater = false;
      }

      boolean shouldToggle = this.isSwimming ? player.onGround() : player.isUnderWater() && player.isSwimming();
      if (shouldToggle) {
         this.toggleSwimming(player);
         this.syncSwimming(player);
      }

      this.updateSwimProgress(player);
   }

   private void updateSwimProgress(Player player) {
      if (this.shouldDeplete(player)) {
         int flightDuration = SwimInAir.getFlightDuration(player);
         this.swimProgress += 1.0 / flightDuration;
         if (this.swimProgress >= 1.0) {
            this.swimProgress = 1.0;
            this.toggleSwimming(player);
            this.syncSwimming(player);
         }
      } else if (this.swimProgress > 0.0) {
         int rechargeDuration = SwimInAir.getRechargeDuration(player);
         this.swimProgress -= 1.0 / rechargeDuration;
         this.swimProgress = Math.max(0.0, this.swimProgress);
      }
   }

   private boolean shouldDeplete(Player player) {
      return this.isSwimming
         && !player.isCreative()
         && (!player.isUnderWater() || EquipmentHelper.hasAbilityActive(ModDataComponents.SINKING.get(), player, true));
   }

   public void toggleSwimming(Player player) {
      if (this.isSwimming || SwimInAir.canSwim(player)) {
         this.isSwimming = !this.isSwimming;
         if (!this.isSwimming && !player.level().isClientSide() && !player.onGround()) {
            if (!player.isSilent()) {
               player.level()
                  .playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)ModSoundEvents.POP.value(), player.getSoundSource(), 1.0F, 1.0F);
            }

            EquipmentHelper.iterateAbilities(
               ModDataComponents.SWIM_IN_AIR.get(),
               player,
               true,
               true,
               (ability, stack) -> player.getCooldowns().addCooldown(stack.getItem(), Math.max(5, ability.cooldown().get() * 20))
            );
         }
      }
   }

   public void syncSwimming(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         NetworkHandler.sendToPlayer(serverPlayer, new SwimPacket(this.isSwimming));
      } else {
         NetworkHandler.sendToServer(new SwimPacket(this.isSwimming));
      }
   }
}
