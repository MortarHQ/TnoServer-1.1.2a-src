package org.crypticdev.ancient_artifacts.config;

import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.util.List;

public class AncientArtifactsConfig extends ManasConfig {
   public List<String> loot_changes = List.of("artifacts:artifact");
   public AncientArtifactsConfig.ArtifactConfig artifacts = new AncientArtifactsConfig.ArtifactConfig();
   public AncientArtifactsConfig.GradeConfig grade1 = new AncientArtifactsConfig.GradeConfig(0.02, 3.0, 225000.0, List.of(225000.0, 1000000.0, 5000000.0));
   public AncientArtifactsConfig.GradeConfig grade2 = new AncientArtifactsConfig.GradeConfig(0.02, 5.0, 52000.0, List.of(52000.0, 225000.0));
   public AncientArtifactsConfig.GradeConfig grade3 = new AncientArtifactsConfig.GradeConfig(0.02, 10.0, 52000.0, List.of(52000.0));

   public AncientArtifactsConfig.GradeConfig getGradeConfig(int grade) {
      return switch (grade) {
         case 1 -> this.grade1;
         case 2 -> this.grade2;
         case 3 -> this.grade3;
         default -> this.grade1;
      };
   }

   public String getFileName() {
      return "ancient_artifacts/main_config";
   }

   public static class ArtifactConfig extends ManasSubConfig {
      public boolean random_refinement_upgrades = true;
      public int max_grade_1_artifacts = -1;
   }

   public static class GradeConfig extends ManasSubConfig {
      public double EPGain;
      public double MaxRefinement;
      public double StartingEp;
      public List<Double> EffectUnlockEp;

      public GradeConfig(double EPGain, double MaxRefinement, double StartingEp, List<Double> EffectUnlockEp) {
         this.EPGain = EPGain;
         this.MaxRefinement = MaxRefinement;
         this.StartingEp = StartingEp;
         this.EffectUnlockEp = EffectUnlockEp;
      }
   }
}
