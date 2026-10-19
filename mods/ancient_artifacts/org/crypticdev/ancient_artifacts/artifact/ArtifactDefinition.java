package org.crypticdev.ancient_artifacts.artifact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;

public record ArtifactDefinition(String id, ArtifactSlot slot, RefinementComponent defaultRefinement, List<ArtifactEffect> artifactEffects) {
   public ArtifactDefinition {
      Objects.requireNonNull(id, "id");
      Objects.requireNonNull(slot, "slot");
      Objects.requireNonNull(defaultRefinement, "defaultRefinement");
      artifactEffects = List.copyOf(artifactEffects);
   }

   public static ArtifactDefinition.Builder builder(String id) {
      return new ArtifactDefinition.Builder(id);
   }

   public static final class Builder {
      private final String id;
      private ArtifactSlot slot;
      private RefinementComponent defaultRefinement = RefinementComponent.DEFAULT;
      private final List<ArtifactEffect> artifactEffects = new ArrayList<>();

      private Builder(String id) {
         this.id = id;
      }

      public ArtifactDefinition.Builder slot(ArtifactSlot slot) {
         this.slot = Objects.requireNonNull(slot, "slot");
         return this;
      }

      public ArtifactDefinition.Builder grade1(
         ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect,
         ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect1,
         ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect2
      ) {
         this.artifactEffects.add(artifactEffect.apply(1, 0));
         this.artifactEffects.add(artifactEffect1.apply(1, 1));
         this.artifactEffects.add(artifactEffect2.apply(1, 2));
         this.defaultRefinement = new RefinementComponent(1, List.of(0, 0, 0));
         return this;
      }

      public ArtifactDefinition.Builder grade2(
         ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect, ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect1
      ) {
         this.artifactEffects.add(artifactEffect.apply(2, 0));
         this.artifactEffects.add(artifactEffect1.apply(2, 1));
         this.defaultRefinement = new RefinementComponent(2, List.of(0, 0));
         return this;
      }

      public ArtifactDefinition.Builder grade3(ArtifactDefinition.Builder.ArtifactEffectProvider artifactEffect) {
         this.artifactEffects.add(artifactEffect.apply(3, 0));
         this.defaultRefinement = new RefinementComponent(3, List.of(0));
         return this;
      }

      public ArtifactDefinition.Builder defaultRefinement(int grade, int refinement) {
         this.defaultRefinement = new RefinementComponent(grade, refinement, new ArrayList<>());
         return this;
      }

      public ArtifactDefinition.Builder defaultRefinement(int grade) {
         this.defaultRefinement = new RefinementComponent(grade, 1, new ArrayList<>());
         return this;
      }

      public ArtifactDefinition build() {
         return new ArtifactDefinition(this.id, this.slot, this.defaultRefinement, this.artifactEffects);
      }

      @FunctionalInterface
      public interface ArtifactEffectProvider {
         ArtifactEffect apply(int var1, int var2);
      }
   }
}
