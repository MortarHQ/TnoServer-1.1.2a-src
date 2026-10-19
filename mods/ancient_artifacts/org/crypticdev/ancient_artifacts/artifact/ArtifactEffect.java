package org.crypticdev.ancient_artifacts.artifact;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.config.SpecialEffectConfig;

public sealed interface ArtifactEffect permits DescriptionArtifactEffect, DynamicAttributeArtifactEffect, MultiArtifactEffect, UnlockableAttributeArtifactEffect {
   String id();

   List<Component> description(ItemStack var1, Player var2, ArtifactDefinition var3);

   List<Component> description(ItemStack var1, Player var2, ArtifactDefinition var3, int var4);

   ArtifactUnlockCondition unlockCondition();

   OptionalInt maxRefinement();

   static ArtifactEffect.Builder builder(String id) {
      return new ArtifactEffect.Builder(id);
   }

   final class Builder {
      private ArtifactEffect.Builder.artifactType type;
      private String id;
      private Supplier<Holder<Attribute>> attributeSupplier;
      private double baseValue;
      private double valuePerRefinement;
      private ArtifactUnlockCondition unlockCondition;
      private ToDoubleBiFunction<Player, ItemStack> valueProvider;
      private SpecialEffectConfig specialEffectConfig;
      private Operation operation;
      private OptionalInt maxRefinementSteps = OptionalInt.empty();
      private List<ArtifactEffect> artifactEffectList;

      public Builder(String id) {
         this.id = id;
      }

      public ArtifactEffect.Builder Multi(Function<ArtifactUnlockCondition, List<ArtifactEffect>> artifactEffectList, int grade, int ability) {
         ArtifactUnlockCondition condition = new ArtifactUnlockCondition().addEPCondition(grade, ability);
         return this.Multi(condition, artifactEffectList.apply(condition));
      }

      public ArtifactEffect.Builder Multi(Function<ArtifactUnlockCondition, List<ArtifactEffect>> artifactEffectList, ArtifactUnlockCondition unlockCondition) {
         return this.Multi(unlockCondition, artifactEffectList.apply(unlockCondition));
      }

      public ArtifactEffect.Builder Multi(List<ArtifactEffect> artifactEffectList, int grade, int ability) {
         return this.Multi(new ArtifactUnlockCondition().addEPCondition(grade, ability), artifactEffectList);
      }

      public ArtifactEffect.Builder Multi(ArtifactUnlockCondition unlockCondition, List<ArtifactEffect> artifactEffectList) {
         this.type = ArtifactEffect.Builder.artifactType.Multi;
         this.unlockCondition = unlockCondition;
         this.artifactEffectList = artifactEffectList;
         return this;
      }

      public ArtifactEffect.Builder Description(int grade, int ability) {
         return this.Description(new ArtifactUnlockCondition().addEPCondition(grade, ability));
      }

      public ArtifactEffect.Builder Description(int max_refinement, int grade, int ability) {
         return this.Description(OptionalInt.of(max_refinement), new ArtifactUnlockCondition().addEPCondition(grade, ability));
      }

      public ArtifactEffect.Builder Description(OptionalInt max_refinement, ArtifactUnlockCondition unlockCondition) {
         this.type = ArtifactEffect.Builder.artifactType.Description;
         if (max_refinement.isPresent()) {
            this.maxRefinementSteps = max_refinement;
         }

         this.unlockCondition = unlockCondition;
         return this;
      }

      public ArtifactEffect.Builder Description(ArtifactUnlockCondition unlockCondition) {
         this.type = ArtifactEffect.Builder.artifactType.Description;
         this.unlockCondition = unlockCondition;
         return this;
      }

      public ArtifactEffect.Builder Dynamic(
         Supplier<Holder<Attribute>> attributeSupplier,
         SpecialEffectConfig config,
         ToDoubleBiFunction<Player, ItemStack> valueProvider,
         Operation operation,
         int grade,
         int ability
      ) {
         return this.Dynamic(attributeSupplier, config, valueProvider, operation, new ArtifactUnlockCondition().addEPCondition(grade, ability));
      }

