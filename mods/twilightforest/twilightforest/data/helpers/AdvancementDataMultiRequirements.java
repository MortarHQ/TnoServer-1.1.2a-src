package twilightforest.data.helpers;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.Advancement.Builder;
import twilightforest.beanification.Component;

@Component
public class AdvancementDataMultiRequirements {
   public AdvancementDataMultiRequirements.MultiRequirementsBuilder wrap(Builder parent) {
      return new AdvancementDataMultiRequirements.MultiRequirementsBuilder(parent);
   }

   public static final class MultiRequirementsBuilder {
      private final Builder parent;
      private List<String> currentRequirements = new ArrayList<>();
      private final List<List<String>> totalRequirements = new ArrayList<>();

      private MultiRequirementsBuilder(Builder parent) {
         this.parent = parent;
      }

      public AdvancementDataMultiRequirements.MultiRequirementsBuilder addCriterion(String key, Criterion<?> criterion) {
         this.parent.addCriterion(key, criterion);
         this.currentRequirements.add(key);
         return this;
      }

      public AdvancementDataMultiRequirements.MultiRequirementsBuilder and() {
         this.totalRequirements.add(this.currentRequirements);
         this.currentRequirements = new ArrayList<>();
         return this;
      }

      public Builder requirements() {
         this.and();
         return this.parent.requirements(new AdvancementRequirements(this.totalRequirements));
      }
   }
}
