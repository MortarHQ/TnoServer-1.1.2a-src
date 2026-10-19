package org.crypticdev.ancient_artifacts.artifact;

import java.util.List;
import java.util.OptionalInt;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record MultiArtifactEffect(String id, ArtifactUnlockCondition unlockCondition, List<ArtifactEffect> artifactEffectList) implements ArtifactEffect {
   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition) {
      return this.unlocked(stack, player)
         ? List.of()
         : List.of(Component.translatable("artifacts." + this.id() + ".locked").withStyle(ChatFormatting.DARK_GRAY));
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition, int index) {
      return this.unlocked(stack, player)
         ? List.of()
         : List.of(Component.translatable("artifacts." + this.id() + ".locked").withStyle(ChatFormatting.DARK_GRAY));
   }

   @Override
   public OptionalInt maxRefinement() {
      int max_refinement = -1;

      for (ArtifactEffect effect : this.artifactEffectList()) {
         if (effect.maxRefinement().isPresent()) {
            max_refinement = Math.max(max_refinement, effect.maxRefinement().getAsInt());
         }
      }

      return max_refinement == -1 ? OptionalInt.empty() : OptionalInt.of(max_refinement);
   }

   public boolean unlocked(ItemStack stack, Player player) {
      return this.unlockCondition().apply(stack, player);
   }

   public int index(ArtifactDefinition artifact) {
      return artifact.artifactEffects().indexOf(this);
   }
}