      public ArtifactEffect.Builder Dynamic(
         Supplier<Holder<Attribute>> attributeSupplier,
         SpecialEffectConfig config,
         ToDoubleBiFunction<Player, ItemStack> valueProvider,
         Operation operation,
         ArtifactUnlockCondition unlockCondition
      ) {
         this.type = ArtifactEffect.Builder.artifactType.Dynamic;
         this.specialEffectConfig = config;
         this.attributeSupplier = attributeSupplier;
         this.valueProvider = valueProvider;
         this.operation = operation;
         this.unlockCondition = unlockCondition;
         return this;
      }

      public ArtifactEffect.Builder Unlockable(
         Supplier<Holder<Attribute>> attributeSupplier, double baseValue, double valuePerRefinement, Operation operation, int grade, int ability
      ) {
         return this.Unlockable(
            attributeSupplier, baseValue, valuePerRefinement, operation, new ArtifactUnlockCondition().addEPCondition(grade, ability), OptionalInt.empty()
         );
      }

      public ArtifactEffect.Builder Unlockable(
         Supplier<Holder<Attribute>> attributeSupplier,
         double baseValue,
         double valuePerRefinement,
         Operation operation,
         int maxRefinementSteps,
         int grade,
         int ability
      ) {
         return this.Unlockable(
            attributeSupplier,
            baseValue,
            valuePerRefinement,
            operation,
            new ArtifactUnlockCondition().addEPCondition(grade, ability),
            OptionalInt.of(maxRefinementSteps)
         );
      }

      public ArtifactEffect.Builder Unlockable(
         Supplier<Holder<Attribute>> attributeSupplier,
         double baseValue,
         double valuePerRefinement,
         Operation operation,
         ArtifactUnlockCondition unlockCondition
      ) {
         return this.Unlockable(attributeSupplier, baseValue, valuePerRefinement, operation, unlockCondition, OptionalInt.empty());
      }

      public ArtifactEffect.Builder Unlockable(
         Supplier<Holder<Attribute>> attributeSupplier,
         double baseValue,
         double valuePerRefinement,
         Operation operation,
         ArtifactUnlockCondition unlockCondition,
         OptionalInt maxRefinementSteps
      ) {
         this.type = ArtifactEffect.Builder.artifactType.Unlockable;
         this.attributeSupplier = attributeSupplier;
         this.baseValue = baseValue;
         this.valuePerRefinement = valuePerRefinement;
         this.operation = operation;
         this.maxRefinementSteps = maxRefinementSteps;
         this.unlockCondition = unlockCondition;
         return this;
      }

      public ArtifactEffect build() {
         switch (this.type) {
            case Unlockable:
               if (this.maxRefinementSteps.isPresent()) {
                  return new UnlockableAttributeArtifactEffect(
                     this.id,
                     this.attributeSupplier,
                     this.baseValue,
                     this.valuePerRefinement,
                     this.operation,
                     this.unlockCondition,
                     this.maxRefinementSteps.getAsInt()
                  );
               }

               return new UnlockableAttributeArtifactEffect(
                  this.id, this.attributeSupplier, this.baseValue, this.valuePerRefinement, this.operation, this.unlockCondition
               );
            case Description:
               if (this.maxRefinementSteps.isPresent()) {
                  return new DescriptionArtifactEffect(this.id, this.maxRefinementSteps, this.unlockCondition);
               }

               return new DescriptionArtifactEffect(this.id, OptionalInt.empty(), this.unlockCondition);
            case Multi:
               return new MultiArtifactEffect(this.id, this.unlockCondition, this.artifactEffectList);
            case Dynamic:
               return new DynamicAttributeArtifactEffect(
                  this.id, this.attributeSupplier, this.specialEffectConfig, this.valueProvider, this.operation, this.unlockCondition
               );
            default:
               return new DescriptionArtifactEffect(this.id, OptionalInt.empty(), new ArtifactUnlockCondition().addCondition((stack, player) -> true));
         }
      }

      private enum artifactType {
         Unlockable,
         Description,
         Multi,
         Dynamic;
      }
   }
}
