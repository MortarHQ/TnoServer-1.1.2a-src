package net.blay09.mods.waystones.requirement;

import java.util.Collection;
import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CombinedRequirement implements WarpRequirement {
   private final Collection<WarpRequirement> requirements;

   public CombinedRequirement(Collection<WarpRequirement> requirements) {
      this.requirements = requirements;
   }

   @Override
   public boolean canAfford(Player player) {
      return this.requirements.stream().allMatch(requirement -> requirement.canAfford(player));
   }

   @Override
   public void consume(Player player) {
      this.requirements.forEach(requirement -> requirement.consume(player));
   }

   @Override
   public void rollback(Player player) {
      this.requirements.forEach(requirement -> requirement.rollback(player));
   }

   @Override
   public void appendHoverText(Player player, List<Component> tooltip) {
      this.requirements.forEach(requirement -> requirement.appendHoverText(player, tooltip));
   }

   @Override
   public boolean isEmpty() {
      return this.requirements.stream().allMatch(WarpRequirement::isEmpty);
   }

   public Collection<WarpRequirement> getRequirements() {
      return this.requirements;
   }
}
