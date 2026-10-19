package net.blay09.mods.waystones.requirement;

import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CooldownRequirement implements WarpRequirement {
   private ResourceLocation key;
   private int seconds;

   public CooldownRequirement(ResourceLocation key, int seconds) {
      this.key = key;
      this.seconds = seconds;
   }

   @Override
   public boolean canAfford(Player player) {
      return this.getCooldownMillisLeft(player) <= 0L;
   }

   @Override
   public void consume(Player player) {
      if (this.seconds > 0) {
         PlayerWaystoneManager.setCooldownUntil(player, this.key, System.currentTimeMillis() + this.seconds * 1000L);
         WaystoneSyncManager.sendWaystoneCooldowns(player);
      }
   }

   @Override
   public void rollback(Player player) {
      PlayerWaystoneManager.setCooldownUntil(player, this.key, 0L);
   }

   @Override
   public void appendHoverText(Player player, List<Component> tooltip) {
      long millisLeft = this.getCooldownMillisLeft(player);
      if (millisLeft > 0L) {
         tooltip.add(Component.translatable("tooltip.waystones.cooldown_left", new Object[]{millisLeft / 1000L}).withStyle(ChatFormatting.GOLD));
      }
   }

   public long getCooldownMillisLeft(Player player) {
      return PlayerWaystoneManager.getCooldownMillisLeft(player, this.key);
   }

   @Override
   public boolean isEmpty() {
      return this.seconds <= 0;
   }

   public void setCooldown(ResourceLocation key, int seconds) {
      this.key = key;
      this.seconds = seconds;
   }

   public ResourceLocation getCooldownKey() {
      return this.key;
   }

   public int getCooldownSeconds() {
      return this.seconds;
   }
}
