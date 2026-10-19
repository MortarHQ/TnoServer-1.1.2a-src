package org.crypticdev.ancient_artifacts.artifact;

import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public final class ArtifactEffects {
   private ArtifactEffects() {
   }

   public static void tick(Player player) {
      if (!player.level().isClientSide()) {
         for (ArtifactDefinition definition : Artifacts.ALL) {
            if ((
                  definition.defaultRefinement().grade() != 1
                     || PlatformEquipment.amountOfGradeEquipped(player, 1) <= AncientArtifacts.CONFIG.artifacts.max_grade_1_artifacts
                     || AncientArtifacts.CONFIG.artifacts.max_grade_1_artifacts == -1
               )
               && !definition.artifactEffects().isEmpty()) {
               ItemStack stack = PlatformEquipment.getArtifactStack(player, definition);

               for (ArtifactEffect modifier : definition.artifactEffects()) {
                  if (modifier instanceof MultiArtifactEffect multiArtifactEffect) {
                     int index = multiArtifactEffect.index(definition);

                     for (ArtifactEffect artifactEffect : multiArtifactEffect.artifactEffectList()) {
                        if (artifactEffect instanceof DynamicAttributeArtifactEffect Dmodifier) {
                           updateModifier(player, definition, stack, Dmodifier);
                        }

                        if (artifactEffect instanceof UnlockableAttributeArtifactEffect Umodifier) {
                           updateUnlockableModifier(player, definition, stack, Umodifier, index);
                        }
                     }
                  } else {
                     updateModifier(player, definition, stack, modifier);
                  }
               }
            }
         }
      }
   }

   public static boolean EpUnlock(ItemStack stack, double ep) {
      return !stack.has((DataComponentType)TensuraDataComponents.EP.get()) ? false : (Double)stack.get((DataComponentType)TensuraDataComponents.EP.get()) >= ep;
   }

   private static void updateModifier(Player player, ArtifactDefinition definition, ItemStack stack, ArtifactEffect effect) {
      if (effect instanceof DynamicAttributeArtifactEffect modifier) {
         updateDynamicModifier(player, definition, stack, modifier);
      }

      if (effect instanceof UnlockableAttributeArtifactEffect modifier) {
         updateUnlockableModifier(player, definition, stack, modifier);
      }
   }

   private static void updateUnlockableModifier(Player player, ArtifactDefinition definition, ItemStack stack, UnlockableAttributeArtifactEffect modifier) {
      updateUnlockableModifier(player, definition, stack, modifier, definition.artifactEffects().indexOf(modifier));
   }

   private static void updateUnlockableModifier(
      Player player, ArtifactDefinition definition, ItemStack stack, UnlockableAttributeArtifactEffect modifier, int index
   ) {
      AttributeInstance instance = player.getAttribute(modifier.attribute());
      if (instance != null) {
         ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", definition.id() + "_" + modifier.id());
         if (stack.isEmpty()) {
            instance.removeModifier(modifierId);
         } else {
            double amount = modifier.value(player, stack, definition, index);
            if (Double.isFinite(amount) && amount != 0.0) {
               AttributeModifier current = instance.getModifier(modifierId);
               if (current == null || Double.compare(current.amount(), amount) != 0 || current.operation() != modifier.operation()) {
                  instance.removeModifier(modifierId);
                  instance.addTransientModifier(new AttributeModifier(modifierId, amount, modifier.operation()));
               }
            } else {
               instance.removeModifier(modifierId);
            }
         }
      }
   }

   private static void updateDynamicModifier(Player player, ArtifactDefinition definition, ItemStack stack, DynamicAttributeArtifactEffect modifier) {
      AttributeInstance instance = player.getAttribute(modifier.attribute());
      if (instance != null) {
         ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath("ancient_artifacts", definition.id() + "_" + modifier.id());
         if (stack.isEmpty()) {
            instance.removeModifier(modifierId);
         } else {
            double amount = modifier.value(player, stack);
            if (Double.isFinite(amount) && amount != 0.0) {
               AttributeModifier current = instance.getModifier(modifierId);
               if (current == null || Double.compare(current.amount(), amount) != 0 || current.operation() != modifier.operation()) {
                  instance.removeModifier(modifierId);
                  instance.addTransientModifier(new AttributeModifier(modifierId, amount, modifier.operation()));
               }
            } else {
               instance.removeModifier(modifierId);
            }
         }
      }
   }
}
