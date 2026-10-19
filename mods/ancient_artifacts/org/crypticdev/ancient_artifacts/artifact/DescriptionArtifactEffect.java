package org.crypticdev.ancient_artifacts.artifact;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record DescriptionArtifactEffect(String id, OptionalInt maxRefinement, ArtifactUnlockCondition unlockCondition) implements ArtifactEffect {
   public boolean unlocked(ItemStack stack, Player player) {
      return this.unlockCondition().apply(stack, player);
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition) {
      List<Component> description = new ArrayList<>();
      if (!this.unlocked(stack, player)) {
         description.add(Component.translatable("artifacts." + this.id + ".locked").withStyle(ChatFormatting.DARK_GRAY));
      } else {
         description.add(
            Component.translatable("artifact." + this.id + ".unlocked").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE})
         );
         description.add(Component.translatable("artifact." + this.id + ".description").withStyle(ChatFormatting.GRAY));
      }

      return description;
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition, int index) {
      return this.description(stack, player, artifactDefinition);
   }
}
