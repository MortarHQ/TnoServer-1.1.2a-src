package org.crypticdev.ancient_artifacts.artifact;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.ArtifactDataComponents;

public final class ArtifactText {
   private ArtifactText() {
   }

   public static Component name(Item item, ItemStack stack) {
      return Component.translatable(item.getDescriptionId(stack))
         .append(Component.literal(" "))
         .append(
            Component.translatable("tooltip.ancient_artifacts.grade_suffix", new Object[]{ArtifactRefinement.get(stack).grade()})
               .withStyle(ChatFormatting.GOLD)
         );
   }

   @OnlyIn(Dist.CLIENT)
   public static void appendTooltip(ItemStack stack, List<Component> tooltip) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && minecraft.level.isClientSide) {
         Player player = Minecraft.getInstance().player;
         if (player != null) {
            RefinementComponent refinement = ArtifactRefinement.get(stack);
            if (stack.getItem() instanceof ArtifactItem artifactItem) {
               tooltip.add(
                  Component.translatable("tooltip.ancient_artifacts.evolution", new Object[]{artifactItem.getEvolution(stack)}).withStyle(ChatFormatting.GRAY)
               );
            }

            tooltip.add(Component.translatable("tooltip.ancient_artifacts.refinement", new Object[]{refinement.refinement()}).withStyle(ChatFormatting.GRAY));
            ArtifactDefinition artifactDefinition = ((ArtifactItem)stack.getItem()).getArtifact();

            for (int i = 0; i < artifactDefinition.artifactEffects().size(); i++) {
               ArtifactEffect artifactEffect = artifactDefinition.artifactEffects().get(i);
               if (artifactEffect instanceof MultiArtifactEffect multiArtifactEffect) {
                  int index = multiArtifactEffect.index(artifactDefinition);
                  if (multiArtifactEffect.unlocked(stack, player)) {
                     for (ArtifactEffect effect : multiArtifactEffect.artifactEffectList()) {
                        tooltip.addAll(effect.description(stack, player, artifactDefinition, index));
                     }
                  }
               }

               tooltip.addAll(artifactEffect.description(stack, player, artifactDefinition));
               if (stack.has(ArtifactDataComponents.REFINEMENT.get())) {
                  if (player.containerMenu instanceof AnvilMenu anvilMenu) {
                     if (anvilMenu.getSlot(2).getItem() == stack
                        && anvilMenu.getSlot(0).getItem().getItem() instanceof ArtifactItem
                        && anvilMenu.getSlot(1).getItem().getItem() instanceof ArtifactItem
                        && anvilMenu.getSlot(2).getItem().getItem() instanceof ArtifactItem) {
                        tooltip.add(Component.translatable("artifact.effect.refinement", new Object[]{"?"}).withStyle(ChatFormatting.DARK_RED));
                     } else {
                        tooltip.add(
                           Component.translatable(
                                 "artifact.effect.refinement",
                                 new Object[]{((RefinementComponent)stack.get(ArtifactDataComponents.REFINEMENT.get())).upgrades().get(i)}
                              )
                              .withStyle(ChatFormatting.DARK_RED)
                        );
                     }
                  } else {
                     tooltip.add(
                        Component.translatable(
                              "artifact.effect.refinement",
                              new Object[]{((RefinementComponent)stack.get(ArtifactDataComponents.REFINEMENT.get())).upgrades().get(i)}
                           )
                           .withStyle(ChatFormatting.DARK_RED)
                     );
                  }
               }
            }

            if (refinement.grade() == 1
               && PlatformEquipment.amountOfGradeEquipped(player, 1) > AncientArtifacts.CONFIG.artifacts.max_grade_1_artifacts
               && AncientArtifacts.CONFIG.artifacts.max_grade_1_artifacts != -1) {
               tooltip.add(
                  Component.translatable("artifacts.too_many_of_grade", new Object[]{1})
                     .withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD})
               );
            }
         }
      }
   }
}
