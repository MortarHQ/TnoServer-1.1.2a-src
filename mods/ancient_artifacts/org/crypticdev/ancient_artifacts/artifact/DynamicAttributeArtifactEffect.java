package org.crypticdev.ancient_artifacts.artifact;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public record DynamicAttributeArtifactEffect(
   String id,
   Supplier<Holder<Attribute>> attributeSupplier,
   SpecialEffectConfig specialEffectConfig,
   ToDoubleBiFunction<Player, ItemStack> valueProvider,
   Operation operation,
   ArtifactUnlockCondition unlockCondition
) implements ArtifactEffect {
   public DynamicAttributeArtifactEffect {
      Objects.requireNonNull(id, "id");
      Objects.requireNonNull(attributeSupplier, "attributeSupplier");
      Objects.requireNonNull(specialEffectConfig, "dynamicEffectsConfig");
      Objects.requireNonNull(valueProvider, "valueProvider");
      Objects.requireNonNull(operation, "operation");
      Objects.requireNonNull(unlockCondition, "unlockCondition");
   }

   public Holder<Attribute> attribute() {
      return this.attributeSupplier.get();
   }

   public double value(Player entity, ItemStack stack) {
      return !this.unlocked(stack, entity) ? 0.0 : this.valueProvider.applyAsDouble(entity, stack);
   }

   public boolean unlocked(ItemStack stack, Player player) {
      return this.unlockCondition().apply(stack, player);
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition) {
      List<Component> description = new ArrayList<>();
      if (this.unlocked(stack, player)) {
         description.add(
            Component.translatable("artifact." + this.id + ".unlocked").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.UNDERLINE})
         );
         description.add(Component.translatable("artifact." + this.id + ".description").withStyle(ChatFormatting.GRAY));
         double value = this.value(player, stack);
         DecimalFormat df = new DecimalFormat("+#.#####;-#.#####");
         String value_string = df.format(value);
         String after = " ";
         if (this.operation() == Operation.ADD_MULTIPLIED_BASE || this.operation() == Operation.ADD_MULTIPLIED_TOTAL) {
            after = "% ";
            value_string = df.format(value * 100.0);
         }

         if (player.containerMenu instanceof AnvilMenu anvilMenu
            && anvilMenu.getSlot(2).getItem() == stack
            && anvilMenu.getSlot(0).getItem().getItem() instanceof ArtifactItem
            && anvilMenu.getSlot(1).getItem().getItem() instanceof ArtifactItem
            && anvilMenu.getSlot(2).getItem().getItem() instanceof ArtifactItem) {
            value_string = "?";
         }

         description.add(
            Component.literal(value_string + after)
               .withStyle(ChatFormatting.BLUE)
               .append(Component.translatable(((Attribute)this.attribute().value()).getDescriptionId()).withStyle(ChatFormatting.BLUE))
         );
      } else {
         description.add(Component.translatable("artifacts." + this.id() + ".locked").withStyle(ChatFormatting.DARK_GRAY));
      }

      return description;
   }

   @Override
   public List<Component> description(ItemStack stack, Player player, ArtifactDefinition artifactDefinition, int index) {
      return this.description(stack, player, artifactDefinition);
   }

   @Override
   public OptionalInt maxRefinement() {
      return this.specialEffectConfig.max_refinements() == -1 ? OptionalInt.empty() : OptionalInt.of(this.specialEffectConfig.max_refinements());
   }
}
