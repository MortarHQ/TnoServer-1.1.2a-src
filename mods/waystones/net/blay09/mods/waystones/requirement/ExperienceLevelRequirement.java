package net.blay09.mods.waystones.requirement;

import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ExperienceLevelRequirement implements WarpRequirement {
   private int levels;

   public ExperienceLevelRequirement(int levels) {
      this.levels = Math.max(0, levels);
   }

   @Override
   public boolean canAfford(Player player) {
      return player.experienceLevel >= this.levels;
   }

   @Override
   public void consume(Player player) {
      player.giveExperienceLevels(-this.levels);
   }

   @Override
   public void rollback(Player player) {
      player.giveExperienceLevels(this.levels);
   }

   @Override
   public void appendHoverText(Player player, List<Component> tooltip) {
      if (this.levels > 0) {
         tooltip.add(Component.translatable("gui.waystones.waystone_selection.level_requirement", new Object[]{this.levels}).withStyle(ChatFormatting.GREEN));
      }
   }

   @Override
   public boolean isEmpty() {
      return this.levels <= 0;
   }

   public void setLevels(int levels) {
      this.levels = levels;
   }

   public int getLevels() {
      return this.levels;
   }
}
