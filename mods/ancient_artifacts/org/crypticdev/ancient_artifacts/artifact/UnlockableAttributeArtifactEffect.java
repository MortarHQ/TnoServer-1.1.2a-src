package org.crypticdev.ancient_artifacts.artifact;

import io.github.manasmods.manascore.config.ConfigRegistry;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.config.UnlockableEffectConfig;

public record UnlockableAttributeArtifactEffect(
   String id,
   Supplier<Holder<Attribute>> attributeSupplier,
   double baseValue,
   double valuePerRefinement,
   ArtifactUnlockCondition unlockCondition,
   Operation operation,
   OptionalInt maxRefinement
) implements ArtifactEffect {
   public UnlockableAttributeArtifactEffect(
      String id,
      Supplier<Holder<Attribute>> attributeSupplier,
      double baseValue,
      double valuePerRefinement,
      Operation operation,
      ArtifactUnlockCondition condition,
      int maxRefinementSteps
   ) {
      this(id, attributeSupplier, baseValue, valuePerRefinement, condition, operation, OptionalInt.of(maxRefinementSteps));
   }

   public UnlockableAttributeArtifactEffect(
      String id,
      Supplier<Holder<Attribute>> attributeSupplier,
      double baseValue,
      double valuePerRefinement,
      Operation operation,
      ArtifactUnlockCondition condition
   ) {
      this(id, attributeSupplier, baseValue, valuePerRefinement, condition, operation, OptionalInt.empty());
   }

   public UnlockableAttributeArtifactEffect {
      if (maxRefinement.isPresent()) {
         ConfigRegistry.registerConfig(new UnlockableEffectConfig(id, baseValue, valuePerRefinement, maxRefinement.getAsInt()));
      } else {
         ConfigRegistry.registerConfig(new UnlockableEffectConfig(id, baseValue, valuePerRefinement, -1));
      }

      UnlockableEffectConfig unlockableEffectConfig = (UnlockableEffectConfig)ConfigRegistry.getConfig(UnlockableEffectConfig.class);
      baseValue = unlockableEffectConfig.baseValue;
      valuePerRefinement = unlockableEffectConfig.valuePerRefinement;
      if (unlockableEffectConfig.maxRefinement == -1) {
         maxRefinement = OptionalInt.empty();
      } else {
         maxRefinement = OptionalInt.of(unlockableEffectConfig.maxRefinement);
      }

      Objects.requireNonNull(id, "id");
      Objects.requireNonNull(attributeSupplier, "attributeSupplier");
      Objects.requireNonNull(unlockCondition, "unlockCondition");
      Objects.requireNonNull(operation, "operation");
      Objects.requireNonNull(maxRefinement, "maxRefinement");
      if (maxRefinement.isPresent() && maxRefinement.getAsInt() < 0) {
         throw new IllegalArgumentException("maxRefinement must be non-negative");
      }
   }

   public Holder<Attribute> attribute() {
      return this.attributeSupplier.get();
   }

   public boolean unlocked(ItemStack stack, Player player) {
      return this.unlockCondition().apply(stack, player);
   }

   public double value(Player player, ItemStack stack, ArtifactDefinition artifact) {
      if (!this.unlocked(stack, player)) {
         return 0.0;
      }

      int index = artifact.artifactEffects().indexOf(this);
      if (index < 0) {
         return 0.0;
      }

      List<Integer> upgrades = ArtifactRefinement.get(stack).upgrades();
      if (index >= upgrades.size()) {
         return 0.0;
      }

      int refinementSteps = Math.max(0, upgrades.get(index));
      if (this.maxRefinement.isPresent()) {
         refinementSteps = Math.min(refinementSteps, this.maxRefinement.getAsInt());
      }

      return this.baseValue + this.valuePerRefinement * refinementSteps;
   }

   public double value(Player player, ItemStack stack, ArtifactDefinition artifact, int index) {
      if (!this.unlocked(stack, player)) {
         return 0.0;
      }

      if (index < 0) {
         return 0.0;
      }

      List<Integer> upgrades = ArtifactRefinement.get(stack).upgrades();
      if (index >= upgrades.size()) {
         return 0.0;
      }

      int refinementSteps = Math.max(0, upgrades.get(index));
      if (this.maxRefinement.isPresent()) {
         refinementSteps = Math.min(refinementSteps, this.maxRefinement.getAsInt());
      }

      return this.baseValue + this.valuePerRefinement * refinementSteps;
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition) {
      List<Component> description = new ArrayList<>();
      if (!this.unlocked(stack, player)) {
         description.add(Component.translatable("artifacts." + this.id() + ".locked").withStyle(ChatFormatting.DARK_GRAY));
      } else {
         description.add(
            Component.translatable("artifacts." + this.id() + ".unlocked").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE})
         );
         double value = this.value(player, stack, artifactDefinition);
         DecimalFormat df = new DecimalFormat("+#.#####;-#.#####");
         String valueString = df.format(value);
         String after = " ";
         switch (this.operation()) {
            case ADD_MULTIPLIED_BASE:
            case ADD_MULTIPLIED_TOTAL:
               valueString = df.format(value * 100.0);
               after = "% ";
            default:
               if (player.containerMenu instanceof AnvilMenu anvilMenu
                  && anvilMenu.getSlot(2).getItem() == stack
                  && anvilMenu.getSlot(0).getItem().getItem() instanceof ArtifactItem
                  && anvilMenu.getSlot(1).getItem().getItem() instanceof ArtifactItem
                  && anvilMenu.getSlot(2).getItem().getItem() instanceof ArtifactItem) {
                  valueString = "?";
               }

               description.add(
                  Component.literal(valueString + after)
                     .withStyle(ChatFormatting.BLUE)
                     .append(Component.translatable(((Attribute)this.attribute().value()).getDescriptionId()).withStyle(ChatFormatting.BLUE))
               );
         }
      }

      return description;
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition, int index) {
      List<Component> description = new ArrayList<>();
      if (!this.unlocked(stack, player)) {
         description.add(Component.translatable("artifacts." + this.id() + ".locked").withStyle(ChatFormatting.DARK_GRAY));
      } else {
         description.add(
            Component.translatable("artifacts." + this.id() + ".unlocked").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE})
         );
         double value = this.value(player, stack, artifactDefinition, index);
         DecimalFormat df = new DecimalFormat("+#.#####;-#.#####");
         String valueString = df.format(value);
         String after = " ";
         switch (this.operation()) {
            case ADD_MULTIPLIED_BASE:
            case ADD_MULTIPLIED_TOTAL:
               valueString = df.format(value * 100.0);
               after = "% ";
            default:
               if (player.containerMenu instanceof AnvilMenu anvilMenu
                  && anvilMenu.getSlot(2).getItem() == stack
                  && anvilMenu.getSlot(0).getItem().getItem() instanceof ArtifactItem
                  && anvilMenu.getSlot(1).getItem().getItem() instanceof ArtifactItem
                  && anvilMenu.getSlot(2).getItem().getItem() instanceof ArtifactItem) {
                  valueString = "?";
               }

               description.add(
                  Component.literal(valueString + after)
                     .withStyle(ChatFormatting.BLUE)
                     .append(Component.translatable(((Attribute)this.attribute().value()).getDescriptionId()).withStyle(ChatFormatting.BLUE))
               );
         }
      }

      return description;
   }
}
