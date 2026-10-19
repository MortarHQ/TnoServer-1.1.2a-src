package net.blay09.mods.waystones.requirement;

import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class RefuseRequirement implements WarpRequirement {
   private Component message = Component.empty();

   public RefuseRequirement() {
   }

   public RefuseRequirement(Component message) {
      this.message = message;
   }

   @Override
   public boolean canAfford(Player player) {
      return false;
   }

   @Override
   public void consume(Player player) {
   }

   @Override
   public void rollback(Player player) {
   }

   @Override
   public void appendHoverText(Player player, List<Component> tooltip) {
      tooltip.add(this.message.copy().withStyle(ChatFormatting.RED));
   }

   public void setMessage(Component message) {
      this.message = message;
   }

   public Component getMessage() {
      return this.message;
   }
}
